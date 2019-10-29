package com.rainyalley.practice.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by sllx on 15-1-30.
 */
public class MethodTest {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        Method method = Class.forName("java.lang.Shutdown").getDeclaredMethod("exit", int.class);
        method.setAccessible(true);
        method.invoke(null, 0);
    }
}

class Test {
    private static void test() {
        System.out.println("it works");
    }
}