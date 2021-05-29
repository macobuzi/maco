import java.util.*;

public class NumAnalyse {

	private static void backtrack(int i, int left, int prev, int[] conf) {
		for (int v=prev; v<=left; v++) {
			conf[i] = v;
			if (v == left) {
				for (int j=0; j<=i; j++)
					System.out.print(conf[j] + " ");
				System.out.println();
			} else {
				backtrack(i+1, left - v, v, conf);
			}
		}
	}
	
	public static void main(String[] argv) {
		Scanner sc = new Scanner(System.in);

		// read input
		int n = sc.nextInt();
		int[] conf = new int[n];

		backtrack(0, n, 1, conf);
	}
}
