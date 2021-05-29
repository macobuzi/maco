import java.util.*;

public class PermuteBT {
	private static void backtrack(int i, boolean[] used, int[] conf, int n) {
		for (int v=1; v<=n; v++) {
			if (!used[v]) {
				conf[i] = v;

				if (i == n-1) {
					for (int j=0; j<n; j++)
						System.out.print(conf[j] + " ");
					System.out.println();
				} else {
					used[v] = true;
					backtrack(i+1, used, conf, n);
					used[v] = false;
				}
			}
		}
	}
	
	public static void main(String[] argv) {
		Scanner sc = new Scanner(System.in);

		// read input
		int n = sc.nextInt();
		int[] conf = new int[n];
		boolean[] used = new boolean[n+1];
		
		backtrack(0, used, conf, n);
	}
}
