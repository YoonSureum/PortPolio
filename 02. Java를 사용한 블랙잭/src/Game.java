
public class Game {
	static boolean gameRun = false;
	static boolean isBust = false;
	static boolean isException = false;
	static boolean canDoubledown = false;

	static void play() {

		CardDeck deck = new CardDeck();
		Player player = new Player();
		Dealer dealer = new Dealer();

		String action;
		boolean playerRun = true;
		int betting = 0;
		System.out.println("게임을 시작합니다.");
		gameRun = true;

		int wallet = FileManager.callWallet();
		System.out.println("현재 자금 : " + wallet);
		System.out.println("자금을 추가하시겠습니까?");
		action = GameManager.sc.next();
		GameManager.sc.nextLine();

		if (action.equals("예")) {
			do {
				try {
					System.out.print("추가 자금을 입력하시오>");
					wallet += GameManager.sc.nextInt();
					GameManager.sc.nextLine();
					isException = false;
				} catch (Exception e) {
					System.out.println("숫자를 입력하세요.");
					GameManager.sc.nextLine();
					isException = true;
					System.out.println("=======================================================");
				}
			} while (isException);
		}

		player.setWallet(wallet);
		if (player.getWallet() != 0) {
			while (gameRun) {
				do {
					try {
						System.out.println("플레이어의 현재 자금: " + player.getWallet());
						System.out.print("배팅할 금액을 입력하시오>");
						System.out.println();
						betting = GameManager.sc.nextInt();
						GameManager.sc.nextLine();
						isException = false;
					} catch (Exception e) {
						System.out.println("숫자를 입력하세요.");
						GameManager.sc.nextLine();
						isException = true;
						System.out.println("=======================================================");
					}
				} while (isException);

				if (betting <= 0 || betting > player.getWallet()) {
					System.out.println("유효한 배팅금을 입력하십시오");
					continue;
				}

				player.subtractWallet(betting);
				System.out.println("=======================================================");

				canDoubledown = true; // 더블다운 가능 초기화
				isBust = false; // 버스트 미발생 초기화

				// 첫 카드 드로우 코드
				player.addCard(deck.drawCard());
				dealer.addCard(deck.drawCard());
				player.addCard(deck.drawCard());
				dealer.addCard(deck.drawCard());

				System.out.println("베팅한 금액: " + betting);
				System.out.println("딜러 카드: [" + dealer.getHand().get(0) + ", (가려진 카드)]");
				System.out.println("플레이어 카드: " + player.getHand());
				System.out.println("=======================================================");

				Rule.EvenMoney(betting, player, dealer);
				Rule.Blackjack(betting, player, dealer);
				Rule.Insurance(betting, player, dealer);
				Rule.DoubleDown(betting, player, dealer, deck);

				// hit 또는 stand 선택하며 진행
				while (playerRun && gameRun) {
					System.out.println("어떤 액션을 선택하시겠습니까? (히트, 스탠드)");
					action = GameManager.sc.next();
					GameManager.sc.nextLine();
					System.out.println("=======================================================");

					switch (action) {
					case "히트":
						Card playerCard = deck.drawCard();
						player.addCard(playerCard);
						System.out.println("플레이어가 " + playerCard + "를 받았습니다.");
						System.out.println("딜러 카드: [" + dealer.getHand().get(0) + ", (가려진 카드)]");
						System.out.println("플레이어 카드: " + player.getHand());
						System.out.println("=======================================================");

						Rule.Bust(betting, player, dealer);
						break;

					case "스탠드":
						System.out.println("플레이어가 스탠드를 선택했습니다.");
						System.out.println("딜러 카드: " + dealer.getHand());
						System.out.println("플레이어 카드: " + player.getHand());
						while (dealer.getHandValue() < 17) {
							Card dealererCard = deck.drawCard();
							dealer.addCard(dealererCard);
							System.out.println("딜러 카드: " + dealer.getHand());
							Rule.Bust(betting, player, dealer);

						}
						Rule.Push(betting, player, dealer);
						Rule.Result(betting, player, dealer);
						gameRun = false;
						playerRun = false;
						break;

					default:
						System.out.println("올바른 액션을 선택해주세요.");
						break;
					}
				}

				player.resetHand();
				dealer.resetHand();

				if (player.getWallet() <= 0)
					System.out.println("자금이 모두 소진되었습니다.");

				while (true) {
					System.out.println("계속해서 플레이하시겠습니까? (예, 아니오)");
					String restart = GameManager.sc.next();
					GameManager.sc.nextLine();
					if (restart.equals("예")) {
						if (player.getWallet() <= 0) {
							System.out.print("추가할 자금을 입력하시오>");
							wallet = GameManager.sc.nextInt();
							player.setWallet(wallet);
							GameManager.sc.nextLine();
						}
						gameRun = true;
						playerRun = true;
						isException = false;
						System.out.println("=======================================================");
						break;
					} else if (restart.equals("아니오")) {
						System.out.println(player.getWallet()+"원을 저장합니다.");
						FileManager.saveWallet(player.getWallet());
						GameManager.managerRun = false;
						break;
					} else {
						System.out.println("잘못 입력하셨습니다.");
						continue;
					}
				}
			}
		} else
			System.out.println("자금이 부족합니다.");
	}
}
