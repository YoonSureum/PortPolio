import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class FileManager {
	static boolean access = false;
	static String[] split;
	static String line;
	static BufferedReader br;

	static void makeFile() {
		try {
			String id, pw, name;
			File f = new File("MemberData.txt");
			if (!f.exists())
				f.createNewFile();

			FileReader fr = new FileReader(f);
			br = new BufferedReader(fr);

			System.out.print("이름: ");
			name = GameManager.sc.next();
			System.out.print("아이디: ");
			id = GameManager.sc.next();
			System.out.print("암호: ");
			pw = GameManager.sc.next();

			boolean isValidId = false;

			while ((line = br.readLine()) != null) {
				String[] split = line.split(",");
				String storedId = split[1];

				if (storedId.equals(id)) {
					isValidId = true;
					break;
				}
			}

			if (isValidId) {
				System.out.println("이미 사용 중인 아이디입니다. 회원가입이 실패하였습니다.");
				System.out.println("=======================================================");
			} else {
				FileWriter fwr = new FileWriter(f, true);
				PrintWriter pwr = new PrintWriter(fwr);
				pwr.println(name + "," + id + "," + pw+ ",0");
				pwr.close();
				System.out.println("회원가입이 완료되었습니다.");
				System.out.println("=======================================================");
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void readFile() {
		System.out.println("로그인을 위해 아이디와 비밀번호를 입력하세요.");
		System.out.print("아이디 입력: ");
		String id = GameManager.sc.nextLine();
		System.out.print("비밀번호 입력: ");
		String pw = GameManager.sc.nextLine();
		File f = new File("MemberData.txt");

		try (FileReader filereader = new FileReader(f); BufferedReader bufReader = new BufferedReader(filereader)) {
			String line;

			while ((line = bufReader.readLine()) != null) {
				split = line.split(",");
				String storedId = split[1];
				String storedPw = split[2];

				if (storedId.equals(id) && storedPw.equals(pw)) {
					System.out.println(split[0] + "님 로그인 성공");
					access = true;
					System.out.println("=======================================================");
					break;
				}
			}

			if (!access) {
				System.out.println("로그인 실패");
				System.out.println("=======================================================");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void updateFile(String id, String updatedLine) {
		File inputFile = new File("MemberData.txt");
		File tempFile = new File("tempMemberData.txt");

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
				PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

			String line;
			boolean found = false;

			while ((line = reader.readLine()) != null) {
				String[] split = line.split(",");
				String storedId = split[1];

				if (storedId.equals(id)) {
					writer.println(updatedLine);
					found = true;
				} else {
					writer.println(line);
				}
			}

			if (!found) {
				System.out.println("아이디를 찾을 수 없습니다.");
				System.out.println("=======================================================");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (inputFile.delete()) {
			if (!tempFile.renameTo(inputFile)) {
				System.out.println("파일을 업데이트하는 중에 오류가 발생했습니다.");
			}
		} else {
			System.out.println("기존 파일을 삭제하는 중에 오류가 발생했습니다.");
		}
	}

	static void saveWallet(int wallet) {
		String idToUpdate = split[1];
		String updatedLine = split[0] + "," + split[1] + "," + split[2] + "," + Integer.toString(wallet);
		FileManager.updateFile(idToUpdate, updatedLine);
	}

	static int callWallet() {
		return Integer.parseInt(split[3]);
	}

	static void Rule_Book() {
		// 파일 객체 생성
		Path path = Paths.get("RuleBook.txt");
		// 캐릭터셋 지정
		Charset cs = StandardCharsets.UTF_8;
		// 파일 내용담을 리스트
		List<String> list = new ArrayList<String>();
		try {
			list = Files.readAllLines(path, cs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String readLine : list) {
			System.out.println(readLine);
		}
	}
}
