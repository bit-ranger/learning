package com.rainyalley.practice.design.factory.absfactory;

/**
 * Created by sllx on 7/30/15.
 */
public class Main {
    private static Factory factory = new DemoFactory();

    public static void main(String[] args) {
        ProductA productA = factory.createA();
        ProductB productB = factory.createB();
        productA.showA();
        productB.showB();
    }
}
