package com.freetymekiyan.algorithms.level.Medium;

/**
 * Given two strings S and T, determine if they are both one edit distance apart.
 * <p>
 * Company Tags: Snapchat, Uber, Facebook, Twitter
 * Tags: String
 * Similar Problems: (H) Edit Distance
 *
 * Hint:
 1. If | n – m | is greater than 1, we know immediately both are not one-edit distance apart.
 2. It might help if you consider these cases separately, m == n and m ≠ n.
 3. Assume that m is always ≤ n, which greatly simplifies the conditional statements.
    If m > n, we could just simply swap S and T.
 4. If m == n, it becomes finding if there is exactly one modified operation.
    If m ≠ n, you do not have to consider the delete operation. Just consider the insert operation in T.

 [分析]
Edit Distance算法是DP的经典题

 这个题目只要O(1)的空间，O(n)的时间复杂度。假定有一下几种情况
 1）修改一个字符（假定两个String等长）
 2）插入一个字符（中间或者结尾）

 [注意事项]
 1）shift变量的作用
 */
public class OneEditDistance {

    /**
     * Edit distance: add, remove, or replace.
     * Three possible situations:
     * 1) s and t are of same length, replace
     * 2) delete 1 char from s
     * 3) delete 1 char from t
     * If the difference of their lengths are larger than 1, return false.
     * Loop through the shorter string to find a different char.
     * If found and they are of same length, the rest of them should be the same.
     * If found and they are of different length, the rest of shorter string, including that char,
     * should be the same as the rest of longer string, excluding that char.
     * If all chars are the same, it can only be the last character in longer string.
     * Return true iff longer string is one character longer.
     */
    public boolean isOneEditDistance(String s, String t) {
        int m = s.length();
        int n = t.length();
        if (m > n) return isOneEditDistance(t, s); // n is always bigger
        if (n - m > 1) return false;

        int i = 0;
        int shift = n - m; // 0 or 1

        while (i < m && s.charAt(i) == t.charAt(i))
            i++; // arrives at non-matching char

        if (i == m)
            return shift > 0; // whether T has more characters

        if (shift == 0)
            i++; // skip both

        while (i < m && s.charAt(i) == t.charAt(i + shift))
            i++; // go on

        return i == m; // should reach the end of shorter string
    }

    /**
    The basic idea is we keep comparing s and t from the beginning, once there's a difference,
     we try to replace s(i) with t(j) or insert t(j) to s(i) and see if the rest are the same.

    Example: i and j are the two pointers of S and T, we found that 'b' != 'c' and we try to replace it:

    i                           i
    S: a c d      replace       S: a b d
    T: a b c d   --------->     T: a b c d    --->  "d" != "cd", no good
    j                           j
    now we try to insert T(j) to S(i) and we get:

    i                           i
    S: a c d      insert        S: a b c d
    T: a b c d   --------->     T: a b c d    --->  "cd" == "cd", viola!
    j                           j
    To keep the code simple, we make s is always shorter than t, so we don't need to try deletion.
     */
    public boolean isOneEditDistance2(String s, String t) {
        if (s == null || t == null)
            return false;

        if (s.length() > t.length())
            return isOneEditDistance2(t, s);

        int i = 0, j = 0;

        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) != t.charAt(j)) {
                // we try to replace s[i] with t[j] or insert t[j] to s[i]
                // then compare the rest and see if they are the same
                return s.substring(i + 1).equals(t.substring(j + 1)) ||
                        s.substring(i).equals(t.substring(j + 1));
            }

            i++; j++;
        }

        return t.length() - j == 1;
    }
    public boolean isOneEditDistance2_2(String s, String t) {
        if (s == null || t == null)
            return false;

        if (s.length() > t.length())
            return isOneEditDistance2(t, s);

        int i = 0;

        while (i < s.length() && i < t.length()) {
            if (s.charAt(i) != t.charAt(i)) {
                return s.substring(i + 1).equals(t.substring(i + 1)) ||
                        s.substring(i).equals(t.substring(i + 1));
            }

            i++;
        }

        return t.length() - i == 1;
    }

    public boolean isOneEditDistance3(String s, String t) {
        if (s.length() > t.length()) {
            return isOneEditDistance3(t, s);
        }
        if (s.length()+1 < t.length()) {
            return false;
        }
        char[] cs=s.toCharArray();
        char[] ct=t.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] != ct[i]) {
                if (s.length() == t.length()) {
                    return s.substring(i+1, s.length()).equals(t.substring(i+1, t.length()));
                } else {
                    return s.substring(i, s.length()).equals(t.substring(i+1, t.length()));
                }
            }
        }
        return s.length() != t.length();
    }
}
