public class Board {
    private int[][] board;
    private int dim;

    // construct a board from an N-by-N array of blocks    
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null)
            throw new NullPointerException();
        dim = blocks[0].length;
        board = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                board[i][j] = blocks[i][j];
            }
        }
    }

    private int desireRow(int n) {
        if (n == 0)
            return dim - 1;
        return (n - 1) / dim;
    }

    private int desireColumn(int n) {
        if (n == 0)
            return dim - 1;
        return (n - 1) % dim;
    }

    private void swapNode(int[][] b, int thisRow, int thisColumn, int thatRow, int thatColumn) {
        int swap = b[thisRow][thisColumn];
        b[thisRow][thisColumn] = b[thatRow][thatColumn];
        b[thatRow][thatColumn] = swap;
    }

    // board dimension N
    public int dimension() {
        return dim;
    }

    // number of blocks out of place
    public int hamming() {
        int now = 0;
        int count = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                now++;
                if (now == dim * dim)
                    now = 0;
                if (board[i][j] == 0)
                    continue;
                if (board[i][j] != now)
                    count++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between blocks and goal                
    public int manhattan() {
        int count = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0)
                    continue;

                int dr = desireRow(board[i][j]);
                int dc = desireColumn(board[i][j]);
                count += Math.abs(i - dr);
                count += Math.abs(j - dc);
            }
        }
        return count;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int dr = desireRow(board[i][j]);
                int dc = desireColumn(board[i][j]);
                if (i != dr || j != dc)
                    return false;
            }
        }
        return true;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int[][] re = new int[dim][dim];
        int blankRow = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                re[i][j] = board[i][j];
                if (board[i][j] == 0)
                    blankRow = i;
            }
        }

        boolean done = false;
        for (int i = 0; i < dim; i++) {
            if (done)
                break;
            if (i == blankRow)
                continue;
            int swap = re[i][0];
            re[i][0] = re[i][1];
            re[i][1] = swap;
            done = true;
        }

        return new Board(re);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.dim != this.dim) return false;
        return java.util.Arrays.deepEquals(this.board, that.board);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        java.util.ArrayList<Board> re = new java.util.ArrayList<Board>();
        int[][] dump = new int[dim][dim];
        int blankRow = 0, blankColumn = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                dump[i][j] = board[i][j];
                if (dump[i][j] == 0) {
                    blankRow = i;
                    blankColumn = j;
                }
            }
        }

        if (blankRow - 1 >= 0) {
            swapNode(dump, blankRow, blankColumn, blankRow - 1, blankColumn);
            re.add(new Board(dump));
            swapNode(dump, blankRow, blankColumn, blankRow - 1, blankColumn);
        }

        if (blankRow + 1 < dim) {
            swapNode(dump, blankRow, blankColumn, blankRow + 1, blankColumn);
            re.add(new Board(dump));
            swapNode(dump, blankRow, blankColumn, blankRow + 1, blankColumn);
        }
        
        if (blankColumn - 1 >= 0) {
            swapNode(dump, blankRow, blankColumn, blankRow, blankColumn - 1);
            re.add(new Board(dump));
            swapNode(dump, blankRow, blankColumn, blankRow, blankColumn - 1);
        }

        if (blankColumn + 1 < dim) {
            swapNode(dump, blankRow, blankColumn, blankRow, blankColumn + 1);
            re.add(new Board(dump));
            swapNode(dump, blankRow, blankColumn, blankRow, blankColumn + 1);
        }
        return re;
    }

    // string representation of the board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
