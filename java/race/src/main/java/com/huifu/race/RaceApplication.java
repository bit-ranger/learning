package com.huifu.race;


import com.huifu.race.sort.FileSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author bin.zhang
 */
public class RaceApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileSorter.class);

    public static void main(String[] args) throws Exception{
        long start = System.currentTimeMillis();

        File srcFile = new File(args[0]);
        File sortedFile = new File(args[1]);
        File filterFile = new File(args[2]);
        int inMemAreaMax = Integer.valueOf(args[3]);
        int srcDstIoBufferSize = Integer.valueOf(args[4]);
        int perAreaFileIoBufferSize = Integer.valueOf(args[5]);
        int perAreaSortInMemRowNum = Integer.valueOf(args[6]);
        int areaThreadNum = Integer.valueOf(args[7]);
        int sortThreadNum = Integer.valueOf(args[8]);

        Race race = new Race(inMemAreaMax, srcDstIoBufferSize, perAreaFileIoBufferSize, perAreaSortInMemRowNum, areaThreadNum, sortThreadNum, false);
        race.race(srcFile, sortedFile, filterFile);
        race.close();

        long end = System.currentTimeMillis();

        LOGGER.info("elapsed time {}", end - start);

    }

}
