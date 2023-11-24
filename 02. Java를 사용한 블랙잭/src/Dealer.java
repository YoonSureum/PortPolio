import java.util.List;

class Dealer {
	private Hand hand;

	Dealer() {
		hand = new Hand();
	}

	void addCard(Card card) {
		hand.addCard(card);
	}

	int getHandValue() {
		return hand.getValue();
	}

	List<Card> getHand() {
		return hand.getCards();
	}

	void resetHand() {
		hand = new Hand();
	}
}