import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

/**
 * 
 * @author ClydeBe
 *
 */
public class PointSET {

	private final SET<Point2D> set;

	public PointSET() {
		set = new SET<Point2D>();
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public int size() {
		return set.size();
	}

	public void insert(Point2D p) {
		if (p == null) throw new IllegalArgumentException();
		set.add(p);
	}

	public boolean contains(Point2D p) {
		if (p == null) throw new IllegalArgumentException();
		return set.contains(p);
	}

	public void draw() {
		for (Point2D p : set) p.draw();
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) throw new IllegalArgumentException();
		if (set.isEmpty()) return null;
		ArrayList<Point2D> range = new ArrayList<Point2D>();
		for (Point2D p : set) if (rect.contains(p)) range.add(p);
		return range;
	}

	public Point2D nearest(Point2D p) {
		if (p == null) throw new IllegalArgumentException();
		if (set.isEmpty()) return null;
		double distance = Double.POSITIVE_INFINITY;
		Point2D nearest = set.min();
		for (Point2D point : set) if (p.distanceTo(point) < distance) {
			distance = p.distanceTo(point);
			nearest = point;
		}
		return nearest;
	}
}