package com.freetymekiyan.algorithms.Other;

/**
 Given a string, find out if the string is K-Palindrome or not.
 A k-palindrome string transforms into a palindrome on removing at most k characters from it.
 */
public class KPalindrome {

    /**
     * If we carefully analyze the problem, the task is to transform the given string into its reverse
     * by removing at most K characters from it.
     *
     * The problem is basically a variation of Edit Distance. We can modify the Edit Distance problem
     * to consider given string and its reverse as input and only operation allowed is deletion.
     *
     * Since given string is compared with its reverse, we will do at most N deletions from first
     * string (original) and N deletions from second string (reversal) to make them equal.
     * Therefore, for a string to be k-palindrome, 2*N <= 2*K should hold true.
     *
     * Below are the detailed steps of algorithm - Process all characters one by one staring from
     * either left or right sides of both strings.
     *
     * Let us traverse from the right corner, there are two possibilities for every pair of character
     * being traversed.
     *
     If last characters of two strings are same, we ignore last characters and get count for remaining
     strings. So we recur for lengths m-1 and n-1 where m is length of str1 and n is length of str2.

     If last characters are not same, we consider remove operation on last character of first string
     and last character of second string, recursively compute minimum cost for the operations and
     take minimum of two values.
     Remove last char from str1: Recur for m-1 and n.
     Remove last char from str2: Recur for m and n-1.
     */
    // Returns true if str is k palindrome.
    boolean isKPal(String str, int k) {
        StringBuffer sb = new StringBuffer(str);
        sb.reverse();
        String revStr = sb.toString();

        int len = str.length();
        return (isKPalRec(str, revStr, len, len) <= k*2);
    }

    /**
     * O(2^n)
     *
     * @param str1
     * @param str2
     * @param m
     * @param n
     * @return Number of deletes needed to make str1 and str2 palindrome
     */
    int isKPalRec(String str1, String str2, int m, int n) {
        // If first string is empty, the only option is to remove all characters of second string
        if (m == 0) return n;

        // If second string is empty, the only option is to remove all characters of first string
        if (n == 0) return m;

        // If last characters of two strings are same, ignore last characters and get count for
        // remaining strings.
        if (str1.charAt(m-1) == str2.charAt(n-1))
            return isKPalRec(str1, str2, m-1, n-1);

        // If last characters are not same,
        // 1. Remove last char from str1 and recur for m-1 and n
        // 2. Remove last char from str2 and recur for m and n-1
        // Take minimum of above two operations
        return 1 + Math.min(isKPalRec(str1, str2, m-1, n), // Remove from str1
                isKPalRec(str1, str2, m, n-1)); // Remove from str2
    }

    /**
     * O(n*m)
     * DP: bottom-up tabulization
     * @param str1
     * @param str2
     * @param m
     * @param n
     * @return
     */
    int isKPalDP(String str1, String str2, int m, int n) {
        // Create a table to store results of subproblems
        int[][] dp = new int[m + 1][n + 1];

        // Fill dp[][] in bottom-up manner
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                // If first string is empty, only option is to remove all characters of second string
                if (i == 0)
                    dp[i][j] = j; // Min. operations = j

                // If second string is empty, only option is to remove all characters of first string
                else if (j == 0)
                    dp[i][j] = i; // Min. operations = i

                // If last characters are same, ignore last character and recur for remaining string
                else if (str1.charAt(i - 1) == str2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];

                // If last character are different, remove it and find minimum
                else
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], // Remove from str1
                            dp[i][j - 1]); // Remove from str2
            }
        }

        return dp[m][n];
    }

}
