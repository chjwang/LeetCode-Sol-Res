package com.freetymekiyan.algorithms.Other;

import java.lang.reflect.Array;

/**
 Iterating through a k-dimensional array given size of each dimension in an array.
 */
public class KDimensionalArrayIterator {

    public static void main(String[] args) {
        // the process method will be called on each element of the k-dimensional
//        ElementProcessor p = new ElementProcessor() {
//            @Override
//            public void process(Object e) {
//                // simply log for example
//                System.out.println(e);
//            }
//        };
        ElementProcessor p = e -> {
            // simply log for example
            System.out.println(e);
        };

        int[] a1 = new int[] { 1, 2 };
        int[][] a2 = new int[][] { new int[] { 3, 4 }, new int[] { 5, 6 } };

        int[][][] a3 = new int[][][] {{{1}, {2, 3}}, {{4, 5,6}, {7, 8, 9, 10}}, {{11}}};
        iterate(a1, p);
        iterate(a2, p);
        iterate(a3, p);
    }

    public interface ElementProcessor {
        void process(Object e);
    }

    public static void iterate(Object o, ElementProcessor p) {
        int n = Array.getLength(o);
        for (int i = 0; i < n; i++) {
            Object e = Array.get(o, i);
            if (e != null && e.getClass().isArray()) {
                iterate(e, p);
            } else {
                p.process(e);
            }
        }
    }
}
