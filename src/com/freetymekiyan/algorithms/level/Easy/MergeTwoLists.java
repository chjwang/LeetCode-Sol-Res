package com.freetymekiyan.algorithms.level.Easy;

<<<<<<< HEAD
import com.freetymekiyan.algorithms.utils.Utils.ListNode;

=======
>>>>>>> 65c45a8ca4844254c0cfb702bf71e83c3c72e0fa
/**
 * Merge two sorted linked lists and return it as a new list. The new list
 * should be made by splicing together the nodes of the first two lists.
 * 
 * Tags: Linkedlist
 */
public class MergeTwoLists {
    
    /**
     * Recursive
     * the order of l1, l2 doesn't matter
     */
    public ListNode mergeTwoListsRec(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        
        // next node should be the result of comparison
        if (l1.val < l2.val) {
            l1.next = mergeTwoListsRec(l1.next, l2); // notice l1.next
            return l1;
        } else {
            l2.next = mergeTwoListsRec(l1, l2.next); // notice l2.next
            return l2;
        }
    }
    
    /**
     * iterasive
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        
        // merge
        ListNode temp = new ListNode(0);
        ListNode beforeHead = temp;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                temp.next = l1;
                l1 = l1.next;
            } else {
                temp.next = l2;
                l2 = l2.next;
            }
            temp = temp.next;
        }
<<<<<<< HEAD
        // merge remaining, NO NEED!!!
//        while (l1 != null) {
//            temp.next = l1;
//            temp = temp.next;
//            l1 = l1.next;
//        }
//        while (l2 != null) {
//            temp.next = l2;
//            temp = temp.next;
//            l2 = l2.next;
//        }
        temp.next = (l1 == null) ? l2 : l1;
        return beforeHead.next.next;
=======
        // merge remain
        temp = mergeRemain(l1, temp);
        temp = mergeRemain(l2, temp);
        return beforeHead.next;
    }

    private ListNode mergeRemain(ListNode l1, ListNode temp) {
        while (l1 != null) {
            temp.next = l1;
            temp = temp.next;
            l1 = l1.next;
        }
        return temp;
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
>>>>>>> 65c45a8ca4844254c0cfb702bf71e83c3c72e0fa
    }

}