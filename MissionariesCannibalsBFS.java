import java.util.*;

class MissionariesCannibalsBFS {
    static class State {
        int ml, cl, boat, mr, cr;
        List<String> path;

        public State(int ml, int cl, int boat, int mr, int cr, List<String> path) {
            this.ml = ml;
            this.cl = cl;
            this.boat = boat;
            this.mr = mr;
            this.cr = cr;
            this.path = new ArrayList<>(path);
            this.path.add(toString());
        }

        public boolean isGoal() {
            return ml == 0 && cl == 0 && mr == 3 && cr == 3;
        }

        public boolean isValid() {
            return (ml == 0 || ml >= cl) && (mr == 0 || mr >= cr) && ml >= 0 && cl >= 0 && mr >= 0 && cr >= 0;
        }

        public String toString() {
            return "(" + ml + "M, " + cl + "C, " + (boat == 1 ? "Left" : "Right") + ", " + mr + "M, " + cr + "C)";
        }
    }

    public static void bfs() {
        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(new State(3, 3, 1, 0, 0, new ArrayList<>()));

        while (!queue.isEmpty()) {
            State state = queue.poll();
            System.out.println("BFS Traversing: " + state);

            if (state.isGoal()) {
                System.out.println("\nSolution Path:");
                for (String step : state.path) {
                    System.out.println(step);
                }
                return;
            }

            visited.add(state.toString());

            int[][] moves = {{1, 0}, {2, 0}, {0, 1}, {0, 2}, {1, 1}};
            int direction = (state.boat == 1) ? -1 : 1;

            for (int[] move : moves) {
                State next = new State(
                    state.ml + direction * move[0], state.cl + direction * move[1], state.boat ^ 1,
                    state.mr - direction * move[0], state.cr - direction * move[1], state.path
                );
                if (next.isValid() && !visited.contains(next.toString())) {
                    queue.add(next);
                }
            }
        }
        System.out.println("No solution found.");
    }

    public static void main(String[] args) {
        System.out.println("\n--- BFS Solution Path ---");
        bfs();
    }
}
