package com.rainyalley.practice.lambda;

@FunctionalInterface
interface Executable{

    void execute();

    default boolean dfm(Object str){
        System.out.println("Executable dfm");
        return false;
    }
}
