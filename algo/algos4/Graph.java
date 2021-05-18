import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;

public class Graph {
	private LinkedList<Integer>[] adj;
	private int edgeCnt;

	public Graph(int v) {
		adj = new LinkedList[v];
		for (int i=0; i<v; i++)
			adj[i] = new LinkedList<Integer>();
		edgeCnt = 0;
	}

	public Graph(Scanner sc) {
		int v = sc.nextInt();
		adj = new LinkedList[v];
		for (int i=0; i<v; i++)
			adj[i] = new LinkedList<Integer>();
		edgeCnt = sc.nextInt();
		for (int i=0; i<edgeCnt; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			adj[a].add(b);
			adj[b].add(a);
		}
	}

	public int vertexNum() {
		return adj.length;
	}

	public int edgeNum() {
		return edgeCnt;
	}

	public void addEdge(int v, int u) {
		adj[v].add(u);
		adj[u].add(v);
		edgeCnt++;
	}

	public Iterable<Integer> neighbors(int v) {
		return adj[v];
	}

	public int degree(int v) {
		return adj[v].size();
	}

	public static int maxDegree(Graph g) {
		int max = 0;
		for (int i=0; i<g.vertexNum(); i++)
			max = Math.max(max, g.degree(i));
		return max;
	}

	public static int avgDegree(Graph g) {
		return 2 * g.edgeNum() / g.vertexNum(); 
	}

	public static int selfLoopNum(Graph g) {
		int cnt = 0;
		for (int v=0; v<g.vertexNum(); v++)
			for (int u : g.neighbors(v))
				if (v == u) cnt++;
		return cnt/2;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Graph g = new Graph(sc);
		System.out.printf("Max degree = %d\n", maxDegree(g));
		System.out.printf("Avg degree = %d\n", avgDegree(g));
		System.out.printf("Self loop num = %d\n", selfLoopNum(g));
	}
}
