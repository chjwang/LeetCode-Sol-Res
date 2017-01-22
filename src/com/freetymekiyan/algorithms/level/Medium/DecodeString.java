package com.freetymekiyan.algorithms.level.Medium;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given an encoded string, return it's decoded string.
 * <p>
 * The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated
 * exactly k times. Note that k is guaranteed to be a positive integer.
 * <p>
 * You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.
 * <p>
 * Furthermore, you may assume that the original data does not contain any digits and that digits are only for those
 * repeat numbers, k. For example, there won't be input like 3a or 2[4].
 * <p>
 * Examples:
 * <p>
 * s = "3[a]2[bc]", return "aaabcbc".
 * s = "3[a2[c]]", return "accaccacc".
 * s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
 * <p>
 * Tags: Depth-first Search, Stack
 */
public class DecodeString {

    /**
     * Two Stacks.
     * Use two stacks to save current level information:
     * One is the count, the other is the pattern so far.
     *
     * */
    public String decodeString(String s) {
        Deque<String> res = new ArrayDeque<>();
        Deque<Integer> count = new ArrayDeque<>();
        String decode = "";
        int num = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = 10 * num + c - '0';
            } else if (c == '[') {
                count.push(num);
                num = 0;
                res.push(decode);
                decode = "";
            } else if (c == ']') {
                String tmp = decode;
                decode = res.pop();
                for (int j = count.pop(); j > 0; j--) {
                    decode += tmp;
                }
            } else {
                decode += c;
            }
        }
        return decode;
    }

    /**
     * recursion
     *
     * @param s
     * @return
     */
    public String decodeString2(String s) {
        StringBuilder cur = new StringBuilder();
        int k = 0;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                k = k * 10 + (s.charAt(i) - '0');
            } else if (s.charAt(i) == '[') {
                //find the matching ]
                int begin = i;
                i++;
                int bracketCount = 1;
                while (bracketCount != 0) {
                    if (s.charAt(i) == '[') {
                        bracketCount++;
                    } else if (s.charAt(i) == ']') {
                        bracketCount--;
                    }
                    i++;
                }
                i--;

                String substr = decodeString3(s.substring(begin + 1, i));
                for (int j = 0; j < k; j++) {
                    cur.append(substr);
                }
                k = 0;
            } else {
                cur.append(s.charAt(i));
            }
        }
        return cur.toString();
    }

    /**
     * 一个DFS的题目, 给定的字符串可能会有嵌套很多层, 在每一层我们只要在碰到正常的字符就保存到当前层的结果中,
     * 如果碰到数字就另外保存起来作为倍数, 碰到'[' 就进入下层递归, 碰到']' 就将当前层结果返回, 这样在返回给上层
     * 之后就可以用倍数加入到上层结果字符串中. 最终当所有层都完成之后就可以得到结果. 在不同层次的递归中, 我们可以
     * 维护一个共同的位置索引, 这样在下层递归完成之后上层可以知道已经运算到哪里了.
     *
     */
    private int strIndex;

    public String decodeString3(String s) {
        // Adapt the input to my algorithm
        return dfs("1[" + s + "]").toString(); //no need to add 1[]
    }

    private StringBuilder dfs(String s) {
        StringBuilder cur = new StringBuilder();

        int k = 0;
        for (; strIndex < s.length(); ++strIndex) {
            char c = s.charAt(strIndex);
            if (c >= '0' && c <= '9') { // Calculate the number K
                k = k * 10 + c - '0';
            } else if (c == '[') { // Recursive step
                ++strIndex;
                StringBuilder sb = dfs(s);

                for (; k > 0; k--) cur.append(sb);
            } else if (c == ']') { // Exit the loop and return the result.
                break;
            } else {
                cur.append(c);
            }
        }

        return cur;
    }

    public String decodeString4(String s) {
        if (s==null || s.length() == 0) return s;
        i = 0;
        return decodeHelper(s.toCharArray());
    }

    /**
     * DFS recursion
     *
     * @param s
     * @return
     */
    int i = 0;
    private String decodeHelper(char[] s) {
        String res = "";
        int n = s.length;

        while (i < n && s[i] != ']') {
            if (s[i] < '0' || s[i] > '9') {
                res += s[i++];
            } else {
                int cnt = 0;
                while (i < n && s[i] >= '0' && s[i] <= '9') {
                    cnt = cnt * 10 + s[i++] - '0';
                }

                ++i; // skip [

                String t = decodeHelper(s);

                ++i; // skip ]

                while (cnt-- > 0) {
                    res += t;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        // 3[a]2[bc]
        Integer i;
        String input = "3[a]2[bc]";
        DecodeString ds = new DecodeString();
        System.out.println("input is: " + input);

        String output = ds.decodeString(input);
        System.out.println("result is: " + output);

        output = ds.decodeString2(input);
        System.out.println("result2 is: " + output);
    }


/**
 * Given a non-empty string, encode the string such that its encoded length is the shortest.
 The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is
 being repeated exactly k times.

 Note:
 k will be a positive integer and encoded string will not be empty or have extra space.
 You may assume that the input string contains only lowercase English letters. The string's length is at most 160.
 If an encoding process does not make the string shorter, then do not encode it. If there are several solutions, return any of them is fine.

 Example 1:
 Input: "aaa"
 Output: "aaa"
 Explanation: There is no way to encode it such that it is shorter than the input string, so we do not encode it.

 Example 2:
 Input: "aaaaa"
 Output: "5[a]"
 Explanation: "5[a]" is shorter than "aaaaa" by 1 character.

 Example 3:
 Input: "aaaaaaaaaa"
 Output: "10[a]"
 Explanation: "a9[a]" or "9[a]a" are also valid solutions, both of them have the same length = 5, which is the same as "10[a]".

 Example 4:
 Input: "aabcaabcd"
 Output: "2[aabc]d"
 Explanation: "aabc" occurs twice, so one answer can be "2[aabc]d".

 Example 5:
 Input: "abbbabbbcabbbabbbc"
 Output: "2[2[abbb]c]"
 Explanation: "abbbabbbc" occurs twice, but "abbbabbbc" can also be encoded to "2[abbb]c", so one answer can be "2[2[abbb]c]".

 记忆化搜索
 利用字典dp记录字符串的最优编码串

 枚举分隔点p， 将字符串拆解为left, right左右两部分

 尝试将left调用solve函数进行编码压缩，并对right递归调用encode函数进行搜索

 将left和right组合的最短字符串返回，并更新dp

 def __init__(self):
    self.dp = dict()

 def encode(self, s):
     """
     :type s: str
     :rtype: str
     """
     size = len(s)
     if size <= 1: return s
     if s in self.dp: return self.dp[s]
     ans = s
     for p in range(1, size + 1):
         left, right = s[:p], s[p:]
         t = self.solve(left) + self.encode(right)
         if len(t) < len(ans): ans = t
     self.dp[s] = ans
     return ans

 def solve(self, s):
     ans = s
     size = len(s)
     for x in range(1, size / 2 + 1):
         if size % x or s[:x] * (size / x) != s: continue
         y = str(size / x) + '[' + self.encode(s[:x]) + ']'
         if len(y) < len(ans): ans = y
     return ans


 */


}