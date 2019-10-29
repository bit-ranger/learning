package com.rainyalley.practice;

import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by sllx on 15-1-21.
 */
public class Polymorphic {

    public static void main(String[] args) {
        //会先匹配方法参数类型在继承链中离自己最近的方法,如果有两个重载方法与自己最近，编译将不通过
        //Ambiguous method call
        //new Polymorphic().p("hello polymorphic");
        //new Polymorphic().p(p -> System.out.print(p));
    }

    private void p(Object s) {
        System.out.printf("p(Object) says %s", s);
    }

    private void p(Serializable s) {
        System.out.printf("p(Object) says %s", s);
    }

//    private void p(String s){
//        System.out.printf("p(String) says %s", s);
//    }

    private void p(Comparable<?> s) {
        System.out.printf("p(Comparable) says %s", s);
    }

    private void p(Consumer<String> c) {
        c.accept("p(Consumer)");
    }

    private void p(Function<String, Object> c) {
        c.apply("p(Function)");
    }
}
