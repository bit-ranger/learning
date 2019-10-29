package com.rainyalley.practice.algorithm;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * 递归
 */
public class Recursion {

    public static int sum_recu(int a, int b) {
        if (a > b) {
            return 0;
        }
        return a + sum_recu(a + 1, b);
    }

    public static int sum_iter(int a, int b) {
        return iter(a, 0, b);
    }

    private static int iter(int point, int product, int limit) {
        if (point > limit) {
            return product;
        }
        return iter(point + 1, product + point, limit);
    }


    public static void main(String[] args) {
        //累加奇数
        System.out.println(new Accumulate(
                (a, b) -> a + b,
                x -> x,
                x -> x + 2,
                0).accumulate(1, 100));
        //累加偶数
        System.out.println(new Accumulate(
                (a, b) -> a + b,
                x -> x,
                x -> x + 2,
                0).accumulate(2, 100));
        //平方和累加
        System.out.println(new Accumulate(
                (a, b) -> a + b,
                x -> x * x,
                x -> x + 1,
                0).accumulate(1, 10));
        //阶乘
        System.out.println(new Accumulate(
                (a, b) -> a * b,
                x -> x,
                x -> x + 1,
                1).accumulate(1, 10));
    }

    static class Accumulate {

        BinaryOperator<Integer> operator;
        UnaryOperator<Integer> current, next;
        Integer defaultValue;

        Accumulate(BinaryOperator<Integer> operator,
                   UnaryOperator<Integer> current,
                   UnaryOperator<Integer> next,
                   Integer defaultValue) {
            this.operator = operator;
            this.current = current;
            this.next = next;
            this.defaultValue = defaultValue;
        }

        public Integer accumulate(Integer begin, Integer end) {
            return iter(begin, defaultValue, end);
        }

        private Integer iter(Integer point, Integer product, Integer end) {
            if (point > end) {
                return product;
            }
            return iter(next.apply(point), operator.apply(product, current.apply(point)), end);
        }
    }
}


