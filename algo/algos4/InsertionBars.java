import java.util.Random;

public class InsertionBars {
	private static final double ROW_HEIGHT = 0.8;

	private static void sort(double[] a) {
		for (int i=0; i<a.length; i++) {
			int j=i;
			while (j > 0 && a[j] < a[j-1]) {
				double t = a[j];
				a[j] = a[j-1];
				a[j-1] = t;
				j--;
			}
			show(a, i, j);
		}
	}
	
	private static void show(double[] a, int i, int j) {
		int numRow = a.length;
		
		// Move down one row
		StdDraw.setYscale(-numRow + i + ROW_HEIGHT, i + ROW_HEIGHT);

		// Draw untouch
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		for (int k=0; k<a.length; k++)
			StdDraw.filledRectangle(k, a[k] * 0.3, 0.25, a[k] * 0.3);

		// Draw touch
		StdDraw.setPenColor(StdDraw.BLACK);
		for (int k=j+1; k<=i; k++)
			StdDraw.filledRectangle(k, a[k] * 0.3, 0.25, a[k] * 0.3);

		// Draw insert element
		StdDraw.setPenColor(StdDraw.BOOK_RED);
		StdDraw.filledRectangle(j, a[j] * 0.3, 0.25, a[j] * 0.3);
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
