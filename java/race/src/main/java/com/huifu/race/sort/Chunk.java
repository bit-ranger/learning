package com.huifu.race.sort;

import java.io.*;
import java.util.*;

/**
 * @author bin.zhang
 */
public class Chunk<T> implements Closeable {

    private File chunkFile;

    private BufferedReader chunkFileReader;

    private int level;

    private int idx;

    private int size;

    private List<T> rows = Collections.emptyList();

    private List<Chunk<T>> parts = Collections.emptyList();

    private Comparator<T> comparator;

    private LineConverter<T> converter;

    private T head;

    public Chunk(int level, int idx, List<T> rows, LineConverter<T> converter) {
        this.level = level;
        this.idx = idx;
        this.rows = rows;
        this.size = rows.size();
        this.converter = converter;
    }

    public Chunk(List<Chunk<T>> chunks, Comparator<T> comparator, LineConverter<T> converter){
        this.level = chunks.get(0).getLevel() + 1;
        this.idx = chunks.get(0).getIdx();
        this.size = chunks.stream().map(Chunk::getSize).reduce((p, n) -> p + n).get();
        this.comparator = comparator;
        this.converter = converter;
        this.parts = chunks;
    }

    public void store(File workDir, int bufferSize){
        try {
            this.chunkFile =  new File(String.format(workDir.getPath() + File.separator +"%s_%s_%s", level, idx, size));
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(chunkFile), bufferSize)){
                if(!rows.isEmpty()){
                    for (T chunkRow : rows) {
                        bw.write(converter.toString(chunkRow));
                        bw.newLine();
                    }
                    rows = Collections.emptyList();
                }

                if(!parts.isEmpty()){
                    while (true) {
                        List<Chunk<T>> chunkList =  new ArrayList<>(parts);
                        Chunk<T> headMinChunk = peekMinRemoveEmpty(chunkList);
                        //所有chunk都读完了
                        if (headMinChunk == null) {
                            break;
                        }

                        T line = headMinChunk.take();
                        bw.write(converter.toString(line));
                        bw.newLine();
                    }
                    parts = Collections.emptyList();
                }

            }

            this.chunkFileReader = new BufferedReader(new FileReader(chunkFile), bufferSize);
            this.head = converter.convert(chunkFileReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private T peek(){
        return head;
    }

    private T take(){
        if(head == null){
            throw new IllegalStateException();
        }

        T result = head;
        try {
            head = converter.convert(chunkFileReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private Chunk<T> peekMinRemoveEmpty(List<Chunk<T>> list) {
        Iterator<Chunk<T>> i = list.iterator();
        Chunk<T> candidate = null;

        while (i.hasNext()) {
            Chunk<T> next = i.next();

            if (next.peek() == null) {
                i.remove();
                continue;
            } else if (candidate == null) {
                candidate = next;
                continue;
            }

            if (comparator.compare(next.peek(), candidate.peek()) < 0) {
                candidate = next;
            }
        }
        return candidate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Chunk{");
        sb.append("level=").append(level);
        sb.append(", idx=").append(idx);
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }

    public int getLevel() {
        return level;
    }

    public int getIdx() {
        return idx;
    }

    public int getSize() {
        return size;
    }

    public File getChunkFile() {
        return chunkFile;
    }

    @Override
    public void close() throws IOException {
        chunkFileReader.close();
    }
}
