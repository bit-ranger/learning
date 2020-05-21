package com.rainyalley.practice;

import com.sun.xml.internal.ws.util.CompletedFuture;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zhangbin
 * @date 2020-05-21
 */

public class MultiFuture {

    private static BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(200);
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(10,10, 10, TimeUnit.SECONDS, blockingQueue);
    private static CompletableFuture<?>[] type = new CompletableFuture[]{};

    private void a() {
        List<CompletableFuture<?>> futureList = IntStream.range(0, 10)
                .mapToObj(ai -> CompletableFuture.runAsync(() -> b(ai), executor)
                ).collect(Collectors.toList());
        CompletableFuture.allOf(futureList.toArray(type))
                .thenRunAsync(() -> sleep(5))
                .thenRunAsync(() -> System.out.println("A-finished"), executor)
                .thenRunAsync(() -> executor.shutdown());
    }

    private void b(int ai){
        List<CompletableFuture<?>> futureList = IntStream.range(0, 10)
                .mapToObj(bi -> CompletableFuture.runAsync(() -> System.out.println(ai + "-B-" + bi), executor)
                ).collect(Collectors.toList());
        CompletableFuture.allOf(futureList.toArray(type)).thenRunAsync(() ->
                System.out.println(ai + "-B-finished"), executor);
    }

    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        new MultiFuture().a();
    }
}
