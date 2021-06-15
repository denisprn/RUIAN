package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.KatastralniUzemi;
import com.bp.RUIAN.entities.MluvnickeCharakteristiky;
import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.Stat;
import com.bp.RUIAN.services.EsService;
import com.bp.RUIAN.utils.Prefixes;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Date;

public class KatastralniUzemiParser extends AbstractSaveParser {
    private static final String PREFIX = Prefixes.KATASTRALNI_UZEMI_PREFIX;

    public KatastralniUzemiParser(EsService esService, Element element) {
        super(esService, element, PREFIX);
    }

    @Override
    public void parse() throws ParseException {
        boolean nespravny = false;
        Integer kod = attributeParser.getKod();
        String nazev = attributeParser.getNazev();
        Boolean existujeDigitalniMapa = attributeParser.getExistujeDigitalniMapa();
        Integer kodObce = attributeParser.getKodObce();
        Date platiOd = attributeParser.getPlatiOd();
        Date platiDo = attributeParser.getPlatiDo();
        Long idTransakce = attributeParser.getIdTransakce();
        Long globalniIdNavrhuZmeny = attributeParser.getGlobalniIdNavrhuZmeny();
        Long rizeniId = attributeParser.getIdRizeni();
        MluvnickeCharakteristiky mluvChar = attributeParser.getMluvChar();
        GeoJsonPoint definicniBod = attributeParser.getDefinicniBod();
        GeoJsonPolygon hranice = attributeParser.getHranice();
        NespravnyUdaj nespravnyUdaj = attributeParser.getNespravneUdaje();

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Date datumVzniku = attributeParser.getDatumVzniku();

        KatastralniUzemi katastralniUzemi = new KatastralniUzemi(kod, nazev, nespravny,
                existujeDigitalniMapa, kodObce, platiOd, platiDo, idTransakce, globalniIdNavrhuZmeny,
                rizeniId, mluvChar, definicniBod, hranice, nespravnyUdaj, datumVzniku);
        esService.saveKatastralniUzemi(katastralniUzemi);
    }
}
