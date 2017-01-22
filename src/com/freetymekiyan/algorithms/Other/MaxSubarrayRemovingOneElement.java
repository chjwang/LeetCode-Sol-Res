package com.freetymekiyan.algorithms.Other;

/**
 Maximum sum subarray removing at most one element

 Given an array, we need to find maximum sum subarray, removing one element is also allowed to get
 the maximum sum.

 Examples:

 Input  : arr[] = {1, 2, 3, -4, 5}
 Output : 11
 Explanation : We can get maximum sum subarray by removing -4.

 Input  : arr[] = [-2, -3, 4, -1, -2, 1, 5, -3]
 Output : 9
 Explanation : We can get maximum sum subarray by removing -2 as, [4, -1, 1, 5] summing 9, which is
 the maximum achievable sum.

 */
public class MaxSubarrayRemovingOneElement {
/**
 * If element removal condition is not applied, we can solve this problem using Kadane’s algorithm
 * but here one element can be removed also for increasing maximum sum.
 *
 * This condition can be handled using two arrays, forward and backward array, these arrays store
 * the current maximum subarray sum from starting to ith index, and from ith index to ending respectively.
 *
 * In below code, two loops are written, first one stores maximum current sum in forward direction
 * in fw[] and other loop stores the same in backward direction in bw[].
 *
 * Getting current maximum and update is same as Kadane’s algorithm.
 *
 * Now when both arrays are created, we can use them for one element removal conditions as follows,
 * at each index i, maximum subarray sum after ignoring i’th element will be fw[i-1] + bw[i+1]
 * so we loop for all possible i values and we choose maximum among them.
 *
 * Total time complexity and space complexity of solution is O(N), O(N)
 */
    // Method returns maximum sum of all subarray where removing one element is also allowed
    int maxSumSubarrayRemovingOneEle(int arr[]) {
        // Maximum sum subarrays in forward and backward directions
        int n = arr.length;
        int[] fw = new int[n];
        int[] bw = new int[n];

        // Initialize current max and max so far.
        int curMax = arr[0], maxSoFar = arr[0];

        // calculating maximum sum subarrays in forward direction
        fw[0] = 0;
        for (int i = 1; i < n; i++) {
            curMax = Math.max(arr[i], curMax + arr[i]);
            maxSoFar = Math.max(maxSoFar, curMax);

            // storing current maximum till ith, in forward array
            fw[i] = curMax;
        }

        // calculating maximum sum subarrays in backward direction
        curMax = maxSoFar = bw[n - 1] = arr[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            curMax = Math.max(arr[i], curMax + arr[i]);
            maxSoFar = Math.max(maxSoFar, curMax);

            // storing current maximum from ith, in backward array
            bw[i] = curMax;
        }

        /*  Initializing final ans by maxSoFar so that, case when no element is removed to get max sum subarray is also handled  */
        int fans = maxSoFar;

        //  choosing maximum ignoring ith element
        for (int i = 1; i < n - 1; i++)
            fans = Math.max(fans, fw[i - 1] + bw[i + 1]);

        return fans;
    }

    //  Driver code to test above methods
    public static void main(String[] args) {
        int arr[] = {-2, -3, 4, -1, -2, 1, 5, -3};
        System.out.println(new MaxSubarrayRemovingOneElement().maxSumSubarrayRemovingOneEle(arr));
    }
}
