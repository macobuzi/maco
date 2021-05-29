import java.util.*;

public class BinaryGen {
	private static char[] next(char[] conf) {
		int n = conf.length;
		// lz <- find last 0
		int lastZero = -1;
		for (int i=n-1; i>=0; i--)
			if (conf[i] == '0') {
				lastZero = i;
				break;
			}
		// if not found return null
		if (lastZero == -1)
			return null;
		
		// nc[lz] = 1 and nc[lz+1..n-1] = 0
		conf[lastZero] = '1';
		for (int i=lastZero+1; i<n; i++)
			conf[i] = '0';
		return conf;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// read n
		int n = sc.nextInt();
		char[] conf = new char[n];
		// state <- 00..00
		for (int i=0; i<n; i++)
			conf[i] = '0';
		// while state <> null do print state; gen(state)
		while(conf != null) {
			System.out.println(new String(conf));
			conf = next(conf);
		}
	}
}
