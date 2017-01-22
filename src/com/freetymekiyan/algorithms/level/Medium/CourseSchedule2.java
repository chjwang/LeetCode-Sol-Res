package com.freetymekiyan.algorithms.level.Medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * There are a total of n courses you have to take, labeled from 0 to n - 1.
 * <p>
 * Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is
 * expressed as a pair: [0,1]
 * <p>
 * Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should take
 * to finish all courses.
 * <p>
 * There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all
 * courses, return an empty array.
 * <p>
 * For example:
 * <p>
 * 2, [[1,0]]
 * There are a total of 2 courses to take. To take course 1 you should have finished course 0. So the correct course
 * order is [0,1]
 * <p>
 * 4, [[1,0],[2,0],[3,1],[3,2]]
 * There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2. Both courses
 * 1 and 2 should be taken after you finished course 0. So one correct course order is [0,1,2,3]. Another correct
 * ordering is[0,2,1,3].
 * <p>
 * Note:
 * The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a
 * graph is represented.
 * <p>
 * Hints:
 * 1. This problem is equivalent to finding the topological order in a directed graph. If a cycle exists, no
 * topological ordering exists and therefore it will be impossible to take all courses.
 * 2. Topological Sort via DFS - A great video tutorial (21 minutes) on Coursera explaining the basic concepts of
 * Topological Sort.
 * 3. Topological sort could also be done via BFS.
 * <p>
 * Tags: Depth-first Search, Breadth-first Search, GraphPrintAllPaths, Topological Sort
 * Similar Problems: (M) Course Schedule, (H) Alien Dictionary, (M) Minimum Height Trees
 */
public class CourseSchedule2 {

    /** Label the topological sort result */
    private int currentLabel;

    public int[] topSort(int numCourses, int[][] prerequisites) {
        int[] res = new int[numCourses];
        Course[] courses = new Course[numCourses];
        for (int i = 0; i < numCourses; i++) {
            courses[i] = new Course(i);
        }
        for (int[] prereq : prerequisites) {
            courses[prereq[0]].addPrereq(courses[prereq[1]]);
        }

        for (int i = 0; i < numCourses; i++) {
            if (! isAcyclic(courses[i], res)) {
                return new int[0];
            }
        }
        return res;
    }

    /**
     * Check whether a node is acyclic or not starting DFS from course
     * If no, return true and this graph is not a DAG
     * If yes, go ahead and do the DFS topological sort
     */
    private boolean isAcyclic(Course course, int[] result) {
        if (course.validated) return true;
        if (course.visited) return false;

        course.visited = true;
        for (Course c : course.prereq) {
            if (! isAcyclic(c, result)) { // DFS
                return false;
            }
        }
        course.validated = true; // Mark as validated
        result[currentLabel++] = course.number; // Add course number to output
        return true;
    }

    /**
     * Course object, acted as a DAG node
     */
    class Course {
        /** Keep track of whether we already visited this node */
        boolean visited = false;
        /** Keep track of whether we already validated it's acyclic starting DFS from this node */
        boolean validated = false;
        /** Course number */
        int number;
        /** Prerequisite courses */
        List<Course> prereq = new ArrayList<>();

        public Course(int i) {
            number = i;
        }

        public void addPrereq(Course c) {
            prereq.add(c);
        }
    }

    // DFS
    public int[] findOrder1(int numCourses, int[][] prerequisites) {
        if (numCourses <= 0) return new int[0];

        currentLabel = numCourses - 1;

        int[] result = new int[numCourses];

        // No prerequisites
        if (prerequisites == null || prerequisites.length == 0) {
            for (int i = 0; i < numCourses; i++) {
                result[i] = i;
            }

            return result;
        }

        // Convert the edge list to adj. list
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        for (int[] edge : prerequisites) {
            if (adjList.containsKey(edge[1])) {
                List<Integer> neighbors = adjList.get(edge[1]);
                neighbors.add(edge[0]);
                adjList.put(edge[1], neighbors);
            } else {
                List<Integer> neighbors = new ArrayList<Integer>();
                neighbors.add(edge[0]);
                adjList.put(edge[1], neighbors);
            }
        }

        int[] visited = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (! toplogicalSorting(i, visited, adjList, result)) {
                return new int[0];
            }
        }

        return result;
    }

    /**
     *
     * @param vertexId
     * @param visited
     * @param adjList
     * @param result
     * @return false if no topological order exists - cycle exists
     */
    private boolean toplogicalSorting(int vertexId, int[] visited,
                                      Map<Integer, List<Integer>> adjList,
                                      int[] result) {
        // on stack
        if (visited[vertexId] == -1) return false;

        // Has been added into the list (means no cycle doing DFS from this node)
        if (visited[vertexId] == 1) return true;

        visited[vertexId] = -1;
        List<Integer> neighbors = adjList.get(vertexId);
        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (! toplogicalSorting(neighbor, visited, adjList, result))
                    return false;
            }
        }

        result[currentLabel--] = vertexId; // add node to result list
        visited[vertexId] = 1;

        return true;
    }

    // BFS
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adjLists = new ArrayList<List<Integer>>();
        for (int i = 0; i < numCourses; i++) {
            adjLists.add(new ArrayList<Integer>());
        }

        int[] indegrees = new int[numCourses];
        for (int i = 0; i < prerequisites.length; i++) {
            adjLists.get(prerequisites[i][1]).add(prerequisites[i][0]);
            indegrees[prerequisites[i][0]]++;
        }

        Queue<Integer> q = new LinkedList<Integer>(); // storing vertices whose indegree==0
        for (int i = 0; i < numCourses; i++) {
            if (indegrees[i] == 0) q.offer(i);
        }

        int count = numCourses;
        while (!q.isEmpty()) {
            // remove a node from q, remove all edges starting from the node
            int cur = q.poll();
            for (int i : adjLists.get(cur)) {
                indegrees[i]--;
                if (indegrees[i] == 0) q.offer(i);
            }
            count--;
        }

        return count == 0; // all nodes have been removed
    }

    // BFS
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adjLists = new ArrayList<List<Integer>>();
        for (int i = 0; i < numCourses; i++) adjLists.add(new ArrayList<Integer>());

        int[] indegrees = new int[numCourses];
        for (int i = 0; i < prerequisites.length; i++) {
            adjLists.get(prerequisites[i][1]).add(prerequisites[i][0]);
            indegrees[prerequisites[i][0]]++;
        }

        Queue<Integer> q = new LinkedList<Integer>();
        for (int i = 0; i < numCourses; i++) if (indegrees[i] == 0) q.offer(i);

        int[] res = new int[numCourses];
        int count = 0;
        while (!q.isEmpty()) {
            int cur = q.poll();
            for (int x : adjLists.get(cur)) {
                indegrees[x]--; if (indegrees[x] == 0) q.offer(x);
            }
            res[count++] = cur;
        }

        if (count == numCourses) return res;
        return new int[0];
    }
}
