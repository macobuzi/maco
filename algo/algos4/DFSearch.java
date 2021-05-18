import java.util.Scanner;

public class DFSearch {
	private Graph g;
	private boolean[] visited;
	private int cnt;
	
	public DFSearch(Graph g, int s) {
		this.g = g;
		visited = new boolean[g.vertexNum()];
		cnt = 0;
		dfs(s);
	}

	private void dfs(int v) {
		if (visited[v]) return;
		visited[v] = true;
		cnt++;
		for (int u : g.neighbors(v))
			dfs(u);
	}

	public boolean marked(int v) {
		return visited[v];
	}

	public int count() {
		return cnt - 1;
	}

	

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Graph g = new Graph(sc);
		DFSearch dfs = new DFSearch(g, 0);
		System.out.printf("Connected = %b\n", dfs.count() == g.vertexNum() - 1);
		System.out.printf("Num connected to 0 = %d\n", dfs.count());
	}
}

