package com.freetymekiyan.algorithms.Other;


import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

// Recursive Java program to find the diameter of a
// Binary Tree

/**
 * The diameter of a tree T is the largest of the following quantities:
 *  the diameter of T’s left subtree
 *  the diameter of T’s right subtree
 *  the longest getPath between leaves that goes through the root of T
 *  (this can be computed from the heights of the subtrees of T)
 */
public class BinaryTreeDiameter {
    public static void main(String args[]) {
        /* creating a binary tree and entering the nodes */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        System.out.println("The diameter of given binary tree is : " + diameter(root));
    }

    /*
    The function Compute the "height" of a tree.

    Height is the number of nodes along the longest getPath from the root node down to the farthest leaf node.*/
    public static int getHeight(TreeNode node) {
        /* base case tree is empty */
        if (node == null)
            return 0;

        /* If tree is not empty then getHeight = 1 + max of left
           getHeight and right heights */
        return (1 + Math.max(getHeight(node.left), getHeight(node.right)));
    }

    /* define height = 0 globally and  call diameterOpt(root,getHeight)
       from main */
    public static int diameterOpt(TreeNode root, Height height) {
        /* lh --> Height of left subtree
           rh --> Height of right subtree */
        Height lh = new Height(), rh = new Height();

        if (root == null) {
            height.h = 0;
            return 0; /* diameter is also 0 */
        }

        /* ldiameter  --> diameter of left subtree
           rdiameter  --> Diameter of right subtree

           Get the heights of left and right subtrees in lh and rh and store the returned values in
           ldiameter and rdiameter
           */
//        lh.h = height(root.left); // can be optimized by calculating height in the same recursion as below
//        rh.h = height(root.right);
        lh.h++;
        rh.h++;

        int ldiameter = diameterOpt(root.left, lh);
        int rdiameter = diameterOpt(root.right, rh);

        /* Height of current node is max of heights of left and right subtrees plus 1*/
        height.h = Math.max(lh.h, rh.h) + 1;

         /* Return max of following three
         1) Diameter of left subtree
         2) Diameter of right subtree
         3) Height of left subtree + height of right subtree + 1
         */
        return Math.max(lh.h + rh.h + 1, Math.max(ldiameter, rdiameter));
    }

    /* A wrapper over diameter(TreeNode root) */
    public static int diameter(TreeNode root) {
        Height height = new Height();
        return diameterOpt(root, height);
    }

    /**
     * The function Compute the "height" of a tree.
     * Height is the number of nodes along the longest getPath from the root node down to the farthest
     * leaf node.
     */
    static int height(TreeNode node) {
        /* base case tree is empty */
        if (node == null)
            return 0;

        /* If tree is not empty then height = 1 + max of left height and right heights */
        return (1 + Math.max(height(node.left), height(node.right)));
    }

    // A utility class to pass heigh object because Integer class is immutable
    static class Height {
        int h;
    }

    public static int getDiameter2(TreeNode root) {
        if (root == null)
            return 0;

        int rootDiameter = getHeight(root.left) + getHeight(root.right) + 1;
        int leftDiameter = getDiameter2(root.left);
        int rightDiameter = getDiameter2(root.right);

        return Math.max(rootDiameter, Math.max(leftDiameter, rightDiameter));
    }

    /**
     * calculates height and diameter at the same time.
     *
     * And since Java does not have pass-by-reference we defined an int[] to return the result.
     *
     * @param root
     * @return
     */
    public static int[] getDiameter2Optimized(TreeNode root) {
        int[] result = new int[]{0,0};    //1st element: diameter, 2nd: height
        if (root == null)  return result;

        int[] leftResult = getDiameter2Optimized(root.left);
        int[] rightResult = getDiameter2Optimized(root.right);

        int height = Math.max(leftResult[1], rightResult[1]) + 1;
        int rootDiameter = leftResult[1] + rightResult[1] + 1;
        int leftDiameter = leftResult[0];
        int rightDiameter = rightResult[0];

        result[0] = Math.max(rootDiameter, Math.max(leftDiameter, rightDiameter));
        result[1] = height;

        return result;
    }
}