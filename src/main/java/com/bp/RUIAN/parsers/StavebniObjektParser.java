package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.DetailniTEA;
import com.bp.RUIAN.entities.NespravnyUdaj;
import com.bp.RUIAN.entities.StavebniObjekt;
import com.bp.RUIAN.utils.Prefixes;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class StavebniObjektParser implements RecordParser<StavebniObjekt> {
    private static final String PREFIX = Prefixes.STAVEBNI_OBJEKT_PREFIX;
    private final IntegerParser integerParser;
    private final LongParser longParser;
    private final ListIntegerParser intArrParser;
    private final DateParser dateParser;
    private final DetailniTeaParser detailniTeaParser;
    private final DefinicniBodParser definicniBodParser;
    private final HraniceParser hraniceParser;
    private final NespravnyUdajParser nespravnyUdajParser;

    public StavebniObjektParser(Element element) {
        this.integerParser = new IntegerParser(element, PREFIX);
        this.longParser = new LongParser(element, PREFIX);
        this.intArrParser = new ListIntegerParser(element, PREFIX);
        this.dateParser = new DateParser(element, PREFIX);
        this.detailniTeaParser = new DetailniTeaParser(element, PREFIX);
        this.definicniBodParser = new DefinicniBodParser(element);
        this.hraniceParser = new HraniceParser(element);
        this.nespravnyUdajParser = new NespravnyUdajParser(element, PREFIX);
    }

    @Override
    public StavebniObjekt parse() throws ParseException {
        boolean nespravny = false;
        Integer kod = integerParser.parse("Kod");
        List<Integer> cislaDomovni = intArrParser.parse("com:CisloDomovni");
        Long identifikacniParcelaId = longParser.parse("pai:Id");
        Integer typStavebnihoObjektuKod = integerParser.parse("TypStavebnihoObjektuKod");
        Integer zpusobVyuzitiKod = integerParser.parse("ZpusobVyuzitiKod");
        Integer castObceKod = integerParser.parse("coi:Kod");
        Date platiOd = dateParser.parse("PlatiOd");
        Date platiDo = dateParser.parse("PlatiDo");
        Long idTransakce = longParser.parse("IdTransakce");
        Long globalniIdNavrhuZmeny = longParser.parse("GlobalniIdNavrhuZmeny");
        Long isknBudovaId = longParser.parse("IsknBudovaId");
        Date dokonceni = dateParser.parse("Dokonceni");
        Integer druhKonstrukceKod = integerParser.parse("DruhKonstrukceKod");
        Integer obestavenyProstor = integerParser.parse("ObestavenyProstor");
        Integer pocetBytu = integerParser.parse("PocetBytu");
        Integer pocetPodlazi = integerParser.parse("PocetPodlazi");
        Integer podlahovaPlocha = integerParser.parse("PodlahovaPlocha");
        Integer pripojeniKanalizaceKod = integerParser.parse("PripojeniKanalizaceKod");
        Integer pripojeniPlynKod = integerParser.parse("PripojeniPlynKod");
        Integer pripojeniVodovodKod = integerParser.parse("PripojeniVodovodKod");
        Integer vybaveniVytahemKod = integerParser.parse("VybaveniVytahemKod");
        Integer zastavenaPlocha = integerParser.parse("ZastavenaPlocha");
        Integer zpusobVytapeniKod = integerParser.parse("ZpusobVytapeniKod");
        List<Integer> zpusobyOchranyKod = intArrParser.parse("ZpusobOchranyKod");
        List<DetailniTEA> detailniTEAList = detailniTeaParser.parse("DetailniTEA");
        GeoJsonPoint definicniBod = definicniBodParser.parse("pos");
        GeoJsonPolygon hranice = hraniceParser.parse("posList");
        NespravnyUdaj nespravnyUdaj = nespravnyUdajParser.parse("NespravneUdaje");

        if (nespravnyUdaj != null) {
            nespravny = true;
        }

        return new StavebniObjekt(kod, nespravny, cislaDomovni, identifikacniParcelaId,
                typStavebnihoObjektuKod, zpusobVyuzitiKod, castObceKod, platiOd, platiDo, idTransakce,
                globalniIdNavrhuZmeny, isknBudovaId, dokonceni, druhKonstrukceKod, obestavenyProstor, pocetBytu,
                pocetPodlazi, podlahovaPlocha, pripojeniKanalizaceKod, pripojeniPlynKod, pripojeniVodovodKod,
                vybaveniVytahemKod, zastavenaPlocha, zpusobVytapeniKod, zpusobyOchranyKod, detailniTEAList,
                definicniBod, hranice, nespravnyUdaj);
    }
}
