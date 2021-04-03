import java.util.Stack;

/**
 * 
 * @author ClydeBe
 *
 */
public class BruteCollinearPoints {

	private LineSegment ls[];

	public BruteCollinearPoints(Point[] points) {
		if (points == null) throw new IllegalArgumentException("Array can't be null");
		Stack<LineSegment> allLs = new Stack<LineSegment>();
		if (points.length < 4) {
			if (points[0] == null) throw new IllegalArgumentException();
			for (int i = 0; i < points.length - 1; i++) for (int j = i + 1; j < points.length; j++)
				if (points[j] == null || points[j].compareTo(points[i]) == 0) throw new IllegalArgumentException();
		} else for (int i = 0; i < points.length - 3; i++) for (int j = i + 1; j < points.length - 2; j++)
			for (int k = j + 1; k < points.length - 1; k++) for (int l = k + 1; l < points.length; l++)
				if (isLineSegment(points[i], points[j], points[k], points[l]) != null)
					allLs.push(isLineSegment(points[i], points[j], points[k], points[l]));
		ls = new LineSegment[allLs.size()];
		int i = 0;
		for (LineSegment lineSegment : allLs) ls[i++] = lineSegment;
		allLs = null;
	}

	public int numberOfSegments() {
		return ls.length;
	}

	public LineSegment[] segments() {
		return ls.clone();
	}

	private LineSegment isLineSegment(Point p, Point q, Point r, Point s) {
		if (p == null || q == null || r == null || s == null || p.compareTo(q) == 0 || p.compareTo(r) == 0
				|| p.compareTo(s) == 0 || q.compareTo(r) == 0 || q.compareTo(s) == 0 || r.compareTo(s) == 0)
			throw new IllegalArgumentException("Points can't be null or equal to each others");
		return (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s))
				? new LineSegment(min(p, q, r, s), max(p, q, r, s))
				: null;
	}

	private Point min(Point p, Point q, Point r, Point s) {
		Point min = p.compareTo(q) < 0 ? p : q;
		min = min.compareTo(r) < 0 ? min : r;
		min = min.compareTo(s) < 0 ? min : s;
		return min;
	}

	private Point max(Point p, Point q, Point r, Point s) {
		Point max = p.compareTo(q) > 0 ? p : q;
		max = max.compareTo(r) > 0 ? max : r;
		max = max.compareTo(s) > 0 ? max : s;
		return max;
	}
}