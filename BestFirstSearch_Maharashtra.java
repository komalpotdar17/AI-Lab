import java.util.*;

public class BestFirstSearch_Maharashtra {

    static class Node {
        String name;
        int heuristic;
        List<Node> neighbors;
        Node parent;

        public Node(String name, int heuristic) {
            this.name = name;
            this.heuristic = heuristic;
            this.neighbors = new ArrayList<>();
        }

        public void addNeighbor(Node neighbor) {
            this.neighbors.add(neighbor);
        }

        public String toString() {
            return name;
        }
    }

    public static void bestFirstSearch(Node start, Node goal) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.heuristic));
        Set<Node> visited = new HashSet<>();
        List<Node> visitedOrder = new ArrayList<>();

        openList.add(start);

        int step = 1;
        while (!openList.isEmpty()) {
            Node current = openList.poll();
            visitedOrder.add(current);
            System.out.println("\nStep " + step++ + ": Visiting " + current.name + " (h=" + current.heuristic + ")");

            if (current == goal) {
                System.out.println("Goal Found: " + current.name);
                printPath(goal);
                printVisited(visitedOrder);
                return;
            }

            visited.add(current);

            for (Node neighbor : current.neighbors) {
                if (!visited.contains(neighbor) && !openList.contains(neighbor)) {
                    neighbor.parent = current;
                    System.out.println("   Adding Neighbor: " + neighbor.name + " (h=" + neighbor.heuristic + ")");
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
            System.out.printf("%d. %-15s (h=%d)\n", count++, node.name, node.heuristic);
        }
    }

    public static void main(String[] args) {
        // Create nodes with heuristics to Gadchiroli
        Node pune = new Node("Pune", 366);
        Node amravati = new Node("Amravati", 374);
        Node mumbai = new Node("Mumbai", 380);
        Node kolhapur = new Node("Kolhapur", 253);
        Node solapur = new Node("Solapur", 178);
        Node sangli = new Node("Sangli", 193);
        Node ratnagiri = new Node("Ratnagiri", 98);
        Node latur = new Node("Latur", 329);
        Node nagpur = new Node("Nagpur", 244);
        Node nanded = new Node("Nanded", 241);
        Node sambhajinagar = new Node("SambhajiNagar", 242);
        Node nashik = new Node("Nashik", 160);
        Node gadchiroli = new Node("Gadchiroli", 0); // Goal
        Node buldhana = new Node("Buldhana", 77);

        // Add connections (undirected graph)
        pune.addNeighbor(amravati);
        pune.addNeighbor(kolhapur);
        pune.addNeighbor(latur);

        amravati.addNeighbor(pune);
        amravati.addNeighbor(mumbai);

        mumbai.addNeighbor(amravati);
        mumbai.addNeighbor(kolhapur);

        kolhapur.addNeighbor(pune);
        kolhapur.addNeighbor(solapur);
        kolhapur.addNeighbor(mumbai);
        kolhapur.addNeighbor(sangli);

        solapur.addNeighbor(kolhapur);
        solapur.addNeighbor(gadchiroli);

        sangli.addNeighbor(kolhapur);
        sangli.addNeighbor(ratnagiri);
        sangli.addNeighbor(nashik);

        ratnagiri.addNeighbor(sangli);
        ratnagiri.addNeighbor(gadchiroli);
        ratnagiri.addNeighbor(nashik);

        latur.addNeighbor(pune);
        latur.addNeighbor(nagpur);

        nagpur.addNeighbor(latur);
        nagpur.addNeighbor(nanded);

        nanded.addNeighbor(nagpur);
        nanded.addNeighbor(sambhajinagar);

        sambhajinagar.addNeighbor(nanded);
        sambhajinagar.addNeighbor(nashik);

        nashik.addNeighbor(sambhajinagar);
        nashik.addNeighbor(sangli);
        nashik.addNeighbor(ratnagiri);

        gadchiroli.addNeighbor(solapur);
        gadchiroli.addNeighbor(ratnagiri);
        gadchiroli.addNeighbor(buldhana);

        buldhana.addNeighbor(gadchiroli);

        // Run search from Pune to Gadchiroli
        bestFirstSearch(pune, gadchiroli);
    }
}
