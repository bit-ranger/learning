package com.rainyalley.practice.algorithm;

/**
 * 三色汉诺塔
 */
public class Hanoi3 {

    public static void main(String[] args) {

        hanoi3colors(12);
    }

    public static void hanoi(int disks,
                             char source, char temp, char target) {
        if (disks == 1) {
            System.out.println("move disk from "
                    + source + " to " + target);
            System.out.println("move disk from "
                    + source + " to " + target);
            System.out.println("move disk from "
                    + source + " to " + target);
        } else {
            hanoi(disks-1, source, target, temp);
            hanoi(1, source, temp, target);
            hanoi(disks-1, temp, source, target);
        }
    }

    public static void hanoi3colors(int disks) {
        char source = 'A';
        char temp   = 'B';
        char target = 'C';
        if (disks == 3) {
            System.out.println("move disk from "
                    + source + " to " + temp);
            System.out.println("move disk from "
                    + source + " to " + temp);
            System.out.println("move disk from "
                    + source + " to " + target);
            System.out.println("move disk from "
                    + temp + " to " + target);
            System.out.println("move disk from "
                    + temp + " to " + source);
            System.out.println("move disk from "
                    + target + " to " + temp);
        } else {
            hanoi(disks/3-1, source, temp, target);
            System.out.println("move disk from "
                    + source + " to " + temp);
            System.out.println("move disk from "
                    + source + " to " + temp);
            System.out.println("move disk from "
                    + source + " to " + temp);
            hanoi(disks/3-1, target, temp, source);
            System.out.println("move disk from "
                    + temp + " to " + target);
            System.out.println("move disk from "
                    + temp + " to " + target);
            System.out.println("move disk from "
                    + temp + " to " + target);
            hanoi(disks/3-1, source, target, temp);
            System.out.println("move disk from "
                    + target + " to " + source);
            System.out.println("move disk from "
                    + target + " to " + source);
            hanoi(disks/3-1, temp, source, target);
            System.out.println("move disk from "
                    + source + " to " + temp);

            for (int i = disks / 3 - 1; i > 0; i--) {
                if (i>1) {
                    hanoi(i-1, target, source, temp);
                }
                System.out.println("move disk from "
                        + target + " to " + source);
                System.out.println("move disk from "
                        + target + " to " + source);
                if (i>1) {
                    hanoi(i-1, temp, source, target);
                }
                System.out.println("move disk from "
                        + source + " to " + temp);
            }
        }
    }
}
