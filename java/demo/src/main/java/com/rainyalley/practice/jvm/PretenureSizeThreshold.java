package com.rainyalley.practice.jvm;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * -server
 * -XX:+UseConcMarkSweepGC
 * -Xms20M
 * -Xmx20M
 * -Xmn10M
 * -XX:SurvivorRatio=8
 * -XX:PretenureSizeThreshold=2000000
 * -XX:+PrintGC
 * -XX:+PrintGCDetails
 * -XX:+PrintGCDateStamps
 * -XX:+PrintHeapAtGC
 * -Xloggc:/var/logs/PretenureSizeThreshold.log
 * -XX:+HeapDumpOnOutOfMemoryError
 * -XX:HeapDumpPath=/var/dumps/PretenureSizeThreshold.hprof
 * <p>
 * <p>
 * //////////////////////////////////////////
 * jmap -dump:format=b,file=/var/dumps/PretenureSizeThreshold.jmap.hprof <pid>
 */
public class PretenureSizeThreshold {

    private static final int _1MB = 1000 * 1000;

    public static void main(String[] args) throws Exception {
        RuntimeMXBean mxBean = ManagementFactory.getRuntimeMXBean();
        System.out.println(mxBean.getName());
        String pid = mxBean.getName().split("@")[0];


        byte[] allocation = new byte[3 * _1MB];

        while (true) {
            Thread.sleep(1000);
        }

    }
}
