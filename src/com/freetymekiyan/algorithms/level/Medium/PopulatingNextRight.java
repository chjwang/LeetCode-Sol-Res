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
     * Iterative, only works for perfect binary tree
     * Store node in previous level
     *
     * Time: O(n) Space: O(1)
     */
    public void connect(TreeLinkNode root) {
        if (root == null) return;

        TreeLinkNode prev = root; // prev node on current level
        TreeLinkNode cur = null; // current node on current level

        while (prev.left != null && prev.right != null) {
            // no more level if left and child are both null - assumption works only for perfect binary tree

            cur = prev;
            while (cur != null) { // work on next level

                cur.left.next = cur.right; // connect left and right

                // connect right child with next node's left child
                if (cur.next != null)
                    cur.right.next = cur.next.left;

                cur = cur.next; // move current to next node
            }
            prev = prev.left; // move to next level
        }
    }

    /**
     * Use 4 pointers to move towards right on two levels
     *
     * Time: O(n) Space: O(1)
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
     * 3 pointers: cur, head, prev. Most elegant solution.
     *
     * Use dummy node, whose next pointer points to next level's first node but itself is a dummy node
     *
     * Time: O(n) Space: O(1)
     *
     * @param root root
     */
    public void connect2(TreeLinkNode root) {
        //dummy is the leading node on the next level
        TreeLinkNode dummy = new TreeLinkNode();

        //prev is the node before on the next level
        TreeLinkNode prev = dummy;

        TreeLinkNode cur = root;

        while(cur != null) {
            if(cur.left != null) {
                prev.next = cur.left;
                prev = prev.next;
            }
            if(cur.right != null) {
                prev.next = cur.right;
                prev = prev.next;
            }
            cur = cur.next;

            if(cur == null) { // done with current level
                // move on to next level
                cur = dummy.next;

                // reset prev and dummy pointers to default values
                prev = dummy;
                dummy.next = null;
            }
        }

    }

    /**
     * based on level order traversal, 3 pointers. Similar to connect2 but not as elegant
     *
     * Time: O(n) Space: O(1)
     *
     * @param root
     */
    public void connect3(TreeLinkNode root) {

        TreeLinkNode head = null; //head of the next level, not a dummy node
        TreeLinkNode prev = null; //the leading node on the next level
        TreeLinkNode cur = root;  //current node of current level

        while (cur != null) {

            while (cur != null) { //iterate on the current level
                //left child
                if (cur.left != null) {
                    if (prev != null) {
                        prev.next = cur.left;
                    } else {
                        head = cur.left;
                    }
                    prev = cur.left;
                }
                //right child
                if (cur.right != null) {
                    if (prev != null) {
                        prev.next = cur.right;
                    } else {
                        head = cur.right;
                    }
                    prev = cur.right;
                }
                //move to next node
                cur = cur.next;
            }

            //move to next level
            cur = head;
            head = null;
            prev = null;
        }

    }

    /**
     * 3 pointers
     * @param root
     * Time: O(n) Space: O(1)
     */
    public void connect4(TreeLinkNode root) {
        TreeLinkNode cur = root;  //current node of current level
        TreeLinkNode dummy = null; // dummy node on next level, whose next points to head of next level
        TreeLinkNode prev = null; //the prev node on the next level

        while (root != null) {
            dummy = new TreeLinkNode();
            prev = dummy;

            while (cur != null) { // process current level
                if (cur.left != null) {
                    prev.next = cur.left;
                    prev = prev.next;
                }
                if (cur.right != null) {
                    prev.next = cur.right;
                    prev = prev.next;
                }
                cur = cur.next;
            }
            cur = dummy.next; // move on to next level
        }
    }

    /**
     * Use two Qs: one for BFS, another to keep the corresponding node depth
     *
     * Time: O(n) Space: O(n)
     * So it doesn't meet this requirement: You may only use constant extra space.
     *
     * @param root
     */
    public void connect5(TreeLinkNode root) {
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

            if (depthQueue.isEmpty() || depthQueue.peek() > depth) {
                // last node of the tree or last node on this depth level
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