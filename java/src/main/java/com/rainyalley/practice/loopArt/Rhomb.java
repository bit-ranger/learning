package com.rainyalley.practice.loopArt;

/**
 * Created by sllx on 14-10-8.
 */
public class Rhomb {
    static void printRhomb(int size){
        //对称双重循环
        for (int i = -size; i <= size; i++) {
            for(int j = -size; j<= size; j++){
                int sum = Math.abs(i) + Math.abs(j);
                //控制形状
                if(sum <= size){
                    System.out.print((char)('A' + (size - sum)));
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args){
        printRhomb(6);
    }
}
