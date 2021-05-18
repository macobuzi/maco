import java.util.Scanner;

public class QuickSelect {

	public static Comparable quickSelect(Comparable[] a, int k) {
		return quickSelect(a, 0, a.length-1, k);
	}

	private static Comparable quickSelect(Comparable[] a, int lo, int hi, int k) {
		if (lo == hi)
			return a[lo];
		int p = partition(a, lo, hi);
		int r = p - lo + 1;
		if (r == k)
			return a[p];
		if (r > k)
			return quickSelect(a, lo, p-1, k);
		else
			return quickSelect(a, p+1, hi, r - k);
	}

	private static int partition(Comparable[] a, int lo, int hi) {
		Comparable v = a[hi];
		int i = lo-1;
		int j = lo;

		/*
		  Invariant:
		  a[lo..i] <= v
		  a[i+1..j-1] > v
		  a[hi] = v
		*/
		for (; j<hi; j++)
			if (a[j].compareTo(v) <= 0) {
				swap(a, ++i, j);
			}
		swap(a, i+1, hi);
		return i+1;
	}

	private static void swap(Comparable[] a, int i, int j) {
		Comparable t = a[j];
		a[j] = a[i];
		a[i] = t;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int k = sc.nextInt();		
		Integer[] a = new Integer[n];
		for (int i=0; i<n; i++)
			a[i] = sc.nextInt();
		System.out.printf("a[%d] = %d\n", k, quickSelect(a, k));
	}
}
