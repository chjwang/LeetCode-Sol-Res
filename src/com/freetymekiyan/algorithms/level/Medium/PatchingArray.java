package com.freetymekiyan.algorithms.level.Medium;

/**
 * Given a sorted positive integer array nums and an integer n, add/patch elements to the array such that any number in
 * range [1, n] inclusive can be formed by the sum of some elements in the array.
 * Return the minimum number of patches required.
 *
 * Example 1:
 * nums = [1, 3], n = 6
 * Return 1.
 *
 * Combinations of nums are [1], [3], [1,3], which form possible sums of: 1, 3, 4.
 * Now if we add/patch 2 to nums, the combinations are: [1], [2], [3], [1,3], [2,3], [1,2,3].
 * Possible sums are 1, 2, 3, 4, 5, 6, which now covers the range [1, 6].
 * So we only need 1 patch.
 *
 * Example 2:
 * nums = [1, 5, 10], n = 20
 * Return 2.
 * The two patches can be [2, 4].
 *
 * Example 3:
 * nums = [1, 2, 2], n = 5
 * Return 0.
 *
 * Tags: Greedy
 */
public class PatchingArray {

    /**
     * Build the largest range possible and find the smallest missing number
     * if missing >= nums[i], range can be extended to missing + nums[i]
     * else add missing number, range can be extended to missing * 2
     * stop till missing > n
     *
     *
     * Let miss be the smallest number that can not be formed by the sum of elements in the array.
     * All elements in [0, miss) can be formed.
     *
     * The miss value starts with 1.
     *
     * If the next number nums[i]<=miss, then the boundary is increased to be [0, miss+nums[i]),
     * because all numbers between the boundaries can be formed;
     *
     * If next number nums[i]>miss, that means there is a gap and we need to insert a number,
     * inserting miss itself is a the choice because its boundary doubles and cover every number
     * between the boundaries [0, miss+miss).
     *
     * 以此类推直至遍历完整个数组，我们可以得到结果。
     *
     * 下面我们来举个例子说明：
     *
     * 给定nums = [1, 2, 4, 11, 30], n = 50，我们需要让[0, 50]之间所有的数字都能被nums中的数字之和表示出来。
     * 首先使用1, 2, 4可能表示出0到7之间的所有数，表示范围为[0, 8)，
     * 但我们不能表示8，因为下一个数字11太大了，所以我们要在数组里加上一个8，此时能表示的范围是[0, 16)，
     * 那么我们需要插入16吗，答案是不需要，因为我们数组有1和4，可以组成5，而下一个数字11，加一起能组成16，
     * 所以有了数组中的11，我们此时能表示的范围扩大到[0, 27)，
     * 但我们没法表示27，因为30太大了，所以此时我们给数组中加入一个27，那么现在能表示的范围是[0, 54)，
     * 已经满足要求了，我们总共添加了两个数8和27，所以返回2即可。
     */
    public int minPatches2(int[] nums, int n) {
        int count = 0;
        int i = 0;

        for (long miss = 1; miss <= n; count++) {
            miss += (i < nums.length && nums[i] <= miss) ? nums[i++] : miss;
        }
        return count - i;
    }

    public int minPatches(int[] nums, int n) {
        long miss = 1;
        int count = 0;
        int i = 0;

        while(miss <= n){
            if(i<nums.length && nums[i] <= miss){
                miss = miss + nums[i];
                i++;
            } else {
                miss += miss;
                count++;
            }
        }

        return count;
    }
}
