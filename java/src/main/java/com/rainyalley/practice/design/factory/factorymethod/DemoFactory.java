package com.rainyalley.practice.design.factory.factorymethod;

/**
 * Created by sllx on 7/30/15.
 */
public class DemoFactory implements Factory{
    @Override
    public Product create() {
        return new DemoProduct();
    }
}
