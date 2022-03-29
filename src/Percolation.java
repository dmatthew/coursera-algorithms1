import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int openCount;
    private boolean[] grid;
    private final WeightedQuickUnionUF uf;
    private final int gridSize;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Argument n must be greater than 0");
        uf = new WeightedQuickUnionUF(n * n + 2); // Plus 2 for virtual top and bottom.
        gridSize = n;
        grid = new boolean[n  * n + 2]; // Plus 2 for virtual top and bottom.
        // for (int i = 0; i < n * n; i++)
        //     grid[i] = false;
    }

    private int xyTo1D(int row, int col) {
        return (row - 1) * gridSize + col - 1;
    }

    private boolean isValidIndex(int row, int col) {
        return row <= gridSize && row > 0 && col <= gridSize && col > 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isValidIndex(row, col)) throw new IllegalArgumentException("Arguments row and col must be in range.");
        if (isOpen(row, col))
            return;
        
        grid[xyTo1D(row, col)] = true;
        openCount++;
        if (isValidIndex(row, col + 1) && isOpen(row, col + 1))
            uf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        
        if (isValidIndex(row, col - 1) && isOpen(row, col - 1))
            uf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        
        if (isValidIndex(row + 1, col) && isOpen(row + 1, col))
            uf.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        
        if (isValidIndex(row - 1, col) && isOpen(row - 1, col))
            uf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        
        // connect to top node
        if (row == 1)
            uf.union(xyTo1D(row, col), xyTo1D(gridSize, gridSize) + 1);

        if (row == gridSize)
            uf.union(xyTo1D(row, col), xyTo1D(gridSize, gridSize) + 2);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isValidIndex(row, col)) throw new IllegalArgumentException("Arguments row and col must be in range.");
        return grid[xyTo1D(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isValidIndex(row, col)) throw new IllegalArgumentException("Arguments row and col must be in range.");
        return isOpen(row, col) 
            && uf.find(xyTo1D(row, col)) == uf.find(gridSize * gridSize);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(gridSize * gridSize) == uf.find(gridSize * gridSize + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        // Percolation p = new Percolation(5);
        // p.open(1, 1);
        // p.open(1, 2);
        // int x = p.uf.find(0);
        // int y = p.uf.find(1);
        // System.out.println(x);
        // System.out.println(y);
        // System.out.println("count: " + p.numberOfOpenSites());
    }
}
