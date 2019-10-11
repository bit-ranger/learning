package com.rainyalley.practice;

import java.util.PriorityQueue;
import java.util.Queue;


public class PriorityQueueTest {
    /**
     * not thread safe
     */
    static Queue<String> queue = new PriorityQueue<>();

    public static void main(String[] args){
        queue.add("4");
        queue.add("3");
        queue.add("2");
        int size = queue.size();

        queue.forEach(p -> System.out.println(p));
        for (int i = 0; i < size; i++) {
            System.out.println(queue.poll());
        }
    }
}
