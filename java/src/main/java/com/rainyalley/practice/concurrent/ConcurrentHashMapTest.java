package com.rainyalley.practice.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

public class ConcurrentHashMapTest {

    static Map<String,String>  map = new ConcurrentHashMap();

    public static void main(String[] args) throws InterruptedException {

        map.put("1","1");
        map.put("2","2");
        map.put("3","3");
        map.put("4","4");
        Set<Map.Entry<String,String>> entrySet = map.entrySet();

        Callable<String> callable = new Callable() {
            @Override
            public String call() {
                for (Map.Entry<String, String> entry : entrySet) {
                    System.out.println(entry.getKey());
                    entrySet.remove(entry);
                    System.out.println(Thread.currentThread().getName());
                }
                return "hello";
            }
        };

        ExecutorService executorService =  Executors.newFixedThreadPool(3);

        List<Callable<String>> tasks = Arrays.asList(callable, callable, callable);

        executorService.invokeAll(tasks).stream().forEach(p -> {
            try {
                System.out.println(p.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }
}
