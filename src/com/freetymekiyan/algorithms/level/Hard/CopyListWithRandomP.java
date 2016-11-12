import java.util.*;

/**
 * A linked list is given such that each node contains an additional random
 * pointer which could point to any node in the list or null.
 * 
 * Return a deep copy of the list.
 * 
 * Tags: Hashtable, Linkedlist
 */
class CopyListWithRandomP {
    
    public static void main(String[] args) {
        
    }
    
    /**
     * Use a hashmap to store map between original node and copy node
     */
    public RandomListNode copyRandomList(RandomListNode head) {
        Map<RandomListNode, RandomListNode> map = new HashMap<RandomListNode, RandomListNode>();
        return helper(head, map);
    }
    
    /**
     * Get copy node from map
     */
    public RandomListNode helper(RandomListNode node, Map<RandomListNode, RandomListNode> map) {
        if (node == null) return null;
        if (map.containsKey(node)) return map.get(node); // return copy
        RandomListNode res = new RandomListNode(node.label);
        map.put(node, res); // build map
        res.next = helper(node.next, map); // build next
        res.random = helper(node.random, map); // build copy
        return res;
    }

    /**
     * Insert a same node after current node
     * Then split into two lists
     */
    public RandomListNode copyRandomList2(RandomListNode head) {
        if (head == null) return head;
        RandomListNode p1 = head;
        while (p1 != null) {
            RandomListNode copy = new RandomListNode(p1.label);
            copy.next = p1.next;
            p1.next = copy;
            p1 = p1.next.next;
        }

        // now fix random for copy
        p1 = head;
        while (p1 != null && p1.next != null) {
            if (p1.random != null) p1.next.random = p1.random.next;
            p1 = p1.next.next;
        }
        
        // split lists
        p1 = head;
        RandomListNode copy = p1.next;
        RandomListNode newHead = copy;
        while (copy != null && p1 != null) {
            p1.next = p1.next.next;
            if (copy.next == null) break;
            copy.next = copy.next.next;
            copy = copy.next;
            p1 = p1.next;
        }
        return newHead;
    }
    
    /**
     * Build a new list and connect original and new node together
     */
    public RandomListNode copyRandomList3(RandomListNode haed) {
        return null;
    }

    public RandomListNode copyRandomList4(RandomListNode head) {
        if (head == null) return null;

        Map<RandomListNode, RandomListNode> map = new HashMap<>();
        RandomListNode p1 = head;
        while (p1 != null) {
            RandomListNode p2 = p1.next;
            RandomListNode p3 = p1.random;

            RandomListNode p1Copy = addMapping(map, p1);
            RandomListNode p2Copy = addMapping(map, p2);
            RandomListNode p3Copy = addMapping(map, p3);

            p1Copy.next = p2Copy;
            p1Copy.random = p3Copy;

            p1 = p2;
        }

        return map.get(head);
    }

    private RandomListNode addMapping(Map<RandomListNode, RandomListNode> map, RandomListNode node) {
        if (node == null)
            return null;
        RandomListNode nodeCopy = map.get(node);
        if (nodeCopy == null) {
            nodeCopy = new RandomListNode(node.label);
            map.put(node, nodeCopy);
        }
        return nodeCopy;
    }

    class RandomListNode {
        int label;
        RandomListNode next, random;
        RandomListNode(int x) { this.label = x; }
    };
}
