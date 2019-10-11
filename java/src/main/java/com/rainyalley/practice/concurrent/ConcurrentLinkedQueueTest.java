package com.rainyalley.practice.concurrent;


import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ConcurrentLinkedQueueTest {
    static Queue<String> queen = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) throws InterruptedException {
        queen.add("3");
        queen.add("2");
        queen.add("1");
        queen.add("4");


        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                //lock
                queen.add(Thread.currentThread().getName());
                //unlock
                //ConcurrentModification
                queen.forEach(p -> System.out.println(queen.remove()));
                return "hello";
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Callable<String>> tasks = Arrays.asList(callable, callable, callable);
        executorService.invokeAll(tasks);
    }
}
