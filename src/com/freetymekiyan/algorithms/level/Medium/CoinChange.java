package com.freetymekiyan.algorithms.level.Medium;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * You are given coins of different denominations and a total amount of money amount. Write a
 * function to compute the fewest number of coins that you need to make up that amount. If that
 * amount of money cannot be made up by any combination of the coins, return -1.
 * <p>
 * Example 1: coins = [1, 2, 5], amount = 11 return 3 (11 = 5 + 5 + 1)
 * <p>
 * Example 2: coins = [2], amount = 3 return -1.
 * <p>
 * Note: You may assume that you have an infinite number of each kind of coin.
 *
 * <p>
 * Tags: Dynamic Programming
 */
public class CoinChange {

    public static void main(String[] args) {
        int[] coins = new int[] {1,2,5};
        int amount = 11;

//        coins = new int[] {2};
//        coins = new int[] {1, 2, 3};
//        amount = 4;

        CoinChange cc = new CoinChange();
//        int res = cc.coinChange(coins, amount);
        int res = cc.coinChange4(coins, amount);
//        int res = cc.coinChangeTotalCount2(coins, coins.length, amount);
        for (int c : coins) {
            System.out.print(c + " ");
        }
        System.out.println(" - " + amount);
        System.out.println(res);
    }

    /**
     * O(n^2)
     *
     * DP, bottom up
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        int[] res = new int[amount + 1];
        Arrays.fill(res, 1, amount + 1, amount + 1); // amount + 1 is an impossible max value
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                res[i] = Math.min(res[i], res[i - coin] + 1);
            }
        }
        return res[amount] == amount+1 ? -1 : res[amount];
    }

    /**
     * O(n^2)
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange1(int[] coins, int amount) {
        if(amount==0) return 0;

        int[] dp = new int [amount+1];
        dp[0] = 0; // do not need any coin to get 0 amount
//        Arrays.fill(dp, 1, amount + 1, Integer.MAX_VALUE);
        for(int i=1; i<=amount; i++)
            dp[i] = Integer.MAX_VALUE;

        for (int i=0; i<=amount; i++){
            for (int coin: coins){
                if (i+coin <= amount){
                    if (dp[i] == Integer.MAX_VALUE){
                        dp[i+coin] = dp[i];
                    } else {
                        dp[i+coin] = Math.min(dp[i+coin], dp[i]+1);
                    }
                }
            }
        }

        if(dp[amount] == Integer.MAX_VALUE)
            return -1;

        return dp[amount];
    }

    public int coinChange2(int[] coins, int amount) {
        if (amount < 1) return 0;
        int[] dp = new int[amount + 1];
        int sum = 0;

        // Modification.
        Arrays.sort(coins);

        while (++sum <= amount) {
            int min = -1;
            for (int coin : coins) {
                // Modification
                if (sum < coin) break;

                if (dp[sum - coin] != -1) {
                    int temp = dp[sum - coin] + 1;
                    min = min < 0 ? temp : (temp < min ? temp : min);
                }
            }
            dp[sum] = min;
        }
        return dp[amount];
    }

    /**
     * The idea is very classic dynamic programming: think of the last step we take.
     *
     * Suppose we have already found out the best way to sum up to amount a, then for the last step,
     * we can choose any coin type which gives us a remainder r where r = a-coins[i] for all i's.
     *
     * For every remainder, go through exactly the same process as before until either the remainder
     * is 0 or less than 0 (meaning not a valid solution).
     *
     * With this idea, the only remaining detail is to store the minimum number of coins needed to
     * sum up to r so that we don't need to recompute it over and over again.
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange3(int[] coins, int amount) {
        if (amount < 1)
            return 0;
        return helper(coins, amount, new int[amount]);
    }

    /**
     * DP, top down
     *
     * @param coins
     * @param rem remaining amount after the last step
     * @param count count[i-1]: minimum number of coins to sum up to i
     * @return
     */
    private int helper(int[] coins, int rem, int[] count) {
        if (rem < 0)
            return -1; // not valid
        if (rem == 0)
            return 0; // completed
        if (count[rem - 1] != 0)
            return count[rem - 1]; // already computed, so reuse

        int min = Integer.MAX_VALUE;
        for (int coin : coins) {
            int res = helper(coins, rem - coin, count);
            if (res >= 0 && res < min)
                min = 1 + res;
        }
        count[rem - 1] = (min == Integer.MAX_VALUE) ? -1 : min;
        return count[rem - 1];
    }

    /**
     * BFS O(n^3)
     *
     * Most dynamic programming problems can be solved by using BFS. We can view this problem as
     * going to a target position with steps that are allowed in the array coins.
     *
     * We maintain two queues: one of the amount so far and the other for the minimal steps.
     *
     * The time is too much because of the contains method take n and total time is O(n^3).
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange4(int[] coins, int amount) {
        if (amount == 0)
            return 0;

        LinkedList<Integer> amountQueue = new LinkedList<>();
        LinkedList<Integer> stepQueue = new LinkedList<>();

        // to get 0, 0 step is required
        amountQueue.offer(0);
        stepQueue.offer(0);

        while (amountQueue.size() > 0) {
            int temp = amountQueue.poll();
            int step = stepQueue.poll();

            if (temp == amount) // reached target amount
                return step;

            for (int coin : coins) {
                if (temp > amount) {
                    continue;
                } else if (!amountQueue.contains(temp + coin)) {
                    amountQueue.offer(temp + coin);
                    stepQueue.offer(step + 1);
                }
            }
        }

        return -1;
    }

    /**
     * http://www.geeksforgeeks.org/dynamic-programming-set-7-coin-change/
     *
     * Given a value N, if we want to make change for N cents, and we have infinite supply of each
     * of S = { S1, S2, .. , Sm} valued coins, how many ways can we make the change? The order of
     * coins doesn’t matter.

     For example, for N = 4 and S = {1,2,3}, there are four solutions: {1,1,1,1},{1,1,2},{2,2},{1,3}.
     So output should be 4. For N = 10 and S = {2, 5, 3, 6}, there are five solutions: {2,2,2,2,2},
     {2,2,3,3}, {2,2,6}, {2,3,5} and {5,5}. So the output should be 5.

     1) Optimal Substructure

     To count total number solutions, we can divide all set solutions in two sets.
     1) Solutions that do not contain mth coin (or Sm).
     2) Solutions that contain at least one Sm.
     Let count(S[], m, n) be the function to count the number of solutions, then it can be written as
     sum of count(S[], m-1, n) and count(S[], m, n-Sm).

     Therefore, the problem has optimal substructure property as the problem can be solved using
     solutions to subproblems.

     2) Overlapping Subproblems
     Following is a simple recursive implementation of the Coin Change problem. The implementation
     simply follows the recursive structure mentioned above.

     C() --> count()
          C({1,2,3}, 5)
      /                 \
     /                   \
     C({1,2,3}, 2)                 C({1,2}, 5)
     /       \                        /         \
     /        \                     /           \
     C({1,2,3}, -1)  C({1,2}, 2)        C({1,2}, 3)    C({1}, 5)
     /     \            /    \            /     \
     /        \          /      \          /       \
     C({1,2},0)  C({1},2)   C({1,2},1) C({1},3)    C({1}, 4)  C({}, 5)
     / \      / \       / \        /     \
     /   \    /   \     /   \      /       \
     .      .  .     .   .     .   C({1}, 3) C({}, 4)
     /  \
     /    \
     .      .

     It should be noted that the above function computes the same subproblems again and again.
     See the following recursion tree for S = {1, 2, 3} and n = 5.
     The function C({1}, 3) is called two times. If we draw the complete tree, then we can see that
     there are many subproblems being called more than once.

     Since same suproblems are called again, this problem has Overlapping Subprolems property.
     So the Coin Change problem has both properties (see this and this) of a dynamic programming
     problem. Like other typical Dynamic Programming(DP) problems, recomputations of same subproblems
     can be avoided by constructing a temporary array table[][] in bottom up manner.

     f(n, k): number of ways of making change for n cents, using only the first k+1 types of coins.

     f(n, k) = 0, n < 0 || k < 0
     f(n, k) = 1, n == 0
     f(n, k) = f(n, k-1) + f(n-C[k], k), else

     * @param coins
     * @param m
     * @param amount
     * @return
     */
    public int coinChangeTotalCount(int[] coins, int m, int amount) {
        // If amount is 0 then there is 1 solution (do not include any coin)
        if (amount == 0)
            return 1;

        // If amount is less than 0 then no solution exists
        if (amount < 0)
            return 0;

        // If there are no coins and amount is greater than 0, then no solution exist
        if (m <=0 && amount >= 1)
            return 0;

        // count is sum of solutions (i) including coins[m-1] (ii) excluding coins[m-1]
        int sub1 = coinChangeTotalCount(coins, m - 1, amount);
        int sub2 = coinChangeTotalCount(coins, m, amount-coins[m-1] );
        return sub1 + sub2;
    }

    /**
     * DP
     * O(m*n)
     *
     * @param coins
     * @param m
     * @param amount
     * @return
     */
    public int coinChangeTotalCount2(int[] coins, int m, int amount) {
        // table[i] will be storing the number of solutions for
        // value i. We need n+1 rows as the table is consturcted
        // in bottom up manner using the base case (n = 0)
        int[] table = new int[amount+1];

        // Initialize all table values as 0
        Arrays.fill(table, 0, amount+1, 0);

        // Base case (If given value is 0)
        table[0] = 1;

        // Pick all coins one by one and update the table[] values
        // after the index greater than or equal to the value of the
        // picked coin
        for(int i=0; i<m; i++)
            for(int j=coins[i]; j<=amount; j++)
                table[j] += table[j-coins[i]];

        return table[amount];
    }

    /* Further, what if we need to print out all solutions.

    Basically, you move from largest to smallest denominations.
    Recursively,

    You have a current total to fill, and a largest denomination (with more than 1 left). If there
    is only 1 denomination left, there is only one way to fill the total. You can use 0 to k copies
    of your current denomination such that k * cur denomination <= total.

    For 0 to k, call the function with the modified total and new largest denomination.

    Add up the results from 0 to k. That's how many ways you can fill your total from the current
    denomination on down. Return this number.
    */

    /**
     *
     * @param start start index
     * @param denominations
     * @param N remaining amount
     * @param vals solution array recording number of coins at each index
     */
    // find the number of ways to reach a total with the given number of combinations
    public static void printAllCoinSolutions(int start, int[] denominations, int N, int[] vals) {
        if (N == 0) {
            System.out.println(Arrays.toString(vals));
            return;
        }

        if (start == (denominations.length))
            return;

        int currDenom = denominations[start];
        for (int i = 0; i <= (N / currDenom); i++) {
            vals[start] = i;
            printAllCoinSolutions(start + 1, denominations, N - i * currDenom, vals);
        }
    }
}
