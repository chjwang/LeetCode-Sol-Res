package com.freetymekiyan.algorithms.level.Hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Given a collection of numbers that might contain duplicates, return all
 * possible unique permutations.
 * 
 * For example,
 * [1,1,2] have the following unique permutations:
 * [1,1,2], [1,2,1], and [2,1,1].
 * 
 * Tags: Backtracking
 */
class Permutations2 {
    public static void main(String[] args) {
        List<List<Integer>> res = permuteUnique(new int[]{1, 2, 1});

        System.out.println("permuteUnique");
        for (List<Integer> l : res) System.out.println(l);

        System.out.println("permuteUniqueB");
        res = permuteUniqueB(new int[]{1, 2, 1});
        for (List<Integer> l : res) System.out.println(l);

        System.out.println("permuteUnique3");
        res = permuteUnique3(new int[]{1, 2, 1});
        for (List<Integer> l : res) System.out.println(l);
    }
    
    /**
     * Same idea as Permutation 1 except we skip if duplicate of current element
     * is found in previous sequence
     */
    public static  List<List<Integer>> permuteUnique(int[] num) {
        List<List<Integer>> res = new ArrayList<>();
        if (num == null || num.length == 0) return res;
        Arrays.sort(num);
        permute(num, 0, res);
        return res;
    }
    
    public static void permute(int[] num, int pos, List<List<Integer>> res) {
        if (pos == num.length) {
            List<Integer> row = new ArrayList<>();
            for (int a : num)
                row.add(a);
            res.add(row);
            return;
        }
        for (int i = pos; i < num.length; i++) {
            // skip if we have duplicates of current element before i
            boolean skip = false;
            for (int j = pos; j < i; j++) {
                if (num[j] == num[i]) {
                    skip = true;
                    break;
                }
            }
            if (skip) continue;
            swap(num, pos, i);
            permute(num, pos + 1, res);
            swap(num, pos, i); // backtrack
        }
    }

    public static void  swap(int[] num, int i, int j) {
        if (i == j) return;
        num[i] = num[j] - num[i];
        num[j] = num[j] - num[i];
        num[i] = num[j] + num[i];
    }
    
    /**
     * Lexicography Order next permutation
     * Find the next permutation in lexicographic order.
     *http://en.wikipedia.org/wiki/Permutation#Generation_in_lexicographic_order
     */
    public static List<List<Integer>> permuteUniqueB(int[] num) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (num == null || num.length == 0)
            return res;

        Arrays.sort(num);
        List<Integer> row = new ArrayList<>();
        for (int a : num)
            row.add(a);
        res.add(new ArrayList<Integer>(row)); // first permutation

        while (nextPermutation(row)) { // if there is next permutation
            res.add(new ArrayList<Integer>(row));
        }
        return res;
    } 
    
    /**
     * e.g.: 1234 -> 1243, 1243 -> 1324
     * Traverse backward to get 3
     * Then traverse forward to get furthest number bigger than 3
     * Swap these two digits and reverse from next to last
     */
    public static boolean nextPermutation(List<Integer> row) {
        int last = row.size() - 1;
        for (int pos = last - 1; pos >= 0; pos--) {
            if (row.get(pos) < row.get(pos + 1)) {
                int partition = pos;
                int change = pos + 1;
                for (int i = pos + 1; i <= last; i++) 
                    if (row.get(i) > row.get(pos)) change = i;
                swap(row, partition, change);
                reverse(row, pos + 1, last);
                return true;
            }
        }
        return false;
    }
    
    public static void swap(List<Integer> row, int a, int b) {
        int t = row.get(a);
        row.set(a, row.get(b));
        row.set(b, t);
    }
    
    public static void reverse(List<Integer> row, int s, int e) {
        while (s < e) {
            swap(row, s, e);
            s++;
            e--;
        }
    }


    ////////

    public static List<List<Integer>> permuteUnique3(int[] num) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> row = new ArrayList<>();
        dfs(num, 0, res, row);
        return res;
    }

    /**
     *
     * @param num number array
     * @param start DFS start position
     * @param res results
     * @param row partial row to be added to results when complete
     */
    public static void dfs(int[] num, int start, List<List<Integer>> res, List<Integer> row) {
        if (start == num.length) {
            List<Integer> temp = new ArrayList<>(row);
            res.add(temp);
            return;
        }
        Set<Integer> s = new HashSet<>(); // keep track of what has been added for DFS
        for (int i = start; i < num.length; i++) {
            if (!s.contains(num[i])) {
                s.add(num[i]);

                swap(num, start, i);
                row.add(num[start]);

                dfs(num, start + 1, res, row);

                // backtrack
                row.remove(row.size() - 1);
                swap(num, start, i);
            }
        }
    }

}
