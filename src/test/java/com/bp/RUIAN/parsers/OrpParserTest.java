package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.Orp;
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

public class OrpParserTest {
    private static final String PREFIX = Prefixes.ORP_PREFIX;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    @DisplayName("Should parse Orp from XML file")
    void parseOrpElement() throws IOException, ParserConfigurationException, SAXException, ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        boolean nespravny = false;

        File file = new ClassPathResource("xml/ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(9).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Orp", node.getNodeName());

        Element element = (Element) node;

        AttributeParser attributeParser = new AttributeParser(element, PREFIX);

        Integer kod = attributeParser.getKod();
        String nazev = attributeParser.getNazev();
        Integer spravniObecKod = attributeParser.getKodSpravniObec();
        Integer kodOkresu = attributeParser.getKodOkresu();
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
        points.add(new Point(-754587.64, -1025005.93));
        points.add(new Point(-754588.64, -1025006.93));
        points.add(new Point(-754510.23, -1024994.37));
        points.add(new Point(-754408.01, -1025109.34));

        Orp orpExpected = new Orp(78, "Kladno", false, 532053, 3203,
                sdf.parse("2021-03-18T00:00:00"), null, 3748176L,
                2450941L,GeoJsonPoint.of(new Point(-764439.00, -1033266.00)),
                GeoJsonPolygon.of(points),
                null, sdf.parse("2003-01-01T00:00:00"));

        Orp orpActual = new Orp(kod, nazev, nespravny, spravniObecKod, kodOkresu,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, definicniBod, hranice, nespravnyUdaj, datumVzniku);

        assertEquals(orpExpected, orpActual);
    }
}
