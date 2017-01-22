package com.freetymekiyan.algorithms.Other;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
Find K closet points to origin in 2-D plane
 */
public class FindKClosestPoints {

    public List<Point> findKClosest(Point[] p, int k) {
//        PriorityQueue<Point> pq = new PriorityQueue<>(10, new Comparator<Point>() {
//            @Override
//            public int compare(Point a, Point b) {
//                return (b.x * b.x + b.y * b.y) - (a.x * a.x + a.y * a.y);
//            }
//        });

        PriorityQueue<Point> pq = new PriorityQueue<>(10,
                (Point a, Point b)->(b.x * b.x + b.y * b.y) - (a.x * a.x + a.y * a.y));

        for (int i = 0; i < p.length; i++) {
            if (i < k)
                pq.offer(p[i]);
            else {
                Point temp = pq.peek();
                if ((p[i].x * p[i].x + p[i].y * p[i].y) - (temp.x * temp.x + temp.y * temp.y) < 0) {
                    pq.poll();
                    pq.offer(p[i]);
                }
            }
        }

        List<Point> x = new ArrayList<>();
        while (!pq.isEmpty())
            x.add(pq.poll());

        return x;
    }

    class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
