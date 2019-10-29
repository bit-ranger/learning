package com.rainyalley.practice.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CyclicBarrierTest {
    public static void main(String[] args) {
        Runnable ready = new Runnable() {
            @Override
            public void run() {
                System.out.println("ready");
            }
        };

        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, ready);

        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            };
            executor.submit(task);
        }
        executor.shutdown();
    }


}
