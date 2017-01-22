import java.util.ArrayList;
import java.util.List;

/**
 Find the element before which all the elements are smaller than it, and after which all are greater
 Given an array, find an element before which all elements are smaller than it, and after which all are greater than it.
 Return index of the element if there is such an element, otherwise return -1.

 Examples:

 Input:   arr[] = {5, 1, 4, 3, 6, 8, 10, 7, 9};
 Output:  Index of element is 4
 All elements on left of arr[4] are smaller than it
 and all elements on right are greater.

 Input:   arr[] = {5, 1, 4, 4};
 Output:  Index of element is -1
 Expected time complexity is O(n).

 Tag: TripAdvisor

 */
public class RightLocationInArray {
/**
 * A Simple Solution is to consider every element one by one. For every element, compare it with all elements on left and
 * all elements on right. Time complexity of this solution is O(n^2).

 An Efficient Solution can solve this problem in O(n) time using O(n) extra space. Below is detailed solution.

 1) Create two arrays leftMax[] and rightMin[].
 2) Traverse input array from left to right and fill leftMax[] such that leftMax[i] contains maximum element from 0 to i-1 in input array.
 3) Traverse input array from right to left and fill rightMin[] such that rightMin[i] contains minimum element from to n-1 to i+1 in input array.
 4) Traverse input array. For every element arr[i], check if arr[i] is greater than leftMax[i] and smaller than rightMin[i]. If yes, return i.

 Further Optimization to above approach is to use only one extra array and traverse input array only twice.
 First traversal is same as above and fills leftMax[].
 Next traversal traverses from right and keeps track of minimum. The second traversal also finds the required element.
 */
    public int findElement(int[] arr) {
        // leftMax[i] stores maximum of arr[0..i-1]
        int[] leftMax = new int[arr.length];
        leftMax[0] = Integer.MIN_VALUE;

        // Fill leftMax[1..n-1]
        for (int i = 1; i < leftMax.length; i++)
            leftMax[i] = Math.max(leftMax[i - 1], arr[i - 1]);

        // Initialize minimum from right
        int rightMin = Integer.MIN_VALUE;

        // Traverse array from right
        for (int i = arr.length - 1; i >= 0; i--) {
            // Check if we found a required element
            if (leftMax[i] < arr[i] && rightMin > arr[i])
                return i;

            // Update right minimum
            rightMin = Math.min(rightMin, arr[i]);
        }

        // If there was no element matching criteria
        return -1;
    }

    public List<Integer> findElements(int[] arr) {
        List<Integer> res = new ArrayList<>();

        // leftMax[i] stores maximum of arr[0..i]
        int[] leftMax = new int[arr.length];

        // Fill leftMax
        leftMax[0] = arr[0];
        for (int i = 1; i < leftMax.length; i++)
            leftMax[i] = Math.max(leftMax[i - 1], arr[i]);

        // rightMin stores minimum from right down to current index i
        int rightMin = arr[arr.length - 1];

        // Traverse array from right
        for (int i = arr.length - 1; i >= 0; i--) {
            // Update right minimum
            rightMin = Math.min(rightMin, arr[i]);

            // Check if we found a required element
            if (leftMax[i] <= arr[i] && rightMin >= arr[i])
                res.add(i);
        }
        return res;
    }
}
