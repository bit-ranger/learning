package com.rainyalley.practice.design.strategy;

/**
 * Created by sllx on 7/29/15.
 */
public class Main {
    public static void main(String[] args) {
        Fly fly = new RocketFly();
        Duck model = new ModelDuck();
        model.fly();
        model.setFly(fly);
        model.fly();
    }
}
