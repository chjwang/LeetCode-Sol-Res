package com.freetymekiyan.algorithms.Other;

/**
 * Suppose you are at a party with n people (labeled from 0 to n - 1) and among them, there may
 * exist one celebrity. The definition of a celebrity is that all the other n - 1 people know
 * him/her but he/she does not know any of them.
 *
 * Now you want to find out who the celebrity is or verify that there is not one. The only thing you
 * are allowed to do is to ask questions like: "Hi, A. Do you know B?" to get information of whether
 * A knows B. You need to find out the celebrity (or verify there is not one) by asking as few
 * questions as possible (in the asymptotic sense).
 *
 * You are given a helper function bool knows(a, b) which tells you whether A knows B. Implement a
 * function int findCelebrity(n), your function should minimize the number of calls to knows().
 *
 * Note: There will be exactly one celebrity if he/she is in the party. Return the celebrity's label
 * if there is a celebrity in the party. If there is no celebrity, return -1.
 *
 * Given: function: isFriend(a, b) Returns true iff b is treated as a friend by a
 */
class Celebrity {
    public static void main(String[] args) {

    }

    /**
     * The idea is to use two pointers, one from start and one from the end. Assume the start person
     * is A, and the end person is B. If A knows B, then A must not be the celebrity. Else, B must
     * not be the celebrity. We will find a celebrity candidate at the end of the loop. Go through
     * each person again and check whether this is the celebrity.
     */
    int findCelebrity(int n) {
        int a = 0, b = n - 1;
        while (a < b) {
            if (knows(a, b))
                ++a;
            else
                --b;
        }
        for (int i = 0; i < n; ++i) {
            if (i != a) {
                if (knows(a, i) || !knows(i, a))
                    return -1;
            }
        }
        return a;
    }

    // Person with 2 is celebrity
    int MATRIX[][] = {
        {0, 0, 1, 0},
        {0, 0, 1, 0},
        {0, 0, 0, 0},
        {0, 0, 1, 0}
    };

    private boolean knows(int a, int b) {
        return MATRIX[a][b] == 1;
    }
}