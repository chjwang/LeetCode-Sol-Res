package com.freetymekiyan.algorithms.Other;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

import java.util.Stack;

/**
 Level order traversal in spiral form

 Write a function to print spiral order traversal of a tree.
 */
public class BinaryTreeLevelOrderSpiral {
    /**
     * We can print spiral order traversal in O(n) time and O(n) extra space. The idea is to use two stacks.
     *
     * We can use one stack for printing from left to right and other stack for printing from right to left.
     * In every iteration, we have nodes of one level in one of the stacks. We print the nodes, and
     * push nodes of next level in other stack.
     */
    void printSpiral1(TreeNode node) {
        if (node == null)
            return;   // NULL check

        // Create two stacks to store alternate levels
        Stack<TreeNode> s1 = new Stack<TreeNode>();// For levels to be printed from right to left
        Stack<TreeNode> s2 = new Stack<TreeNode>();// For levels to be printed from left to right

        // Push first level to first stack 's1'
        s1.push(node);

        // Keep printing while any of the stacks has nodes
        while (!s1.empty() || !s2.empty()) {
            // Print nodes of current level from s1 and push nodes of next level to s2
            while (!s1.empty()) {
                TreeNode temp = s1.peek();
                s1.pop();
                System.out.print(temp.val + " ");

                // Note that right is pushed before left
                if (temp.right != null)
                    s2.push(temp.right);

                if (temp.left != null)
                    s2.push(temp.left);

            }

            // Print nodes of current level from s2 and push nodes of next level to s1
            while (!s2.empty()) {
                TreeNode temp = s2.peek();
                s2.pop();
                System.out.print(temp.val + " ");

                // Note that left is pushed before right
                if (temp.left != null)
                    s1.push(temp.left);
                if (temp.right != null)
                    s1.push(temp.right);
            }
        }
    }

    /**
     * Recursion, DFS
     *
     printSpiral(tree)
        bool ltr = 0;
        for d = 1 to height(tree)
            printGivenLevel(tree, d, ltr);
            ltr ~= ltr //flip ltr

     printGivenLevel(tree, level, ltr)
         if tree is NULL then return;
         if level is 1, then
             print(tree->data);
         else if level greater than 1, then
             if(ltr)
                 printGivenLevel(tree->left, level-1, ltr);
                 printGivenLevel(tree->right, level-1, ltr);
             else
                 printGivenLevel(tree->right, level-1, ltr);
                 printGivenLevel(tree->left, level-1, ltr);

     *
     */

    // Function to print the spiral traversal of tree
    void printSpiral(TreeNode node) {
        int h = height(node);
        int i;

        /* ltr -> left to right. If this variable is set then the
           given label is transversed from left to right */
        boolean ltr = false;
        for (i = 1; i <= h; i++) {
            printGivenLevel(node, i, ltr);

            /*Revert ltr to traverse next level in opposite order*/
            ltr = !ltr;
        }

    }

    /* Compute the "height" of a tree -- the number of
    nodes along the longest getPath from the root node
    down to the farthest leaf node.*/
    int height(TreeNode node) {
        if (node == null)
            return 0;
        else {

            /* compute the height of each subtree */
            int lheight = height(node.left);
            int rheight = height(node.right);

            /* use the larger one */
            if (lheight > rheight)
                return (lheight + 1);
            else
                return (rheight + 1);
        }
    }

    /* Print nodes at a given level */
    void printGivenLevel(TreeNode node, int level, boolean ltr) {
        if (node == null)
            return;
        if (level == 1)
            System.out.print(node.val + " ");
        else if (level > 1) {
            if (ltr) {
                printGivenLevel(node.left, level - 1, ltr);
                printGivenLevel(node.right, level - 1, ltr);
            } else {
                printGivenLevel(node.right, level - 1, ltr);
                printGivenLevel(node.left, level - 1, ltr);
            }
        }
    }

}
