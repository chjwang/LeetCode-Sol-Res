package com.freetymekiyan.algorithms.level.Medium;

/**
 * Given an unsorted array of integers, find the length of longest increasing subsequence. <p> For
 * example, Given [10, 9, 2, 5, 3, 7, 101, 18], The longest increasing subsequence is [2, 3, 7,
 * 101], therefore the length is 4. Note that there may be more than one LIS combination, it is only
 * necessary for you to return the length. <p> Your algorithm should run in O(n2) complexity. <p>
 * Follow up: Could you improve it to O(n log n) time complexity? <p> Tags: Dynamic Programming,
 * Binary Search Similar Problems: (M) Increasing Triplet Subsequence, (H) Russian Doll Envelopes
 */
public class LongestIncreasingSubsequence {

    /**
     * DP.
     *
     * 方法一：

     dp[i] 为 以 A[i]结尾的LIS，那么对于dp[i]有dp[i] =max( dp[j] + 1) [ 0<= j < i, nums[j] < nums[i]  ]

     效率为O(n^2)

     方法二：

     dp[i] 为 以 A[i]结尾的LIS ， g(i) = min(A[j]) (dp[j] = i) 即g(i)表示上升子序列为i，结尾最小的值。

     比如，1，2，4，3中A[3] = 3

     那么显然， g(1) <= g(2) <=……g(n)

     我们可以用二分搜索查找满足g(k) >= A[i]的第一个下标k，则dp[i] = k ，此时 A[i] <= g(k)  ， 而dp[i] =k，所以更新g(k) = A[i]

     * Use an array tails to store the minimum value of nums' LIS at a specific length.
     * How to get the value? Use binary search.
     *
     * Search for the insertion point of current number.
     * Update the number in tails.
     * If the insertion point equal to the current size, it means the array tails can be extended.
     * Then the length of Longest Increasing Subsequence can increase by 1.
     */
    public int lengthOfLIS(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0; // tails size (0 based)
        for (int x : nums) {
            int l = 0; // Binary search for the insertion point
            int r = size;
            while (l < r) {
                int m = l + (r - l) / 2;
                if (tails[m] < x) {
                    l = m + 1;
                } else {
                    r = m;
                }
            }
            tails[l] = x; // Update tails, x is smallest LIS value of length l+1
            if (l == size) { // x is larger than all elements, increase tails size
                size++;
            }
        }
        return size;
    }

    public int longestIncreasingSubsequence(int[] nums) {
        int[] minLast = new int[nums.length + 1];
        minLast[0] = Integer.MIN_VALUE;
        for (int i = 1; i <= nums.length; i++)
            minLast[i] = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            // find the first number in minLast > nums[i]
            int index = binarySearch(minLast, nums[i]);
            minLast[index] = nums[i];
        }

        for (int i = nums.length; i >= 1; i--) {
            if (minLast[i] != Integer.MAX_VALUE) return i;
        }

        return 0;
    }

    // find the first number > num
    private int binarySearch(int[] minLast, int num) {
        int start = 0, end = minLast.length - 1;
        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (minLast[mid] < num) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (minLast[start] > num) {
            return start;
        }
        return end;
    }

    /*
    思路
在1,3,5,2,8,4,6这个例子中，当到6时，我们一共可以有四种
(1)不同长度
(2)且保证该升序序列在同长度升序序列中末尾最小的升序序列

1
1,2
1,3,4
1,3,5,6
这些序列都是未来有可能成为最长序列的候选人。这样，每来一个新的数，我们便按照以下规则更新这些序列

- 如果nums[i]比所有序列的末尾都大，或等于最大末尾，说明有一个新的不同长度序列产生，我们把最长的序列复制一个，并加上这个nums[i]。
- 如果nums[i]比所有序列的末尾都小，说明长度为1的序列可以更新了，更新为这个更小的末尾。
- 如果在中间，则更新那个末尾数字刚刚大于等于自己的那个序列，说明那个长度的序列可以更新了。

比如这时，如果再来一个9，那就是第三种情况，更新序列为
1
1,2
1,3,4
1,3,5,6
1,3,5,6,9

如果再来一个3，那就是第二种情况，更新序列为
1
1,2
1,3,3
1,3,5,6

如果再来一个0，那就是第一种情况，更新序列为
0
1,2
1,3,3
1,3,5,6

前两种都很好处理，O(1)就能解决，主要是第三种情况，实际上我们观察直到6之前这四个不同长度的升序序列，他们末尾是递增的，
所以可以用二分搜索来找到适合的更新位置。

注意
二分搜索时如果在tails数组中，找到我们要插入的数，也直接返回那个结尾的下标，虽然这时候更新这个结尾没有意义，但少了些判断
简化了逻辑
     */

    public int longestIncreasingSubsequence2(int[] nums) {
        if(nums.length == 0) return 0;

        // len表示当前最长的升序序列长度（为了方便操作tails我们减1）
        int len = 0;

        // tails[i]表示长度为i的升序序列其末尾的数字
        int[] tails = new int[nums.length];
        tails[0] = nums[0];

        // 根据三种情况更新不同升序序列的集合
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < tails[0]) {
                tails[0] = nums[i];
            } else if (nums[i] >= tails[len]) {
                tails[++len] = nums[i];
            } else {
                // 如果在中间，则二分搜索
                tails[binarySearch(tails, 0, len, nums[i])] = nums[i];
            }
        }
        return len + 1;
    }

    private int binarySearch(int[] tails, int min, int max, int target){
        while (min <= max) {
            int mid = min + (max - min) / 2;
            if (tails[mid] == target) return mid;
            if (tails[mid] < target) min = mid + 1;
            if (tails[mid] > target) max = mid - 1;
        }
        return min;
    }

    /**
     * DP, O(n^2)
     *
     * lis[i]表示以nums[i]为结尾的最长递增子串的长度
     * 对于每一个nums[i]，我们从第一个数再搜索到i，如果发现某个数小于nums[i]，我们更新lis[i]，
     * 更新方法为dp[i] = max(dp[i], dp[j] + 1)，即比较当前dp[i]的值和那个小于num[i]的数的dp值加1的大小，
     * 我们就这样不断的更新dp数组，到最后dp数组中最大的值就是我们要返回的LIS的长度
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS2(int[] nums) {
        int n = nums.length;
        int lis[] = new int[n];
        int max = 0;

        /* Initialize LIS values for all indexes */
        for (int i = 0; i < n; i++) lis[i] = 1;

        /* Compute optimized LIS values in bottom up manner */
        for (int i = 1; i < n; i++)
            for (int j = 0; j < i; j++)
                if (nums[i] > nums[j] && lis[i] < lis[j] + 1) lis[i] = lis[j] + 1;

        /* Pick maximum of all LIS values */
        for (int i = 0; i < n; i++)
            if (max < lis[i]) max = lis[i];

        return max;
    }
}
