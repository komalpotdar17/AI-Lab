import java.util.Scanner;

public class TicTacToeNonAIMagicSquare {
    static final int SIZE = 9;
    static final int[] MOVETABLE = new int[19683];
    static int[] board = new int[SIZE];

    // Magic square positions: numbers from 1 to 9 in magic square order
    static final int[] MAGIC = {
        8, 1, 6,
        3, 5, 7,
        4, 9, 2
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializeMoveTable();

        boolean isHumanTurn = true;
        while (true) {
            printBoard();
            if (isGameOver()) break;

            if (isHumanTurn) {
                System.out.println("Your turn (X). Enter position (1-9): ");
                int move = scanner.nextInt() - 1;
                if (move < 0 || move >= SIZE || board[move] != 0) {
                    System.out.println("Invalid move! Try again.");
                    continue;
                }
                board[move] = 1;
            } else {
                System.out.println("AI (O) is making a move...");
                int nextState = MOVETABLE[convertBoardToDecimal(board)];
                board = convertDecimalToBoard(nextState);
            }

            isHumanTurn = !isHumanTurn;
        }
        scanner.close();
    }

    public static void initializeMoveTable() {
        for (int i = 0; i < MOVETABLE.length; i++) {
            int[] tempBoard = convertDecimalToBoard(i);
            MOVETABLE[i] = getNextMove(tempBoard);
        }
    }

    public static int convertBoardToDecimal(int[] board) {
        int decimal = 0;
        for (int i = 0; i < SIZE; i++) {
            decimal = decimal * 3 + board[i];
        }
        return decimal;
    }

    public static int[] convertDecimalToBoard(int decimal) {
        int[] tempBoard = new int[SIZE];
        for (int i = SIZE - 1; i >= 0; i--) {
            tempBoard[i] = decimal % 3;
            decimal /= 3;
        }
        return tempBoard;
    }

    public static int getNextMove(int[] board) {
        for (int i = 0; i < SIZE; i++) {
            if (board[i] == 0) {
                board[i] = 2;
                if (isWinning(board, 2)) {
                    return convertBoardToDecimal(board);
                }
                board[i] = 0;
            }
        }
        for (int i = 0; i < SIZE; i++) {
            if (board[i] == 0) {
                board[i] = 1;
                if (isWinning(board, 1)) {
                    board[i] = 2;
                    return convertBoardToDecimal(board);
                }
                board[i] = 0;
            }
        }
        for (int i = 0; i < SIZE; i++) {
            if (board[i] == 0) {
                board[i] = 2;
                return convertBoardToDecimal(board);
            }
        }
        return convertBoardToDecimal(board);
    }

    public static boolean isWinning(int[] board, int player) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = i + 1; j < SIZE; j++) {
                for (int k = j + 1; k < SIZE; k++) {
                    if (board[i] == player && board[j] == player && board[k] == player) {
                        int sum = MAGIC[i] + MAGIC[j] + MAGIC[k];
                        if (sum == 15) return true;
                    }
                }
            }
        }
        return false;
    }

    public static void printBoard() {
        System.out.println("\nCurrent Board:");
        System.out.println("-------------");
        for (int i = 0; i < SIZE; i++) {
            char mark = board[i] == 1 ? 'X' : (board[i] == 2 ? 'O' : '-');
            System.out.print("| " + mark + " ");
            if (i % 3 == 2) {
                System.out.println("|");
                System.out.println("-------------");
            }
        }
    }

    public static boolean isGameOver() {
        if (isWinning(board, 1)) {
            printBoard();
            System.out.println("\nHuman (X) wins!");
            return true;
        } else if (isWinning(board, 2)) {
            printBoard();
            System.out.println("\nAI (O) wins!");
            return true;
        }

        for (int cell : board) {
            if (cell == 0) return false;
        }

        printBoard();
        System.out.println("\nIt's a draw!");
        return true;
    }
}
