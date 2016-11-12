package com.freetymekiyan.algorithms.level.Medium;

import java.util.LinkedList;

/**
 * Given a binary tree
 * 
 *     struct TreeLinkNode {
 *       TreeLinkNode *left;
 *       TreeLinkNode *right;
 *       TreeLinkNode *next;
 *     }
 * 
 * Populate each next pointer to point to its next right node. If there is no
 * next right node, the next pointer should be set to NULL.
 * 
 * Initially, all next pointers are set to NULL.
 * 
 * Note:
 * 
 * You may only use constant extra space.
 * You may assume that it is a perfect binary tree (ie, all leaves are at the
 * same level, and every parent has two children).
 * For example,
 * Given the following perfect binary tree,
 *          1
 *        /  \
 *       2    3
 *      / \  / \
 *     4  5  6  7
 * 
 * After calling your function, the tree should look like:
 *          1 -> NULL
 *        /  \
 *       2 -> 3 -> NULL
 *      / \  / \
 *     4->5->6->7 -> NULL
 * 
 * Tags: Tree, DFS
 */
class PopulatingNextRight {
    public static void main(String[] args) {
        
    }
    
    /**
     * Iterative
     * Store node in previous level
     */
    public void connect(TreeLinkNode root) {
        if (root == null) return;

        TreeLinkNode pre = root;
        TreeLinkNode cur = null;

        while (pre.left != null && pre.right != null) { // no more level if left and child are both null
            cur = pre;
            while (cur != null) { // work on next level

                cur.left.next = cur.right; // connect left and right

                // connect right child with next node's left child
                if (cur.next != null)
                    cur.right.next = cur.next.left;

                cur = cur.next; // move current to next node
            }
            pre = pre.left; // move to next level
        }
    }

    /**
     * Use 4 pointers to move towards right on two levels
     *
     * @param root
     */
    public void connect1(TreeLinkNode root) {
        if(root == null)
            return;

        TreeLinkNode lastHead = root;//prevous level's head
        TreeLinkNode lastCurrent = null;//previous level's pointer
        TreeLinkNode currentHead = null;//currnet level's head
        TreeLinkNode current = null;//current level's pointer

        while(lastHead!=null){
            lastCurrent = lastHead;

            while(lastCurrent!=null){
                if(currentHead == null){
                    currentHead = lastCurrent.left;
                    current = lastCurrent.left;
                }else{
                    current.next = lastCurrent.left;
                    current = current.next;
                }

                if(currentHead != null){
                    current.next = lastCurrent.right;
                    current = current.next;
                }

                lastCurrent = lastCurrent.next;
            }

            //update last head
            lastHead = currentHead;
            currentHead = null;
        }

    }

    /**
     * Use two Qs: one for DFS, another to keep the corresponding node depth
     * @param root
     */
    public void connect2(TreeLinkNode root) {
        if(root==null)
            return;

        LinkedList<TreeLinkNode> nodeQueue = new LinkedList<>();
        LinkedList<Integer> depthQueue = new LinkedList<>();

        if (root != null) {
            nodeQueue.offer(root);
            depthQueue.offer(1);
        }

        while (! nodeQueue.isEmpty()) {
            TreeLinkNode topNode = nodeQueue.poll();
            int depth = depthQueue.poll();

            if (depthQueue.isEmpty() || depthQueue.peek() > depth) { // last node of the tree or last node on this depth level
                topNode.next = null;
            } else {
                topNode.next = nodeQueue.peek(); // next sibling on this depth level
            }

            if (topNode.left != null) { // left child
                nodeQueue.offer(topNode.left);
                depthQueue.offer(depth+1);
            }

            if (topNode.right != null) { // right child
                nodeQueue.offer(topNode.right);
                depthQueue.offer(depth+1);
            }
        }
    }

    static class TreeLinkNode{
        TreeLinkNode left;
        TreeLinkNode right;
        TreeLinkNode next;
    }
}