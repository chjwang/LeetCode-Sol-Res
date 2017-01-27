package com.freetymekiyan.algorithms.Other;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**

 boggle-solver

 A trie + dynamic programming solution to the Boggle game

 The dictionary is stored in a trie (prefix tree). A DFS like traversal is done on the dictionary to
 find the words. For each word, we use a dynamic programming technique to figure out whether the board
 contains the word or not.

 The runtime of the algorithm is O(Dictionary size * Board dimension * Board dimension * Max word length
 in dictionary).

 On my ~3GHz iMac, it takes 1.8 seconds to run on a dictionary of size ~175K words.

 I ran the same dynamic programming algo with C++ code (Described here:
 http://exceptional-code.blogspot.com/2012/02/solving-boggle-game-recursion-prefix.html) that took much
 shorter time than this. The C++ approach though used a list instead of a trie to store the dictionary words.


 First solution - Recursion + Binary Search:

 In this approach, we recurse (using backtracking) through the board and generate all possible words.
 For each word that are three or more letters long we check to see if it's in the dictionary. If it is,
 we have a match!

 Here is the algorithm steps:

 1. Read in the dictionary in to a container in memory.
 2. Sort the dictionary.
 3. Using backtracking, search the board for words.
 4. If a word is found and it contains 3 or more letters, do a binary search on the dictionary to see if the word is there.
 5. If searching was successful in the previous step, print the letter out.
 6. Continue step 3-5 as long as there are more words to construct on the board.

 Complexity of this approach:

 In this solution, we do a good job on the dictionary search. Using binary search, we are quickly finding out
 whether a word is in dictionary or not. But the real bottleneck is in searching the board for words.
 For an N x N board the search space is O((N*N)!). I am not exactly sure about this number, but you
 can find some discussions of it here: http://www.danvk.org/wp/2007-08-02/how-many-boggle-boards-are-there/.
 (N*N)! is a HUGE number even for N = 5. So this approach is is impractical and out of question for any useful implementation.


 Second Solution - Pruned Recursion + Prefix Tree (also known as a Trie):

 From the previous approach, our major concern was the enormous search space on the board.
 Fortunately, using a a prefix tree or trie data structure we could significantly cut down on this search space.
 The reasoning behind this improvement is that, if a word "abc" does not occur as a prefix to any word in the dictionary
 there is no reason to keep searching for words after we encounter "abc" on the board. This actually cut down the run time a lot.

 Here is the algorithm steps:

 1. Read a word from the dictionary file.
 2. Insert it into a prefix tree data structure in memory.
 3. Repeat steps 1-2 until all words in the dictionary have been inserted into the prefix tree.
 4. Using backtracking, search the board for words.
 5. If a word is found and it contains 3 or more letters, search the prefix tree for the word.
 6. If searching was *not* successful in the previous step, return from this branch of the backtracking stage.
    (There is no point to continue searching in this branch, nothing in the dictionary as the prefix tree says).
 7. If searching was successful in step 5, continue searching by constructing more words along this
    branch of backtracking and stop when the leaf node has been reached in the prefix tree.
    (at that point there is nothing more to search).
 8. Repeat steps 4-7 as long as there are more words to search in the backtracking.

 Complexity of this approach:

 This approach significantly improves on the first one. Building a prefix tree our of the dictionary words is O(W * L),
 where W is the number of words in the dictionary and L is the maximum length of a word in the dictionary.
 Searching the board will be of the same order as the dictionary since we are not really searching words
 that are not in the dictionary. But in reality it will be more work than that as we still need to backtrack
 along the board to construct new words until we can consult the dictionary prefix tree to know whether it exists or not.

 Third and Final Solution - No search space + Dynamic Programming:

 The 2nd approach mentioned above was good enough until the board size was 5.
 Unfortunately with a board size of 6, that too was taking forever to complete!



 It got me into thinking - "Dang, this search space is still too big to search! Can I just get rid of it entirely?"
 And then this idea popped into my mind: instead of random constructing word after word in this infinite ocean of words
 why don't I take a word from the dictionary and somehow magically check whether that's available on the board or not?

 It turns out, we can use a nifty dynamic programming technique to quickly check whether a word
 (from the dictionary in this case) can be constructed from the board or not!

 Here is core point of the dynamic programming idea:


 For a word of length k to be found (end location) at the [i, j]-th location of the board, the k-1'th
 letter of that word must be located in one of the adjacent cells of [i, j].



 The base case is k = 1.

 A letter of length 1 will be found (end location) in the [i, j]-th cell of the board of the only letter
 in the word matches the letter in the [i, j]-th location of the board.

 Once our dynamic programming table is populated with the base case, we can build on top of that for
 any word of length k, k > 1.

 Here is a sample code for this:

 for (k = 2; k < MAX_WORD_LENGTH; ++k)
   for (i = 0; i < N; ++i)
      for (j = 0; j < N; ++j)
         if (board[i][j] == word[k])
         {
            for all the "adjacent" cells of board[i, j]
              if we table[k-1][adjacent_i][adjacent_j] is true
              then table[k][i][j] = true;
         }


 Run Time Complexity:

 The run time complexity of this approach is obvious is pretty obvious. It's O (W * N * N * MAX_WORD_LENGTH).
 Here N is dimension of the board which is usually between 4 to 6. So essentially the algorithm runs
 in the order of the size of the dictionary!


 */
public class Boggler {

  private static final int N = 4;
  private static char[][] board = new char[N][N];
  private static DictNode root;

  private static class DictNode {

    public final char letter;
    public DictNode[] nextNodes = new DictNode[26];
    public boolean wordEnd;

    public DictNode(final char letter) {
      this.letter = letter;
    }

    public void insert(final String word) {
      DictNode node = root;
      char[] letters = word.toCharArray();
      for (int i = 0; i < letters.length; i++) {
        if (node.nextNodes[letters[i] - 'a'] == null) {
          node.nextNodes[letters[i] - 'a'] = new DictNode(letters[i]);

          if (i == letters.length - 1) {
            node.nextNodes[letters[i] - 'a'].wordEnd = true;
          }
        }

        node = node.nextNodes[letters[i] - 'a'];
      }
    }

    public boolean contains(final String word) {
      DictNode node = root;
      char[] letters = word.toCharArray();
      int i = 0;
      while (i < letters.length && node.nextNodes[letters[i] - 'a'] != null) {
        node = node.nextNodes[letters[i] - 'a'];
        i++;
      }

      return (i == letters.length) && node.wordEnd;

    }
  }

  public static boolean isInBoard(final String word) {
    int[] dx = {1, 1, 0, -1, -1, -1, 0, 1};
    int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};

    // dp[k][i][j]: whether letter[k] matches board[i][j]. Can be further optimized since dp[k][i][j]
    // only relies on dp[k-1][i+dx][j+dy]. Can use two variables and swap them at each iteration.
    boolean[][][] dp = new boolean[50][N][N];

    char[] letters = word.toCharArray();

    for (int k = 0; k < letters.length; k++) {
      for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
          if (k == 0) {
            dp[k][i][j] = true;
          } else {
            for (int l = 0; l < 8; l++) {
              int x = i + dx[l];
              int y = j + dy[l];

              if ((x >= 0) && (x < N) && (y >= 0) && (y < N)
                  && (dp[k - 1][x][y]) && (board[i][j] == letters[k])) {
                dp[k][i][j] = true;
                if (k == letters.length - 1) {
                  return true;
                }
              }
            }
          }
        }
      }
    }

    return false;
  }

  public static void boggleTrieDynamic(DictNode node, char[] currentBranch, int currentHeight) {
    if (node == null) {
      return;
    }

    if (node.wordEnd && currentHeight > 3) {
      String word = new String(currentBranch, 0, currentHeight - 1);
      boolean inBoard = isInBoard(word);
      if (inBoard) {
        System.out.println(word);
      }
    }

    for (int i = 0; i < node.nextNodes.length; i++) {
      if (node.nextNodes[i] != null) {
        currentBranch[currentHeight] = (char) (i + 'a');
        boggleTrieDynamic(node.nextNodes[i], currentBranch, currentHeight + 1);
      }
    }
  }

  public static void readBoard(final String boardFile) throws IOException {
    byte[] bytes = Files.readAllBytes(new File(boardFile).toPath());
    String content = new String(bytes, StandardCharsets.UTF_8);
    content = content.toLowerCase();

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        board[i][j] = content.toCharArray()[i * N + j];
      }
    }
  }

  public static void readDict() throws IOException {
    String dictFile = "./data/dictionary.txt";
    List<String> words = Files.readAllLines(new File(dictFile).toPath(), StandardCharsets.UTF_8);

    root = new DictNode('\0');
    for (String word : words) {
      root.insert(word);
    }
  }

  public static void main(String[] args) throws IOException {
    readDict();

    readBoard("./data/board.txt");

    long start = System.currentTimeMillis();

    boggleTrieDynamic(root, new char[50], 0);

    long end = System.currentTimeMillis();

    System.out.println("Total time spent = " + (end - start) + " milli seconds.");

    System.out.println("Done...");
  }
}