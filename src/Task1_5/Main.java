package Task1_5;

import Task1_5.Entity.Flower;
import Task1_5.Entity.Helpers.GrowTips;
import Task1_5.Entity.Helpers.Multiplying;
import Task1_5.Entity.Helpers.Soil;
import Task1_5.Entity.Helpers.VisualParam;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void validateSchema() throws SAXException, IOException {

        String xmlPath = "/Users/alexandrzhidkov/Documents/УчебаJava/Курс/WebServices/Lesson1/HW1/prj/Sch/src/Task1_5/Resources/chamomile.xml";
        String xsdPath = "/Users/alexandrzhidkov/Documents/УчебаJava/Курс/WebServices/Lesson1/HW1/prj/Sch/src/Task1_5/Resources/fl.xsd";

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new StreamSource(xsdPath));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(xmlPath));
        System.out.println("File is valid");

    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XMLStreamException {

        validateSchema();

        Flower saxFlower;

        try {
            saxFlower = createSAX_Flower();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        Flower flowerD = createDOMFlower();
        Flower flowerSt = createSTAXFlower();



        List<Flower> flowers = new ArrayList<>();
        Comparator<Flower> comparator = (f1, f2)->f1.getName().compareTo(f2.getName());
        flowers.add(saxFlower);
        flowers.add(flowerD);
        flowers.add(flowerSt);
        Collections.sort(flowers,comparator);
        for (Flower f: flowers) {
            System.out.println(f);
            System.out.println("--------------------------");
        }







    }

    public static Flower createSTAXFlower() throws FileNotFoundException, XMLStreamException {

        XMLInputFactory factory = XMLInputFactory.newFactory();

        Flower flower = new Flower();

            XMLEventReader eventReader = factory.createXMLEventReader(new FileInputStream("/Users/alexandrzhidkov/Documents/УчебаJava/Курс/WebServices/Lesson1/HW1/prj/Sch/src/Task1_5/Resources/chamomile.xml"));

            while (eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();
                if(event.isStartElement()){
                    StartElement startElement = event.asStartElement();
                   if (startElement.getName().getLocalPart().equals("name")){
                       event = eventReader.nextEvent();
                       flower.setName(event.asCharacters().getData());
                   } else if (startElement.getName().getLocalPart().equals("soil")){
                       event = eventReader.nextEvent();
                       flower.setSoil(Soil.valueOf(event.asCharacters().getData().toUpperCase()));
                   } else if ((startElement.getName().getLocalPart().equals("origin"))) {
                       event = eventReader.nextEvent();
                       flower.setOrigin(event.asCharacters().getData());
                   } else if (startElement.getName().getLocalPart().equals("visual_param")){
                       VisualParam visualParam = new VisualParam();
                       while (true){
                           event = eventReader.nextEvent();
                           if (event.isStartElement()){
                               startElement = event.asStartElement();
                               if (startElement.getName().getLocalPart().equals("stem_color")){
                                   event = eventReader.nextEvent();
                                   visualParam.setStemColor(event.asCharacters().getData());
                               }
                               else if (startElement.getName().getLocalPart().equals("leaf_color")){
                                   event = eventReader.nextEvent();
                                   visualParam.setLeafColor(event.asCharacters().getData());
                               } else if (startElement.getName().getLocalPart().equals("avg_size")) {
                                   event = eventReader.nextEvent();
                                   visualParam.setAvgSize(event.asCharacters().getData());
                                   flower.setVisualParam(visualParam);
                                   break;
                               }
                           }
                       }
                   } else if (startElement.getName().getLocalPart().equals("grow_tips")) {
                       GrowTips growTips = new GrowTips();
                       while (true){
                           event = eventReader.nextEvent();
                           if (event.isStartElement()){
                               startElement = event.asStartElement();
                               if (startElement.getName().getLocalPart().equals("temp")){
                                   event = eventReader.nextEvent();
                                   growTips.setTemp(Float.parseFloat(event.asCharacters().getData()));
                               }
                               else if (startElement.getName().getLocalPart().equals("lighting")){
                                   event = eventReader.nextEvent();
                                   growTips.setLighting(Boolean.parseBoolean(event.asCharacters().getData()));
                               } else if (startElement.getName().getLocalPart().equals("watering")) {
                                   event = eventReader.nextEvent();
                                   growTips.setWatering(Float.parseFloat(event.asCharacters().getData()));
                                   flower.setGrowTips(growTips);
                                   break;
                               }
                           }
                       }
                   } else if (startElement.getName().getLocalPart().equals("multiplying")) {
                       event = eventReader.nextEvent();
                       flower.setMultiplying(Multiplying.valueOf(event.asCharacters().getData().toUpperCase()));
                   }

                }
            }





                return flower;

    }

    public static Flower createDOMFlower() throws ParserConfigurationException, IOException, SAXException {
        FlDomHandler domHandler = new FlDomHandler();
        Flower flowerD = domHandler.readCreateFlower();
        return flowerD;
    }

    public static Flower createSAX_Flower() throws ParserConfigurationException, SAXException, IOException{

        Flower flower;

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        FlSaxHandler fh = new FlSaxHandler();

        File file = new File("/Users/alexandrzhidkov/Documents/УчебаJava/Курс/WebServices/Lesson1/HW1/prj/Sch/src/Task1_5/Resources/tulip.xml");

        parser.parse(file, fh);

        flower = fh.getFlower();
        return flower;
    }

}
