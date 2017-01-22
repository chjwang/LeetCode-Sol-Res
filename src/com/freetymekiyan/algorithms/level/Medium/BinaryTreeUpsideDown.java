package com.freetymekiyan.algorithms.level.Medium;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;
import com.sun.source.tree.Tree;

/**
 * Given a binary tree where all the right nodes are either leaf nodes with a sibling (a left node that shares the same
 * parent node) or empty, flip it upside down and turn it into a tree where the original right nodes turned into left
 * leaf nodes. Return the new root.
 * <p>
 * For example:
 * Given a binary tree {1,2,3,4,5},
 * |     1
 * |    / \
 * |   2   3
 * |  / \
 * | 4   5
 * return the root of the binary tree [4,5,2,#,#,3,1].
 * |   4
 * |  / \
 * | 5   2
 * |    / \
 * |   3   1
 * confused what "{1,#,2,3}" means? > read more on how binary tree is serialized on OJ.
 * <p>
 * Company Tags: LinkedIn
 * Tags: Tree
 * Similar Problems: (E) Reverse Linked List
 */
public class BinaryTreeUpsideDown {

    /**
     * 这题有一个重要的限制就是，整个数的任何一个右孩子都不会再生枝节，基本就是一个梳子的形状。对于树类型的题目，
     * 首先可以想到一种递归的思路：把左子树继续颠倒，颠倒完后，原来的那个左孩子的左右孩子指针分别指向原来的根节点
     * 以及原来的右兄弟节点即可。
     *
     * Recursive.
     *
     * Observations:
     * Leftmost child becomes the new root, suppose it's parent is p.
     * p's right child becomes the new root's left.
     * p itself becomes the new root's right.
     * Then set p to a leaf.
     * Return new root.
     * <p>
     * Implementation:
     * Recurse down to the leftmost leaf, which is the new root.
     * From its parent p, we can set new root's left to p's right.
     * Then new root's right to p.
     * Finally disconnect p from both its children.
     * Move on to the previous parent.
     */
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) return root;

        TreeNode parent = root;
        TreeNode left = root.left;
        TreeNode right = root.right;

        TreeNode newRoot = upsideDownBinaryTree(left);

        left.left = right;
        left.right = parent;

        // parent becomes leaf node
        parent.left = null;
        parent.right = null;

        return newRoot;
    }

    /**
     * Iterative.
     *
     * All right subtrees only have 1 node.
     * Move down along the left children.
     * While current node is not null:
     * | Store the next left child.
     * | Set current node's left to previous right.
     * | Update right to current right.
     * | Set current node's right to previous.
     * | Move on by updating prev to curr, curr to next.
     * Return prev.
     */
    public TreeNode upsideDownBinaryTreeC(TreeNode root) {
        TreeNode curr = root;
        TreeNode prev = null; // Previous root.
        TreeNode next = null; // Next node to flip.
        TreeNode right = null; // Previous right child.

        while (curr != null) {
            // Store next.
            next = curr.left;

            // Swap nodes.
            curr.left = right; // Current left is previous right.
            right = curr.right; // Update right.
            curr.right = prev; // Current right is previous root.

            // Move on.
            prev = curr;
            curr = next;
        }
        return prev;
    }

    private TreeNode out = null;
    public TreeNode upsideDownBinaryTreeD(TreeNode root) {
        TreeNode dummy = new TreeNode(0);
        dummy.left = new TreeNode(0);
        out = dummy;

        postorder(root);
        return dummy.right;
    }

    private void postorder(TreeNode root) {
        if (root == null)
            return;

        postorder(root.left);
        postorder(root.right);

        if (out.left == null) {
            out.left = root;
            out.left.left = null;
            out.left.right = null;
        } else if (out.right == null) {
            out.right = root;
            out.right.left = null;
            out.right.right = null;
        }

        if (out.left != null && out.right != null)
            out = out.right;
    }
}
