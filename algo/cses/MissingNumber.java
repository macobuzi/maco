import java.util.*;

public class MissingNumber {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
	    Set<Integer> nums = new HashSet<>();
		for (int i=1; i<=n; i++)
			nums.add(i);
		for (int i=1; i<n; i++)
			nums.remove(sc.nextInt());
		System.out.print((Integer) nums.toArray()[0]);
	}
}
