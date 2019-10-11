package com.rainyalley.practice.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;


public class LockTest {

    private Lock reentrantLock = new ReentrantLock();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();
    //can not newCondition with readLock
    private Condition condition = reentrantLock.newCondition();
    private List<Integer>  list = Arrays.asList(1,2,3,4,5,6,7,8,9,0);


    private Runnable task = new Runnable() {
        @Override
        public void run() {
            reentrantLock.lock();
            //if pool-1-thread-1 is the last, no thread will notify it;
            while (Thread.currentThread().getName().equals("pool-1-thread-1")){
                try {
                    condition.await();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (Integer integer : list) {
                System.out.println(integer);
                System.out.println(Thread.currentThread().getName());
            }
            condition.signalAll();
            reentrantLock.unlock();
        }
    };

    private ExecutorService executorService = Executors.newFixedThreadPool(3);

    public void execute(){
        executorService.execute(task);
        executorService.execute(task);
        executorService.execute(task);
        executorService.shutdown();
        System.out.println("---");
    }

    public static void main(String[] args) throws InterruptedException {
        new LockTest().execute();

        new AtomicInteger().incrementAndGet();
        ThreadLocalRandom.current().nextInt();
    }


    public synchronized void test(){
        //do something
        synchronized(this) {
            //do something
            synchronized (this){
                //do something
                synchronized (this){
                    //do something
                }
            }
        }
    }
}
