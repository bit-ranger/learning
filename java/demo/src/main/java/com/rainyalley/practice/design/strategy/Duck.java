package com.rainyalley.practice.design.strategy;

/**
 * Created by sllx on 7/29/15.
 */
public abstract class Duck {
    private Fly fly = new CannotFly();

    public void fly() {
        fly.fly();
    }

    public void setFly(Fly fly) {
        this.fly = fly;
    }
}
