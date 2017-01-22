package com.freetymekiyan.algorithms.Other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
Compute intersection and union of lists.
 */
public class ListUnionIntersection {

    public static void main(String... args) throws Exception {

        List<String> list1 = new ArrayList<String>(Arrays.asList("A", "B", "C"));
        List<String> list2 = new ArrayList<String>(Arrays.asList("B", "C", "D", "E", "F"));

        System.out.println(new ListUnionIntersection().intersection(list1, list2));
        System.out.println(new ListUnionIntersection().union(list1, list2));
    }

    public <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> set = new HashSet<T>();

        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<T>(set);
    }

    /**
     * The solution marked is not efficient. It has a O(n^2) time complexity.
     *
     * O(n^2)
     * @param list1
     * @param list2
     * @param <T>
     * @return
     */
    public <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }

    public <T> List<T> union2(List<T> list1, List<T> list2) {
        List<T> union = Stream.concat(list1.stream(), list2.stream()).distinct().collect(Collectors.toList());
        return union;
    }

    public <T> List<T> intersection2(List<T> list1, List<T> list2) {
        List<T> intersect = list1.stream().filter(list2::contains).collect(Collectors.toList());
        return intersect;
    }

    /**
     * What we can do is to sort both lists, and the execute an intersection algorithm as the one below.
     * This one has a complexity of O(n log n + n) which is in O(n log n).
     * The union is done in a similar manner.
     *
     * @param f
     * @param s
     * @return
     */
    private  static ArrayList<Integer> interesect3(ArrayList<Integer> f, ArrayList<Integer> s) {
        ArrayList<Integer> res = new ArrayList<>();

        int i = 0, j = 0;
        while (i != f.size() && j != s.size()) {

            if (f.get(i) < s.get(j)) {
                i ++;
            } else if (f.get(i) > s.get(j)) {
                j ++;
            } else {
                res.add(f.get(i));
                i ++;
                j ++;
            }
        }


        return res;
    }
    private  static ArrayList<Integer> union3(ArrayList<Integer> f, ArrayList<Integer> s) {
        ArrayList<Integer> res = new ArrayList<>();

        int i = 0, j = 0;
        while (i != f.size() && j != s.size()) {

            if (f.get(i) < s.get(j)) {
                res.add(f.get(i));
                i ++;
            } else if (f.get(i) > s.get(j)) {
                res.add(s.get(j));
                j ++;
            } else {
                if (!f.get(i).equals(f.get(i-1)))
                    res.add(f.get(i));
                i ++;
                j ++;
            }
        }


        return res;
    }
}