package com.freetymekiyan.algorithms.level.Medium;

/**
 * Write a program to find the n-th ugly number.
 *
 * Ugly numbers are positive numbers whose prime factors only include 2, 3, 5. For example, 1, 2, 3, 4, 5, 6, 8, 9, 10,
 * 12 is the sequence of the first 10 ugly numbers.
 *
 * Note that 1 is typically treated as an ugly number.
 *
 * Hint:
 *
 * The naive approach is to call is Ugly for every number until you reach the nth one. Most numbers are not ugly. Try to
 * focus your effort on generating only the ugly ones.
 *
 * An ugly number must be multiplied by either 2, 3, or 5 from a smaller ugly number.
 *
 * The key is how to maintain the order of the ugly numbers. Try a similar approach of merging from three sorted lists:
 * L1, L2, and L3.
 *
 * Assume you have Uk, the kth ugly number. Then Uk+1 must be Min(L1 * 2, L2 * 3, L3 * 5).
 *
 * Tags: Dynamic Programming, Heap, Math
 *
 * Similar Problems: (H) Merge k Sorted Lists, (E) Count Primes, (E) Ugly Number, (M) Perfect Squares, (M) Super Ugly
 * Number
 */
public class UglyNumber2 {

    public static void main(String[] args) {
        UglyNumber2 ugnum2 = new UglyNumber2();
        for (int i=6; i<=10; i++)
            System.out.println("the " + i + "th ugly number is " + ugnum2.nthUglyNumber(i));
    }

    /**
     * We can maintain three lists of ugly numbers:
     1*2, 2*2, 3*2, 4*2, 5*2, 6*2, 8*2, etc..  (ugly number * 2)
     1*3, 2*3, 3*3, 4*3, 5*3, 6*3, 8*3, etc..  (ugly number * 3)
     1*5, 2*5, 3*5, 4*5, 5*5, 6*5, 8*5, etc... (ugly number * 5)

     Then we can see that in each list, the ugly number is the ugly number itself times 2, 3 or 5.
     Then we can maintain three pointers of i, j, and k, and the next ugly number must be minimum
     number of Li, Lj and Lk. At last, we move forward the pointer.

     * @param n
     * @return
     */
    public int nthUglyNumber(int n) {
        if (n <= 0) return -1;
        if (n == 1) return 1;

        // pointers for 2, 3, 5
        int p2 = 0;
        int p3 = 0;
        int p5 = 0;

        // generate ugly numbers array
        int[] uglyNums = new int[n];
        uglyNums[0] = 1;
        for (int i = 1; i < n; i++) {
            uglyNums[i] = Math.min(uglyNums[p2] * 2, Math.min(uglyNums[p3] * 3, uglyNums[p5] * 5));
            // update indices
            if (uglyNums[i] == uglyNums[p2] * 2) p2++;
            if (uglyNums[i] == uglyNums[p3] * 3) p3++;
            if (uglyNums[i] == uglyNums[p5] * 5) p5++;
        }
        System.out.println("p2: " + p2 + ", p3: " + p3 + ", p5: " + p5 );
        return uglyNums[n - 1];
    }
}
