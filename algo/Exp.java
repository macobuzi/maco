import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.lang.StringBuffer;
import java.lang.String;
import java.lang.Integer;

public class Exp {

	private static void reportSystem() {
		long total = Runtime.getRuntime().totalMemory();
		long used = total - Runtime.getRuntime().freeMemory();
		System.out.printf("Memory total: %d bytes, used: %d bytes \n", total, used);
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br;
		InputStreamReader isr;
		Stack<Character> sc;
		Stack<Integer> si;
		String l, exp;
		StringBuffer sb;
		char[] a;
		char c;

		isr = new InputStreamReader(System.in);
		br = new BufferedReader(isr);
		sc = new Stack<>();
		si = new Stack<>();
		sb = new StringBuffer();

		while ((l = br.readLine()) != null) {
			sc.clear();
			si.clear();
			sb.setLength(0);

			// to infix
			a = l.toCharArray();
			for(int i=0; i<a.length; i++) {
				c = a[i];
				if (c == ')')
					sb.append(sc.pop()).append(' ');
				if (c == '+' || c == '-' || c == '*' || c == '/')
					sc.push(c);
				while (c >= '0' && c <= '9' && i<a.length) {
					sb.append(c);
					c = a[++i];
					if (c < '0' || c > '9')
						sb.append(' ');
				}
			}
			sb.deleteCharAt(sb.length()-1); /* Last one is empty space */
			exp = sb.toString();
			System.out.printf("infix: %s\n", exp);

			// evaluate
			a = exp.toCharArray();
			for (int i = 0, x = 0, t; i < a.length; i+=2, x = 0) {
				c = a[i];
				if (c == '+')
					x = si.pop() + si.pop();
				if (c == '-') {
					t = si.pop();
					x = si.pop() - t;
				}
				if (c == '*')
					x = si.pop() * si.pop();
				if (c == '/') {
					t = si.pop();
					if (t == 0)
						throw new IllegalArgumentException("Divide by zero");
					x = si.pop() / t;
				}
				while(c >= '0' && c <= '9' && i < a.length) {
					x = x*10 + (c - '0');
					c = a[++i];
					if (c < '0' || c > '9')
						i--;
				}
//				System.out.printf("Push %d %c %d\n", i, c, x);
				si.push(x);
			}

			System.out.printf("%d\n", si.pop());
			reportSystem();
		}
	}
}
