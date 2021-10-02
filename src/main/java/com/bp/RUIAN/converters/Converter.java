package com.bp.RUIAN.converters;

import com.bp.RUIAN.entities.AdresniMisto;
import com.bp.RUIAN.entities.Obec;
import com.bp.RUIAN.entities.Ulice;
import com.bp.RUIAN.entities.Unit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.search.SearchHit;

import java.util.Map;

public class Converter {
    public Unit convertHitsToUnit(
            SearchHit obecHit, SearchHit uliceHit, SearchHit adresniMistoHit) {
        ObjectMapper mapper = new ObjectMapper();

        Obec obec = null;
        Ulice ulice = null;
        AdresniMisto adresniMisto = null;

        if (obecHit != null) {
            Map<String, Object> obecMap = obecHit.getSourceAsMap();
            obec = mapper.convertValue(obecMap, Obec.class);
        }

        if (uliceHit != null) {
            Map<String, Object> uliceMap = uliceHit.getSourceAsMap();
            ulice = mapper.convertValue(uliceMap, Ulice.class);
        }

        if (adresniMistoHit != null) {
            Map<String, Object> amMap = adresniMistoHit.getSourceAsMap();
            adresniMisto = mapper.convertValue(amMap, AdresniMisto.class);
        }

        return new Unit(adresniMisto, ulice, obec);
    }
}
