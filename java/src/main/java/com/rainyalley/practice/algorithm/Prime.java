package com.rainyalley.practice.algorithm;

import java.util.ArrayList;

/**
 * Eratosthenes 厄拉多塞
 * 筛选求质数
 */
public class Prime {
    
    public static int[] findPrimes(int max) {
        int[] prime = new int[max+1];
        ArrayList list = new ArrayList();

        //假设所有数都是质数
        for(int i = 2; i <= max; i++){
            prime[i] = 1;
        }

        //检查所有数
        for(int i = 2; i*i <= max; i++) { // 這邊可以改進
            //如果当前数是质数
            if(prime[i] == 1) {
                //排除掉所有能整除当前数的
                for(int j = 2*i; j <= max; j++) {
                    if(j % i == 0)
                        prime[j] = 0;
                }
            }
        }

        for(int i = 2; i < max; i++) {
            if(prime[i] == 1) {
                list.add(new Integer(i));
            }
        }

        int[] p = new int[list.size()];
        Object[] objs = list.toArray();
        for(int i = 0; i < p.length; i++) {
            p[i] = ((Integer) objs[i]).intValue();
        }

        return p;
    }

    public static void main(String[] args) {
        int[] prime = Prime.findPrimes(1000);

        for(int i = 0; i < prime.length; i++) {
            System.out.print(prime[i] + " ");
        }

        System.out.println();
    }
}
