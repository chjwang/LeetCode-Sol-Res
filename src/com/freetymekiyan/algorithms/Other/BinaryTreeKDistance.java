package com.freetymekiyan.algorithms.Other;

import com.freetymekiyan.algorithms.utils.Utils;
import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

/**
 Print all nodes at distance k from a given node

 Given a binary tree, a target node in the binary tree, and an integer value k, print all the nodes
 that are at distance k from the given target node. No parent pointers are available.

 Consider the tree 20,8,22,4,12,#,#,10,14
       20
     /   \
   8     22
 /  \
4  12
   / \
  10 14

 Input: target = pointer to node with data 8, root = pointer to node with data 20, k = 2.
 Output : 10 14 22

 If target is 14 and k is 3, then output should be "4 20"

 */
public class BinaryTreeKDistance {
/**
 * There are two types of nodes to be considered.
 1) Nodes in the subtree rooted with target node. For example if the target node is 8 and k is 2,
 then such nodes are 10 and 14.
 2) Other nodes, may be an ancestor of target, or a node in some other subtree.
 For target node 8 and k is 2, the node 22 comes in this category.

 Finding the first type of nodes is easy to implement. Just traverse subtrees rooted with the target
 node and decrement k in recursive call. When the k becomes 0, print the node currently being traversed.

 How to find nodes of second type?
 For the output nodes not lying in the subtree with the target node as the root, we must go through
 all ancestors.
 For every ancestor, we find its distance from target node, let the distance be d,
 now we go to other subtree (if target was found in left subtree, then we go to right subtree and
 vice versa) of the ancestor and find all nodes at k-d distance from the ancestor.
 */

    TreeNode root;
    /* Recursive function to print all the nodes at distance k in
       tree (or subtree) rooted with given root. */

    // Driver program to test the above functions
    public static void main(String args[]) {
        /* Let us construct the tree shown in above diagram */
        BinaryTreeKDistance tree = new BinaryTreeKDistance();
        tree.root = new TreeNode(20);
        tree.root.left = new TreeNode(8);
        tree.root.right = new TreeNode(22);
        tree.root.left.left = new TreeNode(4);
        tree.root.left.right = new TreeNode(12);
        tree.root.left.right.left = new TreeNode(10);
        tree.root.left.right.right = new TreeNode(14);
        TreeNode target = tree.root.left.right;
        tree.printkdistanceNode(tree.root, target, 2);
        tree.printkdistanceNode(tree.root, target.val, 2);
    }



    /**
     *
     */
    private void printkdistanceNodeDown(TreeNode node, int k) {
        // Base Case
        if (node == null || k < 0)
            return;

        // If we reach a k distant node, print it
        if (k == 0) {
            System.out.print(node.val);
            System.out.println("");
            return;
        }

        // Recur for left and right subtrees
        printkdistanceNodeDown(node.left, k - 1);
        printkdistanceNodeDown(node.right, k - 1);
    }

    /**
     * Prints all nodes at distance k from a given target node. The k distant nodes may be upward or downward.
     *
     * @param node
     * @param target
     * @param k
     * @return distance of root from target node, returns -1 if target node is not present in tree rooted with root.
     */
    public int printkdistanceNode(TreeNode node, TreeNode target, int k) {
        // Base Case 1: If tree is empty, return -1
        if (node == null) return -1;

        // If target is same as root. Use the downward function to print all nodes at distance k in subtree rooted with target or root
        if (node == target) {
            printkdistanceNodeDown(node, k);
            return 0;
        }

        // Recursion for left subtree
        int dl = printkdistanceNode(node.left, target, k);

        // Check if target node was found in left subtree
        if (dl != -1) {
            // If root is at distance k from target, print root. Note that dl is Distance of root's left child from target
            if (dl + 1 == k) {
                System.out.print(node.val);
                System.out.println("");
            }
            // Else go to right subtree and print all k-dl-2 distant nodes. Note that the right child is 2 edges away from left child
            else
                printkdistanceNodeDown(node.right, k - dl - 2);

            // Add 1 to the distance and return value for parent calls
            return 1 + dl;
        }

        // MIRROR OF ABOVE CODE FOR RIGHT SUBTREE
        // Note that we reach here only when node was not found in left subtree
        int dr = printkdistanceNode(node.right, target, k);
        if (dr != -1) {
            if (dr + 1 == k) {
                System.out.print(node.val);
                System.out.println("");
            } else
                printkdistanceNodeDown(node.left, k - dr - 2);
            return 1 + dr;
        }

        // If target was neither present in left nor in right subtree
        return -1;
    }


    /**
     *
     */
    public void printkdistanceNode(TreeNode root, int node, int k) {
        int pl = getPathLength(root, node) - 1;
        getPath(root, node, pl, k);
    }

    public void print(TreeNode root, int node, TreeNode prev, int k,
                      boolean searchingDown) {
        if (root != null) {
            if (k == 0 && root.val != node) {
                System.out.print(" " + root.val);
            }
            if (searchingDown) {
                print(root.left, node, prev, --k, searchingDown);
                print(root.right, node, prev, k, searchingDown);
            } else {
                if (root.left != prev) {
                    print(root.left, node, prev, --k, searchingDown);
                }
                if (root.right != prev) {
                    print(root.right, node, prev, --k, searchingDown);
                }
            }
        }
    }

    public TreeNode getPath(TreeNode root, int dest, int k, int n) {
        if (root == null)
            return null;
        TreeNode x = null;
        if (root.val == dest || (x = getPath(root.left, dest, k - 1, n)) != null
                || (x = getPath(root.right, dest, k - 1, n)) != null) {
            if (k == 0) {
                print(root, dest, x, n - k, true);
            } else {
                print(root, dest, x, n - k, false);
            }

            return root;
        }
        return null;
    }

    public int getPathLength(TreeNode root, int n1) {
        if (root != null) {
            int x = 0;
            if ((root.val == n1) || (x = getPathLength(root.left, n1)) > 0
                    || (x = getPathLength(root.right, n1)) > 0) {
                return x + 1;
            }
            return 0;
        }
        return 0;
    }



    private void printkdistanceNode1(TreeNode root, TreeNode target, int k) {
        if (root != null) {
            // calculate if the target is in left or right subtree - if target is root this variable is null
            Boolean left = isLeft(root, target);
            int depth = depth(root, target, 0);

            if (depth == -1) return;
            printNodeDown(root, k);

            if (root == target) return;

            if (left) {
                if (depth > k) {
                    // print the nodes at depth-k level in left tree
                    printNode(depth - k - 1, root.left);
                } else if (depth < k) {
                    // print the nodes at right tree level k-depth
                    printNode(k - depth - 1, root.right);
                } else {
                    System.out.println(root.val);
                }
            } else {
                // similar if the target is in right subtree
                if (depth > k) {
                    // print the nodes at depth-k level in left tree
                    printNode(depth - k - 1, root.right);
                } else if (depth < k) {
                    // print the nodes at right tree level k-depth
                    printNode(k - depth - 1, root.left);
                } else {
                    System.out.println(root.val);
                }
            }
        }
    }

    private Boolean isLeft(TreeNode root, TreeNode target) {
        if (root.left == target) return Boolean.TRUE;

        return isDecendant(root.left, target);
    }

    private Boolean isDecendant(TreeNode root, TreeNode target) {
        if (root == null) return Boolean.FALSE;

        if (root == target || root.left == target || root.right == target)
            return Boolean.TRUE;

        return isDecendant(root.left, target) || isDecendant(root.right, target);
    }

    // print the nodes at depth - "level" from root
    void printNode(int level, TreeNode root) {
        if (level == 0 && root != null) {
            System.out.println(root.val);
        } else {
            printNode(level - 1, root.left);
            printNode(level - 1, root.right);
        }

    }

    // print the children of the start
    void printNodeDown(TreeNode start, int k) {
        if (start != null) {
            if (k == 0) {
                System.out.println(start.val);
            }
            printNodeDown(start.left, k - 1);
            printNodeDown(start.right, k - 1);
        }
    }

    /**
     * Get the path length from root to node
     * @param root
     * @param node
     * @param d
     * @return
     */
    private int depth(TreeNode root, TreeNode node, int d) {
        if (root == null)
            return -1;
        if (root != null && node == root) {
            return d;
        } else {
            int left = depth(root.left, node, d + 1);
            int right = depth(root.right, node, d + 1);
            if (left > right)
                return left;
            else
                return right;
        }
    }


}
