package com.freetymekiyan.algorithms.Other;

import java.util.Comparator;
import java.util.PriorityQueue;

class Node {
    public int x, y, sum;
    public Node(int x, int y, int sum) {
        this.x = x;
        this.y = y;
        this.sum = sum;
    }
}

class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node a, Node b) {
        return a.sum - b.sum;
    }
}

public class KthSmallestSumInSortedArrays {
    int[] dx = new int[] {0, 1};
    int[] dy = new int[] {1, 0};

    // Check if a coordinate is valid and should be marked as visited
    public boolean isValid(int x, int y, int[] A, int[] B, boolean[][] visited) {
        if (x < A.length && y < B.length && !visited[x][y]) {
            return true;
        }
        return false;
    }

    /**
     * @param A an integer arrays sorted in ascending order
     * @param B an integer arrays sorted in ascending order
     * @param k an integer
     * @return an integer
     */
    public int kthSmallestSum(int[] A, int[] B, int k) {
        // Validation of input
        if (A == null || B == null || A.length == 0 || B.length == 0) {
            return -1;
        }
        if (A.length * B.length < k) {
            return -1;
        }

        PriorityQueue<Node> heap = new PriorityQueue<Node>(k, new NodeComparator());
        heap.offer(new Node(0, 0, A[0] + B[0]));

        boolean[][] visited = new boolean[A.length][B.length];

        for (int i = 0; i < k - 1; i++) {
            Node smallest = heap.poll();
            for (int j = 0; j < 2; j++) {
                int nextX = smallest.x + dx[j];
                int nextY = smallest.y + dy[j];

                if (isValid(nextX, nextY, A, B, visited)) {
                    visited[nextX][nextY] = true;
                    int nextSum = A[nextX] + B[nextY];
                    heap.offer(new Node(nextX, nextY, nextSum));
                }
            }
        }
        return heap.peek().sum;
    }
}