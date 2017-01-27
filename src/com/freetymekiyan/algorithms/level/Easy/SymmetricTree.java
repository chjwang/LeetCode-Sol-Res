package com.freetymekiyan.algorithms.level.Easy;

import java.util.Stack;

/**
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric
 * around its center).
 *
 * For example, this binary tree is symmetric:
 *
 *     1
 *    /  \
 *  2    2
 * / \   / \
 * 3  4 4  3
 *
 * But the following is not:
 *    1
 *   / \
 * 2   2
 *  \   \
 *  3    3
 *
 * Note:
 * Bonus points if you could solve it both recursively and iteratively.
 *
 * Tags: Tree, DFS, Stack
 */
class SymmetricTree {
    public static void main(String[] args) {

    }

    /**
     * Use a stack to store nodes in order
     * Then pop and compare
     */
    private boolean isSymmetric(TreeNode root) {
        if (root == null) return true;

        Stack<TreeNode> s = new Stack<>();
        s.push(root.left);
        s.push(root.right);
        while (!s.isEmpty()) {
            TreeNode n1 = s.pop();
            TreeNode n2 = s.pop();
            if (n1 == null && n2 == null) continue;
            if (n1 == null || n2 == null || n1.val != n2.val) return false;
            s.push(n1.left); // add those pairs that should be symmetric
            s.push(n2.right);
            s.push(n1.right);
            s.push(n2.left);
        }

        return true;
    }

    private boolean isSymmetric2(TreeNode root) {
        if (root == null || root.left == null && root.right == null) return true;

        TreeNode t1 = root.left, t2 = root.right;
        if (t1 != null && t2 == null || t1 == null && t2 != null || t1.val != t2.val) return false;

        Stack<TreeNode> s1 = new Stack<>(), s2 = new Stack<>();
        s1.push(t1);
        s2.push(t2);

        boolean flag = false; // should do RL
        while (!s1.isEmpty() && !s2.isEmpty()) {
            if (!flag && (t1.left != null || t2.right != null)) { //LR
                s1.push(t1);
                s2.push(t2);

                t1 = t1.left;
                t2 = t2.right;

                if (t1 != null && t2 == null || t1  == null && t2 != null || t1.val != t2.val)
                    return false;

                s1.push(t1);
                s2.push(t2);
            } else if (t1.right != null || t2.left != null) {
                t1 = t1.right;
                t2 = t2.left;
                if (t1 != null && t2 == null || t1 == null && t2 != null || t1.val != t2.val)
                    return false;
                flag = false;  // reset, should do LR
            } else {
                t1 = s1.pop();
                t2 = s2.pop();
                flag = true;
            }
        }
        return s1.isEmpty() && s2.isEmpty();
    }

    /**
     * Recursive, pre-order traversal
     * Check two symmetric nodes a time
     */
    private boolean isSymmetricRec(TreeNode root) {
        if (root == null) return true;
        return isSymmetricRec(root.left, root.right);
    }

    private boolean isSymmetricRec(TreeNode n1, TreeNode n2) {
        if (n1 == null || n2 == null) return n1 == n2;
        return n1.val == n2.val && isSymmetricRec(n1.left, n2.right) && isSymmetricRec(n1.right, n2.left);
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}