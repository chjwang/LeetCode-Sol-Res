package com.freetymekiyan.algorithms.Other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given a list of words and two words word1 and word2, return the shortest distance between these
 * two words in the list.

 For example, Assume that words = ["practice", "makes", "perfect", "coding", "makes"].

 Given word1 = “coding”, word2 = “practice”, return 3. Given word1 = "makes", word2 = "coding", return 1.

 Note: You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.

 */
public class ShortestWordDistance {
    // 时间 O(N) 空间 O(1)
    public int shortestDistance(String[] words, String word1, String word2) {
        int index1 = -1, index2 = -1, distance = Integer.MAX_VALUE;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word1)) {
                index1 = i;
                if (index2 != -1) distance = Math.min(distance, index1 - index2);
            }
            if (words[i].equals(word2)) {
                index2 = i;
                if (index1 != -1) distance = Math.min(distance, index2 - index1);
            }
        }
        return distance;
    }

    /**
     * This is a follow up of Shortest Word Distance. The only difference is now you are given the
     * list of words and your method will be called repeatedly many times with different parameters.
     * How would you optimize it?

     Design a class which receives a list of words in the constructor, and implements a method that
     takes two words word1 and word2 and return the shortest distance between these two words in the
     list.

     For example, Assume that words = ["practice", "makes", "perfect", "coding", "makes"].

     Given word1 = “coding”, word2 = “practice”, return 3. Given word1 = "makes", word2 = "coding", return 1.

     Note: You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.

     时间 O(N) 空间 O(N)
     */
    Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();

    // constructor
    public ShortestWordDistance(String[] words) {
        // 统计每个单词出现的下标存入哈希表中
        for (int i = 0; i < words.length; i++) {
            List<Integer> cnt = map.getOrDefault(words[i], new ArrayList<Integer>());
            cnt.add(i);
            map.put(words[i], cnt);
        }
    }

    public int shortest(String word1, String word2) {
        List<Integer> indexList1 = map.get(word1);
        List<Integer> indexList2 = map.get(word2);
        int distance = Integer.MAX_VALUE;

//        int i = 0, j = 0;
//        // 每次比较两个下标列表最小的下标，然后把跳过较小的那个
//        while (i < indexList1.size() && j < indexList2.size()) {
//            distance = Math.min(Math.abs(indexList1.get(i) - indexList2.get(j)), distance);
//            if (indexList1.get(i) < indexList2.get(j))
//                i++;
//            else
//                j++;
//        }

        // better performance than accessing List by index: list.get(i)
        for (int a : indexList1) {
            for (int b : indexList2) {
                distance = Math.min(Math.abs(b - a), distance);
            }
        }

        return distance;
    }

    /**
     * This is a follow up of Shortest Word Distance. The only difference is now word1 could be the same as word2.

     Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.

     word1 and word2 may be the same and they represent two individual words in the list.

     For example, Assume that words = ["practice", "makes", "perfect", "coding", "makes"].

     Given word1 = “makes”, word2 = “coding”, return 1. Given word1 = "makes", word2 = "makes", return 3.

     Note: You may assume word1 and word2 are both in the list.

     时间 O(N) 空间 O(N)
     */
    public int shortestWordDistance3A(String[] words, String word1, String word2) {
        int index1 = -1, index2 = -1, distance = Integer.MAX_VALUE;

        int turn = 0;
        int inc = (word1.equals(word2) ? 1 : 0);

        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word1) && turn % 2 == 0) {
                index1 = i;
                if (index2 != -1) distance = Math.min(distance, index1 - index2);
                turn += inc;
            } else if (words[i].equals(word2)) {
                index2 = i;
                if (index1 != -1) distance = Math.min(distance, index2 - index1);
                turn += inc;
            }
        }
        return distance;
    }

    public int shortestWordDistance3(String[] words, String word1, String word2) {
        Map<String, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            String s = words[i];
            List<Integer> list = map.getOrDefault(s, new ArrayList<>());

            // add current word's index to list
            list.add(i);

            map.put(s, list);
        }

        List<Integer> l1 = map.get(word1);
        List<Integer> l2 = map.get(word2);
        int min = Integer.MAX_VALUE;
        for (int a : l1) {
            for (int b : l2) {
                int dist = Math.abs(b - a);
                if (dist != 0) { // this is the only difference between II and III
                    min = Math.min(dist, min);
                }
            }
        }
        return min;
    }
}
