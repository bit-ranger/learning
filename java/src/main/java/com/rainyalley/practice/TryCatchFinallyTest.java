package com.rainyalley.practice;

/**
 * Created by sllx on 14-9-25.
 */
public class TryCatchFinallyTest {

    public static void main(String[] args)throws Exception{
//        test1();
//        test2();
        test3();
    }

    static void test1(){
        try {
            System.out.println("try");
            throw new Exception();
        } catch (Exception e) {
            System.out.println("catch");
        } finally {
            System.out.println("finally");
        }
    }

    static void test2(){
        try {
            System.out.println(1/0);
        } catch (Exception e) {
            System.out.println("catch");
        } finally {
            System.out.println("finally");
        }
    }

    static void test3() throws Exception {
        try {
            System.out.println("try");
            throw new Exception();
        } catch (Error e) {
            System.out.println("catch");
        } finally {
            System.out.println("finally");
        }
    }
}
