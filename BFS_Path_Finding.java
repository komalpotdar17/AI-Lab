import java.util.*;

public class BFS_Path_Finding {

    static class Node {
        String name;
        List<Edge> neighbors;
        Node parent;

        public Node(String name) {
            this.name = name;
            this.neighbors = new ArrayList<>();
        }

        public void addNeighbor(Node neighbor, int cost) {
            this.neighbors.add(new Edge(neighbor, cost));
        }

        public String toString() {
            return name;
        }
    }

    static class Edge {
        Node neighbor;
        int cost;

        public Edge(Node neighbor, int cost) {
            this.neighbor = neighbor;
            this.cost = cost;
        }
    }

    // Calculate heuristic dynamically (can be customized based on the problem)
    public static int calculateHeuristic(Node current, Node goal) {
        // Example: Using a dummy heuristic logic based on node name length
        return Math.abs(current.name.length() - goal.name.length());
    }

    public static void bestFirstSearch(Node start, Node goal) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(n -> calculateHeuristic(n, goal)));
        Set<Node> visited = new HashSet<>();
        List<Node> visitedOrder = new ArrayList<>();

        openList.add(start);

        int step = 1;
        while (!openList.isEmpty()) {
            Node current = openList.poll();
            visitedOrder.add(current);
            int heuristic = calculateHeuristic(current, goal);
            System.out.println("\nStep " + step++ + ": Visiting " + current.name + " (h=" + heuristic + ")");

            if (current == goal) {
                System.out.println("Goal Found: " + current.name);
                printPath(goal);
                printVisited(visitedOrder);
                return;
            }

            visited.add(current);

            for (Edge edge : current.neighbors) {
                Node neighbor = edge.neighbor;
                if (!visited.contains(neighbor) && !openList.contains(neighbor)) {
                    neighbor.parent = current;
                    int edgeCost = edge.cost;
                    System.out.println("   Adding Neighbor: " + neighbor.name + " (h=" + calculateHeuristic(neighbor, goal) + ", cost=" + edgeCost + ")");
                    openList.add(neighbor);
                }
            }
        }

        System.out.println("Goal not found.");
    }

    public static void printPath(Node goal) {
        System.out.println("\n---------------- Final Path ----------------");
        List<Node> path = new ArrayList<>();
        for (Node node = goal; node != null; node = node.parent) {
            path.add(node);
        }
        Collections.reverse(path);
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i).name);
            if (i < path.size() - 1) System.out.print(" -> ");
        }
        System.out.println();
    }

    public static void printVisited(List<Node> visitedOrder) {
        System.out.println("\n---------------- Visited Nodes ----------------");
        int count = 1;
        for (Node node : visitedOrder) {
            System.out.printf("%d. %-15s\n", count++, node.name);
        }
    }

    public static void main(String[] args) {
        // Create nodes without hardcoded heuristic values
        Node pune = new Node("Pune");
        Node amravati = new Node("Amravati");
        Node mumbai = new Node("Mumbai");
        Node kolhapur = new Node("Kolhapur");
        Node solapur = new Node("Solapur");
        Node sangli = new Node("Sangli");
        Node ratnagiri = new Node("Ratnagiri");
        Node latur = new Node("Latur");
        Node nagpur = new Node("Nagpur");
        Node nanded = new Node("Nanded");
        Node sambhajinagar = new Node("SambhajiNagar");
        Node nashik = new Node("Nashik");
        Node gadchiroli = new Node("Gadchiroli"); // Goal
        Node buldhana = new Node("Buldhana");

        // Add connections (undirected graph with edge costs)
        pune.addNeighbor(amravati, 50); // Pune -> Amravati with cost 50
        pune.addNeighbor(kolhapur, 70); // Pune -> Kolhapur with cost 70
        pune.addNeighbor(latur, 100);  // Pune -> Latur with cost 100

        amravati.addNeighbor(pune, 50);
        amravati.addNeighbor(mumbai, 80);

        mumbai.addNeighbor(amravati, 80);
        mumbai.addNeighbor(kolhapur, 90);

        kolhapur.addNeighbor(pune, 70);
        kolhapur.addNeighbor(solapur, 40);
        kolhapur.addNeighbor(mumbai, 90);
        kolhapur.addNeighbor(sangli, 30);

        solapur.addNeighbor(kolhapur, 40);
        solapur.addNeighbor(gadchiroli, 200);

        sangli.addNeighbor(kolhapur, 30);
        sangli.addNeighbor(ratnagiri, 60);
        sangli.addNeighbor(nashik, 120);

        ratnagiri.addNeighbor(sangli, 60);
        ratnagiri.addNeighbor(gadchiroli, 150);
        ratnagiri.addNeighbor(nashik, 110);

        latur.addNeighbor(pune, 100);
        latur.addNeighbor(nagpur, 130);

        nagpur.addNeighbor(latur, 130);
        nagpur.addNeighbor(nanded, 120);

        nanded.addNeighbor(nagpur, 120);
        nanded.addNeighbor(sambhajinagar, 90);

        sambhajinagar.addNeighbor(nanded, 90);
        sambhajinagar.addNeighbor(nashik, 70);

        nashik.addNeighbor(sambhajinagar, 70);
        nashik.addNeighbor(sangli, 120);
        nashik.addNeighbor(ratnagiri, 110);

        gadchiroli.addNeighbor(solapur, 200);
        gadchiroli.addNeighbor(ratnagiri, 150);
        gadchiroli.addNeighbor(buldhana, 180);

        buldhana.addNeighbor(gadchiroli, 180);

        // Run search from Pune to Gadchiroli
        bestFirstSearch(pune, gadchiroli);
    }
}
 