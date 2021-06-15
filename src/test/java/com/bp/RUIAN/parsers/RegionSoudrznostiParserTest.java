package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.RegionSoudrznosti;
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

public class RegionSoudrznostiParserTest {
    private static final String PREFIX = Prefixes.REGION_SOUDRZNOSTI_PREFIX;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    @DisplayName("Should parse RegionSoudrznosti from XML file")
    void parseRegionSoudrznostiElement() throws ParserConfigurationException, IOException,
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
        NodeList nodeL = nodeListData.item(3).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:RegionSoudrznosti", node.getNodeName());

        Element element = (Element) node;

        AttributeParser attributeParser = new AttributeParser(element, PREFIX);

        Integer kod =  attributeParser.getKod();
        String nazev = attributeParser.getNazev();
        Integer kodStatu = attributeParser.getKodStatu();
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
        points.add(new Point(-733566.86, -1053251.22));
        points.add(new Point(-733567.86, -1053252.22));
        points.add(new Point(-733912.03, -1052746.38));
        points.add(new Point(-734508.61, -1052601.41));

        RegionSoudrznosti regionSoudrznostiExpected = new RegionSoudrznosti(19, "Praha", false, 1,
                sdf.parse("2016-11-23T00:00:00"), null, 1572842L,
                1173831L, "CZ01", GeoJsonPoint.of(new Point(-743100.00, -1043300.00)),
                GeoJsonPolygon.of(points), null, sdf.parse("2001-01-01T00:00:00"));

        RegionSoudrznosti regionSoudrznostiActual = new RegionSoudrznosti(kod, nazev, nespravny, kodStatu,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, nutsLau, definicniBod, hranice, nespravnyUdaj, datumVzniku);

        assertEquals(regionSoudrznostiExpected, regionSoudrznostiActual);
    }
}
