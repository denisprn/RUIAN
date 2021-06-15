package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.*;
import com.bp.RUIAN.services.EsService;
import com.bp.RUIAN.utils.Prefixes;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class ParcelaParser extends AbstractSaveParser {
    private static final String PREFIX = Prefixes.PARCELA_PREFIX;

    public ParcelaParser(EsService esService, Element element) {
        super(esService, element, PREFIX);
    }

    @Override
    public void parse() throws ParseException {
        boolean nespravny = false;
        Long id = attributeParser.getId();
        Integer kmenoveCislo = attributeParser.getKmenoveCislo();
        Integer pododdeleniCisla = attributeParser.getPoddodeleniCisla();
        Long vymeraParcely = attributeParser.getVymeraParcely();
        Integer druhCislovaniKod = attributeParser.getDruhCislovaniKod();
        Integer druhPozemkuKod = attributeParser.getDruhPozemkuKod();
        Integer kodKatastralniUzemi = attributeParser.getKodKatastralniUzemi();
        Date platiOd = attributeParser.getPlatiOd();
        Date platiDo = attributeParser.getPlatiDo();
        Long idTransakce = attributeParser.getIdTransakce();
        Long rizeniId = attributeParser.getIdRizeni();
        List<BonitovanyDil> bonitovaneDily = attributeParser.getBonitovaneDily();
        ZpusobOchranyPozemku zpusobOchranyPozemku = attributeParser.getZpusobOchranyPozemku();
        GeoJsonPoint definicniBod = attributeParser.getDefinicniBod();
        GeoJsonPolygon hranice = attributeParser.getHranice();
        NespravnyUdaj nespravnyUdaj = attributeParser.getNespravneUdaje();

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        Parcela parcela = new Parcela(id, nespravny, kmenoveCislo, pododdeleniCisla, vymeraParcely,
                druhCislovaniKod, druhPozemkuKod, kodKatastralniUzemi, platiOd, platiDo, idTransakce,
                rizeniId, bonitovaneDily, zpusobOchranyPozemku, definicniBod, hranice, nespravnyUdaj);

        esService.saveParcela(parcela);
    }
}
