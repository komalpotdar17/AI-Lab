import java.util.Scanner;

public class TicTacToeAIAlphaBetaPruning {
    static final char HUMAN = 'O', AI = 'X', EMPTY = ' ';
    static char[][] board = {
        {EMPTY, EMPTY, EMPTY},
        {EMPTY, EMPTY, EMPTY},
        {EMPTY, EMPTY, EMPTY}
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isHumanTurn = true;

        while (true) {
            printBoard();
            if (isGameOver()) break;

            if (isHumanTurn) {
                System.out.println("Your turn (O). Enter row and column (0-2): ");
                int row = scanner.nextInt(), col = scanner.nextInt();
                if (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != EMPTY) {
                    System.out.println("Invalid move! Try again.");
                    continue;
                }
                board[row][col] = HUMAN;
            } else {
                System.out.println("AI (X) is making a move...");
                int[] bestMove = findBestMove();
                board[bestMove[0]][bestMove[1]] = AI;
            }
            isHumanTurn = !isHumanTurn;
        }
        scanner.close();
    }

    public static int minimax(char[][] board, int depth, boolean isMaximizing, int alpha, int beta) {
        int score = evaluateBoard();
        if (score == 1 || score == -1 || isBoardFull()) return score;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == EMPTY) {
                        board[row][col] = AI;
                        bestScore = Math.max(bestScore, minimax(board, depth + 1, false, alpha, beta));
                        board[row][col] = EMPTY;
                        alpha = Math.max(alpha, bestScore);
                        if (beta <= alpha) return bestScore; // Beta cut-off
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == EMPTY) {
                        board[row][col] = HUMAN;
                        bestScore = Math.min(bestScore, minimax(board, depth + 1, true, alpha, beta));
                        board[row][col] = EMPTY;
                        beta = Math.min(beta, bestScore);
                        if (beta <= alpha) return bestScore; // Alpha cut-off
                    }
                }
            }
            return bestScore;
        }
    }

    public static int[] findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == EMPTY) {
                    board[row][col] = AI;
                    int moveScore = minimax(board, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board[row][col] = EMPTY;
                    if (moveScore > bestScore) {
                        bestScore = moveScore;
                        bestMove = new int[]{row, col};
                    }
                }
            }
        }
        return bestMove;
    }

    public static int evaluateBoard() {
        int[][] winPatterns = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
        };
        for (int[] pattern : winPatterns) {
            char a = board[pattern[0] / 3][pattern[0] % 3];
            char b = board[pattern[1] / 3][pattern[1] % 3];
            char c = board[pattern[2] / 3][pattern[2] % 3];
            if (a == AI && b == AI && c == AI) return 1;
            if (a == HUMAN && b == HUMAN && c == HUMAN) return -1;
        }
        return 0;
    }

    public static boolean isBoardFull() {
        for (char[] row : board)
            for (char cell : row)
                if (cell == EMPTY)
                    return false;
        return true;
    }

    public static boolean isGameOver() {
        int score = evaluateBoard();
        if (score == 1) {
            printBoard();
            System.out.println("\nAI (X) wins!");
            return true;
        } else if (score == -1) {
            printBoard();
            System.out.println("\nHuman (O) wins!");
            return true;
        } else if (isBoardFull()) {
            printBoard();
            System.out.println("\nIt's a draw!");
            return true;
        }
        return false;
    }

    public static void printBoard() {
        System.out.println("\nCurrent Board:");
        System.out.println("-------------");
        for (char[] row : board) {
            System.out.print("| ");
            for (char cell : row)
                System.out.print((cell == EMPTY ? '-' : cell) + " | ");
            System.out.println("\n-------------");
        }
    }
}
