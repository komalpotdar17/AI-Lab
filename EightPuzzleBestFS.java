import java.util.*;

public class EightPuzzleBestFS {

    static class State {
        int[][] board;
        int x, y; // Blank tile position
        int h;    // Heuristic (Manhattan distance)
        State parent;

        State(int[][] board, int x, int y, int h, State parent) {
            this.board = board;
            this.x = x;
            this.y = y;
            this.h = h;
            this.parent = parent;
        }

        static void printBoard(int[][] board) {
            for (int[] row : board) {
                for (int val : row) {
                    System.out.print(val == 0 ? "  " : val + " ");
                }
                System.out.println();
            }
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

        bestFirstSearch(initial, goal);
    }

    static void bestFirstSearch(int[][] initial, int[][] goal) {
        PriorityQueue<State> open = new PriorityQueue<>(Comparator.comparingInt(s -> s.h));
        Set<String> closed = new HashSet<>();

        int startX = findX(initial);
        int startY = findY(initial);

        State start = new State(initial, startX, startY, manhattanDistance(initial, goal), null);
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

                    int h = manhattanDistance(newBoard, goal);
                    State neighbor = new State(newBoard, newX, newY, h, current);
                    open.add(neighbor);
                }
            }
        }

        System.out.println("No solution found.");
    }

    static int manhattanDistance(int[][] board, int[][] goal) {
        int dist = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = board[i][j];
                if (value != 0) {
                    int goalX = -1, goalY = -1;
                    for (int m = 0; m < 3; m++) {
                        for (int n = 0; n < 3; n++) {
                            if (goal[m][n] == value) {
                                goalX = m;
                                goalY = n;
                            }
                        }
                    }
                    dist += Math.abs(i - goalX) + Math.abs(j - goalY);
                }
            }
        }
        return dist;
    }

    static void printSolution(State goalState) {
        Stack<State> path = new Stack<>();
        State curr = goalState;
        while (curr != null) {
            path.push(curr);
            curr = curr.parent;
        }

        int step = 0;
        while (!path.isEmpty()) {
            System.out.println("Step " + step++);
            State.printBoard(path.pop().board);
            System.out.println();
        }

        System.out.println("Total steps taken: " + (step - 1));
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

    static boolean isValid(int x, int y) {
        return x >= 0 && x < 3 && y >= 0 && y < 3;
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
            for (int val : row)
                sb.append(val);
        return sb.toString();
    }
}
