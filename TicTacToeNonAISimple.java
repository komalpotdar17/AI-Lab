import java.util.Arrays;
import java.util.Scanner;

public class TicTacToeNonAISimple {
    static final int SIZE = 9; 
    static final int[] MOVETABLE = new int[19683]; 
    static int[] board = new int[SIZE]; 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializeMoveTable(); 

        boolean isHumanTurn = true;
        while (true) {
            printBoard();
            if (isGameOver()) {
                break;
            }

            if (isHumanTurn) {
                System.out.println("Your turn (X). Enter position (1-9): ");
                int move = scanner.nextInt() - 1;

                if (move < 0 || move >= SIZE || board[move] != 0) {
                    System.out.println("Invalid move! Try again.");
                    continue;
                }
                board[move] = 1; 
            } else {
                System.out.println("Computer (O) is making a move...");
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
                return convertBoardToDecimal(board);
            }
        }
        return convertBoardToDecimal(board);
    }

    public static void printBoard() {
        System.out.println("\nCurrent Board:");
        for (int i = 0; i < SIZE; i++) {
            char mark = board[i] == 1 ? 'X' : (board[i] == 2 ? 'O' : '-');
            System.out.print(" " + mark + " ");
            if (i % 3 == 2) System.out.println();
        }
    }

    public static boolean isGameOver() {
        int[][] winConditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, 
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, 
            {0, 4, 8}, {2, 4, 6}             
        };

        for (int[] win : winConditions) {
            if (board[win[0]] != 0 && board[win[0]] == board[win[1]] && board[win[1]] == board[win[2]]) {
                System.out.println("\nWinner: " + (board[win[0]] == 1 ? "Human (X)" : "Computer (O)"));
                return true;
            }
        }

        for (int cell : board) {
            if (cell == 0) return false; 
        }

        System.out.println("\nIt's a draw!");
        return true;
    }
}
