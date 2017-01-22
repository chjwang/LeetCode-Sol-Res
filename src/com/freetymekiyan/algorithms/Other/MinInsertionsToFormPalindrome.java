package com.freetymekiyan.algorithms.Other;

import java.util.Arrays;

/**
 * Given a string, find the minimum number of characters to be inserted to 
 * convert it to palindrome.
 * 
 * Tags: Recursive, DP
 */
class MinInsertionsToFormPalindrome {
    public static void main(String[] args) {
        String s = "aaaabaa";
        System.out.println(new MinInsertionsToFormPalindrome().minInsertionsDP(s));
        System.out.println(new MinInsertionsToFormPalindrome().minInsertions(s));
        System.out.println(new MinInsertionsToFormPalindrome().minInsertions2(s));
    }
    
    /**
     * DP, bottom-up
     * Fill a table in diagonal direction
     *
     * If we observe the recursion approach carefully, we can find that it exhibits overlapping subproblems.
     Suppose we want to find the minimum number of insertions in string “abcde”:

     abcde
     /       |      \
     /        |        \
     bcde         abcd       bcd  <- case 3 is discarded as str[l] != str[h]
     /   |   \       /   |   \
     /    |    \     /    |    \
     cde   bcd  cd   bcd abc bc
     / | \  / | \ /|\ / | \
     de cd d cd bc c………………….
     The substrings in bold show that the recursion to be terminated and the recursion tree cannot originate from there. Substring in the same color indicates overlapping subproblems.

     How to reuse solutions of subproblems?
     We can create a table to store results of subproblems so that they can be used directly if same subproblem is encountered again.

     The below table represents the stored values for the string abcde.

     a b c d e
     ----------
     0 1 2 3 4
     0 0 1 2 3
     0 0 0 1 2
     0 0 0 0 1
     0 0 0 0 0
     How to fill the table?
     The table should be filled in diagonal fashion. For the string abcde, 0….4, the following should be order in which the table is filled:

     Gap = 1:
     (0, 1) (1, 2) (2, 3) (3, 4)

     Gap = 2:
     (0, 2) (1, 3) (2, 4)

     Gap = 3:
     (0, 3) (1, 4)

     Gap = 4:
     (0, 4)
     */
    int minInsertionsDP(String s) {
        int n = s.length();
        // Create a table of size n*n. dp[i][j] will store minumum number of insertions needed to
        // convert s[i..j] to a palindrome.
        int[][] dp = new int[n][n];

        for (int gap = 1; gap < n; gap++) {
            for (int i = 0, j = gap; j < n; i++, j++) {
                dp[i][j] = s.charAt(i) == s.charAt(j) ? dp[i+1][j-1] : Math.min(dp[i][j-1], dp[i+1][j]) + 1;
            }
        }

        // Return minimum number of insertions for s[0..n-1]
        return dp[0][n-1];
    }
    
    /**
     * Recursive
     *
     * Before we go further, let us understand with few examples:
     ab: Number of insertions required is 1. bab
     aa: Number of insertions required is 0. aa
     abcd: Number of insertions required is 3. dcbabcd
     abcda: Number of insertions required is 2. adcbcda which is same as number of insertions in the substring bcd(Why?).
     abcde: Number of insertions required is 4. edcbabcde

     * Let the input string be str[l……h]. 
     * The problem can be broken down into 3 parts:
     * 1. Find the minimum # of insertions in the substring str[l+1,…….h].
     * 2. Find the minimum # of insertions in the substring str[l…….h-1].
     * 3. Find the minimum # of insertions in the substring str[l+1……h-1].
     * 
     * The minimum # of insertions in the string str[l…..h] can be given as:
     * If 
     *  str[l] is equal to str[h], minInsertions(str[l+1…..h-1]) 
     * Otherwise, 
     *  min(minInsertions(str[l…..h-1]), minInsertions(str[l+1…..h])) + 1
     */

    public int minInsertions(String s) {
        return minInsertions(s, 0, s.length() - 1);
    }

    int minInsertions(String s, int l, int h) {
        // Base Cases
        if (l > h) return Integer.MAX_VALUE;
        if (l == h) return 0;
        if (l == h - 1) return (s.charAt(l) == s.charAt(h)) ? 0 : 1;
        // Check if the first and last characters are same. On the basis of the
        // comparison result, decide which subrpoblem(s) to call
        return s.charAt(l) == s.charAt(h) ? minInsertions(s, l + 1, h - 1) 
            : Math.min(minInsertions(s, l, h-1), minInsertions(s, l+1, h)) + 1;
    }

    /* Returns length of LCS for X[0..m-1], Y[0..n-1]. Longest Common Subsequence.
    See http://goo.gl/bHQVP for details of this function

    LCS Problem Statement:
    Given two sequences, find the length of longest subsequence present in both of them.
    A subsequence is a sequence that appears in the same relative order, but not necessarily contiguous.
    For example, “abc”, “abg”, “bdf”, “aeg”, ‘”acefg”, .. etc are subsequences of “abcdefg”.
    So a string of length n has 2^n different possible subsequences.

    It is a classic computer science problem, the basis of diff (a file comparison program that outputs
    the differences between two files), and has applications in bioinformatics.

    Examples:
    LCS for input Sequences “ABCDGH” and “AEDFHR” is “ADH” of length 3.
    LCS for input Sequences “AGGTAB” and “GXTXAYB” is “GTAB” of length 4.

    # A Naive recursive Python implementation of LCS problem
    def lcs(X, Y, m, n):

        if m == 0 or n == 0:
           return 0;
        elif X[m-1] == Y[n-1]:
           return 1 + lcs(X, Y, m-1, n-1);
        else:
           return max(lcs(X, Y, m, n-1), lcs(X, Y, m-1, n));

    Time complexity of this method is also O(n^2) and this method also requires O(n^2) extra space.
    */
    int lcs( char[] X, char[] Y, int m, int n ) {
        int[][] L = new int[n+1][n+1]; // one extra
        Arrays.fill(L, 0);
        int i, j;

        /* Following steps build L[m+1][n+1] in bottom up fashion.
        Note that L[i][j] = length of LCS of X[0..i-1] and Y[0..j-1] */
        for (i=0; i<=m; i++) {
            for (j=0; j<=n; j++) {
                if (i == 0 || j == 0) L[i][j] = 0; // no need
                else if (X[i-1] == Y[j-1])
                    L[i][j] = L[i-1][j-1] + 1;

                else
                    L[i][j] = Math.max(L[i-1][j], L[i][j-1]);
            }
        }
        /* L[m][n] contains length of LCS for X[0..n-1] and Y[0..m-1] */
        return L[m][n];
    }

    /**
     * Another Dynamic Programming Solution (Variation of Longest Common Subsequence Problem)
     The problem of finding minimum insertions can also be solved using Longest Common Subsequence (LCS) Problem.

     If we find out LCS of string and its reverse, we know how many maximum characters can form a palindrome.
     We need insert remaining characters. Following are the steps.
     1) Find the length of LCS of input string and its reverse. Let the length be ‘l’.
     2) The minimum number insertions needed is length of input string minus ‘l’.

     LCS based function to find minimum number of insersions
     */
    public int minInsertions2(String str) {
        // Creata another string to store reverse of 'str'
        String reverseStr = new StringBuffer(str).reverse().toString();

        int n = str.length();
        // The output is length of string minus length of lcs of
        // str and it reverse
        return (n - lcs(str.toCharArray(), reverseStr.toCharArray(), n, n));
    }
}