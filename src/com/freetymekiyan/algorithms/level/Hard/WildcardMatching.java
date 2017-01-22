package com.freetymekiyan.algorithms.level.Hard;

/**
 * Implement wildcard pattern matching with support for '?' and '*'.
 * '?' Matches any single character.
 * '*' Matches any sequence of characters (including the empty sequence).
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
 * isMatch("aa", "*") → true
 * isMatch("aa", "a*") → true
 * isMatch("ab", "?*") → true
 * isMatch("aab", "c*a*b") → false
 *
 * My own examples:
 * isMatch("aab", "a*a*b") → true
 * isMatch("a", "a*") → true
 *
 * Tags: DP, Backtracking, Greedy, String
 */
class WildcardMatching {
    public static void main(String[] args) {
        WildcardMatching w = new WildcardMatching();
        /*input validation*/
        // System.out.println(w.isMatch("", "a")); // false
        // System.out.println(w.isMatch("a", "")); // false
        // System.out.println(w.isMatch("", "")); // true
        // System.out.println(w.isMatch(null, null)); // true
        // System.out.println(w.isMatch("a", null)); // false
        // System.out.println(w.isMatch(null, "null")); // false
        /*letters*/           
        // System.out.println(w.isMatch("a", "aa")); // false
        // System.out.println(w.isMatch("aa", "a")); // false
        // System.out.println(w.isMatch("aa", "aa")); // true
        // System.out.println(w.isMatch("aa", "ab")); // false
        /**s*/                
        // System.out.println(w.isMatch("aa", "*")); // true
        /*?s*/                
        // System.out.println(w.isMatch("aa", "?")); // false
        // System.out.println(w.isMatch("a", "?")); // true
        /*letters and *s*/    
        // System.out.println(w.isMatch("abc", "a*")); // true
        // System.out.println(w.isMatch("ab", "a*")); // true
        // System.out.println(w.isMatch("ab", "*a")); // false
        // System.out.println(w.isMatch("a", "a*")); // true
        // System.out.println(w.isMatch("bcsa", "*a")); // true
        // System.out.println(w.isMatch("bcs", "*a")); // false
        // System.out.println(w.isMatch("bbbbbbbbbb", "*bbbbb")); // true

        /* * and ? */
        // System.out.println(isMatch("b", "*?*?")); // false
    }

    /**
     * DP, two pointers
     *
     * The basic idea is to have one pointer for the string and one pointer for the pattern.
     * This algorithm iterates at most length(string) + length(pattern) times, for each iteration,
     * at least one pointer advance one step.
     *
     * remember the index of * and matched sequence
     * advance only pattern pointer when * is found
     * match the sequence after * in pattern with the rest of the string
     */
    public boolean isMatch(String str, String pattern) {
        if (str == null && pattern == null) return true;
        if (str == null || pattern == null) return false;
        
        int s = 0, p = 0, match = 0, starIdx = -1; // must be -1
        while (s < str.length()) {
            if (p < pattern.length()  &&
                    (pattern.charAt(p) == '?' ||
                            str.charAt(s) == pattern.charAt(p))){ // found ? or same chars
                s++; // move both pointers
                p++;
            } else if (p < pattern.length() && pattern.charAt(p) == '*') { // found *
                starIdx = p; // save star index in pattern
                match = s; // save current index of string
                p++; // only move pattern pointer forward
            } else if (starIdx != -1){ // try to find last star
                p = starIdx + 1; // move to * one char behind star
                match++; // move current index of string
                s = match;
            } else return false; // not ?, not same char, not *, don't match
        }
        // check remaining characters in pattern, can only be astroid
        while (p < pattern.length() && pattern.charAt(p) == '*') p++;
        return p == pattern.length(); // no remaining
    }

    public boolean isMatch2(String s, String p) {
        int i = 0;
        int j = 0;
        int starIndex = -1;
        int iIndex = -1;

        while (i < s.length()) {
            if (j < p.length() && (p.charAt(j) == '?' || p.charAt(j) == s.charAt(i))) {
                ++i;
                ++j;
            } else if (j < p.length() && p.charAt(j) == '*') {
                starIndex = j;
                iIndex = i;
                j++;
            } else if (starIndex != -1) {
                j = starIndex + 1;
                i = iIndex+1;
                iIndex++;
            } else {
                return false;
            }
        }

        while (j < p.length() && p.charAt(j) == '*') {
            ++j;
        }

        return j == p.length();
    }
}
