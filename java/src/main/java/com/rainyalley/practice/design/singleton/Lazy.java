package com.rainyalley.practice.design.singleton;

/**
 * Created by sllx on 7/29/15.
 */
public class Lazy {

    private static Object sig;

    public static Object getInstance(){
        if(sig == null){
          sig = new Object();
        }
        return sig;
    }
}
