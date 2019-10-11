package com.rainyalley.practice;
import static  java.lang.System.out;

public class MoveOprator {

    public static void main(String[] args) {
        int i = -2;
        out.println(Integer.toBinaryString(i));
        out.println(Integer.toBinaryString(i >>> 3));
        out.println(i >>> 3);
        out.println(Integer.MAX_VALUE >> 2);
    }
}
