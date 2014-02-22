public class Percolation {
    private int size;
    private boolean[][] blocks;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uftop;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        size = N;
        blocks = new boolean[N][N];
        uf = new WeightedQuickUnionUF(2+N*N);
        uftop = new WeightedQuickUnionUF(1+N*N);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = false;
            }
        }
    }

    private int getUFnumber(int i, int j) {
        return (i-1)*size + j;
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        if (i < 1 || i > size || j < 1 || j > size) {
            throw new IndexOutOfBoundsException();
        }
        if (isOpen(i, j)) {
            return;
        }
        blocks[i-1][j-1] = true;
        
        if (i == 1) {
            uf.union(0, getUFnumber(i, j));
            uftop.union(0, getUFnumber(i, j));
        }

        if (i == size) {
            uf.union(getUFnumber(i, j), size * size + 1);
        }

        if ((i - 1) >= 1 && isOpen(i-1, j)) {
            uf.union(getUFnumber(i, j), getUFnumber(i-1, j));
            uftop.union(getUFnumber(i, j), getUFnumber(i-1, j));
        }
        if ((i + 1) <= size && isOpen(i+1, j)) {
            uf.union(getUFnumber(i, j), getUFnumber(i+1, j));
            uftop.union(getUFnumber(i, j), getUFnumber(i+1, j));
        }
        if ((j - 1) >= 1 && isOpen(i, j-1)) {
            uf.union(getUFnumber(i, j), getUFnumber(i, j-1));
            uftop.union(getUFnumber(i, j), getUFnumber(i, j-1));
        }
        if ((j + 1) <= size && isOpen(i, j+1)) {
            uf.union(getUFnumber(i, j), getUFnumber(i, j+1));
            uftop.union(getUFnumber(i, j), getUFnumber(i, j+1));
        }
    }

    // is site (row i, column j) open?        
    public boolean isOpen(int i, int j) {
        if (i < 1 || i > size || j < 1 || j > size) {
            throw new IndexOutOfBoundsException();
        }
        return blocks[i-1][j-1];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if (i < 1 || i > size || j < 1 || j > size) {
            throw new IndexOutOfBoundsException();
        }
        return isOpen(i, j) && uftop.connected(0, getUFnumber(i, j));
    }

    // does the system 
    // percolate?
    public boolean percolates() {
        return uf.connected(0, size * size + 1);
    }
}
