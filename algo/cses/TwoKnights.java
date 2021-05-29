import java.util.*;

public class TwoKnights {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();

		StringBuffer sb = new StringBuffer();
		for (int i=1; i<=n; i++) {
			long size = i*i;
			long all = size * (size-1) / 2;
			long invalid = 0;
			if (i > 2)
				invalid = 4 * (i-1) * (i-2);
			sb.append(all - invalid).append("\n");
		}
		System.out.println(sb.toString());
	}
}
