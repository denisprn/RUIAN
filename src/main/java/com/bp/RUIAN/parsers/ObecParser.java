package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.MluvnickeCharakteristiky;
import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.Obec;
import com.bp.RUIAN.utils.Prefixes;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Date;

public class ObecParser implements RecordParser<Obec> {
    private static final String PREFIX = Prefixes.OBEC_PREFIX;
    private final IntegerParser integerParser;
    private final LongParser longParser;
    private final StringParser stringParser;
    private final DateParser dateParser;
    private final DefinicniBodParser definicniBodParser;
    private final HraniceParser hraniceParser;
    private final MluvnickeCharakteristikyParser mluvCharParser;
    private final NespravnyUdajParser nespravnyUdajParser;

    public ObecParser(Element element) {
        this.integerParser = new IntegerParser(element, PREFIX);
        this.longParser = new LongParser(element, PREFIX);
        this.stringParser = new StringParser(element, PREFIX);
        this.dateParser = new DateParser(element, PREFIX);
        this.definicniBodParser = new DefinicniBodParser(element);
        this.hraniceParser = new HraniceParser(element);
        this.mluvCharParser = new MluvnickeCharakteristikyParser(element, PREFIX);
        this.nespravnyUdajParser = new NespravnyUdajParser(element, PREFIX);
    }

    @Override
    public Obec parse() throws ParseException {
        boolean nespravny = false;
        Integer kod = integerParser.parse("Kod");
        String nazev = stringParser.parse("Nazev");
        Integer statusKod = integerParser.parse("StatusKod");
        Integer kodOkresu = integerParser.parse("oki:Kod");
        Integer kodPou = integerParser.parse("pui:Kod");
        Date platiOd = dateParser.parse("PlatiOd");
        Date platiDo = dateParser.parse("PlatiDo");
        Long idTransakce = longParser.parse("IdTransakce");
        Long globalniIdNavrhuZmeny = longParser.parse("GlobalniIdNavrhuZmeny");
        MluvnickeCharakteristiky mluvChar = mluvCharParser.parse("MluvnickeCharakteristiky");
        String vlajkaText = stringParser.parse("VlajkaText");
        String vlajkaObrazek = stringParser.parse("VlajkaObrazek");
        String znakText = stringParser.parse("ZnakText");
        String znakObrazek = stringParser.parse("ZnakObrazek");
        Integer cleneniSMRozsahKod = integerParser.parse("CleneniSMRozsahKod");
        Integer cleneniSMTypKod = integerParser.parse("CleneniSMTypKod");
        String nutsLau = stringParser.parse("NutsLau");
        GeoJsonPoint definicniBod = definicniBodParser.parse("pos");
        GeoJsonPolygon hranice = hraniceParser.parse("posList");
        NespravnyUdaj nespravnyUdaj = nespravnyUdajParser.parse("NespravneUdaje");

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = dateParser.parse("DatumVzniku");

        return new Obec(kod, nazev, nespravny, statusKod, kodOkresu, kodPou, platiOd, platiDo, idTransakce,
                globalniIdNavrhuZmeny, mluvChar, vlajkaText, vlajkaObrazek, znakText, znakObrazek,
                cleneniSMRozsahKod, cleneniSMTypKod, nutsLau, definicniBod, hranice, nespravnyUdaj, datumVzniku);
    }
}
