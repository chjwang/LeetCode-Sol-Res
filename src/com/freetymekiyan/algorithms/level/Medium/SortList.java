package com.freetymekiyan.algorithms.level.Medium;

import com.freetymekiyan.algorithms.utils.Utils.ListNode;

/**
 * Sort a linked list in O(n log n) time using constant space complexity.
 *
 * Tags: Linkedlist, Sort
 */
class SortList {

    public static void main(String[] args) {

    }

    /**
     * Get list length and do merge sort
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode tail = head;
        int len = 0;
        while (tail != null) {
            tail = tail.next;
            len++;
        }
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        dummy.next = head;
        head = mergeSort(dummy, head, len);
        return head;
    }

    /**
     * Cut into two halves
     * Sort left half first, move to right half's pre head and sort right
     * Merge two halves
     * Insert node in latter part if its smaller than current node
     */
    public ListNode mergeSort(ListNode preHead, ListNode head, int len) {
        if (head == null || len <= 1) return head;

        int left = len / 2;
        int right = len - left;

        // sort left
        head = mergeSort(preHead, head, left);

        // sort right
        ListNode pMid = head;
        for (int i = 0; i < left - 1; i++) pMid = pMid.next;
        mergeSort(pMid, pMid.next, right);

        // merge
        ListNode pre1 = preHead;
        ListNode p1 = head;
        ListNode pre2 = pMid;
        ListNode p2 = pMid.next;
        if (p1.val > p2.val) head = p2; // switch head
        while (left > 0 && right > 0) {
            // merge second half to first half
            if (p1.val > p2.val) {
                pre2.next = p2.next; // insert p2 before p1
                p2.next = p1;
                pre1.next = p2;
                // set to next
                pre1 = p2;
                p2 = pre2.next;
                right--;
            } else {
                // set to next
                pre1 = p1;
                p1 = p1.next;
                left--;
            }
        }
        return head;
    }

    public ListNode sortList2(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode slow = head, fast = head, firstHalf = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode secondHalf = slow.next;
        slow.next = null;

        ListNode left = null, right = null;
        if (firstHalf != secondHalf) {
            left = sortList(firstHalf);
            right = sortList(secondHalf);
        }
        return mergeTwoLists(left, right);
    }

    public ListNode mergeTwoLists(ListNode left, ListNode right) {
        if (right == null)
            return left;
        if (left == null)
            return right;

        ListNode dummy = new ListNode(-1);
        ListNode ptr = dummy;
        while (right != null && left != null) {
            if (right.val < left.val) {
                ptr.next = right;
                right = right.next;
            } else {
                ptr.next = left;
                left = left.next;
            }
            ptr = ptr.next;
        }

        if (right != null)
            ptr.next = right;
        if (left != null)
            ptr.next = left;

        return dummy.next;
    }
}
