import java.util.Scanner;

public class Evaluate {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayStack<String> ops = new ArrayStack<>();
		ArrayStack<Double> vals = new ArrayStack<>();

		while( sc.hasNext() ) {
			String s = sc.next();
			if      (s.equals("("));
			else if (s.equals("+"))    ops.push(s);
			else if (s.equals("-"))    ops.push(s);
			else if (s.equals("*"))    ops.push(s);
			else if (s.equals("/"))    ops.push(s);
			else if (s.equals("sqrt")) ops.push(s);
			else if (s.equals(")")) {
				String op = ops.pop();
				double t = vals.pop();
				if      (op.equals("+"))    t = vals.pop() + t;
				else if (op.equals("-"))    t = vals.pop() - t;
				else if (op.equals("*"))    t = vals.pop() * t;
				else if (op.equals("/"))    t = vals.pop() / t;
				else if (op.equals("sqrt")) t = Math.sqrt(t);
				vals.push(t);
			} else vals.push(Double.parseDouble(s));
		}
		System.out.printf("%f\n", vals.pop());
	}
}
