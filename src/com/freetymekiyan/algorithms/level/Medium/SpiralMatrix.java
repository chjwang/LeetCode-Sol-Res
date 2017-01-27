package com.freetymekiyan.algorithms.level.Medium;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a matrix of m x n elements (m rows, n columns), return all elements of
 * the matrix in spiral order.
 * <p>
 * For example,
 * Given the following matrix:
 * <p>
 * [
 * [ 1, 2, 3 ],
 * [ 4, 5, 6 ],
 * [ 7, 8, 9 ]
 * ]
 * You should return [1,2,3,6,9,8,7,4,5].
 * <p>
 * Tags: Array
 */
class SpiralMatrix {

    public static void main(String[] args) {

    }

    public ArrayList<Integer> spiralOrder1(int[][] matrix) {
        ArrayList<Integer> result = new ArrayList<>();

        if (matrix == null || matrix.length == 0) return result;

        int m = matrix.length;
        int n = matrix[0].length;

        int x = 0;
        int y = 0;

        while (m > 0 && n > 0) {

            //if one row/column left, no circle can be formed
            if (m == 1) {
                for (int i = 0; i < n; i++) {
                    result.add(matrix[x][y++]);
                }
                break;
            } else if (n == 1) {
                for (int i = 0; i < m; i++) {
                    result.add(matrix[x++][y]);
                }
                break;
            }

            //below, process a circle

            //top - move right
            for (int i = 0; i < n - 1; i++)
                result.add(matrix[x][y++]);

            //right - move down
            for (int i = 0; i < m - 1; i++)
                result.add(matrix[x++][y]);

            //bottom - move left
            for (int i = 0; i < n - 1; i++)
                result.add(matrix[x][y--]);

            //left - move up
            for (int i = 0; i < m - 1; i++)
                result.add(matrix[x--][y]);

            x++; y++;
            m = m - 2; n = n - 2;
        }

        return result;
    }

    public List<Integer> spiralOrder2(int[][] matrix) {
        List<Integer> path = new ArrayList<>();
        if (matrix.length == 0) return path;

        int rowStart = 0, rowEnd = matrix.length-1, colStart = 0, colEnd = matrix[0].length-1;

        // Until it reach the spiral center that is rowStart > rowEnd or colStart > colEnd
        while (true) {
            // Right
            for (int j = colStart; j <= colEnd; j++) {
                path.add(matrix[rowStart][j]);
            }
            rowStart++;
            if (rowStart > rowEnd) break;

            // Down
            for (int i = rowStart; i <= rowEnd; i++) {
                path.add(matrix[i][colEnd]);
            }
            colEnd--;
            if (colStart > colEnd) break;

            // Left
            for (int j = colEnd; j >= colStart; j--) {
                path.add(matrix[rowEnd][j]);
            }
            rowEnd--;
            if (rowStart > rowEnd) break;

            // Up
            for (int i = rowEnd; i >= rowStart; i--) {
                path.add(matrix[i][colStart]);
            }
            colStart++;
            if (colStart > colEnd) break;
        }

        return path;
    }

    /**
     * Remember which level it is right now. Level => Spiral
     * Do level by level till reach center
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0) {
            return res;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        int lv = 0;

        while (2 * lv < m && 2 * lv < n) { // note 2 * level
            for (int i = lv; i < n - lv; i++) {
                res.add(matrix[lv][i]); // right
            }
            for (int j = lv + 1; j < m - lv; j++) {
                res.add(matrix[j][n - lv - 1]); // down
            }
            if (2 * lv == m - 1 || 2 * lv == n - 1) {
                break; // reach last row/col
            }
            for (int i = n - lv - 2; i >= lv; i--) {
                res.add(matrix[m - lv - 1][i]);
            }
            for (int j = m - lv - 2; j >= lv + 1; j--) {
                res.add(matrix[j][lv]);
            }
            lv++;
        }
        return res;
    }

    /**
     * Use rMin, rMax, cMin, cMax, to store the boundaries
     * Use i, j to track the position
     * Move i, j around to add elements
     * Break whenever out of bounds to avoid duplicate traversal
     */
    public List<Integer> spiralOrderB(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return res;

        int iMin = 0;
        int iMax = matrix.length - 1;
        int jMin = 0;
        int jMax = matrix[0].length - 1;
        int i = 0;
        int j = 0;
        // update boundary as soon as we traverse through it
        while (iMin <= iMax && jMin <= jMax) {
            for (j = jMin; j <= jMax; j++) {
                res.add(matrix[iMin][j]);
            }
            iMin++;
            if (iMin > iMax) {
                break; // break as soon as possible
            }
            for (i = iMin; i <= iMax; i++) {
                res.add(matrix[i][jMax]);
            }
            jMax--;
            if (jMax < jMin) {
                break;
            }
            for (j = jMax; j >= jMin; j--) {
                res.add(matrix[iMax][j]);
            }
            iMax--;
            if (iMax < iMin) {
                break;
            }
            for (i = iMax; i >= iMin; i--) {
                res.add(matrix[i][jMin]);
            }
            jMin++;
        }
        return res;
    }
}
