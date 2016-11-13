package com.freetymekiyan.algorithms.level.Easy;

import java.util.Arrays;

/**
 * Given a <strong>sorted</strong> array, remove the duplicates in place such 
 * that each element appear only once and return the new length.
 * 
 * Do not allocate extra space for another array, you must do this in place
 * with constant memory.
 * 
 * For example,
 * Given input array A = [1,1,2],
 * Your function should return length = 2, and A is now [1,2].
 * 
 * Tags: Array, Two pointers
 */
class RemoveDuplicates {
    public static void main(String[] args) {
        RemoveDuplicates r = new RemoveDuplicates();
        int[] A = { 1, 1, 2, 2, 3 };
        System.out.println(r.removeDup(A));
    }
    
    /**
     * Use count to remember current position 
     */
    public int removeDupStandard(int[] A) {
        int count = 0;
        for (int i = 0; i < A.length; i++) {
            if (count == 0 || A[i] != A[count - 1]) { // first or not dup
                A[count++] = A[i]; // copy and update count
            }
        }
        return count;
    }

    // Count the number of unique elements only, will not remove dups
    public static int countUnique(int[] A) {
        int count = 0;
        for (int i = 0; i < A.length - 1; i++) {
            if (A[i] == A[i + 1]) {
                count++;
            }
        }
        return (A.length - count);
    }

    /**
     * use two pointers, one in the front, one for the dups
     */
    public static int removeDup(int[] A) {
        int i = 0;
        int j = 0;
        int pos = i + 1; // record current position
        while (i < A.length) {
            j = i + 1;
            while (j < A.length && A[i] == A[j]) {
                j++;
            }
            if (j >= A.length) break; // out of range
            A[pos] = A[j];
            pos++;
            i = j;
        }
        return pos;
    }

    // Create an array with all unique elements
    // use two pointers, one in the front, one for the dups
    public static int[] removeDuplicates(int[] A) {
        if (A.length < 2)
            return A;

        int j = 0;
        int i = 1;

        while (i < A.length) {
            if (A[i] == A[j]) {
                i++;
            } else {
                j++;
                A[j] = A[i];
                i++;
            }
        }

        int[] B = Arrays.copyOf(A, j + 1);

        return B;
    }

}
