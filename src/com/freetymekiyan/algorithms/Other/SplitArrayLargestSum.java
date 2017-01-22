package com.freetymekiyan.algorithms.Other;

/**
 * Given an array which consists of non-negative integers and an integer m, you can split the array
 * into m non-empty continuous subarrays.
 *
 * Write an algorithm to minimize the largest sum among these m subarrays.
 *
 * Note:
 * Given m satisfies the following constraint: 1 ≤ m ≤ length(nums) ≤ 14,000.
 *
 * Examples:
 *
 * Input:
 * nums = [7,2,5,10,8]
 * m = 2
 *
 * Output:
 * 18
 *
 * Explanation:
 * There are four ways to split nums into two subarrays.
 * The best way is to split it into [7,2,5] and [10,8],
 * where the largest sum among the two subarrays is only 18.
 */
public class SplitArrayLargestSum {
    /*
We can use binary search to solve this problem.

So, assume that the maximum value for all sub-array is x, so, we can greedily choose each sub-array
in O(n) so that the sum of each subarray is maximum and less than or equals to x.

After creating all subarray, if the number of sub-array is less than or equal to k, so x is one
possible solution, or else, we increase x.

Pseudocode:

int start = Max_Value_In_Array;
int end = Max_Number; -- use sum of array instead

while(start <= end)
   int mid = (start + end)/2;
   int subSum = 0;
   int numberOfSubArray = 1;
   for(int i = 0; i < n; i++){
      if(subSum + data[i] > mid){
          subSum = data[i];
          numberOfSubArray++;
      }else{
          subSum += data[i];
      }
   }
   if(numberOfSubArray <= k) // x is too big
       end = mid - 1;
   else
       start = mid + 1; // x is too small
Time complexity O(n log k) with k is the maximum sum possible.
     */

    /*
    二分枚举答案（Binary Search）

    将数组nums拆分成m个子数组，每个子数组的和不小于max of nums (or we can use sum(nums) / m)，不大于sum(nums)

    又因为数组nums中只包含非负整数，因此可以通过二分法在上下界内枚举答案。

    时间复杂度O(n * log m)，其中n是数组nums的长度，m为数组nums的和
     */
    public int splitArray(int[] nums, int m) {
        int high = 0, low = 0, mid = 0, ans = 0;
        for (int num : nums) {
            high += num;
            low = Math.max(num, low);
        }

        if (m == 1)
            return high;
        if (m == nums.length)
            return low;
        ans = high;
        while (low <= high) {
            mid = low + (high - low) / 2;

            boolean valid = true; // true means valid partition
            int sum = nums[0];
            int count = 1;
            for (int i = 1; i < nums.length; i++) {
                sum += nums[i];
                if (sum > mid) {
                    count++; // start new subarrary, increment subarray count
                    sum = nums[i]; // new subarrary starts from i now
                }
                if (count > m) { // count of subarray too big
                    valid = false;
                    break;
                }
            }

            if (valid) {
                high = mid - 1; // try smaller max
                ans = mid;
            } else {
                low = mid + 1; // try bigger max
            }
        }
        return ans;
    }

    // https://scottduan.gitbooks.io/leetcode-review/content/split_array_largest_sum.html
    public int splitArray2(int[] nums, int m) {
        long sum = 0;
        int max = 0;
        for (int num : nums) {
            max = Math.max(max, num);
            sum += num;
        }
        return (int) binary(nums, m, sum, max);
    }

    private long binary(int[] nums, int m, long high, long low) {
        long mid = 0;
        while (low < high) {
            mid = (high + low) / 2;
            if (valid(nums, m, mid)) {
                //System.out.println(mid);
                high = mid; // try lower max to see if it's still valid
            } else {
                low = mid + 1; // max too small, try higher max
            }
        }
        return high;
    }

    private boolean valid(int[] nums, int m, long max) {
        int cur = 0;
        int count = 1;
        for (int num : nums) {
            cur += num;
            if (cur > max) {
                cur = num; // start new subarray
                count++;
                if (count > m) { // too many subarrays, which means max is too small
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * DP的话实现起来稍微麻烦一点，需要保存一个2d的array，大小为n*m，每个位置（i，j）保存从数列位置i开始到数列尾端，
     * 分割成j份的largest sum。同时空间和时间复杂度在m比较大的情况下比较高，并不是很建议勇这种方法implement，
     * 但是可以作为一种思路。类似的题目可以参考Burst Balloons。https://discuss.leetcode.com/topic/66289/java-dp
     *
     * @param nums
     * @param m
     * @return
     */
    public int splitArrayDP(int[] nums, int m) {
        int[] s = new int[nums.length];
        s[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            s[i] = nums[i] + s[i - 1];
        }

        for (int k = 2; k <= m; k++) {
            for (int i = nums.length - 1; i >= k - 1; i--) {
                int min = Integer.MAX_VALUE;
                int left = nums[i];
                for (int p = i - 1; p >= k - 2; p--) {
                    min = Math.min(min, Math.max(s[p], left));
                    left += nums[p];
                    if (left >= min) {
                        break;
                    }
                }
                s[i] = min;
            }
        }

        return s[nums.length - 1];
    }

    /**
     * DP solution. This is obviously not as good as the binary search solutions; but it did pass OJ.

     dp[s,j] is the solution for splitting subarray n[j]...n[L-1] into s parts.

     dp[s+1,i] = min{ max(dp[s,j], n[i]+...+n[j-1]) }, i+1 <= j <= L-s

     This solution does not take advantage of the fact that the numbers are non-negative (except to
     break the inner loop early). That is a loss. (On the other hand, it can be used for the problem
     containing arbitrary numbers)

     * @param nums
     * @param m
     * @return
     */
    public int splitArrayDP2(int[] nums, int m) {
        int L = nums.length;
        int[] S = new int[L + 1];
        S[0] = 0;
        for (int i = 0; i < L; i++)
            S[i + 1] = S[i] + nums[i];

        int[] dp = new int[L];
        for (int i = 0; i < L; i++)
            dp[i] = S[L] - S[i];

        for (int s = 1; s < m; s++) {
            for (int i = 0; i < L - s; i++) {
                dp[i] = Integer.MAX_VALUE;
                for (int j = i + 1; j <= L - s; j++) {
                    int t = Math.max(dp[j], S[j] - S[i]);
                    if (t <= dp[i])
                        dp[i] = t;
                    else
                        break;
                }
            }
        }

        return dp[0];
    }
}
