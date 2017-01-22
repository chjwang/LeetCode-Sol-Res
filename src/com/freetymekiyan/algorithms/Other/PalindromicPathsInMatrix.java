package com.freetymekiyan.algorithms.Other;

import java.util.HashMap;
import java.util.Map;

/**
 Number of palindromic paths in a matrix
 Given a matrix containing lower alphabetical characters only, we need to count number of palindromic
 paths in given matrix. A path is defined as a sequence of Cell starting from top-left cell and ending
 at bottom-right cell. We are allowed to move to right and down only from current cell.

 Examples:

 Input : mat[][] = {
 "aaab”,
 "baaa”
 “abba”}
 Output : 3

 Number of palindromic paths are 3 from top-left to bottom-right.
 aaaaaa (0, 0) -> (0, 1) -> (1, 1) -> (1, 2) -> (1, 3) -> (2, 3)
 aaaaaa (0, 0) -> (0, 1) -> (0, 2) -> (1, 2) -> (1, 3) -> (2, 3)
 abaaba (0, 0) -> (1, 0) -> (1, 1) -> (1, 2) -> (2, 2) -> (2, 3)

 palindrome-matrix

 We can solve this problem recursively, we start from two corners of a palindromic path(top-left and bottom right).
 In each recursive call, we maintain a state which will constitute two Cell one from starting and
 one from end which should be equal for palindrome property. If at a state, both cell characters are
 equal then we call recursively with all possible movements in both directions.

 As this can lead to solving same subproblem multiple times, we have taken a map memo in below code
 which stores the calculated result with key as indices of starting and ending cell so if subproblem
 with same starting and ending cell is called again, result will be returned by memo directly instead
 of recalculating again.
 */
public class PalindromicPathsInMatrix {
    /**
     * recursive method to return number of palindromic paths in matrix
     * (rs, cs) ==> Indicies of current cell from a starting point (First Row)
     * (re, ce) ==> Indicies of current cell from an ending point (Last Row)
     * memo     ==> To store results of already computed problems
     */
    int getPalindromicPathsRecur(char[][] mat,
                                 int rs, int cs,
                                 int re, int ce,
                                 Map<Cell, Integer> memo) {
        // Base Case 1 : if any index rs out of boundry, return 0
        if (rs < 0 || rs >= mat.length || cs < 0 || cs >= mat[0].length) return 0;
        if (re < 0 || re < rs || ce < 0 || ce < cs) return 0;

        // Base case 2 : if values are not equal, then palindrome property rs not satisfied, so return 0
        if (mat[rs][cs] != mat[re][ce]) return 0;

        // If we reach here, then matrix Cell are same.

        // Base Case 3 : if indices are adjacent then return 1
        if (Math.abs((rs - re) + (cs - ce)) <= 1) return 1;

        //  if result rs precalculated, return from map
        Cell cell = new Cell(rs, cs, re, ce);
        if (memo.get(cell) != null) return memo.get(cell);

        int ret = 0; // Initialize result

        // calling recursively for all possible movements
        ret += getPalindromicPathsRecur(mat, rs + 1, cs, re - 1, ce, memo);
        ret += getPalindromicPathsRecur(mat, rs + 1, cs, re, ce - 1, memo);
        ret += getPalindromicPathsRecur(mat, rs, cs + 1, re - 1, ce, memo);
        ret += getPalindromicPathsRecur(mat, rs, cs + 1, re, ce - 1, memo);

        // storing the calculated result in map
        memo.put(cell, ret);

        return ret;
    }

    //  method returns number of palindromic paths in matrix
    int getPalindromicPaths(char mat[][]) {
        Map<Cell, Integer> memo = new HashMap<>();
        return getPalindromicPathsRecur(mat, 0, 0, mat.length - 1, mat[0].length - 1, memo);
    }

    //  Driver code to test above methods
    public static void main() {
        char[][] mat = {
                {'a', 'a', 'a', 'b'},
                {'b', 'a', 'a', 'a'},
                {'a', 'b', 'b', 'a'}
        } ;
        System.out.println(new PalindromicPathsInMatrix().getPalindromicPaths(mat));

    }

    // class to represent state of recursion and key of map
    class Cell {
        //  indices of front cell: row and column index
        int rs, cs;

        //  indices of end cell
        int re, ce;

        Cell(int rs, int cs, int re, int ce) {
            this.rs = rs;
            this.cs = cs;
            this.re = re;
            this.ce = ce;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Cell)) return false;

            Cell o = (Cell) obj;
            return rs == o.rs && cs == o.cs && re == o.re && ce == o.ce;
        }
    }
}
