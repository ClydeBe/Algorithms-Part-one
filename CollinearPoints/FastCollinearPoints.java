import java.util.Arrays;
import java.util.Stack;

/**
 * 
 * @author ClydeBe
 *
 */
public class FastCollinearPoints {

	private LineSegment[] ls;
	private Stack<lsProps> s;

	public FastCollinearPoints(Point[] points) {
		if (points == null) throw new IllegalArgumentException("Non null Array");
		Point[] usedArray = new Point[points.length];
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null) throw new IllegalArgumentException("Points must be not null");
			else usedArray[i] = points[i];
		}
		s = new Stack<lsProps>();
		if (points.length > 3) for (int i = 0; i < points.length; i++) allLineSegment(points[i], usedArray, i);
		ls = new LineSegment[s.size()];
		int i = 0;
		for (lsProps prop : s) ls[i++] = new LineSegment(prop.min, prop.max);
		s = null;
	}

	public int numberOfSegments() {
		return ls.length;
	}

	public LineSegment[] segments() {
		return ls.clone();
	}

	private void add(Point min, Point max) {
		int idx = -1;
		double slope = min.slopeTo(max);
		for (int i = 0; i < s.size(); i++) {
			lsProps p = s.elementAt(i);
			if (slope == p.slope && min.slopeTo(p.max) == slope) {
				idx = i;
				break;
			}
		}
		if (idx >= 0) {
			if (s.elementAt(idx).toReplace(min, max)) {
				lsProps old = s.remove(idx);
				s.push(new lsProps(min.slopeTo(max), old.min.compareTo(min) < 0 ? old.min : min,
						old.max.compareTo(max) > 0 ? old.max : max));
			}
		} else s.push(new lsProps(min.slopeTo(max), min, max));
	}

	private void allLineSegment(Point p, Point[] points, int current) {
		Arrays.sort(points, p.slopeOrder());
		int j = 2, k = 0;
		double slope = p.slopeTo(points[1]);
		Point min = p, max = p;
		while (j < points.length) {
			if (points[j] == null || p.compareTo(points[j]) == 0) throw new IllegalArgumentException("Equal points");
			if (slope == p.slopeTo(points[j])) {
				k++;
				min = min.compareTo(points[j]) < 0 ? min : points[j];
				max = max.compareTo(points[j]) > 0 ? max : points[j];
			} else {
				if (k >= 2) add(min, max);
				k = 0;
				min = p;
				max = p;
			}
			slope = p.slopeTo(points[j++]);
		}
		if (k >= 2) add(min, max);
	}

	private class lsProps {
		double slope;
		Point min, max;

		public lsProps(double slope, Point min, Point max) {
			this.slope = slope;
			this.min = min;
			this.max = max;
		}

		public boolean toReplace(Point min, Point max) {
			return this.min.compareTo(min) == 1 || this.max.compareTo(max) == -1;
		}
	}
}