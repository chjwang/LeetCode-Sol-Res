package com.freetymekiyan.algorithms.level.Easy;

/**
 * You are a product manager and currently leading a team to develop a new product. Unfortunately, the latest version of
 * your product fails the quality check. Since each version is developed based on the previous version, all the versions
 * after a bad version are also bad.
 * <p>
 * Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one, which causes all the following
 * ones to be bad.
 * <p>
 * You are given an API bool isBadVersion(version) which will return whether version is bad. Implement a function to
 * find the first bad version. You should minimize the number of calls to the API.
 * <p>
 * Company Tags: Facebook
 * Tags: Binary Search
 * Similar Problems: (M) Search for a Range, (M) Search Insert Position, (E) Guess Number Higher or Lower
 */
public class FirstBadVersion {

    /**
     * Binary Search.
     * n versions from 1 to n.
     * Get the middle version m.
     * If m is bad, which means m+1 to n are all bad, search in the left half.
     * If m is good, search in the right half.
     * Stop when l reaches r.
     */
    public int firstBadVersion(int n) {
        int l = 1;
        int r = n;

        while (l < r) {
            int m = l + (r - l) / 2; // Avoid overflow, since l >= 1, n <= Integer.MAX_VALUE
            if (!isBadVersion(m)) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        return l;
    }

    /**
     * Dummy function, just for compilation.
     */
    public int firstBadVersion2(int n) {
        int l = 1;
        int r = n;

        while (l < r) {
            int m = l + (r - l) / 2;
            if (!isBadVersion(m)) {
                l = m + 1;
            } else if (m == 1 || !isBadVersion(m - 1)) {
                // Early return. If current version is bad and the previous one is good.
                return m;
            } else {
                r = m;
            }
        }
        return l;
    }

    private boolean isBadVersion(int i) {
        return true;
    }

}
