package com.freetymekiyan.algorithms.level.Easy;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

/**
 * Given a binary tree and a sum, determine if the tree has a root-to-leaf getPath
 * such that adding up all the values along the getPath equals the given sum.
 * 
 * For example:
 * Given the below binary tree and sum = 22,
 *               5
 *              / \
 *             4   8
 *            /   / \
 *           11  13  4
 *          /  \      \
 *         7    2      1
 * 
 * return true, as there exist a root-to-leaf getPath 5->4->11->2 which sum is 22.
 * 
 * Tags: Tree, DFS
 */
class PathSum {
    
    public static void main(String[] args) {
        
    }
    
    /**
     * Substract root value from sum every time
     * Return leaf node with sum == 0 
     * Or result in left subtree or right subtree
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) return false; // root == null
        sum -= root.val; // update sum
        // leaf? sum == 0? left subtree? right subtree?
        return root.left == null && root.right == null && sum == 0 ||
                hasPathSum(root.left, sum) ||
                hasPathSum(root.right, sum);
    }
}