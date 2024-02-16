import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private double[] perc_values;
    private double trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials cannot be less than or equal to 0");
        }
        this.trials = trials;
        perc_values = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            double num_opened = 0.0;
            while (!p.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!(p.isOpen(row, col))) {
                    p.open(row, col);
                    num_opened++;
                }
            }
            perc_values[i] = num_opened / (n * n);
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(perc_values);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(perc_values);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double z = 1.96;
        return this.mean() - z * this.stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double z = 1.96;
        return this.mean() + z * this.stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {

        int n = 400;
        int trials = 1000;

        PercolationStats percstats = new PercolationStats(n, trials);

        System.out.println("mean = " + percstats.mean());
        System.out.println("stddev = " + percstats.stddev());
        System.out.println("95 Percent Confidence Interval = [" + percstats.confidenceLo() + ", "
                                   + percstats.confidenceHi() + "]");
        //

    }

}
