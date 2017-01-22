package com.freetymekiyan.algorithms.Other;

import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

import java.util.Iterator;
import java.util.Stack;

/**
 * Finding if a number is equal to sum of 2 nodes in a binary search tree
 */
public class BSTSum {

    public static boolean searchNumBinTree1(TreeNode node, int num) {
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        TreeNode cur1 = node;
        TreeNode cur2 = node;

        while (!stack1.isEmpty() || !stack2.isEmpty() ||
                cur1 != null || cur2 != null) {
            if (cur1 != null || cur2 != null) {
                if (cur1 != null) {
                    stack1.push(cur1);
                    cur1 = cur1.left;
                }

                if (cur2 != null) {
                    stack2.push(cur2);
                    cur2 = cur2.right;
                }
            } else {
                int val1 = stack1.peek().val;
                int val2 = stack2.peek().val;

                // need to break out of here
                if (stack1.peek() == stack2.peek()) break;

                if (val1 +  val2 == num) return true;

                if (val1 + val2 < num) {
                    cur1 = stack1.pop();
                    cur1 = cur1.right;
                } else {
                    cur2 = stack2.pop();
                    cur2 = cur2.left;
                }
            }
        }

        return false;
    }

    public static boolean searchNumBinTree(TreeNode node, int num) {
        if (node == null)
            return false;

        BinTreeIterator leftIter = new BinTreeIterator(node, true);
        BinTreeIterator rightIter = new BinTreeIterator(node, false);

        while (leftIter.hasNext() && rightIter.hasNext()) {
            TreeNode left = leftIter.next();
            TreeNode right = rightIter.next();
            int sum = left.val + right.val;
            if (sum == num) {
                return true;
            } else if (sum > num) {
                rightIter.remove();
                if (!rightIter.hasNext() || rightIter.next() == left) {
                    return false;
                }
            } else {
                leftIter.remove();
                if (!leftIter.hasNext() || leftIter.next() == right) {
                    return false;
                }
            }
        }

        return false;
    }
}

class BinTreeIterator implements Iterator<TreeNode> {
    Stack<TreeNode> stack;
    boolean leftToRight;

    public boolean hasNext() {
        return !stack.empty();
    }

    public TreeNode next() {
        return stack.peek();
    }

    public void remove() {
        TreeNode node = stack.pop();
        if (leftToRight) {
            node = node.right;
            while (node.right != null) {
                stack.push(node);
                node = node.right;
            }
        } else {
            node = node.left;
            while (node.left != null) {
                stack.push(node);
                node = node.left;
            }
        }
    }

    public BinTreeIterator(TreeNode node, boolean leftToRight) {
        stack = new Stack<TreeNode>();
        this.leftToRight = leftToRight;

        if (leftToRight) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        } else {
            while (node != null) {
                stack.push(node);
                node = node.right;
            }
        }
    }
}


class BinTreeIterator2 implements Iterator<TreeNode> {
    Stack<TreeNode> stack;
    boolean leftToRight;

    public boolean hasNext() {
        return !stack.empty();
    }

    public TreeNode next() {
        return stack.peek();
    }

    public void remove() {
        TreeNode node = stack.pop();
        if (leftToRight) {
            node = node.right;
            while (node.right != null) {
                stack.push(node);

                TreeNode leftNode=node.left;
                while (leftNode != null) {
                    stack.push(leftNode);
                    leftNode= node.left;
                }

                node = node.right;
            }
        } else {
            node = node.left;
            while (node.left != null) {
                stack.push(node);

                TreeNode rightNode=node.right;
                while (rightNode != null) {
                    stack.push(rightNode);
                    rightNode= node.right;
                }

                node = node.left;
            }
        }
    }

    public BinTreeIterator2(TreeNode node, boolean leftToRight) {
        stack = new Stack<TreeNode>();
        this.leftToRight = leftToRight;

        if (leftToRight) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        } else {
            while (node != null) {
                stack.push(node);
                node = node.right;
            }
        }
    }

    public static boolean searchNumBinTree(TreeNode node, int num) {
        if (node == null)
            return false;

        BinTreeIterator leftIter = new BinTreeIterator(node,true);
        BinTreeIterator rightIter = new BinTreeIterator(node,false);

        while (leftIter.hasNext() && rightIter.hasNext()) {
            TreeNode left = leftIter.next();
            TreeNode right = rightIter.next();
            int sum = left.val + right.val;
            if (sum == num) {
                return true;
            } else if (sum > num) {
                rightIter.remove();
                if (!rightIter.hasNext() || rightIter.next() == left) {
                    return false;
                }
            } else {
                leftIter.remove();
                if (!leftIter.hasNext() || leftIter.next() == right) {
                    return false;
                }
            }
        }

        return false;
    }

}