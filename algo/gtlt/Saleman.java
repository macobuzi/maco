import java.util.*;

public class Saleman {
	public static class Circuit {
		public int[] path;
		public int cost;
	}

	public static void backtrack(
		int pi, int[][] graph, int[] path, int cost, boolean[] marked, Circuit best) {
		int n = graph.length;
		
		for (int v=0; v<n; v++) {
			if (!marked[v]) { // get possible value
				path[pi] = v;
				int newCost = pi == 0 ? cost : cost + graph[path[pi-1]][v];

				if (pi == n-1) { // end config
					newCost += graph[v][path[0]];
					if (newCost < best.cost) {
						best.cost = newCost;
						best.path = path.clone();
					}
				} else if (newCost < best.cost) { // partial config
					path[pi] = v;
					marked[v] = true;
					backtrack(pi+1, graph, path, newCost, marked, best);
					marked[v] = false;
				}
			}
		}
	}
	
	public static void main(String[] argv) {
		// read graph
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt(); // vertex
		int m = sc.nextInt(); // edge
		int[][] graph = new int[n][n];
		for (int i=0; i<n; i++) {
			Arrays.fill(graph[i], 10000);
		}
		for (int e=0; e<m; e++) {
			int u = sc.nextInt()-1;
			int v = sc.nextInt()-1;
			int w = sc.nextInt();
			graph[u][v] = w;
			graph[v][u] = w;
		}

		// partial solution
		int[] path = new int[n];
		boolean[] marked = new boolean[n];
		
		// best config
		Circuit best = new Circuit();
		best.cost = Integer.MAX_VALUE;
		
		// backtrack
		backtrack(0, graph, path, 0, marked, best);

		// print result
		System.out.printf("Best cost = %d :: ", best.cost);
		for (int v : best.path) {
			System.out.printf("%d -> ", v+1);
		}
		System.out.println(best.path[0]+1);
	}
}
