package com.freetymekiyan.algorithms.level.Easy;

import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * Given a non-empty binary search tree and a target value, find k values in the BST that are
 * closest to the target.
 *
 * Note:
 *
 * Given target value is a floating point. You may assume k is always valid, that is: k ≤ total
 * nodes. You are guaranteed to have only one unique set of k values in the BST that are closest to
 * the target.
 *
 *
 * Follow up: Assume that the BST is balanced, could you solve it in less than O(n) runtime (where n
 * = total nodes)?
 *
 *
 * Hint:
 *
 * Consider implement these two preOrderHelper functions: getPredecessor(N), which returns the next smaller
 * node to N. getSuccessor(N), which returns the next larger node to N.
 *
 * Try to assume that each node has a parent pointer, it makes the problem much easier.
 *
 * Without parent pointer we just need to keep track of the getPath from the root to the current node
 * using a stack.
 *
 * You would need two stacks to track the getPath in finding predecessor and successor node
 * separately.
 *
 * [思路] prefix traverse. 同时维护一个大小为k的 max heap. 注意根据bst的性质, 在diff 大于 maxHeap时, 可以只遍历一边的子树.
 */
public class ClosestBSTValueII {
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        PriorityQueue<DiffNode> maxHeap = new PriorityQueue<>(k, (x, y) -> (int) (y.diff - x.diff));

        closestKValuesHelper(root, target, k, maxHeap);

        List<Integer> diffs;
        diffs = maxHeap.stream()
                .map((diffNode) -> (int) diffNode.node.val)
                .collect(Collectors.toList());

        return diffs;
    }

    private void closestKValuesHelper(TreeNode root, double target, int k, PriorityQueue<DiffNode> maxHeap) {
        if (root == null) return;

        double diff = Math.abs(root.val - target);
        DiffNode diffNode = new DiffNode(diff, root);

        if (maxHeap.size() < k || diff < maxHeap.peek().diff) {
            if (diff < maxHeap.peek().diff) {
                maxHeap.poll();
            }
            maxHeap.offer(diffNode);
            closestKValuesHelper(root.left, target, k, maxHeap);
            closestKValuesHelper(root.right, target, k, maxHeap);
        } else {
            if (root.val > target)
                closestKValuesHelper(root.left, target, k, maxHeap);
            else
                closestKValuesHelper(root.right, target, k, maxHeap);
        }
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public class DiffNode {
        double diff;
        TreeNode node;

        DiffNode(double x, TreeNode node) {
            diff = x;
            this.node = node;
        }
    }
}
