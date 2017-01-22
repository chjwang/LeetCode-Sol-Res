package com.freetymekiyan.algorithms.Other;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;

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
     *
     * Time: O(n), Space: O(1)
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

    /**
     * We have following observation based on elimination technique (Refer Polya’s How to Solve It book).

     If A knows B, then A can’t be celebrity. Discard A, and B may be celebrity.
     If A doesn’t know B, then B can’t be celebrity. Discard B, and A may be celebrity.

     Repeat above two steps till we left with only one person.
     Ensure the remained person is celebrity. (Why do we need this step?)
     We can use stack to verify celebrity.

     Push all the celebrities into a stack.
     Pop off top two persons from the stack, discard one person based on return status of HaveAcquaintance(A, B).
     Push the remained person onto stack.
     Repeat step 2 and 3 until only one person remains in the stack.

     Check the remained person in stack doesn’t have acquaintance with anyone else.

     We will discard N elements at most(Why?). If the celebrity is present in the party, we will call
     HaveAcquaintance() 3(N-1) times.

     * Time: O(n), Space: O(n)
     * @param n
     * @return
     */
    int findCelebrity1(int n) {
        // Handle trivial case of size = 2
        Deque<Integer> s = new ArrayDeque<>();
        int c; // Celebrity

        // Push everybody to stack
        for (int i=0; i<n; i++)
            s.push(i);

        // Extract top 2
        int a = s.pop();
        int b = s.pop();

        // Find a potential celebrity by elimination: pop one from stack each time
        while (s.size() > 1) {
            if (knows(a, b))
                a = s.pop();
            else
                b = s.pop();
        }

        // last one on stack, potential candidate?
        c = s.pop();

        // Last candidate was not examined, it leads one excess comparison (optimize)
        if (knows(c, b)) c = b;
        if (knows(c, a)) c = a;

        // Check if C is actually a celebrity or not
        for (int i = 0; i < n; i++) {
            // If any person doesn't know 'a' or 'a' doesn't know any person, return -1
            if ( (i != c) &&
                    (knows(c, i) || !knows(i, c)) )
                return -1;
        }

        return c;
    }

    /**
     * 设定候选人res为0，原理是先遍历一遍，对于遍历到的人i，若候选人res认识i，则将候选人res设为i，完成一遍遍历后，
     * 我们来检测候选人res是否真正是名人，我们如果判断不是名人，则返回-1，如果并没有冲突，返回res
     *
     * @param n
     * @return
     */
    int findCelebrity2(int n) {
        int res = 0;
        for (int i = 0; i < n; ++i) {
            if (knows(res, i)) res = i;
        }
        for (int i = 0; i < n; ++i) {
            if (res != i && (knows(res, i) || !knows(i, res))) return -1;
        }
        return res;
    }

    int findCelebrity3(int n) {
        for (int i = 0, j = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (i != j && knows(i, j)) break; //if i knows j, i is not celebrity
                if (i != j && !knows(j, i)) break; //if j don't know i, not celebrity
            }
            if (j == n) return i; //i does not know any j , but all j know i
        }
        return -1;
    }

    /**
     * brute force
     *
     * 建立个一维数组用来标记每个人的名人候选状态，开始均初始化为true，表示每个人都是名人候选人，然后我们一个人一个人的验证其是否为名人，
     * 对于候选者i，我们遍历所有其他人j，如果i认识j，或者j不认识i，说明i不可能是名人，那么我们标记其为false，
     * 然后验证下一个候选者，反之如果i不认识j，或者j认识i，说明j不可能是名人，标记之。
     *
     * 对于每个候选者i，如果遍历了一圈而其候选者状态仍为true，说明i就是名人，返回即可，如果遍历完所有人没有找到名人，返回-1
     *
     * @param n
     * @return
     */
    int findCelebrity4(int n) {
        boolean[] candidate = new boolean[n];
        Arrays.fill(candidate, true);

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (candidate[i] && i != j) {
                    if (knows(i, j) || !knows(j, i)) {
                        candidate[i] = false;
                        break;
                    } else {
                        candidate[j] = false;
                    }
                }
            }
            if (candidate[i]) return i;
        }
        return -1;
    }
}