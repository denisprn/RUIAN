package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.MluvnickeCharakteristiky;
import com.bp.RUIAN.entities.Momc;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MomcParserTest {
    private static final String PREFIX = Prefixes.MOMC_PREFIX;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    @DisplayName("Should parse Momc from XML file")
    void parseMomcElement() throws IOException, ParserConfigurationException, SAXException, ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        boolean nespravny = false;

        File file = new ClassPathResource("xml/ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(19).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Momc", node.getNodeName());

        Element element = (Element) node;

        AttributeParser attributeParser = new AttributeParser(element, PREFIX);

        Integer kod = attributeParser.getKod();
        String nazev = attributeParser.getNazev();
        Integer kodMop = attributeParser.getKodMop();
        Integer kodObce = attributeParser.getKodObce();
        Integer kodSpravniObvod = attributeParser.getKodSpravniObvod();
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
        NespravnyUdaj nespravnyUdaj = attributeParser.getNespravneUdaje();

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = attributeParser.getDatumVzniku();

        List<Point> points = new ArrayList<>();
        points.add(new Point(-733972.62, -1034123.26));
        points.add(new Point(-733973.62, -1034124.26));
        points.add(new Point(-733959.18, -1034126.77));
        points.add(new Point(-733954.37, -1034129.44));

        Momc momcExpected = new Momc(547310, "Praha-Čakovice", false, 94,
                554782, 221, sdf.parse("2021-03-23T00:00:00"), null,
                3751290L, 2454410L, "List tvoří osm vodorovných pruhů, modrý a " +
                "sedm střídavě žlutých a černých, v poměru 7:1:1:1:1:1:1:1. V žerďové polovině modrého pruhu žlutá lilie. " +
                "Poměr šířky k délce listu je 2:3.",
                null, new MluvnickeCharakteristiky("Prahy-Čakovic", "Praze-Čakovicím", "Prahu-Čakovice",
                "Praze-Čakovicích", "Prahou-Čakovicemi"),
                "Polcený štít, pravá polovina modro-zlatě dělena, nahoře zlatá lilie, dole tři černá břevna. " +
                        "V levé červené polovině zlatá kvádrovaná věž se stříbrným cimbuřím se čtyřmi stínkami, " +
                        "z nichž krajní jsou viditelné z poloviny. Zeď je u poltící linie prolomena polovinou černé " +
                        "brány se stříbrnou klenbou a z poloviny viditelným zlatým klenákem. Brána má otevřená dřevěná " +
                        "vrata se zlatými panty a vytaženou zlatou mříž. Z brány vyniká stříbrné obrněné rameno držící " +
                        "stříbrný meč se zlatým jílcem. Za zdí vynikají dvě zlaté kvádrované věže s cimbuřím, zlatými " +
                        "střechami a makovicemi. Pravá věž, viditelná z poloviny, je vyšší a širší, má polovinu černého " +
                        "trojdílného okna ve stříbrném rámu a valbovou střechu. Levá, nižší a užší věž má jedno černé " +
                        "dvoudílné okno ve stříbrném rámu a stanovou střechu.",
                null, GeoJsonPoint.of(new Point(-734200.00, -1036800.00)),
                GeoJsonPolygon.of(points), null, sdf.parse("1977-01-01T00:00:00"));

        Momc momcActual = new Momc(kod, nazev, nespravny, kodMop, kodObce, kodSpravniObvod, platiOd, platiDo,
                idTransakce, globalniIdNavrhuZmeny, vlajkaText, vlajkaObrazek, mluvChar, znakText, znakObrazek,
                definicniBod, hranice, nespravnyUdaj, datumVzniku);

        assertEquals(momcExpected, momcActual);
    }
}
