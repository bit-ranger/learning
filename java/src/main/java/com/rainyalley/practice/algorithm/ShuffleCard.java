package com.rainyalley.practice.algorithm;

/**
 * 洗牌，若采用产生随机数的方法，需要考虑随机数是否已存在的问题
 * 该实现方法比较特别
 */
public class ShuffleCard {
    public static void main(String args[]) {
        final int N = 52;
        int[] poker = new int[N + 1];

        // 初始化陣列,阵列中数字有序
        for(int i = 1; i <= N; i++)
            poker[i] = i;

        // 洗牌，顺序访问阵列
        for(int i = 1; i <= N; i++) {
            //产生1个随机数
            int j = (int) (Math.random() * N);

            if(j == 0)
                j = 1;

            //以随机数为索引，将对应位置的元素与当前访问的元素交换
            int tmp = poker[i];
            poker[i] = poker[j];
            poker[j] = tmp;
        }

        for(int i = 1; i <= N; i++) {
            // 判斷花色
            switch((poker[i]-1) / 13) {
                case 0:
                    System.out.print("桃"); break;
                case 1:
                    System.out.print("心"); break;
                case 2:
                    System.out.print("磚"); break;
                case 3:
                    System.out.print("梅"); break;
            }

            // 撲克牌數字
            int remain = poker[i] % 13;
            switch(remain) {
                case 0:
                    System.out.print("K "); break;
                case 12:
                    System.out.print("Q "); break;
                case 11:
                    System.out.print("J "); break;
                default:
                    System.out.print(remain + " "); break;
            }

            if(i % 13 == 0)
                System.out.println("");
        }
    }
}
