import java.util.*;

public class EightPuzzleSolver {

    static class State {
        int[][] board;
        int x, y; // Position of the blank tile (0)
        int g;    // Cost from start to current node
        int h;    // Heuristic cost to goal
        State parent;

        State(int[][] board, int x, int y, int g, int h, State parent) {
            this.board = board;
            this.x = x;
            this.y = y;
            this.g = g;
            this.h = h;
            this.parent = parent;
        }

        int f() {
            return g + h;
        }
    }

    static int[] dx = {-1, 1, 0, 0}; // UP, DOWN
    static int[] dy = {0, 0, -1, 1}; // LEFT, RIGHT

    public static void main(String[] args) {
        int[][] initial = {
            {2, 8, 3},
            {1, 6, 4},
            {7, 0, 5}
        };

        int[][] goal = {
            {1, 2, 3},
            {8, 0, 4},
            {7, 6, 5}
        };

        aStarSearch(initial, goal);
    }

    static void aStarSearch(int[][] initial, int[][] goal) {
        PriorityQueue<State> open = new PriorityQueue<>(Comparator.comparingInt(State::f));
        Set<String> closed = new HashSet<>();

        int startX = findX(initial);
        int startY = findY(initial);

        State start = new State(initial, startX, startY, 0, manhattanDistance(initial), null);
        open.add(start);

        while (!open.isEmpty()) {
            State current = open.poll();

            if (Arrays.deepEquals(current.board, goal)) {
                printSolution(current);
                return;
            }

            closed.add(boardToString(current.board));

            for (int dir = 0; dir < 4; dir++) {
                int newX = current.x + dx[dir];
                int newY = current.y + dy[dir];

                if (isValid(newX, newY)) {
                    int[][] newBoard = copyBoard(current.board);
                    swap(newBoard, current.x, current.y, newX, newY);

                    if (closed.contains(boardToString(newBoard)))
                        continue;

                    State neighbor = new State(newBoard, newX, newY, current.g + 1,
                            manhattanDistance(newBoard), current);

                    open.add(neighbor);
                }
            }
        }

        System.out.println("No solution found.");
    }

    static int manhattanDistance(int[][] board) {
        int dist = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = board[i][j];
                if (value != 0) {
                    int goalX = (value - 1) / 3;
                    int goalY = (value - 1) % 3;
                    dist += Math.abs(i - goalX) + Math.abs(j - goalY);
                }
            }
        }
        return dist;
    }

    static boolean isValid(int x, int y) {
        return x >= 0 && x < 3 && y >= 0 && y < 3;
    }

    static int findX(int[][] board) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == 0)
                    return i;
        return -1;
    }

    static int findY(int[][] board) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == 0)
                    return j;
        return -1;
    }

    static int[][] copyBoard(int[][] board) {
        int[][] newBoard = new int[3][3];
        for (int i = 0; i < 3; i++)
            newBoard[i] = board[i].clone();
        return newBoard;
    }

    static void swap(int[][] board, int x1, int y1, int x2, int y2) {
        int temp = board[x1][y1];
        board[x1][y1] = board[x2][y2];
        board[x2][y2] = temp;
    }

    static String boardToString(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board)
            for (int cell : row)
                sb.append(cell);
        return sb.toString();
    }

    static void printSolution(State state) {
        List<State> path = new ArrayList<>();
        while (state != null) {
            path.add(state);
            state = state.parent;
        }
        Collections.reverse(path);
        int step = 0;
        for (State s : path) {
            System.out.println("Step " + (step++) + ":");
            printBoard(s.board);
            System.out.println();
        }
        System.out.println("Total steps: " + (step - 1));
    }

    static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int val : row) {
                System.out.print(val == 0 ? " " : val);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}