package com.freetymekiyan.algorithms.Other;

import java.util.List;

/**
 * Design a stack with operations on middle element
 *
 * How to implement a stack which will support following operations in O(1) time complexity? 1)
 * push() which adds an element to the top of stack. 2) pop() which removes an element from top of
 * stack. 3) findMiddle() which will return middle element of the stack. 4) deleteMiddle() which
 * will delete the middle element. Push and pop are standard stack operations.
 *
 * The important question is, whether to use a linked list or array for implementation of stack?
 *
 * Please note that, we need to find and delete middle element. Deleting an element from middle is
 * not O(1) for array. Also, we may need to move the middle pointer up when we push an element and
 * move down when we pop(). In singly linked list, moving middle pointer in both directions is not
 * possible.
 *
 * The idea is to use Doubly Linked List (DLL). We can delete middle element in O(1) time by
 * maintaining mid pointer. We can move mid pointer in both directions using previous and next
 * pointers.
 */
public class MiddleStack {

    DLLNode head;
    DLLNode middle;
    int size = 0;

    public void push(int val) {
        size++;
        if (head == null) {
            head = new DLLNode(val);
            middle = head;
        } else {
            DLLNode node = new DLLNode(val);
            node.right = head;
            head.left = node;
            head = node;
            if (size % 2 == 0) {
                middle = middle.left;
            }
        }
    }

    public int pop() {
        if (head == null) {
            return -1;
        }
        size--;
        int ret;
        ret = head.val;
        if (size == 0) {
            head = null;
            middle = null;
        } else {
            head = head.right;
            head.left = null;
            if (size % 2 == 1) {
                middle = middle.right;
            }
        }
        return ret;
    }

    public int findMiddle() {
        return middle.val;
    }


    public void deleteMiddle() {
        size--;
        if (middle.left != null) {
            middle.left.right = middle.right;
        }
        if (middle.right != null) {
            middle.right.left = middle.left;
        }
        if (size % 2 == 1) {
            middle = middle.right;
        } else {
            middle = middle.left;
        }
    }

    public class DLLNode {
        DLLNode left;
        DLLNode right;
        int val;

        public DLLNode(int val) {
            this.val = val;
        }
    }

}