package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.MluvnickeCharakteristiky;
import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.Stat;
import com.bp.RUIAN.entities.Zsj;
import com.bp.RUIAN.services.EsService;
import com.bp.RUIAN.utils.Prefixes;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Date;

public class ZsjParser extends AbstractSaveParser {
    private static final String PREFIX = Prefixes.ZSJ_PREFIX;

    public ZsjParser(EsService esService, Element element) {
        super(esService, element, PREFIX);
    }

    @Override
    public void parse() throws ParseException {
        boolean nespravny = false;
        Integer kod = attributeParser.getKod();
        String nazev = attributeParser.getNazev();
        Integer kodKatastralniUzemi = attributeParser.getKodKatastralniUzemi();
        Date platiOd = attributeParser.getPlatiOd();
        Date platiDo = attributeParser.getPlatiDo();
        Long idTransakce = attributeParser.getIdTransakce();
        Long globalniIdNavrhuZmeny = attributeParser.getGlobalniIdNavrhuZmeny();
        MluvnickeCharakteristiky mluvChar = attributeParser.getMluvChar();
        Long vymera = attributeParser.getVymera();
        Integer charakterZsjKod = attributeParser.getCharakterZsjKod();
        GeoJsonPoint definicniBod = attributeParser.getDefinicniBod();
        GeoJsonPolygon hranice = attributeParser.getHranice();
        NespravnyUdaj nespravnyUdaj = attributeParser.getNespravneUdaje();

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = attributeParser.getDatumVzniku();

        Zsj zsj = new Zsj(kod, nazev, nespravny, kodKatastralniUzemi, platiOd, platiDo, idTransakce,
                globalniIdNavrhuZmeny, mluvChar, vymera, charakterZsjKod, definicniBod, hranice, nespravnyUdaj, datumVzniku);

        esService.saveZsj(zsj);
    }
}
