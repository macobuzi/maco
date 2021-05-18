import java.util.*;

public class IncreasingArray {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int prev = -1;
		int curr;
		long cnt = 0;
		for (int i=0; i<n; i++) {
			curr = sc.nextInt();
			if (prev != -1 && curr < prev) {
				cnt += (prev - curr);
				curr = prev;
			}
			prev = curr;
		}
		System.out.print(cnt);
	}
}
