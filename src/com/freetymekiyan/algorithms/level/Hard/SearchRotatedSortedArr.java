package com.freetymekiyan.algorithms.level.Hard;

/**
 * Suppose a sorted array is rotated at some pivot unknown to you beforehand.
 * 
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * 
 * You are given a target value to search. If found in the array return its
 * index, otherwise return -1.
 * 
 * You may assume no duplicate exists in the array.
 * 
 * Tags: Array, Binary Search
 */
class SearchRotatedSortedArr {
    public static void main(String[] args) {
        
    }
    
    /**
     * Binary Search.
     * rotated sorted array的特性，就是至少有一侧是排好序的（无论pivot在哪）
     *
     * 如果target比A[mid]值要小
            如果A[mid]右边有序（A[mid]<A[high])
                那么target肯定不在右边（target比右边的都得小），在左边找
            如果A[mid]左边有序
                那么比较target和A[low]，如果target比A[low]还要小，证明target不在这一区，去右边找；反之，左边找。
     * 如果target比A[mid]值要大
            如果A[mid]左边有序（A[mid]>A[low]）
                那么target肯定不在左边（target比左边的都得大），在右边找
            如果A[mid]右边有序
                那么比较target和A[high]，如果target比A[high]还要大，证明target不在这一区，去左边找；反之，右边找。
     */
    public int search(int[] A, int target) {
        if (A == null || A.length == 0)
            return -1;

        int low = 0;
        int high = A.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (target < A[mid]) {
                if (A[mid] < A[high]) //right side is sorted
                    high = mid - 1; //target must in left side
                else if (target < A[low])
                    //target<A[mid]&&target<A[low]==>means,target cannot be in [low,mid] since this side is sorted
                    low = mid + 1;
                else
                    high = mid - 1;
            } else if (target > A[mid]) {
                if (A[low] < A[mid]) //left side is sorted
                    low = mid + 1; //target must in right side
                else if (target > A[high])
                    //right side is sorted. If target>A[high] means target is not in this side
                    high = mid - 1;
                else
                    low = mid + 1;
            } else
                return mid;
        }

        return -1;
    }

    /**
     * Check which half is sorted.
     * If target is within that half, search in that half. 
     * If not, search in the other half.
     *
     * A[mid] =  target, 返回mid，否则
     *
     * (1) A[m] < A[r]: right half A[m+1 : r] is sorted
     *     A[m] < target <= A[r]  右半，否则左半。
     *     or
     * (2) else condition (A[m] > A[r]) : left half A[l : m-1] is sorted
     *     A[l] <= target < A[m] 左半，否则右半。
     *
     *     OR
     *
     * (1) A[m] > A[l] : left half A[l : m-1] is sorted
     *     A[l] <= target < A[m] 左半，否则右半。
     *
     * (2) else: right half A[m+1 : r] is sorted
     *     A[m] < target <= A[r]  右半，否则左半。
     *
     */
    public int search2(int[] A, int target) {
        if (A == null || A.length == 0) return -1;
        int l = 0;
        int r = A.length - 1;
        int m;
        while (l <= r) {
            m = l + (r - l) / 2;

            if (A[m] == target)
                return m;

            if (A[m] >= A[l]) { // left half sorted
                if (target >= A[l] && target < A[m]) {
                    r = m - 1;
                } else
                    l = m + 1;
            } else { // right half sorted
                if (target > A[m] && target <= A[r]) {
                    l = m + 1;
                } else r = m - 1;
            }
        }
        return -1;
    }

    /**
     * Follow up for "Search in Rotated Sorted Array":
     What if duplicates are allowed?

     Would this affect the run-time complexity? How and why?

     和之前那道题的解法区别就是，不能通过比较A[mid]和边缘值来确定哪边是有序的，会出现A[mid]与边缘值相等的状态。
     所以，解决方法就是对于A[mid]==A[low]和A[mid]==A[high]单独处理。

     当中间值与边缘值相等时，让指向边缘值的指针分别往前移动，忽略掉这个相同点，再用之前的方法判断即可。

     这一改变增加了时间复杂度，试想一个数组有同一数字组成{1，1，1，1，1}，target=2, 那么这个算法就会将整个数组遍历，
     时间复杂度由O(logn)升到O(n)
     */
    public boolean searchDuplicateAllowed(int[] A, int target) {
        if (A == null || A.length == 0)
            return false;

        int low = 0;
        int high = A.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (target < A[mid]) {
                if (A[mid] < A[high]) //right side is sorted
                    high = mid - 1; //target must in left side
                else if (A[mid] == A[high])
                    //cannot tell right is sorted, move pointer high
                    high--;
                else //left side is sorted
                    if (target < A[low])
                        low = mid + 1;
                    else
                        high = mid - 1;
            } else if (target > A[mid]) {
                if (A[low] < A[mid]) //left side is sorted
                    low = mid + 1; //target must in right side
                else if (A[low] == A[mid])
                    //cannot tell left is sorted, move pointer low
                    low++;
                else //right side is sorted
                    if (target > A[high])
                        high = mid - 1;
                    else
                        low = mid + 1;
            } else
                return true;
        }

        return false;
    }
}