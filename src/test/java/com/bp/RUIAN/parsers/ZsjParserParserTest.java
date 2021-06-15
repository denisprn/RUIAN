package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.MluvnickeCharakteristiky;
import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.Zsj;
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

public class ZsjParserParserTest {
    private static final String PREFIX = Prefixes.ZSJ_PREFIX;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    @DisplayName("Should parse Zsj from XML file")
    void parseZsjElement() throws IOException, ParserConfigurationException, SAXException, ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        boolean nespravny = false;

        File file = new ClassPathResource("xml/ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(25).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Zsj", node.getNodeName());

        Element element = (Element) node;

        AttributeParser attributeParser = new AttributeParser(element, PREFIX);

        Integer kod = attributeParser.getKod();
        String nazev = attributeParser.getNazev();
        Integer kodKatastralniUzemi = attributeParser.getKodKatastralniUzemi();
        Date platiOd = attributeParser.getPlatiOd();
        Date platiDo = attributeParser.getPlatiDo();
        Long idTransakce = attributeParser.getIdTransakce();
        Long globalniIdNavrhuZmeny = attributeParser.getGlobalniIdNavrhuZmeny();
        Long vymera = attributeParser.getVymera();
        Integer charakterZsjKod = attributeParser.getCharakterZsjKod();
        GeoJsonPoint definicniBod = attributeParser.getDefinicniBod();
        GeoJsonPolygon hranice = attributeParser.getHranice();
        MluvnickeCharakteristiky mluvChar = attributeParser.getMluvChar();
        NespravnyUdaj nespravnyUdaj = attributeParser.getNespravneUdaje();

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = attributeParser.getDatumVzniku();

        List<Point> points = new ArrayList<>();
        points.add(new Point(-692061.58, -1005217.93));
        points.add(new Point(-692062.58, -1005218.93));
        points.add(new Point(-692067.54, -1005213.71));
        points.add(new Point(-692072.85, -1005209.95));

        Zsj zsjExpected = new Zsj(67067, "Suhrovice", false, 667064,
                sdf.parse("2015-06-05T00:00:00"), null, 895630L, 731555L, null,
                1753652L, 11, GeoJsonPoint.of(new Point(-691855.00, -1004625.00)),
                GeoJsonPolygon.of(points), null, sdf.parse("1970-12-01T00:00:00"));

        Zsj zsjActual = new Zsj(kod, nazev, nespravny, kodKatastralniUzemi, platiOd, platiDo, idTransakce,
                globalniIdNavrhuZmeny, mluvChar, vymera, charakterZsjKod, definicniBod,
                hranice, nespravnyUdaj, datumVzniku);

        assertEquals(zsjExpected, zsjActual);
    }
}
