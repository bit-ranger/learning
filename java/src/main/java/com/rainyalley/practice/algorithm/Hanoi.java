package com.rainyalley.practice.algorithm;

/**
 * 河内塔
 */
public class Hanoi {

    private void move(int n, char a, char b, char c){
        if(n == 1){
            System.out.printf("盘%d由%s移动到%s\n",n,a,c);
        } else{
            move(n-1,a,c,b);
            System.out.printf("盘%d由%s移动到%s\n",n,a,c);
            move(n-1,b,a,c);
        }
    }

    public static void main(String[] args) {
        new Hanoi().move(5, 'A','B','C');
    }
}
