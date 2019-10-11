package com.rainyalley.practice.algorithm;

import static java.lang.System.out;

/**
 * 8皇后
 */
public class Queen {
    
    private final static int def = -1;
    
    private final static int sideLen = 8;
    
    //皇后的位置,queens索引代表行号,值代表列号
    private int[] queens;

    /**
     * 棋盘坐标表示法
     * <pre>
     *   row
     * col   0 1 2 3 ...
     *    0
     *    1
     *    2
     *    3
     * </pre>
     * */
    Queen(){
        queens = new int[sideLen];
        for (int i = 0; i < queens.length; i++) {
            queens[i] = def;
        }
    }

    /**
     * 判断是否存在皇后
     * @param row
     * @param col
     * @return true 如果不存在皇后; false 如果存在皇后
     */
    boolean isSafe(int row, int col){
        //该行有皇后,不安全
        if(queens[row] != def){
            return false;
        }
        for (int ri = 0; ri < queens.length; ri++) {
            //查看每个皇后的列号
            int ci = queens[ri];
            //该列有皇后,不安全
            if(ci == col){
                return false;
            }
            if(ci != def){
                // "\" 型斜线存在皇后
                if(ci - ri == col - row){
                    return false;
                }
                // "/" 型斜线存在皇后
                if(ci + ri == col + row){
                    return false;
                }
            }
        }
        return true;
    }
    
    void put(int row){
        for (int c = 0; c < sideLen; c++) {
            if (isSafe(row, c)){
                queens[row] = c;
                if(row == sideLen -1){
                    print();
                    queens[row] = def;
                    return;
                }
                put(row + 1);
                queens[row] = def;
            }
        }
    }

    void print()
    {
        for (int row = 0; row < sideLen; row++) {
            for (int col = 0; col < sideLen; col++) {
                int qc = queens[row];
                if(col == qc){
                    out.print("# ");
                }else {
                    out.print("* ");
                }
            }
            out.println();
        }

        out.println("=====================================");
    }

    public static void main(String[] args) {
        new Queen().put(0);
    }
    
}
