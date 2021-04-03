import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * 
 * @author ClydeBe
 *
 * @param <Item> The data type stored in RandomizedQueue
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item data[];
	private int last = 0;

	public RandomizedQueue() {
		data = (Item[]) new Object[2];
	}

	public boolean isEmpty() {
		return last == 0;
	}

	public int size() {
		return last;
	}

	public void enqueue(Item item) {
		if (item == null) throw new IllegalArgumentException();
		if (last == data.length) resize();
		data[last++] = item;
	}

	public Item dequeue() {
		if (isEmpty()) throw new NoSuchElementException();
		int a = StdRandom.uniform(0, last);
		Item it = data[a];
		data[a] = data[--last];
		data[last] = null;
		if (last < data.length / 4) resize();
		return it;
	}

	public Item sample() {
		if (isEmpty()) throw new NoSuchElementException();
		return data[StdRandom.uniform(0, last)];
	}

	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

	private void resize() {
		if (last == data.length) {
			Item[] aux = (Item[]) new Object[last * 2];
			for (int i = 0; i < last; i++) aux[i] = data[i];
			data = aux;
		} else {
			if (last < 2) return;
			Item[] aux = (Item[]) new Object[last / 2];
			for (int i = 0; i < last; i++) aux[i] = data[i];
			data = aux;
		}
	}

	private class RandomizedQueueIterator implements Iterator<Item> {
		int current;
		Item[] iterate;

		public RandomizedQueueIterator() {
			current = last - 1;
			iterate = (Item[]) new Object[last];
			for (int i = 0; i < last; i++) {
				iterate[i] = data[i];
			}
		}

		@Override
		public boolean hasNext() {
			return current > -1;
		}

		@Override
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			int a = current > 0 ? StdRandom.uniform(0, current + 1) : 0;
			Item it = iterate[a];
			iterate[a] = iterate[current--];
			return it;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

}