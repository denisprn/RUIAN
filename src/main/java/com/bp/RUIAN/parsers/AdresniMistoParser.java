package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.AdresniMisto;
import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.RegionSoudrznosti;
import com.bp.RUIAN.entities.Stat;
import com.bp.RUIAN.services.EsService;
import com.bp.RUIAN.utils.Prefixes;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Date;

public class AdresniMistoParser extends AbstractSaveParser {
    private static final String PREFIX = Prefixes.ADRESNI_MISTO_PREFIX;

    public AdresniMistoParser(EsService esService, Element element) {
        super(esService, element, PREFIX);
    }

    @Override
    public void parse() throws ParseException {
        boolean nespravny = false;
        Integer kod = attributeParser.getKod();
        String cisloDomovni = attributeParser.getCisloDomovni();
        String cisloOrientacni = attributeParser.getCisloOrientacni();
        String cisloOrientacniPismeno = attributeParser.getCisloOrientacniPismeno();
        String psc = attributeParser.getPSC();
        Integer stavebniObjektKod = attributeParser.getKodStavebniObjekt();
        Integer uliceKod = attributeParser.getKodUlice();
        Integer voKod = attributeParser.getVOKod();
        Date platiOd = attributeParser.getPlatiOd();
        Date platiDo = attributeParser.getPlatiDo();
        Long idTransakce = attributeParser.getIdTransakce();
        Long globalniIdNavrhuZmeny = attributeParser.getGlobalniIdNavrhuZmeny();
        GeoJsonPoint definicniBod = attributeParser.getDefinicniBod();
        NespravnyUdaj nespravnyUdaj = attributeParser.getNespravneUdaje();

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        AdresniMisto adresniMisto = new AdresniMisto(kod, nespravny, cisloDomovni, cisloOrientacni,
                cisloOrientacniPismeno, psc, stavebniObjektKod, uliceKod, voKod, platiOd, platiDo,
                idTransakce, globalniIdNavrhuZmeny, definicniBod, nespravnyUdaj);

        esService.saveAdresniMisto(adresniMisto);
    }
}
