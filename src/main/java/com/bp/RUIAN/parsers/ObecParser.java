package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.MluvnickeCharakteristiky;
import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.Obec;
import com.bp.RUIAN.entities.Stat;
import com.bp.RUIAN.services.EsService;
import com.bp.RUIAN.utils.Prefixes;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Date;

public class ObecParser extends AbstractSaveParser {
    private static final String PREFIX = Prefixes.OBEC_PREFIX;

    public ObecParser(EsService esService, Element element) {
        super(esService, element, PREFIX);
    }

    @Override
    public void parse() throws ParseException {
        boolean nespravny = false;
        Integer kod = attributeParser.getKod();
        String nazev = attributeParser.getNazev();
        Integer statusKod = attributeParser.getStatusKod();
        Integer kodOkresu = attributeParser.getKodOkresu();
        Integer kodPou = attributeParser.getKodPou();
        Date platiOd = attributeParser.getPlatiOd();
        Date platiDo = attributeParser.getPlatiDo();
        Long idTransakce = attributeParser.getIdTransakce();
        Long globalniIdNavrhuZmeny = attributeParser.getGlobalniIdNavrhuZmeny();
        MluvnickeCharakteristiky mluvChar = attributeParser.getMluvChar();
        String vlajkaText = attributeParser.getVlajkaText();
        String vlajkaObrazek = attributeParser.getVlajkaObrazek();
        String znakText = attributeParser.getZnakText();
        String znakObrazek = attributeParser.getZnakObrazek();
        Integer cleneniSMRozsahKod = attributeParser.getCleneniSMRozsahKod();
        Integer cleneniSMTypKod = attributeParser.getCleneniSMTypKod();
        String nutsLau = attributeParser.getNutsLau();
        GeoJsonPoint definicniBod = attributeParser.getDefinicniBod();
        GeoJsonPolygon hranice = attributeParser.getHranice();
        NespravnyUdaj nespravnyUdaj = attributeParser.getNespravneUdaje();

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = attributeParser.getDatumVzniku();

        Obec obec = new Obec(kod, nazev, nespravny, statusKod, kodOkresu, kodPou, platiOd, platiDo, idTransakce,
                globalniIdNavrhuZmeny, mluvChar, vlajkaText, vlajkaObrazek, znakText, znakObrazek,
                cleneniSMRozsahKod, cleneniSMTypKod, nutsLau, definicniBod, hranice, nespravnyUdaj, datumVzniku);
        esService.saveObec(obec);
    }
}
