package com.freetymekiyan.algorithms.level.Easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Given a string, find the length of the longest substring without repeating
 * characters. For example, the longest substring without repeating letters for
 * "abcabcbb" is "abc", which the length is 3. For "bbbbb" the longest
 * substring is "b", with the length of 1.
 *
 * Tags: Hashtable, Two pointers, String
 */
class LongestSubstring {
    public static void main(String[] args) {
        // System.out.println(lengthOfLongestSubstring("abcabcbb"));
        // System.out.println(lengthOfLongestSubstring("bbbbb"));
        // System.out.println(lengthOfLongestSubstring(""));
        // System.out.println(lengthOfLongestSubstring("fdjskajfhh"));
        // System.out.println(lengthOfLongestSubstring("iiiiiiioooooooooooooo"));
        // System.out.println(lengthOfLongestSubstring("aeiou"));
        System.out.println(lengthOfLongestSubstring("hnwnkuewhsqmgbbuqcljjivswmdkqtbxixmvtrrbljptnsnfwzqfjmafadrrwsofsbcnuvqhffbsaqxwpqcac"));
    }

    /**
     * Traverse the string
     * Get current character
     * Update start point
     * Update max
     * Put current char in map
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;

        Map<Character, Integer> map = new HashMap<>();
        int start = 0;
        int max = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            /*start point can be recent dup's next char or last start*/
            start = Math.max(start, (map.containsKey(c)) ? map.get(c) + 1 : 0);

            /*if current str len is bigger then update*/
            max = Math.max(max, i - start + 1);

            /*add char to map with index*/
            map.put(c, i);
        }
        return max;
    }

    /**
     * 这道题用的方法是在LeetCode中很常用的方法，对于字符串的题目非常有用。
     * 首先brute force的时间复杂度是O(n^3), 对每个substring都看看是不是有重复的字符，找出其中最长的，复杂度非常高。
     *
     * 优化一些的思路是稍微动态规划一下，每次定一个起点，然后从起点走到有重复字符位置，过程用一个HashSet维护当前字符集，
     * 认为是constant操作，这样算法要进行两层循环，复杂度是O(n^2)。
     *
     * 最后，我们介绍一种线性的算法，也是这类题目最常见的方法。
     * 基本思路是维护一个窗口，每次关注窗口中的字符串，在每次判断中，左窗口和右窗口选择其一向前移动。
     * 同样是维护一个HashSet, 正常情况下移动右窗口，如果没有出现重复则继续移动右窗口，如果发现重复字符，则说明当前
     * 窗口中的串已经不满足要求，继续移动右窗口不可能得到更好的结果，此时移动左窗口，直到不再有重复字符为止，
     * 中间跳过的这些串中不会有更好的结果，因为他们不是重复就是更短。因为左窗口和右窗口都只向前，所以两个窗口都对
     * 每个元素访问不超过一遍，因此时间复杂度为O(2*n)=O(n),是线性算法。空间复杂度为HashSet的size,也是O(n).
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring1(String s) {
        if (s == null || s.length() == 0)
            return 0;
        Set<Character> set = new HashSet<>();
        int max = 0;
        int begin = 0;
        int end = 0;

        while (end < s.length()) {
            if (set.contains(s.charAt(end))) {
                if (max < end - begin) max = end - begin;
                while (s.charAt(begin) != s.charAt(end)) {
                    set.remove(s.charAt(begin));
                    begin++;
                }
                begin++; // move to next after current dup char
            } else {
                set.add(s.charAt(end));
            }
            end++;
        }
        max = Math.max(max, end - begin);
        return max;
    }

    public int lengthOfLongestSubstring2(String s) {
        int[] map = new int[256];

        int j = 0;
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            while (j < s.length() && map[s.charAt(j)] == 0) {
                map[s.charAt(j)] = 1;
                ans = Math.max(ans, j - i + 1);
                j++;
            }
            map[s.charAt(i)] = 0;  // remove i, move start index i to next one
        }

        return ans;
    }

    public int lengthOfLongestSubstring2_optimized(String s) {
        int[] map = new int[256]; // map from character's ASCII to its last occured index
        Arrays.fill(map, -1);

        int j = 0, i = 0;
        int ans = 0;
        while (i < s.length()) {
            while (j < s.length() && map[s.charAt(j)] == -1) {
                map[s.charAt(j)] = j;
                ans = Math.max(ans, j - i + 1);
                j++;
            }

            if (j == s.length())
                // reached the end of string, return ans
                return ans;

            // move start index i to first non-duplicate char
            i = map[s.charAt(j)] + 1;
            // reset duplicate char index to -1
            map[s.charAt(j)] = -1;
        }

        return ans;
    }

    /**
     * Use two pointers: start, end, and a count table/map to store each char's location in string
     * @param s
     * @return
     */
    public int lengthOfLongestSubstringBest(String s) {
        int len = s.length();
        if (len == 0) return 0;

        int[] countTable = new int[256];
        Arrays.fill(countTable, -1);
        int max = 1;
        int start = 0;
        int end = 1;

        countTable[s.charAt(0)] = 0; // first char index at 0
        while (end < len) {
            if (countTable[s.charAt(end)] >= start) {
                // reached an existing char, reset start index to the char after duplicate char
                start = countTable[s.charAt(end)] + 1;
            }
            max = Math.max(max, end - start + 1);
            countTable[s.charAt(end)] = end;
            end++;
        }
        return max;
    }

    /**
     * One pointer, but restart from the char after duplicate every time. Not as efficient.
     * @param s
     * @return
     */
    int lengthOfLongestSubstring(char[] s) {
        int[] count = new int[26];
        Arrays.fill(count, -1);

        int len = 0, maxL = 0;

        for (int i = 0; i < s.length; i++, len++) {
            if (count[s[i] - 'a'] >= 0) {
                // found a duplicate char
                maxL = Math.max(len, maxL);

                // reset start index i
                len = 0;
                i = count[s[i] - 'a'] + 1;

                // reset count table
                Arrays.fill(count, -1);
            }
            count[s[i] - 'a'] = i;
        }
        return Math.max(len, maxL);
    }
}
