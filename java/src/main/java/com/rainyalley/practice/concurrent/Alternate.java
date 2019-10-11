package com.rainyalley.practice.concurrent;


public class Alternate {
    public static void main(String[] args){
        Service service = new Service();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    service.main();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    service.sub();
                }
            }
        }).start();
    }

}

class Service{

    private boolean mainBegin = true;

    synchronized void main(){
        while (!mainBegin){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("i am " + i + "of main");
        }
        this.mainBegin = false;
        this.notifyAll();
    }

    synchronized void sub(){
        while (mainBegin){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("i am " + i + "of sub");
        }
        this.mainBegin = true;
        this.notifyAll();
    }
}
