class LC_354_B {
    private static final int MAX_SIZE = 10002;
    
    /*
       A[i] = [wi, hi]
       Sort first, then become
       Find longest strictly increase subsequence (LIS)
       
       1. BF: List all subsequence - check and get longest -> O(2^N * N) (power set)
       2. BT: build increase subsequence - get longest -> < O(2^N * N)
       3. DP: F(i) = LSIS end at i. Need to add dummy [10^4+1, 10^4+1] at the end.
          Target F(N+1)-1. -> O(N^2)
          F(i) = 1 if not exist j | j < i && A[j] < A[i]
                 1 + F(maxj) if exist j | j < i && A[j]  < A[i] && maxj=min(j)
                 
       4. Greedy: reduce finding maxj to O(logN) instead of O(N) ?
	      L = length of running smallest LIS
          M[1..L] = element of running smallest LIS

	   Implementation for #4 solution
    */
    private static class Envelop implements Comparable<Envelop> {
        private int width;
        private int height;
        
        public Envelop(int w, int h) {
            width = w;
            height = h;
        }
        
        public int compareTo(Envelop other) {
			/* TRICK: sort so end of each width elements will be the one with smallest height */
            return width == other.width ? other.height - height : width - other.width;
        }
        
        public boolean strictlyBigger(Envelop other) {
            return width > other.width && height > other.height;
        }
    }
    
    public int maxEnvelopes(int[][] envelopes) {
        int n = envelopes.length;
        Envelop[] envelopArr = new Envelop[n];
        for (int i=0; i<n; i++) {
            envelopArr[i] = new Envelop(envelopes[i][0], envelopes[i][1]);
        }
        Arrays.sort(envelopArr);
        
		//O(NlogN)
		Envelop[] lis = new Envelop[n];
		int len = 0;
        for (int i=0; i<n; i++) {
            int rank = rank(envelopArr[i], lis, len);
            lis[rank] = envelopArr[i];
            if (rank == len) {
				len++;
			}
        }
        
        return len;
    }
    
    // O(logN)
    private int rank(Envelop current, Envelop[] lis, int len) {
		int lo = 0;
		int hi = len-1;
		while(lo <= hi) {
			int mid = (lo + hi) / 2;
			if (current.strictlyBigger(lis[mid])) {
				lo = mid + 1;
			} else {
				hi = mid - 1;
			}
		}
		return lo;
    }
    
    /*
        A = [2,100] [3,200] [4,300] [5, 250] [5, 400] [5,500] [6,360] [6, 370] [7, 380]
        LIS = [2,100] [3,200] [5, 250] [6, 370] [7,380]
        Len = 5
    */
}
