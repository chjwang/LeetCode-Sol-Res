package com.freetymekiyan.algorithms.level.Medium;

/**
 * Find the contiguous subarray within an array (containing at least one
 * number) which has the largest sum.
 * 
 * For example, given the array [−2,1,−3,4,−1,2,1,−5,4],
 * the contiguous subarray [4,−1,2,1] has the largest sum = 6.
 * 
 * More practice:
 * If you have figured out the O(n) solution, try coding another solution using
 * the divide and conquer approach, which is more subtle.
 * 
 * Tags: Divide and Conquer, Array, DP
 */
class MaximumSubarray {
    public static void main(String[] args) {
        int[] A = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
        System.out.println(maxSubArraySum(A));
    }
    
    /**
     * DP, O(n) Time, O(1) Space
     * If A[i] < 0 && currentMax + A[i] < 0, should recalculate max
     * If A[i] < 0 && currentMax + A[i] >= 0, continue
     * currentMax = max(currentMax + A[i], A[i])
     * maxSubArr = max(currentMax, maxSubArr)
     */
    public static int maxSubArraySum(int[] A) {
        if (A == null || A.length == 0) return 0;
        int curMax = A[0];
        int max = A[0];
        for (int i = 1; i < A.length; i++) { // note that i starts from 1
            curMax = Math.max(curMax + A[i], A[i]); // current max including A[i], max is not including A[i]
            max = Math.max(curMax, max);
        }
        return max;
    }
    
    /**
     * DP, O(n) Time, O(n) Space
     */
    public static int maxSubArraySumB(int[] A) {
        if (A == null || A.length == 0) return 0;
        int[] s = new int[A.length]; // save max sum so far in an array
        s[0] = A[0];
        int max = A[0];
        for (int i = 1; i < A.length; i++) {
            s[i] = s[i - 1] > 0 ? (A[i] + s[i - 1]) : A[i];
            max = Math.max(max, s[i]);
        }
        return max;
    }

    /**
     * DP, O(n) Time, O(1) Space
     */
    public static int maxSubArraySumB2(int[] A) {
        if (A == null || A.length == 0) return 0;
        int s = A[0], max = A[0];
        for (int i = 1; i < A.length; i++) {
            s = s > 0 ? (A[i] + s) : A[i];
            max = Math.max(max, s);
        }
        return max;
    }

    /**
     * Return not just sum, but also the range begin/end index
     *
     * If A[i] < 0, current sum + A[i] >= 0, we can continue addition because 
     * the positive sum would still contribute to positiveness of the subarray. 
     * If A[i] < 0, current sum + A[i] < 0, the current subarray has to end.
     */
    int[] maxSubArray(int[] A) {
        int beginTemp = 0; // save the temporary begining index
        int begin = 0; // beginning index
        int end = 0; // ending index
        int maxSoFar = A[0]; // max sum of previous sequence
        int maxEndingHere = A[0]; // max sum of this group

        for (int i = 1; i < A.length; i++) {
            if (maxEndingHere < 0) { // discard previous negative maxEndingHere
                maxEndingHere = A[i];
                beginTemp = i; // update begin temp
            } else {
                maxEndingHere += A[i];
            }

            if (maxEndingHere >= maxSoFar) { // update max so far
                maxSoFar = maxEndingHere;
                begin = beginTemp; // save index range
                end = i;
            }
        }
        return new int[]{begin, end, maxSoFar};
    }

    /**
     * this problem was discussed by Jon Bentley (Sep. 1984 Vol. 27 No. 9 Communications of the ACM P885)
     *
     * the paragraph below was copied from his paper (with a little modifications)
     *
     * algorithm that operates on arrays: it starts at the left end (element A[1]) and scans through
     * to the right end (element A[n]), keeping track of the maximum sum subvector seen so far.
     *
     * The maximum is initially A[0]. Suppose we've solved the problem for A[1 .. i - 1];
     * how can we extend that to A[1 .. i]?
     *
     * The maximum sum in the first I elements is either the maximum sum in the first i - 1 elements
     * (which we'll call MaxSoFar), or it is that of a subvector that ends in position i (which we'll
     * call MaxEndingHere).
     *
     * MaxEndingHere is either A[i] plus the previous MaxEndingHere, or just A[i], whichever is larger.
     * @param A
     * @return
     */
    public static int maxSubArray3(int[] A) {
        int maxSoFar = A[0], maxEndingHere = A[0];

        for (int i = 1; i < A.length; ++i){
            maxEndingHere = Math.max(maxEndingHere + A[i], A[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }
        return maxSoFar;
    }
}
