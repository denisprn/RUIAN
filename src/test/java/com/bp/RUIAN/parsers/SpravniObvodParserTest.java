package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.SpravniObvod;
import com.bp.RUIAN.utils.Prefixes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;
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

public class SpravniObvodParserTest {
    private static final String PREFIX = Prefixes.SPRAVNI_OBVOD_PREFIX;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    @DisplayName("Should parse SpravniObvod from XML file")
    void parseSpravniObvodElement() throws IOException, ParserConfigurationException, SAXException, ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        boolean nespravny = false;

        File file = new ClassPathResource("xml/ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(15).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:SpravniObvod", node.getNodeName());

        Element element = (Element) node;

        AttributeParser attributeParser = new AttributeParser(element, PREFIX);

        Integer kod = attributeParser.getKod();
        String nazev = attributeParser.getNazev();
        Integer spravniMomcKod = attributeParser.getSpravniMomcKod();
        Integer kodObce = attributeParser.getKodObce();
        Date platiOd = attributeParser.getPlatiOd();
        Date platiDo = attributeParser.getPlatiDo();
        Long idTransakce = attributeParser.getIdTransakce();
        Long globalniIdNavrhuZmeny = attributeParser.getGlobalniIdNavrhuZmeny();
        GeoJsonPoint definicniBod = attributeParser.getDefinicniBod();
        GeoJsonPolygon hranice = attributeParser.getHranice();
        NespravnyUdaj nespravnyUdaj = attributeParser.getNespravneUdaje();

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = attributeParser.getDatumVzniku();

        List<Point> points = new ArrayList<>();
        points.add(new Point(-743272.11, -1042309.04));
        points.add(new Point(-743273.11, -1042310.04));
        points.add(new Point(-743242.23, -1042305.23));
        points.add(new Point(-743203.74, -1042302.40));

        SpravniObvod spravniObvodExpected = new SpravniObvod(19, "Praha 1", false, 500054,
                554782, sdf.parse("2013-11-13T00:00:00"), null,
                393750L, 452737L,
                GeoJsonPoint.of(new Point(-742600.00, -1043000.00)),
                GeoJsonPolygon.of(points),null, null);

        SpravniObvod spravniObvodActual = new SpravniObvod(kod, nazev, nespravny, spravniMomcKod, kodObce, platiOd, platiDo,
                idTransakce, globalniIdNavrhuZmeny, definicniBod, hranice, nespravnyUdaj, datumVzniku);

        assertEquals(spravniObvodExpected, spravniObvodActual);
    }
}
