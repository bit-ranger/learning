package com.rainyalley.practice.design.singleton;

/**
 * Created by sllx on 7/29/15.
 */
public class Hunger {
    private static Object sig = new Object();

    public static Object getInstance() {
        return sig;
    }
}
