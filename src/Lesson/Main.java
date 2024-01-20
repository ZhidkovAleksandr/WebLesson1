package Lesson;

import entity.User;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {


        String fPath = "/Users/alexandrzhidkov/Documents/УчебаJava/Курс/WebServices/Lesson1/HW1/prj/Sch/src/Resources/s.xml";
        DomHandler domHandler = new DomHandler();

        List<User> users =  domHandler.letsParse(fPath);
        for (User user:users) {
            System.out.println(user);
        }


    }
}
