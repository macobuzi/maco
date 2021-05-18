import java.util.Scanner;

public class LinkedList<T> {

	public class Node {
		Node prev;
		Node next;
		T data;
	}

	private Node nil;
	
	public LinkedList() {
		nil = new Node();
		nil.prev = nil;
		nil.next = nil;
	}

	public void insert(T data) {
		Node x = new Node();
		x.data = data;
		
		x.next = nil.next;
		nil.next.prev = x;
		x.prev = nil;
		nil.next = x;
	}

	public Node indexOf(T data) {
		Node x = nil.next;
		int i = 0;
		while(x != nil) {
			if (data.equals(x.data))
				return x;
			x = x.next;
			i++;
		}
		return x;
	}

	public boolean isNil(Node x) {
		return x == nil;
	}

	public void remove(T data) {
		Node x = indexOf(data);
		x.prev.next = x.next;
		x.next.prev = x.prev;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int k = sc.nextInt();
		LinkedList<Integer> l = new LinkedList<>();
		while (sc.hasNext()) {
			l.insert(sc.nextInt());
		}
		
		LinkedList<Integer>.Node x = l.indexOf(k);
	    System.out.printf("%s %d\n", l.isNil(x) ? "Not Found" : "Found", k);

		l.remove(k);
		x = l.indexOf(k);
	    System.out.printf("%s %d\n", l.isNil(x) ? "Not Found" : "Found", k);
	}
}
