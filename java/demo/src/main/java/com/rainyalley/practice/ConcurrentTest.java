/**
 * @fileName Main.java
 * @package org.sllx.concurrent
 * @copyright (c) 2014, sincerebravefight@gmail.com All Rights Reserved.
 * @description TODO
 * @author sllx
 * @date 2014-8-27 上午11:27:51
 * @modifiedBy sllx
 * @ModifiedDate 2014-8-27 上午11:27:51
 * Why & What is modified
 * @version V1.0
 */
package com.rainyalley.practice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 并发
 */
public class ConcurrentTest {
    private static final int THREAD_NUM = 200;
    private static final int CLIENT_NUM = 500;


    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semp = new Semaphore(THREAD_NUM);
        for (int i = 0; i < CLIENT_NUM; i++) {
            final int n = i;
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        semp.acquire();
                        //业务逻辑
                        System.out.println(n);
                        semp.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };
            exec.execute(r);
        }
        exec.shutdown();
    }
}
