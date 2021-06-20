package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.Ulice;
import com.bp.RUIAN.utils.Prefixes;
import org.springframework.data.elasticsearch.core.geo.GeoJsonMultiLineString;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Date;

public class UliceParser implements RecordParser<Ulice> {
    private static final String PREFIX = Prefixes.ULICE_PREFIX;
    private final IntegerParser integerParser;
    private final LongParser longParser;
    private final StringParser stringParser;
    private final DateParser dateParser;
    private final DefinicniCaraParser definicniCaraParser;
    private final NespravnyUdajParser nespravnyUdajParser;

    public UliceParser(Element element) {
        this.integerParser = new IntegerParser(element, PREFIX);
        this.longParser = new LongParser(element, PREFIX);
        this.stringParser = new StringParser(element, PREFIX);
        this.dateParser = new DateParser(element, PREFIX);
        this.definicniCaraParser = new DefinicniCaraParser(element);
        this.nespravnyUdajParser = new NespravnyUdajParser(element, PREFIX);
    }

    @Override
    public Ulice parse() throws ParseException {
        boolean nespravny = false;
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

        return new Ulice(kod, nazev, nespravny, kodObce,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, definicniCara, nespravnyUdaj);
    }
}
