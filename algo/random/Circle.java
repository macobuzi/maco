import java.util.Stack;
import java.util.Arrays;
import java.util.Scanner;

public class Cycle {

	private boolean[] marked;
	private int[] edgeTo;
	private Stack<Integer> cycle;
	
	public Cycle(Graph g) {
		int v = g.vertexNum();
		marked = new boolean[v];
		
		if (hasParallelEdge(g))
			return;

		Arrays.fill(marked, false);
		edgeTo = new int[v];
		dfs(g, -1, 0);
	}

	private boolean hasParallelEdge(Graph g) {
		for (int v=0; v<g.vertexNum(); v++) {
			for (int u : g.neighbors(v)) {
				if (marked[u]) {
					// v->u twice
					cycle = new Stack<>();
					cycle.push(v);
					cycle.push(u);
					cycle.push(v);
					return true;
				}
				marked[u] = true;
			}
			Arrays.fill(marked, false);
		}
		return false;
	}

	private void dfs(Graph g, int p, int v) {
		marked[v] = true;
		edgeTo[v] = p;

		for (int v=0; v<g.vertexNum(); v++) {
			for (int u : g.neighbors(v)) {
				if (!marked[u]) {
					dfs(g, v, u);
				} else if (u != p) {           // ignore parallel edge case
					cycle = new Stack<>();
					int t = v;
					while (t != u) {
						cycle.push(t);
						t = edgeTo[t];
					}
					cycle.push(u);
					cycle.push(v);
				}
			}
		}
	}

	public boolean hasCycle() {
		return cycle != null;
	}

	public Iterable<Integer> cycle() {
		return cycle;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Graph g = new Graph(sc);
		Cycle c = new Cycle(g);

		if (g.hasCycle()) {
			System.out.printf("Graph has cycle: ");
			Stack<Integer> path = g.cycle();
			for (int v : 
		} else {
			System.out.printf("Graph is acyclic \n");
		}
	}
}
