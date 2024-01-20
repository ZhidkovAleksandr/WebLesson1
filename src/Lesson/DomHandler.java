package Lesson;

import entity.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.User;

public class DomHandler {


    private Document doc;

    public DomHandler() throws IOException, SAXException, ParserConfigurationException {


    }

    public List<User> letsParse(String fPath) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);

        File file = new File(fPath);

        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(file);

        Element root = doc.getDocumentElement();
        NodeList list = root.getChildNodes();

        int lenght = list.getLength();

        List<User> users = new ArrayList<>();


        for (int i = 0; i < lenght; i++) {

            String nodeName = list.item(i).getNodeName();

            if (nodeName == "user") {
                System.out.println("in user node");
                User u = new User();
                System.out.println(list.item(i).getFirstChild().getNextSibling().getTextContent());
                u.setName(list.item(i).getFirstChild().getNextSibling().getTextContent());
                u.setAge(Integer.parseInt(list.item(i).getFirstChild().getNextSibling().getNextSibling().getNextSibling().getTextContent()));
                u.setRole(list.item(i).getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getTextContent());
                users.add(u);


            }
        }

        return users;

    }

}
