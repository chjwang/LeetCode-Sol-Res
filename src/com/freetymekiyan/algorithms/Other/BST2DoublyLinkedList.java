package com.freetymekiyan.algorithms.Other;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

/**
 */
public class BST2DoublyLinkedList {

    //store the head and tail of the linked list
    static class NodePair{
        TreeNode head;
        TreeNode tail;
    }

    public static TreeNode bstToDll(TreeNode root){
        NodePair result = new NodePair();
        bstToDll(root, result);
        return result.head;
    }

    public static void bstToDll(TreeNode root, NodePair result){
        if(root == null) return;
        bstToDll(root.left, result);   // go left

        root.left = result.tail;
        if(result.head == null)
            result.head = root;
        else
            result.tail.right = root;

        TreeNode right = root.right;
        result.tail = root; // move tail from left child tree to root
        root.right = null;

        bstToDll(right, result);  //go right
    }
}