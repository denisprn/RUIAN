package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.Ulice;
import com.bp.RUIAN.utils.Prefixes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.elasticsearch.core.geo.GeoJsonLineString;
import org.springframework.data.elasticsearch.core.geo.GeoJsonMultiLineString;
import org.springframework.data.geo.Point;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UliceParserTest {
    private static final String PREFIX = Prefixes.ULICE_PREFIX;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    @DisplayName("Should parse Ulice from XML file")
    void parseUliceElement() throws IOException, ParserConfigurationException, SAXException, ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        boolean nespravny = false;

        File file = new ClassPathResource("xml/ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(27).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Ulice", node.getNodeName());

        Element element = (Element) node;

        AttributeParser attributeParser = new AttributeParser(element, PREFIX);

        Integer kod = attributeParser.getKod();
        String nazev = attributeParser.getNazev();
        Integer kodObce = attributeParser.getKodObce();
        Date platiOd = attributeParser.getPlatiOd();
        Date platiDo = attributeParser.getPlatiDo();
        Long idTransakce = attributeParser.getIdTransakce();
        Long globalniIdNavrhuZmeny = attributeParser.getGlobalniIdNavrhuZmeny();
        GeoJsonMultiLineString definicniCara = attributeParser.getDefinicniCara();
        NespravnyUdaj nespravnyUdaj = attributeParser.getNespravneUdaje();

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        List<Point> points1 = new ArrayList<>();
        points1.add(new Point(-515244.88, -1166620.04));
        points1.add(new Point(-515262.38, -1166596.12));
        points1.add(new Point(-515278.17, -1166577.53));

        List<Point> points2 = new ArrayList<>();
        points2.add(new Point(-515183.48, -1166654.22));
        points2.add(new Point(-515146.99, -1166665.95));
        points2.add(new Point(-515122.60, -1166672.64));

        List<GeoJsonLineString> definicniCaraList = new ArrayList<>();
        definicniCaraList.add(GeoJsonLineString.of(points1));
        definicniCaraList.add(GeoJsonLineString.of(points2));

        Ulice uliceExpected = new Ulice(644196, "4. května", false, 500011,
                sdf.parse("2018-01-22T00:00:00"), null, 2277057L, 1672622L,
                GeoJsonMultiLineString.of(definicniCaraList),null);

        Ulice uliceActual = new Ulice(kod, nazev, nespravny, kodObce,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, definicniCara, nespravnyUdaj);

        assertEquals(uliceExpected, uliceActual);
    }
}
