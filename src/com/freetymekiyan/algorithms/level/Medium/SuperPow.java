package com.freetymekiyan.algorithms.level.Medium;

/**
 * Your task is to calculate a^b mod 1337 where a is a positive integer and b is an extremely large
 * positive integer given in the form of an array. <p> Example1: <p> a = 2 b = [3] <p> Result: 8
 * Example2: <p> a = 2 b = [1,0] <p> Result: 1024 <p> Tags: Math Similar Problems: (M) Pow(x, n)
 */
public class SuperPow {


    public static final int BASE = 1337;

    /**
     * Math.
     * ab % k = (a%k)(b%k)%k.
     * For example, if a = 2, b = [1, 1]
     * 2^11 % 1337 = (2^10 % 1337) * (2 % 1337) % 1337
     * Then 2^10 can be further decomposed into (2 % 1337)^10 % 1337
     * https://discuss.leetcode.com/topic/50489/c-clean-and-short-solution
     */
    public int superPow(int a, int[] b) {
        return superPow(a, b, b.length);
    }

    private int superPow(int a, int[] b, int length) {
        if (length == 0) {
            return 1;
        }
        int lastDigit = b[length - 1];
        length--;
        return powMod(superPow(a, b, length), 10) * powMod(a, lastDigit) % BASE;
    }

    /**
     * a^k % 1337
     * = (a % 1337)^k % 1337
     */
    private int powMod(int a, int k) {
        a %= BASE;
        int res = 1;
        for (int i = 0; i < k; i++) {
            res = (res * a) % BASE; // Avoid overflow
        }
        return res;
    }

    public int superPow2(int a, int[] b) {
        int n = b.length;
        int res = 1;
        for (int i = n - 1; i >= 0; i--) {
            res = res * quick_pow(a, b[i]) % BASE;
            a = quick_pow(a, 10);
        }
        return res;
    }

    int quick_pow(int a, int b) {
        int res = 1;
        a %= BASE;
        while (b > 0) {
            if ((b & 1) != 0)
                res = res * a % BASE;
            a = a * a % BASE;
            b >>= 1;
        }
        return res;
    }

    /**
     * 这道题是ACM里面入门问题，powmod，详细的算法可以参照这里：http://blog.csdn.net/xuruoxin/article/details/8578992
     * */

    // 判断是否大于0
    public boolean morethanzero(int[] x){
        for(int i=x.length-1;i>=0;i--){
            if(x[i]>0) return true;
        }
        return false;
    }

    //高精度除法
    public void div(int[] x,int y){
        int tmp=0;
        for(int i=0;i<x.length;i++){
            x[i] += tmp*10;
            tmp = x[i] % y;
            x[i] = x[i] /y;
        }

    }

    public int superPow3(int a, int[] b) {
        if (morethanzero(b) == false) return 1;
        a=a%1337;
        boolean isEven = false;
        if(b[b.length-1] % 2 == 0) isEven = true;
        div(b,2);
        int result = superPow3(a,b);
        result = result % 1337;
        result*=result;
        result = result % 1337;
        if(isEven==false){
            result*=a;
            result = result % 1337;
        }
        return result;
    }
}
