
class Rule {
	static String input;

	static void Blackjack(int betting, Player player, Dealer dealer) {
		if (Game.gameRun == true) {
			if (player.getHandValue() == 21) {
				if (dealer.getHandValue() == 21) {
					System.out.println("딜러 카드: " + dealer.getHand());
					System.out.println("딜러와 플레이어 모두 블랙잭 입니다.");
					Rule.Push(betting, player, dealer);
				} else {
					System.out.println("블랙잭! 플레이어 승리!");
					System.out.println("딜러 카드: " + dealer.getHand());
					player.addWallet((int) (betting * 2.5));
					Game.gameRun = false;
					System.out.println("=======================================================");
				}
			}
		}
	}

	static void Bust(int betting, Player player, Dealer dealer) {
		if (player.getHandValue() > 21) {
			System.out.println("딜러 카드: " + dealer.getHand());
			System.out.println("플레이어 버스트! 딜러 승리!");
			Game.gameRun = false;
			Game.isBust = true;
			System.out.println("=======================================================");
		} else if (dealer.getHandValue() > 21) {
			System.out.println("딜러 버스트! 플레이어 승리!");
			player.addWallet(betting * 2);
			Game.gameRun = false;
			Game.isBust = true;
			System.out.println("=======================================================");
		}
	}

	static void Push(int betting, Player player, Dealer dealer) {
		if (player.getHandValue() == dealer.getHandValue()) {
			System.out.println("푸시! 무승부!");
			player.addWallet(betting);
			Game.gameRun = false;
			System.out.println("=======================================================");
		}
	}

	static void Insurance(int betting, Player player, Dealer dealer) {
		if (Game.gameRun == true) {
			Card dealerOpenCard = dealer.getHand().get(0);
			if (dealerOpenCard.getValue() == 11) {
				System.out.println("인슈어런스를 선택하시겠습니까? (예, 아니오)");
				input = GameManager.sc.nextLine();
				if (input.equals("예")) {
					int insuranceAmount = (int) (betting * 0.5);
					if (player.getWallet() >= insuranceAmount) {
						player.subtractWallet(insuranceAmount);
						System.out.println("인슈어런스 베팅금액: " + insuranceAmount);
						if (dealer.getHandValue() == 21) {
							System.out.println("딜러 카드: " + dealer.getHand());
							System.out.println("딜러의 패가 블랙잭입니다. 인슈어런스에 걸린 보험금을 받습니다.");
							player.addWallet(insuranceAmount * 3);
							Game.gameRun = false;
							System.out.println("=======================================================");
						} else {
							System.out.println("인슈어런스에 걸린 보험금은 소실되었습니다.");
							System.out.println("=======================================================");
							Game.canDoubledown = false;
						}
					} else {
						System.out.println("보유한 자금으로 인슈어런스를 걸 수 없습니다.");
						System.out.println("=======================================================");
					}
				}
			}
		}
	}

	static void EvenMoney(int betting, Player player, Dealer dealer) {
		if (dealer.getHand().get(0).getValue() == 11 && player.getHandValue() == 21) {
			System.out.println("이븐 머니 베팅을 하시겠습니까?(예,아니오)");
			input = GameManager.sc.nextLine();

			if (input.equals("예")) {
				System.out.println("이븐 머니 선택을 하셨습니다. 플레이어 승리!");
				player.addWallet((int) (betting * 2));
				Game.gameRun = false;
				System.out.println("=======================================================");
			} else if (input.equals("아니오")) {
				Rule.Blackjack(betting, player, dealer);
			}
		}
	}

	static void DoubleDown(int betting, Player player, Dealer dealer, CardDeck deck) {
		if (Game.gameRun == true && Game.canDoubledown == true) {
			if (player.getWallet() < betting)
				System.out.println("보유한 자금으로 더블다운을 할 수 없습니다.");
			else {
				System.out.println("더블다운에 배팅하시겠습니까? (예, 아니오)");
				input = GameManager.sc.nextLine();
				if (input.equals("예")) {
					player.subtractWallet(betting);
					betting *= 2;
					player.addCard(deck.drawCard());
					System.out.println("베팅한 금액: " + betting);
					System.out.println("플레이어 카드: " + player.getHand());
					System.out.println("딜러 카드: " + dealer.getHand());
					Rule.Bust(betting, player, dealer);

					if (Game.gameRun == true) {
						while (dealer.getHandValue() < 17) {
							Card dealererCard = deck.drawCard();
							dealer.addCard(dealererCard);
							System.out.println("딜러 카드: " + dealer.getHand());
						}
						Rule.Bust(betting, player, dealer);
						Rule.Push(betting, player, dealer);
						Rule.Result(betting, player, dealer);
						Game.gameRun = false;
					}
				}

			}
		}
	}

	static void Result(int betting, Player player, Dealer dealer) {
		if (Game.isBust == false && player.getHandValue() > dealer.getHandValue()) {
			System.out.println("플레이어 승리!");
			player.addWallet(betting * 2);
			System.out.println("=======================================================");
		} else if (Game.isBust == false && player.getHandValue() < dealer.getHandValue()) {
			System.out.println("딜러 승리!");
			System.out.println("=======================================================");
		}
	}
}