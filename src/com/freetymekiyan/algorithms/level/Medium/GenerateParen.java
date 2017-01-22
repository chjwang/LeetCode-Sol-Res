package com.freetymekiyan.algorithms.level.Medium;

import java.util.*;

/**
 * Given n pairs of parentheses, write a function to generate all combinations
 * of well-formed parentheses.
 * 
 * For example, given n = 3, a solution set is:
 * 
 * "((()))", "(()())", "(())()", "()(())", "()()()"
 * 
 * Tags: Backtracking. String
 */
class GenerateParen {
    public static void main(String[] args) {
        
    }

    /**
     * Backtracking
     * Helper function use left and to represent available left and right(to match left parentheses already added) parentheses respectively
     * Initialize left as n, right as 0
     */
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        if (n <= 0) return ans;
        dfs(n, 0, "", ans);
        return ans;
    }

    /**
     * @param left available left parentheses
     * @param right available right parentheses to match left parentheses already added
     * @param res current result
     * @param ans the answer list of the problem
     */
    public void dfs(int left, int right, String res, List<String> ans) {
        if (left == 0 && right == 0) {
            ans.add(res);
            return;
        }
        if (left > 0) dfs(left - 1, right + 1, res + "(", ans); // addPrereq (, right + 1
        if (right > 0) dfs(left, right - 1, res + ")", ans); // addPrereq ), right - 1
    }

    public List<String> generateParenthesis2(int n) {
        ArrayList<String> result = new ArrayList<>();
        dfs(result, "", n, n);
        return result;
    }
    /*
    left and right represents the remaining number of ( and ) that need to be added.
    When left > right, there are more ")" placed than "(". Such cases are wrong and the method stops.
    */
    public void dfs(ArrayList<String> result, String s, int left, int right){
        if(left > right)
            return;

        if(left==0&&right==0){
            result.add(s);
            return;
        }

        if(left>0){
            dfs(result, s+"(", left-1, right);
        }

        if(right>0){
            dfs(result, s+")", left, right-1);
        }
    }

    public List<String> generateParenthesis3(int n) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Integer> diff = new ArrayList<>();

        result.add("");
        diff.add(0);

        for (int i = 0; i < 2 * n; i++) {
            ArrayList<String> temp1 = new ArrayList<>();
            ArrayList<Integer> temp2 = new ArrayList<>();

            for (int j = 0; j < result.size(); j++) {
                String s = result.get(j);
                int k = diff.get(j);

                if (i < 2 * n - 1) {
                    temp1.add(s + "(");
                    temp2.add(k + 1);
                }

                if (k > 0 && i < 2 * n - 1 || k == 1 && i == 2 * n - 1) {
                    temp1.add(s + ")");
                    temp2.add(k - 1);
                }
            }

            result = new ArrayList<>(temp1);
            diff = new ArrayList<>(temp2);
        }

        return result;
    }
}