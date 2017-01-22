package com.freetymekiyan.algorithms.level.Medium;

import java.util.*;

/**
 * Given a set of distinct integers, S, return all possible subsets.
 * 
 * Note:
 * Elements in a subset must be in non-descending order.
 * The solution set must not contain duplicate subsets.
 * For example,
 * If S = [1,2,3], a solution is:
 * 
 * [
 *   [3],
 *   [1],
 *   [2],
 *   [1,2,3],
 *   [1,3],
 *   [2,3],
 *   [1,2],
 *   []
 * ]
 * 
 * Tags: Array, Backtracking, Bit Manipulation
 */
class Subsets {
    public static void main(String[] args) {
        int[] nums = { 1, 2, 3 };
        List<List<Integer>> res = subsetsB(nums);
        for (List<Integer> l : res) {
            System.out.println(l.toString());
        }
    }
    
    /**
     * Remember the start position and do backtracking
     */
    public static List<List<Integer>> subsetsB(int[] s) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        Arrays.sort(s); // unnecessary, needed to have canonical ordered result sets
        subsetsB(s, 0, new ArrayList<>(), res);
        return res;
    }

    /**
     * DFS backtrack, recursive down to s.length branches: with each element not already in getPath.
     * @param s
     * @param start
     * @param path getPath is the DFS getPath, contains result elements before start index
     * @param result
     */
    public static void subsetsB(int[] s, int start, List<Integer> path, List<List<Integer>> result) {
        result.add(new ArrayList<>(path));
        for (int i = start; i < s.length; i++) {
            path.add(s[i]); // with i
            subsetsB(s, i + 1, path, result); // DFS
            path.remove(path.size() - 1); // remove last element, backtrack
        }
    }
    
    public static List<List<Integer>> subsetsA(int[] s) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(s); // unnecessary
        subsetsA(s, 0, new ArrayList<>(), res);
        res.add(new ArrayList<>()); // addPrereq blank set
        return res;
    }
    
    /**
     * Recursive down to two branches: with current element, or without current element.
     */
    public static void subsetsA(int[] s, int start, List<Integer> path, List<List<Integer>> result) {
        if (start == s.length) {
            result.add(path);
            return;
        }
        List<Integer> pathWithStart = new ArrayList<>(path);

        subsetsA(s, start + 1, path, result); // without
        
        pathWithStart.add(s[start]);
        subsetsA(s, start + 1, pathWithStart, result); // with
    }


    /**
     * @param s: A set of numbers.
     * @return: A list of lists. All valid subsets.
     */
    public ArrayList<ArrayList<Integer>> subsets(int[] s) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        int n = s.length;
        Arrays.sort(s);

        // 1 << n is 2^n
        // each subset equals to an binary integer between 0 .. 2^n - 1
        // 0 -> 000 -> []
        // 1 -> 001 -> [1]
        // 2 -> 010 -> [2]
        // ..
        // 7 -> 111 -> [1,2,3]
        for (int i = 0; i < (1 << n); i++) {
            ArrayList<Integer> subset = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                // check whether the jth digit in i's binary representation is 1
                if ((i & (1 << j)) != 0) {
                    subset.add(s[j]);
                }
            }
            result.add(subset);
        }

        return result;
    }

    public List<List<Integer>> subsetsC(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());

        for (int num: nums) {
            List<List<Integer>> resDup = new ArrayList<>(res);
            for (List<Integer> list:resDup) {
                List<Integer> tmpList = new ArrayList<>(list);
                list.add(num);
                res.add(tmpList);
            }
        }
        return res;
    }
}
