package com.freetymekiyan.algorithms.level.Medium;

import com.freetymekiyan.algorithms.utils.Utils.ListNode;

/**
 * Sort a linked list using insertion sort.
 *
 * Tags: Linkedlist, Sort
 */
class InsertionSortList {
    public static void main(String[] args) {

    }

    /**
     * Check the list one by one to find a node that has smaller value than
     * nodes before it and swap
     */
    public ListNode insertionSortList(ListNode head) {
        ListNode dummy = new ListNode(0);
        ListNode prev = dummy;
        ListNode node = head;

        while (node != null) {
            ListNode temp = node.next;

        /* Before insert, the prev is at the last node of the sorted list.
           Only the last node's value is larger than the current inserting node
           should we move the temp back to the head*/
            if (prev.val >= node.val)
                prev = dummy;

            while (prev.next != null && prev.next.val < node.val) {
                prev = prev.next;
            }

            // insert between prev and prev.next
            node.next = prev.next;
            prev.next = node;

            // prev = dummy; // Don't set prev to the head of the list after insert
            node = temp;
        }
        return dummy.next;
    }
}
