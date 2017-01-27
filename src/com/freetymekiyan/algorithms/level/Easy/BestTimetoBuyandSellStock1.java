package com.freetymekiyan.algorithms.level.Easy;

/**
 * Best Time to Buy and Sell Stock
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * If you were only permitted to complete at most one transaction (ie, buy one and sell one share of the stock), design
 * an algorithm to find the maximum profit.
 * Tags:  Array Dynamic Programming
 * Similar Problems:  (M) Maximum Subarray (M) Best Time to Buy and Sell Stock II (H) Best Time to Buy and Sell Stock
 * III (H) Best Time to Buy and Sell Stock IV (M) Best Time to Buy and Sell Stock with Cooldown
 * O(n) time, O(1) space
 *
 * @author chenshuna
 */

class BestTimetoBuyandSellStock_shuna {

    /**
     限制了只能买卖一次。于是要尽可能在最低点买入最高点抛出。这里的一个隐含的限制是抛出的时间必须在买入的时间之后。
     所以找整个数组的最大最小值之差的方法未必有效，因为很可能最大值出现在最小值之前。
     但是可以利用类似思路，在扫描数组的同时来更新一个当前最小值minPrice。这样能保证当扫到i时，minPrices必然是i之前的最小值。

     当扫到i时：

     如果prices[i] < minPrice，则更新minPrice = prices[i]。并且该天不应该卖出。
     如果prices[i] >= minPrice，则该天可能是最好的卖出时间，计算prices[i] - minPrice，并与当前的单笔最大利润比较更新。

     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int max = 0;
        int minPrice = prices[0];
        for (int i = 1; i < prices.length; i++) {
            minPrice = Math.min(minPrice, prices[i]);
            max = Math.max(max, prices[i] - minPrice);
        }
        return max;
    }

    public static void main(String arg[]) {
        int[] prices = {2, 5, 8, 9, 1, 6};
        System.out.print(maxProfit(prices));
    }
}