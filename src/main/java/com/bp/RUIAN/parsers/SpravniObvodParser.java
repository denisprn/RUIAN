package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.SpravniObvod;
import com.bp.RUIAN.entities.Stat;
import com.bp.RUIAN.services.EsService;
import com.bp.RUIAN.utils.Prefixes;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Date;

public class SpravniObvodParser extends AbstractSaveParser {
    private static final String PREFIX = Prefixes.SPRAVNI_OBVOD_PREFIX;

    public SpravniObvodParser(EsService esService, Element element) {
        super(esService, element, PREFIX);
    }

    @Override
    public void parse() throws ParseException {
        String prefix = "spi";
        boolean nespravny = false;
        Integer kod = attributeParser.getKod();
        String nazev = attributeParser.getNazev();
        Integer spravniMomcKod = attributeParser.getSpravniMomcKod();
        Integer kodObce = attributeParser.getKodObce();
        Date platiOd = attributeParser.getPlatiOd();
        Date platiDo = attributeParser.getPlatiDo();
        Long idTransakce = attributeParser.getIdTransakce();
        Long globalniIdNavrhuZmeny = attributeParser.getGlobalniIdNavrhuZmeny();
        GeoJsonPoint definicniBod = attributeParser.getDefinicniBod();
        GeoJsonPolygon hranice = attributeParser.getHranice();
        NespravnyUdaj nespravnyUdaj = attributeParser.getNespravneUdaje();

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = attributeParser.getDatumVzniku();

        SpravniObvod spravniObvod = new SpravniObvod(kod, nazev, nespravny, spravniMomcKod, kodObce,
                platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny, definicniBod,
                hranice, nespravnyUdaj, datumVzniku);
        esService.saveSO(spravniObvod);
    }
}
