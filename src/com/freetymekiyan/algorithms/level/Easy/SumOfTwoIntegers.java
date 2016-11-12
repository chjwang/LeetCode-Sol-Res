/**
 * Calculate the sum of two integers a and b, but you are not allowed to use the operator + and -.
 * <p>
 * Example:
 * Given a = 1 and b = 2, return 3.
 * <p>
 * Tags: Bit Manipulation
 * Similar Problems: (M) Add Two Numbers
 */
public class SumOfTwoIntegers {

    /**
     * For example, a = 0001, b = 0011.
     * First, we can use "and"("&") operation between a and b to find a carry.
     * carry = a & b, then carry = 0001
     * Second, we can use "xor" ("^") operation between a and b to find the different bit, and assign it to a.
     * Then, we shift carry one position left and assign it to b, b = 0010.
     * Iterate until there is no carry (or b == 0)
     *
     *
     *
     * 我们在做加法运算的时候，每位相加之后可能会有进位Carry产生，然后在下一位计算时需要加上进位一起运算，
     * 那么我们能不能将两部分拆开呢，我们来看一个例子759+674
     * 1. 如果我们不考虑进位，可以得到323
     * 2. 如果我们只考虑进位，可以得到1110
     * 3. 我们把上面两个数字假期323+1110=1433就是最终结果了
     *
     * 然后我们进一步分析，如果得到上面的第一第二种情况，我们在二进制下来看，不考虑进位的加，
     * 0+0=0， 0+1=1, 1+0=1， 1+1=0，这就是异或的运算规则，
     * 如果只考虑进位的加0+0=0, 0+1=0, 1+0=0, 1+1=1，而这其实这就是与的运算，
     * 而第三步在将两者相加时，我们再递归调用这个算法，终止条件是当进位为0时，我们直接返回第一步的结果

     *
     */
    public int getSum(int a, int b) {
        if (b == 0) return a;

        while (b != 0) {
            int carry = a & b;
            a = a ^ b;
            b = carry << 1;
        }

        return a;
    }

    public int getSumRecursive(int a, int b) {
        if (b == 0) return a;
        return getSumRecursive(a ^ b, (a & b) << 1);
    }

}
