package com.freetymekiyan.algorithms.level.Medium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * After robbing those houses on that street, the thief has found himself a new place for his thievery
 * so that he will not get too much attention. This time, all houses at this place are arranged in a
 * circle. That means the first house is the neighbor of the last one.
 *
 * Meanwhile, the security system for these houses remain the same as for those in the previous street.
 * Given a list of non-negative integers representing the amount of money of each house, determine
 * the maximum amount of money you can rob tonight without alerting the police.
 *
 * This is an extension of House Robber. There are two cases here
 * 1) 1st element is included and last is not included
 * 2) 1st is not included and last is included.
 *
 * Therefore, we can use the similar dynamic programming approach to scan the array twice and get
 * the larger value.
 */
public class HouseRobber2 {
    public int rob(int[] nums) {
        if(nums==null || nums.length==0)
            return 0;

        if(nums.length==1)
            return nums[0];

        int max1 = robHelper(nums, 0, nums.length-2);
        int max2 = robHelper(nums, 1, nums.length-1);

        return Math.max(max1, max2);
    }

    public int robHelper(int[] nums, int i, int j){
        if(i==j){
            return nums[i];
        }

        int[] dp = new int[nums.length];
        dp[i]=nums[i];
        dp[i+1]=Math.max(nums[i+1], dp[i]);

        for(int k=i+2; k<=j; k++){
            dp[k]=Math.max(dp[k-1], dp[k-2]+nums[k]);
        }

        return dp[j];
    }
}
