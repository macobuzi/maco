import java.io.*;
import java.util.*;

public class Uva11498 {
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
		int k, n, m, x, y;
		while ((k = io.nextInt()) != 0) {
			n = io.nextInt();
			m = io.nextInt();
			while(k-- > 0) {
				x = io.nextInt();
				y = io.nextInt();
				if (x == n || y == m) {
					io.println("divisa");
				} else if (x < n && y > m) {
					io.println("NO");
				} else if (x > n && y > m) {
					io.println("NE");
				} else if (x < n && y < m) {
					io.println("SO");
				} else {
					io.println("SE");
				}
			}
		}
		io.close();
	}
}