package com.rainyalley.practice.algorithm;

/**
 * 8枚银币
 * 决策树
 */
public class Coins {
    private int[] coins;

    public Coins() {
        coins = new int[8];
        for(int i = 0; i < 8; i++){
            coins[i] = 10;
        }
    }

    /**
     * 设置假币重量
     * @param weight
     */
    public void setFake(int weight) {
        coins[(int) (Math.random() * 7)] = weight;
    }

    /**
     * 决策树
     *
     */
    public void fake() {
        if(coins[0]+coins[1]+coins[2] == coins[3]+coins[4]+coins[5]) {
            //假币必为6或者7
            if(coins[6] > coins[7]) {
                compare(6, 7, 0);
            } else {
                compare(7, 6, 0);
            }
        }
        else if(coins[0]+coins[1]+coins[2] > coins[3]+coins[4]+coins[5]) {
            //假币必在其中一组
            if(coins[0]+coins[3] == coins[1]+coins[4]) {
                compare(2, 5, 0);
            } else if(coins[0]+coins[3] > coins[1]+coins[4]) {
                compare(0, 4, 1);
            }
            if(coins[0]+coins[3] < coins[1]+coins[4]) {
                compare(1, 3, 0);
            }
        }
        else if(coins[0]+coins[1]+coins[2] <
                coins[3]+coins[4]+coins[5]) {
            if(coins[0]+coins[3] == coins[1]+coins[4])
                compare(5, 2, 0);
            else if(coins[0]+coins[3] > coins[1]+coins[4])
                compare(3, 1, 0);
            if(coins[0]+coins[3] < coins[1]+coins[4])
                compare(4, 0, 1);
        }
    }

    protected void compare(int i, int j, int k) {
        if(coins[i] > coins[k])
            System.out.print("\n假幣 " + (i+1) + " 較重");
        else
            System.out.print("\n假幣 " + (j+1) + " 較輕");
    }

    public static void main(String[] args) {

        Coins eightCoins = new Coins();
        eightCoins.setFake(5);
        eightCoins.fake();
    }
}