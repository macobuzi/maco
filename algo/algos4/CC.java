import java.util.Scanner;

public class CC {
	private boolean[] marked;
	private Graph g;
	private int cnt;
	private int[] ids;
	
	public CC(Graph g) {
		this.g = g;
		int n = g.vertexNum();
		marked = new boolean[n];
		ids = new int[n];

		int id = 0;
		for (int v=0; v<n; v++)
			if (!marked[v]) {
				dfs(id++, v);
				cnt++;
			}
	}

	private void dfs(int id, int v) {
		if (marked[v]) return;
		marked[v] = true;
		ids[v] = id;
		for (int u : g.neighbors(v))
			dfs(id, u);
	}

	public boolean connected(int v, int w) {
		return ids[v] == ids[w];
	}

	public int count() {
		return cnt;
	}

	public int id(int v) {
		return ids[v];
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Graph g = new Graph(sc);
		CC cc = new CC(g);
		System.out.printf("CC count = %d\n", cc.count());
		System.out.printf("connected(4,7) = %b\n", cc.connected(4,7));
		System.out.printf("connected(9,12) = %b\n", cc.connected(9,12));
	}
}
