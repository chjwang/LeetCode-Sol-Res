package com.freetymekiyan.algorithms.Other;

/**
 * Count all possible walks from a source to a destination with exactly k edges
 *
 Given a directed graph and two vertices ‘u’ and ‘v’ in it, count all possible walks from ‘u’ to ‘v’
 with exactly k edges on the walk.

 The graph is given as adjacency matrix representation where value of graph[i][j] as 1 indicates that
 there is an edge from vertex i to vertex j and a value 0 indicates no edge from i to j.

 */
class KPaths {
    static final int V = 4; //Number of vertices

    // Driver method
    public static void main(String[] args) throws java.lang.Exception {
        /* Let us create the graph shown in above diagram*/
        int graph[][] = new int[][]{{0, 1, 1, 1},
                {0, 0, 0, 1},
                {0, 0, 0, 1},
                {0, 0, 0, 0}
        };
        int u = 0, v = 3, k = 2;
        KPaths p = new KPaths();
        System.out.println(p.countwalks(graph, u, v, k));
    }

    /** A naive recursive function to count walks from u
     to v with k edges

     The worst case time complexity of the above function is O(V^k) where V is the number of vertices in the given graph.
     */
    int countwalks_recursive(int graph[][], int u, int v, int k) {
        // Base cases
        if (k == 0 && u == v) return 1;
        if (k == 1 && graph[u][v] == 1) return 1;
        if (k <= 0) return 0;

        // Initialize result
        int count = 0;

        // Go to all adjacents of u and recur
        for (int i = 0; i < V; i++)
            if (graph[u][i] == 1)  // Check if is adjacent of u
                count += countwalks_recursive(graph, i, v, k - 1);

        return count;
    }

    /**
     * We can optimize the above solution using Dynamic Programming.
     *
     * The idea is to build a 3D table where first dimension is source, second dimension is destination,
     * third dimension is number of edges from source to destination, and the value is count of walks.
     * Like other Dynamic Programming problems, we fill the 3D table in bottom up manner.
     *
     * Time complexity of the above function is O(V^3 K).
     *
     * We can also use Divide and Conquer to solve the above problem in O(V^3 Logk) time.
     * The count of walks of length k from u to v is the [u][v]’th entry in (graph[V][V])k.
     * We can calculate power of by doing O(Logk) multiplication by using the divide and conquer technique
     * to calculate power. A multiplication between two matrices of size V x V takes O(V^3) time.
     * Therefore overall time complexity of this method is O(V^3 Logk).
     *
     * @param graph
     * @param u source
     * @param v destination
     * @param k number of steps
     * @return number of possible paths
     */
    int countwalks(int graph[][], int u, int v, int k) {
        // Table to be filled up using DP. The value count[i][j][e]
        // will/ store count of possible walks from i to j with
        // exactly k edges
        int count[][][] = new int[V][V][k + 1];

        // Loop for number of edges from 0 to k
        for (int e = 0; e <= k; e++)
            for (int i = 0; i < V; i++)  // for source
                for (int j = 0; j < V; j++) // for destination
                {
                    // initialize value
                    count[i][j][e] = 0;

                    // from base cases
                    if (e == 0 && i == j)
                        count[i][j][e] = 1;
                    if (e == 1 && graph[i][j] != 0)
                        count[i][j][e] = 1;

                    // go to adjacent only when number of edges
                    // is more than 1
                    if (e > 1) {
                        for (int a = 0; a < V; a++) // adjacent of i
                            if (graph[i][a] != 0)
                                count[i][j][e] += count[a][j][e - 1];
                    }
                }
        return count[u][v][k];
    }

    /**
     给定矩阵起点和终点，求走K步有多少条路
     走K步从A点到B点的走法有多少种,然后,可以走对角线或是上下左右移动

     O(M*N*K)
     */
    public static int pathNum(Point A, Point B, int size, int K) {
        int[][][] dp = new int[size][size][2];
        // no need for the full length of K for the 3rd dimension. Just need to keep previous and current.

        dp[A.x][A.y][0] = 1;
        for (int i = 1; i <= K; i++) {
            for (int m = 0; m < size; m++) {
                for (int n = 0; n < size; n++)
                    dp[m][n][i % 2] = sumNeighbor(dp, i, m, n, size);
            }
        }
        return dp[B.x][B.y][K % 2];
    }

    public static int sumNeighbor(int[][][] dp, int i, int x, int y, int size) {
        int[] dx = {1, 1, 1, -1, -1, -1, 0, 0};
        int[] dy = {1, -1, 0, 1, -1, 0, 1, -1};
        int sum = 0;
        for (int d = 0; d < 8; d++) {
            int newX = x + dx[d];
            int newY = y + dy[d];

            if (newX >= 0 && newX < size && newY >= 0 && newY < size)
                sum += dp[newX][newY][(i - 1) % 2];
        }
        return sum;
    }

    static class Point {
        int x;
        int y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}