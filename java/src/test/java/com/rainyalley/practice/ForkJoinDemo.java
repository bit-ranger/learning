package com.rainyalley.practice;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.stream.IntStream;

public class ForkJoinDemo {



    public static class SelectMaxProblem {
        private  int[] numbers = null;
        private  int start = 0;
        private  int end = 0;
        public  int size = 0;

        public SelectMaxProblem(int[] numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
            this.size = end - start + 1;
        }

        public int solveSequentially() {
            int max = Integer.MIN_VALUE;
            for (int i=start; i<=end; i++) {
                int n = numbers[i];
                if (n > max)
                    max = n;
            }
            return max;
        }

        public SelectMaxProblem subproblem(int subStart, int subEnd) {
            return new SelectMaxProblem(numbers, start + subStart, start + subEnd);
        }
    }

    public static class MaxWithFJ extends RecursiveAction {
        private final int threshold;
        private final SelectMaxProblem problem;
        public int result;

        public MaxWithFJ(SelectMaxProblem problem, int threshold) {
            this.problem = problem;
            this.threshold = threshold;
        }

        protected void compute() {
            if (problem.size <= threshold)
                result = problem.solveSequentially();
            else {
                int midpoint = problem.size / 2;
                MaxWithFJ left = new MaxWithFJ(problem.subproblem(0, midpoint), threshold);
                MaxWithFJ right = new MaxWithFJ(problem.subproblem(midpoint + 1, problem.size - 1), threshold);
                left.fork();
                right.fork();
                left.join();
                right.join();

                result = Math.max(left.result, right.result);
            }
        }
    }



    @Test
    public void main() {
        int[] nums = IntStream.range(1, 60).toArray();
        SelectMaxProblem problem = new SelectMaxProblem(nums,0, nums.length - 1);
        int threshold = 2;
        int nThreads = 2;
        MaxWithFJ mfj = new MaxWithFJ(problem, threshold);
        ForkJoinPool fjPool = new ForkJoinPool(nThreads);

        fjPool.invoke(mfj);
        int result = mfj.result;

        System.out.println(result);
    }
}
