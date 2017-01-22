package com.freetymekiyan.algorithms.level.Hard;

/**
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 *
 * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
 *
 * Example 1:
 * nums1 = [1, 3]
 * nums2 = [2]
 *
 * The median is 2.0
 * Example 2:
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 *
 * The median is (2 + 3)/2 = 2.5
 */
public class MedianOfTwoSortedArrays {

    /**
    This problem can be converted to the problem of finding kth element, k is (A's length + B' Length)/2.

     If any of the two arrays is empty, then the kth element is the non-empty array's kth element.
     If k == 0, the kth element is the first element of A or B.

     For normal cases(all other cases), we need to move the pointer at the pace of half of the array
     size to get log(n) time.
    */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int total = nums1.length + nums2.length;
        if (total%2 == 0) {
            return (findKth(total/2 + 1, nums1, nums2, 0, 0)+
                    findKth(total/2, nums1, nums2, 0, 0)) / 2.0;
        }else{
            return findKth(total/2+1, nums1, nums2, 0, 0);
        }
    }

    public int findKth(int k, int[] nums1, int[] nums2, int s1, int s2){
        if(s1>=nums1.length)
            return nums2[s2+k-1];

        if(s2>=nums2.length)
            return nums1[s1+k-1];

        if(k==1)
            return Math.min(nums1[s1], nums2[s2]);

        int m1 = s1+k/2-1;
        int m2 = s2+k/2-1;

        int mid1 = m1<nums1.length?nums1[m1]:Integer.MAX_VALUE;
        int mid2 = m2<nums2.length?nums2[m2]:Integer.MAX_VALUE;

        if(mid1<mid2){
            return findKth(k-k/2, nums1, nums2, m1+1, s2);
        }else{
            return findKth(k-k/2, nums1, nums2, s1, m2+1);
        }
    }

    /**
    Actually, we can write the binary search solution in multiple different ways. The following is one of them.
    */
    public static double findMedianSortedArrays2(int A[], int B[]) {
        int m = A.length;
        int n = B.length;

        if ((m + n) % 2 != 0) // odd
            return (double) findKth(A, B, (m + n) / 2, 0, m - 1, 0, n - 1);
        else { // even
            return (findKth(A, B, (m + n) / 2, 0, m - 1, 0, n - 1)
                    + findKth(A, B, (m + n) / 2 - 1, 0, m - 1, 0, n - 1)) * 0.5;
        }
    }

    public static int findKth(int A[], int B[], int k,
                              int aStart, int aEnd, int bStart, int bEnd) {

        int m = aEnd - aStart + 1;
        int n = bEnd - bStart + 1;

        // Handle special cases
        if (m == 0)
            return B[bStart + k];
        if (n == 0)
            return A[aStart + k];
        if (k == 0)
            return A[aStart] < B[bStart] ? A[aStart] : B[bStart];

        int aMid = m * k / (m + n); // a's middle point
        int bMid = k - aMid - 1; // b's middle point

        // make aMid and bMid to be array index
        aMid = aMid + aStart;
        bMid = bMid + bStart;

        if (A[aMid] > B[bMid]) {
            k = k - (bMid - bStart + 1);
            aEnd = aMid;
            bStart = bMid + 1;
        } else {
            k = k - (aMid - aStart + 1);
            bEnd = bMid;
            aStart = aMid + 1;
        }

        return findKth(A, B, k, aStart, aEnd, bStart, bEnd);
    }

    // find kth number of two sorted arrays
    public static int findKth3(int[] A, int aStart,
                              int[] B, int bStart,
                              int k) {
        if (aStart >= A.length) {
            return B[bStart + k - 1];
        }
        if (bStart >= B.length) {
            return A[aStart + k - 1];
        }

        if (k == 1) {
            return Math.min(A[aStart], B[bStart]);
        }

        int A_key = aStart + k / 2 - 1 < A.length
                ? A[aStart + k / 2 - 1]
                : Integer.MAX_VALUE;
        int B_key = bStart + k / 2 - 1 < B.length
                ? B[bStart + k / 2 - 1]
                : Integer.MAX_VALUE;

        if (A_key < B_key) {
            return findKth3(A, aStart + k / 2, B, bStart, k - k / 2);
        } else {
            return findKth3(A, aStart, B, bStart + k / 2, k - k / 2);
        }
    }

    public double findMedianSortedArrays3(int A[], int B[]) {
        int len = A.length + B.length;
        if (len % 2 == 1) {
            return findKth3(A, 0, B, 0, len / 2 + 1);
        }
        return (findKth3(A, 0, B, 0, len / 2) + findKth3(A, 0, B, 0, len / 2 + 1)) / 2.0;
    }

    /**
     * 如何判断两个有序数组A,B中第k大的数呢？

     我们需要判断A[k/2-1]和B[k/2-1]的大小。

     如果A[k/2-1]==B[k/2-1]，那么这个数就是两个数组中第k大的数。

     如果A[k/2-1]<B[k/2-1], 那么说明A[0]到A[k/2-1]都不可能是第k大的数，所以需要舍弃这一半，
     继续从A[k/2]到A[A.length-1]继续找。当然，因为这里舍弃了A[0]到A[k/2-1]这k/2个数，那么第k大
     也就变成了第k-k/2个大的数了。

     如果 A[k/2-1]>B[k/2-1]，就做之前对称的操作就好。

     这样整个问题就迎刃而解了。

     当然，边界条件页不能少，需要判断是否有一个数组长度为0，以及k==1时候的情况。

     因为除法是向下取整，并且为了方便起见，对每个数组的分半操作采取：

     int partA = Math.min(k/2,m);
     int partB = k - partA;

     为了能保证上面的分半操作正确，需要保证A数组的长度小于B数组的长度。

     同时，在返回结果时候，注意精度问题，返回double型的就好。
     */
    public static double findMedianSortedArrays4(int A[], int B[]) {
        int m = A.length;
        int n = B.length;
        int total = m + n;
        if (total % 2 != 0)
            return (double) findKth(A, 0, m - 1, B, 0, n - 1, total / 2 + 1);//k传得是第k个，index实则k-1
        else {
            double x = findKth(A, 0, m - 1, B, 0, n - 1, total / 2);//k传得是第k个，index实则k-1
            double y = findKth(A, 0, m - 1, B, 0, n - 1, total / 2 + 1);//k传得是第k个，index实则k-1
            return (double) (x + y) / 2;
        }
    }

    public static int findKth(int[] A, int aStart, int aEnd, int[] B, int bStart, int bEnd, int k) {
        int m = aEnd - aStart + 1;
        int n = bEnd - bStart + 1;

        if (m > n) // always start from smaller array
            return findKth(B, bStart, bEnd, A, aStart, aEnd, k);
        if (m == 0)
            return B[k - 1];
        if (k == 1)
            return Math.min(A[aStart], B[bStart]);

        int partA = Math.min(k/2, m);
        int partB = k - partA;
        if (A[aStart + partA - 1] < B[bStart + partB - 1])
            return findKth(A, aStart + partA, aEnd, B, bStart, bEnd, k - partA);
        else if (A[aStart + partA - 1] > B[bStart + partB - 1])
            return findKth(A, aStart, aEnd, B, bStart + partB, bEnd, k - partB);
        else
            return A[aStart + partA - 1];
    }
}
