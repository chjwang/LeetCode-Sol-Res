package com.freetymekiyan.algorithms.level.Medium;

import java.util.*;

/**
 * Given an array of integers, every element appears twice except for one. Find
 * that single one.
 * 
 * Note:
 * Your algorithm should have a linear runtime complexity. Could you implement
 * it without using extra memory?
 * 
 * Tags: Hashtable, Bit Manipulation
 */
class SingleNum {
    
    public static void main(String[] args) {
        int[] A = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7};
        System.out.println(singleNum(A));
        System.out.println(singleNumNoSpace(A));
    }
    
    /**
     * Without extra space
     * XOR of two equal numbers is 0 : a^a=0. This is the main idea of the
     * algorithm.
     */
    public static int singleNumNoSpace(int[] A) {
        int res = 0;
        for (int i = 0; i < A.length; i++) res ^= A[i];
        return res;
    }
    
    /**
     * hashtable, store the value and remove when appears second time
     * the only number left is the one
     */
    public static int singleNum(int[] A) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < A.length; i++) {
            if (!map.containsKey(A[i]))
                map.put(A[i], 1);
            else
                map.remove(A[i]);
        }
        int res = 0;
        for (Integer key : map.keySet())
            res = key;
        return res;
    }

    //通用性好，适合两种情况
    public static int singleNumber(int A[], int n) {
        //特殊情况1,2
        if(n<=0) return -1;
        if(n==1) return A[0];

        Arrays.sort(A, 0, n);
        int j = 1;
        for(int i = 0; i < n - 1; i++)
        {
            if(A[i] == A[i+1])
                j++;
            else
            {
                if(j<2) return A[i];//这里修改为j<3那么就可以适用于single number II了。
                j = 1;
            }
        }

        //特殊情况3 最后一个是single number的特殊情况
        return A[n-1];
    }
}
