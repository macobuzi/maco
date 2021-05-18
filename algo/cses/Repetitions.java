import java.util.*;

public class Repetitions {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String dna = sc.next();
		int maxRep = 1;
		int curRep = 1;
		int n = dna.length();

		for (int i=1; i<n; i++) {
			if (dna.charAt(i-1) == dna.charAt(i))
				curRep++;
			else {
				maxRep = Math.max(maxRep, curRep);
				curRep = 1;
			}
		}
		maxRep = Math.max(maxRep, curRep);
		
		System.out.print(maxRep);
	}
}
