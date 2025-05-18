import java.util.*;

public class WaterJarDFS {

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

        Stack<State> stack = new Stack<>();
        Set<String> visited = new HashSet<>();
        List<String> tree = new ArrayList<>();

        stack.push(new State(0, 0, "Start at (0, 0)"));
        visited.add("0,0");

        while (!stack.isEmpty()) {
            State current = stack.pop();
            tree.add(current.toString());

            if (current.jar4 == target) {
                System.out.println("Path to solution:\n" + current.path);
                System.out.println("Generated Tree: " + tree);
                return;
            }

            List<State> leftChildren = new ArrayList<>();
            List<State> rightChildren = new ArrayList<>();

            // Left side operations
            if (current.jar4 > 0)
                leftChildren.add(new State(0, current.jar3, current.path + " -> Empty 4-gallon jar"));

            if (current.jar3 < 3)
                leftChildren.add(new State(current.jar4, 3, current.path + " -> Fill 3-gallon jar"));

            if (current.jar3 > 0 && current.jar4 < 4) {
                int transfer = Math.min(current.jar3, 4 - current.jar4);
                leftChildren.add(new State(current.jar4 + transfer, current.jar3 - transfer,
                        current.path + " -> Transfer 3 to 4"));
            }

            // Right side operations
            if (current.jar3 > 0)
                rightChildren.add(new State(current.jar4, 0, current.path + " -> Empty 3-gallon jar"));

            if (current.jar4 < 4)
                rightChildren.add(new State(4, current.jar3, current.path + " -> Fill 4-gallon jar"));

            if (current.jar4 > 0 && current.jar3 < 3) {
                int transfer = Math.min(current.jar4, 3 - current.jar3);
                rightChildren.add(new State(current.jar4 - transfer, current.jar3 + transfer,
                        current.path + " -> Transfer 4 to 3"));
            }

            // DFS: push right first, then left (so left is explored first)
            for (State child : rightChildren) {
                String stateKey = child.jar4 + "," + child.jar3;
                if (!visited.contains(stateKey)) {
                    stack.push(child);
                    visited.add(stateKey);
                }
            }

            for (State child : leftChildren) {
                String stateKey = child.jar4 + "," + child.jar3;
                if (!visited.contains(stateKey)) {
                    stack.push(child);
                    visited.add(stateKey);
                }
            }
        }

        System.out.println("No solution found.");
        System.out.println("Generated Tree: " + tree);
    }
}
