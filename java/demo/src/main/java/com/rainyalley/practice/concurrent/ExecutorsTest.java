package com.rainyalley.practice.concurrent;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ExecutorsTest {
    static ExecutorService cachedExecutor = Executors.newCachedThreadPool();
    static ExecutorService fixedExecutor = Executors.newFixedThreadPool(3);
    static ExecutorService singleExecutor = Executors.newSingleThreadExecutor();
    static ExecutorService scheduledExecutor = Executors.newScheduledThreadPool(3);
    static ExecutorService workStealingExcutor = Executors.newWorkStealingPool();

    static Callable<String> task = new Callable<String>() {
        @Override
        public String call() throws Exception {
            System.out.println(Thread.currentThread().getName());
            return "call";
        }
    };

    static Lock lock = new ReentrantLock();

    static Queue<Callable<String>> tasks = new ConcurrentLinkedQueue<>();

    static Map<Callable<String>, Future<String>> futureMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        tasks.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return task.call();
            }
        });
        tasks.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return task.call();
            }
        });

        while (tasks.size() > 0) {
            lock.lock();
            Callable<String> task = tasks.remove();
            Future f = fixedExecutor.submit(task);
            futureMap.put(task, f);
            lock.unlock();
            //System.out.println(f.isDone());
        }

        fixedExecutor.submit(task);


        for (Map.Entry<Callable<String>, Future<String>> entry : futureMap.entrySet()) {
            System.out.println("exe-" + entry.getValue().isDone());
        }

        ExecutorCompletionService<String> executorCompletionService = new ExecutorCompletionService(fixedExecutor);
        executorCompletionService.submit(task);
        for (Callable<String> callable : futureMap.keySet()) {
            //take() : block,return the first completion task with future
            System.out.println("completion-" + executorCompletionService.take().isDone());
        }

        fixedExecutor.shutdown();
    }
}
