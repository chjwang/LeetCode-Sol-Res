package com.freetymekiyan.algorithms.level.Medium;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.
 *
 * Define a pair (u,v) which consists of one element from the first array and one element from the
 * second array.
 *
 * Find the k pairs (u1,v1),(u2,v2) ...(uk,vk) with the smallest sums.
 *
 * Example
 * 1: Given nums1 = [1,7,11], nums2 = [2,4,6],  k = 3
 * Return: [1,2],[1,4],[1,6]
 * The first 3 pairs are returned from the sequence: [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
 *
 * Example 2: Given nums1 = [1,1,2], nums2 = [1,2,3],  k = 2
 * Return: [1,1],[1,1]
 * The first 2 pairs are returned from the sequence: [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
 *
 * Example 3: Given nums1 = [1,2], nums2 = [3],  k = 3
 * Return: [1,3],[2,3]
 * All possible pairs are returned from the sequence: [1,3],[2,3]
 *
 * Tags: Heap Similar Problems: (M) Kth Smallest Element in a Sorted Matrix
 */
public class FindKPairsWithSmallestSums {

    /**
     * Min Heap.
     *
     * Suppose len(nums1) = m, len(nums2) = n, there can be m * n pairs.
     *
     * All integers in nums1 should start from the first integer in nums2.
     * So add the first k pairs to a priority queue pq based on the sum.
     * Then poll from pq and add it to result.
     *
     * Move the index in nums2 for the integer in nums1 one step further if possible.
     * Then add the new pair to pq.
     * Stop when we have k pairs.
     * https://discuss.leetcode.com/topic/50885/simple-java-o-klogk-solution-with-explanation/2
     */
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] + a[1] - b[0] - b[1]);
        List<int[]> res = new ArrayList<>();
        if (nums1.length == 0 || nums2.length == 0 || k == 0) {
            return res;
        }
        for (int i = 0; i < nums1.length && i < k; i++) {
            pq.offer(new int[]{nums1[i], nums2[0], 0});
        }
        while (k-- > 0 && !pq.isEmpty()) {
            int[] cur = pq.poll();
            res.add(new int[]{cur[0], cur[1]});
            if (cur[2] == nums2.length - 1) { // reached last element in nums2
                continue;
            }
            pq.offer(new int[]{cur[0], nums2[cur[2] + 1], cur[2] + 1}); // add next element in nums2 to pq
        }
        return res;
    }

    /**
     * This problem is similar to Super Ugly Number. The basic idea is using an array to track the
     * index of the next element in the other array.
     *
     * The best way to understand this solution is using an example such as nums1={1,3,11} and
     * nums2={2,4,8}.
     */
    public List<int[]> kSmallestPairs2(int[] nums1, int[] nums2, int k) {
        List<int[]> result = new ArrayList<>();
        k = Math.min(k, nums1.length * nums2.length);
        if (k == 0) return result;

        int[] partnerIndex = new int[nums1.length];
        while (k > 0) {
            int t = getSmallest(nums1, nums2, partnerIndex);

            int[] arr = {nums1[t], nums2[partnerIndex[t]]};
            result.add(arr);

            partnerIndex[t]++; // move smallest t's partner pointer up in nums2
            k--;
        }

        return result;
    }

    private int getSmallest(int[] nums1, int[] nums2, int[] idx) {
        int min = Integer.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < nums1.length; i++) {
            if (idx[i] < nums2.length && nums1[i] + nums2[idx[i]] < min) {
                minIndex = i;
                min = nums1[i] + nums2[idx[i]];
            }
        }
        return minIndex;
    }
}
