package com.freetymekiyan.algorithms.Other;

/**
 Given a string S, you are allowed to convert it to a palindrome by adding characters in front of it.
 Find and return the shortest palindrome you can find by performing this transformation.

 For example, given "aacecaaa", return "aaacecaaa"; given "abcd", return "dcbabcd".
 */
public class ShortestPalindrome {

    public static void main(String[] args) {
        ShortestPalindrome p = new ShortestPalindrome();
        String s = "abcd";
//        System.out.println(p.shortestPalindrome(s));
//        System.out.println(p.shortestPalindrome2(s));
        s = "aacecaaa";
        System.out.println(p.shortestPalindrome(s));
        System.out.println(p.shortestPalindrome2(s));
    }

    /*
    用Java暴力是可以过的，思路也很简单：补充完成之后的回文串中心必定在原字符串中，所以原字符串以第一个字符为起点必然
    存在至少一个回文串（长度可以为1），那么任务就变为找到原字符串中以第一个字符为起点最长的回文串，找到之后剩下的工作
    就是把剩余部分的翻转补充到原字符串头部即可。

    这样代码逻辑就很简单，就是从原字符串的头部开始截取子串，长度递减，直到获取到第一个是回文串的子串，此时就找到了
    需要截断的部分，从该位置开始到原字符串末尾就是需要截取并翻转拼接的部分。算法复杂度是O(n^2)。
    */
    public String shortestPalindromeBruteForce(String s) {
        if (s == null || s.length() == 0 || s.length() == 1) return s;
        int len = s.length();
        int tail = len;
        StringBuilder builder = new StringBuilder();

        while (tail > 1) {
            if (isPalindrome(s.substring(0, tail))) {
                builder = builder.append(s.substring(tail, len)).reverse();
                break;
            }
            tail--;
        }
        if (builder.length() == 0)
            builder = builder.append(s.substring(tail, len)).reverse();

        return builder.append(s).toString();
    }

    private boolean isPalindrome(String str) {
        int len = str.length();
        for (int i = 0; i < len / 2; i++) {
            if (str.charAt(i) != str.charAt(len - i - 1))
                return false;
        }
        return true;
    }

    /**
     * KMP
     *
     *  The solution has following steps:

     Create a new string p which is the original string appended with the revers of the input string
     p = s + "#" + reverse(s).

     Find the longest prefix which is also the suffix.

     Add in front the remaining substring of s in reverse order.

     We can see, the step 2 is similar to the KMP algorithm pre-computing the failure function table,
     which is O(n) time complexity, so we can solve this problem in O(n) totally.
     *
     * 其实思路也很简单：

     求字符串s的翻转s_rev
     将两个字符串进行拼接：{s}#{s_rev}
     找出新字符串中最长公共前缀后缀长度comLen
     s_rev.substring(0, s.length() - comLen)就是在原字符串头部插入的子串部分

     举个例子：

     对于字符串s：babcd，先求rev_s：dcbaba，拼接之后：babcd#dcbaba。上文已经解释过，s的前缀必然是一个回文串（长度可能为1），
     任务就是求这个回文串的最长长度，因此拼接之后的{s}#{s_rev}必然有公共前缀后缀，任务就是求这个公共前缀后缀的最长长度，
     那么这个时候就需要祭出KMP算法了。有了解的同学，估计一看就看出这个就是求KMP里的next数组。
     由于之前学KMP的时候也只学了个一知半解，所以这次又重新学习了下从头到尾彻底理解KMP（2014年8月22日版），这下对KMP又有更好的理解了。

     详细的KMP算法上面提到的文章里讲的非常详细，就不从头说了。这里讲一讲我之前一直困惑现在理解了的点。

     对于KMP算法，核心的地方就是求next数组，而求next数组中比较难理解的地方就是当当前位置的字符和目标字符不匹配的时候。
     对于字符串s，已经有p[0]到p[i-1]，且p[i-1]=j，求p[i]
     （p即next数组，其中p[k]表示从0到k位置为止公共前缀后缀的长度，
     例如：abacaba，公共前缀后缀长度是3，当p[k]=m则表示s.substring(0,m)和s.substring(k-m+1,k+1)是相等的）：

     若s[i]=s[j]，也就是当前字符延续了之前的公共前缀后缀，那么p[i]=p[i-1]+1即可

     若s[i]!=s[j]，即s.substring(0,j)和s.substring(i-j+1,i+1)是不匹配的，但是仍然可能存在
     s.substring(0,x)和s.substring(i-x+1,i+1)，这一点就是我以前最不能理解的地方，这次结题的经历加深了我这部分的理解。

     到目前位置，期望i位置的最长公共前缀后缀为j+1的期望已经失败，那我是否可以期望下缩短长度之后能有匹配的公共前缀后缀呢？
     答案是肯定的，因为对于位置i-1来说，其实是可能存在多个公共前缀后缀的，只是p[i-1]只记录其中最长的，那么次长的是多少呢，
     答案就在p[j-1]里。对于位置i-1来说，已知0到j-1的子串和i-j+1到i-1子串是相等的，而对于位置j-1来说，
     从0到p[j-1]-1的子串和从j-p[j-1]到j-1的子串是相同的，更进一步和i-p[j-1]到i-1的子串也是相同的，
     那如果现在比较一下i和p[j-1]是否相等同样可以求出最长公共前缀后缀的值
     （因为p中记录是到每个位置为止的最长公共前缀后缀，所以这样每次递推下去每次得到都是当前可能的最长公共前缀后缀）。

     梳理一下，就是对于位置i-1而言，公共前缀后缀的长度依次为：p[i-1],p[p[i-1]-1],p[p[p[i-1]-1]-1],……。
     在此基础上，对于位置i而言，只要比对某几个特定的位置，看s[i]是否能符合条件（即是否和当前公共前缀后缀后的第一个
     字符相等）就能求得p[i]的值。当然，如果比对某个位置的时候p[x]已经为0，那么就可以马上结束比较跳出循环，然后只要
     和首字母比对下就行了（因为这种情况说明可能的公共前缀后缀都已经被比对完了，s[i]依然不符合条件，那么只能从头开始了）。

     * @param s
     * @return
     */
    public String shortestPalindrome(String s) {
        StringBuilder builder = new StringBuilder(s);
        return builder.reverse().substring(0, s.length() - getCommonLength(s)) + s;
    }

    private int getCommonLength(String str) {
        StringBuilder builder = new StringBuilder(str);

        String rev = new StringBuilder(str).reverse().toString();
        builder.append("#").append(rev);
        int[] p = new int[builder.length()];

        for (int i = 1; i < p.length; i++) {
            int j = p[i - 1];
            while (j > 0 && builder.charAt(i) != builder.charAt(j))
                j = p[j - 1];

            p[i] = j == 0 ? (builder.charAt(i) == builder.charAt(0) ? 1 : 0) : j + 1;
        }
        return p[p.length - 1];
    }


    /**
     * 使用两个指针从前后对向遍历，就跟我们判断String是否是Palindrome一样，假如s.charAt(i) == s.charAt(j)，则j++。
     * 走完之后的结果j所在假如是s.length() - 1，则整个String为Palindrome，返回s，
     * 否则，j所在的位置及其以后的部分肯定不是Palindrome，这是我们要把这部分翻转并且加到结果的前面。
     * 至于 substring(0, j)这部分，我们仍需要使用递归的方法继续判断
     *
     * @param s
     * @return
     */
    public String shortestPalindrome1(String s) {
        int j = 0;
        for (int i = s.length()-1; i >= 0; i--) {//找到第一个使他不回文的位置
            if (s.charAt(i) == s.charAt(j)) j++;
        }

        if (j == s.length()) {  //本身是回文
            return s;
        }

        String suffix = s.substring(j); // 后缀不能够匹配的字符串
        String prefix = new StringBuilder(suffix).reverse().toString(); // 前面补充prefix让他和suffix回文匹配

        String mid = shortestPalindrome1(s.substring(0, j)); //递归调用找 [0,j]要最少可以补充多少个字符让他回文

        String ans = prefix + mid + suffix;

        return ans;
    }

    /**
     * We can solve this problem by using one of the methods which is used to solve the longest
     * palindrome substring problem.

     Specifically, we can start from the center and scan two sides. If reach the left boundary, then
     the shortest palindrome is identified.

     * @param s
     * @return
     */
    public String shortestPalindrome2(String s) {
        if (s == null || s.length() <= 1)
            return s;

        String result = null;

        int len = s.length();
        int mid = len / 2;

        for (int i = mid; i >= 1; i--) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                if ((result = scanFromCenter(s, i - 1, i)) != null)
                    return result;
            } else {
                if ((result = scanFromCenter(s, i - 1, i - 1)) != null)
                    return result;
            }
        }

        return result;
    }

    private String scanFromCenter(String s, int l, int r) {
        int i = 1;

        //scan from center to both sides
        for (; l - i >= 0; i++) {
            if (s.charAt(l - i) != s.charAt(r + i))
                break;
        }

        //if not end at the beginning of s, return null
        if (l - i >= 0)
            return null;

        StringBuilder sb = new StringBuilder(s.substring(r + i));
        sb.reverse();

        return sb.append(s).toString();
    }

    /**
     * 下面这种方法的写法比较简洁，虽然不是明显的KMP算法，但是也有其的影子在里面，
     * 首先我们还是先将其的翻转字符串搞出来，
     * 然后比较原字符串s的前缀后翻转字符串t的对应位置的后缀是否相等，起始位置是比较s和t是否相等，
     * 如果相等，说明s本身就是回文串，不用添加任何字符，直接返回即可；
     * 如果不相等，s去掉最后一位，t去掉第一位，继续比较，
     * 以此类推直至有相等，或者循环结束，这样我们就能将两个字符串在正确的位置拼接起来了。
     *
     * 很有意思的是，这种方法Java写法却会TLE，无法通过OJ。

     * @param s
     * @return
     */
    public String shortestPalindrome3(String s) {
        String r = new StringBuilder(s).reverse().toString();
        String t = s + "#" + r;
        int[] next = new int[t.length()];
        for (int i = 1; i < t.length(); ++i) {
            int j = next[i - 1];
            while (j > 0 && t.charAt(i) != t.charAt(j)) j = next[j - 1];
            j += (t.charAt(i) == t.charAt(j)) ? 1 : 0;
            next[i] = j;
        }
        return r.substring(0, s.length() - next[t.length() - 1]) + s;
    }

    /**
     *
     * 下面这种Java写法也是在找相同的前缀后缀，但是并没有每次把前缀后缀取出来比较，而是用两个指针分别指向对应的位置比较，
     * 然后用end指向相同后缀的起始位置，最后再根据end的值来拼接两个字符串。有意思的是这种方法对应的C++写法会TLE，
     * 跟上面正好相反，那么我们是否能得出Java的substring操作略慢，而C++的reverse略慢呢，我也仅仅是猜测而已。
     *
     * @param s
     * @return
     */
    public String shortestPalindrome4(String s) {
        char[] arr = s.toCharArray();
        int end = s.length() - 1;

        int i = 0;
        int j = end;

        while (i < j) {
            if (arr[i] == arr[j]) { // palindrome substr candidate
                ++i;
                --j;
            } else {
                i = 0;
                --end; // reduce length of candidate and keep checking
                j = end;
            }
        }
        // 0 - end is palindrome, end+1 - len is suffix
        return new StringBuilder(s.substring(end + 1)).reverse().toString() + s;
    }
}
