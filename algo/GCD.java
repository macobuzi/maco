import java.io.*;

class GCD {

	private static int gcd(int u, int v) {
		int t;

		while (u > 0) {
			if (u < v) {
				t = u;
				u = v;
				v = t;
			}
			u = u - v;
		}

		return v;
	}

	public static void main(String[] args) throws Exception {
		InputStreamReader isr;
		BufferedReader br;
		int x, y;
		String l;
		
		isr = new InputStreamReader(System.in);
		br = new BufferedReader(isr);
		while ((l = br.readLine()) != null) {
			String[] p = l.split(" ");
			x = Integer.parseInt(p[0]);
			y = Integer.parseInt(p[1]);
			if (x > 0 && y > 0)
				System.out.printf("%d %d %d\n", x, y, gcd(x,y));
		}
	}
}
