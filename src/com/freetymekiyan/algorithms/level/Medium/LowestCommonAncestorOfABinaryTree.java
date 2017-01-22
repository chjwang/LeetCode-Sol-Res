package com.freetymekiyan.algorithms.level.Medium;

import com.freetymekiyan.algorithms.utils.Utils;
import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
 * <p>
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes v and w as
 * the lowest node in T that has both v and w as descendants (where we allow a node to be a descendant of itself).”
 * <p>
 *        _______3______
 *       /              \
 *   ___5__          ___1__
 *  /      \        /      \
 * 6      _2       0       8
 *       /  \
 *      7   4
 * For example, the lowest common ancestor (LCA) of nodes 5 and 1 is 3. Another example is LCA of nodes 5 and 4 is 5,
 * since a node can be a descendant of itself according to the LCA definition.
 * <p>
 * Tags: Tree
 * Similar Problems: (E) Lowest Common Ancestor of a Binary Search Tree
 */
public class LowestCommonAncestorOfABinaryTree {


    private LowestCommonAncestorOfABinaryTree l;

    /**
     * Recursive.
     * Base case: if root is null, return null; if root is p or q, return root.
     * Recurrence relation: try to find LCA in left and right subtree.
     * If both are found, it means the two nodes are in different subtrees, root should be their LCA.
     * If one of them is null, it means not possible LCA found for p or q.
     * Then the one that is not null should be their LCA.
     *
     *
     * Using a bottom-up approach, we can improve over the top-down approach by avoiding traversing
     * the same nodes over and over again.
     *
     * We traverse from the bottom, and once we reach a node which matches one of the two nodes,
     * we pass it up to its parent. The parent would then test its left and right subtree if each
     * contain one of the two nodes.
     * If yes, then the parent must be the LCA and we pass its parent up to the root.
     * If not, we pass the lower node which contains either one of the two nodes (if the left or
     * right subtree contains either p or q), or NULL (if both the left and right subtree does not
     * contain either p or q) up.
     *
     * Sounds complicated? Surprisingly the code appears to be much simpler than the top-down one.
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        return left == null ? right : right == null ? left : root;
    }

    /**
     * Iterative. Two stacks.
     * First, DFS for p or q.
     * If we found anyone of them, copy current stack to a new stack, which keeps all its ancestors.
     * Then we try to find the other one.
     * Every time when we pop from the stack for DFS, we check whether the node is the found node's ancestor.
     * If it is, pop the top node from ancestor and update lca to it.
     * If it's not, do nothing.
     * When we find the other node, return lca.
     */
    public TreeNode lowestCommonAncestorB(TreeNode root, TreeNode p, TreeNode q) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        Deque<TreeNode> ancestors = null;
        TreeNode lca = null;
        TreeNode next = null;
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            } else {
                root = stack.pop();
                // Visit
                if (lca == null) {
                    if (root == p || root == q) {
                        ancestors = new ArrayDeque<>(stack);
                        lca = root;
                        next = lca == p ? q : p;
                    }
                } else {
                    // Update lca when root is lca's ancestor
                    if (!ancestors.isEmpty() && ancestors.peek() == root) {
                        lca = ancestors.pop();
                    }
                    if (root == next) {
                        break;
                    }
                }
                root = root.right;
            }
        }
        return lca;
    }

    public TreeNode lowestCommonAncestorC(TreeNode root, TreeNode p, TreeNode q) {
        Stack<TreeNode> sp = new Stack<>();
        Stack<TreeNode> sq = new Stack<>();

        findNode(root, p, sp);
        findNode(root, q, sq);
        TreeNode target = root;
        TreeNode temp;

        // root is top of both stacks
        while (!sp.isEmpty() && !sq.isEmpty()){
            temp = sp.pop();
            if (temp == sq.pop()) {
                target = temp;
            }
            else{
                // find the first one from two stacks which are not equal
                break;
            }
        }
        return target;
    }

    /**
     * Preorder traversal, otherwise known as depth-first search can find the path from root to a node.

     If you implement preorder traversal recursively, then when you reach the desired node, you can
     unwind your stack (of recursive calls) and construct your path in reverse.

     If you implement the preorder traversal non-recursively, then you will be building a stack
     directly, so in this case once you reach your desired node you have your path already.

     * @param root
     * @param p
     * @param s
     * @return
     */
    public boolean findNode(TreeNode root, TreeNode p, Stack<TreeNode> s) {
        if(root == null) {
            return false;
        }
        if(root == p) {
            s.push(root);
            return true;
        }else if(findNode(root.left, p, s) || findNode(root.right, p,s)) {
            s.push(root);
            return true;
        }else{
            return false;
        }
    }

    /* Python solution 1
class Solution:
    # @param {TreeNode} root
    # @param {TreeNode} p
    # @param {TreeNode} q
    # @return {TreeNode}
    def lowestCommonAncestor(self, root, p, q):
        pathP, pathQ = self.findPath(root, p), self.findPath(root, q)
        lenP, lenQ = len(pathP), len(pathQ)
        ans, x = None, 0
        while x < min(lenP, lenQ) and pathP[x] == pathQ[x]:
            ans, x = pathP[x], x + 1
        return ans

    def findPath(self, root, target):
        stack = []
        lastVisit = None
        while stack or root:
            if root:
                stack.append(root)
                root = root.left
            else:
                peek = stack[-1]
                if peek.right and lastVisit != peek.right:
                    root = peek.right
                else:
                    if peek == target:
                        return stack
                    lastVisit = stack.pop()
                    root = None
        return stack


 Python solution 2: If we can addPrereq a parent pointer to binary tree nodes:

 def LCA(root, p, q):
    vis = set()
    while p or q:
        if p:
            if p in vis:
                return p
            vis.addPrereq(p)
            p = p.parent
        if q:
            if q in vis:
                return q
            vis.addPrereq(q)
            q = q.parent
    return None
     */

    @Before
    public void setUp() {
        l = new LowestCommonAncestorOfABinaryTree();
    }

    /**
     * Iterative solution cannot pass this test the binary tree is built in Utils.
     * There is no good way to get reference to the nodes inside.
     * Only comparing the value is not enough.
     */
    @Test
    public void testExamples() {
        TreeNode root = Utils.buildBinaryTree(new Integer[]{3, 5, 1, 6, 2, 0, 8, null, null, 7, 4});
        TreeNode p = new TreeNode(5);
        TreeNode q = new TreeNode(1);
        TreeNode res = l.lowestCommonAncestor(root, p, q);
        Assert.assertNotNull(res);
        Assert.assertEquals(3, res.val);
        root =
            Utils.buildBinaryTree(
                new Integer[]{37, -34, -48, null, -100, -100, 48, null, null, null, null, -54, null, -71, -22, null,
                              null, null, 8});
        p = new TreeNode(-100);
        q = new TreeNode(-71);
        res = l.lowestCommonAncestor(root, p, q);
        Assert.assertNotNull(res);
        Assert.assertEquals(-48, res.val);
    }

    @After
    public void tearDown() {
        l = null;
    }

}
