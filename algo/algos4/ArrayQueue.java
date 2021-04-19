import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ArrayQueue<Item> implements Iterable<Item> {
	private static final int INIT_CAPACITY = 8;

	private Item[] a;    // queue elements
	private int n;       // number of elements in queue
	private int first;   // index of first element (circular)
	private int last;    // index of last elementb (curcular)

	public ArrayQueue() {
		a = (Item[]) new Object[INIT_CAPACITY];
		first = last = n = 0;
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public int size() {
		return n;
	}

	private void resize(int capacity) {
		assert capacity >= n;

		Item[] copy = (Item[]) new Object[capacity];
		for (int i=0; i<copy.length; i++)
			copy[i] = a[(first + i) % a.length];
		a = copy;
		first = 0;
		last = n;
	}

	public void enqueue(Item item) {
		if (n == a.length)
			resize(2 * a.length);
		if (last == a.length)
			last = 0;
		a[last++] = item;
		n++;
	}

	public Item dequeue() {
		if (isEmpty())
			throw new NoSuchElementException("Queue underflow");
		if (first == a.length)
			first = 0;
		Item item = a[first];
		a[first++] = null;
		n--;
		return item;
	}

	public Iterator<Item> iterator() {
		return new ArrayQueueIterator();
	}

    private class ArrayQueueIterator implements Iterator<Item> {
        private int i;

        public ArrayQueueIterator() {
            i = 0;
        }

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[(first + i++) % a.length];
        }
    }
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayQueue<String> q = new ArrayQueue<>();
		
		while (sc.hasNext()) {
			String item = sc.next();
			if (!item.equals("-"))
				q.enqueue(item);
			else if (!q.isEmpty())
				System.out.printf("%s ", q.dequeue());		
		}
		System.out.printf("(%d left on queue)", q.size());
	}
}
