package com.freetymekiyan.algorithms.Other;

import com.freetymekiyan.algorithms.utils.Utils.ListNode;
import com.freetymekiyan.algorithms.utils.Utils.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Convert BST to heap
 *
 If we are allowed to use extra space, we can perform inorder traversal of the tree and store the
 keys in an auxiliary array. As we’re doing inorder traversal on a BST, array will be sorted.
 Finally, we construct a complete binary tree from the sorted array.

 We construct the binary tree level by level and from left to right by taking next minimum element
 from sorted array. The constructed binary tree will be a min-Heap.

 This solution works in O(n) time, but is not in-place.

 How to do it in-place?

 The idea is to convertPostfix2Infix the binary search tree into a sorted LinkedList first.
 We can do this by traversing the BST in inorder fashion. We add nodes at the beginning of current
 linked list and update head of the list using pointer to head pointer. Since we insert at the beginning,
 to maintain sorted order, we first traverse the right subtree before the left subtree. i.e.
 do a reverse inorder traversal.

 Finally we convertPostfix2Infix the sorted linked list into a min-Heap by setting the left and right pointers
 appropriately. We can do this by doing a Level order traversal of the partially built Min-Heap Tree
 using queue and traversing the linked list at the same time. At every step, we take the parent node
 from queue, make next two nodes of linked list as children of the parent node, and enqueue the next
 two nodes to queue. As the linked list is sorted, the min-heap property is maintained.

 */
public class BST2MinHeap {
    // Function to convertPostfix2Infix BST into a Min-Heap without using any extra space
    public TreeNode BSTToMinHeap(TreeNode root) {
        // head of Linked List
        ListNode head = null;

        // Convert a given BST to Sorted Linked List
        head = BSTToSortedLL(root);

        // Convert Sorted Linked List to Min-Heap
        root = SortedLLToMinHeap(head);
        return root;
    }

    // Utility function to print Min-heap level by level
    private void printLevelOrder(TreeNode root) {
        // Base Case
        if (root == null)  return;

        // Create an empty queue for level order traversal
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(root);

        while (!q.isEmpty()) {
            int nodeCount = q.size();
            while (nodeCount > 0) {
                TreeNode node = q.poll();
                System.out.print(node.val + " ");

                if (node.left != null)
                    q.offer(node.left);
                if (node.right != null)
                    q.offer(node.right);
                nodeCount--;
            }
            System.out.println();
        }
    }

    /**
     A simple recursive function to convertPostfix2Infix a given Binary Search tree to Sorted Linked List

     * @param root Root of Binary Search Tree
     * @return Pointer to head node of created linked list
     */
    ListNode BSTToSortedLL(TreeNode root) {
        // Base cases
        if (root == null) return null;

        // Recursively convertPostfix2Infix right subtree
        ListNode rt = BSTToSortedLL(root.right);

        // Recursively convertPostfix2Infix left subtree
        ListNode lt = BSTToSortedLL(root.left);

        // insert root into linked list
        ListNode root2 = new ListNode(root.val);
        root2.next = rt;

        ListNode ln = lt;
        while (ln.next != null) ln = ln.next;

        ln.next = root2;
        return lt;
    }

    // Function to convertPostfix2Infix a sorted LinkedList to Min-Heap.
    // root --> Root of Min-Heap
    // head --> Pointer to head node of sorted linked list
    private TreeNode SortedLLToMinHeap(ListNode root) {
        // Base Case
        if (root == null) return null;

        // queue to store the parent nodes
        Queue<TreeNode> q = new LinkedList<>();

        // The first node is always the root node
        TreeNode head = new TreeNode(root.val);
        TreeNode res = head;

        // advance the pointer to the next node
        root = root.next;

        // set right child to NULL
        head.right = null;

        // add first node to the queue
        q.offer(head);

        // run until the end of linked list is reached
        while (root != null) {
            // Take the parent node from the q and remove it from q
            TreeNode parent = q.poll();

            // Take next two nodes from the linked list and
            // Add them as children of the current parent node
            // Also in push them into the queue so that
            // they will be parents to the future nodes
            TreeNode leftChild = head;
            head = head.right;        // advance linked list to next node
            leftChild.right = null; // set its right child to NULL
            q.offer(leftChild);

            // Assign the left child of parent
            parent.left = leftChild;

            if (head != null) {
                TreeNode rightChild = head;
                head = head.right; // advance linked list to next node
                rightChild.right = null; // set its right child to NULL
                q.offer(rightChild);

                // Assign the right child of parent
                parent.right = rightChild;
            }
        }
        return res;
    }


    public TreeNode BSTToMinHeap2(TreeNode root) {
        BSTToMinHeap(root.left);
        BSTToMinHeap(root.right);
        swap(root, root.left);
//        heapify(root.left);
        return root;
    }
    private static void swap(TreeNode a, TreeNode b)
    {
        int tmp = a.val;
        a.val = b.val;
        b.val = tmp;
    }
    public void heapify(int[] A) {
        for (int i = A.length / 2; i >= 0; i--) {
            siftdown(A, i);
        } // for
    }
    /**
     * @param A: Given an integer array
     * @return: void
     */
    private void siftdown(int[] A, int k) {
        while (k < A.length) {
            int smallest = k;
            if (k * 2 + 1 < A.length && A[k * 2 + 1] < A[smallest]) {
                smallest = k * 2 + 1;
            }
            if (k * 2 + 2 < A.length && A[k * 2 + 2] < A[smallest]) {
                smallest = k * 2 + 2;
            }
            if (smallest == k) {
                break;
            }
            int temp = A[smallest];
            A[smallest] = A[k];
            A[k] = temp;

            k = smallest;
        }
    }

}
