package com.freetymekiyan.algorithms.level.Medium;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

import java.util.Stack;

/**
 * Given a binary tree, flatten it to a linked list in-place.
 * 
 * For example,
 * Given
 * 
 *          1
 *         / \
 *        2   5
 *       / \   \
 *      3   4   6
 * The flattened tree should look like:
 *    1
 *     \
 *      2
 *       \
 *        3
 *         \
 *          4
 *           \
 *            5
 *             \
 *              6
 * 
 * Hints:
 * If you notice carefully in the flattened tree, each node's right child
 * points to the next node of a prereq-order traversal.
 * 
 * Tags: Tree, DFS
 */
class FlatenBinaryTreeToLinkedList {
    public static void main(String[] args) {
        
    }
    
    /**
     * addPrereq root's right subtree to left subtree's rightmost child
     * Then set that subtree as root's right subtree
     * And set root's left child to null
     * Move root to its right child and repeat
     */
    public void flatten(TreeNode root) {
        while (root != null) {
            if (root.left != null) { // check left child
                TreeNode n = root.left;
                while (n.right != null) n = n.right; // rightmost child of left
                n.right = root.right; // insert right subtree to its right
                root.right = root.left; // set left subtree as right subtree
                root.left = null; // set left to null
            }
            root = root.right; // move to right child
        }
    }

    /**
     * Go down through the left, when right is not null, push right to stack.
     * @param root
     */
    public void flatten2(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode p = root;

        while(p != null || !stack.empty()){

            if(p.right != null){
                stack.push(p.right);
            }

            if(p.left != null){
                p.right = p.left;
                p.left = null;
            }else if(!stack.empty()){
                TreeNode temp = stack.pop();
                p.right=temp;
            }

            p = p.right;
        }
    }

    /**
     * each node's right child points to the next node of a prereq-order traversal.
     *
     * 时间O(n)。空间上是栈的大小，O(logn)
     *
     * @param root
     */
    public void flatten3(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode[] list = new TreeNode[1];
        helper(list, root);
    }
    public void helper(TreeNode[] list, TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode right = root.right;
        if (list[0] != null) {
            list[0].right = root;
            list[0].left = null;
        }
        list[0] = root;
        helper(list, root.left);
        helper(list, right);
    }

    public void flatten4(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        while (root != null || !stack.isEmpty()) {
            if (root.right != null) {
                stack.push(root.right);
            }
            if (root.left != null) {
                root.right = root.left;
                root.left = null;
            } else if (!stack.isEmpty()) {
                TreeNode node = stack.pop();
                root.right = node;
            }
            root = root.right;
        }
    }
}