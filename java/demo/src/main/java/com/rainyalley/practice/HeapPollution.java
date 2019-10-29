package com.rainyalley.practice;


import java.util.ArrayList;
import java.util.List;

/**
 * 堆污染
 */
public class HeapPollution {
    public static void main(String[] args) {
        List list = new ArrayList<Integer>();
        list.add(1);
        List<String> ls = list;
        ls.add("1");
        String s = ls.get(0);
    }
}
