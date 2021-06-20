package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.SpravniObvod;
import com.bp.RUIAN.utils.Prefixes;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Date;

public class SpravniObvodParser implements RecordParser<SpravniObvod> {
    private static final String PREFIX = Prefixes.SPRAVNI_OBVOD_PREFIX;
    private final IntegerParser integerParser;
    private final LongParser longParser;
    private final StringParser stringParser;
    private final DateParser dateParser;
    private final DefinicniBodParser definicniBodParser;
    private final HraniceParser hraniceParser;
    private final NespravnyUdajParser nespravnyUdajParser;

    public SpravniObvodParser(Element element) {
        this.integerParser = new IntegerParser(element, PREFIX);
        this.longParser = new LongParser(element, PREFIX);
        this.stringParser = new StringParser(element, PREFIX);
        this.dateParser = new DateParser(element, PREFIX);
        this.definicniBodParser = new DefinicniBodParser(element);
        this.hraniceParser = new HraniceParser(element);
        this.nespravnyUdajParser = new NespravnyUdajParser(element, PREFIX);
    }

    @Override
    public SpravniObvod parse() throws ParseException {
        boolean nespravny = false;
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

        return new SpravniObvod(kod, nazev, nespravny, spravniMomcKod, kodObce,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, definicniBod,
                hranice, nespravnyUdaj, datumVzniku);
    }
}
