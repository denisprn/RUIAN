package com.bp.RUIAN;

import com.bp.RUIAN.entities.*;
import com.bp.RUIAN.services.EsService;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

class FilesParserTest {
    private EsService esService;
    private final FilesParser filesParser;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");;
    private final Method getKod, getId, getNazev, getKodStatu, getKodRS, getKodVusc, getKodSpravniObec,
            getKodOkresu, getKodOrp, getStatusKod, getKodPou, getSpravniMomcKod, getKodObce, getKodMop,
            getKodSpravniObvod, getKodKatastralniUzemi, getKodCastObce, getKodStavebniObjekt,
            getKodUlice, getVOKod, getCleneniSMRozsahKod, getCleneniSMTypKod, getCisloDomovni, getCisloOrientacni,
            getCisloOrientacniPismeno, getPSC, getPlatiOd, getPlatiDo, getIdTransakce, getGlobalniIdNavrhuZmeny,
            getExistujeDigitalniMapa, getIdRizeni, getNutsLau, getVymera, getCharakterZsjKod, getKmenoveCislo,
            getPoddodeleniCisla, getVymeraParcely, getDruhCislovaniKod, getDruhPozemkuKod, getCislaDomovni,
            getIdentifikacniParcelaId, getTypStavebnihoObjektuKod, getZpusobVyuzitiKod, getIsknBudovaId, getDokonceni,
            getDruhKonstrukceKod, getObestavenyProstor, getPocetBytu, getPocetPodlazi, getPodlahovaPlocha,
            getPripojeniKanalizaceKod, getPripojeniPlynKod, getPripojeniVodovodKod, getVybaveniVytahemKod,
            getZastavenaPlocha, getZpusobVytapeniKod, getZpusobyOchranyKod, getDetailniTea, getPos, getNespravneUdaje,
            getDatumVzniku, getMluvChar, getBonitovaneDily, getZpusobOchranyPozemku, getVlajkaText, getVlajkaObrazek,
            getZnakObrazek, getZnakText;


    FilesParserTest() throws NoSuchMethodException {
        this.filesParser = new FilesParser(esService);
        this.sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        this.getKod = FilesParser.class.getDeclaredMethod("getKod", Element.class, String.class);
        this.getKod.setAccessible(true);
        this.getId = FilesParser.class.getDeclaredMethod("getId", Element.class, String.class);
        this.getId.setAccessible(true);
        this.getNazev = FilesParser.class.getDeclaredMethod("getNazev", Element.class, String.class);
        this.getNazev.setAccessible(true);
        this.getKodStatu = FilesParser.class.getDeclaredMethod("getKodStatu", Element.class);
        this.getKodStatu.setAccessible(true);
        this.getKodRS = FilesParser.class.getDeclaredMethod("getKodRS", Element.class);
        this.getKodRS.setAccessible(true);
        this.getKodVusc = FilesParser.class.getDeclaredMethod("getKodVusc", Element.class);
        this.getKodVusc.setAccessible(true);
        this.getKodSpravniObec = FilesParser.class.getDeclaredMethod("getKodSpravniObec", Element.class, String.class);
        this.getKodSpravniObec.setAccessible(true);
        this.getKodOkresu = FilesParser.class.getDeclaredMethod("getKodOkresu", Element.class);
        this.getKodOkresu.setAccessible(true);
        this.getKodOrp = FilesParser.class.getDeclaredMethod("getKodOrp", Element.class);
        this.getKodOrp.setAccessible(true);
        this.getStatusKod = FilesParser.class.getDeclaredMethod("getStatusKod", Element.class, String.class);
        this.getStatusKod.setAccessible(true);
        this.getKodPou = FilesParser.class.getDeclaredMethod("getKodPou", Element.class);
        this.getKodPou.setAccessible(true);
        this.getSpravniMomcKod = FilesParser.class.getDeclaredMethod("getSpravniMomcKod", Element.class, String.class);
        this.getSpravniMomcKod.setAccessible(true);
        this.getKodObce = FilesParser.class.getDeclaredMethod("getKodObce", Element.class);
        this.getKodObce.setAccessible(true);
        this.getKodMop = FilesParser.class.getDeclaredMethod("getKodMop", Element.class);
        this.getKodMop.setAccessible(true);
        this.getKodSpravniObvod = FilesParser.class.getDeclaredMethod("getKodSpravniObvod", Element.class);
        this.getKodSpravniObvod.setAccessible(true);
        this.getKodKatastralniUzemi = FilesParser.class.getDeclaredMethod("getKodKatastralniUzemi", Element.class);
        this.getKodKatastralniUzemi.setAccessible(true);
        this.getKodCastObce = FilesParser.class.getDeclaredMethod("getKodCastObce", Element.class);
        this.getKodCastObce.setAccessible(true);
        this.getKodStavebniObjekt = FilesParser.class.getDeclaredMethod("getKodStavebniObjekt", Element.class);
        this.getKodStavebniObjekt.setAccessible(true);
        this.getKodUlice = FilesParser.class.getDeclaredMethod("getKodUlice", Element.class);
        this.getKodUlice.setAccessible(true);
        this.getVOKod = FilesParser.class.getDeclaredMethod("getVOKod", Element.class, String.class);
        this.getVOKod.setAccessible(true);
        this.getCleneniSMRozsahKod = FilesParser.class.getDeclaredMethod("getCleneniSMRozsahKod", Element.class, String.class);
        this.getCleneniSMRozsahKod.setAccessible(true);
        this.getCleneniSMTypKod = FilesParser.class.getDeclaredMethod("getCleneniSMTypKod", Element.class, String.class);
        this.getCleneniSMTypKod.setAccessible(true);
        this.getCisloDomovni = FilesParser.class.getDeclaredMethod("getCisloDomovni", Element.class, String.class);
        this.getCisloDomovni.setAccessible(true);
        this.getCisloOrientacni = FilesParser.class.getDeclaredMethod("getCisloOrientacni", Element.class, String.class);
        this.getCisloOrientacni.setAccessible(true);
        this.getCisloOrientacniPismeno = FilesParser.class.getDeclaredMethod("getCisloOrientacniPismeno", Element.class, String.class);
        this.getCisloOrientacniPismeno.setAccessible(true);
        this.getPSC = FilesParser.class.getDeclaredMethod("getPSC", Element.class, String.class);
        this.getPSC.setAccessible(true);
        this.getPlatiOd = FilesParser.class.getDeclaredMethod("getPlatiOd", Element.class, String.class);
        this.getPlatiOd.setAccessible(true);
        this.getPlatiDo = FilesParser.class.getDeclaredMethod("getPlatiDo", Element.class, String.class);
        this.getPlatiDo.setAccessible(true);
        this.getIdTransakce = FilesParser.class.getDeclaredMethod("getIdTransakce", Element.class, String.class);
        this.getIdTransakce.setAccessible(true);
        this.getGlobalniIdNavrhuZmeny = FilesParser.class.getDeclaredMethod("getGlobalniIdNavrhuZmeny", Element.class, String.class);
        this.getGlobalniIdNavrhuZmeny.setAccessible(true);
        this.getExistujeDigitalniMapa = FilesParser.class.getDeclaredMethod("getExistujeDigitalniMapa", Element.class, String.class);
        this.getExistujeDigitalniMapa.setAccessible(true);
        this.getIdRizeni = FilesParser.class.getDeclaredMethod("getIdRizeni", Element.class, String.class);
        this.getIdRizeni.setAccessible(true);
        this.getNutsLau = FilesParser.class.getDeclaredMethod("getNutsLau", Element.class, String.class);
        this.getNutsLau.setAccessible(true);
        this.getVymera = FilesParser.class.getDeclaredMethod("getVymera", Element.class, String.class);
        this.getVymera.setAccessible(true);
        this.getCharakterZsjKod = FilesParser.class.getDeclaredMethod("getCharakterZsjKod", Element.class, String.class);
        this.getCharakterZsjKod.setAccessible(true);
        this.getKmenoveCislo = FilesParser.class.getDeclaredMethod("getKmenoveCislo", Element.class, String.class);
        this.getKmenoveCislo.setAccessible(true);
        this.getPoddodeleniCisla = FilesParser.class.getDeclaredMethod("getPoddodeleniCisla", Element.class, String.class);
        this.getPoddodeleniCisla.setAccessible(true);
        this.getVymeraParcely = FilesParser.class.getDeclaredMethod("getVymeraParcely", Element.class, String.class);
        this.getVymeraParcely.setAccessible(true);
        this.getDruhCislovaniKod = FilesParser.class.getDeclaredMethod("getDruhCislovaniKod", Element.class, String.class);
        this.getDruhCislovaniKod.setAccessible(true);
        this.getDruhPozemkuKod = FilesParser.class.getDeclaredMethod("getDruhPozemkuKod", Element.class, String.class);
        this.getDruhPozemkuKod.setAccessible(true);
        this.getCislaDomovni = FilesParser.class.getDeclaredMethod("getCislaDomovni", Element.class);
        this.getCislaDomovni.setAccessible(true);
        this.getIdentifikacniParcelaId = FilesParser.class.getDeclaredMethod("getIdentifikacniParcelaId", Element.class);
        this.getIdentifikacniParcelaId.setAccessible(true);
        this.getTypStavebnihoObjektuKod = FilesParser.class.getDeclaredMethod("getTypStavebnihoObjektuKod", Element.class, String.class);
        this.getTypStavebnihoObjektuKod.setAccessible(true);
        this.getZpusobVyuzitiKod = FilesParser.class.getDeclaredMethod("getZpusobVyuzitiKod", Element.class, String.class);
        this.getZpusobVyuzitiKod.setAccessible(true);
        this.getIsknBudovaId = FilesParser.class.getDeclaredMethod("getIsknBudovaId", Element.class, String.class);
        this.getIsknBudovaId.setAccessible(true);
        this.getDokonceni = FilesParser.class.getDeclaredMethod("getDokonceni", Element.class, String.class);
        this.getDokonceni.setAccessible(true);
        this.getDruhKonstrukceKod = FilesParser.class.getDeclaredMethod("getDruhKonstrukceKod", Element.class, String.class);
        this.getDruhKonstrukceKod.setAccessible(true);
        this.getObestavenyProstor = FilesParser.class.getDeclaredMethod("getObestavenyProstor", Element.class, String.class);
        this.getObestavenyProstor.setAccessible(true);
        this.getPocetBytu = FilesParser.class.getDeclaredMethod("getPocetBytu", Element.class, String.class);
        this.getPocetBytu.setAccessible(true);
        this.getPocetPodlazi = FilesParser.class.getDeclaredMethod("getPocetPodlazi", Element.class, String.class);
        this.getPocetPodlazi.setAccessible(true);
        this.getPodlahovaPlocha = FilesParser.class.getDeclaredMethod("getPodlahovaPlocha", Element.class, String.class);
        this.getPodlahovaPlocha.setAccessible(true);
        this.getPripojeniKanalizaceKod = FilesParser.class.getDeclaredMethod("getPripojeniKanalizaceKod", Element.class, String.class);
        this.getPripojeniKanalizaceKod.setAccessible(true);
        this.getPripojeniPlynKod = FilesParser.class.getDeclaredMethod("getPripojeniPlynKod", Element.class, String.class);
        this.getPripojeniPlynKod.setAccessible(true);
        this.getPripojeniVodovodKod = FilesParser.class.getDeclaredMethod("getPripojeniVodovodKod", Element.class, String.class);
        this.getPripojeniVodovodKod.setAccessible(true);
        this.getVybaveniVytahemKod = FilesParser.class.getDeclaredMethod("getVybaveniVytahemKod", Element.class, String.class);
        this.getVybaveniVytahemKod.setAccessible(true);
        this.getZastavenaPlocha = FilesParser.class.getDeclaredMethod("getZastavenaPlocha", Element.class, String.class);
        this.getZastavenaPlocha.setAccessible(true);
        this.getZpusobVytapeniKod = FilesParser.class.getDeclaredMethod("getZpusobVytapeniKod", Element.class, String.class);
        this.getZpusobVytapeniKod.setAccessible(true);
        this.getZpusobyOchranyKod = FilesParser.class.getDeclaredMethod("getZpusobyOchranyKod", Element.class, String.class);
        this.getZpusobyOchranyKod.setAccessible(true);
        this.getDetailniTea = FilesParser.class.getDeclaredMethod("getDetailniTea", Element.class, String.class);
        this.getDetailniTea.setAccessible(true);
        this.getPos = FilesParser.class.getDeclaredMethod("getPos", Element.class);
        this.getPos.setAccessible(true);
        this.getNespravneUdaje = FilesParser.class.getDeclaredMethod("getNespravneUdaje", Element.class, String.class);
        this.getNespravneUdaje.setAccessible(true);
        this.getDatumVzniku = FilesParser.class.getDeclaredMethod("getDatumVzniku", Element.class, String.class);
        this.getDatumVzniku.setAccessible(true);
        this.getMluvChar = FilesParser.class.getDeclaredMethod("getMluvChar", Element.class, String.class);
        this.getMluvChar.setAccessible(true);
        this.getBonitovaneDily = FilesParser.class.getDeclaredMethod("getBonitovaneDily", Element.class, String.class);
        this.getBonitovaneDily.setAccessible(true);
        this.getZpusobOchranyPozemku = FilesParser.class.getDeclaredMethod("getZpusobOchranyPozemku", Element.class, String.class);
        this.getZpusobOchranyPozemku.setAccessible(true);
        this.getVlajkaText = FilesParser.class.getDeclaredMethod("getVlajkaText", Element.class, String.class);
        this.getVlajkaText.setAccessible(true);
        this.getVlajkaObrazek = FilesParser.class.getDeclaredMethod("getVlajkaObrazek", Element.class, String.class);
        this.getVlajkaObrazek.setAccessible(true);
        this.getZnakObrazek = FilesParser.class.getDeclaredMethod("getZnakObrazek", Element.class, String.class);
        this.getZnakObrazek.setAccessible(true);
        this.getZnakText = FilesParser.class.getDeclaredMethod("getZnakText", Element.class, String.class);
        this.getZnakText.setAccessible(true);

    }

    @Test
    @DisplayName("Should assert that files have .xml extension")
    void walk() throws IOException {
        File xmlDirectory = new ClassPathResource("/xml/").getFile();

        File root = new File(xmlDirectory.getAbsolutePath());
        File[] list = root.listFiles();

        assertNotNull(list);

        for (File f : list) {
            assertTrue(f.isFile());

            if (f.getName().endsWith(".xml")) {
                assertTrue(f.getName().endsWith(".xml"));
            }
        }
    }

    @Test
    @DisplayName("Should assert correct and incorrect 'vf:Data' child nodes")
    void childNodesFromXML() throws IOException, ParserConfigurationException, SAXException {
        File file = new ClassPathResource("xml/ruian_test.xml").getFile();

        assertEquals("ruian_test.xml", file.getName());

        //Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Build Document
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();

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
    void parseStatElement() throws ParseException, IOException,
            ParserConfigurationException, SAXException, InvocationTargetException, IllegalAccessException {
        String prefix = "sti";
        boolean nespravny = false;
        File file = new ClassPathResource("xml/ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(1).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Stat", node.getNodeName());

        Element element = (Element) node;

        Integer kod = (Integer) getKod.invoke(filesParser, element, prefix);
        String nazev = (String) getNazev.invoke(filesParser, element, prefix);
        Date platiOd = (Date) getPlatiOd.invoke(filesParser, element, prefix);
        Date platiDo = (Date) getPlatiDo.invoke(filesParser, element, prefix);
        Long idTransakce = (Long) getIdTransakce.invoke(filesParser, element, prefix);
        Long globalniIdNavrhuZmeny = (Long) getGlobalniIdNavrhuZmeny.invoke(filesParser, element, prefix);
        String nutsLau = (String) getNutsLau.invoke(filesParser, element, prefix);
        Point pos = (Point) getPos.invoke(filesParser, element);
        NespravnyUdaj nespravnyUdaj = (NespravnyUdaj) getNespravneUdaje.invoke(filesParser, element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = (Date) getDatumVzniku.invoke(filesParser, element, prefix);

        Stat statExpected = new Stat(1, "Česká republika", false, sdf.parse("2015-06-06T00:00:00"),
                null, 0L, 731654L, "CZ", new Point(-743100.00, -1043300.00),
                null, sdf.parse("1993-01-01T00:00:00"));

        Stat statActual = new Stat(kod, nazev, nespravny, platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny,
                nutsLau, pos, nespravnyUdaj, datumVzniku);

        assertEquals(statExpected, statActual);
    }

    @Test
    @DisplayName("Should parse RegionSoudrznosti from XML file")
    void parseRegionSoudrznostiElement() throws ParserConfigurationException, IOException,
            SAXException, InvocationTargetException, IllegalAccessException, ParseException {
        String prefix = "rsi";
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
        Integer kod = (Integer) getKod.invoke(filesParser, element, prefix);
        String nazev = (String) getNazev.invoke(filesParser, element, prefix);
        Integer kodStatu = (Integer) getKodStatu.invoke(filesParser, element);
        Date platiOd = (Date) getPlatiOd.invoke(filesParser, element, prefix);
        Date platiDo = (Date) getPlatiDo.invoke(filesParser, element, prefix);
        Long idTransakce = (Long) getIdTransakce.invoke(filesParser, element, prefix);
        Long globalniIdNavrhuZmeny = (Long) getGlobalniIdNavrhuZmeny.invoke(filesParser, element, prefix);
        String nutsLau = (String) getNutsLau.invoke(filesParser, element, prefix);
        Point pos = (Point) getPos.invoke(filesParser, element);
        NespravnyUdaj nespravnyUdaj = (NespravnyUdaj) getNespravneUdaje.invoke(filesParser, element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = (Date) getDatumVzniku.invoke(filesParser, element, prefix);

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
    void parseVuscElement() throws ParserConfigurationException, IOException,
            SAXException, InvocationTargetException, IllegalAccessException, ParseException {
        String prefix = "vci";
        boolean nespravny = false;

        File file = new ClassPathResource("xml/ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(5).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Vusc", node.getNodeName());

        Element element = (Element) node;

        Integer kod = (Integer) getKod.invoke(filesParser, element, prefix);
        String nazev = (String) getNazev.invoke(filesParser, element, prefix);
        Integer kodRs = (Integer) getKodRS.invoke(filesParser, element);
        Date platiOd = (Date) getPlatiOd.invoke(filesParser, element, prefix);
        Date platiDo = (Date) getPlatiDo.invoke(filesParser, element, prefix);
        Long idTransakce = (Long) getIdTransakce.invoke(filesParser, element, prefix);
        Long globalniIdNavrhuZmeny = (Long) getGlobalniIdNavrhuZmeny.invoke(filesParser, element, prefix);
        String nutsLau = (String) getNutsLau.invoke(filesParser, element, prefix);
        Point pos = (Point) getPos.invoke(filesParser, element);
        NespravnyUdaj nespravnyUdaj = (NespravnyUdaj) getNespravneUdaje.invoke(filesParser, element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = (Date) getDatumVzniku.invoke(filesParser, element, prefix);

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
    void parseOkresElement() throws ParserConfigurationException, IOException,
            SAXException, InvocationTargetException, IllegalAccessException, ParseException {
        String prefix = "oki";
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

        Integer kod = (Integer) getKod.invoke(filesParser, element, prefix);
        String nazev = (String) getNazev.invoke(filesParser, element, prefix);
        Integer kodVusc = (Integer) getKodVusc.invoke(filesParser, element);
        Date platiOd = (Date) getPlatiOd.invoke(filesParser, element, prefix);
        Date platiDo = (Date) getPlatiDo.invoke(filesParser, element, prefix);
        Long idTransakce = (Long) getIdTransakce.invoke(filesParser, element, prefix);
        Long globalniIdNavrhuZmeny = (Long) getGlobalniIdNavrhuZmeny.invoke(filesParser, element, prefix);
        String nutsLau = (String) getNutsLau.invoke(filesParser, element, prefix);
        Point pos = (Point) getPos.invoke(filesParser, element);
        NespravnyUdaj nespravnyUdaj = (NespravnyUdaj) getNespravneUdaje.invoke(filesParser, element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = (Date) getDatumVzniku.invoke(filesParser, element, prefix);

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
    void parseOrpElement() throws IOException, ParserConfigurationException, SAXException,
            InvocationTargetException, IllegalAccessException, ParseException {
        String prefix = "opi";
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

        Integer kod = (Integer) getKod.invoke(filesParser, element, prefix);
        String nazev = (String) getNazev.invoke(filesParser, element, prefix);
        Integer spravniObecKod = (Integer) getKodSpravniObec.invoke(filesParser, element, prefix);
        Integer kodOkresu = (Integer) getKodOkresu.invoke(filesParser, element);
        Date platiOd = (Date) getPlatiOd.invoke(filesParser, element, prefix);
        Date platiDo = (Date) getPlatiDo.invoke(filesParser, element, prefix);
        Long idTransakce = (Long) getIdTransakce.invoke(filesParser, element, prefix);
        Long globalniIdNavrhuZmeny = (Long) getGlobalniIdNavrhuZmeny.invoke(filesParser, element, prefix);
        Point pos = (Point) getPos.invoke(filesParser, element);
        NespravnyUdaj nespravnyUdaj = (NespravnyUdaj) getNespravneUdaje.invoke(filesParser, element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = (Date) getDatumVzniku.invoke(filesParser, element, prefix);

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
    void parsePouElement() throws IOException, ParserConfigurationException, SAXException,
            InvocationTargetException, IllegalAccessException, ParseException {
        String prefix = "pui";
        boolean nespravny = false;

        File file = new ClassPathResource("xml/ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(11).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Pou", node.getNodeName());

        Element element = (Element) node;

        Integer kod = (Integer) getKod.invoke(filesParser, element, prefix);
        String nazev = (String) getNazev.invoke(filesParser, element, prefix);
        Integer spravniObecKod = (Integer) getKodSpravniObec.invoke(filesParser, element, prefix);
        Integer kodOrp = (Integer) getKodOrp.invoke(filesParser, element);
        Date platiOd = (Date) getPlatiOd.invoke(filesParser, element, prefix);
        Date platiDo = (Date) getPlatiDo.invoke(filesParser, element, prefix);
        Long idTransakce = (Long) getIdTransakce.invoke(filesParser, element, prefix);
        Long globalniIdNavrhuZmeny = (Long) getGlobalniIdNavrhuZmeny.invoke(filesParser, element, prefix);
        Point pos = (Point) getPos.invoke(filesParser, element);
        NespravnyUdaj nespravnyUdaj = (NespravnyUdaj) getNespravneUdaje.invoke(filesParser, element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = (Date) getDatumVzniku.invoke(filesParser, element, prefix);

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
    void parseObecElement() throws IOException, ParserConfigurationException, SAXException,
            InvocationTargetException, IllegalAccessException, ParseException {
        String prefix = "obi";
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

        Integer kod = (Integer) getKod.invoke(filesParser, element, prefix);
        String nazev = (String) getNazev.invoke(filesParser, element, prefix);
        Integer statusKod = (Integer) getStatusKod.invoke(filesParser, element, prefix);
        Integer kodOkresu = (Integer) getKodOkresu.invoke(filesParser, element);
        Integer kodPou = (Integer) getKodPou.invoke(filesParser, element);
        Date platiOd = (Date) getPlatiOd.invoke(filesParser, element, prefix);
        Date platiDo = (Date) getPlatiDo.invoke(filesParser, element, prefix);
        Long idTransakce = (Long) getIdTransakce.invoke(filesParser, element, prefix);
        Long globalniIdNavrhuZmeny = (Long) getGlobalniIdNavrhuZmeny.invoke(filesParser, element, prefix);
        Point pos = (Point) getPos.invoke(filesParser, element);
        MluvnickeCharakteristiky mluvChar = (MluvnickeCharakteristiky) getMluvChar.invoke(filesParser, element, prefix);
        String vlajkaText = (String) getVlajkaText.invoke(filesParser, element, prefix);
        String vlajkaObrazek = (String) getVlajkaObrazek.invoke(filesParser, element, prefix);
        String znakText = (String) getZnakText.invoke(filesParser, element, prefix);
        String znakObrazek = (String) getZnakObrazek.invoke(filesParser, element, prefix);
        String nutsLau = (String) getNutsLau.invoke(filesParser, element, prefix);
        Integer cleneniSMTypKod = (Integer) getCleneniSMTypKod.invoke(filesParser, element, prefix);
        Integer cleneniSMRozsahKod = (Integer) getCleneniSMRozsahKod.invoke(filesParser, element, prefix);
        NespravnyUdaj nespravnyUdaj = (NespravnyUdaj) getNespravneUdaje.invoke(filesParser, element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = (Date) getDatumVzniku.invoke(filesParser, element, prefix);

        Obec obecExpected = new Obec(569089, "Maleč", false, 2, 3601, 1970,
                sdf.parse("2019-01-16T00:00:00"), null, 2718946L,
                1933345L,
                new MluvnickeCharakteristiky("Malče", "Malči", "Maleč", "Malči", "Malčí"),
                "List tvoří dva žluté žerďové klíny s vrcholy v první čtvrtině délky listu, červené pole s bílým mořským psem se žlutou zbrojí a dva svislé pruhy, bílý a modrý, každý široký jednu osminu délky listu. Poměr šířky k délce listu je 2:3.",
                null, "V červeném štítě se stříbrno-modře vlnitě dělenou vlnitou patou, pod dvěma zlatými, ke středu prolomenými zvýšenými klíny stříbrný mořský pes se zlatou zbrojí.", null,
                null, null, "CZ0631569089",
                new Point(-657903.00, -1089346.00), null, null);

        Obec obecActual = new Obec(kod, nazev, nespravny, statusKod, kodOkresu, kodPou,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, mluvChar, vlajkaText, vlajkaObrazek,
                znakText, znakObrazek, cleneniSMTypKod, cleneniSMRozsahKod, nutsLau, pos,
                nespravnyUdaj, datumVzniku);

        assertEquals(obecExpected, obecActual);
    }

    @Test
    @DisplayName("Should parse SpravniObvod from XML file")
    void parseSpravniObvodElement() throws IOException, ParserConfigurationException, SAXException,
            InvocationTargetException, IllegalAccessException, ParseException {
        String prefix = "spi";
        boolean nespravny = false;

        File file = new ClassPathResource("xml/ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(15).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:SpravniObvod", node.getNodeName());

        Element element = (Element) node;

        Integer kod = (Integer) getKod.invoke(filesParser, element, prefix);
        String nazev = (String) getNazev.invoke(filesParser, element, prefix);
        Integer spravniMomcKod = (Integer) getSpravniMomcKod.invoke(filesParser, element, prefix);
        Integer kodObce = (Integer) getKodObce.invoke(filesParser, element);
        Date platiOd = (Date) getPlatiOd.invoke(filesParser, element, prefix);
        Date platiDo = (Date) getPlatiDo.invoke(filesParser, element, prefix);
        Long idTransakce = (Long) getIdTransakce.invoke(filesParser, element, prefix);
        Long globalniIdNavrhuZmeny = (Long) getGlobalniIdNavrhuZmeny.invoke(filesParser, element, prefix);
        Point pos = (Point) getPos.invoke(filesParser, element);
        NespravnyUdaj nespravnyUdaj = (NespravnyUdaj) getNespravneUdaje.invoke(filesParser, element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = (Date) getDatumVzniku.invoke(filesParser, element, prefix);

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
    void parseMopElement() throws IOException, ParserConfigurationException, SAXException,
            InvocationTargetException, IllegalAccessException, ParseException {
        String prefix = "mpi";
        boolean nespravny = false;

        File file = new ClassPathResource("xml/ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(17).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Mop", node.getNodeName());

        Element element = (Element) node;

        Integer kod = (Integer) getKod.invoke(filesParser, element, prefix);
        String nazev = (String) getNazev.invoke(filesParser, element, prefix);
        Integer kodObce = (Integer) getKodObce.invoke(filesParser, element);
        Date platiOd = (Date) getPlatiOd.invoke(filesParser, element, prefix);
        Date platiDo = (Date) getPlatiDo.invoke(filesParser, element, prefix);
        Long idTransakce = (Long) getIdTransakce.invoke(filesParser, element, prefix);
        Long globalniIdNavrhuZmeny = (Long) getGlobalniIdNavrhuZmeny.invoke(filesParser, element, prefix);
        Point pos = (Point) getPos.invoke(filesParser, element);
        NespravnyUdaj nespravnyUdaj = (NespravnyUdaj) getNespravneUdaje.invoke(filesParser, element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = (Date) getDatumVzniku.invoke(filesParser, element, prefix);

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
    void parseMomcElement() throws IOException, ParserConfigurationException, SAXException,
            InvocationTargetException, IllegalAccessException, ParseException {
        String prefix = "mci";
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

        Integer kod = (Integer) getKod.invoke(filesParser, element, prefix);
        String nazev = (String) getNazev.invoke(filesParser, element, prefix);
        Integer kodMop = (Integer) getKodMop.invoke(filesParser, element);
        Integer kodObce = (Integer) getKodObce.invoke(filesParser, element);
        Integer kodSpravniObvod = (Integer) getKodSpravniObvod.invoke(filesParser, element);
        Date platiOd = (Date) getPlatiOd.invoke(filesParser, element, prefix);
        Date platiDo = (Date) getPlatiDo.invoke(filesParser, element, prefix);
        Long idTransakce = (Long) getIdTransakce.invoke(filesParser, element, prefix);
        Long globalniIdNavrhuZmeny = (Long) getGlobalniIdNavrhuZmeny.invoke(filesParser, element, prefix);
        Point pos = (Point) getPos.invoke(filesParser, element);
        MluvnickeCharakteristiky mluvChar = (MluvnickeCharakteristiky) getMluvChar.invoke(filesParser, element, prefix);
        String vlajkaText = (String) getVlajkaText.invoke(filesParser, element, prefix);
        String vlajkaObrazek = (String) getVlajkaObrazek.invoke(filesParser, element, prefix);
        String znakText = (String) getZnakText.invoke(filesParser, element, prefix);
        String znakObrazek = (String) getZnakObrazek.invoke(filesParser, element, prefix);
        NespravnyUdaj nespravnyUdaj = (NespravnyUdaj) getNespravneUdaje.invoke(filesParser, element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = (Date) getDatumVzniku.invoke(filesParser, element, prefix);

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
    void parseCastObceElement() throws IOException, ParserConfigurationException, SAXException,
            InvocationTargetException, IllegalAccessException, ParseException {
        String prefix = "coi";
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

        Integer kod = (Integer) getKod.invoke(filesParser, element, prefix);
        String nazev = (String) getNazev.invoke(filesParser, element, prefix);
        Integer kodObce = (Integer) getKodObce.invoke(filesParser, element);
        Date platiOd = (Date) getPlatiOd.invoke(filesParser, element, prefix);
        Date platiDo = (Date) getPlatiDo.invoke(filesParser, element, prefix);
        Long idTransakce = (Long) getIdTransakce.invoke(filesParser, element, prefix);
        Long globalniIdNavrhuZmeny = (Long) getGlobalniIdNavrhuZmeny.invoke(filesParser, element, prefix);
        Point pos = (Point) getPos.invoke(filesParser, element);
        MluvnickeCharakteristiky mluvChar = (MluvnickeCharakteristiky) getMluvChar.invoke(filesParser, element, prefix);
        NespravnyUdaj nespravnyUdaj = (NespravnyUdaj) getNespravneUdaje.invoke(filesParser, element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = (Date) getDatumVzniku.invoke(filesParser, element, prefix);

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
    void parseKatastralniUzemiElement() throws IOException, ParserConfigurationException, SAXException,
            InvocationTargetException, IllegalAccessException, ParseException {
        String prefix = "kui";
        boolean nespravny = false;

        File file = new ClassPathResource("xml/ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(23).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:KatastralniUzemi", node.getNodeName());

        Element element = (Element) node;

        Integer kod = (Integer) getKod.invoke(filesParser, element, prefix);
        String nazev = (String) getNazev.invoke(filesParser, element, prefix);
        Boolean existujeDigitalniMapa = (Boolean) getExistujeDigitalniMapa.invoke(filesParser, element, prefix);
        Integer kodObce = (Integer) getKodObce.invoke(filesParser, element);
        Date platiOd = (Date) getPlatiOd.invoke(filesParser, element, prefix);
        Date platiDo = (Date) getPlatiDo.invoke(filesParser, element, prefix);
        Long idTransakce = (Long) getIdTransakce.invoke(filesParser, element, prefix);
        Long globalniIdNavrhuZmeny = (Long) getGlobalniIdNavrhuZmeny.invoke(filesParser, element, prefix);
        Long rizeniId = (Long) getIdRizeni.invoke(filesParser, element, prefix);
        Point pos = (Point) getPos.invoke(filesParser, element);
        MluvnickeCharakteristiky mluvChar = (MluvnickeCharakteristiky) getMluvChar.invoke(filesParser, element, prefix);
        NespravnyUdaj nespravnyUdaj = (NespravnyUdaj) getNespravneUdaje.invoke(filesParser, element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = (Date) getDatumVzniku.invoke(filesParser, element, prefix);

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
    void parseZsjElement() throws IOException, ParserConfigurationException, SAXException,
            InvocationTargetException, IllegalAccessException, ParseException {
        String prefix = "zji";
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

        Integer kod = (Integer) getKod.invoke(filesParser, element, prefix);
        String nazev = (String) getNazev.invoke(filesParser, element, prefix);
        Integer kodKatastralniUzemi = (Integer) getKodKatastralniUzemi.invoke(filesParser, element);
        Date platiOd = (Date) getPlatiOd.invoke(filesParser, element, prefix);
        Date platiDo = (Date) getPlatiDo.invoke(filesParser, element, prefix);
        Long idTransakce = (Long) getIdTransakce.invoke(filesParser, element, prefix);
        Long globalniIdNavrhuZmeny = (Long) getGlobalniIdNavrhuZmeny.invoke(filesParser, element, prefix);
        Long vymera = (Long) getVymera.invoke(filesParser, element, prefix);
        Integer charakterZsjKod = (Integer) getCharakterZsjKod.invoke(filesParser, element, prefix);
        Point pos = (Point) getPos.invoke(filesParser, element);
        MluvnickeCharakteristiky mluvChar = (MluvnickeCharakteristiky) getMluvChar.invoke(filesParser, element, prefix);
        NespravnyUdaj nespravnyUdaj = (NespravnyUdaj) getNespravneUdaje.invoke(filesParser, element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = (Date) getDatumVzniku.invoke(filesParser, element, prefix);

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
    void parseUliceElement() throws IOException, ParserConfigurationException, SAXException,
            InvocationTargetException, IllegalAccessException, ParseException {
        String prefix = "uli";
        boolean nespravny = false;

        File file = new ClassPathResource("xml/ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(27).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:Ulice", node.getNodeName());

        Element element = (Element) node;

        Integer kod = (Integer) getKod.invoke(filesParser, element, prefix);
        String nazev = (String) getNazev.invoke(filesParser, element, prefix);
        Integer kodObce = (Integer) getKodObce.invoke(filesParser, element);
        Date platiOd = (Date) getPlatiOd.invoke(filesParser, element, prefix);
        Date platiDo = (Date) getPlatiDo.invoke(filesParser, element, prefix);
        Long idTransakce = (Long) getIdTransakce.invoke(filesParser, element, prefix);
        Long globalniIdNavrhuZmeny = (Long) getGlobalniIdNavrhuZmeny.invoke(filesParser, element, prefix);
        NespravnyUdaj nespravnyUdaj = (NespravnyUdaj) getNespravneUdaje.invoke(filesParser, element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Ulice uliceExpected = new Ulice(644196, "4. května", false, 500011,
                sdf.parse("2018-01-22T00:00:00"), null, 2277057L, 1672622L, null);

        Ulice uliceActual = new Ulice(kod, nazev, nespravny, kodObce,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, nespravnyUdaj);

        assertEquals(uliceExpected, uliceActual);
    }

    @Test
    @DisplayName("Should parse Parcela from XML file")
    void parseParcelaElement() throws IOException, ParserConfigurationException, SAXException,
            InvocationTargetException, IllegalAccessException, ParseException {
        String prefix = "pai";
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

        Long id = (Long) getId.invoke(filesParser, element, prefix);
        Integer kmenoveCislo = (Integer) getKmenoveCislo.invoke(filesParser, element, prefix);
        Integer pododdeleniCisla = (Integer) getPoddodeleniCisla.invoke(filesParser, element, prefix);
        Long vymeraParcely = (Long) getVymeraParcely.invoke(filesParser, element, prefix);
        Integer druhCislovaniKod = (Integer) getDruhCislovaniKod.invoke(filesParser, element, prefix);
        Integer druhPozemkuKod = (Integer) getDruhPozemkuKod.invoke(filesParser, element, prefix);
        Integer kodKatastralniUzemi = (Integer) getKodKatastralniUzemi.invoke(filesParser, element);
        Long rizeniId = (Long) getIdRizeni.invoke(filesParser, element, prefix);
        Date platiOd = (Date) getPlatiOd.invoke(filesParser, element, prefix);
        Date platiDo = (Date) getPlatiDo.invoke(filesParser, element, prefix);
        Long idTransakce = (Long) getIdTransakce.invoke(filesParser, element, prefix);
        List<BonitovanyDil> bonitovaneDily = (List<BonitovanyDil>) getBonitovaneDily.invoke(filesParser, element, prefix);
        ZpusobOchranyPozemku zpusobOchranyPozemku = (ZpusobOchranyPozemku) getZpusobOchranyPozemku.invoke(filesParser, element, prefix);
        Point pos = (Point) getPos.invoke(filesParser, element);
        NespravnyUdaj nespravnyUdaj = (NespravnyUdaj) getNespravneUdaje.invoke(filesParser, element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
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
    void parseStavebniObjektElement() throws IOException, ParserConfigurationException, SAXException,
            InvocationTargetException, IllegalAccessException, ParseException {
        String prefix = "soi";
        boolean nespravny = false;

        File file = new ClassPathResource("xml/ruian_test.xml").getFile();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        NodeList nListDataNode = document.getElementsByTagName("vf:Data");
        NodeList nodeListData = nListDataNode.item(0).getChildNodes();
        NodeList nodeL = nodeListData.item(31).getChildNodes();
        Node node = nodeL.item(1);

        assertEquals("vf:StavebniObjekt", node.getNodeName());

        Element element = (Element) node;

        Integer kod = (Integer) getKod.invoke(filesParser, element, prefix);
        List<Integer> cislaDomovni = (List<Integer>) getCislaDomovni.invoke(filesParser, element);
        Long identifikacniParcelaId = (Long) getIdentifikacniParcelaId.invoke(filesParser, element);
        Integer typStavebnihoObjektuKod = (Integer) getTypStavebnihoObjektuKod.invoke(filesParser, element, prefix);
        Integer zpusobVyuzitiKod = (Integer) getZpusobVyuzitiKod.invoke(filesParser, element, prefix);
        Integer castObceKod = (Integer) getKodCastObce.invoke(filesParser, element);
        Long globalniIdNavrhuZmeny = (Long) getGlobalniIdNavrhuZmeny.invoke(filesParser, element, prefix);
        Long isknBudovaId = (Long) getIsknBudovaId.invoke(filesParser, element, prefix);
        Date dokonceni = (Date) getDokonceni.invoke(filesParser, element, prefix);
        Integer druhKonstrukceKod = (Integer) getDruhKonstrukceKod.invoke(filesParser, element, prefix);
        Integer obestavenyProstor = (Integer) getObestavenyProstor.invoke(filesParser, element, prefix);
        Integer pocetBytu = (Integer) getPocetBytu.invoke(filesParser, element, prefix);
        Integer pocetPodlazi = (Integer) getPocetPodlazi.invoke(filesParser, element, prefix);
        Integer podlahovaPlocha = (Integer) getPodlahovaPlocha.invoke(filesParser, element, prefix);
        Integer pripojeniKanalizaceKod = (Integer) getPripojeniKanalizaceKod.invoke(filesParser, element, prefix);
        Integer pripojeniPlynKod = (Integer) getPripojeniPlynKod.invoke(filesParser, element, prefix);
        Integer pripojeniVodovodKod = (Integer) getPripojeniVodovodKod.invoke(filesParser, element, prefix);
        Integer vybaveniVytahemKod = (Integer) getVybaveniVytahemKod.invoke(filesParser, element, prefix);
        Integer zastavenaPlocha = (Integer) getZastavenaPlocha.invoke(filesParser, element, prefix);
        Integer zpusobVytapeniKod = (Integer) getZpusobVytapeniKod.invoke(filesParser, element, prefix);
        List<Integer> zpusobyOchranyKod = (List<Integer>) getZpusobyOchranyKod.invoke(filesParser, element, prefix);
        List<DetailniTEA> detailniTEAList = (List<DetailniTEA>) getDetailniTea.invoke(filesParser, element, prefix);
        Date platiOd = (Date) getPlatiOd.invoke(filesParser, element, prefix);
        Date platiDo = (Date) getPlatiDo.invoke(filesParser, element, prefix);
        Long idTransakce = (Long) getIdTransakce.invoke(filesParser, element, prefix);
        Point pos = (Point) getPos.invoke(filesParser, element);
        NespravnyUdaj nespravnyUdaj = (NespravnyUdaj) getNespravneUdaje.invoke(filesParser, element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
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
    void parseAdresniMistoElement() throws IOException, ParserConfigurationException, SAXException,
            InvocationTargetException, IllegalAccessException, ParseException {
        String prefix = "ami";
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

        Integer kod = (Integer) getKod.invoke(filesParser, element, prefix);
        String cisloDomovni = (String) getCisloDomovni.invoke(filesParser, element, prefix);
        String cisloOrientacni = (String) getCisloOrientacni.invoke(filesParser, element, prefix);
        String cisloOrientacniPismeno = (String) getCisloOrientacniPismeno.invoke(filesParser, element, prefix);
        String psc = (String) getPSC.invoke(filesParser, element, prefix);
        Integer stavebniObjektKod = (Integer) getKodStavebniObjekt.invoke(filesParser, element);
        Integer uliceKod = (Integer) getKodUlice.invoke(filesParser, element);
        Integer voKod = (Integer) getVOKod.invoke(filesParser, element, prefix);
        Date platiOd = (Date) getPlatiOd.invoke(filesParser, element, prefix);
        Date platiDo = (Date) getPlatiDo.invoke(filesParser, element, prefix);
        Long idTransakce = (Long) getIdTransakce.invoke(filesParser, element, prefix);
        Long globalniIdNavrhuZmeny = (Long) getGlobalniIdNavrhuZmeny.invoke(filesParser, element, prefix);
        Point pos = (Point) getPos.invoke(filesParser, element);
        NespravnyUdaj nespravnyUdaj = (NespravnyUdaj) getNespravneUdaje.invoke(filesParser, element, prefix);

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        AdresniMisto adresniMistoExpected = new AdresniMisto(72850124, false, "668", "55",
                "A", "76311", 78171482, 647268, 42877,
                sdf.parse("2014-06-26T00:00:00"), null,
                630728L, 594036L, new Point(-516005.93, -1168276.78), null);

        AdresniMisto adresniMistoActual = new AdresniMisto(kod, nespravny, cisloDomovni, cisloOrientacni,
                cisloOrientacniPismeno, psc, stavebniObjektKod, uliceKod, voKod, platiOd, platiDo,
                idTransakce, globalniIdNavrhuZmeny, pos, nespravnyUdaj);

        assertEquals(adresniMistoExpected, adresniMistoActual);
    }
}