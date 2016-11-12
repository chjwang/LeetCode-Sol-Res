package com.freetymekiyan.algorithms.level.Medium;

/**
 * Given an array of n positive integers and a positive integer s, find the 
 * minimal length of a subarray of which the sum ≥ s. If there isn't one, return 
 * 0 instead.
 * 
 * For example, given the array [2,3,1,2,4,3] and s = 7,
 * the subarray [4,3] has the minimal length under the problem constraint.
 * 
 * More practice:
 * If you have figured out the O(n) solution, try coding another solution of 
 * which the time complexity is O(n log n).
 * 
 * Tags: Array, Two Pointers, Binary Search
 *
 * Similar Problems: (H) Minimum Window Substring, (E) Maximum Size Subarray Sum 
 *                  Equals k
 */
class MinSizeSubarraySum {

    /**
     * 我们需要定义两个指针left和right，分别记录子数组的左右的边界位置，然后我们让right向右移，
     * 直到子数组和大于等于给定值或者right达到数组末尾，此时我们更新最短距离，并且将left像右移一位，
     * 然后再sum中减去移去的值，
     * 然后重复上面的步骤，直到right到达末尾，且left到达临界位置，即要么到达边界，要么再往右移动，和就会小于给定值。
     *
     * O(n)
     */
    public int minSubArrayLen(int s, int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int start = 0;
        int end = 0;
        int sum = 0;
        int min = Integer.MAX_VALUE;

        while (end < nums.length) {
            sum += nums[end++];
            while (sum >= s) {
                min = Math.min(min, end - start + 1);
                sum -= nums[start++];
            }
        }

        return min == Integer.MAX_VALUE ? 0 : min;
    }

    /**
     * O(nlgn)的解法，这个解法要用到二分查找法，思路是，我们建立一个比原数组长一位的sums数组，
     * 其中sums[i]表示nums数组中[0, i - 1]的和，
     * 然后我们对于sums中每一个值sums[i]，用二分查找法找到子数组的右边界位置，使该子数组之和大于sums[i] + s，
     * 然后我们更新最短长度的距离即可。
     * @param s
     * @param nums
     * @return
     */
    public int minSubArrayLen3(int s, int[] nums) {
        int res = Integer.MAX_VALUE;
        int n = nums.length;
        int[] sums = new int[n + 1];

        for (int i = 1; i < n + 1; ++i)
            sums[i] = sums[i - 1] + nums[i - 1];

        for (int i = 0; i < n; ++i) {
            int left = i + 1;
            int right = n;
            int t = sums[i] + s;

            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (sums[mid] < t)
                    left = mid + 1;
                else
                    right = mid - 1;
            }
            if (left == n + 1)
                break;
            res = Math.min(res, left - i);
        }
        return res == Integer.MAX_VALUE ? 0 : res;

    }


    /**
     * Another solution is by using binary search algorithm, we initial another array with the length
     * to be nums.length + 1, and sum[i] is equal to the sum from nums[0] to num[i - 1],
     * when we meet an element, for example sum[i] is larger or equal to s, then find the largest element
     * that is smaller than sum[i] - target by using binary search.
     *
     * If find, the index j to index i - 1 is the subarray. length = i - j
     * Time Complexity: O(nlogn)
     *
     * @param s
     * @param nums
     * @return
     */
    public int minSubArrayLenBinarySearch(int s, int[] nums) {
        if (null == nums || nums.length == 0) {
            return 0;
        }
        int min = nums.length + 1;
        int[] sum = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            sum[i + 1] = sum[i] + nums[i];
            if (sum[i + 1] >= s) {
                int left = binarySearch(sum, sum[i + 1] - s, 0, i + 1);
                min = Math.min(min, i + 1 - left);
            }
        }
        return min == nums.length + 1 ? 0 : min;
    }

    private int binarySearch(int[] nums, int target, int start, int end) {
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] >= target) {
                end = mid;
            } else {
                start = mid;
            }
        }
        if (nums[end] <= target) {
            return end;
        } else {
            return start;
        }
    }


    /**
     * O(nlogn)
     */
    public int minSubArrayLen2(int s, int[] nums) {
        int i = 1;
        int j = nums.length;
        int min = 0;
        while (i <= j) {
            int mid = (j - i) / 2 + i;
            if (isWindowExist(mid, nums, s)) {
                j = mid - 1;
                min = mid;
            } else i = mid + 1;
        }
        return min;
    }
    
    private boolean isWindowExist(int size, int[] nums, int s) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i >= size) sum -= nums[i - size];
            sum += nums[i];
            if (sum >= s) return true;
        }
        return false;
    }
}