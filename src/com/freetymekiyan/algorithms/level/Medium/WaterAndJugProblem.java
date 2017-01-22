package com.freetymekiyan.algorithms.level.Medium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * You are given two jugs with capacities x and y litres. There is an infinite amount of water supply available. You
 * need to determine whether it is possible to measure exactly z litres using these two jugs.
 * <p>
 * If z liters of water is measurable, you must have z liters of water contained within one or both buckets by the end.
 * <p>
 * Operations allowed:
 * <p>
 * Fill any of the jugs completely with water.
 * Empty any of the jugs.
 * Pour water from one jug into another till the other jug is completely full or the first jug itself is empty.
 * Example 1: (From the famous "Die Hard" example)
 * <p>
 * Input: x = 3, y = 5, z = 4
 * Output: True
 * Example 2:
 * <p>
 * Input: x = 2, y = 6, z = 5
 * Output: False
 * <p>
 * Tags: Math
 */
public class WaterAndJugProblem {

    private WaterAndJugProblem w;

    /**
     * Math, Bézout's identity.
     * Let a and b be nonzero integers and let d be their greatest common divisor.
     * Then there exist integers x and y such that ax+by=d, linear combination of x and y.
     *
     *
     * 求最大公约数GCD（Greatest Common Divisor)。

     如果x与y互质（最大公约数为1），则容量范围[1, x + y]之内的任意整数体积均可以通过适当的操作得到。

     否则，记x与y的最大公约数为gcd，则可以获得的容量z只能为gcd的整数倍，且z <= x + y

     简单的证明：

     假设最终体积z = m * x + n * y（m与n为整数，可以为0或者为负）

     令x = p * gcd, y = q * gcd，可知p与q互质。

     则z = (m * p + n * q) * gcd

     可以证明一定存在m, n，使得m * p + n * q = 1（p与q互质的性质，参见：裴蜀定理）

     由此可知z一定是gcd的整数倍

     */
    public boolean canMeasureWater(int x, int y, int z) {
        if (x + y < z) {
            return false;
        }
        if (x == z || y == z || x + y == z) {
            return true;
        }
        int gcd = gcd(x, y);
        return z % gcd == 0;
    }

    /**
     * Euclid's Algorithm.
     * gcd(a, 0) = a
     * gcd(a, b) = gcd(b, a mod b)
     * a mod b = a - b * floor(a / b)
     */
    private int gcd(int x, int y) {
        return y == 0 ? x : gcd(y, x % y);
    }

    @Before
    public void setUp() {
        w = new WaterAndJugProblem();
    }

    @Test
    public void testExamples() {
        Assert.assertTrue(w.canMeasureWater(3, 5, 4));
        Assert.assertFalse(w.canMeasureWater(0, 0, 1));
        Assert.assertTrue(w.canMeasureWater(0, 0, 0));
        Assert.assertFalse(w.canMeasureWater(2, 6, 5));
    }

    @After
    public void tearDown() {
        w = null;
    }

}
