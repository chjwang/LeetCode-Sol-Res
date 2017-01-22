package com.freetymekiyan.algorithms.level.Hard;

/**
 * Follow up for N-Queens problem.
 * 
 * Now, instead outputting board configurations, return the total number of
 * distinct solutions.
 * 
 * Tags: Backtracking
 */
class NQueens2 {
    public static void main(String[] args) {
        System.out.println(totalNQueens(2));
        System.out.println(totalNQueens(3));
        System.out.println(totalNQueens(4));
        System.out.println(totalNQueens(5));
    }
    
    /**
     * backtrace program using bit-wise operation to speed up calculation.
     * 'limit'is all '1's.
     * 'h'    is the bits all the queens vertically projected on a row. If
     *        h==limit, then it's done, answer++.
     * 'r'    is the bits all the queens anti-diagonally projected on a row.
     * 'l'    is the bits all the queens diagonally projected on a row.
     * h|r|l  is all the occupied bits. Then pos = limit & (~(h|r|l)) is all
     *        the free positions.
     * p = pos & (-pos)
     *        gives the right most '1'. pos -= p means we will place
     *        a queen on this bit represented by p.
     * 'h+p'  means one more queue vertically projected on next row.
     * '(r+p) << 1'
     *        means one more queue anti-diagonally projected on next row.
     *        Because we are moving to next row and the projection is skew from
     *        right to left, we have to shift left one position after moved to
     *        next row.
     * '(l+p) >> 1'
     *        means one more queue diagonally projected on next row. Because we
     *        are moving to next row and the projection is skew from left to
     *        right, we have to shift right one position after moved to next
     *        row.
     * https://oj.leetcode.com/discuss/743/whats-your-solution
     */
    public static int totalNQueens(int n) {
        ans = 0;
        limit = (1 << n) - 1; // note that parentheses can't be ignored
        dfs(0, 0, 0);
        return ans;
    }
    
    static int ans, limit;
    
    /**
     * Backtracking
     */
    public static void dfs(int h, int r, int l) {
        if (h == limit) { 
            ans++;
            return;
        }
        int pos = limit & (~(h|r|l));
        while (pos != 0) { // has position
            int p = pos & (-pos); // right most 1
            pos -= p; // place a queen, 1 -> 0 on that position
            dfs(h + p, (r + p) << 1, (l + p) >> 1);
        }
    }

    /**
     * 这道题跟NQueens的解法完全一样，只不过要求的返回值不同了。所以要记录的result稍微改一下就好了。

     因为涉及到递归，result传进去引用类型（List，数组之类的）才能在层层递归中得以保存，所以这里使用一个长度为1的数组帮助计数。

     当然，也可以使用一个全局变量来帮助计数。
     * @param n
     * @return
     */
    public int totalNQueens2(int n) {
        int[] res = {0};
        if (n <= 0)
            return res[0];

        int [] columnVal = new int[n];

        DFS_helper(n, res, 0, columnVal);
        return res[0];
    }

    public void DFS_helper(int nQueens, int[] res, int row, int[] columnVal){
        if (row == nQueens){
            res[0] += 1;
        } else {
            for (int i = 0; i < nQueens; i++) {
                columnVal[row] = i;//(row,columnVal[row)==>(row,i)

                if(isValid(row,columnVal))
                    DFS_helper(nQueens, res, row+1, columnVal);
            }
        }
    }

    public boolean isValid(int row, int [] columnVal) {
        for (int i = 0; i < row; i++) {
            if (columnVal[row] == columnVal[i]
                    || Math.abs(columnVal[row] - columnVal[i]) == row - i)
                return false;
        }
        return true;
    }

    // 使用全局变量来记录结果
    int res;
    public int totalNQueens3(int n) {
        res = 0;
        if (n <= 0)
            return res;

        int [] columnVal = new int[n];

        DFS_helper(n, 0, columnVal);
        return res;
    }

    public void DFS_helper(int nQueens, int row, int[] columnVal){
        if (row == nQueens){
            res += 1;
        } else {
            for (int i = 0; i < nQueens; i++) {
                columnVal[row] = i; //(row,columnVal[row)==>(row,i)

                if (isValid(row, columnVal))
                    DFS_helper(nQueens, row+1, columnVal);
            }
        }
    }
}
