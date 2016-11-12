package com.freetymekiyan.algorithms.level.Hard;

/**
 * Implement regular expression matching with support for '.' and '*'.
 *
 * '.' Matches any single character.
 * '*' Matches zero or more of the preceding element.
 *
 * The matching should cover the entire input string (not partial).
 *
 * The function prototype should be:
 * bool isMatch(const char *s, const char *p)
 *
 * Some examples:
 * isMatch("aa","a") → false
 * isMatch("aa","aa") → true
 * isMatch("aaa","aa") → false
 * isMatch("aa", "a*") → true
 * isMatch("aa", ".*") → true
 * isMatch("ab", ".*") → true
 * isMatch("aab", "c*a*b") → true
 *
 * Note:
 * "*" only works on the preceding one element, not the whole string.
 *
 * Tags: DP, Backtracking, String
 */
class RegularExpMatching {

    public static void main(String[] args) {
        System.out.println(isMatch("aa", "a"));
        System.out.println(isMatch("aa", "aa"));
        System.out.println(isMatch("aaa", "aa"));
        System.out.println(isMatch("aa", "a*"));
        System.out.println(isMatch("aa", ".*"));
        System.out.println(isMatch("ab", ".*"));
        System.out.println(isMatch("aab", "c*a*b"));
    }

    /**
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
            }
            return false;
        }
    }
}
