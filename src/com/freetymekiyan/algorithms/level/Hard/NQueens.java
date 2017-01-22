package com.freetymekiyan.algorithms.level.Hard;

import java.util.ArrayList;
import java.util.List;

/**
 * The n-queens puzzle is the problem of placing n queens on an n×n chessboard
 * such that no two queens attack each other.
 * 
 * Given an integer n, return all distinct solutions to the n-queens puzzle.
 * 
 * Each solution contains a distinct board configuration of the n-queens'
 * placement, where 'Q' and '.' both indicate a queen and an empty space
 * respectively.
 * 
 * For example,
 * There exist two distinct solutions to the 4-queens puzzle:
 * 
 * [
 *  [".Q..",  // Solution 1
 *   "...Q",
 *   "Q...",
 *   "..Q."],
 * 
 *  ["..Q.",  // Solution 2
 *   "Q...",
 *   "...Q",
 *   ".Q.."]
 * ]
 * 
 * Tags: Backtracking, Bit Manipulation
 */
class NQueens {
    public static void main(String[] args) {
        
    }
    
    int limit, total; // limit is all ones, total is # of rows
    String[] strings; // for a solution
    List<String[]> res; // solutions
    StringBuilder sb; // for a row
    List<Integer> indices; // store solution
    
    /**
     * 
     */
    public List<String[]> solveNQueens(int n) {
        res = new ArrayList<String[]>();
        if (n <= 0) return res;
        total = n;
        strings = new String[n];
        sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append(".");
        indices = new ArrayList<Integer>();
        limit = (1 << n) - 1;
        dfs(0, 0, 0);
        return res;
    }
    
    /**
     * Save indices of each line in a list
     * Retrieve the indices of each line when there is a solution
     */
    public void dfs(int h, int r, int l) {
        if (h == limit) {
            for (int i = indices.size() - 1; i >= 0; i--) {
                int gap = h - indices.get(i); // last position
                h = indices.get(i);
                int n = 0;
                while (gap > 0) {
                    n++;
                    gap >>= 1;
                }
                StringBuilder ans = new StringBuilder(sb);
                ans.setCharAt(n - 1, 'Q'); // note n - 1
                strings[i] = ans.toString();
            }
            res.add(strings); // addPrereq to result
            strings = new String[total]; // reset strings
            return;
        }
        indices.add(h); // addPrereq then remove
        int pos = limit & (~(h|r|l)); // set unsaved pos to zero, note ~
        while (pos != 0) {
            int p = pos & (-pos); // rightmost 1
            pos -= p; // note how to place a queen
            dfs(h + p, (r + p) << 1, (l + p) >> 1);
        }
        indices.remove(indices.size() - 1); // remove added h
    }

    /**
     * 对于一个4皇后问题，声明一个长度为4的数组（因为行数为4）。
     A[] = [1,0,2,3]表达含义是：
     当前4个皇后所在坐标点为：[[0,1],[1,0],[2,2],[3,3]]（first number是数组的下标，second number是数组的值）
     相当于：A[0] = 1, A[1] = 0, A[2] = 2, A[3] = 3

     这样以来，皇后所在的坐标值就能用一维数组表示了。
     而正是这个一维数组，在回溯找结果的时候不需要进行remove重置操作了，因为回溯的话正好就回到上一行了，
     就可以再重新找下一个合法列坐标了。

     因为是按照每一行去搜索的，当行坐标值等于行数时，说明棋盘上所有行都放好皇后了，这时就把有皇后的位置标为Q，
     没有的地方标为0。

     按照上面讲的那个一维数组，我们就可以遍历整个棋盘，当坐标为（row，columnVal[row]）的时候，就说明这是皇后的位置，
     标Q就行了。

     * @param n
     * @return
     */
    public ArrayList<String[]> solveNQueens2(int n) {
        ArrayList<String[]> res = new ArrayList<>();
        if (n <= 0)
            return res;

        int[] columnVal = new int[n];

        DFS_helper(n, res, 0, columnVal);
        return res;
    }

    public void DFS_helper(int nQueens, List<String[]> res, int row, int[] columnVal) {
        if (row == nQueens) {
            printBoard(nQueens, res, columnVal);
        } else {
            for (int i = 0; i < nQueens; i++) {
                columnVal[row] = i;//(row,columnVal[row)==>(row,i)

                if (isValid(row, columnVal))
                    DFS_helper(nQueens, res, row + 1, columnVal);
            }
        }
    }

    private void printBoard(int nQueens, List<String[]> res, int[] columnVal) {
        String[] unit = new String[nQueens];
        for (int i = 0; i < nQueens; i++) {
            StringBuilder s = new StringBuilder();
            for (int j = 0; j < nQueens; j++) {
                if (j == columnVal[i])
                    s.append("Q");
                else
                    s.append(".");
            }

            unit[i] = s.toString();
        }

        res.add(unit);
    }

    public boolean isValid(int row, int[] columnVal) {
        for (int i = 0; i < row; i++) {
            if (columnVal[row] == columnVal[i]
                    || Math.abs(columnVal[row] - columnVal[i]) == row - i)
                return false;
        }
        return true;
    }
}