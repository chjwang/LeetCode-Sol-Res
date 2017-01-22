package com.freetymekiyan.algorithms.level.Hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * You are given an integer array nums and you have to return a new counts array. The counts array has the property
 * where counts[i] is the number of smaller elements to the right of nums[i].
 * <p>
 * Example:
 * <p>
 * Given nums = [5, 2, 6, 1]
 * <p>
 * To the right of 5 there are 2 smaller elements (2 and 1).
 * To the right of 2 there is only 1 smaller element (1).
 * To the right of 6 there is 1 smaller element (1).
 * To the right of 1 there is 0 smaller element.
 * Return the array [2, 1, 1, 0].
 * <p>
 * Tags: Divide and Conquer, Binary Indexed Tree, Segment Tree, Binary Search Tree
 * Similar Problems: (H) Count of Range Sum
 * <p>
 * Created by kiyan on 6/2/16.
 */
public class CountOfSmallerNumbersAfterSelf {

    public static void main(String[] args) {
//        int[] input = {5, 2, 6, 1};
        int[] input = {1, 2, 3, 4};
        CountOfSmallerNumbersAfterSelf c = new CountOfSmallerNumbersAfterSelf();
        List<Integer> result = c.countSmaller(input);
        System.out.println(result);
    }

    void constructLowerArray(int arr[], int countSmaller[], int n) {
        int i, j;

        // initialize all the counts in countSmaller array as 0
        for (i = 0; i < n; i++)
            countSmaller[i] = 0;

        for (i = 0; i < n; i++) {
            for (j = i + 1; j < n; j++) {
                if (arr[j] < arr[i])
                    countSmaller[i]++;
            }
        }
    }

    /**
     * The smaller numbers on the right of a number are exactly those that jump from its right to its left during a
     * stable sort. So I do mergesort with added tracking of those right-to-left jumps.
     */
    public List<Integer> countSmaller(int[] nums) {
        NumWithIndex[] array = new NumWithIndex[nums.length];
        for (int i = 0; i < nums.length; i++) {
            array[i] = new NumWithIndex(nums[i], i);
        }
        return MergeSort.sort(array);
    }

    static class NumWithIndex {
        int num;
        int index;

        public NumWithIndex(int num, int index) {
            this.num = num;
            this.index = index;
        }
    }

    static class MergeSort {

        public static List<Integer> sort(NumWithIndex[] nums) {
            List<Integer> ans = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                ans.add(0);
            }
            System.out.println(ans.size());
            sort(nums, 0, nums.length - 1, ans);
            return ans;
        }

        private static void sort(NumWithIndex[] nums, int start, int end, List<Integer> ans) {
            if (start < end) {
                int mid = start + (end - start) / 2;
                sort(nums, start, mid, ans);
                sort(nums, mid + 1, end, ans);
                merge(nums, start, mid, end, ans);
            }
        }

        private static void merge(NumWithIndex[] nums, int start, int mid, int end, List<Integer> ans) {
            NumWithIndex[] copy = new NumWithIndex[nums.length];
            for (int i = start; i <= end; i++) {
                copy[i] = nums[i];
            }

            int low = start;
            int high = mid + 1;
            int currentIdx = low;
            while (low <= mid && high <= end) {
                if (copy[low].num <= copy[high].num) {
                    // how many elements moved from right to left
                    ans.set(copy[low].index, ans.get(copy[low].index) + (high - mid - 1));
                    nums[currentIdx++] = copy[low++];
                } else {
                    nums[currentIdx++] = copy[high++];
                }
            }

            for (int i = low; i <= mid; i++) {
                // how many elements moved from right to left
                ans.set(copy[i].index, ans.get(copy[i].index) + (high - mid - 1));
                nums[currentIdx++] = copy[i];
            }
        }

    }

    /**
     * Use binary search to find the position of nums[i] in sorted array.
     *
     * @param nums
     * @return
     */
    public List<Integer> countSmaller2(int[] nums) {
        Integer[] ans = new Integer[nums.length];
        List<Integer> sorted = new ArrayList<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            int index = findIndex(sorted, nums[i]);
            ans[i] = index;
            sorted.add(index, nums[i]);
        }
        return Arrays.asList(ans);
    }
    private int findIndex(List<Integer> sorted, int target) {
        if (sorted.size() == 0) return 0;
        int start = 0;
        int end = sorted.size() - 1;
        if (sorted.get(end) < target) return end + 1;
        if (sorted.get(start) >= target) return 0;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (sorted.get(mid) < target) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        if (sorted.get(start) >= target) return start;
        return end;
    }

    /**
     * Use BST
     *
     */
    private TreeNode root;
    private void add(int num) {
        if (root == null) {
            root = new TreeNode(num, null, null);
        } else {
            add(root, num);
        }
    }

    private void add(TreeNode node, int num) {
        if (num < node.val) {
            if (node.left == null) {
                node.left = new TreeNode(num, null, null);
            } else {
                add(node.left, num);
            }
        } else if (num > node.val) {
            if (node.right == null) {
                node.right = new TreeNode(num, null, null);
            } else {
                add(node.right, num);
            }
        }
    }

    public static TreeNode insertIntoBST(TreeNode node, int value, int leftSum) {
        if (null == node) return new TreeNode(value);

        if (value <= node.val) {
            node.leftCount++;
            node.left = insertIntoBST(node.left, value, leftSum);

            if (node.left.left == null && node.left.right == null)
                node.left.rightCount = leftSum;
        } else {
            node.right = insertIntoBST(node.right, value, node.leftCount+leftSum);

            if (node.right.right == null && node.right.left == null)
                node.right.rightCount = node.leftCount+node.rightCount;
        }
        return node;
    }

    public void constructBST1(int[] nums) {
        for (int num : nums) {
            add(num);
        }
    }

    public TreeNode constructBST(TreeNode node, int[] nums) {
        if (null == nums) { return null; }
        int len = nums.length;

        for (int i=len-1; i>=0; i--) {
            node = insertIntoBST(node, nums[i], 0);
        }

        return node;
    }

//    public boolean findValue(TreeNode node, int value) {
//        if (node == null) return false;
//        return node.val == value || findValue(node.left, value) || findValue(node.right, value);
//    }
    public TreeNode findValue(TreeNode node, int value) {
        // Finds the node that contains the value and returns a reference to the node.
        // Returns null if value does not exist in the tree.
        if (node == null) return null;
        if (node.val == value) {
            return node;
        } else {
            TreeNode left = findValue(node.left, value);
            if (left != null) {
                return left;
            }
            TreeNode right = findValue(node.right, value);
            return right;
        }
    }
    public List<Integer> countSmaller3(int[] nums) {

        if (nums == null) return new ArrayList<Integer>();

        Integer[] ans = new Integer[nums.length];
        TreeNode node = new TreeNode(nums[0]);
        node = constructBST(node, nums);

        for (int i=0; i<nums.length; i++) {
            TreeNode nd = findValue(node, nums[i]);
            if (nd != null)
                ans[i] = nd.rightCount;
        }
        return Arrays.asList(ans);
    }
}

class TreeNode {

    public int val;
    public TreeNode left;
    public TreeNode right;
    public boolean visited;
    public int leftCount; //every tree node has leftCount (size of left subtree + 1) and rightCount (number of smaller elements on the right side.)
    public int rightCount;

    public TreeNode(int x) {
        this(x, null, null);
    }

    public TreeNode(int x, TreeNode l, TreeNode r) {
        val = x;
        left = l;
        right = r;
        leftCount = 1;
        rightCount = 0;

    }

    private boolean isLeaf() {
        return left==null && right==null;
    }
}
