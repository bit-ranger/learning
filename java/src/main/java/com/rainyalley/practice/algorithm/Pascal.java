package com.rainyalley.practice.algorithm;

import javax.swing.*;
import java.awt.*;

/**
 * 杨辉三角
 */
public class Pascal extends JFrame{

    private final int maxRow;

    Pascal(int maxRow){
        setBackground(Color.white);
        setTitle("杨辉三角");
        setSize(520, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.maxRow = maxRow;
    }

    /**
     * 获取值
     * @param row 行号 从0开始
     * @param col 列号 从0开始
     * @return
     */
    private int value(int row,int col){
        int v = 1;
        for (int i = 1; i < col; i++) {
            v = v * (row - i + 1) / i;
        }
        return v;
    }

    @Override
    public void paint(Graphics g) {
        int r, c;
        for(r = 0; r <= maxRow; r++) {
            for(c = 0; c <= r; c++){
                g.drawString(String.valueOf(value(r, c)), (maxRow - r) * 20 + c * 40, r * 20 + 50);
            }
        }
    }

    public static void main(String[] args) {
        new Pascal(12);
    }
}
