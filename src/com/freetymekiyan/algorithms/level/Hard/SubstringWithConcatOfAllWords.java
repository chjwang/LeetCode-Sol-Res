package com.freetymekiyan.algorithms.level.Hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * You are given a string, S, and a list of words, L, that are all of the same
 * length. Find all starting indices of substring(s) in S that is a
 * concatenation of each word in L exactly once and without any intervening
 * characters.
 *
 * For example, given:
 * S: "barfoothefoobarman"
 * L: ["foo", "bar"]
 *
 * You should return the indices: [0,9].
 * (order does not matter).
 *
 * Tags: Hash Table, Two Pointers, String
 */
public class SubstringWithConcatOfAllWords {
    public static void main(String[] args) {
        String S = "barfoothefoobarman";
        String[] L = new String[]{"foo", "bar"};
        List<Integer> l = findSubstring(S, L);
        for (int i : l) System.out.print(i + " ");
    }

    /**
     * Build a map for words in L and its relative counts
     *
     * 和strStr那题的双指针解法类似。关键在于如何判断以任意i起始的S的substring是否整个L的concatenation。
     * 这里显然要用到hash table。由于L中可能存在重复的word，所以hash table的key = word，val = count of the word。
     *
     * 在建立好L的hash table后，对每个S[i]进行检查。这里的一个技巧建立一个新的hash table记录已经找到的word。
     * 因为L的hash table需要反复利用，不能被修改，并且如果以hash table作为参数进行值传递的化，时间空间消耗都很大。
     */
    public static List<Integer> findSubstring(String S, String[] L) {
        List<Integer> res = new ArrayList<>();
        if (S == null || L == null || L.length == 0) return res;
        int len = L[0].length(); // length of each word

        Map<String, Integer> map = new HashMap<>(); // map for L
        for (String w : L)
            map.put(w, map.getOrDefault(w, 0) + 1);

        for (int i = 0; i <= S.length() - len * L.length; i++) {
            Map<String, Integer> copy = new HashMap<>(map);
            for (int j = 0; j < L.length; j++) { // check if match
                String str = S.substring(i + j * len, i + j * len + len); // next word
                if (copy.containsKey(str)) { // is in remaining words
                    int count = copy.get(str);
                    if (count == 1)
                        copy.remove(str);
                    else
                        copy.put(str, count - 1);
                    if (copy.isEmpty()) { // matches
                        res.add(i);
                        break;
                    }
                } else
                    break; // not in L
            }
        }
        return res;
    }

    /**
     * 这道题看似比较复杂，其实思路和Longest Substring Without Repeating Characters差不多。
     * 因为那些单词是定长的，所以本质上和单一个字符一样。和Longest Substring Without Repeating Characters的
     * 区别只在于我们需要维护一个字典，然后保证目前的串包含字典里面的单词有且仅有一次。
     *
     * 思路仍然是维护一个窗口，如果当前单词在字典中，则继续移动窗口右端，否则窗口左端可以跳到字符串下一个单词了。
     * 假设源字符串的长度为n，字典中单词的长度为l。因为不是一个字符，所以我们需要对源字符串所有长度为l的子串进行判断。
     * 做法是: i从0到l-1个字符开始，得到开始index分别为i, i+l, i+2*l, ...的长度为l的单词。
     * 这样就可以保证判断到所有的满足条件的串。因为每次扫描的时间复杂度是O(2*n/l)
     * (每个单词不会被访问多于两次，一次是窗口右端，一次是窗口左端)，总共扫描l次（i=0, ..., l-1)，
     * 所以总复杂度是O(2*n/l*l)=O(n)，是一个线性算法。空间复杂度是字典的大小，即O(m*l)，其中m是字典的单词数量。
     *
     * @param S
     * @param L
     * @return
     */
    public static ArrayList<Integer> findSubstring2(String S, String[] L) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if (S == null || L == null || S.length() == 0 || L.length == 0)
            return res;
        int wordLen = L[0].length();//same length for each word in dictionary

        //put given dictionary into hashmap with each word's count
        HashMap<String, Integer> dict = new HashMap<>();
        for (String word : L)
            dict.put(word, dict.getOrDefault(word, 0) + 1);

        for (int i = 0; i < wordLen; i++) {
            int count = 0;
            int index = i;//index of each startpoint
            Map<String, Integer> curDict = new HashMap<>();
            //till the first letter of last word
            for (int j = i; j <= S.length() - wordLen; j += wordLen) {
                String curWord = S.substring(j, j + wordLen);
                //check each word to tell if it existes in give dictionary
                if (!dict.containsKey(curWord)) {
                    curDict.clear();
                    count = 0;
                    index = j + wordLen;
                } else {
                    //form current dictionary
                    if (!curDict.containsKey(curWord))
                        curDict.put(curWord, 1);
                    else
                        curDict.put(curWord, curDict.get(curWord) + 1);

                    //count for current found word and check if it exceed given word count
                    if (curDict.get(curWord) <= dict.get(curWord)) {
                        count++;
                    } else {
                        while (curDict.get(curWord) > dict.get(curWord)) {
                            String temp = S.substring(index, index + wordLen);
                            curDict.put(temp, curDict.get(temp) - 1);
                            index = index + wordLen;//make index move next
                        }
                    }

                    //put into res and move index point to nextword
                    //and update current dictionary as well as count num
                    if (count == L.length) {
                        res.add(index);
                        String temp = S.substring(index, index + wordLen);
                        curDict.put(temp, curDict.get(temp) - 1);
                        index = index + wordLen;
                        count--;
                    }
                }
            }//end for j
        }//end for i
        return res;
    }
}
