package com.freetymekiyan.algorithms.level.Medium;

import java.util.*;

/**
 * Implement next permutation, which rearranges numbers into the
 * lexicographically next greater permutation of numbers.
 * 
 * If such arrangement is not possible, it must rearrange it as the lowest
 * possible order (ie, sorted in ascending order).
 * 
 * The replacement must be in-place, do not allocate extra memory.
 * 
 * Here are some examples. Inputs are in the left-hand column and its
 * corresponding outputs are in the right-hand column.
 * 1,2,3 → 1,3,2
 * 3,2,1 → 1,2,3
 * 1,1,5 → 1,5,1
 * 
 * Tags: Array
 */
class NextPermutation {
    public static void main(String[] args) {
        
    }
    
    /**
     * O(n) Time
     * Move pointer to second last element of ascending order 
     * From that index, find the farthest element that is bigger
     * Swap these two elements
     * Reverse the order from the next index
     *
     这道题是给定一个数组和一个排列，求下一个排列。算法上其实没有什么特别的地方，主要的问题是经常不是一见到这个题就能马上理清思路。
     下面我们用一个例子来说明，比如排列是(2,3,6,5,4,1)，求下一个排列的基本步骤是这样：
     1) 先从后往前找到第一个不是依次增长的数，记录下位置p (partition number)。比如例子中的3，对应的位置是1;
     2) 接下来分两种情况：
       (1) 如果上面的数字都是依次增长的，那么说明这是最后一个排列，下一个就是第一个，其实把所有数字反转过来即可
           (比如(6,5,4,3,2,1)下一个是(1,2,3,4,5,6));
       (2) 否则，如果p存在，从end开始往front找，找找找，找到第一个比p大的数(change number)，然后两个调换位置，比如例子中的4。
           调换位置后得到(2,4,6,5,3,1)。最后把p之后的所有数字倒序，比如例子中得到(2,4,1,3,5,6), 这个即是要求的下一个排列。
     以上方法中，最坏情况需要扫描数组三次，所以时间复杂度是O(3*n)=O(n)，空间复杂度是O(1)

     Analysis

     The steps to solve this problem:

     1) scan from right to left, find the first element that is less than its previous one.
     4 5 6 3 2 1
       |
       p

     2) scan from right to left, find the first element that is greater than p.
     4 5 6 3 2 1
         |
         q

     3) swap p and q
     4 5 6 3 2 1
     swap
     4 6 5 3 2 1

     4) reverse elements [p+1, nums.length]
     4 6 1 2 3 5

     */
    public void nextPermutation(int[] num) {
        if (num == null || num.length < 2) return;

        for (int i = num.length - 2; i >= 0; i--) {
            // find the partition number (the first number which violates the increasing trend - counting backwards)
            if (num[i] < num[i + 1]) { // i is partition number
                int j = num.length - 1;

                // find the change number (the first number greater than the partition number - counting backwards)
                for (; j > i; j--)
                    if (num[j] > num[i]) // j is change number
                        break;

                // swap partition number with change number
                swap(num, i, j);

                // reverse all numbers on right side of partition index to get the minimum starting with patition index
                reverse(num, i + 1);

                return;
            }
        }
        reverse(num, 0); 
        return;
    }
    
    private void swap(int[] num, int i, int j) {
        int t = num[i];
        num[i] = num[j];
        num[j] = t;
    }
    
    private void reverse(int[] num, int s) {
        int e = num.length - 1;
        while (s < e) {
            swap(num, s, e);
            s++;
            e--;
        }
    }
}
