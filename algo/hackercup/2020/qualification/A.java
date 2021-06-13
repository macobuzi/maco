import java.util.*;
import java.io.*;

public class A {
	private static Scanner in = new Scanner(System.in);
	private static PrintWriter out = new PrintWriter(System.out);
	
	public static void main(String[] argv) {
		int t = in.nextInt();
		Solver solver = new Solver();
		for(int i=1; i<=t; i++) {
			out.printf("Case #%d: \n", i);
			solver.run();
		}
		out.flush();
	}

	private static class Solver {
		public void run() {
			int n = in.nextInt();
			char[] allowIn = in.next().toCharArray();
			char[] allowOut = in.next().toCharArray();

			boolean[] moveLeft = new boolean[n];
			boolean[] moveRight = new boolean[n];
			for (int u=1; u<n; u++) {
				if (allowOut[u]=='Y' && allowIn[u-1]=='Y')
					moveLeft[u] = true;
			}

			for (int u=0; u<n-1; u++) {
				if (allowOut[u]=='Y' && allowIn[u+1]=='Y')
					moveRight[u] = true;
			}

			boolean[] connected = new boolean[n];
			for (int u=0; u<n; u++) {
				Arrays.fill(connected, false);
				connected[u] = true;
				int left = u;
				while(moveLeft[left])
					connected[--left] = true;
				int right = u;
				while(moveRight[right])
					connected[++right] = true;
				for (int i=0; i<n; i++) {
					out.print(connected[i] ? 'Y' : 'N');
				}
				out.println();
			}
		}
	}
}
