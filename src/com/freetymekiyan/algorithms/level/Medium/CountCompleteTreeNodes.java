package com.freetymekiyan.algorithms.level.Medium;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

/**
 * Given a complete binary tree, count the number of nodes.
 *
 * Definition of a complete binary tree from Wikipedia:
 *
 * In a complete binary tree every level, except possibly the last, is completely filled, and all nodes in the last
 * level are as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.
 *
 * Tags: Tree, Binary Search
 *
 * Similar Problems: (E) Closest Binary Search Tree Value
 */
public class CountCompleteTreeNodes {

    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = fullTreeHeight(root.left);
        int rightHeight = fullTreeHeight(root.right);

        if (leftHeight == rightHeight) {
            return (1 << leftHeight) + countNodes(root.right);
        } else {
            return (1 << rightHeight) + countNodes(root.left);
        }
    }

    private int fullTreeHeight(TreeNode root) {
        if (root == null) return 0;
        int res = 0;
        while (root != null) {
            root = root.left;
            res++;
        }
        return res;
    }

    /** This function checks if the binary tree is complete or not
     *
     * Call it like this: isComplete(root, 0, countNodes2(root));
     */
    boolean isComplete(TreeNode root, int index, int numberNodes)
    {
        // An empty tree is complete
        if (root == null)
            return true;

        // If index assigned to current node is more than
        // number of nodes in tree, then tree is not complete
        if (index >= numberNodes)
            return false;

        // Recur for left and right subtrees
        return (isComplete(root.left, 2 * index + 1, numberNodes)
                && isComplete(root.right, 2 * index + 2, numberNodes));

    }

    /* This function counts the number of nodes in a binary tree */
    int countNodes2(TreeNode root)
    {
        if (root == null)
            return (0);
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    public static boolean isFull(TreeNode  root)
// prereq: root of tree, 0 or more nodes
// post: returns true if the input tree is a full tree; false otherwise
    {

        if (root!=null)
        {
            if(root.right == null && root.left == null)
            {
                return true;
            }
            if ((root.right != null && root.left != null))
            {
                return isFull(root.left) && isFull(root.right);
            }
        }
        return false;
    }
}
