package com.freetymekiyan.algorithms.level.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a set of distinct positive integers, find the largest subset such that every pair (Si, Sj) of elements in this
 * subset satisfies: Si % Sj = 0 or Sj % Si = 0.
 * <p>
 * If there are multiple solutions, return any subset is fine.
 * <p>
 * Example 1:
 * <p>
 * nums: [1,2,3]
 * <p>
 * Result: [1,2] (of course, [1,3] will also be ok)
 * Example 2:
 * <p>
 * nums: [1,2,4,8]
 * <p>
 * Result: [1,2,4,8]
 * <p>
 * Tags: Dynamic Programming, Math
 */
public class LargestDivisibleSubset {

    /**
     * For a new integer S, S can be placed into the set as long as it can divide the smallest number of the set or is
     * divisible by the largest number of the set.
     * <p>
     * Recurrent relation:
     * T[n] = the length of the largest divisible subset whose largest number is a[n]
     * T[n+1] = max{ 1 + T[i] } if a[n+1] mod a[i] == 0 for 0<=i<=n, else 1
     * <p>
     * Sort the array first.
     * Create an array named parent to record parent index of current element.
     * Create an array named count to record the largest subset of current element.
     * Then Start from one end of the array and try to find larger element that divides current element.
     * If it divides and subset size is smaller than that element + 1, then update size and parent.
     * If count is bigger than max, update max and max index.
     * Finally, recover the list of integers with parent array.
     */
    public List<Integer> largestDivisibleSubset(int[] nums) {
        if (nums == null) throw new IllegalArgumentException("Input array nums is null");
        if (nums.length <= 1) throw new IllegalArgumentException("Input array must have at least 2 elements");

        Arrays.sort(nums);
        int[] parent = new int[nums.length];
        int[] count = new int[nums.length];
        int max = 0;
        int maxIndex = -1;
        for (int i = nums.length - 1; i >= 0; i--) {
            for (int j = i; j < nums.length; j++) {
                if (nums[j] % nums[i] == 0 && count[i] < 1 + count[j]) {
                    count[i] = 1 + count[j];
                    parent[i] = j;
                    if (count[i] > max) {
                        max = count[i];
                        maxIndex = i;
                    }
                }
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            res.add(nums[maxIndex]);
            maxIndex = parent[maxIndex];
        }
        return res;
    }

    public List<Integer> largestDivisibleSubset2(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        if (nums.length == 0) return ans;

        Arrays.sort(nums);
        int[] dp = new int[nums.length], parent = new int[nums.length];
        Arrays.fill(dp, 1);
        Arrays.fill(parent, -1); //当parent数组中某数为-1时，表示这个数自己是一个集合
        int max_index = 0, max_dp = 1;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i - 1 ; j >= 0 ; j--) {
                if (nums[i] % nums[j] == 0 && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }
            }
            if (max_dp < dp[i]) {
                max_dp = dp[i];
                max_index = i;
            }
        }
        for (int i = max_index; i != -1; i = parent[i])
            ans.add(nums[i]);
        return ans;
    }
}
