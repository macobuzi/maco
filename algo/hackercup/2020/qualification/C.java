import java.util.*;
import java.io.*;

public class A {
	private static Scanner in = new Scanner(System.in);
	private static PrintWriter out = new PrintWriter(System.out);
	
	public static void main(String[] argv) {
		int t = in.nextInt();
		Solver solver = new Solver();
		for(int i=1; i<=t; i++) {
			out.printf("Case #%d: ", i);
			solver.run();
		}
		out.flush();
	}
	
	private static class Solver {
		
		private static class Tree implements Comparable<Tree> {
			private int position;
			private int height;

			public Tree(int position, int height) {
				this.position = position;
				this.height = height;
			}

			public int getPosition() {
				return position;
			}

			public int getHeight() {
				return height;
			}

			public int compareTo(Tree other) {
				return position - other.position;
			}
		}
		
		public void run() {
			int n = in.nextInt();
			Tree[] trees = new Tree[n];
			for (int i=0; i<n; i++) {
			   int position = in.nextInt();
			   int height = in.nextInt();
			   trees[i] = new Tree(position, height);
			}

			// sort
			Arrays.sort(trees);

			// dp[j]: max length end at j
			Map<Integer, Integer> maxLenAtMap = new HashMap<>();
			int maxLen = 0;
			for (Tree tree : trees) {
				int fallLeftPos = tree.getPosition() - tree.getHeight();
				int fallRightPos = tree.getPosition() + tree.getHeight();
				maxLenAtMap.put(fallRightPos, Math.max(
									maxLenAtMap.getOrDefault(fallRightPos, 0),
									maxLenAtMap.getOrDefault(tree.getPosition(), 0) + tree.getHeight()));
				maxLenAtMap.put(tree.getPosition(), Math.max(
									maxLenAtMap.getOrDefault(tree.getPosition(), 0),
									maxLenAtMap.getOrDefault(fallLeftPos, 0) + tree.getHeight()));
				maxLen = Math.max(maxLen, Math.max(maxLenAtMap.get(fallRightPos), maxLenAtMap.get(tree.getPosition())));
			}
			out.println(maxLen);
		}
	}
}
