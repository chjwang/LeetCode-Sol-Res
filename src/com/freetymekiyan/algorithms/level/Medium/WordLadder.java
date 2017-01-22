package com.freetymekiyan.algorithms.level.Medium;

import java.util.*;
/**
 * Given two words (start and end), and a dictionary, find the length of
 * shortest transformation sequence from start to end, such that:
 * 
 * Only one letter can be changed at a time
 * Each intermediate word must exist in the dictionary
 * For example,
 * 
 * Given:
 * start = "hit"
 * end = "cog"
 * dict = ["hot","dot","dog","lot","log"]
 * 
 * As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 * return its length 5.
 * 
 * Note:
 * Return 0 if there is no such transformation sequence.
 * All words have the same length.
 * All words contain only lowercase alphabetic characters.
 * 
 * Tags: BFS, Hashtable
 */
class WordLadder {
    public static void main(String[] args) {
        String start = "hot";
        String end = "dog";
        Set<String> s = new HashSet<>();
        s.add("hot");
        s.add("dog");
        s.add("dot");
        System.out.println(ladderLength(start, end, s));
    }
    
    /**
     * BFS, level by level
     *
     * 这道题是套用BFS同时也利用BFS能寻找最短路径的特性来解决问题。
     * 把每个单词作为一个node进行BFS。当取得当前字符串时，对他的每一位字符进行从a~z的替换，如果在字典里面，
     * 就入队，并将下层count++，并且为了避免环路，需把在字典里检测到的单词从字典里删除。这样对于当前字符串的
     * 每一位字符安装a~z替换后，在queue中的单词就作为下一层需要遍历的单词了。
     *
     * 正因为BFS能够把一层所有可能性都遍历了，所以就保证了一旦找到一个单词equals（end），那么return的路径肯定是最短的。
     */
    public static int ladderLength(String start, String end, Set<String> dict) {
        if (dict == null || dict.size() == 0) return 0;

        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        dict.remove(start);
        int length = 1;

        while (!queue.isEmpty()) {
            int count = queue.size(); // get current level size
            for (int i = 0; i < count; i++){
                String word = queue.poll(); // get word from queue
                if (word.equals(end)) return length; // end when word is found
                for (int j = 0; j < word.length(); j++) {
                    for (char c = 'a'; c <= 'z'; c++) { // build new word
                        char[] ch = word.toCharArray();
                        if (ch[j] == c) continue;
                        ch[j] = c;
                        String newWord = new String(ch);
                        if (dict.contains(newWord)){ // new word in dict
                            queue.add(newWord); // addPrereq to queue
                            dict.remove(newWord); // remove from dict
                        }
                    }
                }
            }
            length++;
        }
        return 0;
    }
    
    /**
     * BFS
     * use Map to record distance with word as key
     * if found end, return distance
     * else continue build queue
     */
    public static int ladderLengthB(String start, String end, Set<String> dict) {
        if (dict == null || dict.isEmpty())
            return 0;

        dict.add(end);

        Queue<String> q = new LinkedList<>();
        Map<String, Integer> map = new HashMap<>();

        q.add(start);
        map.put(start, 1);

        while (!q.isEmpty()) {
            String word = q.poll();
            if (word.equals(end))
                break;
            for (int i = 0; i < word.length(); i++) {
                for (char j = 'a'; j <= 'z'; j++) {
                    char[] ch = word.toCharArray();
                    if (ch[i] == j) continue;
                    ch[i] = j;
                    String newWord = new String(ch);
                    if (dict.contains(newWord) && !map.containsKey(newWord)) {
                        q.add(newWord);
                        map.put(newWord, map.get(word) + 1);
                    }
                }
            }
        }
        return map.containsKey(end) ? map.get(end) : 0;
    }

    public int ladderLength_two_end_bfs(String start, String end, Set<String> dict) {
        Set<String> beginSet = new HashSet<>(), endSet = new HashSet<>();

        int len = 1;
        HashSet<String> visited = new HashSet<>();

        beginSet.add(start);
        endSet.add(end);
        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            if (beginSet.size() > endSet.size()) {
                // start from the smaller set, swap bigger set to end
                Set<String> set = beginSet;
                beginSet = endSet;
                endSet = set;
            }

            Set<String> temp = new HashSet<>(); // store the next level in BFS
            for (String word : beginSet) {
                char[] chs = word.toCharArray();

                for (int i = 0; i < chs.length; i++) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        char old = chs[i];
                        chs[i] = c;
                        String target = String.valueOf(chs);

                        if (endSet.contains(target)) {
                            return len + 1;
                        }

                        if (!visited.contains(target) && dict.contains(target)) {
                            temp.add(target);
                            visited.add(target);
                        }
                        chs[i] = old;
                    }
                }
            }

            beginSet = temp;
            len++;
        }

        return 0;
    }
}
