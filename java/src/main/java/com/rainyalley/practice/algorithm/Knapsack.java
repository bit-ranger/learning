package com.rainyalley.practice.algorithm;

/**
 * 背包问题
 * 求最优解
 * 动态规划
 */
class Fruit {
    private String name;
    private int size;
    private int price;

    public Fruit(String name, int size, int price) {
        this.name = name;
        this.size = size;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getSize() {
        return size;
    }
}

public class Knapsack {
    public static void main(String[] args) {
        final int MAX = 8;
        final int MIN = 1;
        //存放最后1个放入背包的水果编号，索引为重量
        int[] item = new int[MAX+1];
        //存放背包中的水果的价值，索引为重量
        int[] value = new int[MAX+1];

        Fruit fruits[] = {
                new Fruit("李子", 4, 4500),
                new Fruit("蘋果", 5, 5700),
                new Fruit("橘子", 2, 2250),
                new Fruit("草莓", 1, 1100),
                new Fruit("甜瓜", 6, 6700)};

        //迭代所有水果
        for(int i = 0; i < fruits.length; i++) {
            //取一个水果，迭代所有可能的重量
            for(int s = fruits[i].getSize(); s <= MAX; s++) {
                //当前可能的重量中的剩余重量
                int p = s - fruits[i].getSize();
                //当前总价值
                int newValue = value[p] + fruits[i].getPrice();
                //重量为s时，最优解一定是fruits[i]
                if(newValue > value[s]) {// 找到階段最佳解
                    value[s] = newValue;
                    item[s] = i;
                }
            }
        }

        System.out.println("物品\t價格");
        for(int i = MAX;
            i >= MIN;
            i = i - fruits[item[i]].getSize()) {
            System.out.println(fruits[item[i]].getName()+
                    "\t" + fruits[item[i]].getPrice());
        }

        System.out.println("合計\t" + value[MAX]);
    }
}
