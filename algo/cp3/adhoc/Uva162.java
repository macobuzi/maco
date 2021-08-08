import java.util.*;

public class Uva162 {
	private static boolean isFaceCard(Character c) {
		return c == 'A' || c == 'K' || c == 'Q' || c == 'J';
	}
	
	public static void main(String[] argv) {
		Scanner in = new Scanner(System.in);
		String card;
		Deque<Character>[] hands = new LinkedList[2];
		hands[0] = new LinkedList<Character>();
		hands[1] = new LinkedList<Character>();
		Deque<Character> playedCard = new LinkedList<>();

		while (!(card = in.next()).equals("#")) {
			hands[0].clear();
			hands[1].clear();
			playedCard.clear();

			hands[1].offerLast(card.charAt(1));
			for (int i = 0; i < 51; i++) {
				card = in.next();
//				System.out.println("Card drawn " + card);
				hands[i % 2].offerLast(card.charAt(1));
			}

			int player = 1;
			while (!hands[player].isEmpty()) {
				char lastCard = playedCard.isEmpty() ? 0 : playedCard.peekLast();
//				System.out.printf("player %d, dealer hands size %d, non-dealer hand size %d, playedCard size %d, "
//						+ "dealer hand top %c, non-dealer hand top %c, last card %c \n", 
//						player + 1, hands[0].size(), hands[1].size(), playedCard.size(), hands[0].peekLast(), hands[1].peekLast(), lastCard);

				switch (lastCard) {
				case 'A':
					playedCard.offerLast(hands[player].pollLast());
					if (isFaceCard(playedCard.peekLast())) {
						player = 1 - player;
						break;
					}
					if (hands[player].isEmpty()) {
						break;
					}
				case 'K':
					playedCard.offerLast(hands[player].pollLast());
					if (isFaceCard(playedCard.peekLast())) {
						player = 1 - player;
						break;
					}
					if (hands[player].isEmpty()) {
						break;
					}
				case 'Q':
					playedCard.offerLast(hands[player].pollLast());
					if (isFaceCard(playedCard.peekLast())) {
						player = 1 - player;
						break;
					}
					if (hands[player].isEmpty()) {
						break;
					}
				case 'J':
					playedCard.offerLast(hands[player].pollLast());
					if (!isFaceCard(playedCard.peekLast())) { // round done
						int op = 1 - player;
//						System.out.println("* Add heap to player " + (op + 1));
						while(!playedCard.isEmpty()) {
							hands[op].offerFirst(playedCard.pollFirst());
						}
					}
					player = 1 - player;
					break;
				default:
					playedCard.offerLast(hands[player].pollLast());
					player = 1 - player;
				}
			}

			int winner = 1 - player;
			System.out.printf("%d%3d\n", winner + 1, hands[winner].size());
		}
	}
}