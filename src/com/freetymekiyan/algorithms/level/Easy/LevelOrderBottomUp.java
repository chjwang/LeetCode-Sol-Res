package com.freetymekiyan.algorithms.level.Easy;

import java.util.*;

/**
 * Given a binary tree, return the bottom-up level order traversal of its
 * nodes' values. (ie, from left to right, level by level from leaf to root).
 * 
 * For example:
 * Given binary tree {3,9,20,#,#,15,7},
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 
 * return its bottom-up level order traversal as:
 * [
 *   [15,7],
 *   [9,20],
 *   [3]
 * ]
 * 
 * Tags: Tree, BFS
 */
class LevelOrderBottomUp {
    
    public static void main(String[] args) {
        
    }
    
    /**
     * Use a level list to store the nodes of this level
     * addRecursive root to it to begin
     * Build next level with current level, addPrereq current level value to result
     * Assign next level to current level
     * addRecursive curLevel to first of result each time to get reverse order
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        if (root == null) return res;
        
        /*store the nodes of thie level*/
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> levelList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode n = q.poll();
                levelList.add(n.val);
                if (n.left != null)
                    q.add(n.left);
                if (n.right != null)
                    q.add(n.right);
            }
            res.add(0, levelList);
        }

        return res; 
    }
    
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
}