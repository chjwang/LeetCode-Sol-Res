package com.freetymekiyan.algorithms.level.Medium;

/**
 * Given an array with n objects colored red, white or blue, sort them so that
 * objects of the same color are adjacent, with the colors in the order red,
 * white and blue.
 * 
 * Here, we will use the integers 0, 1, and 2 to represent the color red,
 * white, and blue respectively.
 * 
 * Note:
 * You are not suppose to use the library's sort function for this problem.
 * 
 * Follow up:
 * A rather straight forward solution is a two-pass algorithm using counting
 * sort.
 * First, iterate the array counting number of 0's, 1's, and 2's, then
 * overwrite array with total number of 0's, then 1's and followed by 2's.
 * 
 * Could you come up with an one-pass algorithm using only constant space?
 * 
 * Tags: Array, Two Pointers, Sort
 */
class SortColors {
    public static void main(String[] args) {
        SortColors s = new SortColors();
        // normal case
        int[] A = {0, 1, 0, 1, 2, 1, 0};
        // other test cases
        // int[] A = {1, 2, 0};
        // int[] A = {2};
        // int[] A = {2, 2};
        s.onePassSortColors(A);
    }
    
    /**
     * One-pass count sorting
     * Remember the count of red, and count of red + white
     */
    public void onePassSortColors(int[] A) {
        int i = -1; // red count, start of white 
        int j = -1; // red + white count, start of blue
        
        for (int k = 0; k < A.length; k++) {
            int v = A[k];
            A[k] = 2; // overwrite as blue
            if (v == 0) {
                A[++j] = 1; // write white first, then red
                A[++i] = 0; // overwrite 1 when there is no white yet
            } else
                if (v == 1)
                    A[++j] = 1;
        }
    }

    /**
     * two-pass count sorting
     */
    public void sortColors(int[] A) {
        int red   = 0;
        int white = 0;
        int blue  = 0;

        for (int i = 0; i < A.length; i++) {
            if (A[i] == 0) {
                red++;
            } else if (A[i] == 1) {
                white++;
            } else {
                blue++;
            }
        }
        int i = 0;
        while (i < red) {
            A[i++] = 0;
        }
        while (i < white + red) {
            A[i++] = 1;
        }
        while (i < white + red + blue) {
            A[i++] = 2;
        }
        
        for (int k = 0; k < A.length; k++) {
            System.out.println(A[k]);
        }
    }

    /**
     * The famous Dutch national flag problem.

     The problem was posed with three colours, here `0′, `1′ and `2′. The array is divided into four sections:

     a[1..Lo-1] zeroes (red)
     a[Lo..Mid-1] ones (white)
     a[Mid..Hi] unknown
     a[Hi+1..N] twos (blue)

     The unknown region is shrunk while maintaining these conditions

     Lo := 1; Mid := 1; Hi := N;
     while Mid <= Hi do
         Invariant: a[1..Lo-1]=0 and a[Lo..Mid-1]=1 and a[Hi+1..N]=2; a[Mid..Hi] are unknown.
         case a[Mid] in
            0: swap a[Lo] and a[Mid]; Lo++; Mid++
            1: Mid++
            2: swap a[Mid] and a[Hi]; Hi–

     — Dutch National Flag Algorithm, or 3-way Partitioning —

     Part way through the process, some red, white and blue elements are known and are in the “right”
     place. The section of unknown elements, a[Mid..Hi], is shrunk by examining a[Mid]

     * @param a
     */
    // Sort the input array, the array is assumed to have values in {0, 1, 2}
    public void sort012(int a[]) {
        int lo = 0;
        int hi = a.length - 1;
        int mid = 0;
        while (mid <= hi) {
            switch (a[mid]) {
                case 0: {
                    swap(a, lo, mid);
                    lo++; mid++;
                    break;
                }
                case 1:
                    mid++;
                    break;
                case 2: {
                    swap(a, mid, hi);
                    hi--;
                    break;
                }
            }
        }
    }

    public void sortColors1Pass(int[] nums) {
        int lastZero = -1;
        int lastOne = -1;
        int cur = 0;
        while (cur < nums.length) {
            if (nums[cur] == 1) {
                swap(nums, lastOne + 1, cur);
                lastOne++;
            } else if (nums[cur] == 0) {
                swap(nums, lastZero + 1, cur);
                lastZero++;
                lastOne++;
                if (lastOne > lastZero) {
                    swap(nums, lastOne, cur);
                }
            }
            cur++;
        }
        return;
    }

    /**
     * Given an array of n objects with k different colors (numbered from 1 to k),
     * sort them so that objects of the same color are adjacent, with the colors in the order 1, 2, … k.
     *
     * Each time sort the array into three parts:
     [all min] [all unsorted others] [all max],
     then update min and max and sort the [all unsorted others] with the same method.
     * @param colors
     * @param k
     */
    public void sortColors2(int[] colors, int k) {
        int pl = 0;
        int pr = colors.length - 1;

        int i = 0;
        int min = 1, max = k;

        while (min < max) {
            while (i <= pr) {
                if (colors[i] == min) {
                    swap(colors, pl, i);
                    i++;
                    pl++;
                } else if (colors[i] == max) {
                    swap(colors, pr, i);
                    pr--;
                } else {
                    i++;
                }
            }
            i = pl;
            min++;
            max--;
        }
    }

    private void swap(int[] colors, int i, int j) {
        int temp = colors[i];
        colors[i] = colors[j];
        colors[j] = temp;
    }

}
