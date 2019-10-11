package com.rainyalley.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * 值传递
 */
public class TransmitTest {
    public static void main(String[] args){
        //1
        String s = "a";
        change(s);
        System.out.println(s);

        //2
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        setNull(list);
        if(list.size()>0){
            System.out.println(list.get(0));
        }
    }

    static void change(String s){
        s = "b";
    }

    private static void setNull(List<?> list){
        list = null;
    }
}
