import java.lang.*;
import java.io.*;
import java.util.*;

public class Sieve {
	
	public static void main(String[] args) throws Exception {
		BufferedReader br;
		InputStreamReader isr;
		int m, n, i, j;
		boolean[] s;
		String l;
		String[] p;

		isr = new InputStreamReader(System.in);
		br = new BufferedReader(isr);
		while ((l = br.readLine()) != null) {
			p = l.split(" ");
			m = Integer.parseInt(p[0]);
			n = Integer.parseInt(p[1]);

			s = new boolean[n+1];
			Arrays.fill(s, true);
			for (i=2; i<=n/2; i++)
				for (j=2; j<=n/i; j++)
					s[i * j] = false;
			for (i=2; i<=n; i++)
				if (i >=m && s[i])
					System.out.printf("%d\n", i);
		}
	}
}
