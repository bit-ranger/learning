package com.rainyalley.practice.design.singleton;

/**
 * Created by sllx on 7/29/15.
 */
public class DoubleCheck {
    private volatile static Object sing;

    public static Object getInstance() {
        if (sing == null) {
            synchronized (DoubleCheck.class) {
                if (sing == null) {
                    sing = new Object();
                }
            }
        }
        return sing;
    }
}
