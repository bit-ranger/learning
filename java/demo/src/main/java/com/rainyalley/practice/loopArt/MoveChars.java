package com.rainyalley.practice.loopArt;

/**
 * Created by sllx on 14-10-13.
 */
public class MoveChars {

    /**
     * @param len 向左移动的长度
     */
    private static void moveChars(int len) {
        String str = "abcdefg";
        char[] sa = str.toCharArray();
        char s;
        if (len > 0) {
            //拆为两部分，分别反转，然后再整体反转，表现为左移
            for (int i = 0; i < len >> 1; i++) {
                s = sa[i];
                sa[i] = sa[len - i - 1];
                sa[len - i - 1] = s;
            }
            for (int i = 0; i < (sa.length - len) >> 1; i++) {
                s = sa[len + i];
                sa[len + i] = sa[sa.length - i - 1];
                sa[sa.length - i - 1] = s;
            }
            for (int i = 0; i < sa.length >> 1; i++) {
                s = sa[i];
                sa[i] = sa[sa.length - i - 1];
                sa[sa.length - i - 1] = s;
            }
        } else {
            //拆为两部分，整体反转，然后再分别反转，表现为右移
            len = Math.abs(len);
            for (int i = 0; i < sa.length >> 1; i++) {
                s = sa[i];
                sa[i] = sa[sa.length - i - 1];
                sa[sa.length - i - 1] = s;
            }
            for (int i = 0; i < len >> 1; i++) {
                s = sa[i];
                sa[i] = sa[len - i - 1];
                sa[len - i - 1] = s;
            }
            for (int i = 0; i < (sa.length - len) >> 1; i++) {
                s = sa[len + i];
                sa[len + i] = sa[sa.length - i - 1];
                sa[sa.length - i - 1] = s;
            }
        }

        System.out.println(new String(sa));
    }

    public static void main(String[] args) {
        moveChars(-2);
    }
}
