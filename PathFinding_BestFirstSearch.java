//7. **Best-First Search (Simplified)**: Time \( O(E \cdot \log V) \), Space \( O(V) \).

import java.util.*;

class Node {
    String name;
    int heuristic;
    List<Node> neighbors;

    public Node(String name, int heuristic) {
        this.name = name;
        this.heuristic = heuristic;
        this.neighbors = new ArrayList<>();
    }

    public void addNeighbor(Node neighbor) {
        this.neighbors.add(neighbor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name + " (" + heuristic + ")";
    }
}

public class PathFinding_BestFirstSearch {

    public static void bestFirstSearch(Node start, Node goal) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.heuristic));
        Set<Node> visited = new HashSet<>();

        openList.add(start);
        System.out.println("Starting Best-First Search from " + start.name + " to " + goal.name + "\n");

        while (!openList.isEmpty()) {
            System.out.println("Open List: " + getNodeNames(openList));
            Node current = openList.poll();
            System.out.println("Visiting Node: " + current.name + " (Heuristic: " + current.heuristic + ")");

            if (current.equals(goal)) {
                System.out.println("\nGoal Found: " + current.name);
                return;
            }

            visited.add(current);
            System.out.println(" Visited Nodes: " + getNodeNames(visited));

            for (Node neighbor : current.neighbors) {
                if (!visited.contains(neighbor) && !openList.contains(neighbor)) {
                    openList.add(neighbor);
                    System.out.println("   -> Adding Neighbor: " + neighbor.name + " (Heuristic: " + neighbor.heuristic + ")");
                }
            }

            System.out.println("-------------------------------------\n");
        }

        System.out.println("\n Goal not found.");
    }

    // Helper method to format node names and heuristics
    private static String getNodeNames(Collection<Node> nodes) {
        StringBuilder sb = new StringBuilder("[");
        for (Node node : nodes) {
            sb.append(node.toString()).append(", ");
        }
        if (!nodes.isEmpty()) sb.setLength(sb.length() - 2); // Remove trailing comma and space
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        // Create nodes
        Node A = new Node("A", 3);
        Node B = new Node("B", 2);
        Node C = new Node("C", 1);
        Node D = new Node("D", 4);
        Node E = new Node("E", 0); // Goal node

        // Add neighbors (graph edges)
        A.addNeighbor(B);
        A.addNeighbor(C);
        B.addNeighbor(D);
        C.addNeighbor(E);

        // Run Best-First Search
        bestFirstSearch(A, E);
    }
}
