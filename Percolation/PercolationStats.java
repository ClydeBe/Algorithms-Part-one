import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * 
 * @author ClydeBe
 *
 */
public class PercolationStats {

	private final double mean, dev, confLo, confHi;

	public PercolationStats(int n, int trials) {
		if (n < 1 || trials < 1) { throw new IllegalArgumentException(); }

		double[] percolationThresholds = new double[trials];

		for (int i = 0; i < trials; i++) {
			Percolation p = new Percolation(n);
			int row, col;
			do {
				row = StdRandom.uniform(1, n + 1);
				col = StdRandom.uniform(1, n + 1);
				p.open(row, col);
			} while (!p.percolates());
			percolationThresholds[i] = p.numberOfOpenSites() * 1.0d / (n * n);
		}
		mean = StdStats.mean(percolationThresholds);
		dev = StdStats.stddev(percolationThresholds);
		confLo = mean - 1.96 * dev / Math.sqrt(trials);
		confHi = mean + 1.96 * dev / Math.sqrt(trials);
	}

	// sample mean of percolation threshold
	public double mean() {
		return mean;
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return dev;
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return confLo;
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return confHi;
	}

	// test client (see below)
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]), trials = Integer.parseInt(args[1]);
		PercolationStats p = new PercolationStats(n, trials);
		StdOut.println("mean                    = " + p.mean());
		StdOut.println("stddev                  = " + p.stddev());
		StdOut.println("95% confidence interval = [ " + p.confLo + ", " + p.confHi + " ]");
	}

}