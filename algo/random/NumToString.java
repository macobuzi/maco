import java.util.Scanner;

public class NumToString {

	private static final String[] lookup1 = new String[] {
		"zero",
		"one",
		"two",
		"three",
		"four",
		"five",
		"six",
		"seven",
		"eight",
		"nine",
		"ten",
		"eleven",
		"twelfth",
		"thirteen",
		"fourteen",
		"fifteen",
		"sixteen",
		"seventeen",
		"eighteen",
		"nineteen",
	};

	private static final String[] lookup2 = new String[] {
		"",
		"",
		"twenty",
		"thirty",
		"fourty",
		"fifty",
		"sixty",
		"seventy",
		"eighty",
		"ninety",
	};

	private static final String[] lookup3 = new String[] {
		"hundred",
		"thousand",
		"million",
		"billion",
	};

	public String toString(long num) {
		StringBuilder sb = new StringBuilder();

		if (num < 0) {
			sb.append("Minus ");
			num = -num;
		}
		long n = num;
		int k = 3;
		long u = 1000000000L;

		while (k > 0) {
			int t = (int) (n / u);
			n %= u;
			if (t > 0) {
				toString3(sb, t);
				sb.append(lookup3[k]).append(", ");
			}
			k--;
			u /= 1000;
		}
		
		toString3(sb, (int) n);
		
		return sb.toString();
	}

	private void toString3(StringBuilder sb, int num) {
		if (num < 20)
			sb.append(lookup1[num]).append(" ");
		else if (num < 100) {
			sb.append(lookup2[num/10]).append(" ");
			sb.append(lookup1[num%10]).append(" ");
		} else if (num < 1000) {
			sb.append(lookup1[num/100]).append(" hundred ");
			int d = (num/10) % 10;
			sb.append(d > 0 ? lookup2[d] : "and").append(" ");
			sb.append(lookup1[num%10]).append(" ");
		} else {
			throw new IllegalArgumentException(num + " is more than 999");
		}
	}
	
	public static void main(String[] args) {
		NumToString p = new NumToString();
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			long num  = sc.nextLong();
			System.out.printf("%s\n", p.toString(num));
		}
	}
}
