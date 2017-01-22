package com.freetymekiyan.algorithms.level.Medium;

import java.util.*;

/**
 * Given an array of integers, every element appears three times except for
 * one. Find that single one.
 * 
 * Note:
 * Your algorithm should have a linear runtime complexity. Could you implement
 * it without using extra memory?
 * 
 * Tags: Bit Manipulation
 */
class SingleNum2 {
    
    public static void main(String[] args) {
        int[] A = {1, 1, 1, 2, 2, 2, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7};
        System.out.println(singleNum(A));
    }
    
    /**
     * Since we know that XOR operation can be used for testing if 1 bit occurs twice, in other words,
     for a single bit, if 1 occurs twice, it turns to 0.

     Now we need a 3-time criteria for each bit, by utilizing the bit operations.
     This 3-time criteria needs every bit turns to 0 if '1' occurs three times.

     If we know on which bits '1' occurs twice, and also know on which bits '1' occurs 1-time,
     a simple '&' operation would result in the bit where '1' occurs three times. Then we turn these
     bits to zero, would do well for this problem.

     (1). Check bits which have 1-time '1', use the XOR operation.
     (2). Check bits which have 2-times '1's, use current 1-time result & current number.
     (3). Check bits which have 3-times '1's, use '1-time' result & '2-times' result
     (4). To turn 3-times bits into 0:   ~(3-times result) & 1-time result
     ~(3-times result) & 2-times result

     E.g.,We have numbers:  101101,   001100, 101010
     To count the occurrence of 1's:
     101101
     001100
     101010
     count:  {2,0,3,2,1,1}

     Denote:
     t1: bit=1 if current bit has 1-time '1'
     t2: bit=1 if current bit  has 2-times '1'
     t3: bit=1 if current bit  has 3-times '1'

     Result:
     t1 = 000011, t2 = 100100, t3 = 001000



     Initialization: t1 = 000000, t2=000000, t3 = 000000
     (1) 101101
     t1 = 101101  (using XOR)
     t2 = 000000
     t3 = 000000

     (2)001100
     % Current 2 times bits (t2) and NEW 2 times bits coming from 1 time bits and new number.
     t2 = t2 | 001100 & t1 =  001100 & 101101 = 001100
     t1 = t1 XOR 001100 = 100001
     t3 = t2 & t1 = 000000

     (3)101010
     t2 = t2 | (101010 & t1) = t2 | (101010 & 100001) = 101100
     t1 = t1 XOR 101010 = 100001 XOR 101010 = 001011

     t3 = t1 & t2 = 001000

     %Turn 3-time bits into zeros
     t1 = t1 & ~t3 = 000011
     t2 = t2 & ~t3 = 100100

     */
    public int singleNumber(int A[]) {
        int t1 = 0;
        int t2 = 0;
        int t3 = 0;

        for (int i = 0; i < A.length; i++){
            t1 = t1 ^ A[i];

            t2 = t2 | ((t1^A[i]) & A[i]);
            t3 = ~(t1 & t2);
            t1 = t1 & t3;
            t2 = t2 & t3;
        }

        return t1;

    }

    /**
     * Use ones to store those nums only appeared once
     * twos to store those nums appeared twice
     *
     * @param A
     * @return
     */
    public static int singleNum(int[] A) {
        int ones = 0;
        int twos = 0;
        for (int i = 0; i < A.length; i++) {
            ones = (ones ^ A[i]) & ~twos; // in ones not in twos
            twos = (twos ^ A[i]) & ~ones; // in twos not in ones
        }
        return ones; // only appeared once
    }

    /**
     * http://traceformula.blogspot.com/2015/08/single-number-ii-how-to-come-up-with.html
     *
     * p is the bits that appear twice
     * q is the bits that appear once or twice
     *
     * @param nums
     * @return
     */
    public int singleNum2(int[] nums) {
        int p = 0;
        int q = 0;
        for (int i = 0; i<nums.length; i++) {
            p = q & (p ^ nums[i]);
            q = p | (q ^ nums[i]);
        }
        return q;
    }

    /*
    在 I 中巧妙地用异或运算解决了把重复的元素的“消除”，只留下“落单”的元素的问题。而在 II 中，除了要找的元素，
    每个元素都出现 3 次，I 的解法适用于出现偶数次的情况，但对于奇数次已不再适用。

    考虑每个数用二进制展开，将各个数横向比较：
    对于一个32位（或者64位）的整数，对于这32个位中的某个位而言，如果每个数都出现三次，那么对于所有数在这个位上“1”的个数，
    一定是 3 的倍数；而反之，如果存在某个数不是出现 3 次（也不是 3 的倍数次，这是题目未讲明处，I 亦同理如此），
    那么对于它的二进制展开后数为 1 的位而言，对于所有数在这个位上“1”的个数，一定不是 3 的倍数。

    所以具体的解决办法是：

    用一个 32 长的数组存储对于所有数的二进制展开，每一个位上总共 “1” 的个数和，最后看那些位上 “1” 的个数不是 3 的倍数，
    那么这一位在 ans 中就是 1。

    算法是 O(32n) 的。
     */
    int singleNumber(int A[], int n) {
        int res = 0;
        for (int i=31; i>=0; i--) {
            int sum = 0;
            int mask = 1<<i;
            for (int j=0; j<n; j++) {
                if ((A[j] & mask) != 0)
                    sum++;
            }
            res = (res<<1) + (sum%3);
        }
        return res;
    }

    // http://traceformula.blogspot.com/2015/08/single-number-ii-how-to-come-up-with.html
    public int singleNumber2(int[] nums) {
        int result = 0;
        for(int i = 0; i<32; i++){
            int bit = 1 << i;
            int count = 0;
            for(int j=0; j<nums.length; j++){
                if((nums[j] & bit) != 0) // if it is bit 1
                    count++;
            }
            if (count % 3 == 1) result |= bit;
        }
        return result;
    }

    int getSingle(int arr[]) {
        int ones = 0;
        int twos = 0 ;
        int n = arr.length;

        int commonBitMask;

        // Let us take the example of {3, 3, 2, 3} to understand this
        for ( int i=0; i< n; i++ ) {
        /* The expression "one & arr[i]" gives the bits that are there in both 'ones' and new element from arr[].
        We add these bits to 'twos' using bitwise OR

        Value of 'twos' will be set as 0, 3, 3 and 1 after 1st, 2nd, 3rd and 4th iterations respectively */
            twos  = twos | (ones & arr[i]);

        /* XOR the new bits with previous 'ones' to get all bits appearing odd number of times

        Value of 'ones' will be set as 3, 0, 2 and 3 after 1st, 2nd, 3rd and 4th iterations respectively */
            ones  = ones ^ arr[i];

        /* The common bits are those bits which appear third time, so these bits should not be there in both 'ones' and 'twos'.
           commonBitMask contains all these bits as 0, so that the bits can be removed from 'ones' and 'twos'

           Value of 'commonBitMask' will be set as 00, 00, 01 and 10 after 1st, 2nd, 3rd and 4th iterations respectively */
            commonBitMask = ~(ones & twos);

        /* Remove common bits (the bits that appear third time) from 'ones'

           Value of 'ones' will be set as 3, 0, 0 and 2 after 1st, 2nd, 3rd and 4th iterations respectively */
            ones &= commonBitMask;

        /* Remove common bits (the bits that appear third time) from 'twos'

           Value of 'twos' will be set as 0, 3, 1 and 0 after 1st, 2nd, 3rd and 4th itearations respectively */
            twos &= commonBitMask;

            // uncomment this code to see intermediate values
            //System.out.println(ones + ", " + twos);
        }

        return ones;
    }

    /*
Single Number III

Given an array of numbers nums, in which exactly two elements appear only once and all the other
elements appear exactly twice. Find the two elements that appear only once.

For example:

Given nums = [1, 2, 1, 3, 2, 5], return [3, 5].

Note:

The order of the result is not important. So in the above example, [5, 3] is also correct.
Your algorithm should run in linear runtime complexity. Could you implement it using only constant space complexity?

很容易联想到 I 的解法，把所有数异或起来。但是异或之后我们得到的是我们想要的那两个数的异或值，如何把它们从异或中拆分开呢？



假设我们要找的这两个数为 a, b, 而 x = a ^ b。
首先，a 肯定不等于 b，那么说明它们的二进制位一定是不完全相同的，所以 x 肯定不为 0。
也就是说，a 与 b 一定存在“某一位”，使得在它们中的某个数中是 0，而在另一个数中是 1，这是他们之间的一个差别。
我们可不可以利用这个差别来把这两个数从 x 中揪出来呢？
是可以的。

利用这个差别，我们可以将整个 nums 集合分成两个集合。一个集合中是这 “某一位” 为 0 的在nums中的所有数，假设为集合 A。
而另一个集合是这 “某一位” 为 1 的在nums中的所有数。假设 a 的这 “某一位” 是 0 ，b 的 这个“某一位”是1，那么显然
a 在集合 A 中，b 在集合 B 中，这样问题就完全转化成了与 I 一样的两个子问题，于是可以得解。

关于具体的代码实现，还有一点说明：
我们如何找到这个 “某一位” 呢？理论上，只要是在这一位上 a与b的值不同，都可以合格的成为我们需要找的某一位。既然无更多限制，
那我们肯定是找好找的某一位咯。
我们可以用很常规和易懂的方法去找，但一般而言，我们肯定是找最右边（低位）那边符合要求的“某一位”嘛。更进一步说，就是找到
x 中最低位的 1 嘛。那当然我们可以从最低位开始枚举每一位，直到找到我们需要找的那个“某一位”。

还有一种更trick利用位运算的办法：找到 x 中最低位的 1，仔细想想，这跟另外一个已经熟知的问题很像。
当我们统计某个数的二进制展开中1的个数的时候，我们使用了一个技巧，即用 n &= n - 1 每次来清除 n 中当前最右边的那个 1。
n-1 是把 n 的最低位的 1 变成 0，并且使更低位的 0 全部变成 1，然后异或一下就把 最低位的 1 及其更低位全部都变成了 0，
即达到了“清除最低位的 1 的目的”。
（详见：统计二进制展开中数位1的个数的优化 优化解法1）

在这个地方，我们需要逆向思维，即 保留 最低位的1，并且最好使得其他位 都变成0，这样我直接与 nums 中的每一个数相与，
就可以直接将它们分成 A 和 B 两个集合了。

逆向思维会是这样：
n-1 的过程其实是把 最低位 1 和 跟低位 都位反转 (Bit Flipping) 的过程，那我们这里 首先也将 n 的所有位反转得到 n'。
然后我们再把 n'+1。。。
Opps! What did we get?
我们发现 n'+1 相对于 n 而言，最低位的1及其更低位的0 都没变，而其他位（比 最低位1 更高的位）都反转了。
那此时如果用 n & (n'+1) 得到的便是 除 n 中最低位 1 继续为 1 以外，其他各位都为 0 的数了。
n' 如何求？当然我们可以直接取反。但是联合 n'+1 来看，各个位取反再加一，这不正是计算机中 “负数补码” 的表示方式！
所以我们可以直接用 n &= -n 得到 “除 n 中最低位 1 继续为 1 以外，其他各位都为 0 的数”！
（注意正数的补码的补码是它本身，所以即便 n 本身是负数，-n是正数，但 -n 依然是求 n 的补码。）

完美！

Once again, we need to use XOR to solve this problem. But this time, we need to do it in two passes:

In the first pass, we XOR all elements in the array, and get the XOR of the two numbers we need to find.
Note that since the two numbers are distinct, so there must be a set bit (that is, the bit with value '1')
in the XOR result. Find out an arbitrary set bit (for example, the rightmost set bit).

In the second pass, we divide all numbers into two groups, one with the aforementioned bit set, another
with the aforementinoed bit unset. Two different numbers we need to find must fall into thte two distrinct groups.
XOR numbers in each group, we can find a number in either group.

Complexity: Time: O (n) Space: O (1)

A Corner Case:

When diff == Integer.MIN_VALUE, -diff is also Integer.MIN_VALUE (silent overflow: Integer.MAX_VALUE + 1 -> Integer.MIN_VALUE).
Therefore, the value of diff after executing diff &= -diff is still Integer.MIN_VALUE.
The answer is still correct.

     */
    public int[] singleNumber3(int[] nums) {
        // Pass 1 :
        // Get the XOR of the two numbers we need to find
        int diff = 0;
        for (int num : nums)
            diff ^= num;

        // Get its last set bit. It's an old trick that gives a number with a single bit in it,
        // the bottom bit that was set in n. Another way: n & (~n + 1)
        diff &= -diff;

        // Pass 2 :
        int[] rets = {0, 0}; // this array stores the two numbers we will return
        for (int num : nums) {
            if ((num & diff) == 0) // the bit is not set
            {
                rets[0] ^= num;
            } else // the bit is set
            {
                rets[1] ^= num;
            }
        }
        return rets;
    }


/*
Find the element that appears once in a sorted array
Given a sorted array in which all elements appear twice (one after one) and one element appears only once.
Find that element in O(log n) complexity.

Example:

Input:   arr[] = {1, 1, 3, 3, 4, 5, 5, 7, 7, 8, 8}
Output:  4

Input:   arr[] = {1, 1, 3, 3, 4, 4, 5, 5, 7, 7, 8}
Output:  8


A Simple Solution is to traverse the array from left to right. Since the array is sorted, we can easily
figure out the required element.

An Efficient Solution can find the required element in O(Log n) time. The idea is to use Binary Search.
Below is an observation in input array.

All elements before the required have first occurrence at even index (0, 2, ..) and next occurrence at odd index (1, 3, …).
And all elements after the required element have first occurrence at odd index and next occurrence at even index.

1) Find the middle index, say ‘mid’.

2) If ‘mid’ is even, then compare arr[mid] and arr[mid + 1]. If both are same, then the required element
appears after ‘mid’ else before mid.

3) If ‘mid’ is odd, then compare arr[mid] and arr[mid – 1]. If both are same, then the required element
appears after ‘mid’ else before mid.
 */

    // A Binary Search based function to find the element
// that appears only once
    public void search(int[] arr, int low, int high) {
        // Base cases
        if (low > high) return;

        if (low==high) {
            System.out.println("The required element is " + arr[low]);
            return;
        }

        // Find the middle point
        int mid = (low + high) / 2;

        // If mid is even and element next to mid is the same as mid, then output element appears on
        // the right side, else on the left side
        if (mid%2 == 0) {
            if (arr[mid] == arr[mid+1])
                search(arr, mid+2, high);
            else
                search(arr, low, mid);
        }
        else  // If mid is odd
        {
            if (arr[mid] == arr[mid-1])
                search(arr, mid-2, high);
            else
                search(arr, low, mid-1);
        }
    }

}
