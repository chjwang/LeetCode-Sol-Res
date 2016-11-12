package com.freetymekiyan.algorithms.level.Medium;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not
 * the kth distinct element.
 *
 * For example, Given [3,2,1,5,6,4] and k = 2, return 5.
 *
 * Note: You may assume k is always valid, 1 ≤ k ≤ array's length.
 *
 * Tags: Divide and Conquer, Heap
 * Similar Problems: (M) Wiggle Sort II
 */
public class KthLargestElementInArray {

    /**
     * Priority queue
     */
    public int findKthLargest1(int[] nums, int k) {
        Queue<Integer> pq = new PriorityQueue<>(nums.length, Collections.reverseOrder());
        for (int n : nums) pq.add(n);
        int res = 0;
        for (int i = 0; i < k; i++) res = pq.poll();
        return res;
    }

    /**
     * QuickSelect
     * Use partition algorithm in Quick Sort
     * Average case time is O(n), worst case time is O(n^2).
     */
    public int findKthLargest2(int[] nums, int k) {
        k = nums.length - k;
        int l = 0;
        int r = nums.length - 1;
        while (l < r) {
            final int j = partition(nums, l, r);
            if (j < k)
                l = j + 1;
            else if (j > k)
                r = j - 1;
            else
                break;
        }
        return nums[k];
    }

    private int partition(int nums[], int l, int r) {
        int i = l, j = r;
        int pivot = nums[l];

        while (i <= j) {
            while (nums[i] < pivot)
                i++;
            while (nums[j] > pivot)
                j--;
            if (i <= j) {
                swap(nums, i, j);
                i++;
                j--;
            }
        }
        return i;
    }

    /**
     * Average case time is O(n), worst case time is O(n^2).
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest3(int[] nums, int k) {
        if (k < 1 || nums == null) {
            return 0;
        }
        return getKth(nums.length - k +1, nums, 0, nums.length - 1);
    }

    public int getKth(int k, int[] nums, int start, int end) {
        int pivot = nums[end];
        int i = start, j = end;

        while (true) {
            while (nums[i] < pivot && i < j) i++;
            while (nums[j] >= pivot && i < j) j--;
            if (i == j) break;
            swap(nums, i, j);
        }

        swap(nums, i, end);

        if (k == i + 1) {
            return pivot;
        } else if (k < i + 1) {
            return getKth(k, nums, start, i - 1);
        } else {
            return getKth(k, nums, i + 1, end);
        }
    }

    private void swap(int[] a, int i, int j) {
        final int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void main(String[] args) {
        KthLargestElementInArray k = new KthLargestElementInArray();
        int res = k.findKthLargest1(new int[]{-1, 2, 0}, 2);
        System.out.println(res);
        res = k.findKthLargest2(new int[]{2, 1}, 1);
        System.out.println(res);
    }
}
