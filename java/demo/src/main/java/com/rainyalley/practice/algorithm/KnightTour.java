package com.rainyalley.practice.algorithm;


import java.awt.*;

import static java.lang.System.out;

/**
 * 骑士周游
 */
public class KnightTour {

    // 對應騎士可走的八個方向
    private final static Point[] relatives = new Point[]{
            new Point(-2, 1),
            new Point(-1, 2),
            new Point(1, 2),
            new Point(2, 1),
            new Point(2, -1),
            new Point(1, -2),
            new Point(-1, -2),
            new Point(-2, -1)
    };

    private final static int direct = 8;
    private int sideLen;
    private int[][] board;

    public KnightTour(int sideLen) {
        this.sideLen = sideLen;
        board = new int[sideLen][sideLen];
    }

    public static void main(String[] args) {
        int sideLen = 9;
        KnightTour knight = new KnightTour(sideLen);

        if (knight.travel(4, 2)) {
            out.println("遊歷完成！");
        } else {
            out.println("遊歷失敗！");
        }

        for (int y = 0; y < sideLen; y++) {
            for (int x = 0; x < sideLen; x++) {
                out.printf("%3d ", knight.board[x][y]);
            }
            out.println();
        }
    }

    public boolean travel(int startX, int startY) {

        // 当前出口的位置集
        Point[] nexts;

        // 记录每个出口的可用出口個數
        int[] exits;

        //当前位置
        Point current = new Point(startX, startY);

        board[current.x][current.y] = 1;

        //当前步的编号
        for (int m = 2; m <= Math.pow(board.length, 2); m++) {

            //清空每个出口的可用出口数
            nexts = new Point[direct];
            exits = new int[direct];

            int count = 0;
            // 試探八個方向
            for (int k = 0; k < direct; k++) {
                int tmpX = current.x + relatives[k].x;
                int tmpY = current.y + relatives[k].y;

                // 如果這個方向可走，記錄下來
                if (accessable(tmpX, tmpY)) {
                    nexts[count] = new Point(tmpX, tmpY);
                    // 可走的方向加一個
                    count++;
                }
            }

            //可用出口数最少的出口在exits中的索引
            int minI = 0;
            if (count == 0) {
                return false;
            } else {
                // 记录每个出口的可用出口數
                for (int l = 0; l < count; l++) {
                    for (int k = 0; k < direct; k++) {
                        int tmpX = nexts[l].x + relatives[k].x;
                        int tmpY = nexts[l].y + relatives[k].y;
                        if (accessable(tmpX, tmpY)) {
                            exits[l]++;
                        }
                    }
                }

                // 從可走的方向中尋找最少出路的方向
                int tmp = exits[0];
                for (int l = 1; l < count; l++) {
                    if (exits[l] < tmp) {
                        tmp = exits[l];
                        minI = l;
                    }
                }
            }

            // 走最少出路的方向
            current = new Point(nexts[minI]);
            board[current.x][current.y] = m;
        }

        return true;
    }

    private boolean accessable(int x, int y) {
        return x >= 0 && y >= 0 && x < sideLen && y < sideLen && board[x][y] == 0;
    }
}
