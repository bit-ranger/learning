package com.rainyalley.practice.algorithm;

/**
 * 走迷宫,简单寻路
 */
public class Maze {

    private int startI, startJ;  // 入口
    private int endI, endJ;  // 出口
    private boolean success = false;

    private final static int maze[][] =
           {{2, 2, 2, 2, 2, 2, 2, 2, 2},
            {2, 0, 0, 0, 0, 0, 0, 0, 2},
            {2, 0, 2, 2, 0, 2, 2, 0, 2},
            {2, 0, 2, 0, 0, 2, 0, 0, 2},
            {2, 0, 2, 0, 2, 0, 2, 0, 2},
            {2, 0, 0, 0, 0, 0, 2, 0, 2},
            {2, 2, 0, 2, 2, 0, 2, 2, 2},
            {2, 0, 0, 0, 0, 0, 0, 0, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 2}};


    public void setStart(int i, int j) {
        this.startI = i;
        this.startJ = j;
    }

    public void setEnd(int i, int j) {
        this.endI = i;
        this.endJ = j;
    }

    public void go(){
        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 2) {
                    System.out.print("█");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }

        if(!this.visit(maze, startI, startJ)) {
            System.out.println("\n沒有找到出口！");
        } else {
            System.out.println("\n找到出口！");
            for(int i = 0; i < maze.length; i++) {
                for(int j = 0; j < maze[0].length; j++) {
                    if(maze[i][j] == 2)
                        System.out.print("█");
                    else if(maze[i][j] == 1)
                        System.out.print("◇");
                    else
                        System.out.print("  ");
                }
                System.out.println();
            }
        }
    }


    protected boolean visit(int[][] maze, int i, int j) {
        maze[i][j] = 1;

        if(i == endI && j == endJ)
            success = true;

        if(!success && maze[i][j+1] == 0)
            visit(maze, i, j+1);
        if(!success && maze[i+1][j] == 0)
            visit(maze, i+1, j);
        if(!success && maze[i][j-1] == 0)
            visit(maze, i, j-1);
        if(!success && maze[i-1][j] == 0)
            visit(maze, i-1, j);

        if(!success)
            maze[i][j] = 0;

        return success;
    }


    /** 找到所有路径,与Maze不同点在于找到一条路径后,返回上一步继续寻路 **/
    private class MazeMulti extends  Maze{

        public void go(){
            for(int i = 0; i < maze.length; i++) {
                for(int j = 0; j < maze[0].length; j++) {
                    if (maze[i][j] == 2) {
                        System.out.print("█");
                    } else {
                        System.out.print("  ");
                    }
                }
                System.out.println();
            }
            //此处需要super,MazeMulti本身并不存在这两个域,值始终为0值;
            //切忌在内部类中继承外部类,非常容易出错
            this.visitMulti(maze, super.startI, super.startJ);
        }

        private void visitMulti(int[][] maze, int i, int j) {
            maze[i][j] = 1;
            boolean isExit = i == super.endI && j == super.endJ;

            if(isExit){
                System.out.println("\n找到出口！");
                for(int m = 0; m < maze.length; m++) {
                    for(int n = 0; n < maze[0].length; n++) {
                        if(maze[m][n] == 2)
                            System.out.print("█");
                        else if(maze[m][n] == 1)
                            System.out.print("◇");
                        else
                            System.out.print("  ");
                    }
                    System.out.println();
                }
            }

            if(maze[i][j+1] == 0)
                visitMulti(maze, i, j+1);
            if(maze[i+1][j] == 0)
                visitMulti(maze, i+1, j);
            if(maze[i][j-1] == 0)
                visitMulti(maze, i, j-1);
            if(maze[i-1][j] == 0)
                visitMulti(maze, i-1, j);

            maze[i][j] = 0;
        }

    }



    public static void main(String[] args) {
        //Maze maze = new Maze();
        Maze maze = new Maze().new MazeMulti();
        maze.setStart(1, 1);
        maze.setEnd(7, 7);
        maze.go();
    }

}
