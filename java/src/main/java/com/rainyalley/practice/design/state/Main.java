package com.rainyalley.practice.design.state;

/**
 * Created by sllx on 7/30/15.
 */
public class Main {
    public static void main(String[] args){
        Context context = new Context();
        context.doSthB();
        context.doSthA();
        context.doSthA();
        context.doSthB();
    }
}
