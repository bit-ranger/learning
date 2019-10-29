package com.rainyalley.practice.design.singleton;

/**
 * Created by sllx on 7/29/15.
 */
public class InnerStatic {


    public static Object getInstance() {
        return SingletonHolder.sing;
    }

    private static class SingletonHolder {
        private static Object sing = new Object();
    }
}
