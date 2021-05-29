import java.util.*;

public class CardDeck {
	public static class Deck<T extends Card> {
		private Stack<T> cards;

		public Deck() {
			cards = new Stack<>();
			
		}

		public void addCard(T card) {
			cards.push(card);
		}

		public void shuffle() {
			Collections.shuffle(cards);
		}

		public List<T> deal(int time) {
			if (time <= 0 || cards.size() < time) {
				throw new IllegalArgumentException("Invalid deal time");
			}
			List<T> dealed = new ArrayList<>();
			for (int i=0; i<time; i++)
				dealed.add(cards.pop());
			return dealed;
		}
	}

	public static enum Suit {
		HEART, DIAMOND, SPADE, CLUB
	}

	public static class Card {
		protected Suit suit;
		protected int faceValue;

		public Card(int faceValue, Suit suit) {
			this.suit = suit;
			this.faceValue = faceValue;
		}

		public Suit getSuit() {
			return suit;
		}

		public int getFaceValue() {
			return faceValue;
		}

		public boolean isAce() {
			return faceValue == 1;
		}

		public boolean isFaceCard() {
			return faceValue > 10;
		}
	}

	public static abstract class Hand<T extends Card> {
		protected List<T> cards;

		public Hand(List<T> cards) {
			this.cards = new LinkedList<>();
			for (T c : cards) {
				addCard(c);
			}
		}

		public void addCard(T c) {
			cards.add(c);
		}

		public abstract int score();
	}

	public static class BlackJackCard extends Card {
		public BlackJackCard(int faceValue, Suit suit) {
			super(faceValue, suit);
		}
		
		public int value() {
			return faceValue <= 10 ? faceValue : 10;
		}

		public int minValue() {
			return value();
		}

		public int maxValue() {
			return isAce() ? 11 : value();
		}

		public boolean isDualValue() {
			return isAce();
		}
	}

	public static class BlackJackHand extends Hand<BlackJackCard> {
		public final static int MAX_SCORE = 21;

		public BlackJackHand(List<BlackJackCard> cards) {
			super(cards);
		}
		
		public int score() {
			Queue<Integer> queue = new LinkedList<>();
			queue.offer(0);
			for (BlackJackCard c : cards) {
				int qSize = queue.size();
				for (int i=0; i<qSize; i++) {
					int sum = queue.poll();
					if (c.isDualValue()) {
						queue.offer(sum + c.minValue());
						queue.offer(sum + c.maxValue());
					} else {
						queue.offer(sum + c.value());
					}
				}
			}
			int minDiff = Integer.MAX_VALUE;
			int ans = 0;
			for (int sum : queue) {
				int diff = Math.abs(sum - MAX_SCORE);
				if (diff < minDiff) {
					minDiff = diff;
					ans = sum;
				}
			}
			return ans;
		}

		public boolean isBusted() {
			return score() > MAX_SCORE;
		}

		public boolean isMaxScore() {
			return score() == MAX_SCORE;
		}

		public boolean isBlackJack() {
			return isMaxScore() && cards.size() == 2;
		}
	}

	public static void main(String[] argv) {
		Deck<BlackJackCard> bjDeck = new Deck<>();
		// play around with intial deck :)
		for (int faceValue =1; faceValue<=13; faceValue+=10) {
			for (Suit suit : Suit.values()) {
				bjDeck.addCard(new BlackJackCard(faceValue, suit));
			}
		}
		bjDeck.shuffle();

		// deal 2 cards
		List<BlackJackCard> cards = bjDeck.deal(2);
		System.out.println("Deal 2 cards");
		for (BlackJackCard card : cards) {
			System.out.printf("suit = %s, fvalue = %d\n", card.getSuit().name(), card.getFaceValue());
		}
		BlackJackHand bjHand = new BlackJackHand(cards);
		System.out.printf("score = %d \n", bjHand.score());

		// hit one if not max 
		if (bjHand.score() < BlackJackHand.MAX_SCORE) {
			System.out.println("Hit 1 card");
			cards = bjDeck.deal(1);
			System.out.printf("suit = %s, fvalue = %d\n", cards.get(0).getSuit().name(), cards.get(0).getFaceValue());
			bjHand.addCard(cards.get(0));
		}

		// .. result
		System.out.printf("new score = %d -> busted = %b, maxscore = %b, blackjack = %b\n",
						  bjHand.score(), bjHand.isBusted(), bjHand.isMaxScore(), bjHand.isBlackJack());
	}
}
