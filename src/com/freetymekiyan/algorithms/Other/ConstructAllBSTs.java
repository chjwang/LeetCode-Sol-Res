package com.freetymekiyan.algorithms.Other;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 Construct all possible BSTs for keys 1 to N

 How many structurally unique BSTs for keys from 1..N?

 For example, for N = 2, there are 2 unique BSTs
 1               2
 \            /
 2         1

 For N = 3, there are 5 possible BSTs
 1            3        3        2      1
 \           /        /        / \      \
  3        2         1        1   3      2
 /        /           \                   \
2        1             2                   3


 Catalan numbers are a sequence of natural numbers that occurs in many interesting counting problems like following.

 1) Count the number of expressions containing n pairs of parentheses which are correctly matched.
 For n = 3, possible expressions are ((())), ()(()), ()()(), (())(), (()()).
 2) Count the number of possible Binary Search Trees with n keys
 3) Count the number of full binary trees (A rooted binary tree is full if every vertex has either
 two children or no children) with n+1 leaves.

 The first few Catalan numbers for n = 0, 1, 2, 3, … are 1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862, …

 Recursive Solution
 Catalan numbers satisfy the following recursive formula.
 c(0) = 1
 c(n+1) = sum( c(i) * c(n-i) ) for n>=0

 */
public class ConstructAllBSTs {
    /**
     * The idea is to maintain a list of roots of all BSTs.
     * Recursively construct all possible left and right subtrees.
     * Create a tree for every pair of left and right subtree and addPrereq the tree to list.
     *
     * Below is detailed algorithm.

     1) Initialize list of BSTs as empty.

     2) For every number i where i varies from 1 to N, do following
        a)  Create a new node with key as 'i', let this node be 'node'
        b)  Recursively construct list of all left subtrees.
        c)  Recursively construct list of all right subtrees.
     3) Iterate for all left subtrees
        a) For current leftsubtree, iterate for all right subtrees
         Add current left and right subtrees to 'node' and addPrereq 'node' to list.
     */
    public List<TreeNode> constructTrees(int start, int end) {
        List<TreeNode> list = new ArrayList<>();

    /*  if start > end then subtree will be empty so returning NULL in the list */
        if (start > end) {
            list.add(null);
            return list;
        }

    /*  iterating through all values from start to end  for constructing\
        left and right subtree recursively  */
        for (int i = start; i <= end; i++) {
        /*  constructing left subtree   */
            List<TreeNode> leftSubtree  = constructTrees(start, i - 1);

        /*  constructing right subtree  */
            List<TreeNode> rightSubtree = constructTrees(i + 1, end);

        /*  now looping through all left and right subtrees and connecting them to ith root  below*/
            for (int j = 0; j < leftSubtree.size(); j++) {
                TreeNode left = leftSubtree.get(j);
                for (int k = 0; k < rightSubtree.size(); k++) {
                    TreeNode right = rightSubtree.get(k);
                    TreeNode node = new TreeNode(i);// making value i as root
                    node.left = left;              // connect left subtree
                    node.right = right;            // connect right subtree
                    list.add(node);           // addPrereq this tree to list
                }
            }
        }
        return list;
    }
}

class CatalanNumber {

    // A recursive function to find nth catalan number

    int catalan(int n) {
        int res = 0;

        // Base case
        if (n <= 1) {
            return 1;
        }
        for (int i = 0; i < n; i++) {
            res += catalan(i) * catalan(n - i - 1);
        }
        return res;
    }

    /**
     * time O(n2)
     * @param n
     * @return
     */
    int catalanDP(int n) {
        // Table to store results of subproblems
        int[] catalan = new int[n+1];

        // Initialize first two values in table
        catalan[0] = catalan[1] = 1;

        // Fill entries in catalan[] using recursive formula
        for (int i=2; i<=n; i++) {
            catalan[i] = 0;
            for (int j=0; j<i; j++)
                catalan[i] += catalan[j] * catalan[i-j-1];
        }
        // Return last entry
        return catalan[n];
    }

    public static void main(String[] args) {
        CatalanNumber cn = new CatalanNumber();
        for (int i = 0; i < 10; i++) {
            System.out.print(cn.catalan(i) + " ");
        }
    }
}
