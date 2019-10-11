package com.rainyalley.practice.algorithm;

import java.util.ArrayList;

/**
 *  因式分解
 */
public class Factor {

public static int[] factor(int num) {

        int[] pNum = Prime.findPrimes(num);

        ArrayList list = new ArrayList();

        for(int i = 0; pNum[i] * pNum[i] <= num;) {
            if(num % pNum[i] == 0) {
                list.add(new Integer(pNum[i]));
                num /= pNum[i];
            }
            else
                i++;
        }

        list.add(new Integer(num));

        int[] f = new int[list.size()];
        Object[] objs = list.toArray();
        for(int i = 0; i < f.length; i++) {
            f[i] = ((Integer) objs[i]).intValue();
        }

        return f;
    }


    public static void main(String[] args) {
        int[] f = Factor.factor(100);
        for(int i = 0; i < f.length; i++) {
            System.out.print(f[i] + " ");
        }
        System.out.println();
    }

}
