import java.util.Scanner;

public class BSearch {

	public static int rank(Comparable[] a, Comparable k) {
		int lo = 0;
		int hi = a.length - 1;

		while(lo <= hi) {
			int mid = lo + (hi - lo)/2;
			int cmp = a[mid].compareTo(k);
			if      (cmp < 0)   lo = mid + 1;
			else if (cmp > 0)   hi = mid - 1;
			else                return mid;
		}
		return lo;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Integer k = sc.nextInt();
		Integer n = sc.nextInt();
		Integer[] a = new Integer[n];

		for (int i=0; i<n; i++)
			a[i] = sc.nextInt();
		System.out.printf("Rank = %d\n", rank(a, k));
	}
}
