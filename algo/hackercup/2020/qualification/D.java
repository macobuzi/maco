import java.util.*;
import java.io.*;

public class A {
	private static final long MAX_COST = 1000000005;
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
		private static class Pair {
			int location;
			long minRefuelCost;

			public Pair(int location, long minRefuelCost) {
				this.location = location;
				this.minRefuelCost = minRefuelCost;
			}
		}
		
		public void run() {
			int n = in.nextInt();
			int m = in.nextInt();
			long[] costs = new long[n];
			for (int i=0; i<n; i++) {
				costs[i] = in.nextInt();
			}

			Deque<Pair> deque = new LinkedList<>();
			deque.addLast(new Pair(0, 0));
			for (int i=1; i<n; i++) {
				while(deque.size() > 0 && deque.peekFirst().location < (i - m)) {
					deque.removeFirst();
				}
				if (costs[i] > 0 && deque.size() > 0) {
					long minRefuelCost = deque.peekFirst().minRefuelCost + costs[i];
					while (deque.peekLast().minRefuelCost > minRefuelCost) {
						deque.removeLast();
					}
					deque.addLast(new Pair(i, minRefuelCost));
				}
			}
			long ans = deque.size() > 0 ? deque.peekFirst().minRefuelCost : -1;
			out.println(ans);
		}
	}
}
