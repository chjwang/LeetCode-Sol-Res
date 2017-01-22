//package com.freetymekiyan.algorithms.Other;
//
//import com.freetymekiyan.algorithms.utils.Utils.TreeNode;
//
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Stack;
//
///**
// Print BST Kth level alternatively
// 比如
//  5,  k = 2的话， 打印结果1 8 3 6
// /  \
// 2    7
// / \  / \
// 1  3 6  8
//
// 比如
//  5,  k = 2的话， 打印结果3 8 6
// /  \
// 2    7
//  \  / \
//  3 6  8
// */
//public class BSTKthLevelAlternatively {
//    /**
//     * 维护两个stack，一个stack先push right child，另一个先push left child
//     比如我们有这棵树，我们想打印k=3这层，
//                a1
//           /           \
//        a2             a3
//      /    \        /     \
//     a4    a5      a6      a7
//    / \   /  \   /   \    /  \
//  a8 a9 a10 a11 a12 a13 a14 a15
//
//     k = 1
//     s1: a1/a3/a2
//     s2: a1/a2/a3
//
//     k = 2
//     s1: a1/a3/a2/a5/a4
//     s2: a1/a2/a3/a6/a7
//
//     k = 3
//     s1: a1/a3/a2/a5/a4/a9/a8
//     s2: a1/a2/a3/a6/a7/a14/a15
//     print: a8 a15 a9 a14
//
//     k = 2
//     s1: a1/a3/a2/a5
//     s2: a1/a2/a3/a6
//
//     k = 3
//     s1: a1/a3/a2/a5/a11/a10
//     s2: a1/a2/a3/a6/a12/a13
//     print: a10 a13 a11 a12
//
//     finish
//     *
//     */
//
//
//    private boolean traverseToKthLevel(TreeNode root, Stack<TreeNode> stack, int k, boolean isLeft) {
//        stack.push(root);
//        if (stack.size() == k) return true; // stack size is the BST current level
//        if (isLeft) {
//            if (root.left != null) {
//                if (traverseToKthLevel(root.left, stack, k, isLeft)) return true;
//            }
//            if (root.right != null) {
//                if (traverseToKthLevel(root.right, stack, k, isLeft)) return true;
//            }
//        } else {
//            if (root.right != null) {
//                if (traverseToKthLevel(root.right, stack, k, isLeft)) return true;
//            }
//            if (root.left != null) {
//                if (traverseToKthLevel(root.left, stack, k, isLeft)) return true;
//            }
//        }
//        stack.pop();
//        return false;
//    }
//
//
//    int nextNode(Stack<TreeNode> stack, int k, boolean isLeft) {
//        int ans = stack.peek().val;
//        TreeNode here = stack.pop();
//        if (isLeft) {
//            while (!stack.isEmpty() &&
//                    ((here == stack.peek().left && stack.peek().right == null) || here == stack.peek().right)) {
//                here = stack.pop();
//            }
//            if (stack.isEmpty()) return ans;
//            traverseToKthLevel(stack.peek().right, stack, k, isLeft);
//        } else {
//            while (!stack.isEmpty() &&
//                    ((here == stack.peek().right && stack.peek().left == null) || here == stack.peek().left)) {
//                here = stack.pop();
//            }
//            if (stack.isEmpty()) return ans;
//            traverseToKthLevel(stack.peek().left, stack, k, isLeft);
//        }
//        return ans;
//    }
//
//    public List<Integer> kthLevel(TreeNode root, int k) {
//        Stack<TreeNode> leftStack = new Stack<>();
//        Stack<TreeNode> rightStack = new Stack<>();
//        if (root == null) return new LinkedList<>();
//        traverseToKthLevel(root, leftStack, k, true);
//        traverseToKthLevel(root, rightStack, k, false);
//        List<Integer> ans = new LinkedList<>();
//        while (true) {
//            int left = nextNode(leftStack, k, true);
//            int right = nextNode(rightStack, k, false);
//            if (left == right) {
//                ans.add(left);
//                return ans;
//            } else if (left < right) {
//                ans.add(left);
//                ans.add(right);
//            } else {
//                return ans;
//            }
//        }
//    }
//
//    /**
//     *
//     */
//
//    void traverseToKDistant(TreeNode node, int k, Stack stack, boolean isLeft) {
//        if (node == null)
//            return;
//
//        while (!stack.isEmpty() || node != null) {
//            // check whether current node is null
//            if (node != null) { // current node is not null
//                stack.push(node);
//                node = node.left;
//            } else { // current node is null, pop and go right
//                node = stack.pop();
//                result.add(node.val); // visit()
//                node = node.right;
//            }
//        }
//    }
//
//    public List<Integer> kthLevel2(TreeNode node, int k) {
//        if (node == null)
//            return new LinkedList<>();   // NULL check
//
//        Stack<TreeNode> stack = new Stack<>();
//        stack.push(node);
//        while (!stack.isEmpty() && stack.size() < k) {
//            TreeNode top = stack.peek();
//            if (top.left != null)
//                stack.push(top.left);
//            else
//                stack.push(top.right);
//
//            here == stack.peek().left && stack.peek().right == null
//
//            stack.push(node)
//\                    ((here == stack.peek().left && stack.peek().right == null) || here == stack.peek().right)) {
//                here = stack.pop();
//            }
//
//            stack.push();
//        }
//
//
//        if (stack.size() == k) return true; // stack size is the BST current level
//        if (isLeft) {
//            if (root.left != null) {
//                if (traverseToKthLevel(root.left, stack, k, isLeft)) return true;
//            }
//            if (root.right != null) {
//                if (traverseToKthLevel(root.right, stack, k, isLeft)) return true;
//            }
//        } else {
//            if (root.right != null) {
//                if (traverseToKthLevel(root.right, stack, k, isLeft)) return true;
//            }
//            if (root.left != null) {
//                if (traverseToKthLevel(root.left, stack, k, isLeft)) return true;
//            }
//        }
//
//
//        // Create two stacks to store alternate levels
//        Stack<TreeNode> s1 = new Stack<TreeNode>();// For levels to be printed from right to left
//        Stack<TreeNode> s2 = new Stack<TreeNode>();// For levels to be printed from left to right
//
//        // Push first level to first stack 's1'
//        s1.push(node);
//
//        // Keep printing while any of the stacks has nodes
//        while (!s1.empty() || !s2.empty()) {
//            // Print nodes of current level from s1 and push nodes of next level to s2
//            while (!s1.empty()) {
//                TreeNode temp = s1.peek();
//                s1.pop();
//                System.out.print(temp.val + " ");
//
//                // Note that right is pushed before left
//                if (temp.right != null)
//                    s2.push(temp.right);
//
//                if (temp.left != null)
//                    s2.push(temp.left);
//
//            }
//
//            // Print nodes of current level from s2 and push nodes of next level to s1
//            while (!s2.empty()) {
//                TreeNode temp = s2.peek();
//                s2.pop();
//                System.out.print(temp.val + " ");
//
//                // Note that left is pushed before right
//                if (temp.left != null)
//                    s1.push(temp.left);
//                if (temp.right != null)
//                    s1.push(temp.right);
//            }
//        }
//    }
//
//}
