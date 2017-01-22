package com.freetymekiyan.algorithms.Other;

import java.util.HashMap;
import java.util.Map;

/**
 * Find the longest substring with k unique characters in a given string
 * Given a string you need to print longest possible substring that has exactly k unique characters.
 * If there are more than one substring of longest possible length, then print any one of them.
 *
 * Examples:
 *
 * "aabbcc", k = 1
 * Max substring can be any one from {"aa" , "bb" , "cc"}.
 *
 * "aabbcc", k = 2
 * Max substring can be any one from {"aabb" , "bbcc"}.
 *
 * "aabbcc", k = 3
 * There are substrings with exactly 3 unique characters
 * {"aabbcc" , "abbcc" , "aabbc" , "abbc" }
 * Max is "aabbcc" with length 6.
 *
 * "aaabbb", k = 3
 * There are only two unique characters, thus show error message.
 */
public class LongestSubstringKDistinct {

    /**
     * sliding window. two pointers: left, right
     * O(n)
     */
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (k == 0 || s == null || s.length() == 0)
            return 0;

        int[] count = new int[256];     // there are 256 ASCII characters in the world

        int left = 0, right = 0;  // left will be behind right
        int num = 0;
        int result = 0;

        for (; right < s.length(); right++) {
            if (count[s.charAt(right)]++ == 0) {
                // if count[s.charAt(right)] == 0, we know that it is a distinct character
                num++;
            }
            while (num > k && left < s.length()) {     // sliding window
                count[s.charAt(left)]--; // remove char at left
                if (count[s.charAt(left)] == 0) { // all char at left gone
                    num--;
                }
                left++;
            }
            result = Math.max(result, right - left + 1);
        }
        return result;
    }

    /**
     * Use hashmap instead of count table
     * @param s
     * @param k
     * @return
     */
    public int lengthOfLongestSubstringKDistinct2(String s, int k) {
        if (k == 0 || s == null || s.length() == 0)
            return 0;

        Map<Character, Integer> map = new HashMap<Character, Integer>();

        int maxLen = k;
        int left = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }

            if (map.size() > k) {
                maxLen = Math.max(maxLen, i - left);

                while (map.size() > k) {

                    char fc = s.charAt(left);
                    if (map.get(fc) == 1) {
                        map.remove(fc);
                    } else {
                        map.put(fc, map.get(fc) - 1);
                    }

                    left++;
                }
            }

        }

        maxLen = Math.max(maxLen, s.length() - left);

        return maxLen;
    }
}
