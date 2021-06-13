/**
  A, T 
  E = len(A) combination of sign with dup
  dup in A ? no, need to keep same order
  sign = {+ , -}
  
  a. O(2 ^ N)
  different ? order will give that
  
  N <= 20 -> backtrack
  
  A = 1,1,1,1,1    T = 3
  cnt = 0
  bt 4 -4 
    +1-1+1+1+1 

  a.BF->BT
  b.DP ? -1000 <= S <= 1000
         0<=sum(nums[i]) <= 1000
    N = len(S)
    M = 1000
    F[i][j] = # of way to reach A[i] with sum j | 0 <= i <= N and -1000 <= j <= 1000
	F[i][j] = F[i-1][j + A[i]] + F[i-1][j-A[i]]
    Target: F[N-1][S]
    Base: F[0][A[0]] = 1 F[0][-A[0]] = 1
	O(N * 2 * M)
*/
class TargetSum {
	private static final int MAX_SUM = 1000;
	private int cnt = 0;
	private int[][] tbl;
    
	public int findTargetSumWays(int[] nums, int S) {
		cnt = 0;
		//backtrack(0, 0, nums, S);
		dp(nums, S);
		return cnt;
	}
    
	private void backtrack(int i, int sum, int[] nums, int S) {
		if (i == nums.length) {
			if (sum == S) {
				cnt++;
			}
		} else {
			backtrack(i+1, sum - nums[i], nums, S);
			backtrack(i+1, sum + nums[i], nums, S);
		}
	}

	private void dp(int[] nums, int S) {
		int n = nums.length;
	    tbl = new int[n][2 * MAX_SUM+5];
		dpUpdate(0, nums[0]);
		dpUpdate(0, -nums[0]);
		for (int i=1; i<n; i++) {
			for (int j=-1000; j<=1000; j++) {
				dpUpdate(i, j, dpGet(i-1, j - nums[i]) + dpGet(i-1, j + nums[i]));
			}
		}
		cnt = dpGet(n-1, S);
	}
	
	private void dpUpdate(int i, int s, int cnt) {
		tbl[i][s + MAX_SUM] += cnt;
	}

	private void dpGet(int i, int s) {
		return tbl[i][s + MAX_SUM];
	}
}

