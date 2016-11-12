package com.freetymekiyan.algorithms.level.Easy;

/**
 * Given two sorted integer arrays A and B, merge B into A as one sorted array.
 * 
 * Note:
 * You may assume that A has enough space (size that is greater or equal to m +
 * n) to hold additional elements from B. The number of elements initialized in
 * A and B are m and n respectively.
 * 
 * Tags: Array
 */
class MergeSortedArray {
    public static void main(String[] args) {
        
    }
    
    /**
     * Merge from behind
     */
    private void merge(int A[], int m, int B[], int n) {
        if (n == 0)
            return;

        int i = n-1, j = m-1;

        while (i > 0 && j > 0) {
            if (A[i] > B[j]) {
                A[i + j + 1] = A[i];
                i--;
            } else {
                A[i + j + 1] = B[j];
                j--;
            }
        }

        /* remaining elements in B */
        while (j >= 0) {
            A[j] = B[j];
            j--;
        }
    }
}
