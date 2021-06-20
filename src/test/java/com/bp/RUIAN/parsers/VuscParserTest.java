package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.Vusc;
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

public class VuscParserTest {
    private static final String PREFIX = Prefixes.VUSC_PREFIX;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    @DisplayName("Should parse Vusc from XML file")
    void parseVuscElement() throws ParserConfigurationException, IOException, SAXException, ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        boolean nespravny = false;

        Element element = ElementParser.getElement(5);

        IntegerParser integerParser = new IntegerParser(element, PREFIX);
        LongParser longParser = new LongParser(element, PREFIX);
        StringParser stringParser = new StringParser(element, PREFIX);
        DateParser dateParser = new DateParser(element, PREFIX);
        DefinicniBodParser definicniBodParser = new DefinicniBodParser(element);
        HraniceParser hraniceParser = new HraniceParser(element);
        NespravnyUdajParser nespravnyUdajParser = new NespravnyUdajParser(element, PREFIX);

        Integer kod = integerParser.parse("Kod");
        String nazev = stringParser.parse("Nazev");
        Integer kodRs = integerParser.parse("rsi:Kod");
        Date platiOd = dateParser.parse("PlatiOd");
        Date platiDo = dateParser.parse("PlatiDo");
        Long idTransakce = longParser.parse("IdTransakce");
        Long globalniIdNavrhuZmeny = longParser.parse("GlobalniIdNavrhuZmeny");
        String nutsLau = stringParser.parse("NutsLau");
        GeoJsonPoint definicniBod = definicniBodParser.parse("pos");
        GeoJsonPolygon hranice = hraniceParser.parse("posList");
        NespravnyUdaj nespravnyUdaj = nespravnyUdajParser.parse("NespravneUdaje");

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = dateParser.parse("DatumVzniku");

        List<Point> points = new ArrayList<>();
        points.add(new Point(-733566.86, -1053251.22));
        points.add(new Point(-733567.86, -1053252.22));
        points.add(new Point(-733912.03, -1052746.38));
        points.add(new Point(-734508.61, -1052601.41));

        Vusc vuscExpected = new Vusc(19, "Hlavní město Praha", false, 19,
                sdf.parse("2016-11-23T00:00:00"), null, 1572842L,
                1173831L, "CZ010", GeoJsonPoint.of(new Point(-743100.00, -1043300.00)),
                GeoJsonPolygon.of(points), null, sdf.parse("2000-01-01T00:00:00"));

        Vusc vuscActual = new Vusc(kod, nazev, nespravny, kodRs,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, nutsLau, definicniBod,
                hranice, nespravnyUdaj, datumVzniku);

        assertEquals(vuscExpected, vuscActual);
    }
}
