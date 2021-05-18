import java.util.Iterator;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class Bag<Item> implements Iterable<Item> {
	private Node<Item> first;
	private int n;
	
	private static class Node<Item> {
		private Node<Item> next;
		private Item item;
	}

	public void add(Item item) {
		Node<Item> t = first;
		first = new Node<Item>();
		first.item = item;
		first.next = t;
		n++;
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public int size() {
		return n;
	}
	
	public Iterator<Item> iterator() {
		return new BagIterator(first);
	}

    private class BagIterator implements Iterator<Item> {
        private Node<Item> p;

        public BagIterator(Node<Item> first) {
            p = first;
        }

        public boolean hasNext() {
            return p != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
			Item t = p.item;
            p = p.next;
			return t;
        }
    }
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Bag<String> b = new Bag<>();
		while (sc.hasNext()) {
			b.add(sc.next());
		}

		System.out.printf("Size of bag : %d\n", b.size());
		for (String s : b)
			System.out.printf("%s\n", s);
	}
}
