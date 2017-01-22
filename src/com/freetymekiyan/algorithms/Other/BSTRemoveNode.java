package com.freetymekiyan.algorithms.Other;

/**
 * Created by charles_wang1 on 12/26/16.
 */
public class BSTRemoveNode {
    BinarySearchTree bst = new BinarySearchTree(1);
}
class BinarySearchTree {
    BSTNode root;

    public BinarySearchTree(int i) {
        root = new BSTNode(i);
    }

    public boolean remove(int value) {
        if (root == null)
            return false;
        else {
            if (root.getValue() == value) {
                BSTNode auxRoot = new BSTNode(0);
                auxRoot.setLeft(root);

                boolean result = root.remove(value, auxRoot);
                root = auxRoot.getLeft();
                return result;
            } else {
                return root.remove(value, null); // no need for parent
            }
        }
    }
}
class BSTNode {
    int value;
    BSTNode left, right;

    public BSTNode(int i) {
        value = i;
    }

    public boolean remove(int value, BSTNode parent) {
        if (value < this.value) {
            if (left != null)
                return left.remove(value, this);
            else
                return false;
        } else if (value > this.value) {
            if (right != null)
                return right.remove(value, this);
            else
                return false;
        } else { // value == this.value
            if (left != null && right != null) {
                this.value = right.minValue(); // now both nodes have same value
                right.remove(this.value, this);
            } else if (parent.left == this) { // only left or right child or no child
                parent.left = (left != null) ? left : right;
            } else if (parent.right == this) {
                parent.right = (left != null) ? left : right;
            }
            return true;
        }
    }

    public int minValue() {
        if (left == null)
            return value;
        else
            return left.minValue();
    }

    public int getValue() {
        return value;
    }

    public BSTNode getLeft() {
        return left;
    }

    public BSTNode getRight() {
        return right;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setLeft(BSTNode left) {
        this.left = left;
    }

    public void setRight(BSTNode right) {
        this.right = right;
    }
}