import java.util.List;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
 
public class Rule_Book {
    public static void main(String[] args){
        //파일 객체 생성
        Path path = Paths.get("C:\\Users\\moble\\eclipse-workspace\\Black_jack\\Black_Rule.txt");
        // 캐릭터셋 지정
        Charset cs = StandardCharsets.UTF_8;
        //파일 내용담을 리스트
        List<String> list = new ArrayList<String>();
        try{
            list = Files.readAllLines(path,cs);
        }catch(IOException e){
            e.printStackTrace();
        }
        for(String readLine : list){
            System.out.println(readLine);
        }
    }
}