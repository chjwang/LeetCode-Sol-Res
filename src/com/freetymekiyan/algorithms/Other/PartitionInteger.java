package com.freetymekiyan.algorithms.Other;

/**
 Print all unique integer partitions given an integer as input.
 Integer partition is a way of writing n as a sum of positive integers.
 for ex: Input=4 then output should be Output=

 1 1 1 1
 1 1 2
 2 2
 1 3
 4
 */
public class PartitionInteger {

    /**
     * Print all integer partitions of target, followed by suffix, such that each value in the
     * partition is at most maxValue
     *
     * @param target remaining to be partitioned
     * @param maxValue
     * @param suffix suffix string for already partitioned
     */
    private void printPartitions(int target, int maxValue, String suffix) {
        if (target == 0)
            System.out.println(suffix);
        else {
            if (maxValue > 1)
                printPartitions(target, maxValue-1, suffix);
            if (maxValue <= target)
                printPartitions(target-maxValue, maxValue, maxValue + " " + suffix);
        }
    }

    public void partions(int target) {
        printPartitions(target, target, "");
    }

    /**
     * This is loosely derived from Heuster's approach.

     Firstly, note that the last numbers of the output are 1,2,2,3,4. If the last number is 2,
     the 2nd last numbers are 1,2. This tells me that it might be a good idea have a recursive
     function with a for-loop generating the string from the back.

     The code itself is pretty straight-forward:

     Loop from 1 to target, prepending the variable to the suffix, subtracting it from target and recursing.
     Also note that each generated string is sorted (which implicitly avoids duplication of output).
     We get it sorted by simply passing in the last-generated number and looping no further than that number.

     * @param target remaining to be added
     * @param max
     * @param suffix already added numbers
     */
    private void printPartitions1(int target, int max, String suffix) {
        if (target == 0)
            System.out.println(suffix);
        else {
            for (int i = 1; i <= max && i <= target; i++)
                printPartitions1(target - i, i, i + " " + suffix);
        }
    }

    public void printPartitions1(int target) {
        printPartitions1(target, target, "");
    }

    /**
     * Simpler problem:
     * The partition of an integer is a way of writing it as a sum of positive integers.
     * For example, the partitions of the number 5 are:
     5
     4+1
     3+2
     3+1+1
     2+2+1
     2+1+1+1
     1+1+1+1+1
     Notice that changing the order of the summands will not create a different partition.

     Now how do we find the number of different partitions for any integer N?

     Suppose we want to find all the partitions of the number 5. We could split all the solutions
     into two groups: a group which uses the number 5 itself at least once, and a group that doesn’t
     use it. The group that uses the number 5 has only one solution: five itself. The group that
     doesn’t use the number five is basically the problem of finding all the ways to come up with 5
     using the sub-set 1,2,3 and 4.

     Now repeat the process. We can again split the solutions to our second problem into two groups:
     a group with all the solutions that contain the number 4, and a group that doesn’t.

     There you go, we can apply this split recursively and we’ll break the problem down into many
     sub-problems.

     Or, this problem can be thought as coin change problem with denominations = [1,2…n-1] and
     target coin size to reach as n.

     * @param sum the sum we are trying to reach
     * @param largestNumber the largest number on the sub-set we have available to reach that sum
     * @return
     *
     */
    int partition(int sum, int largestNumber){
     /*
     When sum equals zero it means we just reached the sum exactly, so the function returns 1
     (i.e. it just found one way to do the sum).

     If sum goes below zero it means the last available number was larger than what we needed, so
     the function returns 0.

     Similarly if the largest number available is zero it means we can’t reach the sum, so the
     function returns 0.
     */
        if (largestNumber==0)
            return 0;

        if (sum==0)
            return 1;

        if (sum<0)
            return 0;

        return partition(sum, largestNumber-1)
                + partition(sum-largestNumber, largestNumber);
    }

    int[][] table = new int[100][100];

    int partitionDP(int sum, int largestNumber){
        if (largestNumber==0)
            return 0;
        if (sum==0)
            return 1;
        if (sum<0)
            return 0;

        if (table[sum][largestNumber]!=0)
            return table[sum][largestNumber];

        table[sum][largestNumber]=partition(sum, largestNumber-1)
                + partition(sum-largestNumber, largestNumber);
        return table[sum][largestNumber];

    }

}
