package com.rainyalley.practice.loopArt;

/**
 * Created by sllx on 14-10-8.
 */
public class Prime {
    static boolean isPrime(int n) {
        int i, s = (int) (Math.sqrt((double) n) + 0.01), step = 4;
        if (n <= 3) return true;
        if (n % 2 == 0) return false;
        if (n % 3 == 0) return false;
        for (i = 5; i < s; i += step) {
            if (n % i == 0) {
                return false;
            }
            //step交替变为2与4
            step ^= 6;
        }
        return true;
    }

    public static void main(String[] args) {
        for (int n = 2; n < 100; n++) {
            if (isPrime(n)) {
                System.out.println(n);
            }
        }
    }
}
