import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author ClydeBe
 */
public final class Board {

	private final int[][] board;

	public Board(int[][] tiles) {
		board = new int[tiles.length][];
		for (int i = 0; i < tiles.length; i++) board[i] = Arrays.copyOf(tiles[i], tiles[i].length);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(board.length + "\n");
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) sb.append(" " + board[i][j]);
			sb.append("\n");
		}
		return sb.toString();
	}

	public int dimension() {
		return board.length;
	}

	public int hamming() {
		int j = 0, hamming = 0;
		for (int[] is : board) for (int i : is) if (i != ++j && i != 0) hamming++;
		return hamming;
	}

	public int manhattan() {
		int manathan = 0;
		for (int i = 0; i < board.length; i++) for (int j = 0; j < board.length; j++) {
			if (board[i][j] == 0) continue;
			int y = (board[i][j] % board.length == 0 ? board.length : board[i][j] % board.length) - 1;
			int x = (board[i][j] - y) / board.length;
			manathan += Math.abs(i - x) + Math.abs(j - y);
		}
		return manathan;
	}

	public boolean isGoal() {
		for (int i = 0, value = 1; i < board.length; i++) for (int j = 0; j < board.length; j++) {
			if (i == board.length - 1 && j == board.length - 1 && board[i][j] != 0) return false;
			else if (board[i][j] != value++) return false;
		}
		return true;
	}

	public boolean equals(Object y) {
		if (y == this) return true;
		if (y == null || !(y instanceof Board) || ((Board) y).board.length != board.length) return false;
		int[][] that = ((Board) y).board;
		for (int i = 0; i < that.length; i++)
			for (int j = 0; j < that.length; j++) if (that[i][j] != board[i][j]) return false;
		return true;
	}

	public Iterable<Board> neighbors() {
		LinkedList<Board> neighbors = new LinkedList<Board>();
		String[] z = locate(0).split(" ");
		int x = Integer.parseInt(z[0]);
		int y = Integer.parseInt(z[1]);
		if (x - 1 >= 0) neighbors.add(new Board(exch(x, y, x - 1, y)));
		if (x + 1 <= board.length - 1) neighbors.add(new Board(exch(x, y, x + 1, y)));
		if (y - 1 >= 0) neighbors.add(new Board(exch(x, y - 1, x, y)));
		if (y + 1 <= board.length - 1) neighbors.add(new Board(exch(x, y + 1, x, y)));
		return neighbors;
	}

	public Board twin() {
		if (board[0][0] != 0 && board[0][1] != 0) return new Board(exch(0, 0, 0, 1));
		return new Board(exch(1, 1, 1, 0));
	}

	private String locate(int value) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] == value) return i + " " + j;
			}
		}
		return null;
	}

	private int[][] exch(int i1, int j1, int i2, int j2) {
		int[][] to = new int[board.length][board.length];
		for (int i = 0; i < to.length; i++) {
			for (int j = 0; j < to.length; j++) {
				to[i][j] = board[i][j];
			}
		}
		to[i1][j1] = board[i2][j2];
		to[i2][j2] = board[i1][j1];
		return to;
	}
}