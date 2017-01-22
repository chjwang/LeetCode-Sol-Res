package com.freetymekiyan.algorithms.Other;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

/**
 Given a binary tree, print its perimeter:

 node, left->most nodes from top to bottom, leaf nodes from left-> right, right->most nodes from bottom to top

 ----------------------------1
 -----------------------2--------3
 ------------------4-----5-----6--------7
 -------------8------9-----10------11-----12

 should print:
 1-2-4-8-9-5-10-11-12-7-3

 5 because it doesn't have any children. 10 and 11 are children of 6 and 8 & 9 are children of 4.
 */
public class BinaryTreePerimeter {
    /**
     * To solve this problem, you first need to pay attention to these three key areas:

     - Printing the leftmost edges from top to bottom. When a node is a leftmost edge, its left child must also be a leftmost edge.
     - Printing the leaves from left to right. Remember depth-first traversal?
     - Printing the rightmost edges. You would need to print from bottom to top. The key is printing in the opposite order.
       Easy if you know how to manipulate recursion into a stack-based solution. Remember post-order traversal?

     We could divide the tree into two subtrees. One is the left subtree, and the other is the right subtree.
     For the left subtree, we print the leftmost edges from top to bottom. Then we print its leaves from left to right.
     For the right subtree, we print its leaves from left to right, then its rightmost edges from bottom to top.

     Printing the leaves from left to right order is a classic example of depth-first traversal.
     If you are not familiar with depth-first traversal, you should attempt other questions such as
     Maximum Height of a Binary Tree, Populating Next Right Pointers in Each Node.
     How do you know if a node is a leaf? Easy, if the node does not have both left and right children.

     Printing the leftmost edges is just a trivial addition to the depth-first traversal.
     When a node is a leftmost edge, then its left child must also be a leftmost edge. We could use
     a variable to pass this information down the tree. Please note that you must add the condition
     to avoid printing the node twice (a node could be a leftmost edge as well as a leaf node).

     Printing the rightmost edges from bottom to top is easy if you realize the trick of manipulating
     recursion as a stack. Try relate this with how post-order traversal works.
     When calling recursive functions, function calls are pushed onto a stack, and therefore you could
     use the implicit stack to your advantage. This trick is especially handy when applied in situation
     where you need to reverse the order of operations.
     See: Recursion to the Rescue! and Reversing Linked List Iteratively and Recursively for more practice using this trick.
     */

    public void printLeftEdges(TreeNode p, boolean print) {
        if (p == null) return;
        if (print || (p.left == null && p.right == null))
            System.out.print(p.val + " ");
        printLeftEdges(p.left, print);
        printLeftEdges(p.right, (print && p.left == null ? true : false));
    }

    public void printRightEdges(TreeNode p, boolean print) {
        if (p == null) return;
        printRightEdges(p.left, (print && p.right == null ? true : false));
        printRightEdges(p.right, print);
        if (print || (p.left == null && p.right == null))
            System.out.print(p.val + " ");
    }

    public void printOuterEdges(TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        printLeftEdges(root.left, true);
        printRightEdges(root.right, true);
    }
}
