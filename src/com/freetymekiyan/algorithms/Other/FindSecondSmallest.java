package com.freetymekiyan.algorithms.Other;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class FindSecondSmallest {
    /**
     * Here is the method to find Second smallest. Basic idea is to return the max when search size
     * is <=2. For rest of the search return min.
     *
     * @param A
     * @param start
     * @param end
     * @return
     */
    public static int findSecondSmallest(int[] A, int start, int end){
        if(end == start){
            return A[start];
        }else if(start == end-1){
            return Math.max(A[start], A[end]);
        }else{
            int mid = start + (end-start)/2;
            int min1 = findSecondSmallest(A, start, mid);
            int min2 = findSecondSmallest(A, mid+1, end);

            return Math.min(min1, min2);
        }
    }

    /**
     * BinarySearch to find the smallest. It can be extended to find smallest and second smallest
     * (Tournament like method).
     *
     * @param A
     * @param start
     * @param end
     * @return
     */
    public int findSmallest(int[] A, int start, int end){
        if(end == start){
            return A[start];
        }else if(start == end-1){
            return Math.min(A[start], A[end]);
        }else{
            int mid = start + (end-start)/2;
            int min1 = findSmallest(A, start, mid);
            int min2 = findSmallest(A, mid+1, end);

            return Math.min(min1, min2);
        }
    }

    /* Function to print first smallest and second smallest elements */
    static void print2Smallest(int arr[]) {
        int first, second, arr_size = arr.length;

        /* There should be at least two elements */
        if (arr_size < 2)
        {
            System.out.println(" Invalid Input ");
            return;
        }

        first = second = Integer.MAX_VALUE;
        for (int i = 0; i < arr_size ; i ++) {
            /* If current element is smaller than first
              then update both first and second */
            if (arr[i] < first) {
                second = first;
                first = arr[i];
            }
            /* If arr[i] is in between first and second
               then update second  */
            else if (arr[i] < second && arr[i] != first)
                second = arr[i];
        }
        if (second == Integer.MAX_VALUE)
            System.out.println("There is no second" +
                    "smallest element");
        else
            System.out.println("The smallest element is " +
                    first + " and second Smallest" +
                    " element is " + second);
    }



    public static Integer findSecondLargest(List<Integer> list) {
        if (list == null) {
            return null;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        List<List<Integer>> structure = buildUpStructure(list);
        System.out.println(structure);
        return secondLargest(structure);

    }

    public static List<List<Integer>> buildUpStructure(List<Integer> list) {
        List<List<Integer>> newList = new ArrayList<List<Integer>>();
        List<Integer> tmpList = new ArrayList<Integer>(list);
        newList.add(tmpList);
        int n = list.size();
        while (n>1) {
            tmpList = new ArrayList<Integer>();
            for (int i = 0; i<n; i=i+2) {
                Integer i1 = list.get(i);
                Integer i2 = list.get(i+1);
                tmpList.add(Math.max(i1, i2));
            }
            n/= 2;
            newList.add(tmpList);
            list = tmpList;
        }
        return newList;
    }

    public static Integer secondLargest(List<List<Integer>> structure) {
        int n = structure.size();
        int rootIndex = 0;
        Integer largest = structure.get(n-1).get(rootIndex);
        List<Integer> tmpList = structure.get(n-2);
        Integer secondLargest = Integer.MIN_VALUE;
        Integer leftAdjacent = -1;
        Integer rightAdjacent = -1;
        for (int i = n-2; i>=0; i--) {
            rootIndex*=2;
            tmpList = structure.get(i);
            leftAdjacent = tmpList.get(rootIndex);
            rightAdjacent = tmpList.get(rootIndex+1);
            if (leftAdjacent.equals(largest)) {
                if (rightAdjacent > secondLargest) {
                    secondLargest = rightAdjacent;
                }
            }
            if (rightAdjacent.equals(largest)) {
                if (leftAdjacent > secondLargest) {
                    secondLargest = leftAdjacent;
                }
                rootIndex=rootIndex+1;
            }
        }

        return secondLargest;
    }
}
