import java.util.*;

public class A_star_algorithm {

    public static void main(String[] args) {

        // Initialize nodes
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
        Node n13 = new Node("Gadchiroli", 0); // Goal
        Node n14 = new Node("Buldhana", 77);

        // Initialize edges
        n1.adjacencies = new Edge[]{new Edge(n2, 75), new Edge(n4, 140), new Edge(n8, 118)};
        n2.adjacencies = new Edge[]{new Edge(n1, 75), new Edge(n3, 71)};
        n3.adjacencies = new Edge[]{new Edge(n2, 71), new Edge(n4, 151)};
        n4.adjacencies = new Edge[]{new Edge(n1, 140), new Edge(n5, 99), new Edge(n3, 151), new Edge(n6, 80)};
        n5.adjacencies = new Edge[]{new Edge(n4, 99), new Edge(n13, 211)};
        n6.adjacencies = new Edge[]{new Edge(n4, 80), new Edge(n7, 97), new Edge(n12, 146)};
        n7.adjacencies = new Edge[]{new Edge(n6, 97), new Edge(n13, 101), new Edge(n12, 138)};
        n8.adjacencies = new Edge[]{new Edge(n1, 118), new Edge(n9, 111)};
        n9.adjacencies = new Edge[]{new Edge(n8, 111), new Edge(n10, 70)};
        n10.adjacencies = new Edge[]{new Edge(n9, 70), new Edge(n11, 75)};
        n11.adjacencies = new Edge[]{new Edge(n10, 75), new Edge(n12, 120)};
        n12.adjacencies = new Edge[]{new Edge(n11, 120), new Edge(n6, 146), new Edge(n7, 138)};
        n13.adjacencies = new Edge[]{new Edge(n7, 101), new Edge(n14, 90), new Edge(n5, 211)};
        n14.adjacencies = new Edge[]{new Edge(n13, 90)};

        // Run A* from Pune to Gadchiroli
        List<Node> visitedNodes = AstarSearch(n1, n13);

        System.out.println("\n---------------- Visited Nodes ----------------");
        int count = 1;
        for (Node node : visitedNodes) {
            System.out.printf("%d. %-15s (f=%.1f)\n", count++, node.value, node.f_scores);
        }

        System.out.println("\n---------------- Final Path ----------------");
        List<Node> path = printPath(n13);
        double totalCost = 0;
        for (int i = 0; i < path.size(); i++) {
            Node current = path.get(i);
            System.out.printf("%s (g=%.1f)", current.value, current.g_scores);
            if (i < path.size() - 1) {
                System.out.print(" -> ");
                for (Edge e : current.adjacencies) {
                    if (e.target == path.get(i + 1)) {
                        totalCost += e.cost;
                        break;
                    }
                }
            }
        }
        System.out.println("\n\nTotal Path Cost: " + totalCost);
    }

    public static List<Node> printPath(Node target) {
        List<Node> path = new ArrayList<>();
        for (Node node = target; node != null; node = node.parent) {
            path.add(node);
        }
        Collections.reverse(path);
        return path;
    }

    public static List<Node> AstarSearch(Node source, Node goal) {
        Set<Node> explored = new HashSet<>();
        List<Node> visitedOrder = new ArrayList<>();

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(n -> n.f_scores));

        source.g_scores = 0;
        source.f_scores = source.h_scores;
        queue.add(source);

        int step = 1;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            visitedOrder.add(current);
            explored.add(current);

            System.out.println("\nStep " + step++ + ": Expanding Node: " + current.value);
            for (Edge e : current.adjacencies) {
                Node child = e.target;
                double cost = e.cost;
                double temp_g = current.g_scores + cost;
                double temp_f = temp_g + child.h_scores;

                System.out.printf("    Neighbor: %-12s | g=%.1f | h=%.1f | f=%.1f\n",
                        child.value, temp_g, child.h_scores, temp_f);

                if ((explored.contains(child)) && (temp_f >= child.f_scores)) {
                    continue;
                } else if ((!queue.contains(child)) || (temp_f < child.f_scores)) {
                    child.parent = current;
                    child.g_scores = temp_g;
                    child.f_scores = temp_f;

                    if (queue.contains(child)) {
                        queue.remove(child);
                    }
                    queue.add(child);
                }
            }

            if (current.value.equals(goal.value)) {
                System.out.println("Goal '" + goal.value + "' reached!");
                return visitedOrder;
            }
        }

        System.out.println("Goal not reachable.");
        return visitedOrder;
    }
}

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

    public String toString() {
        return value;
    }
}

class Edge {
    public final double cost;
    public final Node target;

    public Edge(Node targetNode, double costVal) {
        target = targetNode;
        cost = costVal;
    }
}
