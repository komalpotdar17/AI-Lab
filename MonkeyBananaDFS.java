import java.util.*;

class StateDFS {
    String monkeyPos;
    String boxPos;
    String monkeyHeight;
    boolean hasBanana;

    public StateDFS(String monkeyPos, String boxPos, String monkeyHeight, boolean hasBanana) {
        this.monkeyPos = monkeyPos;
        this.boxPos = boxPos;
        this.monkeyHeight = monkeyHeight;
        this.hasBanana = hasBanana;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StateDFS)) return false;
        StateDFS other = (StateDFS) obj;
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

public class MonkeyBananaDFS {
    static StateDFS goal = null;

    public static void main(String[] args) {
        StateDFS initial = new StateDFS("door", "window", "Low", false);
        Set<StateDFS> visited = new HashSet<>();
        Map<StateDFS, StateDFS> parent = new HashMap<>();

        if (dfs(initial, visited, parent)) {
            System.out.println("Goal reached using DFS!\nPath:");
            printPath(goal, parent);
        } else {
            System.out.println("No solution found.");
        }
    }

    static boolean dfs(StateDFS current, Set<StateDFS> visited, Map<StateDFS, StateDFS> parent) {
        if (visited.contains(current)) return false;
        visited.add(current);

        if (current.hasBanana) {
            goal = current;
            return true;
        }

        for (StateDFS next : getNextStates(current)) {
            if (!visited.contains(next)) {
                parent.put(next, current);
                if (dfs(next, visited, parent)) return true;
            }
        }

        return false;
    }

    static void printPath(StateDFS state, Map<StateDFS, StateDFS> parent) {
        if (parent.get(state) != null) {
            printPath(parent.get(state), parent);
        }
        System.out.println(state);
    }

    static List<StateDFS> getNextStates(StateDFS current) {
        List<StateDFS> successors = new ArrayList<>();

        // Move to box
        if (!current.monkeyPos.equals(current.boxPos)) {
            successors.add(new StateDFS(current.boxPos, current.boxPos, current.monkeyHeight, current.hasBanana));
        }

        // Push box to center
        if (current.monkeyPos.equals(current.boxPos) && !current.boxPos.equals("center")) {
            successors.add(new StateDFS("center", "center", current.monkeyHeight, current.hasBanana));
        }

        // Climb box
        if (current.monkeyPos.equals("center") && current.boxPos.equals("center") && current.monkeyHeight.equals("Low")) {
            successors.add(new StateDFS("center", "center", "High", current.hasBanana));
        }

        // Grasp banana
        if (current.monkeyPos.equals("center") && current.boxPos.equals("center") && current.monkeyHeight.equals("High")) {
            successors.add(new StateDFS("center", "center", "High", true));
        }

        return successors;
    }
} 
