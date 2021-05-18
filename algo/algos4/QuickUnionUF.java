import java.util.Iterator;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class QuickUnionUF {
	private int[] id;   // vertex's component id
	private int count;      // number of component;

	public QuickUnionUF(int n) {
		id = new int[n];
		for (int i=0; i<n; i++)
			id[i] = i;
		count = n;
	}

	public void union(int p, int q) {
		int pid = find(p);
		int qid = find(q);
		if (pid == qid)
			return;

		id[pid] = qid;
		count--;
	}

	public int find(int p) {
		if (id[p] != p)
			return find(id[p]);
		return p;
	}

	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	public int count() {
		return count;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		QuickUnionUF uf = new QuickUnionUF(n);

		while (n-- > 0) {
			int p = sc.nextInt();
			int q = sc.nextInt();
			if (uf.find(p) == uf.find(q))
				continue;
			uf.union(p, q);
//			System.out.printf("%d %d\n", p, q);
		}
		System.out.printf("%d components\n", uf.count());
	}
}
