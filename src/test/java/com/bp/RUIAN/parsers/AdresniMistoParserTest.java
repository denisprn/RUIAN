package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.AdresniMisto;
import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.utils.Prefixes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
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

public class AdresniMistoParserTest {
    private static final String PREFIX = Prefixes.ADRESNI_MISTO_PREFIX;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    @DisplayName("Should parse AdresniMisto from XML file")
    void parseAdresniMistoElement() throws IOException, ParserConfigurationException, SAXException, ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        boolean nespravny = false;

        File file = new ClassPathResource("xml/ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(33).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:AdresniMisto", node.getNodeName());

        Element element = (Element) node;

        AttributeParser attributeParser = new AttributeParser(element, PREFIX);

        Integer kod = attributeParser.getKod();
        String cisloDomovni = attributeParser.getCisloDomovni();
        String cisloOrientacni = attributeParser.getCisloOrientacni();
        String cisloOrientacniPismeno = attributeParser.getCisloOrientacniPismeno();
        String psc = attributeParser.getPSC();
        Integer stavebniObjektKod = attributeParser.getKodStavebniObjekt();
        Integer uliceKod = attributeParser.getKodUlice();
        Integer voKod = attributeParser.getVOKod();
        Date platiOd = attributeParser.getPlatiOd();
        Date platiDo = attributeParser.getPlatiDo();
        Long idTransakce = attributeParser.getIdTransakce();
        Long globalniIdNavrhuZmeny = attributeParser.getGlobalniIdNavrhuZmeny();
        GeoJsonPoint definicniBod = attributeParser.getDefinicniBod();
        NespravnyUdaj nespravnyUdaj = attributeParser.getNespravneUdaje();

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        AdresniMisto adresniMistoExpected = new AdresniMisto(72850124, false, "668", "55",
                "A", "76311", 78171482, 647268, 42877,
                sdf.parse("2014-06-26T00:00:00"), null,
                630728L, 594036L, GeoJsonPoint.of(new Point(-516005.93, -1168276.78)), null);

        AdresniMisto adresniMistoActual = new AdresniMisto(kod, nespravny, cisloDomovni, cisloOrientacni,
                cisloOrientacniPismeno, psc, stavebniObjektKod, uliceKod, voKod, platiOd, platiDo,
                idTransakce, globalniIdNavrhuZmeny, definicniBod, nespravnyUdaj);

        assertEquals(adresniMistoExpected, adresniMistoActual);
    }
}
