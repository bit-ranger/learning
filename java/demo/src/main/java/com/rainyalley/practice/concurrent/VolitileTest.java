package com.rainyalley.practice.concurrent;


import java.util.concurrent.CountDownLatch;

/**
 * Created by sllx on 7/8/15.
 */
public class VolitileTest {

    volatile int count;

    public static void main(String[] args) throws InterruptedException {

        int concurrency = 1000;

        CountDownLatch done = new CountDownLatch(concurrency);

        VolitileTest volitileTest = new VolitileTest();

        for (int i = 0; i < concurrency; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        volitileTest.inc();
                    }
                    done.countDown();
                }
            }).start();
        }

        done.await();

        System.out.println(volitileTest.count);


//
//        Bean bean = new Bean();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                bean.run();
//            }
//        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                bean.stop();
//            }
//        }).start();
    }

    public void inc() {
        //maybe read same count
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
    }


//    static class Bean{
//
//        boolean stop;
//
//        void run(){
//            while (!stop){
//                try {
//                    Thread.sleep(300);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("running");
//            }
//        }
//
//
//        void stop(){
//            stop = true;
//            try {
//                Lock lock = new ReentrantLock();
//                lock.lock();
//                lock.newCondition().await();
//                lock.unlock();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
