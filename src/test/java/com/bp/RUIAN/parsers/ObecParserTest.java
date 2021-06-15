package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.MluvnickeCharakteristiky;
import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.Obec;
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

public class ObecParserTest {
    private static final String PREFIX = Prefixes.OBEC_PREFIX;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    @DisplayName("Should parse Obec from XML file")
    void parseObecElement() throws IOException, ParserConfigurationException, SAXException, ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        boolean nespravny = false;

        File file = new ClassPathResource("xml/ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(13).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Obec", node.getNodeName());

        Element element = (Element) node;

        AttributeParser attributeParser = new AttributeParser(element, PREFIX);

        Integer kod = attributeParser.getKod();
        String nazev = attributeParser.getNazev();
        Integer statusKod = attributeParser.getStatusKod();
        Integer kodOkresu = attributeParser.getKodOkresu();
        Integer kodPou = attributeParser.getKodPou();
        Date platiOd = attributeParser.getPlatiOd();
        Date platiDo = attributeParser.getPlatiDo();
        Long idTransakce = attributeParser.getIdTransakce();
        Long globalniIdNavrhuZmeny = attributeParser.getGlobalniIdNavrhuZmeny();
        GeoJsonPoint definicniBod = attributeParser.getDefinicniBod();
        GeoJsonPolygon hranice = attributeParser.getHranice();
        MluvnickeCharakteristiky mluvChar = attributeParser.getMluvChar();
        String vlajkaText = attributeParser.getVlajkaText();
        String vlajkaObrazek = attributeParser.getVlajkaObrazek();
        String znakText = attributeParser.getZnakText();
        String znakObrazek = attributeParser.getZnakObrazek();
        String nutsLau = attributeParser.getNutsLau();
        Integer cleneniSMTypKod = attributeParser.getCleneniSMTypKod();
        Integer cleneniSMRozsahKod = attributeParser.getCleneniSMRozsahKod();
        NespravnyUdaj nespravnyUdaj = attributeParser.getNespravneUdaje();

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = attributeParser.getDatumVzniku();

        List<Point> points = new ArrayList<>();
        points.add(new Point(-657677.99, -1087738.86));
        points.add(new Point(-657678.99, -1087739.86));
        points.add(new Point(-657672.71, -1087682.56));
        points.add(new Point(-657616.44, -1087590.72));

        Obec obecExpected = new Obec(569089, "Maleč", false, 2, 3601, 1970,
                sdf.parse("2019-01-16T00:00:00"), null, 2718946L,
                1933345L,
                new MluvnickeCharakteristiky("Malče", "Malči", "Maleč", "Malči", "Malčí"),
                "List tvoří dva žluté žerďové klíny s vrcholy v první čtvrtině délky listu, červené pole s bílým mořským psem se žlutou zbrojí a dva svislé pruhy, bílý a modrý, každý široký jednu osminu délky listu. Poměr šířky k délce listu je 2:3.",
                null, "V červeném štítě se stříbrno-modře vlnitě dělenou vlnitou patou, pod dvěma zlatými, ke středu prolomenými zvýšenými klíny stříbrný mořský pes se zlatou zbrojí.", null,
                null, null, "CZ0631569089",
                GeoJsonPoint.of(new Point(-657903.00, -1089346.00)),
                GeoJsonPolygon.of(points),null, null);

        Obec obecActual = new Obec(kod, nazev, nespravny, statusKod, kodOkresu, kodPou,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, mluvChar, vlajkaText, vlajkaObrazek,
                znakText, znakObrazek, cleneniSMTypKod, cleneniSMRozsahKod, nutsLau, definicniBod,
                hranice, nespravnyUdaj, datumVzniku);

        assertEquals(obecExpected, obecActual);
    }
}
