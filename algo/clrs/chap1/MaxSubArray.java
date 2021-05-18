import java.util.Scanner;

public class MaxSubArray {
	
	private static int buy;
	private static int sell;
	private static int max;

	public static void solveBF(int[] a) {
		// O(n^2)
		int n = a.length;
		buy = -1;
		sell = -1;
		max = 0;
		for (int i=0; i<n-1; i++) {
			for (int j=i+1; j<n; j++) {
				int diff = a[j] - a[i];
				if (diff > max) {
					max = diff;
					buy = i;
					sell = j;
				}
			}
		}
	}

	public static void solveDAC(int[] a) {
		// O(nlogn)
		int n = a.length - 1;
		int[] b = new int[n];
		for (int i=1; i<n; i++)
			b[i] = a[i] - a[i-1];
		int[] r = findMaxSubArray(b, 0, n-1);
		buy = r[0];
		sell = r[1];
		max = r[2];
	}

	private static int[] findMaxSubArray(int[] a, int lo, int hi) {
		if (lo == hi)
			return new int[] {lo, hi, a[lo]};
		int mid = lo + (hi - lo) / 2;
		int[] lr = findMaxSubArray(a, lo, mid);
		int[] rr = findMaxSubArray(a, mid+1, hi);
		int[] cr = findCrossSubArray(a, lo, mid, hi);

		if (lr[2] > rr[2] && lr[2] > cr[2])
			return lr;
		if (rr[2] > lr[2] && rr[2] > cr[2])
			return rr;
		return cr;
	}

	private static int[] findCrossSubArray(int[] a, int lo, int mid, int hi) {
		int lm = mid;
		int lms = 0;
		int ls = 0;
		for (int l=mid; l>=0; l--) {
			ls += a[l];
			if (ls > lms) {
				lm = l;
				lms = ls;
			}
		}
		int rm = mid+1;
		int rms = 0;
		int rs = 0;
		for (int r=mid+1; r<=hi; r++) {
			rs += a[r];
			if (rs > rms) {
				rm = r;
				rms = rs;
			}
		}
		return new int[] {lm, rm, lms + rms};
	}
	
	public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] a = new int[n];
		for (int i=0; i<n; i++)
			a[i] = sc.nextInt();

		solveDAC(a);
		if (buy != -1)
			System.out.printf("Transaction: p=%d, b=%d, s=%d\n", max, buy, sell);
		else
			System.out.printf("No solution\n");
	}
}
