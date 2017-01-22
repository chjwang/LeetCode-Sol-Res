package com.freetymekiyan.algorithms.level.Easy;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

/**
 * Given a binary tree, find its minimum depth.
 * 
 * The minimum depth is the number of nodes along the shortest path from the
 * root node down to the nearest leaf node.
 * 
 * Tags: Tree, DFS
 */
class MinimumDepth {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1, null, new TreeNode(2, null, null));
        int res = minDepth(root);
        System.out.println(res);
    }
    
    /**
     * Recursive
     * Get minDepth of left and right subtree
     * If one side is 0, return the other side plus 1
     * Return the smaller one + 1
     */
    public static int minDepth(TreeNode root) {
        if (root == null) return 0;

        if (root.left == null)
            return minDepth(root.right) + 1;
        if (root.right == null)
            return minDepth(root.left) + 1;
        else {
            int left = minDepth(root.left);
            int right = minDepth(root.right);
            return Math.min(left, right) + 1; // plus root
        }
    }
}