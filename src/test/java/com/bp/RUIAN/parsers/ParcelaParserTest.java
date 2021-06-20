package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.BonitovanyDil;
import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.Parcela;
import com.bp.RUIAN.entities.ZpusobOchranyPozemku;
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

public class ParcelaParserTest {
    private static final String PREFIX = Prefixes.PARCELA_PREFIX;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    @DisplayName("Should parse Parcela from XML file")
    void parseParcelaElement() throws IOException, ParserConfigurationException, SAXException, ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        boolean nespravny = false;

        Element element = ElementParser.getElement(29);

        IntegerParser integerParser = new IntegerParser(element, PREFIX);
        LongParser longParser = new LongParser(element, PREFIX);
        DateParser dateParser = new DateParser(element, PREFIX);
        DefinicniBodParser definicniBodParser = new DefinicniBodParser(element);
        BonitovaneDilyParser bonitovaneDilyParser = new BonitovaneDilyParser(element, PREFIX);
        ZpusobOchranyPozemkuParser zpOchrPozemkuParser = new ZpusobOchranyPozemkuParser(element, PREFIX);
        HraniceParser hraniceParser = new HraniceParser(element);
        NespravnyUdajParser nespravnyUdajParser = new NespravnyUdajParser(element, PREFIX);

        Long id = longParser.parse("Id");
        Integer kmenoveCislo = integerParser.parse("KmenoveCislo");
        Integer pododdeleniCisla = integerParser.parse("PododdeleniCisla");
        Long vymeraParcely = longParser.parse("VymeraParcely");
        Integer druhCislovaniKod = integerParser.parse("DruhCislovaniKod");
        Integer druhPozemkuKod = integerParser.parse("DruhPozemkuKod");
        Integer kodKatastralniUzemi = integerParser.parse("kui:Kod");
        Date platiOd = dateParser.parse("PlatiOd");
        Date platiDo = dateParser.parse("PlatiDo");
        Long idTransakce = longParser.parse("IdTransakce");
        Long rizeniId = longParser.parse("RizeniId");
        List<BonitovanyDil> bonitovaneDily = bonitovaneDilyParser.parse("BonitovaneDily");
        ZpusobOchranyPozemku zpusobOchranyPozemku = zpOchrPozemkuParser.parse("ZpusobyOchranyPozemku");
        GeoJsonPoint definicniBod = definicniBodParser.parse("pos");
        GeoJsonPolygon hranice = hraniceParser.parse("posList");
        NespravnyUdaj nespravnyUdaj = nespravnyUdajParser.parse("NespravneUdaje");

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
