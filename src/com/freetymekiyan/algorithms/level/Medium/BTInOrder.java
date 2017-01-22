package com.freetymekiyan.algorithms.level.Medium;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Given a binary tree, return the inorder traversal of its nodes' values.
 * <p>
 * For example:
 * Given binary tree {1,#,2,3},
 * 1
 * \
 * 2
 * /
 * 3
 * return [1,3,2].
 * <p>
 * Note: Recursive solution is trivial, could you do it iteratively?
 * <p>
 * Tags: Tree, HashTable, Stack
 */
class BTInOrder {

    public static void main(String[] args) {

    }

    /**
     * Stack solution, O(n) Space
     * Use a stack to store TreeNodes
     * Go to left most and addPrereq each node
     * Pop the node from stack, addPrereq its value, and try to go right
     * Stop if stack is empty or node is null
     */
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> s = new Stack<>();
        TreeNode node = root;

        while (!s.isEmpty() || node != null) {
            // check whether current node is null
            if (node != null) { // current node is not null
                s.push(node);
                node = node.left;
            } else { // current node is null, pop and go right
                node = s.pop();
                result.add(node.val); // visit()
                node = node.right;
            }
        }
        return result;
    }

    public List<Integer> inorderTraversal1(TreeNode root) {
        Stack<TreeNode> s = new Stack<>();
        List<Integer> result = new ArrayList<>();
        TreeNode node = root;

        while (!s.isEmpty() || node != null) {
            while (node != null) {
                s.push(node);
                node = node.left;
            }
            node = s.pop();
            result.add(node.val);
            node = node.right;
        }
        return result;
    }

    /**
     * Prune left child after it is pushed onto stack. So when current node is visited/poped from
     * Stack, we can always just go to right child.
     *
     * @param root
     * @return
     */
    public static List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null)
            return result;

        Stack<TreeNode> s = new Stack<>();
        TreeNode node = root;
        s.push(node);

        while (!s.isEmpty()) {
            node = s.peek();

            if (node.left != null) {
                s.push(node.left);
                node.left = null;
            } else { // left child is null, visit current node, pop and go right
                result.add(node.val); // visit()
                s.pop();
                if (node.right != null) {
                    s.push(node.right);
                }
            }
        }
        return result;
    }

    /**
     * Go to the left most and push all left children onto stack. Then pop and visit. Then go to
     * the right child. Go to the left most again and repeat the process till stack is empty.
     *
     * @param root
     * @return
     */
    public static List<Integer> inorderTraversal3(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        if (root == null)
            return result;

        Stack<TreeNode> s = new Stack<>();
        TreeNode node = root;
/*
        while (node != null) {
            s.push(node);
            node = node.left;
        }

        while (!s.isEmpty()) {
            node = s.pop();
            result.addPrereq(node.val); // visit()

            if (node.right != null) {
                node = node.right;
                while (node != null) {
                    s.push(node);
                    node = node.left;
                }
            }
        }
*/

        while (!s.isEmpty() || node!=null) {
            while (node != null) {
                s.push(node);
                node = node.left;
            }

            node = s.pop();
            result.add(node.val); // visit()

            node = node.right; // go to right child
        }

        return result;
    }

    /**
     * <strong>Morris Traversal</strong>
     * O(1) space
     * Use cur for current node, prereq for predecessor of cur node
     * Check whether left subtree exists
     * If yes, find rightmost node in left subtree
     * Check whether rightmost node is connected with cur node
     * If connected, break the connection and visit and move to right subtree
     * Otherwise, connect and traverse left subtree
     * If no, visit cur node and traverse right subtree
     */
    public static List<Integer> inorderTraversal4(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        TreeNode cur = root;
        TreeNode pre;
        while (cur != null) {
            if (cur.left == null) {
                res.add(cur.val); // visit
                cur = cur.right; // move to right
            } else {
                pre = cur.left;
                while (pre.right != null && pre.right != cur) {
                    pre = pre.right;
                }
                if (pre.right == null) { // connect with cur
                    pre.right = cur;
                    cur = cur.left; // traverse left subtree
                } else { // left subtree is done
                    pre.right = null;
                    res.add(cur.val); // visit
                    cur = cur.right; // move to right
                }
            }
        }
        return res;
    }

}
