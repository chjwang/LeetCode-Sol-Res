package com.freetymekiyan.algorithms.Other;

import com.freetymekiyan.algorithms.level.Medium.FlattenNestedListIterator.NestedInteger;

import java.util.List;

/**
 * Given a nested list of integers, return the sum of all integers in the list weighted by their
 * depth.
 */
public class NestedListWeightSum {

    public int depthSum(List<NestedInteger> nestedList) {
        return helper(nestedList, 1);
    }

    private int helper(List<NestedInteger> list, int depth) {
        int ret = 0;
        for (NestedInteger e : list) {
            ret += e.isInteger() ? e.getInteger() * depth : helper(e.getList(), depth + 1);
        }
        return ret;
    }

}
