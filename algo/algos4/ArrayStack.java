import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ArrayStack<Item> implements Iterable<Item> {

	private static final int INIT_CAPACITY = 8;

	private Item[] a; // array of item
	private int n;    // number of elements in stack

	public ArrayStack() {
		a = (Item[]) new Object[INIT_CAPACITY];
		n = 0;
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public void push(Item item) {
		if (n == a.length)
			resize(2 * a.length);
		a[n++] = item;
	}

	public Item pop() {
		if (isEmpty())
			throw new NoSuchElementException("Stack underflow");
		Item t = a[n-1];
		a[--n] = null;    // remove for GC
		if (n > 0 && n == a.length / 4)
			resize(a.length / 2);
		return t;
	}

	public Item peek() {
		if (isEmpty())
			throw new NoSuchElementException("Stack underflow");
		return a[n-1];
	}

	private void resize(int capacity) {
		assert capacity >= n;

		Item[] copy = (Item[]) new Object[capacity];
		System.arraycopy(a, 0, copy, 0, n);
		a = copy;
	}

	public int size() {
		return n;
	}

	public Iterator<Item> iterator() {
		return new ArrayStackIterator();
	}

    private class ArrayStackIterator implements Iterator<Item> {
        private int i;

        public ArrayStackIterator() {
            i = n-1;
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[i--];
        }
    }

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayStack<String> stack = new ArrayStack<>();
		
		while (sc.hasNext()) {
			String item = sc.next();
			if (!item.equals("-")) {
				stack.push(item);
			}
			else if (!stack.isEmpty())
				System.out.printf("%s ", stack.pop());
		}
		System.out.printf("(%d left on stack)\n", stack.size());
		for (String s : stack)
			System.out.printf("%s \n", s);
	}
}
