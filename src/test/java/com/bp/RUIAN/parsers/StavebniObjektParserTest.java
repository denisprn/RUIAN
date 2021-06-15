package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.DetailniTEA;
import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.StavebniObjekt;
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

public class StavebniObjektParserTest {
    private static final String PREFIX = Prefixes.STAVEBNI_OBJEKT_PREFIX;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    @DisplayName("Should parse StavebniObjekt from XML file")
    void parseStavebniObjektElement() throws IOException, ParserConfigurationException, SAXException, ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
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

        AttributeParser attributeParser = new AttributeParser(element, PREFIX);

        Integer kod = attributeParser.getKod();
        List<Integer> cislaDomovni = attributeParser.getCislaDomovni();
        Long identifikacniParcelaId = attributeParser.getIdentifikacniParcelaId();
        Integer typStavebnihoObjektuKod = attributeParser.getTypStavebnihoObjektuKod();
        Integer zpusobVyuzitiKod = attributeParser.getZpusobVyuzitiKod();
        Integer castObceKod = attributeParser.getKodCastObce();
        Long globalniIdNavrhuZmeny = attributeParser.getGlobalniIdNavrhuZmeny();
        Long isknBudovaId = attributeParser.getIsknBudovaId();
        Date dokonceni = attributeParser.getDokonceni();
        Integer druhKonstrukceKod = attributeParser.getDruhKonstrukceKod();
        Integer obestavenyProstor = attributeParser.getObestavenyProstor();
        Integer pocetBytu = attributeParser.getPocetBytu();
        Integer pocetPodlazi = attributeParser.getPocetPodlazi();
        Integer podlahovaPlocha = attributeParser.getPodlahovaPlocha();
        Integer pripojeniKanalizaceKod = attributeParser.getPripojeniKanalizaceKod();
        Integer pripojeniPlynKod = attributeParser.getPripojeniPlynKod();
        Integer pripojeniVodovodKod = attributeParser.getPripojeniVodovodKod();
        Integer vybaveniVytahemKod = attributeParser.getVybaveniVytahemKod();
        Integer zastavenaPlocha = attributeParser.getZastavenaPlocha();
        Integer zpusobVytapeniKod = attributeParser.getZpusobVytapeniKod();
        List<Integer> zpusobyOchranyKod = attributeParser.getZpusobyOchranyKod();
        List<DetailniTEA> detailniTEAList = attributeParser.getDetailniTea();
        Date platiOd = attributeParser.getPlatiOd();
        Date platiDo = attributeParser.getPlatiDo();
        Long idTransakce = attributeParser.getIdTransakce();
        GeoJsonPoint definicniBod = attributeParser.getDefinicniBod();
        GeoJsonPolygon hranice = attributeParser.getHranice();
        NespravnyUdaj nespravnyUdaj = attributeParser.getNespravneUdaje();

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

        List<Point> points = new ArrayList<>();
        points.add(new Point(-514663.37, -1166842.49));
        points.add(new Point(-514680.76, -1166832.56));
        points.add(new Point(-514698.33, -1166822.53));
        points.add(new Point(-514693.10, -1166813.40));

        StavebniObjekt stavebniObjektExpected = new StavebniObjekt(4165586, false, cislaDomovniExpected, 3563823705L,
                1, 6, 195901, sdf.parse("2019-03-07T00:00:00"), null, 2780214L,
                1964039L, 691202705L, sdf.parse("2019-03-06T00:00:00"), 10, 3850,
                8, 3, 1050, 1, 1, 1,
                2, 422, 1, null, detailniTeaListExpected,
                GeoJsonPoint.of(new Point(-514688.19, -1166820.85)),
                GeoJsonPolygon.of(points), null);

        StavebniObjekt stavebniObjektActual = new StavebniObjekt(kod, nespravny, cislaDomovni, identifikacniParcelaId,
                typStavebnihoObjektuKod, zpusobVyuzitiKod, castObceKod, platiOd, platiDo, idTransakce,
                globalniIdNavrhuZmeny, isknBudovaId, dokonceni, druhKonstrukceKod, obestavenyProstor, pocetBytu,
                pocetPodlazi, podlahovaPlocha, pripojeniKanalizaceKod, pripojeniPlynKod, pripojeniVodovodKod,
                vybaveniVytahemKod, zastavenaPlocha, zpusobVytapeniKod, zpusobyOchranyKod, detailniTEAList,
                definicniBod, hranice, nespravnyUdaj);

        assertEquals(stavebniObjektExpected, stavebniObjektActual);
    }
}
