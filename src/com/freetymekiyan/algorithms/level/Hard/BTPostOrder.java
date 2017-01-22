package com.freetymekiyan.algorithms.level.Hard;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Given a binary tree, return the postorder traversal of its nodes' values.
 * 
 * For example:
 * Given binary tree {1,#,2,3},
 *    1
 *     \
 *      2
 *     /
 *    3
 * return [3,2,1].
 * 
 * Note: Recursive solution is trivial, could you do it iteratively?
 * 
 * Tags: Tree, Stack
 */
class BTPostOrder {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode n1 = new TreeNode(2);
        TreeNode n2 = new TreeNode(3);
        TreeNode n3 = new TreeNode(4);
        TreeNode n4 = new TreeNode(5);
        TreeNode n5 = new TreeNode(6);
        root.left = n1;
        root.right = n2;
        n1.left = n3;
        n1.right = n4;
        n2.right = n5;
        System.out.println(new BTPostOrder().postorderTraversal(root));
        System.out.println(new BTPostOrder().postorderTraversal1(root));
    }
    
    /**
     * post order: left - right - root
     * modify pre order: root - left - right to root - right - left
     * reverse the result
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Stack<TreeNode> s = new Stack<>();
        s.push(root);
        while (!s.isEmpty()) {
            TreeNode curNode = s.pop();
            res.add(curNode.val); // visit
            if (curNode.left != null) s.push(curNode.left);
            if (curNode.right != null) s.push(curNode.right); // right pops first
        }
        Collections.reverse(res); // reverse order
        return res;
    }

    /**
     * Two stacks
     *
     * Same as the above but use a stack to reverse the result
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal1(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;

        Stack<TreeNode> s1 = new Stack<>();
        Stack<TreeNode> s2 = new Stack<>();

        s1.push(root);
        // Run while first stack is not empty
        while (!s1.isEmpty()) {
            TreeNode node = s1.pop();
            s2.push(node);

            // Push left and right children of removed item to s1
            if (node.left != null) s1.push(node.left);
            if (node.right != null) s1.push(node.right); // right pops first
        }

        // Add all elements of second stack to return result
        while (!s2.isEmpty()) {
            TreeNode node = s2.pop();
            res.add(node.val);
        }
        return res;
    }

    /**
     * Use two pointers. 1 for current node, 1 for previous traversed node
     * 3 situations:
     *
     * 1. Traversing down, prev is not set or current is prev's child(left or right)
     *      1.1 Push left child to stack if not null,
     *      1.2 otherwise push rigth child
     *
     * 2. Traversing up from left, prev is current's left child
     *      Push right child to stack if not null
     *
     * 3. Traversing up from right
     *      Visit, and pop
     */
    public List<Integer> postorderTraversal2(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        if (root == null)
            return res;

        TreeNode prev = null; // previously traversed node
        TreeNode cur; // always top of the stack
        Stack<TreeNode> s = new Stack<>();
        s.push(root);

        while (!s.isEmpty()) {
            cur = s.peek();

            // traverse down
            if (prev == null || prev.left == cur || prev.right == cur) {
                if (cur.left != null)
                    s.push(cur.left); // push left first
                else if (cur.right != null)
                    s.push(cur.right);
            }

            // traverse up from the left
            else if (cur.left == prev) {
                if (cur.right != null)
                    s.push(cur.right);
            }

            // traverse up from the right
            else {
                res.add(cur.val); // visit
                s.pop();
            }
            prev = cur;
        }
        return res;
    }

}