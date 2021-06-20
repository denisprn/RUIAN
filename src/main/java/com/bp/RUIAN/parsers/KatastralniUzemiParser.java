package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.KatastralniUzemi;
import com.bp.RUIAN.entities.MluvnickeCharakteristiky;
import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.utils.Prefixes;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Date;

public class KatastralniUzemiParser implements RecordParser<KatastralniUzemi> {
    private static final String PREFIX = Prefixes.KATASTRALNI_UZEMI_PREFIX;
    private final IntegerParser integerParser;
    private final LongParser longParser;
    private final StringParser stringParser;
    private final BooleanParser booleanParser;
    private final DateParser dateParser;
    private final DefinicniBodParser definicniBodParser;
    private final HraniceParser hraniceParser;
    private final MluvnickeCharakteristikyParser mluvCharParser;
    private final NespravnyUdajParser nespravnyUdajParser;

    public KatastralniUzemiParser(Element element) {
        this.integerParser = new IntegerParser(element, PREFIX);
        this.longParser = new LongParser(element, PREFIX);
        this.stringParser = new StringParser(element, PREFIX);
        this.booleanParser = new BooleanParser(element, PREFIX);
        this.dateParser = new DateParser(element, PREFIX);
        this.definicniBodParser = new DefinicniBodParser(element);
        this.hraniceParser = new HraniceParser(element);
        this.mluvCharParser = new MluvnickeCharakteristikyParser(element, PREFIX);
        this.nespravnyUdajParser = new NespravnyUdajParser(element, PREFIX);
    }

    @Override
    public KatastralniUzemi parse() throws ParseException {
        boolean nespravny = false;
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

        return new KatastralniUzemi(kod, nazev, nespravny,
                existujeDigitalniMapa, kodObce, platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny,
                rizeniId, mluvChar, definicniBod, hranice, nespravnyUdaj, datumVzniku);
    }
}
