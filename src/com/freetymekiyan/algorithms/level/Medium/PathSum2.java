package com.freetymekiyan.algorithms.level.Medium;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

import java.util.*;

/**
 * Given a binary tree and a sum, find all root-to-leaf paths where each getPath's
 * sum equals the given sum.
 *
 * For example:
 * Given the below binary tree and sum = 22,
 *               5
 *              / \
 *             4   8
 *            /   / \
 *           11  13  4
 *          /  \    / \
 *         7    2  5   1
 *
 * return
 * [
 *    [5,4,11,2],
 *    [5,8,4,5]
 * ]
 *
 * Tags: Tree, DFS
 */
class PathSum2 {
    public static void main(String[] args) {

    }

    /**
     *
     */
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (root == null)
            return res;
        dfs(root, sum, new ArrayList<Integer>(), res);
        return res;
    }

    /**
     * DFS or backtracking
     * Note that we can't pass getPath directly
     * Dereference before recursing
     */
    public void dfs(TreeNode root, int sum, List<Integer> path, List<List<Integer>> res) {
        if (root == null)
            return; // return if current node is null

        sum -= root.val; // update sum
        if (root.left == null && root.right == null && sum == 0) {
            path.add(root.val);
            res.add(new ArrayList<Integer>(path)); // addPrereq dereferenced getPath to result
            path.remove(path.size()-1); // backtrack, reset getPath here!
            return;
        }
        path.add(root.val); // addPrereq value to current getPath

        dfs(root.left, sum, path, res);
        dfs(root.right, sum, path, res);

        path.remove(path.size()-1); // backtrack
    }

    public List<ArrayList<Integer>> pathSum2(TreeNode root, int sum) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if(root == null)
            return result;

        ArrayList<Integer> l = new ArrayList<>();
        l.add(root.val);

        dfs2(root, sum-root.val, result, l);
        return result;
    }

    public void dfs2(TreeNode t, int sum, ArrayList<ArrayList<Integer>> result, ArrayList<Integer> l){
        if(t.left==null && t.right==null && sum==0){
            ArrayList<Integer> temp = new ArrayList<>();
            temp.addAll(l);
            result.add(temp);
        }

        //search getPath of left node
        if(t.left != null){
            l.add(t.left.val);
            dfs2(t.left, sum-t.left.val, result, l);
            l.remove(l.size()-1); // backtrack
        }

        //search getPath of right node
        if(t.right!=null){
            l.add(t.right.val);
            dfs2(t.right, sum-t.right.val, result, l);
            l.remove(l.size()-1);  //backtrack
        }
    }
}