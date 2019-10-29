package com.rainyalley.practice.algorithm;

/**
 * 斐波那契数列
 */
public class Fibonacci {

    private int[] src;

    public static void main(String[] args) {
        Fibonacci fib = new Fibonacci();
        int n = fib.fibonacci(20);
        System.out.println(n);
        for (int i : fib.src) {
            System.out.println(i);
        }
    }

    public int fibonacci(int n) {
        if (src == null) {
            src = new int[n + 1];
        }
        if (n == 0 || n == 1) {
            src[n] = n;
            return n;
        }
        src[n] = fibonacci(n - 1) + fibonacci(n - 2);
        return src[n];
    }

    public int fibonacci2(int n) {
        if (src == null) {
            src = new int[n + 1];
        }
        src[0] = 0;
        src[1] = 1;
        for (int i = 2; i < src.length; i++) {
            src[i] = src[i - 1] + src[i - 2];
        }
        return src[n];
    }
}
