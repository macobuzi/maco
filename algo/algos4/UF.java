import java.util.Iterator;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class UF {
	private int[] id;       // vertex's component id
	private int count;      // number of component
	private int[] rank;     // component rank

	public UF(int n) {
		id = new int[n];
		rank = new int[n];
		for (int i=0; i<n; i++) {
			id[i] = i;
			rank[i] = 1;
		}
		count = n;
	}

	public void union(int p, int q) {
		int pid = find(p);
		int qid = find(q);
		if (pid == qid)
			return;

		if (rank[pid] < rank[qid])
			id[pid] = qid;
		else if (rank[pid] > rank[qid])
			id[qid] = pid;
		else {
			id[qid] = pid;
			rank[pid]++;
		}
		count--;
	}

	public int find(int p) {
		int pid = p, t;
		while (id[pid] != pid)
			pid = id[pid];
		while (id[p] != pid) {
			t = id[p];
			id[p] = pid;
			p = t;
		}
		return pid;
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
		UF uf = new UF(n);

		while (n-- > 0) {
			int p = sc.nextInt();
			int q = sc.nextInt();
			if (uf.find(p) == uf.find(q))
				continue;
			uf.union(p, q);
			//System.out.printf("%d %d\n", p, q);
		}
		System.out.printf("%d components\n", uf.count());
	}
}
