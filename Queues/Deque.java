import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author ClydeBe
 *
 * @param <Item> The Data type stored in Deque
 */
public class Deque<Item> implements Iterable<Item> {

	private Node first = null;
	private Node last = null;
	private int size;

	public Deque() {
		first = new Node();
		last = first;
		size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void addFirst(Item item) {
		if (item == null) throw new IllegalArgumentException();
		size++;
		first.preview = new Node();
		first.preview.next = first;
		first.preview.value = item;
		first = first.preview;
	}

	public void addLast(Item item) {
		if (item == null) throw new IllegalArgumentException();
		size++;
		last.value = item;
		last.next = new Node();
		last.next.preview = last;
		last = last.next;
	}

	public Item removeFirst() {
		if (isEmpty()) throw new NoSuchElementException();
		size--;
		Item item = first.value;
		first = first.next;
		first.preview = null;
		return item;
	}

	public Item removeLast() {
		if (isEmpty()) throw new NoSuchElementException();
		size--;
		Item item = last.preview.value;
		last = last.preview;
		last.next = null;
		return item;
	}

	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Item> {
		Node current;

		public DequeIterator() {
			current = first;
		}

		@Override
		public boolean hasNext() {
			return current != last;
		}

		@Override
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			Item it = current.value;
			current = current.next;
			return it;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private class Node {
		public Item value;
		public Node next = null;
		public Node preview = null;

		Node() {
		}
	}
}
