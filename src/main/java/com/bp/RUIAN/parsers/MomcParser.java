package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.MluvnickeCharakteristiky;
import com.bp.RUIAN.entities.Momc;
import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.Stat;
import com.bp.RUIAN.services.EsService;
import com.bp.RUIAN.utils.Prefixes;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Date;

public class MomcParser extends AbstractSaveParser{
    private static final String PREFIX = Prefixes.MOMC_PREFIX;

    public MomcParser(EsService esService, Element element) {
        super(esService, element, PREFIX);
    }

    @Override
    public void parse() throws ParseException {
        boolean nespravny = false;
        Integer kod = attributeParser.getKod();
        String nazev = attributeParser.getNazev();
        Integer kodMop = attributeParser.getKodMop();
        Integer kodObce = attributeParser.getKodObce();
        Integer kodSpravniObvod = attributeParser.getKodSpravniObvod();
        Date platiOd = attributeParser.getPlatiOd();
        Date platiDo = attributeParser.getPlatiDo();
        Long idTransakce = attributeParser.getIdTransakce();
        Long globalniIdNavrhuZmeny = attributeParser.getGlobalniIdNavrhuZmeny();
        MluvnickeCharakteristiky mluvChar = attributeParser.getMluvChar();
        String vlajkaText = attributeParser.getVlajkaText();
        String vlajkaObrazek = attributeParser.getVlajkaObrazek();
        String znakText = attributeParser.getZnakText();
        String znakObrazek = attributeParser.getZnakObrazek();
        GeoJsonPoint definicniBod = attributeParser.getDefinicniBod();
        GeoJsonPolygon hranice = attributeParser.getHranice();
        NespravnyUdaj nespravnyUdaj = attributeParser.getNespravneUdaje();

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = attributeParser.getDatumVzniku();

        Momc momc = new Momc(kod, nazev, nespravny, kodMop, kodObce, kodSpravniObvod, platiOd, platiDo,
                idTransakce, globalniIdNavrhuZmeny, vlajkaText, vlajkaObrazek,
                mluvChar, znakText, znakObrazek, definicniBod, hranice, nespravnyUdaj, datumVzniku);
        esService.saveMomc(momc);
    }
}
