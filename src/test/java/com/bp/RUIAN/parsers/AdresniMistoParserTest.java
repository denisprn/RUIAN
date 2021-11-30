/*
package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.AdresniMisto;
import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.utils.Prefixes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.geo.Point;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdresniMistoParserTest {
    private static final String PREFIX = Prefixes.ADRESNI_MISTO_PREFIX;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    @DisplayName("Should parse AdresniMisto from XML file")
    void parseAdresniMistoElement() throws ParseException, IOException,
            ParserConfigurationException, SAXException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        boolean nespravny = false;

        Element element = ElementParser.getElement(33);

        IntegerParser integerParser = new IntegerParser(element, PREFIX);
        LongParser longParser = new LongParser(element, PREFIX);
        StringParser stringParser = new StringParser(element, PREFIX);
        DateParser dateParser = new DateParser(element, PREFIX);
        DefinicniBodParser definicniBodParser = new DefinicniBodParser(element);
        NespravnyUdajParser nespravnyUdajParser = new NespravnyUdajParser(element, PREFIX);

        Integer kod = integerParser.parse("Kod");
        String cisloDomovni = stringParser.parse("CisloDomovni");
        String cisloOrientacni = stringParser.parse("CisloOrientacni");
        String cisloOrientacniPismeno = stringParser.
                parse("CisloOrientacniPismeno");
        String psc = stringParser.parse("Psc");
        Integer stavebniObjektKod = integerParser.parse("soi:Kod");
        Integer uliceKod = integerParser.parse("uli:Kod");
        Integer voKod = integerParser.parse("VOKod");
        Date platiOd = dateParser.parse("PlatiOd");
        Date platiDo = dateParser.parse("PlatiDo");
        Long idTransakce = longParser.parse("IdTransakce");
        Long globalniIdNavrhuZmeny = longParser.parse("GlobalniIdNavrhuZmeny");
        GeoJsonPoint definicniBod = definicniBodParser.parse("pos");
        NespravnyUdaj nespravnyUdaj = nespravnyUdajParser.parse("NespravneUdaje");

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        AdresniMisto adresniMistoExpected = new AdresniMisto(72850124, false, "668", "55",
                "A", "76311", 78171482, 647268, 42877,
                sdf.parse("2014-06-26T00:00:00"), null,
                630728L, 594036L, GeoJsonPoint.of(new Point(-516005.93, -1168276.78)), null);

        AdresniMisto adresniMistoActual = new AdresniMisto(kod, nespravny, cisloDomovni, cisloOrientacni,
                cisloOrientacniPismeno, psc, stavebniObjektKod, uliceKod, voKod, platiOd, platiDo,
                idTransakce, globalniIdNavrhuZmeny, definicniBod, nespravnyUdaj);

        assertEquals(adresniMistoExpected, adresniMistoActual);
    }
}
*/
