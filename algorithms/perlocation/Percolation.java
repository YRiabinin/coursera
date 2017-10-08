/******************************************************************************
 *  Name:    Eugene Riabinin
 *  NetID:   er
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Perlocation class
 ******************************************************************************/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int matrixSize;
    private Site[][] sites;
    private WeightedQuickUnionUF wqUnion;
    
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.wqUnion = new WeightedQuickUnionUF(n * n + 2);
            this.matrixSize = n;
            this.sites = new Site[matrixSize + 1][matrixSize + 1];
            int idCounter = 1;
            for (int i = 1; i <= this.matrixSize; i++) {
                for (int j = 1; j <= this.matrixSize; j++) {
                    this.sites[i][j] = new Site(idCounter++);
                }
            }
        }
    }
    
    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > 0 && row <= this.matrixSize && col > 0 
            && col <= this.matrixSize) {
            Site site = this.sites[row][col];
            site.open();
            int sId = site.getId();
            this.sites[row][col] = site;
            if (row + 1 == this.matrixSize + 1) {
                this.wqUnion.union(sId, this.matrixSize * this.matrixSize + 1);
            } else if (row + 1 <= this.matrixSize && this.isOpen(row + 1, col)) {
                this.wqUnion.union(sId, this.sites[row + 1][col].getId());
            }
            
            if (row - 1 == 0) {
                this.wqUnion.union(sId, 0);
            } else if (row - 1 > 0 && this.isOpen(row - 1, col)) {
                this.wqUnion.union(sId, this.sites[row - 1][col].getId());
            }
            if (col + 1 <= this.matrixSize && this.isOpen(row, col + 1)) {
                this.wqUnion.union(sId, this.sites[row][col + 1].getId());
            }
            if (col - 1 > 0 && this.isOpen(row, col - 1)) {
                this.wqUnion.union(sId, this.sites[row][col - 1].getId());
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        boolean result = false;
        if (row > 0 && row <= this.matrixSize && col > 0 && col <= this.matrixSize) {
            result = this.sites[row][col].isOpen();
        } else {
            throw new IllegalArgumentException();
        }
        return result;
    }
    
    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        boolean result = false;
        if (row > 0 && row <= this.matrixSize && col > 0 && col <= this.matrixSize) {
            result = this.wqUnion.connected(0, this.sites[row][col].getId());
        } else {
            throw new IllegalArgumentException();
        }
        return result;
    }
    
    // number of open sites
    public int numberOfOpenSites() {
        int counter = 0;
        for (int i = 1; i <= this.matrixSize; i++) {
            for (int j = 1; j <= this.matrixSize; j++) {
                if (this.sites[i][j].isOpen()) {
                    counter++;
                }
            }
        }
        return counter;
    }
    
    // does the system percolate?
    public boolean percolates() {
        return this.wqUnion.connected(0, this.matrixSize * this.matrixSize + 1);
    }
    
    private class Site {
        private int id;
        private boolean isOpen;
        
        public Site(int id) {
            this.id = id;
            this.isOpen = false;
        }
        
        public int getId() {
            return this.id;
        }
        
        public void open() {
            this.isOpen = true;
        }    
        
        public boolean isOpen() {
            return this.isOpen;
        }
    }
}