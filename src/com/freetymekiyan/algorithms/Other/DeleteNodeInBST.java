package com.freetymekiyan.algorithms.Other;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

/**
 */
public class DeleteNodeInBST {

    public static void main(String[] args) {
        TreeNode t = new TreeNode(1, null, new TreeNode(2, null, null));
        System.out.println(t.toString());
        t = deleteNode(t, 1);
        System.out.println(t.toString());
        t = new TreeNode(1, null, new TreeNode(2, null, null));
        System.out.println(t.toString());
        t = deleteNode(t, 2);
        System.out.println(t.toString());
    }

    public static TreeNode deleteNode(TreeNode root, int key) {
        if (root == null)
            return null;

        TreeNode p = new TreeNode(Integer.MIN_VALUE);
        p.left = root;

        deleteNode(root, key, p);
        return p.left;
    }

    public static TreeNode deleteNode(TreeNode node, int key, TreeNode parent) {
        // case 1: key < current value
        if (key < node.val) {
            if (node.left != null) {
                node.left = deleteNode(node.left, key, node);
            }
        }
        // case 2: key > current value
        else if (key > node.val) {
            if (node.right != null) {
                node.right = deleteNode(node.right, key, node);
            }
        }
        // case 3: key == current value
        else {
            // case 3.1: current has both left and right children
            // swap right subtree's left most mininum value node with current,
            // then recursively process the subtree
            if (node.left != null && node.right != null) {
                TreeNode leftMostNode = leftMostMinValueNode(node.right);
                node.val = leftMostNode.val;
                leftMostNode.val = key;
                deleteNode(node.right, key, node);
            }
            // case 3.2: current has only one or no child (left and right), prune current node directly
            else {
                if (parent.left == node) {
                    parent.left = (node.left != null) ? node.left : node.right;
                    node = parent.left;
                } else if (parent.right == node) {
                    parent.right = (node.left != null) ? node.left : node.right;
                    node = parent.right;
                }
            }
        }
        return node;
    }

    public static TreeNode leftMostMinValueNode(TreeNode node) {
//        return node.left == null ? node : leftMostMinValueNode(node.left);

        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public static TreeNode addNode(TreeNode node, int key) {
        if (node == null)
            return new TreeNode(key);

        // case 1: key < current value
        if (key < node.val) {
            if (node.left != null) {
                node.left = addNode(node.left, key);
            } else {
                node.left = new TreeNode(key);
            }
        }
        // case 2: key > current value
        else if (key > node.val) {
            if (node.right != null) {
                node.right = addNode(node.right, key);
            } else {
                node.right = new TreeNode(key);
            }
        }
        // case 3: key == current value, node already exists, do nothing

        return node;
    }

}
