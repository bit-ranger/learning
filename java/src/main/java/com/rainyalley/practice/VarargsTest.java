package com.rainyalley.practice;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sllx on 3/19/15.
 */
public class VarargsTest {

    /**
     * <a>adf</a>
     * {@code <a>adf</a>}
     * {@literal <a>adf</a>}
     * @param args
     */
    public static void main(String[] args){
        int[] digits = {3, 1, 4};
        List<int[]> lia = Arrays.asList(digits);
        List<Integer> li = Arrays.asList(3, 1, 4);
        System.out.println(lia);
        System.out.println(li);
        System.out.println(Arrays.toString(digits));
    }
}
