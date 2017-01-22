package com.freetymekiyan.algorithms.level.Medium;

import java.util.Stack;

/**
 * Implement pow(x, n).
 *
 * Tags: Math, Binary Search
 */
class Pow {
    public static void main(String[] args) {
        Pow p = new Pow();
        System.out.println(p.pow(2.0, 5));
    }

    /**
     * Binary Search, divide n by 2
     * Questions:
     * 1. can x be zero?
     * 2. can n be negative?
     *
     * When n is odd, multiply result by f
     * f multiply by itself each time
     * Repeat until n == 0
     */
    public double pow(double x, int n) {
        if (n == 0)
            return 1;

        if (n < 0) { // neg case
            n = -n;
            x = 1 / x; // x can be zero?
        }

        double res = 1; // mind overflow
        double f = x;
        while (n > 0) {
            if (n % 2 == 1)
                res *= f;

            f *= f; // f = f * f
            n = n >> 1; // n >>= 1, n/=2
        }
        return res;
    }

    /**
     * x^n = x^(n/2)*x^(n/2)*x^(n%2)
     *
     * @param x
     * @param n
     * @return
     */
    public double pow2(double x, int n) {
        if (n == 0)
            return 1;
        if (n < 0)
            return 1 / pow2(x, -n);

        double v = pow2(x, n / 2);

        if (n % 2 == 0) {
            return v * v;
        } else {
            return v * v * x;
        }
    }

    /**
     * divide and conquer
     * @param x
     * @param n
     * @return
     */
    public double pow3(double x, int n) {
        if (n == 0)
            return 1;

        if (n == 1)
            return x;

        boolean isNegative = false;
        if (n < 0) {
            isNegative = true;
            n *= -1;
        }

        int k = n / 2;
        int l = n - k * 2;
        double t1 = pow3(x, k);
        double t2 = pow3(x, l);
        if (isNegative)
            return 1/(t1*t1*t2);
        else
            return t1*t1*t2;
    }

    public double pow4(double x, int n) {
        if (n == 0)
            return 1;
        boolean div = false;
        if (n < 0) {
            n = -n;
            div = true;
        }

        Stack<Double> stack = new Stack<>();
        while (n != 1) {
            if (n % 2 == 0)
                stack.push(1.0);
            else {
                stack.push(x);
            }

            n /= 2;
        }

        double result = x;
        while (!stack.isEmpty()) {
            result = result * result * stack.pop();
        }
        if (div)
            return 1.0 / result;
        else
            return result;
    }
}
