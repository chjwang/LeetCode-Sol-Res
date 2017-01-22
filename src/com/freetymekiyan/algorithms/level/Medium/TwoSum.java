package com.freetymekiyan.algorithms.level.Medium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 * <p>
 * You may assume that each input would have exactly one solution.
 * <p>
 * Example:
 * Given nums = [2, 7, 11, 15], target = 9,
 * <p>
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 * <p>
 * UPDATE (2016/2/13):
 * The return format had been changed to zero-based indices. Please read the above updated description carefully.
 * <p>
 * Company Tags: LinkedIn, Uber, Airbnb, Facebook, Amazon, Microsoft, Apple, Yahoo, Dropbox, Bloomberg, Yelp, Adobe
 * Tags: Array, Hash Table
 * Similar Problems: (M) 3Sum, (M) 4Sum, (M) Two Sum II - Input array is sorted, (E) Two Sum III - Data structure
 * design
 */
public class TwoSum {

    private TwoSum t;

    /**
     * Hash Table. O(n) time, O(n) space.
     * This method is an optimization from the brute-force method,
     * which we search for the other target with a second loop.
     * We put number as key , index as value.
     * Search new target in map, and make sure it is not the same number.
     * So check the indices of current position and what we get from the map.
     * If there is no match, just return null.
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int newTarget = target - nums[i];
            if (map.containsKey(newTarget) && i != map.get(newTarget)) {
                // found in map and it's not itself
                return new int[]{i, map.get(newTarget)};
            }
        }
        return null;
    }

    /**
     * Hash Table. One loop.
     * Just put numbers into the map as looping through.
     * So we can search numbers already processed in O(1).
     * Note that if we find an answer, we current index will be larger than the index in map.
     * So put it behind.
     */
    public int[] twoSumB(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int newTarget = target - nums[i];
            if (map.containsKey(newTarget) && i != map.get(newTarget)) {
                return new int[]{map.get(newTarget), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }

    /**
     * 又碰到敏感的关键字array和target，自然而然想到二分查找法。
     * 但是这道题不能像传统二分查找法那样舍弃一半在另外一半查找，需要一点点挪low和high指针，所以时间复杂度为O(n)。

     首先先将整个list拷贝并排序，使用Arrays.Sort()函数，时间复杂度O(nlogn)

     然后利用二分查找法，判断target和numbers[low]+numbers[high]。
     target == numbers[low]+numbers[high]， 记录copy[low]和copy[high]；
     target > numbers[low]+numbers[high]，说明最大的和最小的加一起还小于target，所以小值要取大一点，即low++；
     target > numbers[low]+numbers[high], 说明最大的和最小的加一起大于target，那么大值需要往下取一点，即high--。

     再把找到的两个合格值在原list中找到对应的index，返回即可。

     总共的时间复杂度为O(n+nlogn+n+n) = O(nlogn)。
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSumC(int[] numbers, int target) {
        int [] res = new int[2];
        if(numbers==null || numbers.length<2)
            return res;

        //copy original list and sort
        int[] copylist = new int[numbers.length];
        System.arraycopy(numbers, 0, copylist, 0, numbers.length);
        Arrays.sort(copylist);

        int low = 0;
        int high = copylist.length-1;

        while (low < high){
            if (copylist[low] + copylist[high] < target)
                low++;
            else if (copylist[low] + copylist[high] > target)
                high--;
            else{
                res[0] = copylist[low];
                res[1] = copylist[high];
                break;
            }
        }

        //find index from original list
        int index1 = -1, index2 = -1;
        for(int i = 0; i < numbers.length; i++){
            if(numbers[i] == res[0] && index1 == -1)
                index1 = i;
            else if(numbers[i] == res[1] && index2 == -1)
                index2 = i;
        }
        res[0] = index1;
        res[1] = index2;
        Arrays.sort(res);
        return res;
    }

    @Before
    public void setUp() {
        t = new TwoSum();
    }

    @Test
    public void testExamples() {
        int[] numbers = {3, 2, 4}; // 6 = 3 + 3
        int target = 6;
        int[] res = t.twoSum(numbers, target);
        Assert.assertArrayEquals(new int[]{1, 2}, res);

        numbers = new int[]{2, 7, 11, 15};
        target = 9;
        res = t.twoSum(numbers, target);
        Assert.assertArrayEquals(new int[]{0, 1}, res);
    }

    @After
    public void tearDown() {
        t = null;
    }
}