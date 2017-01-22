package com.freetymekiyan.algorithms.level.Medium;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

/**
 Given a binary tree, find the length of the longest consecutive sequence path.
 The path refers to any sequence of nodes from some starting node to any node in the tree along the
 parent-child connections. The longest consecutive path need to be from parent to child (cannot be the reverse).
 For example,
 1
 \
 3
 / \
 2  4
     \
     5
 Longest consecutive sequence path is 3-4-5, so return 3.
    2
     \
     3
   /
  2
 /
 1
 Longest consecutive sequence path is 2-3,not3-2-1, so return 2.

 [思路]
 递归, 连续就+1.
 */
public class BinaryTreeLongestConsecutiveSequence {
    int max = 1; // global variable to store the longest seq number so far

    public int longestConsecutive(TreeNode root) {
        if (root == null) return 0;
        longestConsecutiveHelper(root, 1);
        return max;
    }

    private void longestConsecutiveHelper(TreeNode n, int c) {
        if (n.left != null) {
            if (n.val + 1 == n.left.val) {
                longestConsecutiveHelper(n.left, c + 1);
                max = Math.max(max, c + 1);
            } else
                longestConsecutiveHelper(n.left, 1);
        }

        if (n.right != null) {
            if (n.val + 1 == n.right.val) {
                longestConsecutiveHelper(n.right, c + 1);
                max = Math.max(max, c + 1);
            } else
                longestConsecutiveHelper(n.right, 1);
        }
    }
}
