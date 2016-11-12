package com.freetymekiyan.algorithms.level.Medium;

import java.util.List;
import java.util.ArrayList;

/**
 * Given two integers n and k, return all possible combinations of k numbers
 * out of 1 ... n.
 * 
 * For example,
 * If n = 4 and k = 2, a solution is:
 * [
 *   [2,4],
 *   [3,4],
 *   [2,3],
 *   [1,2],
 *   [1,3],
 *   [1,4],
 * ]
 * 
 * Tags: Backtracking
 */
class Combinations {
    public static void main(String[] args) {
        List<List<Integer>> lists = combine(4, 2);
//        List<List<Integer>> lists = dfs(4, 2);
        for (List<Integer> l : lists) {
            System.out.println(l.toString());
        }
    }
    
    /**
     * Ascending order, track start
     */
    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        combine(n, k, 1, new ArrayList<Integer>(), result); // note that start is 1
        return result;
    }

    public static void combine(int n, int k, int start, List<Integer> comb, List<List<Integer>> result) {
        if (k == 0) { // k is remaining to be added, k = 0 is a solution
            result.add(comb);
            return;
        }
        for (int i = start; i <= n; i++) { // note that from start to n, <=
            List<Integer> copy = new ArrayList<>(comb);
            copy.add(i); // don't forget to add i to copy
            combine(n, k - 1, i + 1, copy, result); // choose k-1 from i+1 to n
        }
    }

    public static List<List<Integer>> dfs(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(n, k, 1, new ArrayList<Integer>(), result);
        return result;
    }

    public static void dfs(int n, int k, int level, List<Integer> comb, List<List<Integer>> result) {
        if (comb.size() == k) {
            result.add(comb);
            return;
        }
        if(comb.size() > k)  // consider this check to save run time ?
            return;

        for(int i = level; i <= n; ++i) {
            comb.add(i);
            dfs(n, k, i+1, comb, result);
            comb.remove(comb.size() - 1); //backtrack
        }
    }

    /**
     * From back to start
     */
    public static List<List<String>> combine2(int n, int k) {
        List<List<String>> result = new ArrayList<List<String>>();
        combine(n, k, new ArrayList<String>(), result);
        return result;
    }
    
    public static void combine(int n, int k, List<String> comb, List<List<String>> result) {
        if (k == 0) { // k is remaining to be added
            result.add(comb); 
            return;
        }
        if (n <= k) { // choose all
            for (int i = n; i > 0; i--) {
                comb.add(i + "");
            }
            result.add(comb);
            return;
        }
        // with n, choose k-1 from n-1
        List<String> combWithN = new ArrayList<String>(comb);
        combWithN.add(n + "");
        combine(n - 1, k - 1, combWithN, result);
        // without n, choose k from n-1
        combine(n - 1, k, comb, result);
    }
}
