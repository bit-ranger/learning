package com.rainyalley.practice.jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by sllx on 14-12-1.
 */
public class GCTest {

    private static final int _1MB = 1024 * 1024;

    /* DirectMemery直接内存溢出
     * -verbose:gc
     * -Xms10M -Xmx10M -XX:+HeapDumpOnOutOfMemoryError
     * -Xss108K
     */
    public void direct() throws IllegalAccessException {
        Field unSafeField = Unsafe.class.getDeclaredFields()[0];
        unSafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unSafeField.get(null);
        while (true) {
            unsafe.allocateMemory(1024 * 1024);
        }
    }

    /* 查看使用SerialGC时内存情况，对象优先在老年代分配，若Survivor空间不足，将担保分配至老年代。
     * -XX:+UseSerialGC
     * -verbose:gc
     * -Xms20M -Xmx20M -Xmn10M
     * -XX:+PrintGCDetails
     * -XX:SurvivorRatio=8
     */
    public void serial() {
        byte[] all1, all2, all3, all4;
        all1 = new byte[2 * _1MB];
        all2 = new byte[2 * _1MB];
        all3 = new byte[2 * _1MB];
        all4 = new byte[4 * _1MB];//发生GC
    }

}
