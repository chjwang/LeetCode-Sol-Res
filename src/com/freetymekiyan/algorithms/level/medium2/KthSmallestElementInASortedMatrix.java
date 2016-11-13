package com.freetymekiyan.algorithms.level.Medium;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Given a n x n matrix where each of the rows and columns are sorted in ascending order, find the
 * kth smallest element in the matrix. <p> Note that it is the kth smallest element in the sorted
 * order, not the kth distinct element. <p> Example: <p> matrix = [ [ 1,  5,  9], [10, 11, 13], [12,
 * 13, 15] ], k = 8, <p> return 13. Note: You may assume k is always valid, 1 ≤ k ≤ n2. <p> Tags:
 * Binary Search, Heap Similar Problems: (M) Find K Pairs with Smallest Sums
 */
public class KthSmallestElementInASortedMatrix {

    /**
     * Heap (Priority Queue).
     * Put first column or row into the min heap.
     * Poll the root and put the next one in the same column or row into heap.
     * Need to keep track of the position of elements in the queue.
     */
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        Queue<Element> minHeap = new PriorityQueue<>(n);
        for (int i = 0; i < n; i++) {
            minHeap.offer(new Element(matrix[0][i], 0, i));
        }
        int res = 0;
        for (int i = 0; i < k; i++) {
            Element element = minHeap.poll();
            res = element.number;
            if (element.row + 1 < n) {
                minHeap.offer(new Element(matrix[element.row + 1][element.col], element.row + 1, element.col));
            }
        }
        return res;
    }

    /**
     * Binary Search. Binary search the rank of a certain value and find the one that's kth
     * smallest.
     *
     * The initial range is matrix[0][0] to matrix[n - 1][n - 1].
     *
     * Get the middle value and find its position in the matrix by checking how many elements are smaller than it.
     *
     * You may go through each row to find how many elements are larger since the matrix is column ascending.
     * O(n).
     *
     * Then if the ranking is > k, it means the value should be smaller, right range shrinks.
     *
     * If the ranking is < k, the value should be bigger, left range shrinks. If the ranking is k,
     * return the value.
     */
    public int kthSmallestBinarySearch(int[][] matrix, int k) {
        int n = matrix.length;
        int left = matrix[0][0];
        int right = matrix[n - 1][n - 1];
        int mid;
        while (left <= right) {
            mid = (right - left) / 2 + left;
            int j = n - 1;
            int count = 0;
            for (int i = 0; i < n; i++) {
                while (j >= 0 && matrix[i][j] > mid) {
                    j--;
                }
                count += j + 1;
            }
            if (count < k) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        int i1 = 0;
        int i2 = matrix.length - 1;
        int j1 = 0;
        int j2 = matrix[0].length - 1;

        return helper(matrix, i1, i2, j1, j2, target);
    }

    public boolean helper(int[][] matrix, int i1, int i2, int j1, int j2, int target) {

        if (i1 > i2 || j1 > j2)
            return false;

        // can be further optimized using binary search to locate j thst satisfies target < matrix[i1][j]
//        int l = Arrays.binarySearch(matrix[i1], j1, j2, target);
//        if (l < 0) { // -l is the insertion point
//            return helper(matrix, i1, i2, j1, -l-1, target);
//        } else
//            return true;
        for (int j = j1; j <= j2; j++) {
            if (target < matrix[i1][j]) {
                return helper(matrix, i1, i2, j1, j - 1, target);
            } else if (target == matrix[i1][j]) {
                return true;
            }
        }

        for (int i = i1; i <= i2; i++) {
            if (target < matrix[i][j1]) {
                return helper(matrix, i1, i - 1, j1, j2, target);
            } else if (target == matrix[i][j1]) {
                return true;
            }
        }

        for (int j = j1; j <= j2; j++) {
            if (target > matrix[i2][j]) {
                return helper(matrix, i1, i2, j + 1, j2, target);
            } else if (target == matrix[i2][j]) {
                return true;
            }
        }

        for (int i = i1; i <= i2; i++) {
            if (target > matrix[i][j2]) {
                return helper(matrix, i1, i + 1, j1, j2, target);
            } else if (target == matrix[i][j2]) {
                return true;
            }
        }

        return false;
    }

    /**
     * Time Complexity: O(m + n)
     */
    public boolean searchMatrix2(int[][] matrix, int target) {
        int m = matrix.length - 1;
        int n = matrix[0].length - 1;

        int i = m;
        int j = 0;

        while (i >= 0 && j <= n) {
            if (target < matrix[i][j]) {
                i--;
            } else if (target > matrix[i][j]) {
                j++;
            } else {
                return true;
            }
        }

        return false;
    }

    class Element implements Comparable<Element> {

        int number;
        int row;
        int col;

        public Element(int number, int row, int col) {
            this.number = number;
            this.row = row;
            this.col = col;
        }

        @Override
        public int compareTo(Element o) {
            return Integer.compare(this.number, o.number);
        }
    }
}
