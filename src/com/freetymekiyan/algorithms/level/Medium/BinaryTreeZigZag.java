package com.freetymekiyan.algorithms.level.Medium;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Given a binary tree, return the zigzag level order traversal of its nodes'
 * values. (ie, from left to right, then right to left for the next level and
 * alternate between).
 * 
 * For example:
 * Given binary tree {3,9,20,#,#,15,7},
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * return its zigzag level order traversal as:
 * [
 *   [3],
 *   [20,9],
 *   [15,7]
 * ]
 * 
 * Tags: Tree, BFS, Stack
 */
class BinaryTreeZigZag {
    
    public static void main(String[] args) {
        
    }
    
    /**
     * Use queue to do BFS.
     * Get queue's size to get nodes in each level.
     * Use a boolean to indicate difference level order. 
     * Toggle it after a level is finished.
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        boolean right2Left = false;
        while (!q.isEmpty()) {
            List<Integer> curLevel = new ArrayList<>();
            int size = q.size();
            for (int i = 0; i < size; i++) { // process current level
                TreeNode n = q.poll();
                if (!right2Left)
                    curLevel.add(n.val);
                else
                    curLevel.add(0, n.val); // use it as stack to reverse order
                if (n.left != null) q.add(n.left);
                if (n.right != null) q.add(n.right);
            }
            right2Left = !right2Left;
            res.add(curLevel);
        }
        return res;
    }
    
    /**
     * Use two lists, one for cur level, one for next level
     * Use a binary flag to determin whether we toggle the order of current
     * level or not
     * Update flag after each level
     */
    public List<List<Integer>> zigzagLevelOrder2(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null)
            return res;

        List<TreeNode> curLevel = new ArrayList<>();
        List<TreeNode> nextLevel = new ArrayList<>();

        curLevel.add(root);
        boolean toggle = false;

        while (!curLevel.isEmpty()) {
            List<Integer> curLevelRes = new ArrayList<>();

            while (!curLevel.isEmpty()) {
                TreeNode temp = curLevel.remove(0);
                if (!toggle)
                    curLevelRes.add(temp.val);
                else
                    curLevelRes.add(0, temp.val); // insert to front
                if (temp.left != null) nextLevel.add(temp.left);
                if (temp.right != null) nextLevel.add(temp.right);
            }

            res.add(curLevelRes);
            curLevel = nextLevel;
            toggle = !toggle;
        }
        return res;
    }

    /**
     * We can print spiral order traversal in O(n) time and O(n) extra space. The idea is to use two stacks.
     *
     * We can use one stack for printing from left to right and other stack for printing from right to left.
     * In every iteration, we have nodes of one level in one of the stacks. We print the nodes, and
     * push nodes of next level in other stack.
     */
    public List<List<Integer>> zigzagLevelOrder3(TreeNode node) {
        List<List<Integer>> res = new ArrayList<>();
        if (node == null)
            return res;   // NULL check

        // Create two stacks to store alternate levels
        Stack<TreeNode> s1 = new Stack<>();// For levels to be printed from right to left
        Stack<TreeNode> s2 = new Stack<>();// For levels to be printed from left to right

        // Push first level to first stack 's2'
        s2.push(node);

        // Keep printing while any of the stacks has nodes
        while (!s1.empty() || !s2.empty()) {
            List<Integer> curLevelRes = new ArrayList<>();

            // process nodes of current level from s1 and push nodes of next level to s2
            while (!s1.empty()) {
                TreeNode temp = s1.pop();
                curLevelRes.add(temp.val);

                // Note that right is pushed before left
                if (temp.right != null)
                    s2.push(temp.right);

                if (temp.left != null)
                    s2.push(temp.left);

            }

            // process nodes of current level from s2 and push nodes of next level to s1
            while (!s2.empty()) {
                TreeNode temp = s2.pop();
                curLevelRes.add(temp.val);

                // Note that left is pushed before right
                if (temp.left != null)
                    s1.push(temp.left);
                if (temp.right != null)
                    s1.push(temp.right);
            }

            res.add(curLevelRes);
        }
        return res;
    }

}
