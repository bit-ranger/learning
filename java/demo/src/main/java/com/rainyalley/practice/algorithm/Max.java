package com.rainyalley.practice.algorithm;

/**
 * Created by sllx on 15-1-12.
 */
public class Max {


    int max(int x, int y) {
        return y + (
                (x - y) & (
                        (
                                ~(
                                        (x - y) ^ (
                                                (x ^ y) & ((x - y) ^ x)
                                        )
                                )
                        ) >> 31
                )
        );
    }

}
