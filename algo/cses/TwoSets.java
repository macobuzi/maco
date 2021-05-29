import java.util.*;

public class TwoSets {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();

		if (n*(n+1) % 2 == 1) {
			System.out.println("NO");
			return;
		}

		System.out.println("YES");
	}
}
