package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.SpravniObvod;
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

public class SpravniObvodParserTest {
    private static final String PREFIX = Prefixes.SPRAVNI_OBVOD_PREFIX;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    @DisplayName("Should parse SpravniObvod from XML file")
    void parseSpravniObvodElement() throws IOException, ParserConfigurationException, SAXException, ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        boolean nespravny = false;

        Element element = ElementParser.getElement(15);

        IntegerParser integerParser = new IntegerParser(element, PREFIX);
        LongParser longParser = new LongParser(element, PREFIX);
        StringParser stringParser = new StringParser(element, PREFIX);
        DateParser dateParser = new DateParser(element, PREFIX);
        DefinicniBodParser definicniBodParser = new DefinicniBodParser(element);
        HraniceParser hraniceParser = new HraniceParser(element);
        NespravnyUdajParser nespravnyUdajParser = new NespravnyUdajParser(element, PREFIX);

        Integer kod = integerParser.parse("Kod");
        String nazev = stringParser.parse("Nazev");
        Integer spravniMomcKod = integerParser.parse("SpravniMomcKod");
        Integer kodObce = integerParser.parse("obi:Kod");
        Date platiOd = dateParser.parse("PlatiOd");
        Date platiDo = dateParser.parse("PlatiDo");
        Long idTransakce = longParser.parse("IdTransakce");
        Long globalniIdNavrhuZmeny = longParser.parse("GlobalniIdNavrhuZmeny");
        GeoJsonPoint definicniBod = definicniBodParser.parse("pos");
        GeoJsonPolygon hranice = hraniceParser.parse("posList");
        NespravnyUdaj nespravnyUdaj = nespravnyUdajParser.parse("NespravneUdaje");

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = dateParser.parse("DatumVzniku");

        List<Point> points = new ArrayList<>();
        points.add(new Point(-743272.11, -1042309.04));
        points.add(new Point(-743273.11, -1042310.04));
        points.add(new Point(-743242.23, -1042305.23));
        points.add(new Point(-743203.74, -1042302.40));

        SpravniObvod spravniObvodExpected = new SpravniObvod(19, "Praha 1", false, 500054,
                554782, sdf.parse("2013-11-13T00:00:00"), null,
                393750L, 452737L,
                GeoJsonPoint.of(new Point(-742600.00, -1043000.00)),
                GeoJsonPolygon.of(points),null, null);

        SpravniObvod spravniObvodActual = new SpravniObvod(kod, nazev, nespravny, spravniMomcKod, kodObce, platiOd, platiDo,
                idTransakce, globalniIdNavrhuZmeny, definicniBod, hranice, nespravnyUdaj, datumVzniku);

        assertEquals(spravniObvodExpected, spravniObvodActual);
    }
}
