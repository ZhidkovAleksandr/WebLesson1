package Task1_5;

import Task1_5.Entity.Flower;
import Task1_5.Entity.Helpers.GrowTips;
import Task1_5.Entity.Helpers.Multiplying;
import Task1_5.Entity.Helpers.Soil;
import Task1_5.Entity.Helpers.VisualParam;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class FlDomHandler {

    private Document doc;

    public FlDomHandler() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);

        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse("/Users/alexandrzhidkov/Documents/УчебаJava/Курс/WebServices/Lesson1/HW1/prj/Sch/src/Task1_5/Resources/rose.xml");
    }

    public Flower readCreateFlower() {

        Flower flower = new Flower();

        Element root = doc.getDocumentElement();

        NodeList list = root.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {

            Node cNode = list.item(i);
            if (cNode.getNodeType() == Node.ELEMENT_NODE) {
                String nName = cNode.getNodeName();

                switch (nName) {

                    case "name":

                        flower.setName(cNode.getFirstChild().getTextContent());
                        break;
                    case "soil":
                        String soilType = cNode.getFirstChild().getTextContent().toUpperCase();
                        soilType = soilType.replaceFirst("-", "_");
                        flower.setSoil(Soil.valueOf(soilType));
                        break;

                    case "origin":
                        flower.setOrigin(cNode.getFirstChild().getTextContent());
                        break;

                    case "visual_param":
                        VisualParam visualParam = new VisualParam();
                        visualParam.setStemColor(cNode.getFirstChild().getNextSibling().getTextContent());
                        visualParam.setLeafColor(cNode.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getTextContent());
                        visualParam.setAvgSize(cNode.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getTextContent());
                        flower.setVisualParam(visualParam);
                        break;
                    case "grow_tips":
                        System.out.println("gt");
                        GrowTips growTips = new GrowTips();
                        growTips.setTemp(Float.parseFloat(cNode.getFirstChild().getNextSibling().getTextContent()));
                        growTips.setLighting(Boolean.parseBoolean(cNode.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getTextContent()));
                        growTips.setWatering(Float.parseFloat(cNode.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getTextContent()));
                        flower.setGrowTips(growTips);
                        break;
                    case "multiplying":
                        String multiType = cNode.getFirstChild().getTextContent().toUpperCase();
                        multiType = multiType.replaceFirst("-", "_");
                        flower.setMultiplying(Multiplying.valueOf(multiType));
                        break;
                }


            }


        }

        return flower;

    }

}
