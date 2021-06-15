package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.BonitovanyDil;
import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.Parcela;
import com.bp.RUIAN.entities.ZpusobOchranyPozemku;
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

public class ParcelaParserTest {
    private static final String PREFIX = Prefixes.PARCELA_PREFIX;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    @DisplayName("Should parse Parcela from XML file")
    void parseParcelaElement() throws IOException, ParserConfigurationException, SAXException, ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        boolean nespravny = false;

        File file = new ClassPathResource("xml/ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(29).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Parcela", node.getNodeName());

        Element element = (Element) node;

        AttributeParser attributeParser = new AttributeParser(element, PREFIX);

        Long id = attributeParser.getId();
        Integer kmenoveCislo = attributeParser.getKmenoveCislo();
        Integer pododdeleniCisla = attributeParser.getPoddodeleniCisla();
        Long vymeraParcely = attributeParser.getVymeraParcely();
        Integer druhCislovaniKod = attributeParser.getDruhCislovaniKod();
        Integer druhPozemkuKod = attributeParser.getDruhPozemkuKod();
        Integer kodKatastralniUzemi = attributeParser.getKodKatastralniUzemi();
        Long rizeniId = attributeParser.getIdRizeni();
        Date platiOd = attributeParser.getPlatiOd();
        Date platiDo = attributeParser.getPlatiDo();
        Long idTransakce = attributeParser.getIdTransakce();
        List<BonitovanyDil> bonitovaneDily = attributeParser.getBonitovaneDily();
        ZpusobOchranyPozemku zpusobOchranyPozemku = attributeParser.getZpusobOchranyPozemku();
        GeoJsonPoint definicniBod = attributeParser.getDefinicniBod();
        GeoJsonPolygon hranice = attributeParser.getHranice();
        NespravnyUdaj nespravnyUdaj = attributeParser.getNespravneUdaje();

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        List<Point> points = new ArrayList<>();
        points.add(new Point(-516104.71, -1166826.42));
        points.add(new Point(-516105.71, -1166827.42));
        points.add(new Point(-516105.35, -1166833.31));
        points.add(new Point(-516106.98, -1166833.16));

        Parcela parcelaExpected = new Parcela(79432960010L, false, 457, 22, 200L,
                2, 13, 795909, sdf.parse("2020-11-13T00:00:00"),
                null, 3587101L, 78837743010L, null, null,
                GeoJsonPoint.of(new Point(-516112.58, -1166833.49)),
                GeoJsonPolygon.of(points), null);

        Parcela parcelaActual = new Parcela(id, nespravny, kmenoveCislo, pododdeleniCisla, vymeraParcely,
                druhCislovaniKod, druhPozemkuKod, kodKatastralniUzemi, platiOd, platiDo, idTransakce,
                rizeniId, bonitovaneDily, zpusobOchranyPozemku, definicniBod, hranice, nespravnyUdaj);

        assertEquals(parcelaExpected, parcelaActual);
    }
}
