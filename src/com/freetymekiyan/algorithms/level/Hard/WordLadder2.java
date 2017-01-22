package com.freetymekiyan.algorithms.level.Hard;

import java.util.*;

/**
 * Given two words (start and end), and a dictionary, find all shortest
 * transformation sequence(s) from start to end, such that:
 *
 * Only one letter can be changed at a time
 * Each intermediate word must exist in the dictionary
 * For example,
 *
 * Given:
 * start = "hit"
 * end = "cog"
 * dict = ["hot","dot","dog","lot","log"]
 * Return
 *   [
 *     ["hit","hot","dot","dog","cog"],
 *     ["hit","hot","lot","log","cog"]
 *   ]
 * Note:
 * All words have the same length.
 * All words contain only lowercase alphabetic characters.
 *
 * Tags: Array, Backtracking, BFS, String
 */
class WordLadder2 {
    public static void main(String[] args) {
        String start = "hit";
        String end = "cog";
        String[] arr = {"hot","dot","dog","lot","log"};
        Set<String> dict = new HashSet<>(Arrays.asList(arr));
        System.out.println(new WordLadder2().findLadders(start, end, dict).toString());
    }

    /**
     * BFS then DFS: BFS to find shortest getPath. DFS to find all shortest paths in the same level.
     *
     (1) From the previous Word Ladder I, we know that Breadth First Search is a better way than the DFS.

     (2) The requirement of this question is to output ALL the shortest getPath, which means if we find
     one getPath using the BFS, then all the other shortest paths must also in this level, so the search
     will stop once this level ends.

     (3) We need to output the exact getPath, so we need one way to store the paths.

     (4) For each words in the BFS queue, we still need to use the previous way to generate the valid
     words in the dicts (from 1st to last, change every char from 'a' to 'z' ).

     (5) Duplicates is permitted within a level. e.g.,
     hem -> hex -> tex -> ted
     hem->  tem -> tex -> ted,  are all valid paths.
     Draw this into a tree structure:
     hem
     /       \
     hex    tem
     |        |
     tex     tex
     |        |
     ted     ted
     A solution is to erase all the words in the previous level, instead of erasing words for each word in the level.

     (6) Some experiments that I tried and failed:
     (a). Use a big map to store valid words for each dict(map<string, vector<string> >).  Failed: Memory Limit Exceeds.
     (b). Use the struct as Word Ladder I, addPrereq one "pre" string member to store the getPath. Failed: Memory Limit Exceeds.
     (c). Use a vector to store the getPath. Failed: either time limit exceeds, or failed to track all the paths.
     (d). Use bidirectional BFS. Failed: complex to track all the paths.


     (7) Use a map to store and retrieve the paths. map<string, vector<string> >, stores all the
     previous strings for current string. Retrieval of the getPath will need recursion.

     (8) Because we have the map storing the paths, the standard queue is not needed.
     Because what we do now is searching each level (see the tree above), once we found the getPath,
     still need to finish that level and apply the output.

     So two "queue" can be used, one stores the current level words, one stores the next level words.
     The next level words are generated from the current level.

     During the generation of valid words, getPath can be stored at the same time.
     When the next level words are all generated, if the end string is included, we can output the
     paths, otherwise, we can erase the words in current level, and search the next level.

     This erasing step is helping reduce the dict, and eliminate the case that a cycle exists in the getPath.

     (9) The dict in the last test case contains about 3,000 words.
     *
     *
     */
    public List<List<String>> findLadders(String start, String end, Set<String> dict) {
        List<List<String>> res = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        Map<String, Integer> distance = new HashMap<>();

        bfs(map, distance, start, end, dict);
        dfs(res, new LinkedList<String>(), end, start, distance, map);
        return res;
    }
    
    /**
     * Create a queue, addPrereq start to it and put start in distance map
     * Initialize map with lists
     *
     * @param map word => getPath from start in list (reverse order)
     * @param distance word => distance from start
     * @param start
     * @param end
     * @param dict
     */
    void bfs(Map<String, List<String>> map, Map<String, Integer> distance,
                String start, String end, Set<String> dict) {
        Queue<String> q = new LinkedList<>();
        q.offer(start);
        dict.add(start); // make sure start and end in dictionary
        dict.add(end);
        distance.put(start, 0);

        for (String s : dict)
            map.put(s, new ArrayList<String>());

        while (!q.isEmpty()) {
            String word = q.poll();
            List<String> expansion = expand(word, dict); // generate all words
            for (String next : expansion) {
                map.get(next).add(word);
                if (!distance.containsKey(next)) { // not in distance map yet
                    distance.put(next, distance.get(word) + 1);
                    q.offer(next);
                }
            }
        }
    }
    
    /**
     * Generate a list of words the word
     * Skip if it's the same character
     * If word is in dictionary, addPrereq to expansion list
     */
    List<String> expand(String word, Set<String> dict) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                char[] chs = word.toCharArray();
                if (ch != chs[i]) {
                    chs[i] = ch;
                    String next = new String(chs);
                    if (dict.contains(next)) res.add(next);
                }
            }
        }
        return res;
    }
    
    /**
     * addRecursive current word to first position
     * addRecursive getPath to result if word is start
     */
    void dfs(List<List<String>> res, List<String> path, String word, String start,
             Map<String, Integer> dist, Map<String, List<String>> map) {
        if (word.equals(start)) {
            path.add(0, word);
            res.add(new ArrayList<String>(path));
            path.remove(0); // backtrack
            return; // note to return
        }
        for (String next : map.get(word)) {
            if (dist.containsKey(next) && dist.get(word) == dist.get(next) + 1) { // backward, so word = next + 1
                path.add(0, word); // addPrereq current word
                dfs(res, path, next, start, dist, map); // dfs next word
                path.remove(0);
            }
        }

    }

/////////////////////////////////////////////////////////////////////////

    public ArrayList<ArrayList<String>> findLadders2(String start, String end, HashSet<String> dict) {

        HashMap<String, HashSet<String>> neighbours = new HashMap<String, HashSet<String>>();

        dict.add(start);
        dict.add(end);

        // init adjacent graph
        for(String str : dict){
            calcNeighbours(neighbours, str, dict);
        }

        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

        // BFS search queue
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(new Node(null, start, 1)); //the root has not parent and its level == 1

        // BFS level
        int previousLevel = 0;

        // mark which nodes have been visited, to break infinite loop
        HashMap<String, Integer> visited = new HashMap<String, Integer>();
        while(!queue.isEmpty()){
            Node n = queue.pollFirst();
            if(end.equals(n.str)){
                // fine one getPath, check its length, if longer than previous getPath it's valid
                // otherwise all possible short getPath have been found, should stop
                if(previousLevel == 0 || n.level == previousLevel){
                    previousLevel = n.level;
                    findPath(n, result);
                }else {
                    // all getPath with length *previousLevel* have been found
                    break;
                }
            }else {
                HashSet<String> set = neighbours.get(n.str);

                if(set == null || set.isEmpty()) continue;
                // note: I'm not using simple for(String s: set) here. This is to avoid hashset's
                // current modification exception.
                ArrayList<String> toRemove = new ArrayList<String>();
                for (String s : set) {

                    // if s has been visited before at a smaller level, there is already a shorter
                    // getPath from start to s thus we should ignore s so as to break infinite loop; if
                    // on the same level, we still need to put it into queue.
                    if(visited.containsKey(s)){
                        Integer occurLevel = visited.get(s);
                        if(n.level+1 > occurLevel){
                            neighbours.get(s).remove(n.str);
                            toRemove.add(s);
                            continue;
                        }
                    }
                    visited.put(s,  n.level+1);
                    queue.add(new Node(n, s, n.level + 1));
                    if(neighbours.containsKey(s))
                        neighbours.get(s).remove(n.str);
                }
                for(String s: toRemove){
                    set.remove(s);
                }
            }
        }

        return result;
    }

    public void findPath(Node n, ArrayList<ArrayList<String>> result){
        ArrayList<String> path = new ArrayList<String>();
        Node p = n;
        while(p != null){
            path.add(0, p.str);
            p = p.parent;
        }
        result.add(path);
    }

/*
 * complexity: O(26*str.length*dict.size)=O(L*N)
 */
    void calcNeighbours(HashMap<String, HashSet<String>> neighbours, String str, HashSet<String> dict) {
        int length = str.length();
        char [] chars = str.toCharArray();
        for (int i = 0; i < length; i++) {

            char old = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {

                if (c == old)  continue;
                chars[i] = c;
                String newstr = new String(chars);

                if (dict.contains(newstr)) {
                    HashSet<String> set = neighbours.get(str);
                    if (set != null) {
                        set.add(newstr);
                    } else {
                        HashSet<String> newset = new HashSet<String>();
                        newset.add(newstr);
                        neighbours.put(str, newset);
                    }
                }
            }
            chars[i] = old;
        }
    }


    ////////////////////////////////////////////////

    public List<List<String>> findLadders3(String start, String end,
                                          Set<String> dict) {
        List<List<String>> ladders = new ArrayList<List<String>>();
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        Map<String, Integer> distance = new HashMap<String, Integer>();

        dict.add(start);
        dict.add(end);

        bfs3(map, distance, start, end, dict);

        List<String> path = new ArrayList<>();

        dfs3(ladders, path, end, start, distance, map);

        return ladders;
    }

    void dfs3(List<List<String>> ladders, List<String> path, String current,
             String start, Map<String, Integer> distance,
             Map<String, List<String>> map) {
        path.add(current);
        if (current.equals(start)) {
            Collections.reverse(path);
            ladders.add(new ArrayList<String>(path));
            Collections.reverse(path);
        } else {
            for (String next : map.get(current)) {
                if (distance.containsKey(next) && distance.get(current) == distance.get(next) + 1) {
                    dfs3(ladders, path, next, start, distance, map);
                }
            }
        }
        path.remove(path.size() - 1); // backtrack
    }

    void bfs3(Map<String, List<String>> map, Map<String, Integer> distance,
             String start, String end, Set<String> dict) {
        Queue<String> q = new LinkedList<String>();
        q.offer(start);
        distance.put(start, 0);
        for (String s : dict) {
            map.put(s, new ArrayList<String>());
        }

        while (!q.isEmpty()) {
            String current = q.poll();

            List<String> nextList = expand(current, dict);
            for (String next : nextList) {
                map.get(next).add(current);
                if (!distance.containsKey(next)) {
                    distance.put(next, distance.get(current) + 1);
                    q.offer(next);
                }
            }
        }
    }


    /////////////////////////////

    private HashMap<String, Integer> map;

    private void dfs4(String word, String end, List<String> sequence, List<List<String>> res){
        if(map.get(word) == map.get(end) && !end.equals(word)) return;
        else if(end.equals(word)){
            List<String> list = new LinkedList<>(sequence);
            list.add(word);
            Collections.reverse(list);
            res.add(list);
            return;
        }

        sequence.add(word);
        for(int i=0; i<word.length(); i++){
            char[] wordArray = word.toCharArray();
            for(char ch = 'a'; ch <= 'z'; ch++){
                if(wordArray[i] == ch) continue;
                wordArray[i] = ch;
                String tmp = new String(wordArray);
                if(map.containsKey(tmp) && map.get(tmp) == (map.get(word) - 1))
                    dfs4(tmp, end, sequence, res);
            }
        }
        sequence.remove(word);
    }
    public List<List<String>> findLadders4(String start, String end, Set<String> dict) {
        List<List<String>> res = new ArrayList<List<String>>();
        LinkedList<String> queue = new LinkedList<String>();
        map = new HashMap<String, Integer>();
        queue.add(start);
        map.put(start, 1);

        if(start.equals(end)){
            res.add(queue);
            return res;
        }
        while(!queue.isEmpty()){
            String word = queue.poll();
            for(int i=0; i<word.length(); i++){
                char[] wordArray = word.toCharArray();
                for(char j='a'; j<='z'; j++){
                    if(wordArray[i] == j)
                        continue;
                    wordArray[i] = j;
                    String tmp = new String(wordArray);
                    if(tmp.endsWith(end)){
                        map.put(tmp, map.get(word)+1);
                        i = word.length();
                        queue.clear();
                        break;
                    }
                    if(dict.contains(tmp) && !map.containsKey(tmp)){
                        map.put(tmp, map.get(word) + 1);
                        queue.add(tmp);
                    }
                }
            }
        }
        if(map.containsKey(end))
            dfs4(end, start, new LinkedList<String>(), res);
        return res;
    }

}

class Node {
    public Node parent;  //previous node
    public String str;
    public int level;
    public Node(Node p, String s, int l){
        parent = p;
        str = s;
        level = l;
    }
}
