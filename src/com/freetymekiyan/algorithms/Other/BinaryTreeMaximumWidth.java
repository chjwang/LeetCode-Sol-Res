package com.freetymekiyan.algorithms.Other;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;
import java.util.LinkedList;
import java.util.Queue;

/**
 Given a binary tree, write a function to get the maximum width of the given tree. Width of a tree is maximum of widths of all levels.

 Let us consider the below example tree.

        1
      /  \
     2    3
    / \    \
   4   5    8
  / \
 6   7
 For the above tree,
 width of level 1 is 1,
 width of level 2 is 2,
 width of level 3 is 3
 width of level 4 is 2.

 So the maximum width of the tree is 3.

 Tag: TripAdvisor
 */
@SuppressWarnings("CheckStyle")
public class BinaryTreeMaximumWidth {
/**
 * Using Level Order Traversal: recursive O(n^2)
 *
 This method mainly involves two functions. One is to count nodes at a given level (getWidth), and the other is to get
 the maximum width of the tree(getMaxWidth). getMaxWidth() makes use of getWidth() to get the width of all levels starting from root.

 //Function to print level order traversal of tree
 getMaxWidth(tree)
    maxWdth = 0
    for i = 1 to height(tree)
        width =   getWidth(tree, i);
        if(width > maxWdth)
            maxWdth  = width
    return width
 //Function to get width of a given level
 getWidth(tree, level)
     if tree is NULL then return 0;
     if level is 1, then return 1;
     else if level greater than 1, then
         return getWidth(tree->left, level-1) + getWidth(tree->right, level-1);

 Time Complexity: O(n^2) in the worst case.
 We can use Queue based level order traversal to optimize the time complexity of this method.
 The Queue based level order traversal will take O(n) time in worst case.
 */
    /* Get width of a given level */
    public int getWidth(TreeNode node, int level) {
        if (node == null)
            return 0;

        if (level == 1)
            return 1;
        else if (level > 1)
            return getWidth(node.left, level - 1)
                    + getWidth(node.right, level - 1);
        return 0;
    }

    /* UTILITY FUNCTIONS */
    /* Compute the "height" of a tree --
    the number of nodes along the longest path from the root node down to the farthest leaf node.*/
    private int height(TreeNode node) {
        if (node == null)
            return 0;
        else {
            /* compute the height of each subtree */
            int lHeight = height(node.left);
            int rHeight = height(node.right);

            /* use the larger one */
            return (lHeight > rHeight) ? (lHeight + 1) : (rHeight + 1);
        }
    }

    /**
     * Using Level Order Traversal: 1 queue O(n)
     * @param root root
     * @return
     */
    public int getWidth(final TreeNode root) {
        // Base case
        if (root == null) return 0;

        // Initialize result
        int res = 0;

        // Do Level order traversal keeping track of number of nodes at every level.
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            // Get the size of queue when the level order traversal for one level finishes
            int count = q.size();

            // Update the maximum node count value
            res = Math.max(count, res);

            // Iterate for all the nodes in the queue currently
            while (count-- > 0) {
                // Dequeue an node from queue
                TreeNode temp = q.poll();

                // Enqueue left and right children of dequeued node
                if (temp.left != null)
                    q.offer(temp.left);
                if (temp.right != null)
                    q.offer(temp.right);
            }
        }

        return res;
    }

    /**
     * Using Level Order Traversal: 2 queues O(n)
     *
     * @param root root
     * @return
     */
    public int getMaxWidth(final TreeNode root) {
        if (root == null) {
            return 0;
        }
        int maxCount = 0, count = 0;
        Queue<TreeNode> q1 = new LinkedList<TreeNode>();
        Queue<TreeNode> q2 = new LinkedList<TreeNode>();

        q1.offer(root);
        while (!q1.isEmpty()) {
            count++;
            if (null != q1.peek().left) {
                q2.add(q1.peek().left);
            }

            if (null != q1.peek().right) {
                q2.add(q1.peek().right);
            }

            q1.poll();

            if (q1.isEmpty()) {
                maxCount = (count > maxCount) ? count : maxCount;
                Queue<TreeNode> temp = q1;
                q1 = q2;
                q2 = temp;
                count = 0;
            }
        }
        return maxCount;
    }

    /**
     * Method 3 (Using Preorder Traversal)

     In this method we create a temporary array count[] of size equal to the height of tree.

     We initialize all values in count as 0.

     We traverse the tree using preorder traversal and fill the entries in count so that the count array
     contains count of nodes at each level in Binary Tree.
     * @param node root
     */
    public int getMaxWidthPreOrder(final TreeNode node) {
        int h = height(node);

        // Create an array that will store count of nodes at each level
        int[] count = new int[h];

        int level = 0;

        // Fill the count array using preorder traversal
        getMaxWidthRecur(node, count, level);

        // Return the maximum value from count array
        return getMax(count, h);
    }
    // A function that fills count array with count of nodes at every level of given binary tree
    private void getMaxWidthRecur(final TreeNode node, final int[] count, final int level) {
        if (node != null) {
            count[level]++;
            getMaxWidthRecur(node.left, count, level + 1);
            getMaxWidthRecur(node.right, count, level + 1);
        }
    }
    private int getMax(final int[] arr, final int n)
    {
        int max = arr[0];
        int i;
        for (i = 0; i < n; i++) {
            if (arr[i] > max) max = arr[i];
        }
        return max;
    }

    /* Driver program to test above functions */
    public static void main(String args[]) {
        BinaryTreeMaximumWidth btm = new BinaryTreeMaximumWidth();
        TreeNode root = null;

		/*
		Constructed bunary tree is:
			1
			/ \
		2 3
		/ \ \
		4 5 8
				/ \
				6 7 */
        root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(8);
        root.right.right.left = new TreeNode(6);
        root.right.right.right = new TreeNode(7);

        System.out.println("Maximum width is " + btm.getMaxWidth(root));
    }

}
