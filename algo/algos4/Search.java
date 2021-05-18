import java.util.Scanner;

public class DFSearch {
	
	public DFSearch(Graph G, int s) {

	}
		
	public boolean marked(int v) {

	}

	public int count() {

	}

	public static void main(String[] args) {
		Scanner sc = new Scanner();
		Graph g = new Graph(sc);
		DFSearch dfs = new DFSearch(g, 0);
		System.out.printf("5 connected to 0 ? %s\n", dfs.marked(5) ? "Yes" : "No");
		System.out.printf("count = %d\n", dfs.count());
	}
}
