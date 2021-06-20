package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.KatastralniUzemi;
import com.bp.RUIAN.entities.MluvnickeCharakteristiky;
import com.bp.RUIAN.entities.NespravnyUdaj;
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

public class KatastralniUzemiParserTest {
    private static final String PREFIX = Prefixes.KATASTRALNI_UZEMI_PREFIX;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    @DisplayName("Should parse KatastralniUzemi from XML file")
    void parseKatastralniUzemiElement() throws IOException, ParserConfigurationException, SAXException, ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        boolean nespravny = false;

        Element element = ElementParser.getElement(23);

        IntegerParser integerParser = new IntegerParser(element, PREFIX);
        LongParser longParser = new LongParser(element, PREFIX);
        StringParser stringParser = new StringParser(element, PREFIX);
        BooleanParser booleanParser = new BooleanParser(element, PREFIX);
        DateParser dateParser = new DateParser(element, PREFIX);
        DefinicniBodParser definicniBodParser = new DefinicniBodParser(element);
        HraniceParser hraniceParser = new HraniceParser(element);
        MluvnickeCharakteristikyParser mluvCharParser = new MluvnickeCharakteristikyParser(element, PREFIX);
        NespravnyUdajParser nespravnyUdajParser = new NespravnyUdajParser(element, PREFIX);

        Integer kod = integerParser.parse("Kod");
        String nazev = stringParser.parse("Nazev");
        Boolean existujeDigitalniMapa = booleanParser.parse("ExistujeDigitalniMapa");
        Integer kodObce = integerParser.parse("obi:Kod");
        Date platiOd = dateParser.parse("PlatiOd");
        Date platiDo = dateParser.parse("PlatiDo");
        Long idTransakce = longParser.parse("IdTransakce");
        Long globalniIdNavrhuZmeny = longParser.parse("GlobalniIdNavrhuZmeny");
        Long rizeniId = longParser.parse("RizeniId");
        MluvnickeCharakteristiky mluvChar = mluvCharParser.parse("MluvnickeCharakteristiky");
        GeoJsonPoint definicniBod = definicniBodParser.parse("pos");
        GeoJsonPolygon hranice = hraniceParser.parse("posList");
        NespravnyUdaj nespravnyUdaj = nespravnyUdajParser.parse("NespravneUdaje");

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = dateParser.parse("DatumVzniku");

        List<Point> points = new ArrayList<>();
        points.add(new Point(-753388.46, -1177300.38));
        points.add(new Point(-753389.46, -1177301.38));
        points.add(new Point(-753299.33, -1177323.05));
        points.add(new Point(-753145.06, -1177338.88));

        KatastralniUzemi katastralniUzemiExpected = new KatastralniUzemi(668761, "Pašinovice", false,
                true, 535877, sdf.parse("2019-06-13T00:00:00"), null, 2902615L,
                0L, 64903676010L,
                new MluvnickeCharakteristiky("Pašinovic", "Pašinovicím", "Pašinovice", "Pašinovicích", "Pašinovicemi"),
                GeoJsonPoint.of(new Point(-753602.46, -1177927.54)),
                GeoJsonPolygon.of(points), null, sdf.parse("1923-01-01T00:00:00"));

        KatastralniUzemi katastralniUzemiActual = new KatastralniUzemi(kod, nazev, nespravny,
                existujeDigitalniMapa, kodObce, platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny,
                rizeniId, mluvChar, definicniBod, hranice, nespravnyUdaj, datumVzniku);

        assertEquals(katastralniUzemiExpected, katastralniUzemiActual);
    }
}
