package com.freetymekiyan.algorithms.level.Easy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Given a non-empty binary search tree and a target value, find the value in the BST that is
 * closest to the target.
 *
 * Note: Given target value is a floating point. You are guaranteed to have only one unique value in
 * the BST that is closest to the target.
 */
public class ClosestBSTValue {

    public int closestValue(TreeNode root, double target) {
        // 选出子树的根节点
        TreeNode kid = target < root.val ? root.left : root.right;
        // 如果没有子树，也就是递归到底时，直接返回当前节点值
        if(kid == null) return root.val;
        // 找出子树中最近的那个节点
        int closest = closestValue(kid, target);
        // 返回根节点和子树最近节点中，更近的那个节点
        return Math.abs(root.val - target) < Math.abs(closest - target) ? root.val : closest;
    }

    public int closestValue2(TreeNode root, double target) {
        int closest = root.val;
        while(root != null){
            // 如果该节点的离目标更近，则更新到目前为止的最近值
            closest = Math.abs(closest - target) < Math.abs(root.val - target) ? closest : root.val;
            // 二叉搜索
            root = target < root.val ? root.left : root.right;
        }
        return closest;
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
