package idv.jimmyken793.pttbot;

import java.util.NoSuchElementException;

class FIFOSet {
	boolean[] contain;
	int[] set;
	int front, rear;

	public void add(int v) {
		if (contain[v] == true) {
			return;
		}
		// XXX: 沒有檢查空間是否足夠
		set[rear] = v;
		contain[v] = true;

		if (++rear == set.length) {
			rear = 0;
		}
	}

	public int remove() {
		int v;

		if (front == rear) {
			throw new NoSuchElementException();
		}

		v = set[front];
		contain[v] = false;

		if (++front == set.length) {
			front = 0;
		}

		return v;
	}

	public boolean isEmpty() {
		return (front == rear);
	}

	/**
	 * @param range
	 *            Set 的值域 1...(range - 1)
	 */
	public FIFOSet(int range) {
		front = rear = 0;

		// 假設最多 256 column
		contain = new boolean[range];
		set = new int[range];

		for (int i = 0; i < contain.length; i++) {
			contain[i] = false;
		}
	}
}