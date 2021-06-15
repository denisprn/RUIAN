package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.CastObce;
import com.bp.RUIAN.entities.MluvnickeCharakteristiky;
import com.bp.RUIAN.entities.NespravnyUdaj;
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
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CastObceParserTest {
    private static final String PREFIX = Prefixes.CAST_OBCE_PREFIX;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    @DisplayName("Should parse CastObce from XML file")
    void parseCastObceElement() throws IOException, ParserConfigurationException, SAXException, ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        boolean nespravny = false;

        File file = new ClassPathResource("xml/ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(21).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:CastObce", node.getNodeName());

        Element element = (Element) node;

        AttributeParser attributeParser = new AttributeParser(element, PREFIX);

        Integer kod = attributeParser.getKod();
        String nazev = attributeParser.getNazev();
        Integer kodObce = attributeParser.getKodObce();
        Date platiOd = attributeParser.getPlatiOd();
        Date platiDo = attributeParser.getPlatiDo();
        Long idTransakce = attributeParser.getIdTransakce();
        Long globalniIdNavrhuZmeny = attributeParser.getGlobalniIdNavrhuZmeny();
        GeoJsonPoint definicniBod = attributeParser.getDefinicniBod();
        GeoJsonPolygon hranice = attributeParser.getHranice();
        MluvnickeCharakteristiky mluvChar = attributeParser.getMluvChar();
        NespravnyUdaj nespravnyUdaj = attributeParser.getNespravneUdaje();

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = attributeParser.getDatumVzniku();

        CastObce castObceExpected = new CastObce(32263, "Dobrš", false, 551023,
                sdf.parse("2013-11-11T00:00:00"), null,
                391375L, 450266L,
                new MluvnickeCharakteristiky("Dobrše", "Dobrši", "Dobrš", "Dobrši", "Dobrší"),
                GeoJsonPoint.of(new Point(-805130.00, -1138781.00)),
                null, null, null);

        CastObce castObceActual = new CastObce(kod, nazev, nespravny, kodObce, platiOd, platiDo,
                idTransakce, globalniIdNavrhuZmeny, mluvChar, definicniBod, hranice, nespravnyUdaj, datumVzniku);

        assertEquals(castObceExpected, castObceActual);
    }
}
