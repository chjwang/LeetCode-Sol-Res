package com.freetymekiyan.algorithms.level.Medium;

/**
 * Find the length of the longest substring T of a given string (consists of lowercase letters only) such that every
 * character in T appears no less than k times.
 * <p>
 * Example 1:
 * <p>
 * Input:
 * s = "aaabb", k = 3
 * <p>
 * Output:
 * 3
 * <p>
 * The longest substring is "aaa", as 'a' is repeated 3 times.
 * <p>
 * Example 2:
 * <p>
 * Input:
 * s = "ababbc", k = 2
 * <p>
 * Output:
 * 5
 * <p>
 * The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
 */
public class LongestSubstringwithAtLeastKRepeatingCharacters {

    /**
     * 先统计出字符串中每个字符出现的次数，对于出现次数少于k的字符，任何一个包含该字符的字符串都不是符合要求的子串，
     * 因此这样的字符就是分隔符，应该以这些出现次数少于k次的字符做分隔符打断原字符串，
     * 然后对各个打断得到的字符串进行递归统计，得到最长的符合要求的字符串。
     *
     * 如果一个字符串中不包含分隔符(即每个字符出现的次数都达到了k次及以上次数)，那么这个字符串就是符合要求的子串。
     *
     * Divide and conquer.
     * For those characters in string which repeat less than k times, we try to divide the string into two parts:
     * 1) left part: from the beginning to character's left
     * 2) right part: from the character's right to the end
     * The result is the larger one of these two parts.
     */
    public int longestSubstring(String s, int k) {
        return helper(s, 0, s.length(), k);
    }

    /**
     *
     * @param s
     * @param start inclusive
     * @param end exclusive
     * @param k
     * @return
     */
    private int helper(String s, int start, int end, int k) {
        if (end < start) {
            return 0;
        }
        if (end - start < k) { // String length less than k
            return 0;
        }
        int[] count = new int[26]; // Character count for current string
        for (int i = start; i < end; i++) {
            count[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            if (count[i] == 0) {
                continue;
            }
            if (count[i] < k) { // find the fist char appearing less than k times
                for (int j = start; j < end; j++) {
                    if (s.charAt(j) == i + 'a') {
                        int left = helper(s, start, j, k);
                        int right = helper(s, j + 1, end, k);
                        return Math.max(left, right);
                    }
                }
            }
        }
        return end - start; // the whole string is good
    }

    /**
     * Instead of dividing into 2 parts, use regex to split the string using chars of less than k times.
     * @param s
     * @param k
     * @return
     */
    public int longestSubstring2(String s, int k) {
        if (k <= 1) {
            return s.length();
        }

        int[] repeat = new int['z' + 1];
        for (int i = 0; i < s.length(); i++) {
            repeat[s.charAt(i)]++;
        }
        StringBuilder regex = new StringBuilder();
        for (int i = 'a'; i <= 'z'; i++) {
            if (repeat[i] > 0 && repeat[i] < k) {
                if (regex.length() > 0)
                    regex.append("|");

                regex.append((char) i);
            }
        }
        if (regex.length() > 0) {
            String[] strs = s.split(regex.toString());
            int max = 0;
            int subMax = 0;
            for (String str : strs) {
                subMax = longestSubstring2(str, k);
                if (subMax > max) {
                    max = subMax;
                }
            }
            return max;
        } else {
            //没有分隔符,说明s中的每一个字符出现的次数都大于等于k
            return s.length();
        }
    }
}
