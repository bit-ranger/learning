package com.rainyalley.practice.algorithm;

/**
 * 大数运算
 */
public class BigNumber {
    public static int[] add(int[] a, int[] b) {
        int carry = 0;
        int[] c = new int[a.length];

        for(int i = a.length - 1; i >= 0; i--) {
            c[i] = a[i] + b[i] + carry;
            if(c[i] < 10000)
                carry = 0;
            else { // 進位
                c[i] = c[i] - 10000;
                carry = 1;
            }
        }

        return c;
    }

    public static int[] sub(int[] a, int[] b) {
        int borrow = 0;
        int[] c = new int[a.length];

        for(int i = a.length - 1; i >= 0; i--) {
            c[i] = a[i] - b[i] - borrow;
            if(c[i] >= 0)
                borrow = 0;
            else { // 借位
                c[i] = c[i] + 10000;
                borrow = 1;
            }
        }

        return c;
    }

    public static int[] mul(int[] a, int b) { // b 為乘數
        int carry = 0;
        int[] c = new int[a.length];

        for(int i = a.length - 1; i >=0; i--) {
            int tmp = a[i] * b + carry;
            c[i] = tmp % 10000;
            carry = tmp / 10000;
        }

        return c;
    }

    public static int[] div(int[] a, int b) {  // b 為除數
        int remain = 0;
        int[] c = new int[a.length];

        for(int i = 0; i < a.length; i++) {
            int tmp = a[i] + remain;
            c[i] = tmp / b;
            remain = (tmp % b) * 10000;
        }

        return c;
    }

    public static void main(String[] args) {
        int[] a = {1234, 5678, 9910, 1923, 1124};
        int[] b = {1234, 5678, 9910, 1923, 1124};
        int[] c = BigNumber.add(a, b);

        for(int i = 0; i < c.length; i++) {
            System.out.print(c[i]);
        }
        System.out.println();
    }
}
