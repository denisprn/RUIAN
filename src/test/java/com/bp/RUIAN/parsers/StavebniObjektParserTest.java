package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.DetailniTEA;
import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.StavebniObjekt;
import com.bp.RUIAN.utils.Prefixes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;
import org.springframework.data.geo.Point;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
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

        Element element = ElementParser.getElement(31);

        IntegerParser integerParser = new IntegerParser(element, PREFIX);
        LongParser longParser = new LongParser(element, PREFIX);
        DateParser dateParser = new DateParser(element, PREFIX);
        DefinicniBodParser definicniBodParser = new DefinicniBodParser(element);
        HraniceParser hraniceParser = new HraniceParser(element);
        ListIntegerParser intArrParser = new ListIntegerParser(element, PREFIX);
        DetailniTeaParser detailniTeaParser = new DetailniTeaParser(element, PREFIX);
        NespravnyUdajParser nespravnyUdajParser = new NespravnyUdajParser(element, PREFIX);

        Integer kod = integerParser.parse("Kod");
        List<Integer> cislaDomovni = intArrParser.parse("com:CisloDomovni");
        Long identifikacniParcelaId = longParser.parse("pai:Id");
        Integer typStavebnihoObjektuKod = integerParser.parse("TypStavebnihoObjektuKod");
        Integer zpusobVyuzitiKod = integerParser.parse("ZpusobVyuzitiKod");
        Integer castObceKod = integerParser.parse("coi:Kod");
        Date platiOd = dateParser.parse("PlatiOd");
        Date platiDo = dateParser.parse("PlatiDo");
        Long idTransakce = longParser.parse("IdTransakce");
        Long globalniIdNavrhuZmeny = longParser.parse("GlobalniIdNavrhuZmeny");
        Long isknBudovaId = longParser.parse("IsknBudovaId");
        Date dokonceni = dateParser.parse("Dokonceni");
        Integer druhKonstrukceKod = integerParser.parse("DruhKonstrukceKod");
        Integer obestavenyProstor = integerParser.parse("ObestavenyProstor");
        Integer pocetBytu = integerParser.parse("PocetBytu");
        Integer pocetPodlazi = integerParser.parse("PocetPodlazi");
        Integer podlahovaPlocha = integerParser.parse("PodlahovaPlocha");
        Integer pripojeniKanalizaceKod = integerParser.parse("PripojeniKanalizaceKod");
        Integer pripojeniPlynKod = integerParser.parse("PripojeniPlynKod");
        Integer pripojeniVodovodKod = integerParser.parse("PripojeniVodovodKod");
        Integer vybaveniVytahemKod = integerParser.parse("VybaveniVytahemKod");
        Integer zastavenaPlocha = integerParser.parse("ZastavenaPlocha");
        Integer zpusobVytapeniKod = integerParser.parse("ZpusobVytapeniKod");
        List<Integer> zpusobyOchranyKod = intArrParser.parse("ZpusobOchranyKod");
        List<DetailniTEA> detailniTEAList = detailniTeaParser.parse("DetailniTEA");
        GeoJsonPoint definicniBod = definicniBodParser.parse("pos");
        GeoJsonPolygon hranice = hraniceParser.parse("posList");
        NespravnyUdaj nespravnyUdaj = nespravnyUdajParser.parse("NespravneUdaje");

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
