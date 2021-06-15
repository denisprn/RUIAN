package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.Okres;
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

public class OkresParserTest {
    private static final String PREFIX = Prefixes.OKRES_PREFIX;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    @DisplayName("Should parse Okres from XML file")
    void parseOkresElement() throws ParserConfigurationException, IOException,
            SAXException, ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        boolean nespravny = false;

        File file = new ClassPathResource("xml/ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(7).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Okres", node.getNodeName());

        Element element = (Element) node;

        AttributeParser attributeParser = new AttributeParser(element, PREFIX);

        Integer kod = attributeParser.getKod();
        String nazev = attributeParser.getNazev();
        Integer kodVusc = attributeParser.getKodVusc();
        Date platiOd = attributeParser.getPlatiOd();
        Date platiDo = attributeParser.getPlatiDo();
        Long idTransakce = attributeParser.getIdTransakce();
        Long globalniIdNavrhuZmeny = attributeParser.getGlobalniIdNavrhuZmeny();
        String nutsLau = attributeParser.getNutsLau();
        GeoJsonPoint definicniBod = attributeParser.getDefinicniBod();
        GeoJsonPolygon hranice = attributeParser.getHranice();
        NespravnyUdaj nespravnyUdaj = attributeParser.getNespravneUdaje();

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = attributeParser.getDatumVzniku();

        List<Point> points = new ArrayList<>();
        points.add(new Point(-736098.50, -1034452.00));
        points.add(new Point(-736099.50, -1034453.00));
        points.add(new Point(-735474.72, -1034591.34));
        points.add(new Point(-735280.35, -1034312.02));

        Okres okresExpected = new Okres(3100, "Hlavní město Praha", true, 19,
                sdf.parse("2021-01-02T00:00:00"), null, 3650029L,
                2398502L, "CZ0100", GeoJsonPoint.of(new Point(-743100.00, -1043300.00)),
                GeoJsonPolygon.of(points),
                new NespravnyUdaj("KODU", sdf.parse("2021-01-03T09:57:21"),
                        "Okres Hlavní město Praha byl k 1. 1. 2021 zrušen v souvislosti se zrušením vyhlášky č. 564/2002 Sb."),
                sdf.parse("1960-04-11T00:00:00"));

        Okres okresActual = new Okres(kod, nazev, nespravny, kodVusc,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, nutsLau,
                definicniBod, hranice, nespravnyUdaj, datumVzniku);

        assertEquals(okresExpected, okresActual);
    }
}
