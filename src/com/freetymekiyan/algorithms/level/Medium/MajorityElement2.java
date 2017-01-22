package com.freetymekiyan.algorithms.level.Medium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Given an integer array of size n, find all elements that appear more than ⌊ n/3 ⌋ times. The algorithm should run in
 * linear time and in O(1) space.
 * <p>
 * Hint:
 * <p>
 * How many majority elements could it possibly have?
 * <p>
 * Tags: Array
 * Similar Problems: (E) Majority Element
 *
 * https://gregable.com/2013/10/majority-vote-algorithm-find-majority.html
 * https://discuss.leetcode.com/topic/65042/my-understanding-of-boyer-moore-majority-vote
 *
 * Consider 3 cases:
 *
 * 1. there are no elements that appears more than n/3 times, then whatever the algorithm
 * got from 1st round wound be rejected in the second round.
 *
 * 2. there are only one elements that appears more than n/3 times, after 1st round one of
 * the candidate must be that appears more than n/3 times(<2n/3 other elements could only
 * pair out for <n/3 times), the other candidate is not necessarily be the second most frequent
 * but it would be rejected in 2nd round.
 *
 * 3. there are two elments appears more than n/3 times, candidates would contain both of
 * them. (<n/3 other elements couldn't pair out any of the majorities.)
 *
 * We would only think about the fully pairing situation. If the over one third majority
 * exists, it should be left after pairing. Why would we use three elements as a pair?
 * Because it makes sure that in fully pairing the count of majority element equals n/3.
 *
 */
public class MajorityElement2 {

    public List<Integer> majorityElement(int[] nums) {
        if (nums == null || nums.length == 0) {
            return Collections.emptyList();
        }
        int num1 = 0;
        int count1 = 0;
        int num2 = 0;
        int count2 = 0;
        List<Integer> res = new ArrayList<>();
        for (int n : nums) {
            if (n == num1) {
                count1++;
            } else if (n == num2) {
                count2++;
            } else if (count1 == 0) {
                num1 = n;
                count1 = 1;
            } else if (count2 == 0) {
                num2 = n;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }
        count1 = 0;
        count2 = 0;
        for (int n : nums) {
            if (n == num1) {
                count1++;
            } else if (n == num2) {
                count2++;
            }
        }
        if (count1 > nums.length / 3) {
            res.add(num1);
        }
        if (count2 > nums.length / 3) {
            res.add(num2);
        }
        return res;
    }

}
