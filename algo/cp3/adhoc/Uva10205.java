import java.util.*;

public class Uva10205 {
	private static String getName(int cardIdx) {
		int value = cardIdx % 13;
		int suit = cardIdx / 13;
		StringBuilder sb = new StringBuilder();

		switch (value) {
		case 9:
			sb.append("Jack");
			break;
		case 10:
			sb.append("Queen");
			break;
		case 11:
			sb.append("King");
			break;
		case 12:
			sb.append("Ace");
			break;
		default:
			sb.append(value + 2);
		}

		sb.append(" of ");

		switch (suit) {
		case 0:
			sb.append("Clubs");
			break;
		case 1:
			sb.append("Diamonds");
			break;
		case 2:
			sb.append("Hearts");
			break;
		case 3:
			sb.append("Spades");
			break;
		}
		return sb.toString();
	}

	public static void main(String[] argv) {
		Scanner in = new Scanner(System.in);
		
		int tc = Integer.parseInt(in.nextLine());
		while (tc-- > 0) {
			int shuffleNum = in.nextInt();
			int[][] shuffles = new int[shuffleNum][52];
			for (int si=0; si<shuffleNum; si++) {
				for (int ci=0; ci<52; ci++) {
					shuffles[si][ci] = in.nextInt() - 1;
				}
			}
			in.nextLine();
			
			int[] deck = new int[52];
			int[] tmp = new int[52];
			for (int c=0; c<52; c++) {
				deck[c] = c;
			}
			String line;
			while(in.hasNext() && !(line = in.nextLine()).equals("")) {
				int si = Integer.parseInt(line) - 1;
				int[] shuffle = shuffles[si];
				for (int i=0; i<52; i++) {
					tmp[i] = deck[shuffle[i]];
				}
				System.arraycopy(tmp, 0, deck, 0, 52);
			}
			for (int i=0; i<52; i++) {
				System.out.println(getName(deck[i]));
			}
			if (tc > 0) {
				System.out.println();
			}
		}
	}
}