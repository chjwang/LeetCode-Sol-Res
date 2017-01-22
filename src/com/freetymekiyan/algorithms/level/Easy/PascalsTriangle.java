package com.freetymekiyan.algorithms.level.Easy;

import java.util.ArrayList;
import java.util.List;

/**
 * Given numRows, generate the first numRows of Pascal's triangle.
 * <p>
 * For example, given numRows = 5,
 * Return
 * <p>
 * [
 * [1],
 * [1,1],
 * [1,2,1],
 * [1,3,3,1],
 * [1,4,6,4,1]
 * ]
 * <p>
 * Tags: Array
 */
class PascalsTriangle {
    public static void main(String[] args) {

    }

    /**
     * Definition
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();
        if (numRows <= 0) return triangle;

        List<Integer> firstRow = new ArrayList<>();
        firstRow.add(1);
        triangle.add(firstRow);

        for (int i = 1; i < numRows; i++) {
            List<Integer> lastRow = triangle.get(i - 1);
            List<Integer> row = new ArrayList<>(i + 1);

            for (int j = 0; j < i + 1; j++) {
                if (j == 0 || j == i)
                    row.add(1);
                else
                    row.add(lastRow.get(j - 1) + lastRow.get(j));
            }
            triangle.add(row);
        }
        return triangle;
    }

    public static List<List<Integer>> generate2(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        if(numRows == 0) return res;

        for(int j = 0;j<numRows;j++){
            List<Integer> row = new ArrayList<>(j+1);
            row.add(1);
            for(int i=1;i<j;i++){//除去第一行和第二行才进这个循环
                List<Integer> prevRow = res.get(j-1);//当前行的上一行
                int temp = prevRow.get(i-1)+prevRow.get(i);
                row.add(temp);
            }
            if(j!=0)//除了第一行，末尾接个1
                row.add(1);
            res.add(row);
        }
        return res;
    }
}