package com.freetymekiyan.algorithms.level.Easy;

import java.util.HashMap;
import java.util.Map;

/**
 * Related to question Excel Sheet Column Title
 * 
 * Given a column title as appear in an Excel sheet, return its corresponding
 * column number.
 * 
 * For example:
 * 
 *     A -> 1
 *     B -> 2
 *     C -> 3
 *     ...
 *     Z -> 26
 *     AA -> 27
 *     AB -> 28 
 * 
 * Tags: Math
 */
class ExcelSheetColNum {
    public static void main(String[] args) {
        System.out.println(titleToNumber("AAA"));
    }
    
    /**
     * Go through the title
     * Map A ~ Z to 1 ~ 26
     * next result = current res * 26 + number of current letter
     */
    public static int titleToNumber(String s) {
        if (s == null || s.length() == 0) return 0;
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            res = res * 26 + (s.charAt(i) - 'A' + 1);
        }
        return res;
    }

    public int titleToNumber2(String s) {
        if(s==null || s.length() == 0){
            throw new IllegalArgumentException("Input is not valid!");
        }

        Map<Character, Integer> map = new HashMap<>();
        char c = 'A';
        for(int i=1; i<=26; i++){
            map.put(c, i);
            c += 1;
        }

        int result = 0;
        int i = s.length()-1;
        int t = 0;
        while(i >= 0){
            char curr = s.charAt(i);
            result = result + (int) Math.pow(26, t) * map.get(curr);
            t++;
            i--;
        }

        return result;
    }
}