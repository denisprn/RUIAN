package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.Ulice;
import com.bp.RUIAN.utils.Prefixes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.elasticsearch.core.geo.GeoJsonLineString;
import org.springframework.data.elasticsearch.core.geo.GeoJsonMultiLineString;
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

public class UliceParserTest {
    private static final String PREFIX = Prefixes.ULICE_PREFIX;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    @DisplayName("Should parse Ulice from XML file")
    void parseUliceElement() throws IOException, ParserConfigurationException, SAXException, ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        boolean nespravny = false;

        Element element = ElementParser.getElement(27);

        IntegerParser integerParser = new IntegerParser(element, PREFIX);
        LongParser longParser = new LongParser(element, PREFIX);
        StringParser stringParser = new StringParser(element, PREFIX);
        DateParser dateParser = new DateParser(element, PREFIX);
        DefinicniCaraParser definicniCaraParser = new DefinicniCaraParser(element);
        NespravnyUdajParser nespravnyUdajParser = new NespravnyUdajParser(element, PREFIX);

        Integer kod = integerParser.parse("Kod");
        String nazev = stringParser.parse("Nazev");
        Integer kodObce = integerParser.parse("obi:Kod");
        Date platiOd = dateParser.parse("PlatiOd");
        Date platiDo = dateParser.parse("PlatiDo");
        Long idTransakce = longParser.parse("IdTransakce");
        Long globalniIdNavrhuZmeny = longParser.parse("GlobalniIdNavrhuZmeny");
        GeoJsonMultiLineString definicniCara = definicniCaraParser.parse("posList");
        NespravnyUdaj nespravnyUdaj = nespravnyUdajParser.parse("NespravneUdaje");

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        List<Point> points1 = new ArrayList<>();
        points1.add(new Point(-515244.88, -1166620.04));
        points1.add(new Point(-515262.38, -1166596.12));
        points1.add(new Point(-515278.17, -1166577.53));

        List<Point> points2 = new ArrayList<>();
        points2.add(new Point(-515183.48, -1166654.22));
        points2.add(new Point(-515146.99, -1166665.95));
        points2.add(new Point(-515122.60, -1166672.64));

        List<GeoJsonLineString> definicniCaraList = new ArrayList<>();
        definicniCaraList.add(GeoJsonLineString.of(points1));
        definicniCaraList.add(GeoJsonLineString.of(points2));

        Ulice uliceExpected = new Ulice(644196, "4. května", false, 500011,
                sdf.parse("2018-01-22T00:00:00"), null, 2277057L, 1672622L,
                GeoJsonMultiLineString.of(definicniCaraList),null);

        Ulice uliceActual = new Ulice(kod, nazev, nespravny, kodObce,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, definicniCara, nespravnyUdaj);

        assertEquals(uliceExpected, uliceActual);
    }
}
