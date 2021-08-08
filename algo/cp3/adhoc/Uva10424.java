import java.util.*;

public class Uva10424 {
	private static int digitSum(int num) {
		int sum = 0;
		while(num > 0) {
			sum += num % 10;
			num /= 10;
		}
		return sum;
	}
	
	private static int score(String name) {
		int score = 0;
		for (int i=0; i<name.length(); i++) {
			char c = name.charAt(i);
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				score += Character.toLowerCase(c) - 'a' + 1;
			}
		}
		while(score >= 10) {
			score = digitSum(score);
		}
		return score;
	}
	
	public static void main(String[] argv) {
		Scanner sc = new Scanner(System.in);
		
		while (sc.hasNext()) {
			String name1 = sc.nextLine();
			String name2 = sc.nextLine();
			double score1 = (double) score(name1);
			double score2 = (double) score(name2);
			double loveScore = (score1 < score2) ? 
					(score1 / score2) * 100 : (score2 / score1) * 100;
			System.out.println(String.format(Locale.US, "%.2f %%", loveScore));
		}
	}
}