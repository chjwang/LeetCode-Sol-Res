package com.freetymekiyan.algorithms.Other;

import java.util.HashMap;
import java.util.Map;

/**
 * Given two sparse matrices A and B, return the result of AB.
 *
 * You may assume that A's column number is equal to B's row number.
 *
 * Example:
 *
 * A = [
 * [ 1, 0, 0],
 * [-1, 0, 3]
 * ]
 *
 * B = [
 * [ 7, 0, 0 ],
 * [ 0, 0, 0 ],
 * [ 0, 0, 1 ]
 * ]
 *
 * |  1 0 0 |   | 7 0 0 |   |  7 0 0 |
 * AB = | -1 0 3 | x | 0 0 0 | = | -7 0 3 |
 * | 0 0 1 |
 */
public class SparseMatrixMultiplication {


    public int[][] multiply(int[][] A, int[][] B) {
        if (A == null || A.length == 0 ||
                B == null || B.length == 0) {
            return new int[0][0];
        }

        int m = A.length;
        int n = A[0].length;
        int l = B[0].length;

        int[][] C = new int[m][l];

        // Step 1: convert the sparse A to dense format
        Map<Integer, Map<Integer, Integer>> denseA = densifyMatrix(A, m, n);

        // Step 2: convert the sparse B to dense format
        Map<Integer, Map<Integer, Integer>> denseB = densifyMatrix(B, n, l);

        // Step3: calculate the denseA * denseB
        for (int i : denseA.keySet()) {
            for (int j : denseA.get(i).keySet()) {
                if (!denseB.containsKey(j)) continue;

                for (int k : denseB.get(j).keySet())
                    C[i][k] = C[i][k] + denseA.get(i).get(j) * denseB.get(j).get(k);
            }
        }

        return C;
    }

    private Map<Integer, Map<Integer, Integer>> densifyMatrix(int[][] matrix, int m, int n) {
        Map<Integer, Map<Integer, Integer>> denseMatrix = new HashMap<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] != 0) {
                    if (!denseMatrix.containsKey(i)) denseMatrix.put(i, new HashMap<>());
                    denseMatrix.get(i).put(j, matrix[i][j]);
                }
            }
        }
        return denseMatrix;
    }

    /**
     * Use one Table/HashMap
     * @param A
     * @param B
     * @return
     */
    public int[][] multiply1(int[][] A, int[][] B) {
        if (A == null || A[0] == null || B == null || B[0] == null) return null;
        int m = A.length;
        int n = A[0].length;
        int l = B[0].length;
        int[][] C = new int[m][l];

        Map<Integer, Map<Integer, Integer>> tableB = densifyMatrix(B, B.length, B[0].length);

        for(int i = 0; i < m; i++) {
            for(int k = 0; k < n; k++) {
                if (A[i][k] != 0){
                    for (Integer j: tableB.get(k).keySet()) {
                        C[i][j] = C[i][j] + A[i][k] * tableB.get(k).get(j);
                    }
                }
            }
        }
        return C;
    }

    /**
     * Matrix A (m*n) multiplies matrix B (n*l).
     *
     * @param A
     * @param B
     * @return
     */
    public int[][] multiply2(int[][] A, int[][] B) {
        int m = A.length;
        int n = A[0].length;
        int l = B[0].length;

        int[][] C = new int[m][l];

        for(int i = 0; i < m; i++) {
            for(int k = 0; k < n; k++) {
                if (A[i][k] != 0) {
                    for (int j = 0; j < l; j++) {
                        if (B[k][j] != 0)
                            C[i][j] = C[i][k] + A[i][k] * B[k][j];
                    }
                }
            }
        }
        return C;
    }
}
