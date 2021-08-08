import java.io.*;
import java.util.*;

public class Uva11547 {
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
		int t = io.nextInt();
		while (t-- > 0) {
			int n = io.nextInt();
			int ans = (Math.abs(((((n * 567)/9)+7492)*235)/47-498) / 10) % 10;
			io.println(ans);
		}
		io.close();
	}
}