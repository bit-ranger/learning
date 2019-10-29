package com.rainyalley.practice.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CopyOnWriteArrayListTest {
    static CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        copyOnWriteArrayList.add("1");
        copyOnWriteArrayList.add("2");
        copyOnWriteArrayList.add("3");

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                //lock
                copyOnWriteArrayList.add(Thread.currentThread().getName());
                //unlock
                for (String s : copyOnWriteArrayList) {
                    System.out.println(s);
                }
                return "hello";
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Callable<String>> tasks = Arrays.asList(callable, callable, callable);
        executorService.invokeAll(tasks);
    }
}
