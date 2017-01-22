package com.freetymekiyan.algorithms.utils;

import java.security.InvalidParameterException;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Utility class.
 */
public class Utils {

    public static ListNode buildLinkedList(int[] values) {
        ListNode head = new ListNode(values[0]);
        ListNode cur = head;
        for (int i = 1; i < values.length; i++) {
            cur.next = new ListNode(values[i]);
            cur = cur.next;
        }
        return head;
    }

    public static TreeNode buildBinaryTree(Integer[] values) {
        if (values == null || values.length == 0) {
            throw new InvalidParameterException("values should not be null or empty");
        }
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();
            if (i < values.length && values[i] != null) {
                node.left = new TreeNode(values[i]);
                queue.offer(node.left);
            }
            if (i + 1 < values.length && values[i + 1] != null) {
                node.right = new TreeNode(values[i + 1]);
                queue.offer(node.right);
            }
            i += 2;
        }
        return root;
    }

    public static class ListNode {

        public int val;
        public ListNode next;

        public ListNode(int x) {
            val = x;
        }
    }

    public static class TreeNode {

        public int val, maxArrIndex;
        public TreeNode left;
        public TreeNode right;
        public boolean visited;

        public TreeNode(int x) {
            val = x;
        }

        public TreeNode(int x, TreeNode l, TreeNode r) {
            val = x;
            left = l;
            right = r;
        }


        public boolean isVisited() {
            return visited;
        }

        public void setVisited() {
            visited = true;
        }

        public String toString() {
            if (this.isLeaf()) {
                return "[" + this.val + "]";
            }else {
                String result = "(";
                result += this.val + " ";
                result += this.left == null ? "NULL " : this.left.toString();
                result += this.right == null ? "NULL " : this.right.toString();
                result += ")";
                return result;
            }
        }

        private boolean isLeaf() {
            return left==null && right==null;
        }
    }

    public class MaxHeap {
        private int heapSize;
        private TreeNode[] maxHeap = null;

        //Constructor heapsize - 0.
        public MaxHeap()
        {
            this.heapSize = 0;
        }

        //Building new heap
        public void buildHeap(int[] arr)
        {
            this.heapSize = arr.length - 1;
            for (int i = arr.length - 1; i >= 0; i--)
            {
                heapify(maxHeap, i);
            }
        }

        //Insert new value to the heap
        public void insert(int x)
        {
            heapSize = heapSize + 1;
            TreeNode newNode = new TreeNode(x);
            insertMaxHeap(newNode);

        }

        //Finding max value in the heap
        public int findMax()
        {
            return maxHeap[0].val;
        }
        //Insert a new node to the maxheap
        private void insertMaxHeap(TreeNode newNode)
        {
            int i = heapSize;
            while (i > 0 && (maxHeap[(i - 1) / 2].val < newNode.val))
            {
                TreeNode node = new TreeNode(maxHeap[(i - 1) / 2].val);
                node.maxArrIndex = maxHeap[(i - 1) / 2].maxArrIndex;
                maxHeap[i] = node;
                i = (i - 1) / 2;
            }
            newNode.maxArrIndex = i;
            maxHeap[i] = newNode;
        }
        //Heapify the structure
        private void heapify(TreeNode[] heap, int index)
        {
            int largestChild = index;
            int leftChild = (2 * index) + 1;
            int rightChild = (2 * index) + 2;
            if (leftChild <= heapSize && heap[leftChild].val > heap[largestChild].val)
            {
                largestChild = leftChild;
            }
            if (rightChild <= heapSize && heap[rightChild].val > heap[largestChild].val)
            {
                largestChild = rightChild;
            }
            if (largestChild != index)
            {
                exchange(heap, index, largestChild);
                heapify(heap, largestChild);
            }
        }
        private void exchange (TreeNode[] heap, int index, int largestChild)
        {
            int temp = heap[index].val;
            heap[index].val = heap[largestChild].val;
            heap[largestChild].val = temp;

        }
        public TreeNode[] getMaxHeap()
        {
            return maxHeap;
        }


    }
}
