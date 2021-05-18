import java.util.Scanner;

public class CountingSort {

	public static void sort(int[] a, int[] b, int[] c, int k) {
		for (int i=0; i<a.length; i++)
			c[a[i]]++;
		for (int j=1; j<=k; j++)
			c[j] += c[j-1];
		for (int i=a.length-1; i >= 0; i--) {
			b[c[a[i]]-1] = a[i];
			c[a[i]]--;
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int k = sc.nextInt();
		int[] a = new int[n];
		for (int i=0; i<n; i++)
			a[i] = sc.nextInt();
		int[] b = new int[n];
		int[] c = new int[k+1];
		sort(a, b, c, k);

		for (int i=0; i<n; i++)
			System.out.print(b[i] + " ");
		System.out.println();
	}
}
