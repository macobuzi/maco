import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class DFPaths {

	private Graph g;
	private boolean[] visited;
	private int[] edgeTo;
	
	public DFPaths(Graph g, int s) {
		this.g = g;
		visited = new boolean[g.vertexNum()];
		edgeTo = new int[g.vertexNum()];
		dfs(-1, s);
	}

	private void dfs(int p, int v) {
		if (visited[v]) return;
		visited[v] = true;
		edgeTo[v] = p;
		for (int u : g.neighbors(v)) {
			dfs(v, u);
		}
	}
	
	public Iterable<Integer> pathTo(int v) {
		List<Integer> path = new ArrayList<>();
		int p = v;

		path.add(v);
		while ((p = edgeTo[p]) != -1)
			path.add(p);
		return path;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Graph g = new Graph(sc);
		DFPaths dfs = new DFPaths(g, 0);
		for (int v : dfs.pathTo(2))
			System.out.printf(" %d ", v);
		System.out.printf("\n");
	}
}
