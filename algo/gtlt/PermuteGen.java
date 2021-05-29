import java.util.*;

public class ComboGen {
	private static int[] next(int[] conf) {
		int n = conf.length;
		// cliff <- find cliff
		int cliff = -1;
		for (int i=n-1; i>0; i--) {
			if (conf[i] > conf[i-1]) {
				cliff = i-1;
				break;
			}		
		}
		// not found return null
		if (cliff == -1)
			return null;
		// replace <- find replace in conf[cliff+1..n]
		int replace = cliff+1;
		for (int i=n-1; i>cliff; i--) {
			if (conf[i] > conf[cliff]) {
				replace = i;
				break;
			}
		}
		// swap cliff and replace; reverse conf[cliff+1..n]
		swap(conf, cliff, replace);
		reverse(conf, cliff+1, n-1);
		return conf;
	}

	private static void reverse(int[] conf, int lo, int hi) {
		int len = hi - lo + 1;
		// i <- 0..len / 2) : swap(conf, lo + i, hi - i)
		for (int i=0; i<len/2; i++)
			swap(conf, lo + i, hi - i);
	}

	private static void swap(int[] conf, int u, int v) {
		int tmp = conf[u];
		conf[u] = conf[v];
		conf[v] = tmp;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// read n
		int n = sc.nextInt();
		// conf <- 1,2..n
		int[] conf = new int[n];
		for (int i=1; i<=n; i++)
			conf[i-1] = i;

		while(conf != null) {
			for (int i=0; i<n; i++)
				System.out.printf("%d ", conf[i]);
			System.out.println();
			conf = next(conf);
		}
	}
}
