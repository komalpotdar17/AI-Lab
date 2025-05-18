import java.util.*;

class State {
    String monkeyPos;
    String boxPos;
    String monkeyHeight;
    boolean hasBanana;

    public State(String monkeyPos, String boxPos, String monkeyHeight, boolean hasBanana) {
        this.monkeyPos = monkeyPos;
        this.boxPos = boxPos;
        this.monkeyHeight = monkeyHeight;
        this.hasBanana = hasBanana;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof State)) return false;
        State other = (State) obj;
        return this.monkeyPos.equals(other.monkeyPos) &&
               this.boxPos.equals(other.boxPos) &&
               this.monkeyHeight.equals(other.monkeyHeight) &&
               this.hasBanana == other.hasBanana;
    }

    @Override
    public int hashCode() {
        return Objects.hash(monkeyPos, boxPos, monkeyHeight, hasBanana);
    }

    @Override
    public String toString() {
        return "Monkey at " + monkeyPos + ", Box at " + boxPos +
               ", Monkey is " + monkeyHeight + ", Has banana: " + hasBanana;
    }
}

public class MonkeyBananaBFS {
    public static void main(String[] args) {
        State initial = new State("door", "window", "Low", false);
        bfs(initial);
    }

    static void bfs(State start) {
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        Map<State, State> parent = new HashMap<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.hasBanana) {
                System.out.println("Goal reached using BFS!\nPath:");
                printPath(current, parent);
                return;
            }

            for (State next : getNextStates(current)) {
                if (!visited.contains(next)) {
                    queue.add(next);
                    visited.add(next);
                    parent.put(next, current);
                }
            }
        }
        System.out.println("No solution found.");
    }

    static void printPath(State state, Map<State, State> parent) {
        if (parent.get(state) != null) {
            printPath(parent.get(state), parent);
        }
        System.out.println(state);
    }

    static List<State> getNextStates(State current) {
        List<State> successors = new ArrayList<>();

        // Move to box
        if (!current.monkeyPos.equals(current.boxPos)) {
            successors.add(new State(current.boxPos, current.boxPos, current.monkeyHeight, current.hasBanana));
        }

        // Push box to center
        if (current.monkeyPos.equals(current.boxPos) && !current.boxPos.equals("center")) {
            successors.add(new State("center", "center", current.monkeyHeight, current.hasBanana));
        }

        // Climb box
        if (current.monkeyPos.equals("center") && current.boxPos.equals("center") && current.monkeyHeight.equals("Low")) {
            successors.add(new State("center", "center", "High", current.hasBanana));
        }

        // Grasp banana
        if (current.monkeyPos.equals("center") && current.boxPos.equals("center") && current.monkeyHeight.equals("High")) {
            successors.add(new State("center", "center", "High", true));
        }

        return successors;
    }
}