import java.util.*;

public class minmax {
    static final char HUMAN = 'O';
    static final char AI = 'X';
    static final char EMPTY = ' ';
    static List<Integer> movePath = new ArrayList<>();

    public static void main(String[] args) {
        char[] board = {
            ' ', ' ', ' ',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };

        Scanner scanner = new Scanner(System.in);
        printBoard(board);

        while (true) {
            // Human move
            System.out.print("Enter your move (0-8): ");
            int move = scanner.nextInt();
            if (board[move] != EMPTY) {
                System.out.println("Invalid move!");
                continue;
            }
            board[move] = HUMAN;
            printBoard(board);
            if (isGameOver(board)) break;

            // AI move
            int aiMove = findBestMove(board);
            board[aiMove] = AI;
            movePath.add(aiMove);
            System.out.println("AI plays at: " + aiMove);
            printBoard(board);
            if (isGameOver(board)) break;
        }

        System.out.println("Game Over!");
        System.out.println("AI Move Path: " + movePath);
    }

    // Check if game is over (win or draw)
    static boolean isGameOver(char[] board) {
        if (checkWinner(board, AI)) {
            System.out.println("AI wins!");
            return true;
        }
        if (checkWinner(board, HUMAN)) {
            System.out.println("You win!");
            return true;
        }
        if (isFull(board)) {
            System.out.println("It's a draw!");
            return true;
        }
        return false;
    }

    // Print the board
    static void printBoard(char[] board) {
        System.out.println();
        for (int i = 0; i < 9; i++) {
            System.out.print(" " + board[i]);
            if (i % 3 != 2) System.out.print(" |");
            if (i % 3 == 2 && i != 8) System.out.println("\n-----------");
        }
        System.out.println("\n");
    }

    // Check for winner
    static boolean checkWinner(char[] board, char player) {
        int[][] winConditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
            {0, 4, 8}, {2, 4, 6}             // diagonals
        };

        for (int[] wc : winConditions) {
            if (board[wc[0]] == player && board[wc[1]] == player && board[wc[2]] == player)
                return true;
        }
        return false;
    }

    // Check if board is full
    static boolean isFull(char[] board) {
        for (char c : board)
            if (c == EMPTY) return false;
        return true;
    }

    // Minimax algorithm
    static int findBestMove(char[] board) {
        int bestVal = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int i = 0; i < 9; i++) {
            if (board[i] == EMPTY) {
                board[i] = AI;
                int moveVal = minimax(board, 0, false);
                board[i] = EMPTY;

                if (moveVal > bestVal) {
                    bestVal = moveVal;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }

    static int minimax(char[] board, int depth, boolean isMax) {
        if (checkWinner(board, AI)) return 10 - depth;
        if (checkWinner(board, HUMAN)) return depth - 10;
        if (isFull(board)) return 0;

        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == EMPTY) {
                    board[i] = AI;
                    best = Math.max(best, minimax(board, depth + 1, false));
                    board[i] = EMPTY;
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == EMPTY) {
                    board[i] = HUMAN;
                    best = Math.min(best, minimax(board, depth + 1, true));
                    board[i] = EMPTY;
                }
            }
            return best;
        }
    }
}