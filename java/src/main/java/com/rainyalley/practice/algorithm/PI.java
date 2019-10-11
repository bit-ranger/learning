package com.rainyalley.practice.algorithm;

/**
 * 求PI
 * 蒙地卡罗法
 */
public class PI {
    public static void main(String[] args) {
        final int N = 50000;//假设投射50000个飞镖
        
        int sum = 0;//落在扇形中的数量

        for(int i = 1; i < N; i++) {
            double x = Math.random();
            double y = Math.random();
            if((x * x + y * y) < 1){
                sum++;
            }
        }
        
        //半径为1的1/4个圆(扇形)，放在边长为1的正方形中
        //PI/4 = sum/N
        System.out.println("PI = " + (double) 4 * sum / N);
    }
}
