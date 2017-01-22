package com.freetymekiyan.algorithms.level.Medium;

/**
 * Given a m x n grid filled with non-negative numbers, find a getPath from top
 * left to bottom right which minimizes the sum of all numbers along its getPath.
 * 
 * Note: You can only move either down or right at any point in time.
 * 
 * Tags: Array, DP
 */
class MinPathSum {
    
    public static void main(String[] args) {
        // int[][] grid = new int[][]{{0}};
        int[][] grid = new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        System.out.println(minPathSum(grid));
    }
    
    /**
     * 1D DP. bottom-up
     * row by row, left to right, use an array to store values
     */
    public static int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        int m = grid.length;
        int n = grid[0].length;
        int[] rowSum = new int[n];
            
        rowSum[0] = grid[0][0];
        for (int col = 1; col < n; col++)
            rowSum[col] = rowSum[col - 1] + grid[0][col];  // first row
        for (int row = 1; row < m; row++) {
            rowSum[0] += grid[row][0]; // first column
            for (int col = 1; col < n; col++) {
                rowSum[col] = Math.min(rowSum[col - 1], rowSum[col]) +
                        grid[row][col]; // min of previous row and previous column + grid
            }
        }
        return rowSum[n - 1];
    }
    
    private static void printMatrix(int[][] grid){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("-----------");
    }

    // 2D DP, not as efficient
    public int minPathSum2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        if (m == 0 || n == 0)
            return 0;

        int[][] dp = new int[m][n];

        dp[0][0] = grid[0][0];

        //a row
        for (int i = 1; i < n; i++)
            dp[0][i] = dp[0][i - 1] + grid[0][i];

        //a column
        for (int j = 1; j < m; j++)
            dp[j][0] = dp[j - 1][0] + grid[j][0];

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (dp[i - 1][j] < dp[i][j - 1])
                    dp[i][j] = dp[i - 1][j] + grid[i][j];
                else
                    dp[i][j] = dp[i][j - 1] + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }

    // DFS, brute force
    public int minPathSum3(int[][] grid) {
        return dfs(0, 0, grid);
    }

    public int dfs(int i, int j, int[][] grid) {
        if (i == grid.length - 1 && j == grid[0].length - 1)
            return grid[i][j];

        if (i < grid.length - 1 && j < grid[0].length - 1) {
            int r1 = grid[i][j] + dfs(i + 1, j, grid);
            int r2 = grid[i][j] + dfs(i, j + 1, grid);
            return Math.min(r1, r2);
        }

        if (i < grid.length - 1) return grid[i][j] + dfs(i + 1, j, grid);
        if (j < grid[0].length - 1) return grid[i][j] + dfs(i, j + 1, grid);

        return 0;
    }
}