import java.util.*;

public class CombiGen {
	private static int[] next(int[] conf, int n, int k) {
		/*
		  1 <= a[0] <= n-k+1
		  2 <= a[1] <= n-k+2
		  i+1 <= a[i] <= n-k+i+1
		  0 <= i < k
		 */

		// Find from right - candidate not max
		int candidate = -1;
		for (int i=k-1; i>=0; i--) {
			if (conf[i] < n-k+i+1) {
				candidate = i;
				break;
			}
		}
		
		// Not found, return null
		if (candidate == -1)
			return null;
		
		// increase candidate
		// and move conf[candidate+1..k-1] to minimum (=conf[candidate] + i + 1)
		conf[candidate]++;
		for (int i=candidate+1; i<k; i++)
			conf[i] = conf[i-1] + 1;
		
		return conf;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// read n,k
		int n = sc.nextInt();
		int k = sc.nextInt();
			
		// first config
		int[] conf = new int[k];
		for (int i=0; i<k; i++)
			conf[i] = i + 1;

		while (conf != null) {
			//print
			for (int i=0; i<k; i++) {
				System.out.print(conf[i] + " ");
			}
			System.out.println();
			conf = next(conf, n, k);
		}
	}
}
