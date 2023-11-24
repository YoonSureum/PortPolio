import java.util.Scanner;

public class GameManager {
	static Scanner sc = new Scanner(System.in);
	static boolean managerRun = true;

	public static void main(String[] args) {

		while (managerRun) {
			System.out.println("1.로그인 2.회원가입 3.블랙잭 룰 설명 4.종료");
			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1:
				FileManager.readFile();
				if (FileManager.access == true)
					Game.play();
				break;
			case 2:
				FileManager.makeFile();
				break;
			case 3:
				FileManager.Rule_Book();
				break;
			case 4:
				managerRun = false;
				break;
			default:
				System.out.println("잘못 입력하셨습니다.");
			}
		}
		System.out.println("게임을 종료하셨습니다.");
		sc.close();
	}
}
