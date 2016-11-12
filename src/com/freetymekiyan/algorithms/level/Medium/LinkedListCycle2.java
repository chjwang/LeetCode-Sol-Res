package com.freetymekiyan.algorithms.level.Medium;

/**
 * Given a linked list, return the node where the cycle begins. If there is no
 * cycle, return null.
 * 
 * Follow up:
 * Can you solve it without using extra space?
 * 
 * Tags: Linkedlist, Two pointers, Math
 */
class LinkedListCycle2 {
    public static void main(String[] args) {
        
    }
    
    /**
     * Reset slow to head after cycle is detected
     * Then move until slow and fast meets
     * Each one step every time
     *
     * http://fisherlei.blogspot.com/2013/11/leetcode-linked-list-cycle-ii-solution.html
     * 假设linked list有环，环长Y，环以外的长度是X。
     *
     * 现在有两个指针，第一个指针，每走一次走一步，第二个指针每走一次走两步，如果他们走了t次之后相遇在K点
     那么指针一走的路是      t = X + nY + K        ①

     指针二走的路是     2t = X + mY+ K       ②          m,n为未知数

     把等式一代入到等式二中, 有

     2X + 2nY + 2K = X + mY + K

     =>   X+K  =  (m-2n)Y    ③

     这就清晰了，X和K的关系是基于Y互补的。等于说，两个指针相遇以后，再往下走X步就回到Cycle的起点了。这就可以有O(n)的实现了。
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null) return null;
        ListNode slow = head;
        ListNode fast = head;
        boolean hasCycle = false;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                hasCycle = true; 
                break;
            }
        }
        if (!hasCycle) return null;
        slow = head;
        while (slow != fast) { // move x steps further
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
    
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
