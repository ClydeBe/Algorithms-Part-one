import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * 
 * @author ClydeBe
 *
 */
public class Percolation {

	private WeightedQuickUnionUF wqu;
	private final int size;
	private int openSites;
	private boolean data[][];

	public Percolation(int n) {
		if (n < 1) { throw new IllegalArgumentException(); }
		wqu = new WeightedQuickUnionUF(n * n + 2);
		data = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			wqu.union(0, i + 1);
			wqu.union(n * n + 1, (n - 1) * n + i + 1);
		}
		size = n;
		openSites = 0;
	}

	public void open(int row, int col) {
		if (row < 1 || row > size || col < 1 || col > size) { throw new IllegalArgumentException(); }
		if (isOpen(row, col)) { return; }
		data[row - 1][col - 1] = true;
		openSites++;
		int point = (row - 1) * size + col;
		if (row > 1 && data[row - 2][col - 1]) wqu.union(point, point - size);
		if (row < size && data[row][col - 1]) wqu.union(point, point + size);
		if (col > 1 && data[row - 1][col - 2]) wqu.union(point, point - 1);
		if (col < size && data[row - 1][col]) wqu.union(point, point + 1);
	}

	public boolean isOpen(int row, int col) {
		if (row < 1 || row > size || col < 1 || col > size) { throw new IllegalArgumentException(); }
		return data[row - 1][col - 1];
	}

	public boolean isFull(int row, int col) {
		if (row < 1 || row > size || col < 1 || col > size) throw new IllegalArgumentException();
		return wqu.find((row - 1) * size + col) == wqu.find(0) && data[row - 1][col - 1];
	}

	public int numberOfOpenSites() {
		return openSites;
	}

	public boolean percolates() {
		if (size == 1) return wqu.find(0) == wqu.find(size * size + 1) && data[0][0];
		return wqu.find(0) == wqu.find(size * size + 1);
	}
}
