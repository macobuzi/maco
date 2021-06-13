import java.util.*;

/*
  String N <= 1000
  Contains only 'A', 'B', 'C'
  3 criteria:
    - Length N
	- 2 next seq <>
	- minimum C

  Best(c[0..n-1], t)
  Partial solution: c[0..i], t
  Backtrack to build each index i (0 <= i <= n-1) 
  Each build, if reach end then print else
    try c[i] = 'A' or 'B' 
    check dup and t + (n-i) / 4 < Best.t
    //dup(all substr length 1..i/2 end by a[i] that not duplicated)
	build i+1
*/
public class ACBSeq {
	private static class Config {
		public char[] sequence;
		public int cCount;

		public Config(int n) {
			sequence = new char[n];
		}
	}

	private static boolean potential(int i, int n, Config config, Config bestConfig) {
		if (config.cCount + (n - i) / 4 >= bestConfig.cCount)
			return false;
		for (int k=1; k<=(i+1)/2; k++) {
			// compare s[p1..p2-1] and s[p2..i] | i-k-1 = p2
			// 0 1 2 3 | i = 3
			// k <= 4 / 2 = 2
			// k = 1, p2 = 3 - 1 + 1 = 3, p1 = 3 - 1 = 2
			// k = 2, p2 = 3 - 2 + 1 = 2, p1 = 2 - 2 = 0
			int p2 = i - k + 1;
			int p1 = p2 - k;
			boolean same = true;
			// k = 2
			// j = 0, s[0]-s[2]
			// j = 1, s[1]-s[3]
			for (int j=0; j<k; j++) {
				if (config.sequence[p1 + j] != config.sequence[p2 + j]) {
					same = false;
					break;
				}
			}
			if (same)
				return false;
		}
		return true;
	}

	private static void backtrack(int i, int n, Config config, Config bestConfig) {
		for (int j=0; j<3; j++) {
			config.sequence[i] = (char)((int)'a' + j);
			if (j == 2)
				config.cCount++;
			if (potential(i, n, config, bestConfig)) {
				if (i == n-1) {
					bestConfig.sequence = config.sequence.clone();
					bestConfig.cCount = config.cCount;
				} else {
					backtrack(i+1, n, config, bestConfig);
				}
			}
			if (j == 2)
				config.cCount--;
		}
	}
	
	public static void main(String[] argv) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();

		Config bestConfig = new Config(n);
		bestConfig.cCount = n;
		backtrack(0, n, new Config(n), bestConfig);

		System.out.printf("Best config = %s, minC = %d\n",
						  new String(bestConfig.sequence),
						  bestConfig.cCount);
	}
}
