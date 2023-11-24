import java.util.List;

class Player {
	private Hand hand;
	private int wallet;

	Player() {
		hand = new Hand();
		wallet = FileManager.callWallet();
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

	int getWallet() {
		return wallet;
	}

	void setWallet(int wallet) {
		this.wallet = wallet;
	}

	void addWallet(int amount) {
		wallet += amount;
	}

	void subtractWallet(int amount) {
		wallet -= amount;
	}

	void resetHand() {
		hand = new Hand();
	}
}