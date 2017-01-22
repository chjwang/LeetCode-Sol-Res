package com.freetymekiyan.algorithms.acm;

public class BinaryTree {
    int value;
    BinaryTree left;
    BinaryTree right;
    //contructor
    public BinaryTree(int k) {
        value = k;
    }
    //we also define an in-order print method
    public void PrintInOrder() {
        inOrderTraversal(this);
        System.out.println();//add a new line for formatting
    }
    private void inOrderTraversal(BinaryTree root) {
        if(root==null) return;
        inOrderTraversal(root.left);
        System.out.print(root.value);
        inOrderTraversal(root.right);
    }

    public void postOrderRecursive() {

    }
}
