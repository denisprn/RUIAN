package com.bp.RUIAN.parsers;

import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ElementParser {
    public static Element getElement(int item) throws IOException, SAXException, ParserConfigurationException {
        File file = new ClassPathResource("xml/ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(item).getChildNodes();
        Node node = nodeL.item(1);

        return (Element) node;
    }
}
