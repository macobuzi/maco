import java.util.*;

public class WeirdAlgorithm {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		long n = in.nextLong();
		
		while (n != 1) {
			System.out.printf("%d ", n);
			n = n%2 == 0 ? n/2 : n*3+1;
		}
		System.out.print(n);
	}
}
