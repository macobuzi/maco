import java.util.Scanner;

public class InsertionSort {

	public static void sort(Comparable[] a) {
		int n = a.length;
		for (int j=1; j<n; j++) {
			Comparable k = a[j];
			int i = j - 1;
			for (; i >= 0 && a[i].compareTo(k) > 0; i--)
				a[i+1] = a[i];
			a[i+1] = k;
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		Integer[] a = new Integer[n];
		for (int i=0; i<n; i++)
			a[i] = sc.nextInt();
		sort(a);

		for (int i=0; i<n; i++)
			System.out.print(a[i] + " ");
		System.out.println();
	}
}
