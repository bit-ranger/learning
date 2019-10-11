package com.rainyalley.practice.design.strategy;

/**
 * Created by sllx on 7/29/15.
 */
public class CannotFly implements Fly{
    @Override
    public void fly() {
        System.out.println("can not fly");
    }
}
