import java.util.Random;
import java.awt.Font;

public class ShellBars {
	private static final int FF = 4;

	private static void sort(double[] a) {
		int n = a.length;
		int k = 1;
		int h = 1;

		while (h < n/3) {
			h = h * 3 + 1;
			k++;
		}
		show(a, k, "input");

		while(h >= 1) {
			for (int i=h; i<n; i++) {
				for (int j=i; j >= h && a[j] < a[j-h]; j -= h) {
					double t = a[j];
					a[j] = a[j-1];
					a[j-1] = t;
				}
			}
			if (h < n)
				show(a, --k, h + "-sorted");
			h = h/3;
		}
	}
	
	private static void show(double[] a, int k, String message) {
		int n = a.length;
		StdDraw.setPenColor(StdDraw.BLACK);
		for (int i=0; i<n; i++)
			StdDraw.filledRectangle(i, FF*k + a[i]*(FF-1)*0.5, 0.25, a[i]*(FF-1)*0.5);
		StdDraw.setPenColor(StdDraw.BOOK_RED);
		StdDraw.textLeft(0, FF*(k+1) - 0.3, message);
	}
	
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int k = (int) Math.round(Math.log(n) / Math.log(3));
		
		StdDraw.setCanvasSize(n * 8, 900);
		StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 9));
		StdDraw.setXscale(-1, n+1);
		StdDraw.setYscale(-1 - FF, FF * (k+1));
		StdDraw.setPenRadius(0.006);

		Random r = new Random();
		double[] a = new double[n];
		for (int i=0; i<n; i++)
			a[i] = r.nextDouble();
		sort(a);
	}
}
