package com.freetymekiyan.algorithms.Other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Suppose you have a random list of people standing in a queue. Each person is described by a pair
 * of integers (h, k), where h is the height of the person and k is the number of people in front of
 * this person who have a height greater than or equal to h. Write an algorithm to reconstruct the
 * queue.
 *
 * Note: The number of people is less than 1,100.
 *
 * Example Input: [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
 *
 * Output: [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
 */
public class QueueReconstructionByHeight {

    private void swap(int[][] people, int a, int b) {
        int t1 = people[a][0], t2 = people[a][1];
        people[a][0] = people[b][0];
        people[a][1] = people[b][1];
        people[b][0] = t1;
        people[b][1] = t2;

    }

    public int[][] reconstructQueue(int[][] people) {
        //java 排序不方便，我这里就直接暴力排序了
        //让身高按照降序排列，高的在前面，同身高的情况下让要求前面人数人少的在前面，就可以了
        int n = people.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (people[i][0] < people[j][0] || // bubble sort
                        people[i][0] == people[j][0] && people[i][1] > people[j][1])
                    swap(people, i, j);
            }
        }
        //按照顺序插入
        List<Integer> la = new ArrayList<>();
        List<Integer> lb = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            la.add(people[i][1], people[i][0]);
            lb.add(people[i][1], people[i][1]);
        }
        for (int i = 0; i < n; i++) {
            people[i][0] = la.get(i);
            people[i][1] = lb.get(i);
        }
        return people;

    }

    public int[][] reconstructQueue1(int[][] people) {
        Arrays.sort(people,
                (p1, p2) -> p1[0] != p2[0] ? Integer.compare(p2[0], p1[0]) : Integer.compare(p1[1], p2[1]));
        List<int[]> list = new LinkedList();
        for (int[] ppl : people) list.add(ppl[1], ppl); // LinkedList is faster than ArrayList here, O(n/4) vs O(n/2)
        return list.toArray(new int[people.length][]);
    }

    public int[][] reconstructQueue2(int[][] people) {
        int size = people.length;
        LinkedList<int[]> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            list.add(new int[]{people[i][0], people[i][1], 0});
        }
        int ans[][] = new int[size][];
        for (int i = 0; i < size; i++) {
            Collections.sort(list, (a, b) -> {
                if (a[1] == b[1])
                    return a[0] - b[0];
                return a[1] - b[1];
            });
            int[] head = list.removeFirst();
            ans[i] = new int[]{head[0], head[1] + head[2]};
            for (int[] p : list) {
                if (p[0] <= head[0]) {
                    p[1] -= 1;
                    p[2] += 1;
                }
            }
        }
        return ans;
    }

    class PairComp implements Comparator<int[]> {
        public int compare(int[] p1, int[] p2){
            int comp_h = Integer.compare(p2[0], p1[0]);
            return comp_h == 0 ? Integer.compare(p1[1], p2[1]): comp_h;
        }
    }
    public int[][] reconstructQueue4(int[][] people) {
        LinkedList<int[]> list = new LinkedList();
        PriorityQueue<int[]> queue = new PriorityQueue<int[]>(1, new PairComp() );
        for (int[] ppl: people){
            queue.offer( ppl );
        }
        while ( ! queue.isEmpty() ) {
            int[] pair = queue.poll();
            list.add(pair[1], pair);
        }
        int[][] ret = new int[people.length][];
        for (int i=0; i<list.size(); i++){
            ret[i] = list.get(i);
        }
        return ret;
    }
}

