import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

/**
 * 
 * @author ClydeBe
 *
 */
public class KdTree {

	private Node root;
	private int size;
	private double minDist = Double.POSITIVE_INFINITY;
	private Point2D nearest;

	public KdTree() {
	}

	public static void main(String[] args) {
	}

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return size;
	}

	public void insert(Point2D p) {
		if (p == null) throw new IllegalArgumentException();
		root = oddInsert(root, p);
	}

	private Node oddInsert(Node x, Point2D key) {
		if (x == null) {
			size++;
			return new Node(key);
		}
		double cmp = key.x() - x.key.x();
		if (cmp <= 0 && !x.key.equals(key)) x.left = evenInsert(x.left, key);
		else if (cmp > 0) x.right = evenInsert(x.right, key);
		return x;
	}

	private Node evenInsert(Node x, Point2D key) {
		if (x == null) {
			size++;
			return new Node(key);
		}
		double cmp = key.y() - x.key.y();
		if (cmp <= 0 && !x.key.equals(key)) x.left = oddInsert(x.left, key);
		else if (cmp > 0) x.right = oddInsert(x.right, key);
		return x;
	}

	public boolean contains(Point2D p) {
		if (p == null) throw new IllegalArgumentException();
		return oddContains(root, p);
	}

	private boolean evenContains(Node x, Point2D key) {
		if (x == null) return false;
		double cmp = key.y() - x.key.y();
		if (cmp == 0 && x.key.equals(key)) return true;
		if (cmp <= 0) return oddContains(x.left, key);
		return oddContains(x.right, key);
	}

	private boolean oddContains(Node x, Point2D key) {
		if (x == null) return false;
		double cmp = key.x() - x.key.x();
		if (cmp == 0 && x.key.equals(key)) return true;
		if (cmp <= 0) return evenContains(x.left, key);
		return evenContains(x.right, key);
	}

	public void draw() {
		if (root == null) return;
		oddDraw(root, new RectHV(0, 0, 1, 1));
	}

	private void evenDraw(Node x, RectHV myRect) {
		if (x == null) return;
		new Point2D(myRect.xmin(), x.key.y()).drawTo(new Point2D(myRect.xmax(), x.key.y()));
		oddDraw(x.left, new RectHV(myRect.xmin(), myRect.ymin(), myRect.xmax(), x.key.y()));
		oddDraw(x.right, new RectHV(myRect.xmin(), x.key.y(), myRect.xmax(), myRect.ymax()));
	}

	private void oddDraw(Node x, RectHV myRect) {
		if (x == null) return;
		new Point2D(x.key.x(), myRect.ymin()).drawTo(new Point2D(x.key.x(), myRect.ymax()));
		evenDraw(x.left, new RectHV(myRect.xmin(), myRect.ymin(), x.key.x(), myRect.ymax()));
		evenDraw(x.right, new RectHV(x.key.x(), myRect.ymin(), myRect.xmax(), myRect.ymax()));
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) throw new IllegalArgumentException();
		ArrayList<Point2D> all = new ArrayList<Point2D>();
		oddRange(rect, root, all, new RectHV(0, 0, 1, 1));
		return all;
	}

	private void evenRange(RectHV rect, Node x, ArrayList<Point2D> list, RectHV pR) {
		if (x == null) return;
		if (rect.contains(x.key)) list.add(x.key);
		if (rect.ymax() < x.key.y() && rect.intersects(new RectHV(pR.xmin(), pR.ymin(), pR.xmax(), x.key.y())))
			oddRange(rect, x.left, list, new RectHV(pR.xmin(), pR.ymin(), pR.xmax(), x.key.y()));
		else if (rect.ymin() > x.key.y() && rect.intersects(new RectHV(pR.xmin(), x.key.y(), pR.xmax(), pR.ymax())))
			oddRange(rect, x.right, list, new RectHV(pR.xmin(), x.key.y(), pR.xmax(), pR.ymax()));
		else {
			if (rect.intersects(new RectHV(pR.xmin(), pR.ymin(), pR.xmax(), x.key.y())))
				oddRange(rect, x.left, list, new RectHV(pR.xmin(), pR.ymin(), pR.xmax(), x.key.y()));
			if (rect.intersects(new RectHV(pR.xmin(), x.key.y(), pR.xmax(), pR.ymax())))
				oddRange(rect, x.right, list, new RectHV(pR.xmin(), x.key.y(), pR.xmax(), pR.ymax()));
		}
	}

	private void oddRange(RectHV rect, Node x, ArrayList<Point2D> list, RectHV pR) {
		if (x == null) return;
		if (rect.contains(x.key)) list.add(x.key);
		if (rect.xmax() < x.key.x() && rect.intersects(new RectHV(pR.xmin(), pR.ymin(), x.key.x(), pR.ymax())))
			evenRange(rect, x.left, list, new RectHV(pR.xmin(), pR.ymin(), x.key.x(), pR.ymax()));
		else if (rect.xmin() > x.key.x() && rect.intersects(new RectHV(x.key.x(), pR.ymin(), pR.xmax(), pR.ymax())))
			evenRange(rect, x.right, list, new RectHV(x.key.x(), pR.ymin(), pR.xmax(), pR.ymax()));
		else {
			if (rect.intersects(new RectHV(pR.xmin(), pR.ymin(), x.key.x(), pR.ymax())))
				evenRange(rect, x.left, list, new RectHV(pR.xmin(), pR.ymin(), x.key.x(), pR.ymax()));
			if (rect.intersects(new RectHV(x.key.x(), pR.ymin(), pR.xmax(), pR.ymax())))
				evenRange(rect, x.right, list, new RectHV(x.key.x(), pR.ymin(), pR.xmax(), pR.ymax()));
		}

	}

	public Point2D nearest(Point2D p) {
		if (p == null) throw new IllegalArgumentException();
		if (root == null) return null;
		nearest = root.key;
		minDist = Double.POSITIVE_INFINITY;
		oddNearest(root, p, new RectHV(0, 0, 1, 1));
		return nearest;
	}

	private void oddNearest(Node x, Point2D p, RectHV rect) {
		if (x == null || rect.distanceSquaredTo(p) >= minDist) return;
		if (x.key.distanceSquaredTo(p) < minDist) {
			minDist = x.key.distanceSquaredTo(p);
			nearest = x.key;
		}
		RectHV left = new RectHV(rect.xmin(), rect.ymin(), x.key.x(), rect.ymax());
		RectHV right = new RectHV(x.key.x(), rect.ymin(), rect.xmax(), rect.ymax());
		if (p.x() <= x.key.x()) {
			evenNearest(x.left, p, left);
			evenNearest(x.right, p, right);
		} else {
			evenNearest(x.right, p, right);
			evenNearest(x.left, p, left);
		}
	}

	private void evenNearest(Node x, Point2D p, RectHV rect) {
		if (x == null || rect.distanceSquaredTo(p) >= minDist) return;
		if (x.key.distanceSquaredTo(p) < minDist) {
			minDist = x.key.distanceSquaredTo(p);
			nearest = x.key;
		}

		RectHV left = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), x.key.y());
		RectHV right = new RectHV(rect.xmin(), x.key.y(), rect.xmax(), rect.ymax());
		if (p.y() <= x.key.y()) {
			oddNearest(x.left, p, left);
			oddNearest(x.right, p, right);
		} else {
			oddNearest(x.right, p, right);
			oddNearest(x.left, p, left);
		}
	}

	private class Node {
		private final Point2D key;
		private Node left, right;

		public Node(Point2D key) {
			this.key = key;
		}
	}
}
