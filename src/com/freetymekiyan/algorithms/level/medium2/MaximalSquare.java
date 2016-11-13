package com.freetymekiyan.algorithms.level.medium;

/**
 * Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.
 * <p>
 * For example, given the following matrix:
 * <p>
 * 1 0 1 0 0
 * 1 0 1 1 1
 * 1 1 1 1 1
 * 1 0 0 1 0
 * Return 4.
 * <p>
 * Tags: Dynamic Programming
 * Similar: Problems (H) Maximal Rectangle
 */
public class MaximalSquare {

    /**
     * DP, 2D matrix.
     * The recurrence relation here is not easy to see.
     * Use largest square's edge length formed including current grid.
     * If the current grid is an 1,the edge length of current grid is:
     *      the minimum(its top, top-left, and left) + 1.
     * Otherwise, if it is 0, the edge length is also 0.
     */
    public int maximalSquare(char[][] matrix) {
        if (matrix == null) {
            return 0;
        }
        int r = matrix.length;
        int c = r == 0 ? 0 : matrix[0].length;
        int[][] dp = new int[r + 1][c + 1];
        int maxLen = 0;
        for (int i = 1; i <= r; i++) {
            for (int j = 1; j <= c; j++) {
                if (matrix[i - 1][j - 1] == '1') {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    maxLen = Math.max(maxLen, dp[i][j]);
                }
            }
        }
        return maxLen * maxLen; // Return AREA here
    }

    /**
     * DP, 1D matrix.
     * Reduce space usage to 1D matrix and an integer.
     */
    public int maximalSquareB(char[][] matrix) {
        if (matrix == null) {
            return 0;
        }
        int r = matrix.length;
        int c = r == 0 ? 0 : matrix[0].length;
        int[] dp = new int[c + 1]; // Only need one row
        int prev = 0; // Store dp[i-1][j-1]
        int maxLen = 0;
        for (int i = 1; i <= r; i++) {
            for (int j = 1; j <= c; j++) {
                int temp = dp[j]; // Store last dp[j] before update
                if (matrix[i - 1][j - 1] == '1') {
                    dp[j] = Math.min(Math.min(dp[j - 1], dp[j]), prev) + 1;
                    maxLen = Math.max(maxLen, dp[j]);
                } else {
                    dp[j] = 0;
                }
                prev = temp; // dp[j] before udpate is the dp[i-1][j-1] for the next loop
            }
        }
        return maxLen * maxLen; // Return the AREA here
    }

    /**
     * 复杂度
     * 时间 O(MN) 空间 O(MN)
     *
     * 思路
     * 当我们判断以某个点为正方形右下角时最大的正方形时，那它的上方，左方和左上方三个点也一定是某个正方形的右下角，
     * 否则该点为右下角的正方形最大就是它自己了。这是定性的判断，那具体的最大正方形边长呢？我们知道，该点为右下角的
     * 正方形的最大边长，最多比它的上方，左方和左上方为右下角的正方形的边长多1，最好的情况是是它的上方，左方和左上方
     * 为右下角的正方形的大小都一样的，这样加上该点就可以构成一个更大的正方形。
     * 但如果它的上方，左方和左上方为右下角的正方形的大小不一样，合起来就会缺了某个角落，这时候只能取那三个正方形中
     * 最小的正方形的边长加1了。
     *
     * 假设dpi表示以i,j为右下角的正方形的最大边长，则有
     *
     * dp[i][j] = min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) + 1
     *
     * 当然，如果这个点在原矩阵中本身就是0的话，那dpi肯定就是0了。
     *
     * @param matrix
     * @return
     */
    public int maximalSquare3(char[][] matrix) {
        if(matrix.length == 0) return 0;
        int m = matrix.length, n = matrix[0].length;
        int max = 0;
        int[][] dp = new int[m][n];

        // 第一列赋值
        for(int i = 0; i < m; i++){
            dp[i][0] = matrix[i][0] - '0';
            max = Math.max(max, dp[i][0]);
        }

        // 第一行赋值
        for(int i = 0; i < n; i++){
            dp[0][i] = matrix[0][i] - '0';
            max = Math.max(max, dp[0][i]);
        }

        // 递推
        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
                dp[i][j] = matrix[i][j] == '1' ? Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1])) + 1 : 0;
                max = Math.max(max, dp[i][j]);
            }
        }
        return max * max;
    }
}
