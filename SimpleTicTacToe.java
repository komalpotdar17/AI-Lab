public class SimpleTicTacToe {
    static char[] board = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
    static final char PLAYER = 'O';
    static final char AI = 'X';

    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while (true) {
            printBoard();

            // Human move
            System.out.print("Enter your move (0-8): ");
            int move = scanner.nextInt();
            if (board[move] != ' ') {
                System.out.println("Invalid move!");
                continue;
            }
            board[move] = PLAYER;
            if (checkWinner(PLAYER)) {
                printBoard();
                System.out.println("You win!");
                break;
            }
            if (isDraw()) {
                printBoard();
                System.out.println("Draw!");
                break;
            }

            // AI move
            int aiMove = getBestMove();
            board[aiMove] = AI;
            System.out.println("AI played at: " + aiMove);
            if (checkWinner(AI)) {
                printBoard();
                System.out.println("AI wins!");
                break;
            }
            if (isDraw()) {
                printBoard();
                System.out.println("Draw!");
                break;
            }
        }
    }

    static void printBoard() {
        System.out.println();
        for (int i = 0; i < 9; i++) {
            System.out.print(" " + board[i]);
            if (i % 3 != 2) System.out.print(" |");
            if (i % 3 == 2 && i != 8) System.out.println("\n-----------");
        }
        System.out.println("\n");
    }

    static boolean checkWinner(char player) {
        int[][] wins = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // cols
            {0, 4, 8}, {2, 4, 6}             // diags
        };
        for (int[] w : wins) {
            if (board[w[0]] == player && board[w[1]] == player && board[w[2]] == player)
                return true;
        }
        return false;
    }

    static boolean isDraw() {
        for (char c : board) {
            if (c == ' ') return false;
        }
        return true;
    }

    static int getBestMove() {
        // 1. Win
        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                board[i] = AI;
                if (checkWinner(AI)) {
                    board[i] = ' ';
                    return i;
                }
                board[i] = ' ';
            }
        }

        // 2. Block
        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                board[i] = PLAYER;
                if (checkWinner(PLAYER)) {
                    board[i] = ' ';
                    return i;
                }
                board[i] = ' ';
            }
        }

        // 3. Take center
        if (board[4] == ' ') return 4;

        // 4. Take a corner
        int[] corners = {0, 2, 6, 8};
        for (int c : corners) {
            if (board[c] == ' ') return c;
        }

        // 5. Take a side
        int[] sides = {1, 3, 5, 7};
        for (int s : sides) {
            if (board[s] == ' ') return s;
        }

        return -1; // should never happen
    }
}