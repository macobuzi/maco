import java.util.*;

public class Uva11687 {
	private static int countDigit(int n) {
		return (int) Math.floor(Math.log10(n) + 1);
	}
	
	public static void main(String[] argv) {
		Scanner sc = new Scanner(System.in);
		String line;
		while (!(line = sc.nextLine()).equals("END")) {
			int i = 1;
			int xi = line.length();
			int xp = line.equals("1") ? 1 : -1;
			while (xi != xp) {
				i++;
				xp = xi;
				xi = countDigit(xi);
			}
			System.out.println(i);
		}
	}
}