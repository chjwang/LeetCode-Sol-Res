package com.freetymekiyan.algorithms.level.Medium;

import com.freetymekiyan.algorithms.utils.Utils.ListNode;

/**
 * Reverse a linked list from position m to n
 * Do it in-place and in one-pass
 *
 * Eg:
 * Given 1->2->3->4->5->NULL, m = 2 and n = 4
 * Return 1->4->3->2->5->NULL
 *
 * Note:
 * 1 <= m <= n <= length of the list
 * 
 * Tags: Linkedlist
 */
class reverseLinkedList2 {
    
    public static void main(String[] args) {
        
    }
    
    /**
     * Move pointers to m 
     * Then insert next code to sublist's head till we reach n
     * Reconnect sublist with original list after reversing
     * We need 1 dummy head, head and tail for sublist, 
     * And cur for current node, preCur for dummy head of sublist
     * 5 pointers in total
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (m >= n || head == null) return head;
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        for (int i = 1; i < m; i++)
            pre = pre.next; // move pre to before m
        
        ListNode cur = pre.next;
        for (int i = m; i < n; i++) { // insert next to head to reverse
            ListNode temp = cur.next.next;
            cur.next.next = pre.next;
            pre.next = cur.next;
            cur.next = temp;
        }
        return dummy.next;
    }

<<<<<<< HEAD
    public ListNode reverseBetween2(ListNode head, int m, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        if (head==null||head.next==null)
            return dummy.next;

        ListNode startpoint = dummy;//startpoint指向需要开始reverse的前一个
        ListNode node1 = null;//需要reverse到后面去的节点
        ListNode node2 = null;//需要reverse到前面去的节点

        for (int i = 0; i < n; i++) {
            if (i < m-1){
                startpoint = startpoint.next;//找真正的startpoint
            } else if (i == m-1) {//开始第一轮
                node1 = startpoint.next;
                node2 = node1.next;
            }else {
                node1.next = node2.next;//node1交换到node2的后面
                node2.next = startpoint.next;//node2交换到最开始
                startpoint.next = node2;//node2作为新的点
                node2 = node1.next;//node2回归到node1的下一个，继续遍历
            }
=======
/*
  --------->1------>2------->3------->4-------5------->nullptr
  ^         ^       ^       ^         ^       ^
  |         |       |       |         |       |
  |         |       |       |         |       |
  |         |       |       |         |       |
HeadPrev  head  constPrev  prev      cur  cur->next

假设目前的m是3,n是5，那么prev-next指向cur->next,cur指向
constPrev->next,constPrev->next指向cur，然后更新cur至prev->next
思想是头插法交换node

  --------->1------>2------->4------->3-------5------->nullptr
  ^         ^       ^       ^         ^       ^
  |         |       |       |         |       |
  |         |       |       |         |       |
  |         |       |       |         |       |
HeadPrev  head  constPrev           prev     cur
最后返回HeadPrev的next节点即可
 */
public ListNode reverseBetween2(ListNode head, int m, int n) {
    if (m >= n || head == null) return head;

    ListNode headPrev = new ListNode(0);
    headPrev.next = head;
    ListNode prev = headPrev;
    for(int i = 0 ; i < m-1 ; i++){
        prev = prev.next;
    }
    final ListNode constPrev = prev;

    //position m
    prev = prev.next;
    ListNode cur = prev.next;

    for(int i = m ; i < n ; i++){
        ListNode temp = cur.next;

        // insert cur to head of sublist
        cur.next = constPrev.next;
        constPrev.next = cur;

        cur = temp;
    }
    return headPrev.next;
}


    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
>>>>>>> 65c45a8ca4844254c0cfb702bf71e83c3c72e0fa
        }
        return dummy.next;
    }
}
