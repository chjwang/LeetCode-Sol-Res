package com.freetymekiyan.algorithms.Other;

import java.util.HashMap;

/**
 generate random numbers within a particular range where each number has a certain probability to
 occur or not

 http://stackoverflow.com/questions/17250568/randomly-choosing-from-a-list-with-weighted-probabilities/17253335#17253335
 */
public class RandomNumberGeneratorBasedOnProbability {
    private HashMap<Integer, Double> distributionMap;
    private double distSum; // running total of all frequency probablity distributions

    public RandomNumberGeneratorBasedOnProbability() {
        distributionMap = new HashMap<>();
    }

    /*
    The idea:

Iterate through all the elements and set the value of each element as the cumulative frequency thus far.
Generate a random number between 1 and the sum of all frequencies
Do a binary search on the values for this number (finding the first value greater than or equal to the number).
Example:

Element    A B C D
Frequency  1 4 3 2
Cumulative 1 5 8 10
Generate a random number in the range 1-10 (1+4+3+2 = 10, the same as the last value in the cumulative list),
do a binary search, which will return values as follows:

Number   Element returned
1        A
2        B
3        B
4        B
5        B
6        C
7        C
8        C
9        D
10       D
     */
    /**
     *
     * @param value
     * @param distribution frequency distribution percentage, 0.01 = 1%
     */
    public void addNumber(int value, double distribution) {
        Double preV = this.distributionMap.put(value, distribution);
        if (preV != null)
            distSum -= preV;
        distSum += distribution;
    }

    public int getDistributedRandomNumber() {
        double rand = Math.random(); // random between 0 - 1
        double ratio = 1.0f / distSum;
        double cumulativeDistributionsSoFar = 0;
        for (Integer i : distributionMap.keySet()) {
            cumulativeDistributionsSoFar += distributionMap.get(i);
            if (rand / ratio <= cumulativeDistributionsSoFar) {
                return i;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        RandomNumberGeneratorBasedOnProbability drng = new RandomNumberGeneratorBasedOnProbability();
        drng.addNumber(1, 0.2d);
        drng.addNumber(2, 0.3d);
        drng.addNumber(3, 0.5d);

        int testCount = 1000000;

        HashMap<Integer, Double> testMap = new HashMap<>();

        for (int i = 0; i < testCount; i++) {
            int random = drng.getDistributedRandomNumber();
            testMap.put(random,
                    (testMap.get(random) == null) ? (1d / testCount) : testMap.get(random) + 1d / testCount);
        }

        System.out.println(testMap.toString());
    }
}
