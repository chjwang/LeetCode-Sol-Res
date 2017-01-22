package com.freetymekiyan.algorithms.level.Medium;

import java.util.PriorityQueue;

/**
 * Write a program to find the nth super ugly number.
 * <p>
 * Super ugly numbers are positive numbers whose all prime factors are in the given prime numer list of size k. For
 * example, [1, 2, 4, 7, 8, 13, 14, 16, 19, 26, 28, 32] is the sequence of the first 12 super ugly numbers given primes
 * = [2, 7, 13, 19] of size 4.
 * <p>
 * Note:
 * (1) 1 is a super ugly number for any given primes.
 * (2) The given numbers in primes are in ascending order.
 * (3) 0 < k ≤ 100, 0 < n ≤ 106, 0 < primes[i] < 1000.
 * <p>
 * Tags: Math, Heap
 * Similar Problems: (M) Ugly Number II
 */

public class SuperUglyNumber {

    public static void main(String[] args) {
        SuperUglyNumber superUglyNumber = new SuperUglyNumber();
        for (int i=6; i<=10; i++) {
            System.out.println("1: the " + i + "th ugly number is " + superUglyNumber.nthSuperUglyNumber(i, new int[]{2, 3, 5}));
            System.out.println("2: the " + i + "th ugly number is " + superUglyNumber.nthSuperUglyNumber2(i, new int[]{2, 3, 5}));
        }
    }
    /**
     * Refer to ugly number 2
     * Use an array to keep all indices for primes
     *
     We can maintain k lists of super ugly numbers (assume primes are 2,4,7,...):
     1*1 (super ugly number start with 1)
     1*2, 2*2, 4*2, 7*2, 8*2, etc..  (super ugly number * 2)
     1*4, 2*4, 4*4, 7*4, 8*4, etc..  (super ugly number * 4)
     1*7, 2*7, 4*7, 7*7, 8*7, etc..  (super ugly number * 7)
     ...

     Then we can see that in each list, the ugly number is the ugly number itself times p0, p1, p2, p3, p4, p5 ...
     Then we can maintain k pointers for each prime, and the next ugly number must be minimum
     number of L1, L2, L4, L7 ... At last, we move forward the pointer.
     *
     */
    public int nthSuperUglyNumber(int n, int[] primes) {
        int k = primes.length;
        int[] index = new int[k]; // pointers of each prime
        int[] res = new int[n];

        res[0] = 1; // 1 is considered a super ugly number

        for (int i = 1; i < n; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < k; j++) {
                if (res[index[j]] * primes[j] < min) {
                    min = res[index[j]] * primes[j];
                }
            }

            res[i] = min;

            // update pointers of primes
            for (int j = 0; j < k; j++) {
                if (res[index[j]] * primes[j] == min) {
                    index[j]++;
                }
            }
        }
        return res[n - 1];
    }

    public int nthSuperUglyNumber2(int n, int[] primes) {
        int[] res = new int[n];
        res[0] = 1;

        PriorityQueue<Node> q = new PriorityQueue<>();
        for (int i = 0; i < primes.length; i++)
            q.add(new Node(0, primes[i], primes[i]));

        for (int i = 1; i < n; i++) {
            Node cur = q.peek();
            res[i] = cur.val;
            do {
                cur = q.poll();
                cur.val = res[++cur.index] * cur.prime;
                q.add(cur);
            } while (!q.isEmpty() && q.peek().val == res[i]);
        }
        return res[n - 1];
    }
}

class Node implements Comparable<Node> {
    int index;
    int val;
    int prime;

    Node(int index, int val, int prime) {
        this.val = val;
        this.index = index;
        this.prime = prime;
    }

    public int compareTo(Node x) {
        return this.val - x.val ;
    }
}