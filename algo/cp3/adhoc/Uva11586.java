import java.util.*;

public class Uva11586 {
	public static void main(String[] argv) {
		Scanner sc = new Scanner(System.in);
		int tc = Integer.parseInt(sc.nextLine());
		while (tc-- > 0) {
			String line = sc.nextLine();
			String[] pieces = line.split(" ");
			int maleCnt = 0;
			int femaleCnt = 0;
			for (String piece : pieces) {
				for (int i=0; i<piece.length(); i++) {
					if (piece.charAt(i) == 'M') {
						maleCnt ++;
					} else {
						femaleCnt ++;
					}
				}
			}
			if (pieces.length >= 2 && maleCnt == femaleCnt) {
				System.out.println("LOOP");
			} else {
				System.out.println("NO LOOP");
			}
		}
	}
}