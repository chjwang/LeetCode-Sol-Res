package com.freetymekiyan.algorithms.Other;

import java.util.ArrayList;
import java.util.List;

/**
 * Numbers can be regarded as product of its factors. For example,
 *
 * 8 = 2 x 2 x 2; = 2 x 4. Write a function that takes an integer n and return all possible
 * combinations of its factors.
 *
 * Note:
 *
 * Each combination’s factors must be sorted ascending, for example: The factors of 2 and 6 is [2,
 * 6], not [6, 2].
 *
 * You may assume that n is always positive.
 *
 * Factors should be greater than 1 and less than n.
 *
 * Examples:
 *
 * input: 1 output: []
 *
 * input: 37 output: []
 *
 * input: 12 output: [ [2, 6], [2, 2, 3], [3, 4] ]
 *
 * input: 32 output: [ [2, 16], [2, 2, 8], [2, 2, 2, 4], [2, 2, 2, 2, 2], [2, 4, 4], [4, 8] ]
 */
public class FactorCombinations {

    /**
     * 这道题给了我们一个正整数n，让我们写出所有的因子相乘的形式，而且规定了因子从小到大的顺序排列，那么对于这种需要
     * 列出所有的情况的题目，通常都是用回溯法来求解的，由于题目中说明了1和n本身不能算其因子，那么我们可以从2到n的平方根
     * 之间进行遍历，如果当前的数i可以被n整除，说明i是n的一个因子，我们将其存入一位数组out中，然后递归调用n/i，
     * 此时不从2开始遍历，而是从i遍历到n/i，停止的条件是当n等于1时，如果此时out中有因子，我们将这个组合存入结果res中
     *
     */
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        helper(res, new ArrayList<Integer>(), n, 2);
        return res;
    }

    private void helper(List<List<Integer>> res, List<Integer> item, int n, int start) {
        if (n == 1) {
            if (item.size() > 1) {
                res.add(new ArrayList<Integer>(item));
            }
            return;
        }
        for (int i = start; i <= n; i++) {
            if (n % i == 0) {
                item.add(i);
                helper(res, item, n / i, i);
                item.remove(item.size() - 1); // backtrack
            }
        }
    }
}

