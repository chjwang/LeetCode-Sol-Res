package com.freetymekiyan.algorithms.level.Easy;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Given two strings s and t, determine if they are isomorphic. <p> Two strings are isomorphic if
 * the characters in s can be replaced to get t. <p> All occurrences of a character must be replaced
 * with another character while preserving the order of characters. No two characters may map to the
 * same character but a character may map to itself. <p> For example, Given "egg", "add", return
 * true. <p> Given "foo", "bar", return false. <p> Given "paper", "title", return true. <p> Note:
 * You may assume both s and t have the same length. <p> Tags: Hash Table Similar Problems: (E) Word
 * Pattern
 */
public class IsomorphicStrings {

    private IsomorphicStrings is;

    /**
     * Store the last seen positions of current (i-th) characters in both strings.
     */
    public boolean isIsomorphic(String s, String t) {
        if (s == null || t == null)
            return false;

        int[] ms = new int[256];
        int[] mt = new int[256];

        for (int i = 0; i < s.length(); i++) {
            if (ms[s.charAt(i)] != mt[t.charAt(i)])
                return false;
            ms[s.charAt(i)] = mt[t.charAt(i)] = i + 1;
        }
        return true;
    }

    public boolean isIsomorphic2(String s, String t) {
        if (s == null || t == null)
            return false;
        if (s.length() != t.length())
            return false;

        Map<Character, Character> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);

            if (map.containsKey(c1)) {
                if (map.get(c1) != c2)// if not consistent with previous ones
                    return false;
            } else {
                if (map.containsValue(c2)) //if c2 is already being mapped, O(n) time here
                    return false;
                map.put(c1, c2);
            }
        }

        return true;
    }

    /**
     * Time improvement over isIsomorphic2. Use HashSet to store t's chars already seen.
     * @param s
     * @param t
     * @return
     */
    public boolean isIsomorphic3(String s, String t) {
        HashMap<Character, Character> map = new HashMap<>();
        HashSet<Character> mapped = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                if (t.charAt(i) == map.get(s.charAt(i)))
                    continue;
                else
                    return false;
            } else if (mapped.contains(t.charAt(i))) // O(1) time here
                return false;
            map.put(s.charAt(i), t.charAt(i));
            mapped.add(t.charAt(i));
        }
        return true;
    }

    @Before
    public void setUp() {
        is = new IsomorphicStrings();
    }

    @Test
    public void testEdgeCases() {
        Assert.assertFalse(is.isIsomorphic(null, null));
        Assert.assertFalse(is.isIsomorphic(null, ""));
        Assert.assertFalse(is.isIsomorphic("", null));
        Assert.assertTrue(is.isIsomorphic("", ""));
    }

    @Test
    public void testExamples() {
        // "egg", "addPrereq", return true
        Assert.assertTrue(is.isIsomorphic("egg", "addPrereq"));
        // "foo", "bar", return false
        Assert.assertFalse(is.isIsomorphic("foo", "bar"));
        // "paper", "title", return true
        Assert.assertTrue(is.isIsomorphic("paper", "title"));
        // "papper", "tittle", return true
        Assert.assertTrue(is.isIsomorphic("papper", "tittle"));
        // "abba", "abab"
        Assert.assertFalse(is.isIsomorphic("abba", "abab"));
    }

    @After
    public void tearDown() {
        is = null;
    }

}
