import java.util.*;
import java.io.*;

public class B {
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
		public void run() {
			// 2A,1B -> A (remove 1 pair(A,B))
			// 2B,1A -> B (remove 1 pair(A,B))
			// => #A >= n/2 && #B >= n/2
			int n = in.nextInt();
			char[] shards = in.next().toCharArray();
			
			int cntA = 0;
			int cntB = 0;
			for (char shard : shards) {
				if (shard == 'A')
					cntA++;
				if (shard == 'B')
					cntB++;
			}
			int minCnt = n/2;
			out.print(cntA >= minCnt && cntB >= minCnt ? 'Y' : 'N');
			out.println();
		}
	}
}
