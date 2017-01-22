package com.freetymekiyan.algorithms.Other;

import java.util.*;

/**
 * Find the longest increasing(increasing means one step) sequence in an 
 * integer matrix in 4 directions (up down left right), return the sequence
 * 
 * For Example:
 * |1 2 3 4|
 * |8 7 6 5|
 * 
 * Output: 
 * [1, 2, 3, 4, 5, 6, 7, 8]
 * 
 * Tags: DP, DFS
 *
 * classic DFS + memorialization
 */
public class LongestIncreasingSequenceInMat {
    public static void main(String[] args) {
        LongestIncreasingSequenceInMat l = new LongestIncreasingSequenceInMat();
        int[][] mat = {
            {1, 2, 3, 4}, 
            {8, 7, 6, 5},
            {9, 10, 11, 12}
        };
        System.out.println(Arrays.toString(l.longest(mat)));
    }

    protected static final int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    int[][] cache;
    
    /**
     * DP
     * d[i][j] = max{d[i+1][j], d[i-1][j], d[i][j+1], d[i][j-1]} + 1
     */
    public int[] longest(int[][] mat) {
        int[] res = new int[]{};
        if (mat == null || mat.length == 0 || mat[0].length == 0) return res;
        int m = mat.length;
        int n = mat[0].length;
        cache = new int[m][n];
        int maxStart = 0;
        int maxPath = 0;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                int path = dfs(i, j, mat);
                if (path > maxPath) {
                    maxStart = mat[i][j];
                    maxPath = path;
                }
            }
        res = new int[maxPath];
        // TODO recover the getPath, from maxStart with maxPath
        for (int i = 0; i < maxPath; i++) res[i] = maxStart + i;
        return res;
    }
    
    private int dfs(int i, int j, int[][] mat) {
        if (cache[i][j] != 0) return cache[i][j]; // has cache
        int m = mat.length;
        int n = mat[0].length;
        for (int[] d : DIRS) { // search 4 directions
            int ni = i + d[0]; // get next coordinates
            int nj = j + d[1];
            if (ni >= 0 && ni < m && nj >= 0 && nj < n 
                    && mat[ni][nj] == mat[i][j] + 1) { // increasing means one step up
                // if dfs(ni, nj) is bigger, update cache[i][j]
                cache[i][j] = Math.max(cache[i][j], dfs(ni, nj, mat));
            }
        }
        return ++cache[i][j]; // add cache[i][j] by 1 and return!
    }

    /**
     * Another related problem: Given an integer matrix, find the length of the longest increasing path.
     From each cell, you can either move to four directions: left, right, up or down.
     You may NOT move diagonally or move outside of the boundary (i.e. wrap-around is not allowed).
     * @param matrix
     * @return
     */
    public int longestIncreasingPath(int[][] matrix) {
        if(matrix.length == 0) return 0;
        int m = matrix.length, n = matrix[0].length;
        int[][] cache = new int[m][n];
        int max = 1;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                int len = dfs(matrix, i, j, m, n, cache);
                max = Math.max(max, len);
            }
        }
        return max;
    }

    public int dfs(int[][] matrix, int i, int j, int m, int n, int[][] cache) {
        if(cache[i][j] != 0) return cache[i][j];
        int max = 1;
        for(int[] dir: DIRS) {
            int x = i + dir[0], y = j + dir[1];
            if(x < 0 || x >= m || y < 0 || y >= n || matrix[x][y] <= matrix[i][j]) continue;
            int len = 1 + dfs(matrix, x, y, m, n, cache);
            max = Math.max(max, len);
        }
        cache[i][j] = max;
        return max;
    }
}
