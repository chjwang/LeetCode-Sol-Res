package com.freetymekiyan.algorithms.Other;

import java.util.Arrays;

/**
 * Maximum product of a triplet (subsequnece of size 3) in array
 * Given an integer array, find a maximum product of a triplet in array.
 *
 * Examples:
 *
 * Input:  [10, 3, 5, 6, 20]
 * Output: 1200
 * Multiplication of 10, 6 and 20
 *
 * Input:  [-10, -3, -5, -6, -20]
 * Output: -90
 *
 * Input:  [1, -4, 3, -6, 7, 0]
 * Output: 168
 */
public class MaxProductTriplet {

    /** Brute Force Function to find a maximum product of a triplet in array of integers
     * Naive, O(n^3) time, O(1) Space
     */
    public int maxProduct(int arr[]) {
        int n = arr.length;
        // if size is less than 3, no triplet exists
        if (n < 3) return -1;

        // will contain max product
        int max_product = Integer.MIN_VALUE;

        for (int i = 0; i < n - 2; i++)
            for (int j = i + 1; j < n - 1; j++)
                for (int k = j + 1; k < n; k++)
                    max_product = Math.max(max_product, arr[i] * arr[j] * arr[k]);

        return max_product;
    }

    /** Approach 2: O(nlogn) Time, O(1) Space
     Sort the array using some efficient in-place sorting algorithm in ascending order.
     Return the maximum of product of last three elements of the array and product of first two elements and last element.
     */
    public int maxProduct2(int arr[]) {
        int n = arr.length;
        // if size is less than 3, no triplet exists
        if (n < 3) return -1;

        // Sort the array in ascending order
        Arrays.sort(arr);

        // Return the maximum of product of last three elements and
        // product of first two elements and last element
        return Math.max(arr[0] * arr[1] * arr[n - 1],
                arr[n - 1] * arr[n - 2] * arr[n - 3]);
    }

    /** Approach 3: O(n) Time, O(1) Space

     Scan the array and compute Maximum, second maximum and third maximum element present in the array.

     Scan the array and compute Minimum and second minimum element present in the array.

     Return the maximum of product of Maximum, second maximum and third maximum and product of
     Minimum, second minimum and Maximum element.

     Note – Step 1 and Step 2 can be done in single traversal of the array.
     */
    public int maxProduct3(int arr[]) {
        int n = arr.length;
        // if size is less than 3, no triplet exists
        if (n < 3) return -1;

        // Initialize Maximum, second maximum and third maximum element
        int maxA = Integer.MIN_VALUE, maxB = Integer.MIN_VALUE, maxC = Integer.MIN_VALUE;

        // Initialize Minimum and second mimimum element
        int minA = Integer.MAX_VALUE, minB = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            // Update Maximum, second maximum and third maximum element
            if (arr[i] > maxA) {
                maxC = maxB;
                maxB = maxA;
                maxA = arr[i];
            }

            // Update second maximum and third maximum element
            else if (arr[i] > maxB)
            {
                maxC = maxB;
                maxB = arr[i];
            }

            // Update third maximum element
            else if (arr[i] > maxC)
                maxC = arr[i];

            // Update Minimum and second mimimum element
            if (arr[i] < minA)
            {
                minB = minA;
                minA = arr[i];
            }

            // Update second mimimum element
            else if(arr[i] < minB)
                minB = arr[i];
        }

        return Math.max(minA * minB * maxA, maxA * maxB * maxC);
    }

    /**
     *
Approach 4: O(n) Time, O(n) Space

Construct four auxiliary arrays leftMax[], rightMax[], leftMin[] and rightMin[] of same size as input array.

Fill leftMax[], rightMax[], leftMin[] and rightMin[] in below manner.
  leftMax[i] will contain maximum element on left of arr[i] excluding arr[i]. For index 0, left will contain -1.
  leftMin[i] will contain minimum element on left of arr[i] excluding arr[i]. For index 0, left will contain -1.
  rightMax[i] will contain maximum element on right of arr[i] excluding arr[i]. For index n-1, right will contain -1.
  rightMin[i] will contain minimum element on right of arr[i] excluding arr[i]. For index n-1, right will contain -1.

For all array indexes i except first and last index, compute maximum of arr[i]*x*y where x can be
leftMax[i] or leftMin[i] and y can be rightMax[i] or rightMin[i].

Return the maximum from step 3.
     */
    public int maxProduct4(int arr[]) {
        int n = arr.length;
        // if size is less than 3, no triplet exists
        if (n < 3) return -1;

        // Construct four auxiliary vectors of size n and initailize them by -1
        int[] leftMin = new int[n];
        int[] rightMin = new int[n];
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        Arrays.fill(leftMin, -1);
        Arrays.fill(rightMin, -1);
        Arrays.fill(leftMax, -1);
        Arrays.fill(rightMax, -1);

        // will contain max product
        int max_product = Integer.MIN_VALUE;

        // to store maximum element on left of array
        int max_sum = arr[0];

        // to store minimum element on left of array
        int min_sum = arr[0];

        // leftMax[i] will contain max element on left of arr[i] excluding arr[i].
        // leftMin[i] will contain min element on left of arr[i] excluding arr[i].
        for (int i = 1; i < n; i++) {
            leftMax[i] = max_sum;
            if (arr[i] > max_sum) max_sum = arr[i];

            leftMin[i] = min_sum;
            if (arr[i] < min_sum) min_sum = arr[i];
        }

        // reset max_sum to store maximum element on right of array
        max_sum = arr[n - 1];

        // reset min_sum to store minimum element on right of array
        min_sum = arr[n - 1];

        // rightMax[i] will contain max element on right of arr[i] excluding arr[i].
        // rightMin[i] will contain min element on right of arr[i] excluding arr[i].
        for (int j = n - 2; j >= 0; j--) {
            rightMax[j] = max_sum;
            if (arr[j] > max_sum) max_sum = arr[j];

            rightMin[j] = min_sum;
            if (arr[j] < min_sum) min_sum = arr[j];
        }

        // For all array indexes i except first and last, compute maximum of arr[i]*x*y where
        // x can be leftMax[i] or leftMin[i] and y can be rightMax[i] or rightMin[i].
        for (int i = 1; i < n - 1; i++) {
            int max1 = Math.max(arr[i] * leftMax[i] * rightMax[i], arr[i] * leftMin[i] * rightMin[i]);
            int max2 = Math.max(arr[i] * leftMax[i] * rightMin[i], arr[i] * leftMin[i] * rightMax[i]);

            max_product = Math.max(max_product, Math.max(max1, max2));
        }

        return max_product;
    }

    // Driver program to test above functions
    public static void main(String[] args) {
        int arr[] = {10, 3, 5, 6, 20};

        int max = new MaxProductTriplet().maxProduct(arr);

        if (max == -1)
            System.out.println("No Triplet Exists");
        else
            System.out.println("Maximum product is " + max);
    }
}
