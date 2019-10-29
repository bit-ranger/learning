package com.huifu.race.sort;

import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Ignore
public class FileSorterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileSorterTest.class);

    @Test
    public void sort() throws Exception {

        Random random = new Random();
        int numbers = 500000;
        File file = new File("/var/sort/architecture_user.csv");
        File dest = new File("/var/sort/architecture_user.sorted.csv");
        if(dest.exists()){
            dest.delete();
        }

        List<String> memoryList = new ArrayList<>(numbers);

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            for (int i = 0; i < numbers; i++) {
                long id = random.nextLong();
                String line = String.valueOf(id) + ",0.02,I";
                memoryList.add(line);
                bw.write(line);
                bw.newLine();
            }
        }

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1),
                new ThreadPoolExecutor.CallerRunsPolicy());

        FileSorter<String> sorter = new FileSorter<>(String::compareTo,
                new LineConverter<String>() {
                    @Override
                    public String convert(String line) {
                        return line;
                    }

                    @Override
                    public String toString(String t) {
                        return t;
                    }
                },
                threadPoolExecutor,
                2, 5000, 1024 * 1024 * 2, false);
        long start = System.currentTimeMillis();
        sorter.sort(file, dest);
        long end = System.currentTimeMillis();
        LOGGER.info("elapsed time {}", end - start);

        List<String> diskList   = new ArrayList<>(memoryList.size());
        try(BufferedReader br = new BufferedReader(new FileReader(dest))){
            String dLine = null;
            while ((dLine = br.readLine()) != null){
                diskList.add(dLine);
            }
        }

        memoryList.sort(String::compareTo);
        Assert.assertEquals(memoryList, diskList);
    }

    @After
    public void after(){
        new File("/var/sort/architecture_user.csv").delete();
        new File("/var/sort/architecture_user.sorted.csv").delete();
    }
}