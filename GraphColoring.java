public class GraphColoring {
    final int V;  // Number of vertices

    public GraphColoring(int vertices) {
        this.V = vertices;
    }

    // Function to check if color assignment is safe for vertex v
    boolean isSafe(int v, int[][] graph, int[] color, int c) {
        for (int i = 0; i < V; i++)
            if (graph[v][i] == 1 && color[i] == c)
                return false;
        return true;
    }

    // Utility function to solve the problem recursively
    boolean graphColoringUtil(int[][] graph, int m, int[] color, int v) {
        if (v == V)
            return true;

        for (int c = 1; c <= m; c++) {
            if (isSafe(v, graph, color, c)) {
                color[v] = c;

                if (graphColoringUtil(graph, m, color, v + 1))
                    return true;

                color[v] = 0; // Backtrack
            }
        }

        return false;
    }

    // Main function to solve the graph coloring problem
    boolean graphColoring(int[][] graph, int m) {
        int[] color = new int[V];

        if (!graphColoringUtil(graph, m, color, 0)) {
            System.out.println("Solution does not exist.");
            return false;
        }

        printSolution(color);
        return true;
    }

    // Function to print the solution
    void printSolution(int[] color) {
        System.out.println("Solution Exists. Assigned colors:");
        for (int i = 0; i < V; i++)
            System.out.println("Vertex " + i + " --->  Color " + color[i]);
    }

    // Sample driver code
    public static void main(String[] args) {
        /*
         * Sample Graph (Adjacency Matrix)
         * 0---1
         * |  /
         * 2
         */
        int[][] graph = {
            {0, 1, 1},
            {1, 0, 1},
            {1, 1, 0}
        };

        int m = 3; // Number of colors

        GraphColoring g = new GraphColoring(graph.length);
        g.graphColoring(graph, m);
    }
}
