package com.rainyalley.practice.design.factory.factorymethod;

/**
 * Created by sllx on 7/30/15.
 */
public class Main {
    private static Factory factory = new DemoFactory();
    public static void main(String[] args){
        Product product = factory.create();
        product.show();
    }
}
