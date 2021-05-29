import java.util.*;

public class CombiBT {
	private static void backtrack(int i, int[] conf, int n, int k) {
		// 1 <= conf[0] <= n-k+1
		// i+1 <= conf[i] <= n-k+i+1
		// 0 <= i < k
		for (int v=i+1; v <= n-k+i+1; v++) {
			conf[i] = v;
			if (i == k-1) {
				for (int j=0; j<k; j++)
					System.out.print(conf[j] + " ");
				System.out.println();
			} else {
				backtrack(i+1, conf, n, k);
			}
		}
	}
	
	public static void main(String[] argv) {
		Scanner sc = new Scanner(System.in);

		// read input
		int n = sc.nextInt();
		int k = sc.nextInt();
		
		int[] conf = new int[k];
		
		backtrack(0, conf, n, k);
	}
}
