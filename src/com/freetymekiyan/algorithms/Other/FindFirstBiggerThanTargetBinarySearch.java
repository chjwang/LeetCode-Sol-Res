package com.freetymekiyan.algorithms.Other;

/**
 In a general binary search, we are looking for a value which appears in the array. Sometimes, we
 need looking for the first element that is greater/less than a target.

 A particularly elegant way of thinking about this problem is to think about doing a binary search
 over a transformed version of the array, where the array has been modified by applying the function

 f(x) = 1 if x > target
 0 else

 Now, the goal is to find the very first place that this function takes on the value 1.
 */
public class FindFirstBiggerThanTargetBinarySearch {

    public int minElementGreaterThanOrEqualToKey(int[] arr, int target) {
        int low = 0, high = arr.length;

        while (low != high) {
            int mid = low + (high - low) / 2; // a fancy way to avoid int overflow
            if (arr[mid] <= target) {
        /* This index, and everything below it, must not be the first element
         * greater than what we're looking for because this element is no greater
         * than the element.
         */
                low = mid + 1;
            }
            else {
        /* This element is at least as large as the element, so anything after it can't
         * be the first element that's at least as large.
         */
                high = mid;
            }
        }
        /* Now, low and high both point to the element in question. */
        return low;
    }

    public static int minElementGreaterThanOrEqualToKey(int A[], int key, int imin, int imax) {

        // Return -1 if the maximum value is less than the minimum or if the key
        // is great than the maximum
        if (imax < imin || key > A[imax])
            return -1;

        // Return the first element of the array if that element is greater than
        // or equal to the key.
        if (key < A[imin])
            return imin;

        // When the minimum and maximum values become equal, we have located the element.
        if (imax == imin)
            return imax;
        else {
            // calculate midpoint to cut set in half, avoiding integer overflow
            int imid = imin + ((imax - imin) / 2);
            // if key is in upper subset, then recursively search in that subset
            if (A[imid] < key)
                return minElementGreaterThanOrEqualToKey(A, key, imid + 1, imax);
                // if key is in lower subset, then recursively search in that subset
            else
                return minElementGreaterThanOrEqualToKey(A, key, imin, imid);
        }
    }
}
