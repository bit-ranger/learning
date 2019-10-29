package com.rainyalley.practice.design.temmethod;

/**
 * Created by sllx on 7/30/15.
 */
public abstract class Drink {
    public final void recipe() {
        System.out.println("before blew");
        blew();
        System.out.println("after blew");
    }

    protected abstract void blew();
}
