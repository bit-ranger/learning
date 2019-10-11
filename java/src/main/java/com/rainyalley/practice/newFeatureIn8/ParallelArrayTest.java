package com.rainyalley.practice.newFeatureIn8;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 并行数组
 */
public class ParallelArrayTest {
    public static void main(String[] args){
        long[] arrayOfLong = new long [ 20000 ];

        //对arrayLong所有元素随机赋值
        Arrays.parallelSetAll(arrayOfLong,
                index -> ThreadLocalRandom.current().nextInt(1000000));

        //打印前10个元素
        Arrays.stream( arrayOfLong ).limit( 10 ).forEach(
                i -> System.out.print( i + " " ) );
        System.out.println();

        //对arrayLong数组排序
        Arrays.parallelSort( arrayOfLong );

        //打印前10个元素
        Arrays.stream( arrayOfLong ).limit( 10 ).forEach(
                i -> System.out.print( i + " " ) );
        System.out.println();
    }
}
