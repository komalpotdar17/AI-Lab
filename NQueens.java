public class NQueens {
    final int N;

    public NQueens(int n) {
        this.N = n;
    }

    // Function to print the board
    void printSolution(int board[][]) {
        for (int[] row : board) {
            for (int cell : row)
                System.out.print(cell + " ");
            System.out.println();
        }
    }

    // Check if a queen can be placed at board[row][col]
    boolean isSafe(int board[][], int row, int col) {
        // Check this row on the left
        for (int i = 0; i < col; i++)
            if (board[row][i] == 1)
                return false;

        // Check upper diagonal on the left
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return false;

        // Check lower diagonal on the left
        for (int i = row, j = col; i < N && j >= 0; i++, j--)
            if (board[i][j] == 1)
                return false;

        return true;
    }

    // Solve N Queens using backtracking
    boolean solveNQUtil(int board[][], int col) {
        if (col >= N)
            return true;

        for (int i = 0; i < N; i++) {
            if (isSafe(board, i, col)) {
                board[i][col] = 1;

                if (solveNQUtil(board, col + 1))
                    return true;

                board[i][col] = 0; // backtrack
            }
        }

        return false;
    }

    // This function solves the N Queens problem using solveNQUtil()
    void solve() {
        int board[][] = new int[N][N];

        if (!solveNQUtil(board, 0)) {
            System.out.println("Solution does not exist");
            return;
        }

        printSolution(board);
    }

    // Main method
    public static void main(String args[]) {
        int n = 8; // You can change this to any value of N
        NQueens queen = new NQueens(n);
        queen.solve();
    }
}
