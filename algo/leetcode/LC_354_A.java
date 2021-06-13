class LC_354_A {
    private static final int MAX_SIZE = 10002;
    
    /*
       A[i] = [wi, hi]
       Sort first, then become
       Find longest strictly increase subsequence (LSIS)
       
       1. List all subsequence - check and get longest -> O(2^N * N) (power set)
       2. Backtrack build increase subsequence - get longest -> < O(2^N * N)
       3. DP: F(i) = LSIS end at i. Need to add dummy [10^4+1, 10^4+1] at the end.
          Target F(N+1)-1. -> O(N^2)
          F(i) = 1 if not exist j | j < i && A[j] < A[i]
                 1 + F(maxj) if exist j | j < i && A[j]  < A[i] && maxj=min(j)

	   Implementation for #3 solution
    */
    private static class Envelop implements Comparable<Envelop> {
        private int width;
        private int height;
        
        public Envelop(int w, int h) {
            width = w;
            height = h;
        }
        
        public int compareTo(Envelop other) {
            return width == other.width ? height - other.height : width - other.width;
        }
        
        public boolean strictlyBigger(Envelop other) {
            return width > other.width && height > other.height;
        }
    }
    
    public int maxEnvelopes(int[][] envelopes) {
        int n = envelopes.length;
        Envelop[] envelopArr = new Envelop[n+1];
        for (int i=0; i<n; i++) {
            envelopArr[i] = new Envelop(envelopes[i][0], envelopes[i][1]);
        }
        envelopArr[n] = new Envelop(MAX_SIZE, MAX_SIZE);
        Arrays.sort(envelopArr);
        
        int[] dp = new int[n+1];
		
		//O(N^2)
        for (int i=0; i<=n; i++) { 
            int maxLen = getMaxPrevLen(i, envelopArr, dp);
            dp[i] = maxLen + 1;
        }
        
        return dp[n] - 1;
    }
    
    // O(N)
    private int getMaxPrevLen(int i, Envelop[] envelopArr, int[] dp)  {
        int maxPrevLen = 0;
        for (int j=i-1; j>=0; j--) {
            if (envelopArr[i].strictlyBigger(envelopArr[j]) && maxPrevLen < dp[j]) {
                maxPrevLen = dp[j];
            }
        }
        return maxPrevLen;
    }
    
    /*
        A  = [2,3] [5,4] [6,4] [6,7] [M, M]
        dp =   1     2     3      3     4
        
        A  = [2,3] [M, M]
        dp =    1.   2
    */
}
