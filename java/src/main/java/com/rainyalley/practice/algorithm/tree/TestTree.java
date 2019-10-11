package com.rainyalley.practice.algorithm.tree;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by sllx on 7/8/15.
 */
public class TestTree {

    public static void main(String[] args){

        BinarySearchTree<Integer> bst = new BalancedBinarySearchTree<>(11);
        for (int i=100; i>0; i--) {
            int next = ThreadLocalRandom.current().nextInt(1000);
            bst.insert(next);
            //System.out.println(next);
        }

        //bst.inorderTraversal().forEach(p -> System.out.println(p));

        bst.depthDiff().forEach((k, v) -> {if(!(-1 <= v && v <= 1)){
            System.out.println(k + " : " + v);
        }
        });
    }
}
