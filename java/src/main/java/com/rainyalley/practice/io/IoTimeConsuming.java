package com.rainyalley.practice.io;

import java.io.*;

public class IoTimeConsuming {

    public static void main(String[] args) throws Exception{
        long start = System.currentTimeMillis();

        File file = new File("/var/IoTimeConsuming.txt");
        int bufferSize = 1024*1024*2;
        BufferedWriter bw = new BufferedWriter(new FileWriter(file), bufferSize);
        BufferedReader br = new BufferedReader(new FileReader(file), bufferSize);

        for (int i = 0; i < 1000000; i++) {
            bw.write("1,6000060097530001,201603060,20160306,2000.00,P,10.00");
            bw.newLine();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
