public class Solver {
    private int moves;
    private MinPQ<BoardComparable> origPQ;
    private MinPQ<BoardComparable> twinPQ;

    private java.util.ArrayDeque<Board> steps;

    private class BoardComparable implements Comparable {
        private int moves;
        private int priority;
        
        private BoardComparable prev;
        private Board board;

        public BoardComparable(Board b, BoardComparable p) {
            prev = p;
            if (prev == null)
                moves = 0;
            else
                moves = prev.moves + 1;

            board = b;
            //priority = moves + b.hamming();
            priority = moves + b.manhattan();
        }

        public int compareTo(Object that) {
            return priority - ((BoardComparable) that).priority;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        steps = new java.util.ArrayDeque<Board>();
        origPQ = new MinPQ<BoardComparable>();
        twinPQ = new MinPQ<BoardComparable>();

        origPQ.insert(new BoardComparable(initial, null));
        twinPQ.insert(new BoardComparable(initial.twin(), null));
        
        while (true) {
            BoardComparable obc = origPQ.delMin();
            BoardComparable tbc = twinPQ.delMin();

            if (obc.board.isGoal()) {
                BoardComparable current = obc;
                while (current != null) {
                    steps.push(current.board);
                    current = current.prev;
                }
                moves = obc.moves;
                return;
            }

            if (tbc.board.isGoal()) {
                steps = null;
                moves = -1;
                return;
            }

            for (Board b : obc.board.neighbors()) {
                if (obc.prev != null && b.equals(obc.prev.board))
                    continue;

                origPQ.insert(new BoardComparable(b, obc));
            }
            
            for (Board b : tbc.board.neighbors()) {
                if (tbc.prev != null && b.equals(tbc.prev.board))
                    continue;

                twinPQ.insert(new BoardComparable(b, tbc));
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return steps != null;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        return steps;
    }
      
    // solve a slider puzzle
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
