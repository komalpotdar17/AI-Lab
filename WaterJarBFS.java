import java.util.*;

public class WaterJarBFS {

    static class State {
        int jar4;
        int jar3;
        String path;

        State(int jar4, int jar3, String path) {
            this.jar4 = jar4;
            this.jar3 = jar3;
            this.path = path;
        }

        @Override
        public String toString() {
            return "(" + jar4 + ", " + jar3 + ")";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the target volume for the 4-gallon jar: ");
        int target = scanner.nextInt();

        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        List<String> tree = new ArrayList<>();

        queue.add(new State(0, 0, "Start at (0, 0)"));
        visited.add("0,0");

        while (!queue.isEmpty()) {
            State current = queue.poll();
            tree.add(current.toString());

            if (current.jar4 == target) {
                System.out.println("Path to solution:\n" + current.path);
                System.out.println("Generated Tree: " + tree);
                return;
            }

            List<State> children = new ArrayList<>();

            // Empty 4-gallon jar
            if (current.jar4 > 0)
                children.add(new State(0, current.jar3, current.path + " -> Empty 4-gallon jar"));

            // Fill 3-gallon jar
            if (current.jar3 < 3)
                children.add(new State(current.jar4, 3, current.path + " -> Fill 3-gallon jar"));

            // Transfer from 3-gallon to 4-gallon jar
            if (current.jar3 > 0 && current.jar4 < 4) {
                int transfer = Math.min(current.jar3, 4 - current.jar4);
                children.add(new State(current.jar4 + transfer, current.jar3 - transfer,
                        current.path + " -> Transfer 3 to 4"));
            }

            // Empty 3-gallon jar
            if (current.jar3 > 0)
                children.add(new State(current.jar4, 0, current.path + " -> Empty 3-gallon jar"));

            // Fill 4-gallon jar
            if (current.jar4 < 4)
                children.add(new State(4, current.jar3, current.path + " -> Fill 4-gallon jar"));

            // Transfer from 4-gallon to 3-gallon jar
            if (current.jar4 > 0 && current.jar3 < 3) {
                int transfer = Math.min(current.jar4, 3 - current.jar3);
                children.add(new State(current.jar4 - transfer, current.jar3 + transfer,
                        current.path + " -> Transfer 4 to 3"));
            }

            // Add valid children to queue
            for (State child : children) {
                String stateKey = child.jar4 + "," + child.jar3;
                if (!visited.contains(stateKey)) {
                    queue.add(child);
                    visited.add(stateKey);
                }
            }
        }

        System.out.println("No solution found.");
        System.out.println("Generated Tree: " + tree);
    }
}
