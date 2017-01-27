package com.freetymekiyan.algorithms.level.Hard;

import java.util.Arrays;

/**
 * Say you have an array for which the ith element is the price of a given
 * stock on day i.
 * <p>
 * Design an algorithm to find the maximum profit. You may complete at most k
 * transactions.
 * <p>
 * Note:
 * You may not engage in multiple transactions at the same time (ie, you must
 * sell the stock before you buy again).
 * <p>
 * Tags: DP
 */
class BestTimeStock4 {

    public static void main(String[] args) {
        // 2, [3,2,6,5,0,3]
        // 2, [3,3,5,0,0,3,1,4]
        BestTimeStock4 b = new BestTimeStock4();
        int[] A = {3, 3, 5, 0, 0, 3, 1, 4};
        int[] B = {3, 2, 6, 5, 0, 3};
        System.out.println(b.maxProfitOpt(2, A));
        System.out.println(b.maxProfit(2, A));
        System.out.println(b.maxProfitOpt(2, B));
        System.out.println(b.maxProfit(2, B));
    }

    /**
     * DP, bottom-up, O(kn) Time, O(n) Space
     * If k >= n/2, we can have transactions any time, O(n). This is same as BestStock 2.
     *
     * dp[k][i+1] represents the max profit of using [0, i] and k transactions. It can be:
     *      dp[k-1][i+1] (add 1 more transaction changes nothing)
     *      dp[k][i] (prices[i] changes nothing)
     *      prices[i] + max(dp[k-1][j] - prices[j]), 0 <= j < i,
     *          meaning prices[i] will change the max profit, find the biggest from k-1 transactions and add prices[i]
     *
     * dp[k][i+1] = max(dp[k-1][i+1],
     *                  dp[k][i],
     *                  prices[i] + max(dp[k-1][j] - prices[j])), (0 <= j < i)
     */
    public int maxProfitOpt(int k, int[] prices) {
        if (prices == null || prices.length < 2 || k == 0) {
            return 0;
        }

        int n = prices.length;
        if (k >= n/2) { // as many transactions as possible
            return greedy(prices);
        }

        int[] cur = new int[n + 1];
        for (int i = 1; i <= k; i++) {
            int curMax = Integer.MIN_VALUE;
            for (int j = 0; j < n; j++) {
                int temp = cur[j + 1];
                cur[j + 1] = Math.max(Math.max(cur[j + 1], cur[j]), prices[j] + curMax);
                System.out.print(curMax + "|");
                curMax = Math.max(curMax, temp - prices[j]);
                System.out.println(curMax);
            }
            System.out.println(Arrays.toString(cur));
        }
        return cur[n];
    }

    private int greedy(int[] prices) {
        int res = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                res += prices[i] - prices[i - 1];
            }
        }
        return res;
    }

    /**
     * DP, bottom-up, O(kn) Time, O(kn) Space
     */
    public int maxProfit(int k, int[] prices) {
        if (prices == null || prices.length < 2 || k == 0) {
            return 0;
        }
        int n = prices.length;
        if (k >= n/2) {
            return greedy(prices);
        }

        int[][] dp = new int[k + 1][n + 1];
        for (int i = 1; i <= k; i++) {
            int curMax = Integer.MIN_VALUE;
            for (int j = 0; j < n; j++) {
                dp[i][j+1] = Math.max(Math.max(dp[i-1][j+1], dp[i][j]), prices[j] + curMax);
                curMax = Math.max(curMax, dp[i-1][j] - prices[j]);
            }
        }
        return dp[k][n];
    }

    /*
     分析：特殊动态规划法。

     传统的动态规划我们会这样想，到第i天时进行j次交易的最大收益，要么等于到第i-1天时进行j次交易的最大收益（第i天价格低于第i-1天的价格），
     要么等于到第i-1天时进行j-1次交易，然后第i天进行一次交易（第i天价格高于第i-1天价格时）。
     于是得到动规方程如下（其中diff = prices[i] – prices[i – 1]）：
         profit[i][j] = max(profit[i – 1][j], profit[i – 1][j – 1] + diff)

     看起来很有道理，但其实不对，为什么不对呢？
     因为diff是第i天和第i-1天的差额收益，如果第i-1天当天本身也有交易呢（也就是说第i-1天刚卖出了股票，然后又买入等到第i天再卖出），
     那么这两次交易就可以合为一次交易，这样profit[i – 1][j – 1] + diff实际上只进行了j-1次交易，而不是最多可以的j次，
     这样得到的最大收益就小了。

     那么怎样计算第i天进行交易的情况的最大收益，才会避免少计算一次交易呢？
     我们用一个局部最优解和全局最有解表示到第i天进行j次的收益，这就是该动态规划的特殊之处。

     用local[i][j]表示到达第i天时，最多进行j次交易的局部最优解；
     用global[i][j]表示到达第i天时，最多进行j次的全局最优解。
     它们二者的关系如下（其中diff = prices[i] – prices[i – 1]）：
        local[i][j] = max(global[i – 1][j – 1] , local[i – 1][j] + diff)
        global[i][j] = max(global[i – 1][j], local[i][j])

     local[i][j]和global[i][j]的区别是：
     local[i][j]意味着在第i天一定有交易（卖出）发生，当第i天的价格高于第i-1天（即diff > 0）时，那么可以把这次交易
     （第i-1天买入第i天卖出）跟第i-1天的交易（卖出）合并为一次交易，即local[i][j]=local[i-1][j]+diff；
     当第i天的价格不高于第i-1天（即diff<=0）时，那么local[i][j]=global[i-1][j-1]+diff，而由于diff<=0，所以
     可写成local[i][j]=global[i-1][j-1]。

     global[i][j]就是我们所求的前i天最多进行k次交易的最大收益，可分为两种情况：
        如果第i天没有交易（卖出），那么global[i][j]=global[i-1][j]；
        如果第i天有交易（卖出），那么global[i][j]=local[i][j]。
     */
    public int maxProfitA(int k, int[] prices) {
        if (prices.length < 2) return 0;

        int n = prices.length;
        if (k >= n/2) return greedy(prices);

        int[][] local = new int[n][k + 1];
        int[][] global = new int[n][k + 1];

        for (int i = 1; i < n ; i++) {
            int diff = prices[i] - prices[i - 1];

            for (int j = 1; j <= k; j++) {
                local[i][j] = Math.max(global[i-1][j-1], local[i-1][j] + diff);
                global[i][j] = Math.max(global[i-1][j], local[i][j]);
            }
        }

        return global[n - 1][k];
    }

    public int maxProfitB(int k, int[] prices) {
        if (prices.length < 2) return 0;

        int n = prices.length;
        if (k >= n/2) return greedy(prices);

        int[] local = new int[k + 1];
        int[] global = new int[k + 1];

        for (int i = 1; i < n ; i++) {
            int diff = prices[i] - prices[i-1];

            for (int j = k; j > 0; j--) {
                local[j] = Math.max(global[j-1], local[j] + diff);
                global[j] = Math.max(global[j], local[j]);
            }
        }

        return global[k];
    }
}
