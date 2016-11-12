<<<<<<< HEAD
package com.freetymekiyan.algorithms.level.Hard;
=======
package com.freetymekiyan.algorithms.level.hard;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
>>>>>>> 65c45a8ca4844254c0cfb702bf71e83c3c72e0fa

/**
 * Implement regular expression matching with support for '.' and '*'.
 * <p>
 * '.' Matches any single character.
 * '*' Matches zero or more of the preceding element.
 * <p>
 * The matching should cover the entire input string (not partial).
 * <p>
 * The function prototype should be:
 * bool isMatch(const char *s, const char *p)
 * <p>
 * Some examples:
 * isMatch("aa","a") → false
 * isMatch("aa","aa") → true
 * isMatch("aaa","aa") → false
 * isMatch("aa", "a*") → true
 * isMatch("aa", ".*") → true
 * isMatch("ab", ".*") → true
 * isMatch("aab", "c*a*b") → true
 * <p>
 * Company Tags: Google, Uber, Airbnb, Facebook, Twitter
 * Tags: Dynamic Programming, Backtracking, String
 * Similar Problems:  (H) Wildcard Matching
 */
public class RegularExpMatching {

    private RegularExpMatching r;

    /**
<<<<<<< HEAD
     * Basically, the OPT[i][j] means preceding substring of length i of s and
     * length j of p. For any two substrings, the value of OPT[i][j] can be
     * from one of following four cases:
     *
     * case 1: OPT[i-1][j-1] is true, and ith character of s is equal to j th
     * character of p. Or j th character of p is '.', match
     * case 2: OPT[i-1][j] is true, then my pattern now is '*' and preceding
     * character is equal to incoming character of s
     * case 3: OPT[i][j-1] is true, then my pattern now is '*' which can match
     * an empty string
     * case 4: OPT[i][j-2] is true, and the pattern like (a*) matches an empty
     * string, 
     *
     * base case is the OPT[0][0], OPT[i][0], OPT[0][j].
     *
     * Time Complexity: O(m*n). Space: O(m*n)
     */
    public static boolean isMatch(String s, String p) {
        /*validation*/
        if (s == null || p == null) return s == p;

        int m = s.length();
        int n = p.length();
        if (m == 0 && n == 0) return true;

        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true; // base case
        // fill first column p.length() == 0
        for (int i = 1; i <= m; i++) dp[i][0] = false; // no need, default is false
        // fill first row
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') { // j - 1 is current index in pattern
                dp[0][j] = dp[0][j - 2]; // same as two p chars before
            } else {
                dp[0][j] = false; // no need, default us false
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char pChar = p.charAt(j - 1);
                char sChar = s.charAt(i - 1);

                // not "*", matches
                if (pChar == sChar || pChar == '.') {
                    dp[i][j] = dp[i - 1][j - 1]; // same as last match
                }
                // "*" matches
                else if (pChar == '*') {
                    char pCharPre = p.charAt(j - 2);
                    if (pCharPre == sChar || pCharPre == '.') {
                        // chars before "*" matches: 0, not used, 1
                        dp[i][j] = dp[i - 1][j] || dp[i][j - 2] || dp[i][j - 1]; // "a  " or "a*" or "*"
                    } else {
                        // char before "*" don't match
                        dp[i][j] = dp[i][j - 2]; // same as two p chars before
                    }
                } else { // simply don't match
                    dp[i][j] = false; // no need, default is false
                }
                System.out.println("i: " + i + " j: " + j + " " + dp[i][j]);
            }
        }
        return dp[m][n];
    }

    public boolean isMatch2(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean [][] dp = new boolean[m+1][n+1];
        dp[0][0] = true;

        for(int j = 1; j<=n; j++){
            if(p.charAt(j-1) == '*'){
                dp[0][j] = dp[0][j-2];
            }
        }

        for(int i = 1; i<=m; i++){
            for(int j = 1; j<=n; j++){
                char sChar = s.charAt(i-1);
                char pChar = p.charAt(j-1);

                //first chars match - equal or match .
                if(sChar == pChar || pChar == '.'){
                    dp[i][j] = dp[i-1][j-1];
                }
                // * match
                else if(pChar == '*'){
                    //chars before * match: equal or matches .
                    char pCharPre = p.charAt(j - 2);
                    if(sChar == pCharPre || pCharPre == '.'){
                        dp[i][j] = dp[i][j-2] || dp[i-1][j];
                    }else{
                        //chars before * don't match，then * means 0 preceding element
                        dp[i][j] = dp[i][j-2];
                    }
                }
            }
        }
        return dp[m][n];
    }

    /**
     * Recursive
     *
     * Time Complexity: Exponential. Space: O(n). n is length of string
     *
     * @param s
     * @param p
     * @return
     */
    public static boolean isMatch3(String s, String p) {
        // base case
        if (p.length() == 0) return s.length() == 0;

        // special case
        if (p.length() == 1) {
            // if the length of s is 0, return false
            if (s.length() < 1)
                return false;
            //if the first does not match, return false
            else if ((p.charAt(0) != s.charAt(0)) && (p.charAt(0) != '.'))
                return false;
            // otherwise, compare the rest of the string of s and p.
            else
                return isMatch(s.substring(1), p.substring(1));
        }

        // case 1: when the second char of p is not '*'
        if (p.charAt(1) != '*') {
            if (s.length() < 1)
                return false;
            if ((p.charAt(0) != s.charAt(0)) && (p.charAt(0) != '.'))
                return false;
            else
                return isMatch(s.substring(1), p.substring(1));
        }
        // case 2: when the second char of p is '*', complex case.
        else {
            //case 2.1: a char and '*' can stand for 0 element
            if (isMatch(s, p.substring(2)))
                return true;

            //case 2.2: a char and '*' can stand for 1 or more preceding element,
            //so try every sub string
            int i = 0;
            while (i<s.length() && (s.charAt(i)==p.charAt(0) || p.charAt(0)=='.')){
                if (isMatch(s.substring(i + 1), p.substring(2))) {
                    return true;
                }
                i++;
=======
     * DP. O(mn) Time, O(mn) Space.
     * Recurrence relation:
     * f[i][j]: if s[0..i-1] matches p[0..j-1]
     * if p[j - 1] != '*'
     * -   f[i][j] = f[i - 1][j - 1] && s[i - 1] == p[j - 1]
     * if p[j - 1] == '*', denote p[j - 2] with x
     * -   f[i][j] is true iff any of the following is true
     * -   1) "x*" repeats 0 time and matches empty: f[i][j - 2]
     * -   2) "x*" repeats >= 1 times and matches "x*x": s[i - 1] == x && f[i - 1][j]
     * '.' matches any single character
     * Base case:
     * When s and p are both empty, match.
     * When p is empty, but s is not, don't match.
     * When s is empty, but p is not, only matches when the last of p is '*' and previous pattern also matches.
     * That's p[j-1] == '*' && f[0][j-2]
     */
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        // Base cases
        dp[0][0] = true;
//        for (int i = 1; i <= m; i++) { // Is false by default
//            dp[i][0] = false;
//        }
        // p[0.., j - 3, j - 2, j - 1] matches empty iff p[j - 1] is '*' and p[0..j - 3] matches empty
        for (int j = 1; j <= n; j++) {
            dp[0][j] = j > 1 && '*' == p.charAt(j - 1) && dp[0][j - 2];
        }
        // Build matrix
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) != '*') {
                    dp[i][j] = dp[i - 1][j - 1] && (s.charAt(i - 1) == p.charAt(j - 1) || '.' == p.charAt(j - 1));
                } else {
                    // p[0] cannot be '*' so no need to check "j > 1" here
                    dp[i][j] = dp[i][j - 2] // Matches empty
                               /*
                                * If s[i-1] == a, p[j-2] == a, or p[j-2] == '.', and p[j-1] == '*'
                                * p can match s if s[0...i-2] matches p[0...j-1]
                                * Because s[i-2] already matches "p[j-2]*",
                                * "s[i-2]s[i-1]" can match "p[j-2]*" by repeating p[j-2]
                                */
                               || (s.charAt(i - 1) == p.charAt(j - 2) || '.' == p.charAt(j - 2)) && dp[i - 1][j];
                }
>>>>>>> 65c45a8ca4844254c0cfb702bf71e83c3c72e0fa
            }
            return false;
        }
<<<<<<< HEAD
=======

        return dp[m][n];
    }

    @Before
    public void setUp() {
        r = new RegularExpMatching();
    }

    @Test
    public void testExamples() {
        Assert.assertFalse(r.isMatch("aa", "a"));
        Assert.assertTrue(r.isMatch("aa", "aa"));
        Assert.assertFalse(r.isMatch("aaa", "aa"));
        Assert.assertTrue(r.isMatch("aa", "a*"));
        Assert.assertTrue(r.isMatch("aa", ".*"));
        Assert.assertTrue(r.isMatch("ab", ".*"));
        Assert.assertTrue(r.isMatch("aab", "c*a*b"));
    }

    @After
    public void tearDown() {
        r = null;
>>>>>>> 65c45a8ca4844254c0cfb702bf71e83c3c72e0fa
    }
}
