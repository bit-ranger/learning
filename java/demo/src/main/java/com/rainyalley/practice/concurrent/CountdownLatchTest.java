package com.rainyalley.practice.concurrent;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountdownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        int concurrency = 5;
        ExecutorService executor = Executors.newCachedThreadPool();

        final CountDownLatch ready = new CountDownLatch(concurrency);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(concurrency);
        for (int i = 0; i < concurrency; i++) {
            executor.execute(new Runnable() {
                public void run() {
                    ready.countDown();
                    try {
                        start.await();
                        System.out.println(Thread.currentThread().getName() + "----done");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    done.countDown();
                }
            });
        }
        ready.await();
        System.out.println("all threads are ready");
        start.countDown();
        done.await();
        System.out.println("all threads finished");
        executor.shutdown();
    }
}
