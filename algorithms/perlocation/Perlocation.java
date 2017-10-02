/******************************************************************************
 *  Name:    Eugene Riabinin
 *  NetID:   er
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Perlocation app
 ******************************************************************************/
public class Perlocation {
    private final int mapSize;
    private boolean[][] sitesMap;
    
    public Perlocation(int n) {
        this.mapSize = n + 1;
        this.sitesMap = new boolean[mapSize][mapSize];
    }
    
    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > 0 && row <= this.mapSize && col > 0 && col <= this.mapSize) {
            this.sitesMap[row][col] = true;
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        boolean result = false;
        if (row > 0 && row <= this.mapSize && col > 0 && col <= this.mapSize) {
            result = this.sitesMap[row][col];
        } else {
            throw new IllegalArgumentException();
        }
        return result;
    }
    
    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        return !this.isOpen(row, col);
    }
    
    // number of open sites
    public int numberOfOpenSites() {
        int counter = 0;
        for (int i = 1; i < this.mapSize; i++) {
            for (int j = 1; j < this.mapSize; j++) {
                if (this.sitesMap[i][j]) {
                    counter++;
                }
            }
        }
        return counter;
    }
    
    // does the system percolate?
    public boolean percolates() {
        double totalNum = Math.pow(this.mapSize - 1, 2);
        double quotient = this.numberOfOpenSites() / totalNum;
        return  quotient >= 0.593;
    }
    
    public static void main(String[] args) {
        Perlocation p = new Perlocation(5);
    }
}