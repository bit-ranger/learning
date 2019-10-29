package com.huifu.race.sort;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.*;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author bin.zhang
 */
public class FileSorter<T> implements Closeable{

    private static final Logger LOGGER = LoggerFactory.getLogger(FileSorter.class);

    /**
     * 初始化块级别
     */
    private static final int INITIAL_CHUNK_LEVEL = 1;

    /**
     * 第一行的行号
     */
    private static final int INITIAL_ROW_NUM = 1;

    /**
     * io缓冲大小
     */
    private int ioBufferSize;

    /**
     * 行内容比较器
     */
    private Comparator<T> comparator;

    /**
     * 行转换器
     */
    private LineConverter<T> converter;

    /**
     * 归并路数，必须大于或等于2
     */
    private int mergeWayNum;

    /**
     * 内存中排序的行数
     */
    private int inMemSize;

    /**
     * 排序完成后是否删除临时文件
     */
    private boolean clean;

    /**
     * 阻塞队列容量100
     * 最大空闲时间10秒
     */
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * @param comparator   行比较器
     * @param mergeWayNum  归并路数
     * @param inMemRowNum  内存中排序的行数
     * @param ioBufferSize io缓冲区容量
     * @param clean        排序完成后，是否清除临时文件
     */
    public FileSorter(Comparator<T> comparator, LineConverter<T> converter,  ThreadPoolExecutor threadPoolExecutor, int mergeWayNum, int inMemRowNum, int ioBufferSize, boolean clean) {
        Assert.isTrue(mergeWayNum >= 2, "mergeWayNum must greater than 2");
        Assert.isTrue(inMemRowNum >= 1, "inMemSize must greater than 1");
        Assert.isTrue(ioBufferSize >= 1024, "ioBufferSize must greater than 1024");

        this.converter = converter;
        this.comparator = comparator;
        this.mergeWayNum = mergeWayNum;
        this.inMemSize = inMemRowNum;
        this.ioBufferSize = ioBufferSize;
        this.clean = clean;
        this.threadPoolExecutor = threadPoolExecutor;
    }


    /**
     * 排序
     *
     * @param src 原文件
     * @param dst 排序后存文件的目录
     * @throws IOException
     */
    public void sort(File src, File dst) throws IOException {
        beforeSort(src, dst);

        File workDir = workDir(dst);

        //切割
        List<Chunk<T>> splitChunkList = split(src, workDir);

        Chunk<T> finalChunk = null;
        if(splitChunkList.size() > 1){
            finalChunk = mergeAll(splitChunkList, workDir);
        } else if(splitChunkList.size() == 1){
            finalChunk = splitChunkList.get(0);
        } else {
            throw new IllegalArgumentException("splitChunkList.size == 0");
        }
        finalChunk.close();
        FileUtils.moveFile(finalChunk.getChunkFile(), dst);
        afterSort(src, dst);
    }

    private Chunk<T> mergeAll(List<Chunk<T>> splitChunkList, File workDir){
        //块队列，将从此队列中不断取出块，合并后放入此队列
        Queue<Chunk<T>> chunkQueue = new LinkedList<>(splitChunkList);

        //当前处理的chunk level
        int currentLevel = INITIAL_CHUNK_LEVEL;

        List<Future<Chunk<T>>> mergeFutureList = new ArrayList<>();
        while (true) {
            //从队列中获取一组chunk
            List<Chunk<T>> pollChunks = pollChunks(chunkQueue, currentLevel);

            //未取到同级chunk, 表示此级别已合并完成
            if (pollChunks == null || pollChunks.isEmpty()) {
                mergeFutureList.stream().map(this::get).forEach(chunkQueue::add);
                mergeFutureList.clear();
                //chunkQueue 中只有一个元素，表示此次合并是最终合并
                if (chunkQueue.size() == 1) {
                    break;
                } else {
                    currentLevel++;
                    continue;
                }
            }

            Future<Chunk<T>> chunk = threadPoolExecutor.submit(() -> merge(pollChunks, workDir));
            mergeFutureList.add(chunk);
        }

        return chunkQueue.poll();
    }

    private Chunk<T> get(Future<Chunk<T>> future) {
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void beforeSort(File src, File dst) throws IOException {
        Assert.isTrue(src.exists(), String.format("src file not fond: %s", src.getPath()));
        Assert.isTrue(!dst.exists(), String.format("dst file must not exist: %s", dst.getPath()));

        FileUtils.cleanDirectory(workDir(dst));
    }

    private void afterSort(File src, File dst) throws IOException {
        if (clean){
            FileUtils.deleteQuietly(workDir(dst));
        }
    }

    /**
     * 合并
     *
     * @param chunks
     * @return
     * @throws IOException
     */
    private Chunk<T> merge(List<Chunk<T>> chunks, File workDir) throws IOException {
        Chunk<T> mergedChunk = new Chunk<>(chunks, comparator, converter);
        mergedChunk.store(workDir, ioBufferSize);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("merge {}, {}", chunks.size(), chunks);
        }
        for (Chunk<T> chunk : chunks) {
            chunk.close();
        }
        return mergedChunk;
    }



    /**
     * 弹出chunk列表
     *
     * @param chunkQueue 全局chunkQueue
     * @param level      弹出的trunk级别
     * @return
     */
    private List<Chunk<T>> pollChunks(Queue<Chunk<T>> chunkQueue, int level) {
        List<Chunk<T>> pollChunks = new ArrayList<>(mergeWayNum);
        for (int i = 0; i < mergeWayNum; i++) {
            Chunk<T> head = chunkQueue.peek();
            if (head == null) {
                break;
            }
            if (head.getLevel() == level) {
                pollChunks.add(chunkQueue.poll());
            } else if (head.getLevel() > level) {
                break;
            }
        }
        return pollChunks;
    }

    /**
     * 切割
     *
     * @param src 被切割的文件
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    private List<Chunk<T>> split(File src, File workDir) throws IOException {

        List<Chunk<T>> chunkList;

        try (BufferedReader br = new BufferedReader(new FileReader(src), ioBufferSize)) {
            int rowNum = INITIAL_ROW_NUM;
            String line;
            List<T> chunkRows = new ArrayList<>(inMemSize);

            List<Future<Chunk<T>>> sortFutureList = new ArrayList<>();
            while (true) {
                line = br.readLine();

                if (line != null) {
                    chunkRows.add(converter.convert(line));
                }

                if (line == null || chunkRows.size() >= inMemSize) {
                    if (chunkRows.size() > 0) {
                        final int rn = rowNum;
                        final List<T> cr = chunkRows;

                        rowNum += chunkRows.size();
                        chunkRows = new ArrayList<>();

                        Future<Chunk<T>> sort = threadPoolExecutor.submit(() -> {
                            cr.sort(comparator);
                            Chunk<T> chunk = new Chunk<>(INITIAL_CHUNK_LEVEL, rn, cr, converter);
                            chunk.store(workDir, ioBufferSize);
                            return chunk;
                        });
                        sortFutureList.add(sort);
                    }
                }

                if (line == null) {
                    break;
                }
            }
            chunkList = sortFutureList.stream().map(this::get).collect(Collectors.toList());
        }
        return chunkList;
    }

    private File workDir(File dst){
        File work = new File(dst.getPath() + ".tmp");
        if(!work.exists()){
            work.mkdirs();
        }
        return work;
    }

    @Override
    public void close() throws IOException {
        threadPoolExecutor.shutdownNow();
    }

    public Comparator<T> getComparator() {
        return comparator;
    }
}
