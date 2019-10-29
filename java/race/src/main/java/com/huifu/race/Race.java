package com.huifu.race;

import com.huifu.race.sort.FileSorter;
import com.huifu.race.sort.LineConverter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author bin.zhang
 */
public class Race implements Closeable{

    private static final Logger LOGGER = LoggerFactory.getLogger(FileSorter.class);

    private String lineSeparator = java.security.AccessController.doPrivileged(
            new sun.security.action.GetPropertyAction("line.separator"));

    /**
     * 地区文件缓冲区容量
     */
    private int perAreaIoBufferSize;

    /**
     * 原始文件，目标文件缓冲区容量
     */
    private int srcDstIoBufferSize;

    private ThreadPoolExecutor areaThreadPool;

    private FileSorter<Row> sorter;

    private int areaColumnIdx = 7;

    private int registerTimeColumnIdx = 9;

    private int ageColumnIdx = 2;

    private boolean clean;

    private Comparator<Row> inAreaRowComparator;

    private LineConverter<Row> converter = new LineConverter<Row>() {

        @Override
        public Row convert(String line) {
            return new Row(line, Arrays.asList(line.split(",")));
        }

        @Override
        public String toString(Row row) {
            return row.getLine();
        }
    };

    /**
     * @param inMemAreaMax 常驻内存area最大个数
     * @param srcDstIoBufferSize 原始文件结果文件io缓冲区容量
     * @param perAreaFileIoBufferSize 每个地区文件io缓冲区容量
     * @param perAreaSortInMemRowNum 每个地区内排序时, 加载进内存的行数
     * @param areaThreadNum 处理地区数据的线程数
     * @param sortThreadNum 排序线程数
     * @param clean 排序完成后是否清除临时文件
     */
    public Race(int inMemAreaMax,
                int srcDstIoBufferSize,
                int perAreaFileIoBufferSize,
                int perAreaSortInMemRowNum,
                int areaThreadNum,
                int sortThreadNum,
                boolean clean) {
        Area.setInMemAreaMax(inMemAreaMax);
        this.srcDstIoBufferSize = srcDstIoBufferSize;
        this.perAreaIoBufferSize = perAreaFileIoBufferSize;
        this.inAreaRowComparator = Comparator.comparingInt(r -> Integer.parseInt(r.getColumns().get(ageColumnIdx)));
        this.clean = clean;

        this.areaThreadPool = new ThreadPoolExecutor(areaThreadNum, areaThreadNum, 10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(areaThreadNum), new CustomizableThreadFactory("area-"), new ThreadPoolExecutor.CallerRunsPolicy());

        ThreadPoolExecutor sortThreadPool = new ThreadPoolExecutor(sortThreadNum, sortThreadNum, 10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(sortThreadNum), new CustomizableThreadFactory("sort-"),new ThreadPoolExecutor.CallerRunsPolicy());
        this.sorter = new FileSorter<>(inAreaRowComparator, converter, sortThreadPool, 2, perAreaSortInMemRowNum, perAreaFileIoBufferSize, clean);
    }

    public void race(File src, File sorted, File filter) throws Exception{
        File workDir = workDir(sorted);
        if(workDir.exists()){
            FileUtils.cleanDirectory(workDir);
        } else {
            FileUtils.forceMkdir(workDir);
        }

        List<Area> areaList = split(src, workDir);

        LOGGER.info(String.format("split to %s areas", areaList.size()));

        sort(areaList);

        LOGGER.info("all area sorted");

        mergeAndFilter(areaList, sorted, filter);

        LOGGER.info("all area merged");

        areaList.forEach(IOUtils::closeQuietly);

        if(clean){
            FileUtils.deleteQuietly(workDir);
        }
    }

    private void writeLine(Writer writer, String line){
        try {
            writer.write(line);
            writer.write(lineSeparator);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


    private List<Area> split(File src, File workDir){
        Map<String, Area> nameAreaMap = new ConcurrentHashMap<>(512);

        BlockingQueue<String> lineQueue = new LinkedBlockingQueue<>();

        AtomicInteger lineProduced = new AtomicInteger(0);
        AtomicInteger lineConsumed = new AtomicInteger(0);
        Runnable runnable = () -> {
            String line;
            try {
                while (true){
                    line = lineQueue.take();
                    appendLineToArea(line, nameAreaMap, workDir, perAreaIoBufferSize);
                    int n = lineConsumed.incrementAndGet();
                    if(n % 1000000 == 0){
                        LOGGER.info(String.format("consumed %s lines", n));
                    }
                }
            } catch (InterruptedException e) {
                //do nothing
            }
        };

        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < areaThreadPool.getMaximumPoolSize(); i++) {
            futures.add(areaThreadPool.submit(runnable));
        }

        try (BufferedReader is = new BufferedReader(new FileReader(src), srcDstIoBufferSize)) {
            String line;
            while ((line = is.readLine()) != null){
                lineQueue.add(line);
                lineProduced.incrementAndGet();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true){
            if(lineProduced.get() == lineConsumed.get()){
                futures.forEach(f -> f.cancel(true));
                break;
            }
        }

        List<Area> areaList =  new ArrayList<>(nameAreaMap.values());
        try {
            for (Area area : areaList) {
                area.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return areaList;
    }


    private void mergeAndFilter(List<Area> areaList, File sorted, File filter) throws IOException{
        BufferedOutputStream sortedOs = new BufferedOutputStream(new FileOutputStream(sorted), srcDstIoBufferSize);
        Writer sortedWriter = new OutputStreamWriter(sortedOs, StandardCharsets.UTF_8);
        Writer filterWriter = new BufferedWriter(new FileWriter(filter));
        for (Area area : areaList) {
            if(area.getName().contains("上海")){
                try (Stream<String> stream = area.rows()) {
                    stream.forEach(l -> {
                        writeLine(sortedWriter, l);
                        Row row = converter.convert(l);
                        String sex = row.getColumns().get(3);
                        int age = Integer.valueOf(row.getColumns().get(2));
                        double spent = Double.valueOf(row.getColumns().get(8));
                        if(sex.equals("女") && age>=18 && age <=25 && spent >= 50000 && spent <=100000){
                            writeLine(filterWriter, l);
                        }
                    });
                }
            } else {
                area.writeTo(sortedOs);
            }
            sortedWriter.flush();
            LOGGER.info(String.format("%s, merged", area.getName()));
        }

        IOUtils.closeQuietly(sortedWriter, filterWriter, sortedOs);
    }

    private void sort(List<Area> areaList) throws InterruptedException{
        areaList.sort(Comparator.comparing(Area::getFirstRegisterTime));
        AtomicInteger sorted = new AtomicInteger();
        List<Callable<Boolean>> tasks = areaList
                .stream()
                .map(area -> (Callable<Boolean>) () -> {
                    area.sort(sorter);
                    LOGGER.info(String.format("%s,%s sorted", sorted.incrementAndGet(), area.getName()));
                    return true;
                })
                .collect(Collectors.toList());

        areaThreadPool.invokeAll(tasks);
    }

    private void appendLineToArea(String line, Map<String, Area> nameAreaMap, File workDir, int areaIoBufferSize){
        try {
            Row row = converter.convert(line);
            String name = null;
            String registerTime = null;
            try {
                name = row.getColumns().get(areaColumnIdx);
                registerTime = row.getColumns().get(registerTimeColumnIdx);
            } catch (Exception e) {
                throw new IllegalArgumentException(line);
            }
            Area area = nameAreaMap.get(name);
            if(area == null){
                synchronized (this){
                    area = nameAreaMap.get(name);
                    if(area == null){
                        area = new Area(name, workDir, areaIoBufferSize, converter);
                        nameAreaMap.putIfAbsent(name, area);
                    }
                }
            }

            if(area.getFirstRegisterTime() == null){
                area.setFirstRegisterTime(registerTime);
            } else if(registerTime.compareTo(area.getFirstRegisterTime()) < 0){
                area.setFirstRegisterTime(registerTime);
            }

            area.append(row);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File workDir(File dst){
        return  new File(dst.getPath() + ".tmp");
    }

    @Override
    public void close() throws IOException {
        areaThreadPool.shutdownNow();
        sorter.close();
    }
}
