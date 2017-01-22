package com.freetymekiyan.algorithms.level.Medium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Given a triangle, find the minimum path sum from top to bottom. Each step
 * you may move to adjacent numbers on the row below.
 * <p>
 * For example, given the following triangle
 * [
 * [2],
 * [3,4],
 * [6,5,7],
 * [4,1,8,3]
 * ]
 * The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).
 * <p>
 * Note:
 * Bonus point if you are able to do this using only O(n) extra space, where n
 * is the total number of rows in the triangle.
 * <p>
 * Tags: Array, DP
 */
class Triangle {

    public static void main(String[] args) {
        List<List<Integer>> input = generateInput(TestType.TEST1);
        // List<List<Integer>> input = generateInput(TestType.TEST2);
        testInput(input);
        System.out.println(minimumTotal(input));
    }

    /**
     * DP
     * Math.min(result.get(i), result.get(i + 1)) + triangle.get(curLv).get(i)
     * Pick the smaller one of next row and add it up to current level
     */
    public static int minimumTotal(List<List<Integer>> triangle) {
        int level = triangle.size() - 1;

        List<Integer> res = new ArrayList<Integer>(triangle.get(level));

        for (int i = level - 1; i >= 0; i--) { // start from second last row
            for (int j = 0; j <= i; j++) { // go through each node
                int result = Math.min(res.get(j), res.get(j + 1)) + triangle.get(i).get(j); // add the smaller one
                res.set(j, result);
            }
        }
        return res.get(0);
    }

    /**
     * 一道动态规划的经典题目。需要自底向上求解。

     递推公式是： dp[i][j] = dp[i+1][j] + dp[i+1][j+1] ，当前这个点的最小值，由他下面那一行临近的2个点的最小值与当前点的值相加得到。

     由于是三角形，且历史数据只在计算最小值时应用一次，所以无需建立二维数组，每次更新1维数组值，最后那个值里存的就是最终结果。

     * @param triangle
     * @return
     */
    public int minimumTotal2(List<List<Integer>> triangle) {
        if(triangle.size()==1)
            return triangle.get(0).get(0);

        //initial value of dp set by last row
        Integer[] dp = new Integer[triangle.size()];;
        dp = triangle.get(triangle.size() - 1).toArray(dp);

        // iterate from last second row
        for (int i = triangle.size() - 2; i >= 0; i--) {
            List<Integer> row = triangle.get(i);
            for (int j = 0; j < row.size(); j++) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + row.get(j);
            }
        }

        return dp[0];
    }

    static List<List<Integer>> generateInput(TestType t) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (t == TestType.TEST1) {
            // [[-1],[2,3],[1,-1,-3]]
            List<Integer> row1 = new ArrayList<Integer>();
            row1.add(-1);
            result.add(row1);

            List<Integer> row2 = new ArrayList<Integer>();
            row2.add(3);
            row2.add(2);
            result.add(row2);

            List<Integer> row3 = new ArrayList<Integer>();
            row3.add(-3);
            row3.add(1);
            row3.add(-1);
            result.add(row3);
        } else if (t == TestType.TEST2) {
            // [[2],[3,4],[6,5,7],[4,1,8,3]]
            List<Integer> row1 = new ArrayList<Integer>();
            row1.add(2);
            result.add(row1);

            List<Integer> row2 = new ArrayList<Integer>();
            row2.add(3);
            row2.add(4);
            result.add(row2);

            List<Integer> row3 = new ArrayList<Integer>();
            row3.add(6);
            row3.add(5);
            row3.add(7);
            result.add(row3);

            List<Integer> row4 = new ArrayList<Integer>();
            row4.add(4);
            row4.add(1);
            row4.add(8);
            row4.add(3);
            result.add(row4);
        }
        return result;
    }

    static void testInput(List<List<Integer>> input) {
        for (List<Integer> list : input) {
            System.out.println(list.toString());
        }
        return;
    }

    enum TestType {
        TEST1, TEST2;
    }
}