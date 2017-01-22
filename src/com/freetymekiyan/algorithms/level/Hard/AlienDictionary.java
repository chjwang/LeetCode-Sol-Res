package com.freetymekiyan.algorithms.level.Hard;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * There is a new alien language which uses the latin alphabet. However, the order among letters are unknown to you. You
 * receive a list of words from the dictionary, where words are sorted lexicographically by the rules of this new
 * language. Derive the order of letters in this language.
 * <p>
 * For example,
 * Given the following words in dictionary,
 * <p>
 * | [
 * |   "wrt",
 * |   "wrf",
 * |   "er",
 * |   "ett",
 * |   "rftt"
 * | ]
 * The correct order is: "wertf".
 * <p>
 * Note:
 * You may assume all letters are in lowercase.
 * If the order is invalid, return an empty string.
 * There may be multiple valid order of letters, return any one of them is fine.
 * <p>
 * Company Tags: Google, Airbnb, Facebook, Twitter, Snapchat, Pocket Gems
 * Tags: Graph, Topological Sort
 * Similar Problems: (M) Course Schedule II
 */
public class AlienDictionary {

    /**
     * Graph. Topological Sort. BFS.
     * Two steps:
     * 1) Build a graph and in-degree from the given words.
     * 2) Do topological sort.
     * <p>
     * Topological sort based on Kahn's algo.
     * It needs a graph and each node's in-degree.
     * Then first add all 0 in-degree nodes in a queue.
     * While the queue is not empty, remove node from the queue and add to result order.
     * Remove the node from graph as well by reducing the in-degree of connected nodes.
     * If those nodes become 0 in-degree as well, add them into queue.
     * When its done, check whether the result length is the same as nodes.
     */
    public String alienOrder(String[] words) {
        if (words == null || words.length == 0) {
            return "";
        }

        // Init in-degree map and adjacency map.
        Map<Character, Integer> indegreeMap = new HashMap<>();
        for (String s : words) { // Init in-degree of all characters given to 0.
            for (char c : s.toCharArray()) {
                indegreeMap.put(c, 0);
            }
        }
        Map<Character, Set<Character>> adjacencyMap = new HashMap<>(); // Use set to avoid duplicates.

        // Build the adjacency map and in-degree map from words array.
        for (int i = 0; i < words.length - 1; i++) {
            String cur = words[i]; // Compare current word and the next word.
            String next = words[i + 1];

            // Special case: when "abcee" is put before "abc". This case there won't be a DAG.
            if (cur.length() > next.length() && cur.startsWith(next)) {
                return "";
            }

            // Find the first different character.
            int len = Math.min(cur.length(), next.length());
            for (int j = 0; j < len; j++) {
                char c1 = cur.charAt(j);
                char c2 = next.charAt(j);
                if (c1 != c2) { // Create an edge from c1 -> c2.
                    Set<Character> set = adjacencyMap.containsKey(c1) ? adjacencyMap.get(c1) : new HashSet<>();
                    if (!set.contains(c2)) {
                        set.add(c2);
                        adjacencyMap.put(c1, set); // Update graph.
                        indegreeMap.put(c2, indegreeMap.get(c2) + 1); // Update indegree. Set makes sure in-degree count is correct.
                    }
                    break; // IMPORTANT! No need to continue.
                }
            }
        }
        // Topological Sort according to Kahn's Algo, BFS
        Queue<Character> q = new ArrayDeque<>();
        // First add all nodes with 0 degree to queue
        for (Map.Entry<Character, Integer> entry : indegreeMap.entrySet()) {
            if (entry.getValue() == 0) {
                q.offer(entry.getKey());
            }
        }
        StringBuilder res = new StringBuilder();
        while (!q.isEmpty()) {
            char c = q.poll();
            res.append(c);
            // Check the rest of the node and update in-degree
            if (adjacencyMap.containsKey(c)) {
                for (char n : adjacencyMap.get(c)) {
                    indegreeMap.put(n, indegreeMap.get(n) - 1);
                    if (indegreeMap.get(n) == 0) {
                        q.offer(n);
                    }
                }
            }
        }
        return res.length() == indegreeMap.size() ? res.toString() : ""; // Check if all nodes are in result.
    }

/////////////////////////

    public String alienOrder2(String[] words) {
        Map<Character, AlienChar> graph = new HashMap<>();

        // 如果建图失败，比如有a->b和b->a这样的边，就返回false
        boolean isBuildSucceed = buildGraph(words, graph);
        if (!isBuildSucceed) {
            return "";
        }

        // 在建好的图中根据拓扑排序遍历
        String order = findOrder(graph);
        return order.length() == graph.size() ? order : "";
    }

    private boolean buildGraph(String[] words, Map<Character, AlienChar> graph) {
        Set<String> visited = new HashSet<>();

        // 初始化图，每个字母都初始化入度为0
        initializeGraph(words, graph);

        for (int wordIdx = 0; wordIdx < words.length - 1; wordIdx++) {
            String before = words[wordIdx];
            String after = words[wordIdx + 1];
            Character prev = null, next = null;
            // 找到相邻两个单词第一个不一样的字母
            for (int letterIdx = 0; letterIdx < before.length() && letterIdx < after.length(); letterIdx++) {
                if (before.charAt(letterIdx) != after.charAt(letterIdx)) {
                    prev = before.charAt(letterIdx);
                    next = after.charAt(letterIdx);
                    break;
                }
            }
            // 如果有环，则建图失败
            if (prev != null && visited.contains(next + "" + prev))
                return false;

            // 如果这条边没有添加过，则在图中加入这条边
            if (prev != null && !visited.contains(prev + "" + next)) {
                addEdge(prev, next, graph);
                visited.add(prev + "" + next);
            }
        }
        return true;
    }

    private void initializeGraph(String[] words, Map<Character, AlienChar> graph) {
        for (String word : words) {
            for (int idx = 0; idx < word.length(); idx++) {
                if (!graph.containsKey(word.charAt(idx))) {
                    graph.put(word.charAt(idx), new AlienChar(word.charAt(idx)));
                }
            }
        }
    }

    private void addEdge(char prev, char next, Map<Character, AlienChar> graph) {
        AlienChar prevAlienChar = graph.get(prev);
        AlienChar nextAlienChar = graph.get(next);
        nextAlienChar.indegree += 1;
        prevAlienChar.adj.add(nextAlienChar);
        graph.put(prev, prevAlienChar);
        graph.put(next, nextAlienChar);
    }

    private String findOrder(Map<Character, AlienChar> graph) {
        StringBuilder order = new StringBuilder();
        Queue<AlienChar> queue = new LinkedList<>();
        for (Character c : graph.keySet()) {
            if (graph.get(c).indegree == 0) {
                queue.offer(graph.get(c));
            }
        }
        while (!queue.isEmpty()) {
            AlienChar curr = queue.poll();
            order.append(curr.val);
            for (AlienChar next : curr.adj) {
                if (--next.indegree == 0) {
                    queue.offer(next);
                }
            }
        }
        return order.toString();
    }


    class AlienChar {
        char val;
        ArrayList<AlienChar> adj;
        int indegree;

        public AlienChar(char c) {
            this.val = c;
            this.adj = new ArrayList<AlienChar>();
            this.indegree = 0;
        }
    }
}
