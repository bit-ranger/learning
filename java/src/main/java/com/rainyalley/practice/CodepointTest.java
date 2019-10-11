package com.rainyalley.practice;

/**
 * 代码点
 */
public class CodepointTest {
    public static void main(String[] args){
        int p = Character.toCodePoint('\uD800','\uDC00');
        char[] cs = Character.toChars(p);
        System.out.println((char)p);
        int point = Character.codePointAt(cs, 1);
        System.out.println(point);
        System.out.println(Integer.toHexString(point));
    }
}
