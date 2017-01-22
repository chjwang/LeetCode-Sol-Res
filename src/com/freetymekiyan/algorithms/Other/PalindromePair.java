package com.freetymekiyan.algorithms.Other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 给一组不重复的字符串，找到连接后能回文的字符串pair。
 比如输入是["gab", "cat", "bag", "alpha", "race", "car"]
 返回[["gab", "bag"], ["bag", "gab"], ["race", "car"]]
 */
public class PalindromePair {
    public static void main(String[] args) {
        String[] input = new String[] {"gab", "cat", "bag", "alpha", "race", "car"};
        List<String[]> output = palindromePair(input);
        StringBuffer sb = new StringBuffer("[");
        for (String[] p : output) {
            sb.append("[" + p[0] + ", " + p[1] + "]" + ", ");
        }
        sb.delete(sb.length()-2, sb.length());
        sb.append("]");
        System.out.println("input: " + Arrays.toString(input));
        System.out.println("output: " + sb.toString());
    }

    public static List<String[]> palindromePair(String[] sa) {
        List<String[]> res = new ArrayList<>();

        if (sa == null || sa.length < 2)
            return res;

        for (int i=0; i<sa.length; i++) {
            for (int j=0; j<sa.length; j++) {
                if (i!=j && isPalindromePair(sa[i], sa[j])) {
                    res.add(new String[]{sa[i], sa[j]});
                }
            }
        }
        return res;
    }

    private static boolean isPalindromePair(String s1, String s2) {
        StringBuilder sb = new StringBuilder(s1);
        sb.append(s2);
        String sb1 = sb.toString();
        sb.reverse();
        String sb2 = sb.toString();
        return sb1.equals(sb2);
    }
}
