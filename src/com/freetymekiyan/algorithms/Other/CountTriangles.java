package com.freetymekiyan.algorithms.Other;

// Java program to count number of triangles that can be
// formed from given array
import java.io.*;
import java.util.*;

/**
 * Time Complexity: O(n^2).
 *
 * The time complexity looks more because of 3 nested loops. If we take a closer look at the algorithm,
 * we observe that k is initialized only once in the outermost loop. The innermost loop executes at
 * most O(n) time for every iteration of outer most loop, because k starts from i+2 and goes up to n
 * for all values of j. Therefore, the time complexity is O(n^2).
 *
 * Finding complexity by counting nested loops is completely wrong sometimes. Here I try to explain
 * it as simple as I could.
 *
 * Let's fix i variable. Then for that i we must iterate j from i to n (it means O(n) operation)
 * and internal while loop iterate k from i to n (it also means O(n) operation).
 * Note: I don't start while loop from the beginning for each j. We also need to do it for each i
 * from 0 to n. So it gives us n * (O(n) + O(n)) = O(n^2).
 *
 *  Check the way k is being moved. It's not reinitialized in the while loop every time, that's how
 *  the algorithm achieved O(n^2) complexity
 *
 *
 * Source: http://stackoverflow.com/questions/8110538/total-number-of-possible-triangles-from-n-numbers
 */
public class CountTriangles
{
    // Function to count all possible triangles with arr[]
    // elements
    public static int findNumberOfTriangles(int arr[])
    {
        int n = arr.length;
        // Sort the array elements in non-decreasing order
        Arrays.sort(arr);

        // Initialize count of triangles
        int count = 0;

        // Fix the first element.
        // We need to run till n-3 as the other two elements are selected from arr[i+1...n-1]
        for (int i = 0; i < n-2; ++i)
        {
            // Initialize index of the rightmost third element
            int k = i + 2;

            // Fix the second element
            for (int j = i+1; j < n; ++j)
            {
                /* Find the rightmost element which is smaller than the sum of two fixed elements.

                   The important thing to note here is, we use the previous value of k.

                   If value of arr[i] + arr[j-1] was greater than arr[k], then arr[i] + arr[j] must
                   be greater than k, because the array is sorted.
                   */
                while (k < n && arr[i] + arr[j] > arr[k])
                    ++k;

               /* Total number of possible triangles that can be formed with the two fixed elements
                  is k - j - 1.

                  The two fixed elements are arr[i] and arr[j].  All elements between arr[j+1] to
                  arr[k-1] can form a triangle with arr[i] and arr[j].

                  One is subtracted from k because k is incremented one extra in above while loop.

                  k will always be greater than j. If j becomes equal to k, then above loop will
                  increment k, because arr[k] + arr[i] is always/ greater than  arr[k]
                  */
                count += k - j - 1;
            }
        }
        return count;
    }

    /**
     * Allow i, j, k to be the same number from the array
     * @param arr
     * @return
     */
    public static int findNumberOfTrianglesAllowDuplicates(int arr[])
    {
        int answer = 0;
        int n = arr.length;
        Arrays.sort(arr);

        for (int i = 0; i < n; ++i) {
            int k = i;

            for (int j = i; j < n; ++j) {
                while (n > k && arr[i] + arr[j] > arr[k])
                    ++k;

                answer += k - j;
            }
        }
        return answer;
    }

    public static int findNumberOfTriangles2(int arr[]) {
        int answer = 0;
        int n = arr.length;
        Arrays.sort(arr);

        for(int k=n-1; k>=2; k--){
            int i=0, j=k-1;

            //find first (i,j) pair for which triangle is possible with the third side being of length k
            while (i<j) {
                if (arr[i] + arr[j] > arr[k]) {
                    //since triangle is possible for all (x,j) such that i<=x<=j-1.
                    answer += j-i;
                    j--;
                }
                else
                    i++;
            }
        }
        return answer;
    }
    public static void main (String[] args)
    {
        int arr[] = {10, 21, 22, 100, 101, 200, 300};
        System.out.println("Total number of triangles is " +
                findNumberOfTriangles(arr));
        System.out.println("Total number of triangles2 is " +
                findNumberOfTriangles2(arr));
        arr = new int[] {1, 2, 3};
        System.out.println("Total number of triangles (allowing duplicates) is " +
                findNumberOfTrianglesAllowDuplicates(arr));
    }
}