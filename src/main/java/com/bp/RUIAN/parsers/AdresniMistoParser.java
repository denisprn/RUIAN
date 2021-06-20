package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.AdresniMisto;
import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.utils.Prefixes;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Date;

public class AdresniMistoParser implements RecordParser<AdresniMisto> {
    private static final String PREFIX = Prefixes.ADRESNI_MISTO_PREFIX;
    private final IntegerParser integerParser;
    private final LongParser longParser;
    private final StringParser stringParser;
    private final DateParser dateParser;
    private final DefinicniBodParser definicniBodParser;
    private final NespravnyUdajParser nespravnyUdajParser;

    public AdresniMistoParser(Element element) {
        this.integerParser = new IntegerParser(element, PREFIX);
        this.longParser = new LongParser(element, PREFIX);
        this.stringParser = new StringParser(element, PREFIX);
        this.dateParser = new DateParser(element, PREFIX);
        this.definicniBodParser = new DefinicniBodParser(element);
        this.nespravnyUdajParser = new NespravnyUdajParser(element, PREFIX);
    }

    @Override
    public AdresniMisto parse() throws ParseException {
        boolean nespravny = false;
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

        return new AdresniMisto(kod, nespravny, cisloDomovni, cisloOrientacni,
                cisloOrientacniPismeno, psc, stavebniObjektKod, uliceKod, voKod, platiOd, platiDo,
                idTransakce, globalniIdNavrhuZmeny, definicniBod, nespravnyUdaj);
    }
}
