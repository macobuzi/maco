/**
   Complete search: a. every j, count all i | i<=j and sum A[i..j] = k -> O(n^3) 

       b. O(n^2) if use prefix sum
         S[i][j] = PS[i] - PS[j-1]
       F(j) = #count of CSA end at j
	   ans = sum of F(j) | 0 <=j <n
       Op: how to do the count # of j in O(1) ?

       c. pre-count O(n)
	   F(j) = list (or #) prefix sum i | PF[j] - k = PF[i]
	   
	   
      PF(i)
       4          x    #
       3               |
	   2        x      |k so A[0..3] and A[1..3] are the one
       1    x x        #
       0  x 
         -1 0 1 2 3 -> i
 */

public class LC_560 {
    public int subarraySum(int[] nums, int k) {
		int n = nums.length;
		Map<Integer, Integer> sumCnt = new HashMap<>();
		int sum = 0;
		sumCnt.put(0, 1);
		
		int matchCnt = 0;
		for (int i=0; i<n; i++) {
			sum += nums[i];
			matchCnt += sumCnt.getOrDefault(sum - k, 0);
			sumCnt.put(sum, sumCnt.getOrDefault(sum, 0) + 1);
		}
		return matchCnt;
	}
}
