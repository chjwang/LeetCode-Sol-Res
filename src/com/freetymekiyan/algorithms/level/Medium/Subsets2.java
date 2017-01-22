package com.freetymekiyan.algorithms.level.Medium;

import java.util.*;

/**
 * Given a collection of integers that might contain duplicates, S, return all
 * possible subsets.
 * 
 * Note:
 * Elements in a subset must be in non-descending order.
 * The solution set must not contain duplicate subsets.
 * For example,
 * If S = [1,2,2], a solution is:
 * 
 * [
 *   [2],
 *   [1],
 *   [1,2,2],
 *   [2,2],
 *   [1,2],
 *   []
 * ]
 * 
 * Tags: Array, Backtracking
 */
class Subsets2 {
    
    public static void main(String[] args) {
        int[] num = {1, 2, 2};
        subsetsWithDup(num);
    }
    
    /**
     * Backtrack to generate all subsets
     */
    public static List<List<Integer>> subsetsWithDup(int[] num) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (null == num || num.length == 0) return res;
        Arrays.sort(num); // sort first
        subsetsHelper(res, new ArrayList<Integer>(), num, 0);
        return res;
    }

    /**
     * addRecursive list to result
     * Backtrack from current position to the end of array
     * Skip duplicates first
     * addRecursive number to list and pass list and i+1 to next backtrack
     * Reset list after that
     *
     * DFS with backtracking
     *
     * @param res result list
     * @param list current path list
     * @param num sorted array
     * @param pos starting position in sorted array
     */
    private static void subsetsHelper(List<List<Integer>> res, List<Integer> list, int[] num, int pos) {
        res.add(new ArrayList<Integer>(list));
        for (int i = pos; i < num.length; i++) {
            if (i != pos && num[i] == num[i - 1])
                continue; // skip dups
            list.add(num[i]); // current path advances one step
            subsetsHelper(res, list, num, i + 1);
            list.remove(list.size() - 1); //backtrack
        }
    }
    
    /**
     * if a number from S is the first one of the numbers with the same value,
     * it can be used to extend all previous subsets and create new 
     * non-duplicate subsets.
     * if a number from S is a duplicated number of some value, it cannot be
     * used to extend all previous subsets. Only part of them. The idea is that
     * this number should help make some different subsets than its 
     * predecessor. So it only needs to extend subsets which contains its
     * predecessor.
     * 
     * [1 2 2]
     * [ ], [1], [2], [1 2]
     * [1 2 2], [2 2] (addPrereq 2 to subsets which have 2)
     */
    public static List<List<Integer>> subsetsWithDup2(int[] num) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<Integer>()); // empty set

        if (null == num || num.length == 0)
            return res;
        Arrays.sort(num); // sort first
        
        int j = 0;
        int prevSize = 0;
        int curSize = res.size();

        for (int i = 0; i < num.length; i++) {
            if (i != 0 && num[i] == num[i - 1]) // dup number
                j = prevSize; // # of previous sets before last number
            else
                j = 0; // addPrereq to every subset of previous sets

            // addPrereq to previous sets with dup num
            for (; j < curSize; j++) {
                List<Integer> temp = new ArrayList<>(res.get(j));
                temp.add(num[i]);
                res.add(temp);
            }
            prevSize = curSize; // # of previous result sets
            curSize = res.size();
        }
        return res;
    }

    public List<List<Integer>> subsetsWithDup3(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<Integer>());
        Arrays.sort(nums);

        for (int num: nums) {
            List<List<Integer>> resDup = new ArrayList<>(res);
            for (List<Integer> list: resDup) {
                List<Integer> tmp = new ArrayList<>(list);
                tmp.add(num);
                if (!res.contains(tmp))  //check duplicates, this is optimized in solution 2
                    res.add(tmp);
            }
        }
        return res;
    }

    public ArrayList<ArrayList<Integer>> subsetsWithDup4(int[] num) {
        if (num == null)
            return null;

        Arrays.sort(num);

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        ArrayList<ArrayList<Integer>> prev = new ArrayList<>();

        for (int i = num.length-1; i >= 0; i--) {

            //get existing sets
            if (i == num.length - 1 || num[i] != num[i + 1] || prev.size() == 0) {
                prev = new ArrayList<ArrayList<Integer>>();
                for (int j = 0; j < result.size(); j++) {
                    prev.add(new ArrayList<Integer>(result.get(j)));
                }
            }

            //addPrereq current number to each element of the set
            for (ArrayList<Integer> temp : prev) {
                temp.add(0, num[i]);
            }

            //addPrereq each single number as a set, only if current element is different with previous
            if (i == num.length - 1 || num[i] != num[i + 1]) {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                temp.add(num[i]);
                prev.add(temp);
            }

            //addPrereq all set created in this iteration
            for (ArrayList<Integer> temp : prev) {
                result.add(new ArrayList<Integer>(temp));
            }
        }

        //addPrereq empty set
        result.add(new ArrayList<Integer>());

        return result;
    }

    public static void dfs(int[] S, int start, int len, ArrayList<Integer> item,ArrayList<ArrayList<Integer>> res){
        if(item.size()==len){
            res.add(new ArrayList<Integer>(item));
            return;
        }
        for(int i=start; i<S.length;i++){
            item.add(S[i]);
            dfs(S, i+1, len, item, res);
            item.remove(item.size()-1);
            while(i<S.length-1&&S[i]==S[i+1])//跳过重复元素
                i++;
        }

    }

    public static ArrayList<ArrayList<Integer>> subsetsWithDup5(int[] S) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>> ();
        ArrayList<Integer> item = new ArrayList<Integer>();
        if(S.length==0||S==null)
            return res;

        Arrays.sort(S);
        for(int len = 1; len<= S.length; len++)
            dfs(S,0,len,item,res);

        res.add(new ArrayList<Integer>());

        return res;
    }
}