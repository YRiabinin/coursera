/******************************************************************************
 *  Name:    Eugene Riabinin
 *  NetID:   er
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  PercolationStats app
 ******************************************************************************/
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int t;
    private double mean;
    private double stddev;
    
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (trials < 0) {
            throw new IllegalArgumentException();
        } else {
            this.t = trials;
            double[] openSites = new double[trials];
            int counter = 0;
            while (counter < trials) {
                Percolation p = new Percolation(n);
                do {
                    p.open(StdRandom.uniform(1, n + 1), 
                           StdRandom.uniform(1, n + 1));
                } while (!p.percolates());
                openSites[counter] = p.numberOfOpenSites()/Math.pow(n, 2);
                counter++;
            }
            this.mean = StdStats.mean(openSites);
            this.stddev = StdStats.stddev(openSites);
        }
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }       
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddev;
    }
   
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean - (1.96 * this.stddev) / Math.sqrt(this.t);
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean + (1.96 * this.stddev) / Math.sqrt(this.t);
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), 
                                                   Integer.parseInt(args[1]));
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + 
                           "," + ps.confidenceHi() + "]");
    }
}