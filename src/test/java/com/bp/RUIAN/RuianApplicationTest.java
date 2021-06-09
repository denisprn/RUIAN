package com.bp.RUIAN;

import com.bp.RUIAN.entities.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
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

import static org.junit.jupiter.api.Assertions.*;


class RuianApplicationTest {
    private static Document document;

    private MluvnickeCharakteristiky getMluvChar(Element eElement) {
        String pad2 = eElement.getElementsByTagName("com:Pad2").item(0).getTextContent();
        String pad3 = eElement.getElementsByTagName("com:Pad3").item(0).getTextContent();
        String pad4 = eElement.getElementsByTagName("com:Pad4").item(0).getTextContent();
        String pad6 = eElement.getElementsByTagName("com:Pad6").item(0).getTextContent();
        String pad7 = eElement.getElementsByTagName("com:Pad7").item(0).getTextContent();
        return new MluvnickeCharakteristiky(pad2, pad3, pad4, pad6, pad7);
    }

    private String getVlajkaText(Element eElement, String prefix) {
        NodeList nListVlajkaText = eElement.getElementsByTagName(prefix + ":VlajkaText");

        if (nListVlajkaText.getLength() > 0) {
            return nListVlajkaText.item(0).getTextContent();
        }

        return null;
    }

    private String getVlajkaObrazek(Element eElement, String prefix) {
        NodeList nListVlajkaObrazek = eElement.getElementsByTagName(prefix + ":VlajkaObrazek");

        if (nListVlajkaObrazek.getLength() > 0) {
            return nListVlajkaObrazek.item(0).getTextContent();
        }

        return null;
    }

    private String getZnakObrazek(Element eElement, String prefix) {
        NodeList nListZnakObrazek = eElement.getElementsByTagName(prefix + ":ZnakObrazek");

        if (nListZnakObrazek.getLength() > 0) {
            return nListZnakObrazek.item(0).getTextContent();
        }

        return null;
    }

    private String getZnakText(Element eElement, String prefix) {
        NodeList nListZnakText = eElement.getElementsByTagName(prefix + ":ZnakText");

        if (nListZnakText.getLength() > 0) {
            return nListZnakText.item(0).getTextContent();
        }

        return null;
    }

    @BeforeAll
    static void prepareXMLDoc() throws ParserConfigurationException, IOException, SAXException {
        File file = new ClassPathResource("ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(file);
        document.getDocumentElement().normalize();
    }

    @Test
    @DisplayName("Should assert correct and incorrect 'vf:Data' child nodes")
    void childNodesFromXML() {
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();

        assertNotEquals("vf:Staty", nodeListData.item(0).getNodeName());
        assertEquals("vf:Staty", nodeListData.item(1).getNodeName());

        assertNotEquals("vf:RegionySoudrznosti", nodeListData.item(2).getNodeName());
        assertEquals("vf:RegionySoudrznosti", nodeListData.item(3).getNodeName());

        assertNotEquals("vf:Vusc", nodeListData.item(4).getNodeName());
        assertEquals("vf:Vusc", nodeListData.item(5).getNodeName());

        assertNotEquals("vf:Okresy", nodeListData.item(6).getNodeName());
        assertEquals("vf:Okresy", nodeListData.item(7).getNodeName());

        assertNotEquals("vf:Orp", nodeListData.item(8).getNodeName());
        assertEquals("vf:Orp", nodeListData.item(9).getNodeName());

        assertNotEquals("vf:Pou", nodeListData.item(10).getNodeName());
        assertEquals("vf:Pou", nodeListData.item(11).getNodeName());

        assertNotEquals("vf:Obce", nodeListData.item(12).getNodeName());
        assertEquals("vf:Obce", nodeListData.item(13).getNodeName());

        assertNotEquals("vf:SpravniObvody", nodeListData.item(14).getNodeName());
        assertEquals("vf:SpravniObvody", nodeListData.item(15).getNodeName());

        assertNotEquals("vf:Mop", nodeListData.item(16).getNodeName());
        assertEquals("vf:Mop", nodeListData.item(17).getNodeName());

        assertNotEquals("vf:Momc", nodeListData.item(18).getNodeName());
        assertEquals("vf:Momc", nodeListData.item(19).getNodeName());

        assertNotEquals("vf:CastiObci", nodeListData.item(20).getNodeName());
        assertEquals("vf:CastiObci", nodeListData.item(21).getNodeName());

        assertNotEquals("vf:KatastralniUzemi", nodeListData.item(22).getNodeName());
        assertEquals("vf:KatastralniUzemi", nodeListData.item(23).getNodeName());

        assertNotEquals("vf:Zsj", nodeListData.item(24).getNodeName());
        assertEquals("vf:Zsj", nodeListData.item(25).getNodeName());

        assertNotEquals("vf:Ulice", nodeListData.item(26).getNodeName());
        assertEquals("vf:Ulice", nodeListData.item(27).getNodeName());

        assertNotEquals("vf:Parcely", nodeListData.item(28).getNodeName());
        assertEquals("vf:Parcely", nodeListData.item(29).getNodeName());

        assertNotEquals("vf:StavebniObjekty", nodeListData.item(30).getNodeName());
        assertEquals("vf:StavebniObjekty", nodeListData.item(31).getNodeName());

        assertNotEquals("vf:AdresniMista", nodeListData.item(32).getNodeName());
        assertEquals("vf:AdresniMista", nodeListData.item(33).getNodeName());
    }

    @Test
    @DisplayName("Should parse Stat from XML file")
    void parseStatFromXML() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date platiDo = null;
        boolean nespravny = false;
        NespravnyUdaj nespravnyUdaj = null;

        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(1).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Stat", node.getNodeName());

        Element eElement = (Element) node;
        Integer kod = Integer.parseInt(eElement.getElementsByTagName("sti:Kod").item(0).getTextContent());
        String nazev = eElement.getElementsByTagName("sti:Nazev").item(0).getTextContent();
        Date platiOd = sdf.parse(eElement.getElementsByTagName("sti:PlatiOd").item(0).getTextContent());

        NodeList nListPlatiDo = eElement.getElementsByTagName("sti:PlatiDo");

        if (nListPlatiDo.getLength() > 0) {
            platiDo = sdf.parse(nListPlatiDo.item(0).getTextContent());
        }

        Long idTransakce = Long.parseLong(eElement.getElementsByTagName("sti:IdTransakce")
                .item(0).getTextContent());
        Long globalniIdNavrhuZmeny = Long.parseLong(eElement.getElementsByTagName("sti:GlobalniIdNavrhuZmeny")
                .item(0).getTextContent());
        String nutsLau = eElement.getElementsByTagName("sti:NutsLau").item(0).getTextContent();

        String position = eElement.getElementsByTagName("gml:pos").item(0).getTextContent();

        double x = Double.parseDouble(position.split(" ")[0]);
        double y = Double.parseDouble(position.split(" ")[1]);

        Point pos = new Point(x, y);

        NodeList nListNU = eElement.getElementsByTagName("sti:NespravneUdaje");

        if (nListNU.getLength() > 0) {
            nespravny = true;
            String nazevUdaje = eElement.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
            Date oznacenoDne = sdf.parse(eElement.getElementsByTagName("com:OznacenoDne").item(0).getTextContent());
            String oznacenoInfo = eElement.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();
            nespravnyUdaj = new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
        }

        Date datumVzniku = sdf.parse(eElement.getElementsByTagName("sti:DatumVzniku").item(0).getTextContent());

        Stat statExpected = new Stat(1, "Česká republika", false, sdf.parse("2015-06-06T00:00:00"),
                null, 0L, 731654L, "CZ", new Point(-743100.00, -1043300.00),
                null, sdf.parse("1993-01-01T00:00:00"));

        Stat statActual = new Stat(kod, nazev, nespravny, platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny,
                nutsLau, pos, nespravnyUdaj, datumVzniku);

        assertEquals(statExpected, statActual);
    }

    @Test
    @DisplayName("Should parse RegionSoudrznosti from XML file")
    void parseRegionSoudrznostiFromXML() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date platiDo = null;
        boolean nespravny = false;
        NespravnyUdaj nespravnyUdaj = null;

        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(3).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:RegionSoudrznosti", node.getNodeName());

        Element eElement = (Element) node;
        Integer kod = Integer.parseInt(eElement.getElementsByTagName("rsi:Kod").item(0).getTextContent());
        String nazev = eElement.getElementsByTagName("rsi:Nazev").item(0).getTextContent();
        Date platiOd = sdf.parse(eElement.getElementsByTagName("rsi:PlatiOd").item(0).getTextContent());
        Integer kodStatu = Integer.parseInt(eElement.getElementsByTagName("sti:Kod").item(0).getTextContent());
        NodeList nListPlatiDo = eElement.getElementsByTagName("rsi:PlatiDo");

        if (nListPlatiDo.getLength() > 0) {
            platiDo = sdf.parse(nListPlatiDo.item(0).getTextContent());
        }

        Long idTransakce = Long.parseLong(eElement.getElementsByTagName("rsi:IdTransakce")
                .item(0).getTextContent());
        Long globalniIdNavrhuZmeny = Long.parseLong(eElement.getElementsByTagName("rsi:GlobalniIdNavrhuZmeny")
                .item(0).getTextContent());
        String nutsLau = eElement.getElementsByTagName("rsi:NutsLau").item(0).getTextContent();

        String position = eElement.getElementsByTagName("gml:pos").item(0).getTextContent();

        double x = Double.parseDouble(position.split(" ")[0]);
        double y = Double.parseDouble(position.split(" ")[1]);

        Point pos = new Point(x, y);

        NodeList nListNU = eElement.getElementsByTagName("rsi:NespravneUdaje");

        if (nListNU.getLength() > 0) {
            nespravny = true;
            String nazevUdaje = eElement.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
            Date oznacenoDne = sdf.parse(eElement.getElementsByTagName("com:OznacenoDne").item(0).getTextContent());
            String oznacenoInfo = eElement.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();
            nespravnyUdaj = new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
        }

        Date datumVzniku = sdf.parse(eElement.getElementsByTagName("rsi:DatumVzniku").item(0).getTextContent());

        RegionSoudrznosti regionSoudrznostiExpected = new RegionSoudrznosti(19, "Praha", false, 1,
                sdf.parse("2016-11-23T00:00:00"), null, 1572842L,
                1173831L, "CZ01", new Point(-743100.00, -1043300.00),
                null, sdf.parse("2001-01-01T00:00:00"));

        RegionSoudrznosti regionSoudrznostiActual = new RegionSoudrznosti(kod, nazev, nespravny, kodStatu,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, nutsLau, pos, nespravnyUdaj, datumVzniku);

        assertEquals(regionSoudrznostiExpected, regionSoudrznostiActual);
    }

    @Test
    @DisplayName("Should parse Vusc from XML file")
    void parseVuscFromXML() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date platiDo = null;
        boolean nespravny = false;
        NespravnyUdaj nespravnyUdaj = null;

        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(5).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Vusc", node.getNodeName());

        Element eElement = (Element) node;
        Integer kod = Integer.parseInt(eElement.getElementsByTagName("vci:Kod").item(0).getTextContent());
        String nazev = eElement.getElementsByTagName("vci:Nazev").item(0).getTextContent();
        Date platiOd = sdf.parse(eElement.getElementsByTagName("vci:PlatiOd").item(0).getTextContent());
        Integer kodRs = Integer.parseInt(eElement.getElementsByTagName("rsi:Kod").item(0).getTextContent());
        NodeList nListPlatiDo = eElement.getElementsByTagName("vci:PlatiDo");

        if (nListPlatiDo.getLength() > 0) {
            platiDo = sdf.parse(nListPlatiDo.item(0).getTextContent());
        }

        Long idTransakce = Long.parseLong(eElement.getElementsByTagName("vci:IdTransakce")
                .item(0).getTextContent());
        Long globalniIdNavrhuZmeny = Long.parseLong(eElement.getElementsByTagName("vci:GlobalniIdNavrhuZmeny")
                .item(0).getTextContent());
        String nutsLau = eElement.getElementsByTagName("vci:NutsLau").item(0).getTextContent();

        String position = eElement.getElementsByTagName("gml:pos").item(0).getTextContent();

        double x = Double.parseDouble(position.split(" ")[0]);
        double y = Double.parseDouble(position.split(" ")[1]);

        Point pos = new Point(x, y);

        NodeList nListNU = eElement.getElementsByTagName("vci:NespravneUdaje");

        if (nListNU.getLength() > 0) {
            nespravny = true;
            String nazevUdaje = eElement.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
            Date oznacenoDne = sdf.parse(eElement.getElementsByTagName("com:OznacenoDne").item(0).getTextContent());
            String oznacenoInfo = eElement.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();
            nespravnyUdaj = new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
        }

        Date datumVzniku = sdf.parse(eElement.getElementsByTagName("vci:DatumVzniku").item(0).getTextContent());

        Vusc vuscExpected = new Vusc(19, "Hlavní město Praha", false, 19,
                sdf.parse("2016-11-23T00:00:00"), null, 1572842L,
                1173831L, "CZ010", new Point(-743100.00, -1043300.00),
                null, sdf.parse("2000-01-01T00:00:00"));

        Vusc vuscActual = new Vusc(kod, nazev, nespravny, kodRs,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, nutsLau, pos, nespravnyUdaj, datumVzniku);

        assertEquals(vuscExpected, vuscActual);
    }

    @Test
    @DisplayName("Should parse Okres from XML file")
    void parseOkresFromXML() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date platiDo = null;
        boolean nespravny = false;
        NespravnyUdaj nespravnyUdaj = null;

        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(7).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Okres", node.getNodeName());

        Element eElement = (Element) node;
        Integer kod = Integer.parseInt(eElement.getElementsByTagName("oki:Kod").item(0).getTextContent());
        String nazev = eElement.getElementsByTagName("oki:Nazev").item(0).getTextContent();
        Date platiOd = sdf.parse(eElement.getElementsByTagName("oki:PlatiOd").item(0).getTextContent());
        Integer kodVusc = Integer.parseInt(eElement.getElementsByTagName("vci:Kod").item(0).getTextContent());
        NodeList nListPlatiDo = eElement.getElementsByTagName("oki:PlatiDo");

        if (nListPlatiDo.getLength() > 0) {
            platiDo = sdf.parse(nListPlatiDo.item(0).getTextContent());
        }

        Long idTransakce = Long.parseLong(eElement.getElementsByTagName("oki:IdTransakce")
                .item(0).getTextContent());
        Long globalniIdNavrhuZmeny = Long.parseLong(eElement.getElementsByTagName("oki:GlobalniIdNavrhuZmeny")
                .item(0).getTextContent());
        String nutsLau = eElement.getElementsByTagName("oki:NutsLau").item(0).getTextContent();

        String position = eElement.getElementsByTagName("gml:pos").item(0).getTextContent();

        double x = Double.parseDouble(position.split(" ")[0]);
        double y = Double.parseDouble(position.split(" ")[1]);

        Point pos = new Point(x, y);

        NodeList nListNU = eElement.getElementsByTagName("oki:NespravneUdaje");

        if (nListNU.getLength() > 0) {
            nespravny = true;
            String nazevUdaje = eElement.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
            Date oznacenoDne = sdf.parse(eElement.getElementsByTagName("com:OznacenoDne").item(0).getTextContent());
            String oznacenoInfo = eElement.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();
            nespravnyUdaj = new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
        }

        Date datumVzniku = sdf.parse(eElement.getElementsByTagName("oki:DatumVzniku").item(0).getTextContent());

        Okres okresExpected = new Okres(3100, "Hlavní město Praha", true, 19,
                sdf.parse("2021-01-02T00:00:00"), null, 3650029L,
                2398502L, "CZ0100", new Point(-743100.00, -1043300.00),
                new NespravnyUdaj("KODU", sdf.parse("2021-01-03T09:57:21"),
                        "Okres Hlavní město Praha byl k 1. 1. 2021 zrušen v souvislosti se zrušením vyhlášky č. 564/2002 Sb."),
                sdf.parse("1960-04-11T00:00:00"));

        Okres okresActual = new Okres(kod, nazev, nespravny, kodVusc,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, nutsLau, pos, nespravnyUdaj, datumVzniku);

        assertEquals(okresExpected, okresActual);
    }

    @Test
    @DisplayName("Should parse Orp from XML file")
    void parseOrpFromXML() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date platiDo = null;
        boolean nespravny = false;
        NespravnyUdaj nespravnyUdaj = null;

        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(9).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Orp", node.getNodeName());

        Element eElement = (Element) node;
        Integer kod = Integer.parseInt(eElement.getElementsByTagName("opi:Kod").item(0).getTextContent());
        String nazev = eElement.getElementsByTagName("opi:Nazev").item(0).getTextContent();
        Date platiOd = sdf.parse(eElement.getElementsByTagName("opi:PlatiOd").item(0).getTextContent());
        Integer spravniObecKod = Integer.parseInt(eElement.getElementsByTagName("opi:SpravniObecKod")
                .item(0).getTextContent());
        Integer kodOkresu = Integer.parseInt(eElement.getElementsByTagName("oki:Kod")
                .item(0).getTextContent());
        NodeList nListPlatiDo = eElement.getElementsByTagName("opi:PlatiDo");

        if (nListPlatiDo.getLength() > 0) {
            platiDo = sdf.parse(nListPlatiDo.item(0).getTextContent());
        }

        Long idTransakce = Long.parseLong(eElement.getElementsByTagName("opi:IdTransakce")
                .item(0).getTextContent());
        Long globalniIdNavrhuZmeny = Long.parseLong(eElement.getElementsByTagName("opi:GlobalniIdNavrhuZmeny")
                .item(0).getTextContent());

        String position = eElement.getElementsByTagName("gml:pos").item(0).getTextContent();

        double x = Double.parseDouble(position.split(" ")[0]);
        double y = Double.parseDouble(position.split(" ")[1]);

        Point pos = new Point(x, y);

        NodeList nListNU = eElement.getElementsByTagName("opi:NespravneUdaje");

        if (nListNU.getLength() > 0) {
            nespravny = true;
            String nazevUdaje = eElement.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
            Date oznacenoDne = sdf.parse(eElement.getElementsByTagName("com:OznacenoDne").item(0).getTextContent());
            String oznacenoInfo = eElement.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();
            nespravnyUdaj = new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
        }

        Date datumVzniku = sdf.parse(eElement.getElementsByTagName("opi:DatumVzniku").item(0).getTextContent());

        Orp orpExpected = new Orp(78, "Kladno", false, 532053, 3203,
                sdf.parse("2021-03-18T00:00:00"), null, 3748176L,
                2450941L, new Point(-764439.00, -1033266.00),
                null, sdf.parse("2003-01-01T00:00:00"));

        Orp orpActual = new Orp(kod, nazev, nespravny, spravniObecKod, kodOkresu,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, pos, nespravnyUdaj, datumVzniku);

        assertEquals(orpExpected, orpActual);
    }

    @Test
    @DisplayName("Should parse Pou from XML file")
    void parsePouFromXML() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date platiDo = null;
        boolean nespravny = false;
        NespravnyUdaj nespravnyUdaj = null;

        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(11).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Pou", node.getNodeName());

        Element eElement = (Element) node;
        Integer kod = Integer.parseInt(eElement.getElementsByTagName("pui:Kod").item(0).getTextContent());
        String nazev = eElement.getElementsByTagName("pui:Nazev").item(0).getTextContent();
        Date platiOd = sdf.parse(eElement.getElementsByTagName("pui:PlatiOd").item(0).getTextContent());
        Integer spravniObecKod = Integer.parseInt(eElement.getElementsByTagName("pui:SpravniObecKod").item(0).getTextContent());
        Integer kodOrp = Integer.parseInt(eElement.getElementsByTagName("opi:Kod").item(0).getTextContent());
        NodeList nListPlatiDo = eElement.getElementsByTagName("pui:PlatiDo");

        if (nListPlatiDo.getLength() > 0) {
            platiDo = sdf.parse(nListPlatiDo.item(0).getTextContent());
        }

        Long idTransakce = Long.parseLong(eElement.getElementsByTagName("pui:IdTransakce")
                .item(0).getTextContent());
        Long globalniIdNavrhuZmeny = Long.parseLong(eElement.getElementsByTagName("pui:GlobalniIdNavrhuZmeny")
                .item(0).getTextContent());

        String position = eElement.getElementsByTagName("gml:pos").item(0).getTextContent();

        double x = Double.parseDouble(position.split(" ")[0]);
        double y = Double.parseDouble(position.split(" ")[1]);

        Point pos = new Point(x, y);

        NodeList nListNU = eElement.getElementsByTagName("pui:NespravneUdaje");

        if (nListNU.getLength() > 0) {
            nespravny = true;
            String nazevUdaje = eElement.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
            Date oznacenoDne = sdf.parse(eElement.getElementsByTagName("com:OznacenoDne").item(0).getTextContent());
            String oznacenoInfo = eElement.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();
            nespravnyUdaj = new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
        }

        Date datumVzniku = sdf.parse(eElement.getElementsByTagName("pui:DatumVzniku").item(0).getTextContent());

        Pou pouExpected = new Pou(2119, "Heřmanův Městec", false, 571385, 981,
                sdf.parse("2017-10-24T00:00:00"), null, 2089492L,
                1534663L, new Point(-656419.00, -1069916.00),
                null, sdf.parse("1990-11-24T00:00:00"));

        Pou pouActual = new Pou(kod, nazev, nespravny, spravniObecKod, kodOrp,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, pos, nespravnyUdaj, datumVzniku);

        assertEquals(pouExpected, pouActual);
    }

    @Test
    @DisplayName("Should parse Obec from XML file")
    void parseObecFromXML() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date platiDo = null, datumVzniku = null;
        boolean nespravny = false;
        NespravnyUdaj nespravnyUdaj = null;

        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(13).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Obec", node.getNodeName());

        Element eElement = (Element) node;
        Integer kod = Integer.parseInt(eElement.getElementsByTagName("obi:Kod").item(0).getTextContent());
        String nazev = eElement.getElementsByTagName("obi:Nazev").item(0).getTextContent();
        Date platiOd = sdf.parse(eElement.getElementsByTagName("obi:PlatiOd").item(0).getTextContent());
        NodeList nListPlatiDo = eElement.getElementsByTagName("obi:PlatiDo");

        if (nListPlatiDo.getLength() > 0) {
            platiDo = sdf.parse(nListPlatiDo.item(0).getTextContent());
        }

        Integer cleneniSMRozsahKod = null, cleneniSMTypKod = null;
        Integer kodOkresu = Integer.parseInt(eElement.getElementsByTagName("oki:Kod").item(0).getTextContent());
        Integer kodPou = Integer.parseInt(eElement.getElementsByTagName("pui:Kod").item(0).getTextContent());
        Integer statusKod = Integer.parseInt(eElement.getElementsByTagName("obi:StatusKod").item(0).getTextContent());
        String nutsLau = eElement.getElementsByTagName("obi:NutsLau").item(0).getTextContent();
        MluvnickeCharakteristiky mluvChar = getMluvChar(eElement);
        String vlajkaText = getVlajkaText(eElement, "obi");
        String vlajkaObrazek = getVlajkaObrazek(eElement, "obi");
        String znakText = getZnakText(eElement, "obi");
        String znakObrazek = getZnakObrazek(eElement, "obi");

        NodeList nListCleneniSMRozsahKod = eElement.getElementsByTagName("obi:cleneniSMRozsahKod");

        if (nListCleneniSMRozsahKod.getLength() > 0) {
            cleneniSMRozsahKod = Integer.parseInt(nListCleneniSMRozsahKod.item(0).getTextContent());
        }

        NodeList nListCleneniSMTypKod = eElement.getElementsByTagName("obi:cleneniSMTypKod");

        if (nListCleneniSMTypKod.getLength() > 0) {
            cleneniSMTypKod = Integer.parseInt(nListCleneniSMTypKod.item(0).getTextContent());
        }

        Long idTransakce = Long.parseLong(eElement.getElementsByTagName("obi:IdTransakce")
                .item(0).getTextContent());
        Long globalniIdNavrhuZmeny = Long.parseLong(eElement.getElementsByTagName("obi:GlobalniIdNavrhuZmeny")
                .item(0).getTextContent());

        String position = eElement.getElementsByTagName("gml:pos").item(0).getTextContent();

        double x = Double.parseDouble(position.split(" ")[0]);
        double y = Double.parseDouble(position.split(" ")[1]);

        Point pos = new Point(x, y);

        NodeList nListNU = eElement.getElementsByTagName("obi:NespravneUdaje");

        if (nListNU.getLength() > 0) {
            nespravny = true;
            String nazevUdaje = eElement.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
            Date oznacenoDne = sdf.parse(eElement.getElementsByTagName("com:OznacenoDne").item(0).getTextContent());
            String oznacenoInfo = eElement.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();
            nespravnyUdaj = new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
        }

        NodeList nListDatumVzniku = eElement.getElementsByTagName("obi:DatumVzniku");

        if (nListDatumVzniku.getLength() > 0) {
            datumVzniku = sdf.parse(nListDatumVzniku.item(0).getTextContent());
        }

        Obec obecExpected = new Obec(569089, "Maleč", false, 2, 3601, 1970,
                sdf.parse("2019-01-16T00:00:00"), null, 2718946L,
                1933345L,
                new MluvnickeCharakteristiky("Malče", "Malči", "Maleč", "Malči", "Malčí"),
                "List tvoří dva žluté žerďové klíny s vrcholy v první čtvrtině délky listu, červené pole s bílým mořským psem se žlutou zbrojí a dva svislé pruhy, bílý a modrý, každý široký jednu osminu délky listu. Poměr šířky k délce listu je 2:3.",
                null, "V červeném štítě se stříbrno-modře vlnitě dělenou vlnitou patou, pod dvěma zlatými, ke středu prolomenými zvýšenými klíny stříbrný mořský pes se zlatou zbrojí.", null,
                null, null, "CZ0631569089",
                new Point(-657903.00, -1089346.00), null, null);

        Obec obecActual = new Obec(kod, nazev, nespravny, statusKod, kodOkresu, kodPou,
                platiOd, platiDo, idTransakce,
                globalniIdNavrhuZmeny, mluvChar, vlajkaText, vlajkaObrazek,
                znakText, znakObrazek, cleneniSMTypKod, cleneniSMRozsahKod, nutsLau, pos,
                nespravnyUdaj, datumVzniku);

        assertEquals(obecExpected, obecActual);
    }

    @Test
    @DisplayName("Should parse SpravniObvod from XML file")
    void parseSpravniObvodFromXML() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date platiDo = null, datumVzniku = null;
        boolean nespravny = false;
        NespravnyUdaj nespravnyUdaj = null;

        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(15).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:SpravniObvod", node.getNodeName());

        Element eElement = (Element) node;
        Integer kod = Integer.parseInt(eElement.getElementsByTagName("spi:Kod").item(0).getTextContent());
        String nazev = eElement.getElementsByTagName("spi:Nazev").item(0).getTextContent();
        Date platiOd = sdf.parse(eElement.getElementsByTagName("spi:PlatiOd").item(0).getTextContent());
        NodeList nListPlatiDo = eElement.getElementsByTagName("spi:PlatiDo");

        if (nListPlatiDo.getLength() > 0) {
            platiDo = sdf.parse(nListPlatiDo.item(0).getTextContent());
        }

        Integer spravniMomcKod = Integer.parseInt(eElement.getElementsByTagName("spi:SpravniMomcKod")
                .item(0).getTextContent());
        Integer kodObce = Integer.parseInt(eElement.getElementsByTagName("obi:Kod")
                .item(0).getTextContent());
        Long idTransakce = Long.parseLong(eElement.getElementsByTagName("spi:IdTransakce")
                .item(0).getTextContent());
        Long globalniIdNavrhuZmeny = Long.parseLong(eElement.getElementsByTagName("spi:GlobalniIdNavrhuZmeny")
                .item(0).getTextContent());

        String position = eElement.getElementsByTagName("gml:pos").item(0).getTextContent();

        double x = Double.parseDouble(position.split(" ")[0]);
        double y = Double.parseDouble(position.split(" ")[1]);

        Point pos = new Point(x, y);

        NodeList nListNU = eElement.getElementsByTagName("spi:NespravneUdaje");

        if (nListNU.getLength() > 0) {
            nespravny = true;
            String nazevUdaje = eElement.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
            Date oznacenoDne = sdf.parse(eElement.getElementsByTagName("com:OznacenoDne").item(0).getTextContent());
            String oznacenoInfo = eElement.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();
            nespravnyUdaj = new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
        }

        NodeList nListDatumVzniku = eElement.getElementsByTagName("spi:DatumVzniku");

        if (nListDatumVzniku.getLength() > 0) {
            datumVzniku = sdf.parse(nListDatumVzniku.item(0).getTextContent());
        }

        SpravniObvod spravniObvodExpected = new SpravniObvod(19, "Praha 1", false, 500054,
                554782, sdf.parse("2013-11-13T00:00:00"), null,
                393750L, 452737L,
                new Point(-742600.00, -1043000.00), null, null);

        SpravniObvod spravniObvodActual = new SpravniObvod(kod, nazev, nespravny, spravniMomcKod, kodObce, platiOd, platiDo,
                idTransakce, globalniIdNavrhuZmeny, pos, nespravnyUdaj, datumVzniku);

        assertEquals(spravniObvodExpected, spravniObvodActual);
    }

    @Test
    @DisplayName("Should parse Mop from XML file")
    void parseMopFromXML() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date platiDo = null, datumVzniku = null;
        boolean nespravny = false;
        NespravnyUdaj nespravnyUdaj = null;

        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(17).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Mop", node.getNodeName());

        Element eElement = (Element) node;
        Integer kod = Integer.parseInt(eElement.getElementsByTagName("mpi:Kod").item(0).getTextContent());
        String nazev = eElement.getElementsByTagName("mpi:Nazev").item(0).getTextContent();
        Date platiOd = sdf.parse(eElement.getElementsByTagName("mpi:PlatiOd").item(0).getTextContent());
        NodeList nListPlatiDo = eElement.getElementsByTagName("mpi:PlatiDo");

        if (nListPlatiDo.getLength() > 0) {
            platiDo = sdf.parse(nListPlatiDo.item(0).getTextContent());
        }

        Integer kodObce = Integer.parseInt(eElement.getElementsByTagName("obi:Kod")
                .item(0).getTextContent());
        Long idTransakce = Long.parseLong(eElement.getElementsByTagName("mpi:IdTransakce")
                .item(0).getTextContent());
        Long globalniIdNavrhuZmeny = Long.parseLong(eElement.getElementsByTagName("mpi:GlobalniIdNavrhuZmeny")
                .item(0).getTextContent());

        String position = eElement.getElementsByTagName("gml:pos").item(0).getTextContent();

        double x = Double.parseDouble(position.split(" ")[0]);
        double y = Double.parseDouble(position.split(" ")[1]);

        Point pos = new Point(x, y);

        NodeList nListNU = eElement.getElementsByTagName("mpi:NespravneUdaje");

        if (nListNU.getLength() > 0) {
            nespravny = true;
            String nazevUdaje = eElement.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
            Date oznacenoDne = sdf.parse(eElement.getElementsByTagName("com:OznacenoDne").item(0).getTextContent());
            String oznacenoInfo = eElement.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();
            nespravnyUdaj = new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
        }

        NodeList nListDatumVzniku = eElement.getElementsByTagName("mpi:DatumVzniku");

        if (nListDatumVzniku.getLength() > 0) {
            datumVzniku = sdf.parse(nListDatumVzniku.item(0).getTextContent());
        }

        Mop mopExpected = new Mop(19, "Praha 1", false, 554782,
                sdf.parse("2015-06-05T00:00:00"), null,
                895747L, 731637L,
                new Point(-742600.00, -1043000.00), null, sdf.parse("1960-04-11T00:00:00"));

        Mop mopActual = new Mop(kod, nazev, nespravny, kodObce, platiOd, platiDo,
                idTransakce, globalniIdNavrhuZmeny, pos, nespravnyUdaj, datumVzniku);

        assertEquals(mopExpected, mopActual);
    }

    @Test
    @DisplayName("Should parse Momc from XML file")
    void parseMomcFromXML() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Integer kodMop = null, kodSpravniObvod = null;
        String vlajkaText = null, vlajkaObrazek = null,
                znakText = null, znakObrazek = null;
        Date platiDo = null, datumVzniku = null;
        boolean nespravny = false;
        NespravnyUdaj nespravnyUdaj = null;

        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(19).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Momc", node.getNodeName());

        Element eElement = (Element) node;
        Integer kod = Integer.parseInt(eElement.getElementsByTagName("mci:Kod").item(0).getTextContent());
        String nazev = eElement.getElementsByTagName("mci:Nazev").item(0).getTextContent();
        Date platiOd = sdf.parse(eElement.getElementsByTagName("mci:PlatiOd").item(0).getTextContent());
        NodeList nListPlatiDo = eElement.getElementsByTagName("mci:PlatiDo");

        if (nListPlatiDo.getLength() > 0) {
            platiDo = sdf.parse(nListPlatiDo.item(0).getTextContent());
        }

        NodeList nListKodMop = eElement.getElementsByTagName("mpi:Kod");

        if (nListKodMop.getLength() > 0) {
            kodMop = Integer.parseInt(nListKodMop.item(0).getTextContent());
        }

        Integer kodObce = Integer.parseInt(eElement.getElementsByTagName("obi:Kod")
                .item(0).getTextContent());

        NodeList nListKodSpravniObvod = eElement.getElementsByTagName("spi:Kod");

        if (nListKodSpravniObvod.getLength() > 0) {
            kodSpravniObvod = Integer.parseInt(nListKodSpravniObvod.item(0).getTextContent());
        }

        NodeList nListVlajkaText = eElement.getElementsByTagName("mci:VlajkaText");

        if (nListVlajkaText.getLength() > 0) {
            vlajkaText = nListVlajkaText.item(0).getTextContent();
        }

        NodeList nListVlajkaObrazek = eElement.getElementsByTagName("mci:VlajkaObrazek");

        if (nListVlajkaObrazek.getLength() > 0) {
            vlajkaObrazek = nListVlajkaObrazek.item(0).getTextContent();
        }

        NodeList nListZnakText = eElement.getElementsByTagName("mci:ZnakText");

        if (nListZnakText.getLength() > 0) {
            znakText = nListZnakText.item(0).getTextContent();
        }

        NodeList nListZnakObrazek = eElement.getElementsByTagName("mci:ZnakObrazek");

        if (nListZnakObrazek.getLength() > 0) {
            znakObrazek = nListZnakObrazek.item(0).getTextContent();
        }

        MluvnickeCharakteristiky mluvChar = getMluvChar(eElement);
        Long idTransakce = Long.parseLong(eElement.getElementsByTagName("mci:IdTransakce")
                .item(0).getTextContent());
        Long globalniIdNavrhuZmeny = Long.parseLong(eElement.getElementsByTagName("mci:GlobalniIdNavrhuZmeny")
                .item(0).getTextContent());

        String position = eElement.getElementsByTagName("gml:pos").item(0).getTextContent();

        double x = Double.parseDouble(position.split(" ")[0]);
        double y = Double.parseDouble(position.split(" ")[1]);

        Point pos = new Point(x, y);

        NodeList nListNU = eElement.getElementsByTagName("mci:NespravneUdaje");

        if (nListNU.getLength() > 0) {
            nespravny = true;
            String nazevUdaje = eElement.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
            Date oznacenoDne = sdf.parse(eElement.getElementsByTagName("com:OznacenoDne").item(0).getTextContent());
            String oznacenoInfo = eElement.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();
            nespravnyUdaj = new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
        }

        NodeList nListDatumVzniku = eElement.getElementsByTagName("mci:DatumVzniku");

        if (nListDatumVzniku.getLength() > 0) {
            datumVzniku = sdf.parse(nListDatumVzniku.item(0).getTextContent());
        }

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
                null, new Point(-734200.00, -1036800.00), null,
                sdf.parse("1977-01-01T00:00:00"));

        Momc momcActual = new Momc(kod, nazev, nespravny, kodMop, kodObce, kodSpravniObvod, platiOd, platiDo,
                idTransakce, globalniIdNavrhuZmeny, vlajkaText, vlajkaObrazek, mluvChar, znakText, znakObrazek,
                pos, nespravnyUdaj, datumVzniku);

        assertEquals(momcExpected, momcActual);
    }

    @Test
    @DisplayName("Should parse CastObce from XML file")
    void parseCastObceFromXML() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date platiDo = null, datumVzniku = null;
        boolean nespravny = false;
        NespravnyUdaj nespravnyUdaj = null;

        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(21).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:CastObce", node.getNodeName());

        Element eElement = (Element) node;
        Integer kod = Integer.parseInt(eElement.getElementsByTagName("coi:Kod").item(0).getTextContent());
        String nazev = eElement.getElementsByTagName("coi:Nazev").item(0).getTextContent();
        Date platiOd = sdf.parse(eElement.getElementsByTagName("coi:PlatiOd").item(0).getTextContent());
        NodeList nListPlatiDo = eElement.getElementsByTagName("coi:PlatiDo");

        if (nListPlatiDo.getLength() > 0) {
            platiDo = sdf.parse(nListPlatiDo.item(0).getTextContent());
        }

        Integer kodObce = Integer.parseInt(eElement.getElementsByTagName("obi:Kod")
                .item(0).getTextContent());
        MluvnickeCharakteristiky mluvChar = getMluvChar(eElement);
        Long idTransakce = Long.parseLong(eElement.getElementsByTagName("coi:IdTransakce")
                .item(0).getTextContent());
        Long globalniIdNavrhuZmeny = Long.parseLong(eElement.getElementsByTagName("coi:GlobalniIdNavrhuZmeny")
                .item(0).getTextContent());

        String position = eElement.getElementsByTagName("gml:pos").item(0).getTextContent();

        double x = Double.parseDouble(position.split(" ")[0]);
        double y = Double.parseDouble(position.split(" ")[1]);

        Point pos = new Point(x, y);

        NodeList nListNU = eElement.getElementsByTagName("coi:NespravneUdaje");

        if (nListNU.getLength() > 0) {
            nespravny = true;
            String nazevUdaje = eElement.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
            Date oznacenoDne = sdf.parse(eElement.getElementsByTagName("com:OznacenoDne").item(0).getTextContent());
            String oznacenoInfo = eElement.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();
            nespravnyUdaj = new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
        }

        NodeList nListDatumVzniku = eElement.getElementsByTagName("coi:DatumVzniku");

        if (nListDatumVzniku.getLength() > 0) {
            datumVzniku = sdf.parse(nListDatumVzniku.item(0).getTextContent());
        }

        CastObce castObceExpected = new CastObce(32263, "Dobrš", false, 551023,
                sdf.parse("2013-11-11T00:00:00"), null,
                391375L, 450266L,
                new MluvnickeCharakteristiky("Dobrše", "Dobrši", "Dobrš", "Dobrši", "Dobrší"),
                new Point(-805130.00, -1138781.00), null, null);

        CastObce castObceActual = new CastObce(kod, nazev, nespravny, kodObce, platiOd, platiDo,
                idTransakce, globalniIdNavrhuZmeny, mluvChar, pos, nespravnyUdaj, datumVzniku);

        assertEquals(castObceExpected, castObceActual);
    }

    @Test
    @DisplayName("Should parse KatastralniUzemi from XML file")
    void parseKatastralniUzemiFromXML() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date platiDo = null, datumVzniku = null;
        boolean nespravny = false;
        NespravnyUdaj nespravnyUdaj = null;

        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(23).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:KatastralniUzemi", node.getNodeName());

        Element eElement = (Element) node;
        Integer kod = Integer.parseInt(eElement.getElementsByTagName("kui:Kod").item(0).getTextContent());
        String nazev = eElement.getElementsByTagName("kui:Nazev").item(0).getTextContent();
        Date platiOd = sdf.parse(eElement.getElementsByTagName("kui:PlatiOd").item(0).getTextContent());
        NodeList nListPlatiDo = eElement.getElementsByTagName("kui:PlatiDo");

        if (nListPlatiDo.getLength() > 0) {
            platiDo = sdf.parse(nListPlatiDo.item(0).getTextContent());
        }

        Integer kodObce = Integer.parseInt(eElement.getElementsByTagName("obi:Kod")
                .item(0).getTextContent());
        Long rizeniId = Long.parseLong(eElement.getElementsByTagName("kui:RizeniId")
                .item(0).getTextContent());
        Boolean existujeDigitalniMapa = Boolean.parseBoolean(eElement
                .getElementsByTagName("kui:ExistujeDigitalniMapa").item(0).getTextContent());
        MluvnickeCharakteristiky mluvChar = getMluvChar(eElement);
        Long idTransakce = Long.parseLong(eElement.getElementsByTagName("kui:IdTransakce")
                .item(0).getTextContent());
        Long globalniIdNavrhuZmeny = Long.parseLong(eElement.getElementsByTagName("kui:GlobalniIdNavrhuZmeny")
                .item(0).getTextContent());

        String position = eElement.getElementsByTagName("gml:pos").item(0).getTextContent();

        double x = Double.parseDouble(position.split(" ")[0]);
        double y = Double.parseDouble(position.split(" ")[1]);

        Point pos = new Point(x, y);

        NodeList nListNU = eElement.getElementsByTagName("kui:NespravneUdaje");

        if (nListNU.getLength() > 0) {
            nespravny = true;
            String nazevUdaje = eElement.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
            Date oznacenoDne = sdf.parse(eElement.getElementsByTagName("com:OznacenoDne").item(0).getTextContent());
            String oznacenoInfo = eElement.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();
            nespravnyUdaj = new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
        }

        NodeList nListDatumVzniku = eElement.getElementsByTagName("kui:DatumVzniku");

        if (nListDatumVzniku.getLength() > 0) {
            datumVzniku = sdf.parse(nListDatumVzniku.item(0).getTextContent());
        }

        KatastralniUzemi katastralniUzemiExpected = new KatastralniUzemi(668761, "Pašinovice", false,
                true, 535877, sdf.parse("2019-06-13T00:00:00"), null, 2902615L,
                0L, 64903676010L,
                new MluvnickeCharakteristiky("Pašinovic", "Pašinovicím", "Pašinovice", "Pašinovicích", "Pašinovicemi"),
                new Point(-753602.46, -1177927.54), null, sdf.parse("1923-01-01T00:00:00"));

        KatastralniUzemi katastralniUzemiActual = new KatastralniUzemi(kod, nazev, nespravny,
                existujeDigitalniMapa, kodObce, platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny,
                rizeniId, mluvChar, pos, nespravnyUdaj, datumVzniku);

        assertEquals(katastralniUzemiExpected, katastralniUzemiActual);
    }

    @Test
    @DisplayName("Should parse Zsj from XML file")
    void parseZsjFromXML() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date platiDo = null, datumVzniku = null;
        boolean nespravny = false;
        MluvnickeCharakteristiky mluvChar = null;
        NespravnyUdaj nespravnyUdaj = null;

        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(25).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Zsj", node.getNodeName());

        Element eElement = (Element) node;
        Integer kod = Integer.parseInt(eElement.getElementsByTagName("zji:Kod").item(0).getTextContent());
        String nazev = eElement.getElementsByTagName("zji:Nazev").item(0).getTextContent();
        Date platiOd = sdf.parse(eElement.getElementsByTagName("zji:PlatiOd").item(0).getTextContent());
        NodeList nListPlatiDo = eElement.getElementsByTagName("zji:PlatiDo");

        if (nListPlatiDo.getLength() > 0) {
            platiDo = sdf.parse(nListPlatiDo.item(0).getTextContent());
        }

        Integer kodKatastralniUzemi = Integer.parseInt(eElement.getElementsByTagName("kui:Kod")
                .item(0).getTextContent());
        Long vymera = Long.parseLong(eElement.getElementsByTagName("zji:Vymera")
                .item(0).getTextContent());
        Integer charakterZsjKod = Integer.parseInt(eElement.getElementsByTagName("zji:CharakterZsjKod")
                .item(0).getTextContent());

        NodeList nListMluvChar = eElement.getElementsByTagName("zji:MluvnickeCharakteristiky");

        if (nListMluvChar.getLength() > 0) {
            mluvChar = getMluvChar(eElement);
        }
        Long idTransakce = Long.parseLong(eElement.getElementsByTagName("zji:IdTransakce")
                .item(0).getTextContent());
        Long globalniIdNavrhuZmeny = Long.parseLong(eElement.getElementsByTagName("zji:GlobalniIdNavrhuZmeny")
                .item(0).getTextContent());

        String position = eElement.getElementsByTagName("gml:pos").item(0).getTextContent();

        double x = Double.parseDouble(position.split(" ")[0]);
        double y = Double.parseDouble(position.split(" ")[1]);

        Point pos = new Point(x, y);

        NodeList nListNU = eElement.getElementsByTagName("zji:NespravneUdaje");

        if (nListNU.getLength() > 0) {
            nespravny = true;
            String nazevUdaje = eElement.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
            Date oznacenoDne = sdf.parse(eElement.getElementsByTagName("com:OznacenoDne").item(0).getTextContent());
            String oznacenoInfo = eElement.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();
            nespravnyUdaj = new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
        }

        NodeList nListDatumVzniku = eElement.getElementsByTagName("zji:DatumVzniku");

        if (nListDatumVzniku.getLength() > 0) {
            datumVzniku = sdf.parse(nListDatumVzniku.item(0).getTextContent());
        }

        Zsj zsjExpected = new Zsj(67067, "Suhrovice", false, 667064,
                sdf.parse("2015-06-05T00:00:00"), null, 895630L, 731555L, null,
                1753652L, 11, new Point(-691855.00, -1004625.00),
                null, sdf.parse("1970-12-01T00:00:00"));


        Zsj zsjActual = new Zsj(kod, nazev, nespravny, kodKatastralniUzemi, platiOd, platiDo, idTransakce,
                globalniIdNavrhuZmeny, mluvChar, vymera, charakterZsjKod, pos, nespravnyUdaj, datumVzniku);


        assertEquals(zsjExpected, zsjActual);
    }

    @Test
    @DisplayName("Should parse Ulice from XML file")
    void parseUliceFromXML() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date platiDo = null, datumVzniku = null;
        boolean nespravny = false;
        NespravnyUdaj nespravnyUdaj = null;

        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(27).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Ulice", node.getNodeName());

        Element eElement = (Element) node;
        Integer kod = Integer.parseInt(eElement.getElementsByTagName("uli:Kod").item(0).getTextContent());
        String nazev = eElement.getElementsByTagName("uli:Nazev").item(0).getTextContent();
        Date platiOd = sdf.parse(eElement.getElementsByTagName("uli:PlatiOd").item(0).getTextContent());
        NodeList nListPlatiDo = eElement.getElementsByTagName("uli:PlatiDo");

        if (nListPlatiDo.getLength() > 0) {
            platiDo = sdf.parse(nListPlatiDo.item(0).getTextContent());
        }

        Integer kodObce = Integer.parseInt(eElement.getElementsByTagName("obi:Kod")
                .item(0).getTextContent());
        Long idTransakce = Long.parseLong(eElement.getElementsByTagName("uli:IdTransakce")
                .item(0).getTextContent());
        Long globalniIdNavrhuZmeny = Long.parseLong(eElement.getElementsByTagName("uli:GlobalniIdNavrhuZmeny")
                .item(0).getTextContent());

        NodeList nListNU = eElement.getElementsByTagName("uli:NespravneUdaje");

        if (nListNU.getLength() > 0) {
            nespravny = true;
            String nazevUdaje = eElement.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
            Date oznacenoDne = sdf.parse(eElement.getElementsByTagName("com:OznacenoDne").item(0).getTextContent());
            String oznacenoInfo = eElement.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();
            nespravnyUdaj = new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
        }

        Ulice uliceExpected = new Ulice(644196, "4. května", false, 500011,
                sdf.parse("2018-01-22T00:00:00"), null, 2277057L, 1672622L, null);

        Ulice uliceActual = new Ulice(kod, nazev, nespravny, kodObce,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, nespravnyUdaj);

        assertEquals(uliceExpected, uliceActual);
    }

    @Test
    @DisplayName("Should parse Parcela from XML file")
    void parseParcelaFromXML() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date platiDo = null, datumVzniku = null;
        boolean nespravny = false;
        ZpusobOchranyPozemku zpusobOchranyPozemku = null;
        List<BonitovanyDil> bonitovaneDily = null;
        NespravnyUdaj nespravnyUdaj = null;

        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(29).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Parcela", node.getNodeName());

        Element eElement = (Element) node;
        Long id = Long.parseLong(eElement.getElementsByTagName("pai:Id").item(0).getTextContent());
        Date platiOd = sdf.parse(eElement.getElementsByTagName("pai:PlatiOd").item(0).getTextContent());
        NodeList nListPlatiDo = eElement.getElementsByTagName("pai:PlatiDo");

        if (nListPlatiDo.getLength() > 0) {
            platiDo = sdf.parse(nListPlatiDo.item(0).getTextContent());
        }
        Long idTransakce = Long.parseLong(eElement.getElementsByTagName("pai:IdTransakce")
                .item(0).getTextContent());

        NodeList nListNU = eElement.getElementsByTagName("pai:NespravneUdaje");

        if (nListNU.getLength() > 0) {
            nespravny = true;
            String nazevUdaje = eElement.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
            Date oznacenoDne = sdf.parse(eElement.getElementsByTagName("com:OznacenoDne").item(0).getTextContent());
            String oznacenoInfo = eElement.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();
            nespravnyUdaj = new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
        }

        String position = eElement.getElementsByTagName("gml:pos").item(0).getTextContent();

        double x = Double.parseDouble(position.split(" ")[0]);
        double y = Double.parseDouble(position.split(" ")[1]);

        Point pos = new Point(x, y);

        Integer kmenoveCislo = Integer.parseInt(eElement.getElementsByTagName("pai:KmenoveCislo")
                .item(0).getTextContent());

        Integer pododdeleniCisla = Integer.parseInt(eElement.getElementsByTagName("pai:PododdeleniCisla")
                .item(0).getTextContent());

        Long vymeraParcely = Long.parseLong(eElement.getElementsByTagName("pai:VymeraParcely")
                .item(0).getTextContent());

        Integer druhCislovaniKod = Integer.parseInt(eElement.getElementsByTagName("pai:DruhCislovaniKod")
                .item(0).getTextContent());

        Integer druhPozemkuKod = Integer.parseInt(eElement.getElementsByTagName("pai:DruhPozemkuKod")
                .item(0).getTextContent());

        Integer kodKatastralniUzemi = Integer.parseInt(eElement.getElementsByTagName("kui:Kod")
                .item(0).getTextContent());

        Long rizeniId = Long.parseLong(eElement.getElementsByTagName("pai:RizeniId")
                .item(0).getTextContent());

        NodeList nListBonitovaneDily = eElement.getElementsByTagName("pai:BonitovaneDily");

        if (nListBonitovaneDily.getLength() > 0) {
            bonitovaneDily = new ArrayList<>();

            for (int i = 0; i < nListBonitovaneDily.getLength(); i++) {
                Element element = (Element) nListBonitovaneDily.item(i);

                Long vymeraBD = Long.parseLong(element.getElementsByTagName("com:Vymera")
                        .item(0).getTextContent());
                Integer bonitovaJednotkaKodBD = Integer.parseInt(element.getElementsByTagName("com:BonitovanaJednotkaKod")
                        .item(0).getTextContent());
                Long idTransakceBD = Long.parseLong(element.getElementsByTagName("com:IdTranskace")
                        .item(0).getTextContent());
                Long rizeniIdBD = Long.parseLong(element.getElementsByTagName("com:RizeniId")
                        .item(0).getTextContent());

                BonitovanyDil bonitovanyDil = new BonitovanyDil(vymeraBD, bonitovaJednotkaKodBD, idTransakceBD,
                        rizeniIdBD);

                bonitovaneDily.add(bonitovanyDil);
            }
        }

        NodeList nListZpOchranyPozemku = eElement.getElementsByTagName("pai:ZpusobyOchranyPozemku");

        if (nListZpOchranyPozemku.getLength() > 0) {
            Integer kodZOP = Integer.parseInt(eElement.getElementsByTagName("com:Kod")
                    .item(0).getTextContent());
            Integer typOchranyKodZOP = Integer.parseInt(eElement.getElementsByTagName("com:TypOchranyKod")
                    .item(0).getTextContent());
            Long idTransakceZOP = Long.parseLong(eElement.getElementsByTagName("com:IdTransakce")
                    .item(0).getTextContent());
            Long rizeniIdZOP = Long.parseLong(eElement.getElementsByTagName("com:RizeniId")
                    .item(0).getTextContent());

            zpusobOchranyPozemku = new ZpusobOchranyPozemku(kodZOP, typOchranyKodZOP,
                    idTransakceZOP, rizeniIdZOP);
        }

        Parcela parcelaExpected = new Parcela(79432960010L, false, 457, 22, 200L,
                2, 13, 795909, sdf.parse("2020-11-13T00:00:00"),
                null, 3587101L, 78837743010L, null, null,
                new Point(-516112.58, -1166833.49), null);

        Parcela parcelaActual = new Parcela(id, nespravny, kmenoveCislo, pododdeleniCisla, vymeraParcely,
                druhCislovaniKod, druhPozemkuKod, kodKatastralniUzemi, platiOd, platiDo, idTransakce,
                rizeniId, bonitovaneDily, zpusobOchranyPozemku, pos, nespravnyUdaj);

        assertEquals(parcelaExpected, parcelaActual);
    }

    @Test
    @DisplayName("Should parse StavebniObjekt from XML file")
    void parseStavebniObjektFromXML() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date platiDo = null;
        boolean nespravny = false;
        NespravnyUdaj nespravnyUdaj = null;

        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(31).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:StavebniObjekt", node.getNodeName());

        Element eElement = (Element) node;
        Integer kod = Integer.parseInt(eElement.getElementsByTagName("soi:Kod").item(0).getTextContent());
        Date platiOd = sdf.parse(eElement.getElementsByTagName("soi:PlatiOd").item(0).getTextContent());
        NodeList nListPlatiDo = eElement.getElementsByTagName("soi:PlatiDo");

        if (nListPlatiDo.getLength() > 0) {
            platiDo = sdf.parse(nListPlatiDo.item(0).getTextContent());
        }

        String position = eElement.getElementsByTagName("gml:pos").item(0).getTextContent();

        double x = Double.parseDouble(position.split(" ")[0]);
        double y = Double.parseDouble(position.split(" ")[1]);

        Point pos = new Point(x, y);

        Long idTransakce = Long.parseLong(eElement.getElementsByTagName("soi:IdTransakce")
                .item(0).getTextContent());
        Long globalniIdNavrhuZmeny = Long.parseLong(eElement.getElementsByTagName("soi:GlobalniIdNavrhuZmeny")
                .item(0).getTextContent());

        NodeList nListNU = eElement.getElementsByTagName("soi:NespravneUdaje");

        if (nListNU.getLength() > 0) {
            nespravny = true;
            String nazevUdaje = eElement.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
            Date oznacenoDne = sdf.parse(eElement.getElementsByTagName("com:OznacenoDne").item(0).getTextContent());
            String oznacenoInfo = eElement.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();
            nespravnyUdaj = new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
        }

        Date dokonceni = null;
        Integer podlahovaPlocha = null, obestavenyProstor = null, zastavenaPlocha = null;
        List<Integer> cislaDomovni = null,
                zpusobyOchranyKod = null;
        List<DetailniTEA> detailniTEAList = null;

        NodeList nListCislaDomovni = eElement.getElementsByTagName("com:CisloDomovni");

        if (nListCislaDomovni.getLength() > 0) {
            cislaDomovni = new ArrayList<>();

            for (int i = 0; i < nListCislaDomovni.getLength(); i++) {
                cislaDomovni.add(Integer.parseInt(nListCislaDomovni.item(i).getTextContent()));
            }
        }

        Long identifikacniParcelaId = Long.parseLong(eElement.getElementsByTagName("pai:Id")
                .item(0).getTextContent());

        Integer typStavebnihoObjektuKod = Integer.parseInt(eElement.getElementsByTagName("soi:TypStavebnihoObjektuKod")
                .item(0).getTextContent());

        Integer zpusobVyuzitiKod = Integer.parseInt(eElement.getElementsByTagName("soi:ZpusobVyuzitiKod")
                .item(0).getTextContent());

        Integer castObceKod = Integer.parseInt(eElement.getElementsByTagName("coi:Kod")
                .item(0).getTextContent());

        Long isknBudovaId = Long.parseLong(eElement.getElementsByTagName("soi:IsknBudovaId")
                .item(0).getTextContent());

        NodeList nListDokonceni = eElement.getElementsByTagName("soi:Dokonceni");

        if (nListDokonceni.getLength() > 0) {
            dokonceni = sdf.parse(nListDokonceni.item(0).getTextContent());
        }

        Integer druhKonstrukceKod = Integer.parseInt(eElement.getElementsByTagName("soi:DruhKonstrukceKod")
                .item(0).getTextContent());

        NodeList nListObestavenyProstor = eElement.getElementsByTagName("soi:ObestavenyProstor");

        if (nListDokonceni.getLength() > 0) {
            obestavenyProstor = Integer.parseInt(nListObestavenyProstor.item(0).getTextContent());
        }

        Integer pocetBytu = Integer.parseInt(eElement.getElementsByTagName("soi:PocetBytu")
                .item(0).getTextContent());

        Integer pocetPodlazi = Integer.parseInt(eElement.getElementsByTagName("soi:PocetPodlazi")
                .item(0).getTextContent());

        NodeList nListPodlahovaPlocha = eElement.getElementsByTagName("soi:PodlahovaPlocha");

        if (nListPodlahovaPlocha.getLength() > 0) {
            podlahovaPlocha = Integer.parseInt(nListPodlahovaPlocha.item(0).getTextContent());
        }

        Integer pripojeniKanalizaceKod = Integer.parseInt(eElement.getElementsByTagName("soi:PripojeniKanalizaceKod")
                .item(0).getTextContent());

        Integer pripojeniPlynKod = Integer.parseInt(eElement.getElementsByTagName("soi:PripojeniPlynKod")
                .item(0).getTextContent());

        Integer pripojeniVodovodKod = Integer.parseInt(eElement.getElementsByTagName("soi:PripojeniVodovodKod")
                .item(0).getTextContent());

        Integer vybaveniVytahemKod = Integer.parseInt(eElement.getElementsByTagName("soi:VybaveniVytahemKod")
                .item(0).getTextContent());

        NodeList nListZastavenaPlocha = eElement.getElementsByTagName("soi:ZastavenaPlocha");

        if (nListZastavenaPlocha.getLength() > 0) {
            zastavenaPlocha = Integer.parseInt(nListZastavenaPlocha.item(0).getTextContent());
        }

        Integer zpusobVytapeniKod = Integer.parseInt(eElement.getElementsByTagName("soi:ZpusobVytapeniKod")
                .item(0).getTextContent());

        NodeList nListZpusobyOchranyKod = eElement.getElementsByTagName("soi:ZpusobyOchranyKod");

        if (nListZpusobyOchranyKod.getLength() > 0) {
            zpusobyOchranyKod = new ArrayList<>();

            for (int i = 0; i < nListZpusobyOchranyKod.getLength(); i++) {
                zpusobyOchranyKod.add(Integer.parseInt(eElement.getElementsByTagName("com:ZpusobOchranyKod")
                        .item(i).getTextContent()));
            }
        }

        NodeList nListDetailniTea = eElement.getElementsByTagName("soi:DetailniTEA");

        if (nListDetailniTea.getLength() > 0) {
            NodeList nodeListDTChildren = nListDetailniTea.item(0).getChildNodes();
            detailniTEAList = new ArrayList<>();

            for (int i = 0; i < nodeListDTChildren.getLength(); i++) {
                if (nodeListDTChildren.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    boolean nespravnyDT = false;
                    Element element = (Element) nodeListDTChildren.item(i);

                    Integer kodDT = Integer.parseInt(element.getElementsByTagName("soi:Kod")
                            .item(0).getTextContent());
                    Date platiOdDT = sdf.parse(element.getElementsByTagName("soi:PlatiOd")
                            .item(0).getTextContent());

                    NodeList nListNespravnyDT = element.getElementsByTagName("soi:Nespravny");

                    if (nListNespravnyDT.getLength() > 0) {
                        nespravnyDT = true;
                    }

                    Long globalniIdNavrhuZmenyDT = Long.parseLong(element.
                            getElementsByTagName("soi:GlobalniIdNavrhuZmeny").item(0).getTextContent());

                    Integer druhKonstrukceKodDT = Integer.parseInt(element.
                            getElementsByTagName("soi:DruhKonstrukceKod").item(0).getTextContent());

                    Integer pocetBytuDT = Integer.parseInt(element.
                            getElementsByTagName("soi:PocetBytu").item(0).getTextContent());

                    Integer pocetPodlaziDT = Integer.parseInt(element.getElementsByTagName("soi:PocetPodlazi")
                            .item(0).getTextContent());

                    Integer pripojeniKanalizaceKodDT = Integer.parseInt(element.getElementsByTagName("soi:PripojeniKanalizaceKod")
                            .item(0).getTextContent());

                    Integer pripojeniPlynKodDT = Integer.parseInt(element.getElementsByTagName("soi:PripojeniPlynKod")
                            .item(0).getTextContent());

                    Integer pripojeniVodovodKodDT = Integer.parseInt(element.getElementsByTagName("soi:PripojeniVodovodKod")
                            .item(0).getTextContent());

                    Integer zpusobVytapeniKodDT = Integer.parseInt(element.getElementsByTagName("soi:ZpusobVytapeniKod")
                            .item(0).getTextContent());

                    Integer adresniMistoKodDT = Integer.parseInt(element.getElementsByTagName("base:Kod")
                            .item(0).getTextContent());

                    DetailniTEA detailniTEA = new DetailniTEA(kodDT, platiOdDT, nespravnyDT, globalniIdNavrhuZmenyDT,
                            druhKonstrukceKodDT, pocetBytuDT, pocetPodlaziDT, pripojeniKanalizaceKodDT, pripojeniPlynKodDT,
                            pripojeniVodovodKodDT, zpusobVytapeniKodDT, adresniMistoKodDT);

                    detailniTEAList.add(detailniTEA);
                }
            }
        }

        List<Integer> cislaDomovniExpected = new ArrayList<>() {{
            add(326);
            add(327);
        }};

        List<DetailniTEA> detailniTeaListExpected = new ArrayList<>() {{
            add(new DetailniTEA(71193, sdf.parse("2019-03-07T00:00:00"), false, 1964039L,
                    1, 4,3,1,1,1, 1,4195248));
            add(new DetailniTEA(33767, sdf.parse("2019-03-07T00:00:00"), false, 1964039L,
                    10, 4,3,1,3,1, 4,4195230));
        }};

        StavebniObjekt stavebniObjektExpected = new StavebniObjekt(4165586, false, cislaDomovniExpected, 3563823705L,
                1, 6, 195901, sdf.parse("2019-03-07T00:00:00"), null, 2780214L,
                1964039L, 691202705L, sdf.parse("2019-03-06T00:00:00"), 10, 3850,
                8, 3, 1050, 1, 1, 1,
                2, 422, 1, null, detailniTeaListExpected,
                new Point(-514688.19, -1166820.85), null);

        StavebniObjekt stavebniObjektActual = new StavebniObjekt(kod, nespravny, cislaDomovni, identifikacniParcelaId,
                typStavebnihoObjektuKod, zpusobVyuzitiKod, castObceKod, platiOd, platiDo, idTransakce,
                globalniIdNavrhuZmeny, isknBudovaId, dokonceni, druhKonstrukceKod, obestavenyProstor, pocetBytu,
                pocetPodlazi, podlahovaPlocha, pripojeniKanalizaceKod, pripojeniPlynKod, pripojeniVodovodKod,
                vybaveniVytahemKod, zastavenaPlocha, zpusobVytapeniKod, zpusobyOchranyKod, detailniTEAList,
                pos, nespravnyUdaj);

        assertEquals(stavebniObjektExpected, stavebniObjektActual);
    }

    @Test
    @DisplayName("Should parse AdresniMisto from XML file")
    void parseAdresniMistoFromXML() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date platiDo = null;
        boolean nespravny = false;
        NespravnyUdaj nespravnyUdaj = null;

        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(33).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:AdresniMisto", node.getNodeName());

        Element eElement = (Element) node;
        Integer kod = Integer.parseInt(eElement.getElementsByTagName("ami:Kod").item(0).getTextContent());
        Date platiOd = sdf.parse(eElement.getElementsByTagName("ami:PlatiOd").item(0).getTextContent());
        NodeList nListPlatiDo = eElement.getElementsByTagName("ami:PlatiDo");

        if (nListPlatiDo.getLength() > 0) {
            platiDo = sdf.parse(nListPlatiDo.item(0).getTextContent());
        }

        Long idTransakce = Long.parseLong(eElement.getElementsByTagName("ami:IdTransakce")
                .item(0).getTextContent());
        Long globalniIdNavrhuZmeny = Long.parseLong(eElement.getElementsByTagName("ami:GlobalniIdNavrhuZmeny")
                .item(0).getTextContent());

        NodeList nListNU = eElement.getElementsByTagName("ami:NespravneUdaje");

        if (nListNU.getLength() > 0) {
            nespravny = true;
            String nazevUdaje = eElement.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
            Date oznacenoDne = sdf.parse(eElement.getElementsByTagName("com:OznacenoDne").item(0).getTextContent());
            String oznacenoInfo = eElement.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();
            nespravnyUdaj = new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
        }

        String position = eElement.getElementsByTagName("gml:pos").item(0).getTextContent();

        double x = Double.parseDouble(position.split(" ")[0]);
        double y = Double.parseDouble(position.split(" ")[1]);

        Point pos = new Point(x, y);

        String cisloOrientacni = null, cisloOrientacniPismeno = null;
        Integer uliceKod = null;
        String cisloDomovni = eElement.getElementsByTagName("ami:CisloDomovni")
                .item(0).getTextContent();

        NodeList nListCisloOrientacni = eElement.getElementsByTagName("ami:CisloOrientacni");

        if (nListCisloOrientacni.getLength() > 0) {
            cisloOrientacni = nListCisloOrientacni.item(0).getTextContent();
        }

        NodeList nListCisloOrientacniPismeno = eElement.getElementsByTagName("ami:CisloOrientacniPismeno");

        if (nListCisloOrientacniPismeno.getLength() > 0) {
            cisloOrientacniPismeno = nListCisloOrientacniPismeno.item(0).getTextContent();
        }

        String psc = eElement.getElementsByTagName("ami:Psc").item(0).getTextContent();
        Integer stavebniObjektKod = Integer.parseInt(eElement.getElementsByTagName("soi:Kod")
                .item(0).getTextContent());

        NodeList nListUliceKod = eElement.getElementsByTagName("uli:Kod");

        if (nListUliceKod.getLength() > 0) {
            uliceKod = Integer.parseInt(nListUliceKod.item(0).getTextContent());
        }

        Integer voKod = Integer.parseInt(eElement.getElementsByTagName("ami:VOKod")
                .item(0).getTextContent());

        AdresniMisto adresniMistoExpected = new AdresniMisto(72850124, false, "668", null,
                null, "76311", 78171482, 647268, 42877,
                sdf.parse("2014-06-26T00:00:00"), null,
                630728L, 594036L, new Point(-516005.93, -1168276.78), null);

        AdresniMisto adresniMistoActual = new AdresniMisto(kod, nespravny, cisloDomovni, cisloOrientacni,
                cisloOrientacniPismeno, psc, stavebniObjektKod, uliceKod, voKod, platiOd, platiDo,
                idTransakce, globalniIdNavrhuZmeny, pos, nespravnyUdaj);

        assertEquals(adresniMistoExpected, adresniMistoActual);
    }
}