package com.rainyalley.practice.design.factory.absfactory;

/**
 * Created by sllx on 7/30/15.
 */
public class DemoFactory implements Factory {
    @Override
    public ProductA createA() {
        return new DemoA();
    }

    @Override
    public ProductB createB() {
        return new DemoB();
    }
}
