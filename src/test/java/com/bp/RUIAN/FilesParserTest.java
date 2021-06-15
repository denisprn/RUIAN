package com.bp.RUIAN;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FilesParserTest {

    @Test
    @DisplayName("Should assert that files have .xml extension")
    void walk() throws IOException {
        File xmlDirectory = new ClassPathResource("/xml/").getFile();

        File root = new File(xmlDirectory.getAbsolutePath());
        File[] list = root.listFiles();

        assertNotNull(list);

        for (File f : list) {
            assertTrue(f.isFile());

            if (f.getName().endsWith(".xml")) {
                assertTrue(f.getName().endsWith(".xml"));
            }
        }
    }

    @Test
    @DisplayName("Should assert correct and incorrect 'vf:Data' child nodes")
    void childNodesFromXML() throws IOException, ParserConfigurationException, SAXException {
        File file = new ClassPathResource("xml/ruian_test.xml").getFile();

        assertEquals("ruian_test.xml", file.getName());

        //Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Build Document
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();

        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();

        assertNotEquals("vf:Staty", nodeListData.item(0).getNodeName());
        assertEquals("vf:Staty", nodeListData.item(1).getNodeName());

        assertNotEquals("vf:RegionySoudrznosti", nodeListData.item(2).getNodeName());
        assertEquals("vf:RegionySoudrznosti", nodeListData.item(3).getNodeName());

        assertNotEquals("vf:Vusc", nodeListData.item(4).getNodeName());
        assertEquals("vf:Vusc", nodeListData.item(5).getNodeName());

        assertNotEquals("vf:Okresy", nodeListData.item(6).getNodeName());
        assertEquals("vf:Okresy", nodeListData.item(7).getNodeName());

        assertNotEquals("vf:Orp", nodeListData.item(8).getNodeName());
        assertEquals("vf:Orp", nodeListData.item(9).getNodeName());

        assertNotEquals("vf:Pou", nodeListData.item(10).getNodeName());
        assertEquals("vf:Pou", nodeListData.item(11).getNodeName());

        assertNotEquals("vf:Obce", nodeListData.item(12).getNodeName());
        assertEquals("vf:Obce", nodeListData.item(13).getNodeName());

        assertNotEquals("vf:SpravniObvody", nodeListData.item(14).getNodeName());
        assertEquals("vf:SpravniObvody", nodeListData.item(15).getNodeName());

        assertNotEquals("vf:Mop", nodeListData.item(16).getNodeName());
        assertEquals("vf:Mop", nodeListData.item(17).getNodeName());

        assertNotEquals("vf:Momc", nodeListData.item(18).getNodeName());
        assertEquals("vf:Momc", nodeListData.item(19).getNodeName());

        assertNotEquals("vf:CastiObci", nodeListData.item(20).getNodeName());
        assertEquals("vf:CastiObci", nodeListData.item(21).getNodeName());

        assertNotEquals("vf:KatastralniUzemi", nodeListData.item(22).getNodeName());
        assertEquals("vf:KatastralniUzemi", nodeListData.item(23).getNodeName());

        assertNotEquals("vf:Zsj", nodeListData.item(24).getNodeName());
        assertEquals("vf:Zsj", nodeListData.item(25).getNodeName());

        assertNotEquals("vf:Ulice", nodeListData.item(26).getNodeName());
        assertEquals("vf:Ulice", nodeListData.item(27).getNodeName());

        assertNotEquals("vf:Parcely", nodeListData.item(28).getNodeName());
        assertEquals("vf:Parcely", nodeListData.item(29).getNodeName());

        assertNotEquals("vf:StavebniObjekty", nodeListData.item(30).getNodeName());
        assertEquals("vf:StavebniObjekty", nodeListData.item(31).getNodeName());

        assertNotEquals("vf:AdresniMista", nodeListData.item(32).getNodeName());
        assertEquals("vf:AdresniMista", nodeListData.item(33).getNodeName());
    }
}