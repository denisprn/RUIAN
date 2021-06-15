package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.DetailniTEA;
import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.Stat;
import com.bp.RUIAN.entities.StavebniObjekt;
import com.bp.RUIAN.services.EsService;
import com.bp.RUIAN.utils.Prefixes;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class StavebniObjektParser extends AbstractSaveParser {
    private static final String PREFIX = Prefixes.STAVEBNI_OBJEKT_PREFIX;

    public StavebniObjektParser(EsService esService, Element element) {
        super(esService, element, PREFIX);
    }

    @Override
    public void parse() throws ParseException {
        boolean nespravny = false;
        Integer kod = attributeParser.getKod();
        List<Integer> cislaDomovni = attributeParser.getCislaDomovni();
        Long identifikacniParcelaId = attributeParser.getIdentifikacniParcelaId();
        Integer typStavebnihoObjektuKod = attributeParser.getTypStavebnihoObjektuKod();
        Integer zpusobVyuzitiKod = attributeParser.getZpusobVyuzitiKod();
        Integer castObceKod = attributeParser.getKodCastObce();
        Date platiOd = attributeParser.getPlatiOd();
        Date platiDo = attributeParser.getPlatiDo();
        Long idTransakce = attributeParser.getIdTransakce();
        Long globalniIdNavrhuZmeny = attributeParser.getGlobalniIdNavrhuZmeny();
        Long isknBudovaId = attributeParser.getIsknBudovaId();
        Date dokonceni = attributeParser.getDokonceni();
        Integer druhKonstrukceKod = attributeParser.getDruhKonstrukceKod();
        Integer obestavenyProstor = attributeParser.getObestavenyProstor();
        Integer pocetBytu = attributeParser.getPocetBytu();
        Integer pocetPodlazi = attributeParser.getPocetPodlazi();
        Integer podlahovaPlocha = attributeParser.getPodlahovaPlocha();
        Integer pripojeniKanalizaceKod = attributeParser.getPripojeniKanalizaceKod();
        Integer pripojeniPlynKod = attributeParser.getPripojeniPlynKod();
        Integer pripojeniVodovodKod = attributeParser.getPripojeniVodovodKod();
        Integer vybaveniVytahemKod = attributeParser.getVybaveniVytahemKod();
        Integer zastavenaPlocha = attributeParser.getZastavenaPlocha();
        Integer zpusobVytapeniKod = attributeParser.getZpusobVytapeniKod();
        List<Integer> zpusobyOchranyKod = attributeParser.getZpusobyOchranyKod();
        List<DetailniTEA> detailniTEAList = attributeParser.getDetailniTea();
        GeoJsonPoint definicniBod = attributeParser.getDefinicniBod();
        GeoJsonPolygon hranice = attributeParser.getHranice();
        NespravnyUdaj nespravnyUdaj = attributeParser.getNespravneUdaje();

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        StavebniObjekt stavebniObjekt = new StavebniObjekt(kod, nespravny, cislaDomovni, identifikacniParcelaId,
                typStavebnihoObjektuKod, zpusobVyuzitiKod, castObceKod, platiOd, platiDo, idTransakce,
                globalniIdNavrhuZmeny, isknBudovaId, dokonceni, druhKonstrukceKod, obestavenyProstor, pocetBytu,
                pocetPodlazi, podlahovaPlocha, pripojeniKanalizaceKod, pripojeniPlynKod, pripojeniVodovodKod,
                vybaveniVytahemKod, zastavenaPlocha, zpusobVytapeniKod, zpusobyOchranyKod, detailniTEAList,
                definicniBod, hranice, nespravnyUdaj);

        esService.saveStavebniObjekt(stavebniObjekt);
    }
}
