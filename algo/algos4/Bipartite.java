import java.util.Scanner;

public class Bipartite {
	private boolean isBipartite;
	private boolean[] marked;
	private boolean[] colors;

	public Bipartite(Graph g) {
		int n = g.vertexNum();
		isBipartite = true;
		marked = new boolean[n];
		colors = new boolean[n];

		for (int v=0; v<n; v++)
			if (!marked[v])
				dfs(g, v);
	}

	private void dfs(Graph g, int v) {
		marked[v] = true;

		for (int u : g.neighbors(v)) {
			if (!isBipartite)
				return;
			
			if (!marked[u]) {
				colors[u] = !colors[v];
				dfs(g, u);
			} else if (colors[u] == colors[v]) { // odd cycle
				isBipartite = false;
			}
		}
	}

	public boolean isBipartite() {
		return isBipartite;
	}

	public boolean color(int v) {
		return colors[v];
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Graph g = new Graph(sc);
		Bipartite bpt = new Bipartite(g);
		if (bpt.isBipartite()) {
			System.out.println("Is bipartite, color:");
			for (int i=0; i<g.vertexNum(); i++) {
				System.out.printf("%d %s \n", i, bpt.color(i) ? "A" : "B");
			}
		} else {
			System.out.println("Has odd cycle.. no bipartite");
		}
	}
}
