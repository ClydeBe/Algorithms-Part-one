import java.util.LinkedList;

import edu.princeton.cs.algs4.MinPQ;

/**
 * @author ClydeBe
 */
public class Solver {
	private boolean solvable;
	private final LinkedList<Board> solution = new LinkedList<Board>();

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		if (initial == null) throw new IllegalArgumentException();
		MinPQ<Node> pq = new MinPQ<Node>();
		pq.insert(new Node(initial, null));
		pq.insert(new Node(initial.twin(), null));
		while (!pq.min().board.isGoal()) {
			Node min = pq.delMin();
			for (Board board : min.board.neighbors())
				if (min.preview == null || (min.preview != null && !min.preview.board.equals(board)))
					pq.insert(new Node(board, min));
		}
		Node iterate = pq.min();
		while (iterate != null) {
			solution.push(iterate.board);
			iterate = iterate.preview;
		}
		solvable = solution.get(0).equals(initial);
	}

	public boolean isSolvable() {
		return solvable;
	}

	public int moves() {
		return isSolvable() ? solution.size() - 1 : -1;
	}

	public Iterable<Board> solution() {
		return isSolvable() ? solution : null;
	}

	private class Node implements Comparable<Node> {
		private final Board board;
		private final Node preview;
		private int manhattan, heuristic;

		public Node(Board b, Node prev) {
			board = b;
			manhattan = b.manhattan();
			heuristic = prev == null ? 0 : prev.heuristic + 1;
			preview = prev;
		}

		@Override
		public int compareTo(Node n) {
			int p = manhattan + heuristic - n.manhattan - n.heuristic;
			return p == 0 ? manhattan - n.manhattan : p;
		}
	}

}