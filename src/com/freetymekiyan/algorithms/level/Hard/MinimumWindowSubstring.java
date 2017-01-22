package com.freetymekiyan.algorithms.level.Hard;

import java.util.HashMap;
import java.util.Map;

/**
 * Minimum Window Substring Given a string S and a string T, find the minimum window in S which will
 * contain all the characters in T in complexity O(n). For example, S = "ADOBECODEBANC" T = "ABC"
 * Minimum window is "BANC". Tags: Hash Table Two Pointers String Similar Problems:  (H) Substring
 * with Concatenation of All Words (M) Minimum Size Subarray Sum (H) Sliding Window Maximum
 *
 * Analysis: 1.Store the char in string t as a key and times appearing as value into a hashmap; 2.If
 * char at s is contained in hashmap, value(times) in map - 1 and count ++; 3.When count =
 * t.length(), minLenght and minStart records the min number 4.Slide window and let the left point
 * to the first char in s ,which is in map s                        t A D O B E C O D E B A N C
 * A B C A D O B E C B E C O D E B A C O D E B A B A N C
 *
 * @author chenshuna
 */

public class MinimumWindowSubstring {
    /**
     * 这道题是字符串处理的题目，和Substring with Concatenation of All Words思路非常类似，同样是建立一个字典，
     * 然后维护一个窗口。区别是在这道题目中，因为可以跳过没在字典里面的字符（也就是这个串不需要包含且仅仅包含字典里面的字符，
     * 有一些不在字典的仍然可以满足要求），所以遇到没在字典里面的字符可以继续移动窗口右端，而移动窗口左端的条件是
     * 当找到满足条件的串之后，一直移动窗口左端直到有字典里的字符不再在窗口里。
     *
     * 在实现中就是维护一个HashMap，一开始key包含字典中所有字符，value就是该字符的数量，然后遇到字典中字符时就将
     * 对应字符的数量减一。
     *
     * 算法的时间复杂度是O(n),其中n是字符串的长度，因为每个字符再维护窗口的过程中不会被访问多于两次。
     * 空间复杂度则是O(字典的大小)，也就是代码中T的长度。
     *
     * 初始化
     start = i = 0
     i 逐渐往后扫描S直到窗口S[start…i]包含所有T的字符，此时i = 6（字符c的位置）
     缩减窗口：此时我们注意到窗口并不是最小的，需要调整 start 来缩减窗口。
     缩减规则为：如果S[start]不在T中 或者 S[start]在T中但是删除后窗口依然可以包含T中的所有字符，那么start = start+1，
     直到不满足上述两个缩减规则。缩减后i即为窗口的起始位置，此例中从e开始窗口中要依次删掉e、b、a、d，start最后等于4 ，
     那么得到一个窗口大小为6-4+1 = 3
     start = start+1(此时窗口肯定不会包含所有的T中的字符)，跳转到步骤2继续寻找下一个窗口。
     本例中还以找到一个窗口start = 5，i = 8，比上个窗口大，因此最终的最小窗口是S[4…6]
     具体实现时，要用哈希表来映射T中字符以便在O(1)时间内判断一个字符是否在T中，由于是字符缘故，可以用数组简单的来实现；
     还需要一个哈希表来记录扫描时T中的某个字符在S中出现的次数，也可以用数组实现

     */
    public static String minWindow(String s, String t) {
        if (s.length() == 0 || t.length() == 0 || s.length() < t.length())
            return "";

        Map<Character, Integer> map = new HashMap<>();
        for (char c : t.toCharArray())
            map.put(c, map.getOrDefault(c, 0) + 1);

        int left = 0;
        int count = 0; // number of matched chars

        int minStart = 0;
        int minLength = s.length() + 1; // initial value set to MAX

        for (int right = 0; right < s.length(); right++) { // move window right end
            if (map.containsKey(s.charAt(right))) {
                map.put(s.charAt(right), map.get(s.charAt(right)) - 1);
                if (map.get(s.charAt(right)) >= 0) count++;
                while (count == t.length()) { // all t's char matched
                    if ((right - left + 1) < minLength) { // record smaller window size and location
                        minStart = left;
                        minLength = right - left + 1;
                    }
                    if (map.containsKey(s.charAt(left))) { // move window left end
                        map.put(s.charAt(left), map.get(s.charAt(left)) + 1); // remove left, bump up counter
                        if (map.get(s.charAt(left)) > 0) count--;
                    }
                    left++;
                }
            }
        }
        if (minLength == s.length() + 1) {
            return "";
        }
        return s.substring(minStart, minStart + minLength);
    }

    /**
     *
     We are required to implement a O(n)O(n) algorithm. We can achieve it by using two pointers.
     We will use pointer “start” and pointer “end”, which indicates the substring we are processing.

     Firstly, we will move the “end” pointer right, until we find all characters. But how can we know
     that we have found all the characters? There may be duplicate characters in string T. So I use
     a HashMap to save the number of each characters in string T.
     Another HashMap is used to save the number of characters we found. When we move pointer “end”,
     we will check if the number of this character is smaller than we need to found. If it is, we
     will increase one to a total counter and increase one to the number of this character.
     Otherwise only the number of this character is increased.

     When the total counter equals to length of T, we know that we have found all characters. Now we
     can move the “start” pointer as right as possible. We can move it when it pointing to a character
     that is not in T, or even it is in T, but the number of this character is larger than the number
     we need to find. In other words, we move “start” pointer when the substring from “start” to “end”
     does contains all characters in T.

     We can compare the value end–start+1end–start+1, which is the length of a substring that contains
     all characters in T, to the minimum length. If it’s shorter, we can update the minimum length.

     In fact, we can also use an array to save the number of each characters. It could be faster than
     using HashMap
     */
    public String minWindow2(String S, String T) {
        Map<Character, Integer> dict = new HashMap<>();
        for (int i = 0; i < T.length(); i++) {
            char c = T.charAt(i);
            dict.put(c, dict.getOrDefault(c, 0) + 1);
        }

        Map<Character, Integer> found = new HashMap<>();
        int foundCounter = 0;
        int start = 0;
        int end = 0;
        int min = Integer.MAX_VALUE;
        String minWindow = "";

        while (end < S.length()) {
            char c = S.charAt(end);
            if (dict.containsKey(c)) {
                if (found.containsKey(c)) {
                    if (found.get(c) < dict.get(c))
                        foundCounter++;
                    found.put(c, found.get(c) + 1);
                } else {
                    found.put(c, 1);
                    foundCounter++;
                }
            }
            if (foundCounter == T.length()) {
                //When foundCounter equals to T.length(), in other words, found all characters.
                char sc = S.charAt(start);
                while (!found.containsKey(sc) || found.get(sc) > dict.get(sc)) {
                    if (found.containsKey(sc) && found.get(sc) > dict.get(sc))
                        found.put(sc, found.get(sc) - 1);
                    start++;
                    sc = S.charAt(start);
                }
                if (end - start + 1 < min) {
                    minWindow = S.substring(start, end + 1);
                    min = end - start + 1;
                }
            }
            end++;
        }
        return minWindow;
    }

    public static void main(String[] args) {
        System.out.print(minWindow("ADOBECODEBANC", "ABC"));
    }
}