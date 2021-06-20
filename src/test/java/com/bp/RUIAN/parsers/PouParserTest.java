package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.Pou;
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

public class PouParserTest {
    private static final String PREFIX = Prefixes.POU_PREFIX;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    @DisplayName("Should parse Pou from XML file")
    void parsePouElement() throws IOException, ParserConfigurationException, SAXException, ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        boolean nespravny = false;

        Element element = ElementParser.getElement(11);

        IntegerParser integerParser = new IntegerParser(element, PREFIX);
        LongParser longParser = new LongParser(element, PREFIX);
        StringParser stringParser = new StringParser(element, PREFIX);
        DateParser dateParser = new DateParser(element, PREFIX);
        DefinicniBodParser definicniBodParser = new DefinicniBodParser(element);
        HraniceParser hraniceParser = new HraniceParser(element);
        NespravnyUdajParser nespravnyUdajParser = new NespravnyUdajParser(element, PREFIX);

        Integer kod = integerParser.parse("Kod");
        String nazev = stringParser.parse("Nazev");
        Integer spravniObecKod = integerParser.parse("SpravniObecKod");
        Integer kodOrp = integerParser.parse("opi:Kod");
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
        points.add(new Point(-656589.83, -1067506.21));
        points.add(new Point(-656590.83, -1067507.21));
        points.add(new Point(-656450.63, -1067680.89));
        points.add(new Point(-656345.11, -1067679.46));

        Pou pouExpected = new Pou(2119, "Heřmanův Městec", false, 571385, 981,
                sdf.parse("2017-10-24T00:00:00"), null, 2089492L,
                1534663L, GeoJsonPoint.of(new Point(-656419.00, -1069916.00)),
                GeoJsonPolygon.of(points),
                null, sdf.parse("1990-11-24T00:00:00"));

        Pou pouActual = new Pou(kod, nazev, nespravny, spravniObecKod, kodOrp,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, definicniBod,
                hranice, nespravnyUdaj, datumVzniku);

        assertEquals(pouExpected, pouActual);
    }
}
