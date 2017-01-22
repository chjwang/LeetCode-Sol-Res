package com.freetymekiyan.algorithms.level.Medium;

/**
 * Find the contiguous subarray within an array (containing at least one
 * number) which has the largest product.
 *
 * For example, given the array [2,3,-2,4],
 * the contiguous subarray [2,3] has the largest product = 6.
 *
 * Tags: Array, DP
 */
class MaxProductSubArr {

    public static void main(String[] args) {

    }

    /**
     * DP, update according to A[i]
     * f(k) = Largest product subarray, from index 0 up to k.
     * g(k) = Smallest product subarray, from index 0 up to k.
     *
     * f(k) = max( f(k-1) * A[k], A[k], g(k-1) * A[k] )
     * g(k) = min( g(k-1) * A[k], A[k], f(k-1) * A[k] )
     *
     * O(n) time
     * O(1) space
     */
    public int maxProduct(int[] A) {
        if (A == null || A.length == 0)
            return 0;

        int curMax = A[0], curMin = A[0], res = A[0];
        for (int i = 1; i < A.length; i++) {
            int lastMax = curMax, lastMin = curMin; // results of last loop
            curMax = Math.max(Math.max(A[i], lastMax * A[i]), lastMin * A[i]);
            curMin = Math.min(Math.min(A[i], lastMax * A[i]), lastMin * A[i]);
            res = Math.max(curMax, res);
        }
        return res;
    }

    /**
     * O(n) time
     * O(n) space
     */
    public int maxProduct2(int[] A) {
        int[] max = new int[A.length];
        int[] min = new int[A.length];

        max[0] = min[0] = A[0];
        int result = A[0];

        for (int i = 1; i < A.length; i++) {
            if (A[i] > 0) {
                max[i] = Math.max(A[i], max[i - 1] * A[i]);
                min[i] = Math.min(A[i], min[i - 1] * A[i]);
            } else {
                max[i] = Math.max(A[i], min[i - 1] * A[i]);
                min[i] = Math.min(A[i], max[i - 1] * A[i]);
            }

            result = Math.max(result, max[i]);
        }

        return result;
    }
}
