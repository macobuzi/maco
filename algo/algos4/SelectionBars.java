import java.util.Random;

public class SelectionBars {

	private static void sort(double[] a) {
		int min;
		double t;
		
		for (int i=0; i<a.length; i++) {
			min = i;
			for (int j=i+1; j<a.length; j++)
				if (a[j] < a[min])
					min = j;
			show(a, i, min);
			t = a[i];
			a[i] = a[min];
			a[min] = t;
		}
	}

	private static void show(double[] a, int i, int min) {
		// Move down i element
		StdDraw.setYscale(-a.length + 0.8 + i, 0.8 + i);

		// Draw
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		for (int k=0; k<i; k++)
			StdDraw.filledRectangle(k, a[k] * 0.3, 0.25, a[k] * 0.3);
		StdDraw.setPenColor(StdDraw.BLACK);
		for (int k=i; k<a.length; k++)
			StdDraw.filledRectangle(k, a[k] * 0.3, 0.25, a[k] * 0.3);
		StdDraw.setPenColor(StdDraw.BOOK_RED);
		StdDraw.filledRectangle(min, a[min] * 0.3, 0.25, a[min] * 0.3);
	}
	
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		StdDraw.setCanvasSize(n * 8, 640);
		StdDraw.setXscale(-1, n+1);
		StdDraw.setPenRadius(0.006);

		Random r = new Random();
		double[] a = new double[n];
		for (int i=0; i<n; i++)
			a[i] = r.nextDouble();
		sort(a);
	}
}
