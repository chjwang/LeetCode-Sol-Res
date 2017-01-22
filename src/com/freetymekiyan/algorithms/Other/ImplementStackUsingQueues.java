package com.freetymekiyan.algorithms.Other;

import java.util.LinkedList;
import java.util.Queue;

public class ImplementStackUsingQueues {

    /* two queues - q1 and q2 */
    private Queue<Integer> q1 = new LinkedList<>();
    private Queue<Integer> q2 = new LinkedList<>();

    /* push : add the element in q1 */
    public void push(int element) {
        q1.offer(element);
    }

    /* Pop : Move all the elements except the last one from q1 into q2
    and remove the last element in q1 */
    public int pop() {

          /* copying all but last element from q1 into q2 */
        while (q1.size() != 1) {
            int element = q1.poll();
            q2.offer(element);
        }

          /* Return the last element of q1 */
        int element = q1.poll();

          /* Interchange q1 and q2 */
        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;
//        while (!q2.isEmpty())
//            q1.offer(q2.poll());

          /* Return the popped element */
        return element;
    }
}