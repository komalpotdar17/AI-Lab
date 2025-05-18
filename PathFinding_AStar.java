//6. A* Algorithm**: Time \( O(E \cdot \log V) \), Space \( O(V) \).
import java.util.*;

public class PathFinding_AStar {

    public static void main(String[] args) {

        // Initialize the graph
        Node n1 = new Node("Pune", 366);
        Node n2 = new Node("Amravati", 374);
        Node n3 = new Node("Mumbai", 380);
        Node n4 = new Node("Kolhapur", 253);
        Node n5 = new Node("Solapur", 178);
        Node n6 = new Node("Sangli", 193);
        Node n7 = new Node("Ratnagiri", 98);
        Node n8 = new Node("Latur", 329);
        Node n9 = new Node("Nagpur", 244);
        Node n10 = new Node("Nanded", 241);
        Node n11 = new Node("SambhajiNagar", 242);
        Node n12 = new Node("Nashik", 160);
        Node n13 = new Node("Gadchiroli", 0); // Goal node
        Node n14 = new Node("Buldhana", 77);

        // Define edges (neighbors)
        n1.adjacencies = new Edge[] {
                new Edge(n2, 75),
                new Edge(n4, 140),
                new Edge(n8, 118)
        };

        n2.adjacencies = new Edge[] {
                new Edge(n1, 75),
                new Edge(n3, 71)
        };

        n3.adjacencies = new Edge[] {
                new Edge(n2, 71),
                new Edge(n4, 151)
        };

        n4.adjacencies = new Edge[] {
                new Edge(n1, 140),
                new Edge(n5, 99),
                new Edge(n3, 151),
                new Edge(n6, 80)
        };

        n5.adjacencies = new Edge[] {
                new Edge(n4, 99),
                new Edge(n13, 211)
        };

        n6.adjacencies = new Edge[] {
                new Edge(n4, 80),
                new Edge(n7, 97),
                new Edge(n12, 146)
        };

        n7.adjacencies = new Edge[] {
                new Edge(n6, 97),
                new Edge(n13, 101),
                new Edge(n12, 138)
        };

        n8.adjacencies = new Edge[] {
                new Edge(n1, 118),
                new Edge(n9, 111)
        };

        n9.adjacencies = new Edge[] {
                new Edge(n8, 111),
                new Edge(n10, 70)
        };

        n10.adjacencies = new Edge[] {
                new Edge(n9, 70),
                new Edge(n11, 75)
        };

        n11.adjacencies = new Edge[] {
                new Edge(n10, 75),
                new Edge(n12, 120)
        };

        n12.adjacencies = new Edge[] {
                new Edge(n11, 120),
                new Edge(n6, 146),
                new Edge(n7, 138)
        };

        n13.adjacencies = new Edge[] {
                new Edge(n7, 101),
                new Edge(n14, 90),
                new Edge(n5, 211)
        };

        n14.adjacencies = new Edge[] {
                new Edge(n13, 90)
        };

        // Perform A* Search
        AstarSearch(n1, n14);

        // Print the path to goal
        List<Node> path = printPath(n14);
        System.out.println("\nFinal Path from " + n1.value + " to " + n14.value + ":");
        for (Node node : path) {
            System.out.print(node.value + "->");
        }
        System.out.println();
    }

    public static void AstarSearch(Node source, Node goal) {

        Set<Node> explored = new HashSet<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(n -> n.f_scores));

        source.g_scores = 0;
        source.f_scores = source.h_scores;

        queue.add(source);

        boolean found = false;

        System.out.println(" Starting A* Search from " + source.value + " to " + goal.value + "\n");

        while (!queue.isEmpty() && !found) {
            System.out.println(" Open List: " + queue);

            Node current = queue.poll();
            System.out.println(" Visiting: " + current.value + " (g: " + current.g_scores + ", h: " + current.h_scores + ", f: " + current.f_scores + ")");

            explored.add(current);

            if (current.value.equals(goal.value)) {
                found = true;
                System.out.println(" Goal reached: " + current.value + "\n");
                break;
            }

            for (Edge e : current.adjacencies) {
                Node child = e.target;
                double cost = e.cost;
                double temp_g_scores = current.g_scores + cost;
                double temp_f_scores = temp_g_scores + child.h_scores;

                if ((explored.contains(child)) && (temp_f_scores >= child.f_scores)) {
                    continue;
                }

                if ((!queue.contains(child)) || (temp_f_scores < child.f_scores)) {
                    child.parent = current;
                    child.g_scores = temp_g_scores;
                    child.f_scores = temp_f_scores;

                    if (queue.contains(child)) {
                        queue.remove(child);
                    }

                    queue.add(child);
                    System.out.println("   -> Adding/Updating Node: " + child.value + " (g: " + child.g_scores + ", h: " + child.h_scores + ", f: " + child.f_scores + ")");
                }
            }

            System.out.println("-------------------------------------\n");
        }

        if (!found) {
            System.out.println(" Goal not found.");
        }
    }

    public static List<Node> printPath(Node target) {
        List<Node> path = new ArrayList<>();

        for (Node node = target; node != null; node = node.parent) {
            path.add(node);
        }

        Collections.reverse(path);
        return path;
    }
}

// Node class with g, h, f scores
class Node {
    public final String value;
    public double g_scores;
    public final double h_scores;
    public double f_scores = Double.MAX_VALUE;
    public Edge[] adjacencies;
    public Node parent;

    public Node(String val, double hVal) {
        value = val;
        h_scores = hVal;
    }

    @Override
    public String toString() {
        return value + " (f: " + f_scores + ")";
    }
}

// Edge class represents neighbors and cost
class Edge {
    public final double cost;
    public final Node target;

    public Edge(Node targetNode, double costVal) {
        target = targetNode;
        cost = costVal;
    }
}
