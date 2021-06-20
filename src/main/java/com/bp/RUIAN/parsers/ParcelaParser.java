package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.*;
import com.bp.RUIAN.utils.Prefixes;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class ParcelaParser implements RecordParser<Parcela> {
    private static final String PREFIX = Prefixes.PARCELA_PREFIX;
    private final IntegerParser integerParser;
    private final LongParser longParser;
    private final DateParser dateParser;
    private final BonitovaneDilyParser bonitovaneDilyParser;
    private final DefinicniBodParser definicniBodParser;
    private final HraniceParser hraniceParser;
    private final ZpusobOchranyPozemkuParser zpOchrPozemkuParser;
    private final NespravnyUdajParser nespravnyUdajParser;

    public ParcelaParser(Element element) {
        this.integerParser = new IntegerParser(element, PREFIX);
        this.longParser = new LongParser(element, PREFIX);
        this.dateParser = new DateParser(element, PREFIX);
        this.bonitovaneDilyParser = new BonitovaneDilyParser(element, PREFIX);
        this.definicniBodParser = new DefinicniBodParser(element);
        this.hraniceParser = new HraniceParser(element);
        this.zpOchrPozemkuParser = new ZpusobOchranyPozemkuParser(element, PREFIX);
        this.nespravnyUdajParser = new NespravnyUdajParser(element, PREFIX);
    }

    @Override
    public Parcela parse() throws ParseException {
        boolean nespravny = false;
        Long id = longParser.parse("Id");
        Integer kmenoveCislo = integerParser.parse("KmenoveCislo");
        Integer pododdeleniCisla = integerParser.parse("PododdeleniCisla");
        Long vymeraParcely = longParser.parse("VymeraParcely");
        Integer druhCislovaniKod = integerParser.parse("DruhCislovaniKod");
        Integer druhPozemkuKod = integerParser.parse("DruhPozemkuKod");
        Integer kodKatastralniUzemi = integerParser.parse("kui:Kod");
        Date platiOd = dateParser.parse("PlatiOd");
        Date platiDo = dateParser.parse("PlatiDo");
        Long idTransakce = longParser.parse("IdTransakce");
        Long rizeniId = longParser.parse("RizeniId");
        List<BonitovanyDil> bonitovaneDily = bonitovaneDilyParser.parse("BonitovaneDily");
        ZpusobOchranyPozemku zpusobOchranyPozemku = zpOchrPozemkuParser.parse("ZpusobyOchranyPozemku");
        GeoJsonPoint definicniBod = definicniBodParser.parse("pos");
        GeoJsonPolygon hranice = hraniceParser.parse("posList");
        NespravnyUdaj nespravnyUdaj = nespravnyUdajParser.parse("NespravneUdaje");

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        return new Parcela(id, nespravny, kmenoveCislo, pododdeleniCisla, vymeraParcely,
                druhCislovaniKod, druhPozemkuKod, kodKatastralniUzemi, platiOd, platiDo, idTransakce,
                rizeniId, bonitovaneDily, zpusobOchranyPozemku, definicniBod, hranice, nespravnyUdaj);
    }
}
