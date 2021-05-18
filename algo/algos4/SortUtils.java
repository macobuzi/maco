import java.util.Scanner;

public class SortUtils {

	public static void selectionSort(Comparable[] a) {
		int n = a.length;
		for (int i=0; i<n-1; i++) {
			int min = i;
			for (int j=i+1; j<n; j++)
				if (less(a, j, min))
					min = j;
			exch(a, i, min);
		}
	}

	public static void insertionSort(Comparable[] a) {
		int n = a.length;
		for (int i=1; i<n; i++) {
			int j=i;
			while(j>0 && less(a, j, j-1)) {
				exch(a, j, j-1);
				j--;
			}
		}
	}

	public static void shellSort(Comparable[] a) {
		int n = a.length;
		int k = 1;
		int h = 1;
		
		// 3x+1 increase sequence
		while (h < n) {
			h = h * 2;
		}

		while (h > 0) {
			for (int i=h; i<n; i++) {
				for (int j=i; j>=h && less(a, j, j-h); j-=h)
					exch(a, j, j-h);
			}
			h /= 2;
		}
	}

	private static void mergeSort(Comparable[] a) {
		int n = a.length;
		Comparable[] t = new Comparable[n];
		mergeSort(a, t, 0, n-1);
	}

	public static void mergeSort(Comparable[] a, Comparable[] t, int low, int high) {
		if (low >= high) return;
		
		int mid = low + (high - low)/2;
		mergeSort(a, t, low, mid);
		mergeSort(a, t, mid+1, high);
		merge(a, t, low, mid, high);
	}

	public static void mergeSortBU(Comparable[] a) {
		int n = a.length;
		int h = 1;
		Comparable[] t = new Comparable[n];

		while (h < n) {
			for (int lo=0; lo+h<n; lo+=h+h) {
				int mid = lo + h - 1;
				int hi = Math.min(n-1, lo + h + h -1);
				merge(a, t, lo, mid, hi);
			}
			h *= 2;
		}
	}

	private static void merge(Comparable[] a, Comparable[] t, int low, int mid, int high) {
		for (int k=low; k<=high; k++)
			t[k] = a[k];

		int i=low, j=mid+1;
		for (int k=low; k<=high; k++)
			if      (i > mid)       a[k] = t[j++];
			else if (j > high)      a[k] = t[i++];
			else if (less(t, i, j)) a[k] = t[i++];
			else                    a[k] = t[j++];
	}

	public static void quickSort(Comparable[] a) {
		int n = a.length;
		quickSort(a, 0, n-1);
	}

	private static void quickSort(Comparable[] a, int lo, int hi) {
		if (lo >= hi) return;
		
		int j = partition(a, lo, hi);
		quickSort(a, lo, j-1);
		quickSort(a, j+1, hi);
	}

    public static int partition(Comparable[] a, int lo, int hi) {
		int mid = lo + (hi - lo) / 2;
		int m = median3(a, lo, hi, mid);
		exch(a, lo, m);
		
		int i = lo;
		int j = hi+1;
		Comparable v = a[lo];
		while (i < j) {
			while (less(a[++i], v)) if (i == hi) break;
			while (less(v, a[--j])) if (j == lo) break;
			if (i < j)
				exch(a, i, j);
		}
		exch(a, j, lo);
		return j;
	}

	private static int median3(Comparable[] a, int i, int j, int k) {
		return (less(a, i, j) ?
				(less(a, j, k) ? j : less(a, i, k) ? k : i) :
				(less(a, k, j) ? j : less(a, k, i) ? k : i));
	}

	public static void quickSort3(Comparable[] a) {
		int n = a.length;
		quickSort3(a, 0, n-1);
	}

	private static void quickSort3(Comparable[] a, int lo, int hi) {
		if (lo >= hi) return;
		int[] r = partition3(a, lo, hi);
		int lt = r[0];
		int gt = r[1];
		quickSort3(a, lo, lt-1);
		quickSort3(a, gt+1, hi);
	}

	private static int[] partition3(Comparable[] a, int lo, int hi) {
		int i = lo+1;
		int lt = lo;
		int gt = hi;
		Comparable v = a[lo];

		while (i <= gt) {
			if      (less(a[i], v))   exch(a, i++, lt++);
			else if (less(v, a[i]))   exch(a, i,   gt--);
			else                      i++;
		}
		//System.out.printf("lt=%d, gt=%d, i=%d\n", lt, gt, i);
		return new int[] {lt, gt};
	}

	public static void heapSortSW(Comparable[] a) {
		int n = a.length;
		
		for (int i=1; i<=n; i++)
			swim(a, i);

		for (int i=n; i>1; i--) {
			hexch(a, i, 1);
			sink(a, 1, i-1);
		}
	}

	public static void heapSortSS(Comparable[] a) {
		int n = a.length;
		
		for (int i=n/2; i>=1; i--)
			sink(a, i, n);

		for (int i=n; i>1; i--) {
			hexch(a, i, 1);
			sink(a, 1, i-1);
		}
	}

	private static void sink(Comparable[] a, int k, int n) {
		while (2*k <= n) {
			int m = 2*k;
			if (m+1 <= n && hless(a, m, m+1))
				m++; // choose right child
			if (!hless(a, k, m))
				break;
			hexch(a, k, m);
			k = m;
		}
	}

	private static void swim(Comparable[] a, int k) {
		while (k > 1) {
			if (!hless(a, k/2, k))
				break;
			hexch(a, k, k/2);
			k /= 2;
		}
	}

	private static boolean less(Comparable[] a, int u, int v) {
		return a[u].compareTo(a[v]) < 0;
	}

	private static boolean less(Comparable u, Comparable v) {
		return u.compareTo(v) < 0;
	}

	private static void exch(Comparable[] a, int u, int v) {
		Comparable t = a[u];
		a[u] = a[v];
		a[v] = t;
	}

	private static boolean hless(Comparable[] a, int u, int v) {
		return a[u-1].compareTo(a[v-1]) < 0;
	}

	private static void hexch(Comparable[] a, int u, int v) {
		Comparable t = a[u-1];
		a[u-1] = a[v-1];
		a[v-1] = t;
	}

	private static boolean isSorted(Comparable[] a) {
		int n = a.length;
		for (int i=1; i<n; i++)
			if (less(a, i, i-1))
				return false;
		return true;
	}
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.printf("Please input method: [ select | insert | shell | merge | mergebu | heapsw | heapss | quick3 ]\n");
			return;
		}

		Scanner sc = new Scanner(System.in);
		String method = args[0];
		int n = sc.nextInt();
		Integer[] a = new Integer[n];

		for (int i=0; i<n; i++)
			a[i] = sc.nextInt();

		if (method.equals("select"))
			selectionSort(a);
		if (method.equals("insert"))
			insertionSort(a);
		if (method.equals("shell"))
			shellSort(a);
		if (method.equals("merge"))
			mergeSort(a);
		if (method.equals("mergebu"))
			mergeSortBU(a);
		if (method.equals("quick"))
			quickSort(a);
		if (method.equals("heapsw"))
			heapSortSW(a);
		if (method.equals("heapss"))
			heapSortSS(a);
		if (method.equals("quick3"))
			quickSort3(a);

		for (int i=0; i<n; i++)
			System.out.printf("%d\n", a[i]);
		System.out.printf("Validate .... %s\n", isSorted(a) ? "OK" : "NOT OK");
	}
}
