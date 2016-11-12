package com.freetymekiyan.algorithms.level.Easy;

import java.util.Arrays;

/**
 * Rotate an array of n elements to the right by k steps
 * For example, with n = 7 and k = 3,
 * the array [1,2,3,4,5,6,7] is rotated to [5,6,7,1,2,3,4].
 *
 * Note:
 * Try to come up as many solutions as you can, there are at least 3 different
 * ways to solve this problem.
 *
 * Hint:
 * Could you do it in-place with O(1) extra space?
 *
 * Related problem:
 * Reverse Words in a String II
 *
 * Tags: Array
 */
class RotateArray {
    public static void main(String[] args) {
        RotateArray r = new RotateArray();
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        int k = 3;
        r.rotate(nums, k);
        System.out.println(Arrays.toString(nums));

        int[] nums2 = {1, 2, 3, 4, 5, 6};
        int k2 = 2;
        r.rotate(nums2, k2);
        System.out.println(Arrays.toString(nums2));

        int[] nums3 = {1, 2};
        int k3 = 2;
        r.rotate(nums3, k3);
        System.out.println(Arrays.toString(nums3));
    }

    /**
     * O(n) Time, O(1) Space
     * Build a full circle of rotation
     * Start from current index and repeat exactly "length of array" times
     * 1. Calculate new index which is current index move k steps forward
     * If move out of range, just start from beginning again
     * newIdx = (curIdx + k ) % len
     * 2. Circle can be the same, for example, n = 6, k = 2
     * Index will be 0, 2, 4, 0, 2, 4
     * So save the start index of the circle
     * If start from there again, move one step forward
     */
    public void rotate(int[] nums, int k) {
        if (nums == null || nums.length == 0) return;
        if (nums.length == 1 || k == 0 || k == nums.length) return; // special cases

        int len = nums.length;
        k %= len;
        int idx = 0;
        int tmp = nums[idx]; // the number to write to new index
        int tmp2; // save the number at new index
        for (int i = 0, j = 0; i < len; i++) { // j is the start index of current circle
            idx = (idx + k) % len;
            tmp2 = nums[idx];
            nums[idx] = tmp; // write to new index
            tmp = tmp2; // next write
            if (idx == j) { // circle ends
                idx = ++j; // move to next circle
                tmp = nums[idx];
            }
        }
    }


    /**
     * 1. Divide the array two parts: 1,2,3,4 and 5, 6
     * 2. Reverse first part: 4,3,2,1,5,6
     * 3. Reverse second part: 4,3,2,1,6,5
     * 4. Reverse the whole array: 5,6,1,2,3,4
     * @param arr
     * @param order
     */
    public static void rotate2(int[] arr, int order) {
        if (arr == null || arr.length==0 || order < 0) {
            throw new IllegalArgumentException("Illegal argument!");
        }

        if(order > arr.length){
            order = order %arr.length;
        }

        //length of first part
        int a = arr.length - order;

        reverse(arr, 0, a-1);
        reverse(arr, a, arr.length-1);
        reverse(arr, 0, arr.length-1);

    }

    public static void reverse(int[] arr, int left, int right){
        if(arr == null || arr.length == 1)
            return;

        while(left < right){
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
}
