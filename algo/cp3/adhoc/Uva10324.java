import java.util.*;
import java.io.*;

public class Uva10324 {
	static class Kattio extends PrintWriter {
		private BufferedReader rd;
		private StringTokenizer st;
		private String line;

		public Kattio() {
			super(System.out);
			rd = new BufferedReader(new InputStreamReader(System.in));
		}

		public String next() {
			try {
				while (st == null || !st.hasMoreElements()) {
					line = rd.readLine();
					if (null == line)
						return null;
					st = new StringTokenizer(line);
				}
				return st.nextToken();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}
	}
	
	public static void main(String[] argv) {
		Kattio io = new Kattio();
		int tc = 0;
		String seq;
		while ((seq = io.next()) != null) {
			tc++;
			
			int sLen = seq.length();
			int[] pSums = new int[sLen];
			pSums[0] = seq.charAt(0) - '0';
			for (int j=1; j<sLen; j++) {
				pSums[j] = pSums[j-1] + (seq.charAt(j) - '0');
			}
			
			int qNum = io.nextInt();
			io.println("Case " + tc + ":");
			for (int i=0; i<qNum; i++) {
				int u = io.nextInt();
				int v = io.nextInt();
				int lo = Math.max(0, Math.min(u,v));
				int hi = Math.min(sLen, Math.max(u,v));
				int sum = pSums[hi];
				if (lo > 0) {
					sum -= pSums[lo-1];
				}
				if (sum == 0 || sum == (hi - lo + 1)) {
					io.println("Yes");
				} else {
					io.println("No");
				}
			}
		}
		io.close();
	}
}