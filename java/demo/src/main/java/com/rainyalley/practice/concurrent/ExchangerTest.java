package com.rainyalley.practice.concurrent;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerTest {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        ExecutorService executor = Executors.newCachedThreadPool();

        Runnable producer = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        String data = "produce";
                        System.out.println(Thread.currentThread().getName() + "-offer----" + data);
                        exchanger.exchange(data);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Runnable consumer = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        String data = exchanger.exchange(null);
                        System.out.println(Thread.currentThread().getName() + "-get----" + data);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        executor.execute(producer);
        executor.execute(consumer);
        executor.shutdown();
    }
}
