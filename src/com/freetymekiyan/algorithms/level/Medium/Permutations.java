package com.freetymekiyan.algorithms.level.Medium;

import java.util.*;

/**
 * Given a collection of numbers, return all possible permutations.
 * 
 * For example,
 * [1,2,3] have the following permutations:
 * [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], and [3,2,1].
 * 
 * Tags: Backtracking
 */
class Permutations {
    public static void main(String[] args) {
        System.out.println(permute(new int[]{1, 3, 2}));
    }
    
    /**
     * 
     */
    public static List<List<Integer>> permute(int[] num) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(num);
        permute(num, 0, res);
        return res;
    }
    
    public static void permute(int[] num, int start, List<List<Integer>> res) {
        if (start == num.length) {
            List<Integer> row = new ArrayList<>();
            for (int a : num) row.add(a);
            res.add(row);
            return;
        }
        for (int i = start; i < num.length; i++) {
            swap(num, start, i);
            permute(num, start + 1, res);
            swap(num, start, i); // backtrack
        }
    }
    
    public static void swap(int[] num, int i, int j) {
        if (i == j) return;
        num[i] = num[j] - num[i];
        num[j] = num[j] - num[i];
        num[i] = num[j] + num[i];
    }
    
    private static void print(int[] num) {
        for (int i = 0; i < num.length; i++) {
            System.out.print(num[i] + " ");
        }
        System.out.println();
    }
    
    /**
     * another solution
     * store whether a number is used in a boolean array
     * add used number to a list
     */
    class Solution { 
        boolean [] isUsed;
        int numLength;
        ArrayList<ArrayList<Integer>> output;
        ArrayList<Integer> list;

        public ArrayList<ArrayList<Integer>> permute(int[] num) {
            numLength = num.length;
            list = new ArrayList <Integer>();
            output = new ArrayList<ArrayList<Integer>>();
            isUsed = new boolean[num.length];
            doPermutation(0, num);
            return output;
        }
        public void doPermutation(int index, int[] num) {
            // base case
            if (index == numLength) {
                output.add((ArrayList<Integer>) list.clone());
                return;
            }
            for (int i = 0; i < numLength; i++) {
                if (!isUsed[i]) {
                    list.add(num[i]); // mark
                    isUsed[i] = true; // mark

                    doPermutation(index + 1, num);

                    isUsed[i] = false; // backtrack
                    list.remove(index); // reset
                }
            }
        }
    }
}