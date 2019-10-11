package com.rainyalley.practice.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 生命游戏
 */
public class LifeGame {
    private boolean[][] map;
    private boolean[][] newmap;

    public LifeGame(int maxRow, int maxColumn) {
        map = new boolean[maxRow][maxColumn];
        newmap = new boolean[maxRow][maxColumn];
    }

    public void setCell(int x, int y) {
        map[x][y] = true;
    }

    public void next() {
        for(int row = 0; row < map.length; row++) {
            for(int col = 0; col < map[0].length; col++) {
                switch (neighbors(row, col)) {
                    case 0:
                    case 1:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        newmap[row][col] = false;
                        break;
                    case 2:
                        newmap[row][col] = map[row][col];
                        break;
                    case 3:
                        newmap[row][col] = true;
                        break;
                }
            }
        }

        copyMap();
    }

    public void outputMap() throws IOException {
        System.out.println("\n\nGame of life cell status");
        for(int row = 0; row < map.length; row++) {
            System.out.print("\n ");
            for(int col = 0; col < map[0].length; col++)
                if(map[row][col] == true)
                    System.out.print('#');
                else
                    System.out.print('-');
        }
    }

    private void copyMap() {
        for(int row = 0; row < map.length; row++)
            for(int col = 0; col < map[0].length; col++)
                map[row][col] = newmap[row][col];
    }

    private int neighbors(int row, int col) {
        int count = 0;

        for(int r = row-1; r <= row+1; r++)
            for(int c = col-1; c <= col+1; c++) {
                if(r < 0 || r >= map.length ||
                        c < 0 || c >= map[0].length)
                    continue;
                if(map[r][c] == true)
                    count++;
            }

        if(map[row][col] == true)
            count--;

        return count;
    }

    public static void main(String[] args)
            throws NumberFormatException, IOException {
        BufferedReader bufReader =
                new BufferedReader(
                        new InputStreamReader(System.in));

        LifeGame game = new LifeGame(10, 25);

        System.out.println("Game of life Program");
        System.out.println(
                "Enter x, y where x, y is living cell");
        System.out.println("0 <= x < 10, 0 <= y < 25");
        System.out.println("Terminate with x, y = -1, -1");

        while(true) {
            String[] strs = bufReader.readLine().split(" ");
            int row = Integer.parseInt(strs[0]);
            int col = Integer.parseInt(strs[1]);

            if(0 <= row && row < 10 && 0 <= col && row < 25)
                game.setCell(row, col);
            else if(row == -1 || col == -1) {
                break;
            }
            else {
                System.out.print(
                        "(x, y) exceeds map ranage!");
            }
        }

        while(true) {
            game.outputMap();
            game.next();

            System.out.print(
                    "\nContinue next Generation ? ");

            String ans = bufReader.readLine().toUpperCase();

            if(!ans.equals("Y"))
                break;
        }
    }
}