package com.freetymekiyan.algorithms.Other;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

import java.util.List;
import java.util.Stack;

/**
 * Given a binary tree, print all paths from root to leaves.
 * 
 * 0 1 1 1
 * 0 0 0 1
 * 1 1 0 0
 * 0 0 0 0
 * 
 * 2->1->3
 * 2->0->3
 * 2->0->1->3
 * 
 * Tags: Binary Tree, DFS, Backtracking
 */
class BinaryTreePrintAllPaths {
    public static void main(String[] args) {
    }

    // DFS: pre-order
    public static void rootToLeafPathPrint(TreeNode root) {
        Stack<Object> stack = new Stack<>();
        if (root == null)
            return;
        stack.push(root.val + " ");
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = (TreeNode) stack.pop();
            String path = (String) stack.pop();

            // pre-order traversal: right->stack, then left->stack
            if (node.right != null) {
                stack.push(path + node.right.val + " ");
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(path + node.left.val + " ");
                stack.push(node.left);
            }
            if (node.left == null && node.right == null) {
                System.out.println(path);
            }
        }
    }

    public static void rootToLeafPathPrint2(TreeNode node)
    {
        int path[] = new int[1000];
        printPathsRecur(node, path, 0);
    }

    /* Recursive preOrderHelper function -- given a node, and an array
       containing the getPath from the root node up to but not
       including this node, print out all the root-leaf paths.*/
    static void printPathsRecur(TreeNode node, int path[], int pathLen)
    {
        if (node == null)
            return;

        /* append this node to the getPath array */
        path[pathLen] = node.val;
        pathLen++;

        /* it's a leaf, so print the getPath that led to here  */
        if (node.left == null && node.right == null)
            printArray(path, pathLen);
        else
        {
            /* otherwise try both subtrees */
            printPathsRecur(node.left, path, pathLen);
            printPathsRecur(node.right, path, pathLen);
        }
    }

    /* Utility function that prints out an array on a line. */
    private static void printArray(int ints[], int len)
    {
        int i;
        for (i = 0; i < len; i++)
        {
            System.out.print(ints[i] + " ");
        }
        System.out.println("");
    }

    /**
     *
     * @param node
     * @param curPathNodeList stack to store getPath
     */
    public static void rootToLeafPathPrint3(TreeNode node, List<TreeNode> curPathNodeList) {
        if (node == null)
            return;

        curPathNodeList.add(node); // push to stack
        rootToLeafPathPrint3(node.left, curPathNodeList);
        rootToLeafPathPrint3(node.right, curPathNodeList);

        if (node.left == null && node.right == null) {
            for (int i = 0; i < curPathNodeList.size(); i++) {
                System.out.print(curPathNodeList.get(i).val + " ");
            }
            System.out.println();
        }
        curPathNodeList.remove(curPathNodeList.size() - 1); //backtrack, pop from stack
    }

    /**
     * Start with the root. Push root node to stack.
     * while(stack not empty)
     *      top = top_of_stack
     *      mark top as visited
     *      if(top.left_node exists AND not_visited)
     *          push(top.left_node)
     *      else if (top.right_node exists AND not_visited)
     *          push(top.right_node)
     *      else      //Leaf node
     *          print stack
     *      pop
     * @param root
     */
    public void rootToLeafPathPrint3(TreeNode root) {
        if(root == null)
            return;
        Stack<TreeNode> s = new Stack<>();
        s.push(root);
        TreeNode node = root.left;

        while (! s.isEmpty()) {
            // keep going to left
            while(node != null) {
                s.push(node);
                node = node.left;
            }

            TreeNode top = s.peek();
            if (! top.isVisited()) {
                top.setVisited();
                node = top.right;
                if (node == null) { // both children null, it is leaf node
                    printThePath(s);
                    s.pop();
                }
            } else {
                s.pop();
            }
        }

    }

    static void printThePath(Stack<TreeNode> stack) {
        String temp = "  stack = ";
        for (int i = stack.size() - 1; i >= 0; i--) {
            temp = temp + ((TreeNode)(stack.elementAt(i))).val + " ";
        }
        System.out.println(temp);
    }
}
