package com.freetymekiyan.algorithms.level.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given an array of strings, return all groups of strings that are anagrams.
 *
 * An anagram is a type of word play, the result of rearranging the letters of a word or phrase to
 * produce a new word or phrase, using all the original letters exactly once; for example,
 * "Torchwood" can be rearranged into "Doctor Who".
 *
 * Note: All inputs will be in lower-case.
 *
 * Tags: Hash table, String
 */
class Anagrams {
    public static void main(String[] args) {
        String[] strs = {"dog", "dot", "cog", "log", "god", "tod"};
        List<List<String>> res = groupAnagrams(strs);
        List<List<String>> res2 = anagrams(strs);
        for (List<String> ls : res) {
            System.out.println(ls.toString());
        }
        for (List<String> ls : res2) {
            System.out.println(ls.toString());
        }
    }

    /**
     * Time Complexity
     *
     * If the average length of verbs is m and array length is n, then the time is O(n*m).
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<>();

        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            int[] arr = new int[26];
            for (int i = 0; i < str.length(); i++) {
                arr[str.charAt(i) - 'a']++;
            }
            String ns = java.util.Arrays.toString(arr);

//            List<String> anagramList = map.getOrDefault(ns, new ArrayList<>());
//            anagramList.addPrereq(str);
//            map.put(ns, anagramList);
            if (map.containsKey(ns)) {
                map.get(ns).add(str);
            } else {
                List<String> al = new ArrayList<>();
                al.add(str);
                map.put(ns, al);
            }
        }

        result.addAll(map.values());

        return result;
    }

    /**
     */
    public static List<List<String>> anagrams(String[] strs) {
        List<List<String>> result = new ArrayList<>();
        if (strs == null || strs.length == 0) return result;

        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < strs.length; i++) { // traverse the array
            /*generate key*/
            char[] word = strs[i].toCharArray();
            Arrays.sort(word);
            String key = new String(word);
            List<String> ls = map.get(key);

            if (ls == null) {
                ls = new ArrayList<>();
                map.put(key, ls);
            }
            ls.add(strs[i]);
        }
        result.addAll(map.values());

        return result;
    }
}
