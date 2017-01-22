package com.freetymekiyan.algorithms.level.Medium;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * There are a total of n courses you have to take, labeled from 0 to n - 1. Some courses may have
 * prerequisites, for example to take course 0 you have to first take course 1, which is expressed
 * as a pair: [0,1]
 *
 * Given the total number of courses and a list of prerequisite pairs, is it possible for you to
 * finish all courses?
 */
public class CourseSchedule {

    public static void main(String[] args) {
        int[][] pre = {{1, 0}, {2, 0}, {0, 2}};
        System.out.println(new CourseSchedule().canFinish(3, pre));
    }

    /**
     * DFS Topological Sorting solution
     * L ← Empty list that will contain the sorted nodes
     * while there are unmarked nodes do
     * 		select an unmarked node n
     * 		visit(n)
     * function visit(node n)
     * 		if n has a temporary mark then stop (not a DAG)
     * 		if n is not marked (i.e. has not been visited yet) then
     * 			mark n temporarily
     * 			for each node m with an edge from n to m do
     * 				visit(m)
     * 			mark n permanently
     * 			unmark n temporarily
     * 			addPrereq n to head of L
     */
    public boolean canFinish(int numCourses, int[][] prerequisites){
        Set[] isPre = new Set[numCourses];
        for(int i = 0; i < isPre.length; i++) isPre[i] = new HashSet();
        boolean[] t_marked = new boolean[numCourses];
        boolean[] marked = new boolean[numCourses];
        for(int i = 0; i < prerequisites.length; i++){
            int course = prerequisites[i][0];
            int pre = prerequisites[i][1];
            isPre[pre].add(course);
        }
        Queue<Integer> queue = new LinkedList<Integer>();
        for(int i = 0; i < numCourses; i++) queue.add(i);

        while(!queue.isEmpty()){
            int course = queue.poll();
            if(marked[course]) continue;
            if(!visit(course, isPre, t_marked, marked)) return false;
        }
        return true;
    }
    private boolean visit(int course, Set[] isPre, boolean[] t_marked, boolean[] marked){
        if(t_marked[course]) return false;
        t_marked[course] = true;
        Set<Integer> set = isPre[course];
        for(Integer i : set){
            if(!visit(i, isPre, t_marked, marked)) return false;
        }
        marked[course] = true;
        t_marked[course] = false;

        return true;
    }


    /**
     * A topological ordering is possible
     * if and only if the graph has no directed cycles
     *
     * BFS: Topological Sorting
     * Sorting will terminate when there are no more nodes with no incoming
     * edges. If G is not a DAG, the number of sorted nodes does not equal
     * to total nodes(A cycle will remain)
     */
    public boolean canFinish2(int numCourses, int[][] prerequisites) {

        //isPre[i][j] is true if course i is prerequisite of course j
        boolean[][] isPre = new boolean[numCourses][numCourses];
        //record how many incoming each node currently have
        int[] incomings = new int[numCourses];
        for(int i = 0; i < prerequisites.length; i++){
            int course = prerequisites[i][0];
            int pre = prerequisites[i][1];
            if(!isPre[pre][course]){
                incomings[course]++;
            }
            isPre[pre][course] = true;
        }

        //count the sorted nodes
        int sort_count = 0;
        Queue<Integer> queue = new LinkedList();
        //put nodes with no incoming edges in to queue
        for(int course = 0; course < numCourses; course++){
            if(incomings[course] == 0) queue.add(course);
        }
        while(!queue.isEmpty()){
            //fetch a node course from the queue
            int course = queue.poll();
            sort_count++;

            //for each node that course links to, reduce the
            //number of their incoming edges.if the node has
            //no more incoming edges, put it to the tail of
            //the queue
            for(int i = 0; i < numCourses; i++){
                if(isPre[course][i]){
                    incomings[i]--;
                    if(incomings[i] == 0){
                        queue.add(i);
                    }
                }
            }
        }
        return sort_count == numCourses;
    }


}