import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("/Users/alexandrzhidkov/Documents/УчебаJava/Курс/WebServices/Lesson1/HW1/prj/Sch/src/Task3Read/tulip1.xml");

        int i;

        StringBuilder sb = new StringBuilder();


        String res = "";

        while((i=fileInputStream.read())!= -1){
            sb.append((char)i);
            res = sb.toString();
        }

        String s = res.replaceAll("[<>/\n\\s+]", "");
        String sComplete = s.replaceAll("(flower|name|origin|soil|visual_param|stem_color|leaf_color|avg_size|grow_tips|multiplying|temp|lighting|watering)", " ");
        String[] strings = sComplete.split(" ",0);
        System.out.println(sComplete);


        for (int j=2; j<strings.length; j++){
           if (strings[j]=="") {
               continue;
           } else {
               System.out.println(strings[j]);
           }
        }

    }
}