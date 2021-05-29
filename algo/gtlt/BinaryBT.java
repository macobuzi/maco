import java.util.*;

public class BinaryBT {
	private static void backtrack(int i, char[] conf, int n) {
		// for possible value
		for (int v=0; v<=1; v++) {
			conf[i] = v==0 ? '0' : '1';
			
			// if final, print
			if (i == n-1)
				System.out.println(new String(conf));
			
			// else backtrack(i+1)
			else
				backtrack(i+1, conf, n);
		}
	}
	
	public static void main(String[] argv) {
		Scanner sc = new Scanner(System.in);

		// read input
		int n = sc.nextInt();
		char[] conf = new char[n];
		
		backtrack(0, conf, n);
	}
}
