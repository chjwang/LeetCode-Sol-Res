package com.freetymekiyan.algorithms.level.Medium;

/**
 * Implement int sqrt(int x).
 * Compute and return the square root of x.
 *
 * Tags: Math, Binary Search
 */
class Sqrt {
    public static void main(String[] args) {
        int[] nums = {  -1, 1, 2, 4, 9, 16, 25 };
        for (int i = 0; i < nums.length; i++) {
            System.out.println(sqrt(nums[i]));
        }
    }

    /**
     * Validate input first
     * Binary Search from 1 ~ x
     * 
     * Negative?
     * Perfect square?
     * Note possible overflows when mid * mid or (left + right) / 2.
     */
    public static int sqrt(int x) {
        if (x < 0)
            return -1; // if (x <= 0) return x;
        if (x == 0)
            return 0;

        int left = 1; // search range
        int right = x;
        int mid;

        while (left <= right) { // can equal
            mid = left + (right - left) / 2; // left + right can overflow

            if (mid == x / mid)
                return mid; // mid * mid can overflow
            else if (mid > x / mid)
                right = mid - 1; // not right = mid
            else
                left = mid + 1; // break equal
        }

        return right;
    }

    /**
     * 一般牛顿法是用数值方法来解一个f(y)=0的方程（为什么变量在这里用y是因为我们要求的开方是x，避免歧义）。
     *
     * 对于这个问题我们构造f(y)=y^2-x，其中x是我们要求平方根的数，那么当f(y)=0时，即y^2-x=0,
     * 所以y=sqrt(x),即是我们要求的平方根。
     *
     * f(y)的导数f'(y)=2*y，根据牛顿法的迭代公式我们可以得到
     *  y_(n+1) = y_n - f(n)/f'(n) = (y_n + x/y_n) / 2。最后根据以上公式来迭代解以上方程。
     *
     * @param x
     * @return
     */
    public int sqrt2(int x) {
        if (x == 0) return 0;
        double lastY = 0;
        double y = 1;
        while (y != lastY)
        {
            lastY = y;
            y = (y + x / y) / 2;
        }
        return (int)(y);
    }

    public double sqrt3(int x) {
        if (x < 2) return x;

        double lowerBound = 0.0;
        double upperBound = (double) x;
        double middle = (double) x / 2.0;

        while (Math.abs((middle * middle) - (double) x) > 0.001) {
            if (middle * middle > (double) x)
                upperBound = middle;
            else
                lowerBound = middle;
            middle = lowerBound + ((upperBound - lowerBound) / 2.0);
        }
        return middle;
    }
}

class Newton {

    // return the square root of c, computed using Newton's method
    public static double sqrt(double c) {
        if (c < 0) return Double.NaN;
        double EPSILON = 1E-15;
        double t = c;
        while (Math.abs(t - c/t) > EPSILON * t)
            t = (c/t + t) / 2.0;
        return t;
    }

    // overloaded version in which user specifies the error tolerance epsilon
    public static double sqrt(double c, double epsilon) {
        if (c < 0) return Double.NaN;
        double t = c;
        while (Math.abs(t - c/t) > epsilon * t)
            t = (c/t + t) / 2.0;
        return t;
    }


    // test client
    public static void main(String[] args) {

        // parse command-line arguments
        double[] a = new double[args.length];
        for (int i = 0; i < args.length; i++) {
            a[i] = Double.parseDouble(args[i]);
        }

        // compute square root for each command line parameter
        for (int i = 0; i < a.length; i++) {
            double x = sqrt(a[i]);
            System.out.println(x);
        }
    }
}
