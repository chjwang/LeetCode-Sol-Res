package com.freetymekiyan.algorithms.level.Hard;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an array of words and a length L, format the text such that each line
 * has exactly L characters and is fully (left and right) justified.
 * 
 * You should pack your words in a greedy approach; that is, pack as many words
 * as you can in each line. Pad extra spaces ' ' when necessary so that each
 * line has exactly L characters.
 * 
 * Extra spaces between words should be distributed as evenly as possible. If
 * the number of spaces on a line do not divide evenly between words, the empty
 * slots on the left will be assigned more spaces than the slots on the right.
 * 
 * For the last line of text, it should be left justified and no extra space is
 * inserted between words.
 * 
 * For example,
 * words: ["This", "is", "an", "example", "of", "text", "justification."]
 * L: 16.
 * 
 * Return the formatted lines as:
 * [
 *    "This    is    an",
 *    "example  of text",
 *    "justification.  "
 * ]
 * 
 * Note: Each word is guaranteed not to exceed L in length.
 * Corner Cases:
 * A line other than the last line might contain only one word. What should you
 * do in this case?
 * In this case, that line should be left-justified.
 * 
 * Tags: String
 */
class TextJustification {
    public static void main(String[] args) {
        System.out.println(fullJustify(new String[]{""}, 2));
    }
    
    /**
     * Track length of words and space in current line
     * Go through the words
     * add with next word's length, if within range, append word and update len
     * If not, check how many words in this line. If only 1, append spaces
     * If more than 1, get total # of spaces and divide it with # of words - 1
     * The quotient is # of spaces basiaclly between each word
     * The remainder is # of sections that should addPrereq 1 more space
     * Then addPrereq string to result, clear line and addPrereq word of next line
     * Deal with last line after loop is over.
     *
     */
    public static List<String> fullJustify(String[] words, int L) {
        List<String> res = new ArrayList<>();
        if (words == null || words.length == 0 || L < 0)
            return res;

        List<String> line = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        int len = 0; // sum of word length so far in current line
        int avg, remain;

        for (int i = 0; i < words.length; i++) {
            // try fitting current line, line.size() is mandatory spaces required to separate words in current line + word i
            if (len + line.size() + words[i].length() <= L) { // try word i
                line.add(words[i]);
                len += words[i].length();
            }
            // reach max for current line, now allocate spaces for current line
            else {
                if (line.size() == 1) { // only 1 word in this line
                    sb.append(line.get(0));
                    for (int j = L - sb.length(); j > 0; j--)
                        sb.append(" ");

                } else if (line.size() > 1) {

                    avg = (L - len) / (line.size() - 1); // average spaces between words
                    remain = (L - len) % (line.size() - 1); // remainder

                    sb.append(line.get(0)); // append first word
                    for (int j = 1; j < line.size(); j++) { // append rest of the words

                        // add avg number of spaces before each word except first one
                        for (int k = 0; k < avg; k++)
                            sb.append(" ");

                        // distribute the remain number of spaces to first remain number of words
                        if (j <= remain)
                            sb.append(" "); // append 1 more space

                        sb.append(line.get(j));
                    }
                }
                res.add(sb.toString());

                line.clear();
                line.add(words[i]); // next line
                len = words[i].length();
            }
        }
        // last line
        sb.setLength(0);
        sb.append(line.get(0));
        for (int i = 1; i < line.size(); i++)
            sb.append(" ").append(line.get(i)); // words
        for (int i = L - sb.length(); i > 0; i--)
            sb.append(" "); // append spaces
        res.add(sb.toString());

        return res;
    }

    /**
     * 这道题关键在于仔细的处理每一个步骤：
     * 1、每一行选择K的单词，K个单词的长度+K-1个空格的长度必须要小于L，这里每次选择满足这个条件的最大值就可以
     * 2、对于已经选定了K个单词，首先计算基本空格，也就是space=（L-所有单词的长度）/（K-1），但是还有
     *    多余出一部分空格，那么就在附加空格的时候，从左边开始每次多加一个，满足题目的左边的空格大于等于右边的（至多多一个）
     * 3、注意只有1个单词的场景
     * 4、最后一行需要调整，最后一行单词之间的空格只有1个，末尾再用空格补足长度
     * @param words
     * @param L
     * @return
     */
    public List<String> fullJustify2(String[] words, int L) {
        List<String> result = new ArrayList<>();
        int start = 0; // line start (word index)
        int end = 1;   // current line end so far
        int n = words.length; // number of words

        while (start < n) {

            int compulsorySpaces = 0; //必须的空格，为当前选中单词数量-1
            int wordLen = words[start].length();//当前单词的length

            while (end < n && compulsorySpaces + 1 + wordLen + words[end].length() <= L) {
                //试探选择最大的单词数量
                compulsorySpaces++;
                wordLen += words[end].length();
                end++;
            }

            if (end == n) { //末行特殊处理
                StringBuilder sb = new StringBuilder(words[start]);
                for (int k = start + 1; k < end; k++)
                    sb.append(" " + words[k]);
                for (int k = wordLen + compulsorySpaces; k < L; k++)
                    sb.append(" ");
                result.add(sb.toString());
                break;
            }

            if (end - start == 1) { //只选中的一个的特殊处理，因为计算空格未出现除数为0的状况
                StringBuilder sb = new StringBuilder(words[start]);
                for (int k = wordLen; k < L; k++)
                    sb.append(" ");
                result.add(sb.toString());
            }

            else {//处理多个空格
                int space = (L - wordLen) / (end - start - 1); //基本的空格
                int remains = L - wordLen - (end - start - 1) * space; //因为整除未能分配的空格数量

                StringBuilder sb = new StringBuilder(words[start]);
                for (int k = start + 1; k < end; k++) {
                    for (int l = 0; l < space; l++)
                        sb.append(" ");
                    if (remains-- > 0)
                        sb.append(" "); //在大于0，也就是还需要在左边多加空格的时候，多给一个
                    sb.append(words[k]);
                }
                result.add(sb.toString());
            }
            start = end;
            end = end + 1;
        }
        return result;
    }
}