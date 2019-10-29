package com.huifu.race;

import com.huifu.race.sort.FileSorter;
import com.huifu.race.sort.LineConverter;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author bin.zhang
 */
public class Area implements Closeable {

    private static int inMemAreaMax;

    private static int inMemAreaNum = 0;

    private String name;

    private int ioBufferSize;

    private File originFile;

    private File sortedFile;

    private BufferedWriter originWriter;

    private boolean inMem;

    private List<Row> rows;

    private LineConverter<Row> converter;

    private String firstRegisterTime;

    private boolean sorted;

    public static void setInMemAreaMax(int inMemAreaMax) {
        Area.inMemAreaMax = inMemAreaMax;
    }

    public Area(String name, File workDir, int ioBufferSize, LineConverter<Row> converter) throws IOException{
        if(inMemAreaNum >= inMemAreaMax){
            this.inMem = false;
            this.ioBufferSize = ioBufferSize;
            this.originFile = new File(workDir + File.separator + name);
            this.originWriter = new BufferedWriter(new FileWriter(originFile), this.ioBufferSize);
            this.sortedFile = new File(workDir + File.separator + name + ".sorted");
        } else {
            this.inMem = true;
            this.rows = new ArrayList<>();
            ++inMemAreaNum;
        }
        this.converter = converter;
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public String getFirstRegisterTime() {
        return firstRegisterTime;
    }

    public void setFirstRegisterTime(String firstRegisterTime) {
        this.firstRegisterTime = firstRegisterTime;
    }

    public void append(Row row){
        synchronized (this){
            if(inMem){
                rows.add(row);
            } else {
                try {
                    appendToFile(row, originWriter);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void appendToFile(Row row, BufferedWriter writer) throws IOException{
        writer.write(converter.toString(row));
        writer.newLine();
    }

    @Override
    public void close() throws IOException {
        if(!inMem){
            originWriter.close();
        }
    }

    public void sort(FileSorter<Row> fileSorter){
        try {
            if(inMem){
                rows.sort(fileSorter.getComparator());
            } else {
                fileSorter.sort(originFile, sortedFile);
            }

            this.sorted = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Stream<String> rows(){
        try {
            if(inMem){
                return rows.stream().map(r -> converter.toString(r));
            } else {
                BufferedReader reader;
                if(sorted){
                    reader = new BufferedReader(new FileReader(sortedFile), ioBufferSize);
                } else {
                    reader = new BufferedReader(new FileReader(originFile), ioBufferSize);
                }
                return reader.lines()
                        .onClose(asUncheckedRunnable(reader));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeTo(OutputStream os) throws IOException{
        if(inMem){
            rows.stream().map(r -> converter.toString(r)).forEach(l -> {
                try {
                    os.write(l.getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            BufferedInputStream is;
            if(sorted){
                is = new BufferedInputStream(new FileInputStream(sortedFile), ioBufferSize);
            } else {
                is = new BufferedInputStream(new FileInputStream(originFile), ioBufferSize);
            }
            IOUtils.copyLarge(is, os);
            IOUtils.closeQuietly(is);
        }
    }

    private static Runnable asUncheckedRunnable(Closeable c) {
        return () -> {
            try {
                c.close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }
}
