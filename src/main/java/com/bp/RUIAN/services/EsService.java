package com.bp.RUIAN.services;

import com.bp.RUIAN.entities.*;
import com.bp.RUIAN.repositories.*;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Service for API's business logic
 * @author Denys Peresychanskyi
 */
@Service
public class EsService {
    private final StatRepository statRepository;
    private final RegionSoudrznostiRepository regionSoudrznostiRepository;
    private final VuscRepository vuscRepository;
    private final OkresRepository okresRepository;
    private final OrpRepository orpRepository;
    private final PouRepository pouRepository;
    private final ObecRepository obecRepository;
    private final SpravniObvodRepository spravniObvodRepository;
    private final MopRepository mopRepository;
    private final MomcRepository momcRepository;
    private final CastObceRepository castObceRepository;
    private final KatastralniUzemiRepository katastralniUzemiRepository;
    private final ZsjRepository zsjRepository;
    private final UliceRepository uliceRepository;
    private final ParcelaRepository parcelaRepository;
    private final StavebniObjektRepository stavebniObjektRepository;
    private final AdresniMistoRepository adresniMistoRepository;

    private final RestHighLevelClient esClient;

    public EsService(RestHighLevelClient esClient, StatRepository statRepository,
                     RegionSoudrznostiRepository regionSoudrznostiRepository,
                     VuscRepository vuscRepository, OkresRepository okresRepository,
                     OrpRepository orpRepository, PouRepository pouRepository,
                     ObecRepository obecRepository, SpravniObvodRepository spravniObvodRepository,
                     MopRepository mopRepository, MomcRepository momcRepository,
                     CastObceRepository castObceRepository, KatastralniUzemiRepository katastralniUzemiRepository,
                     ZsjRepository zsjRepository, UliceRepository uliceRepository,
                     ParcelaRepository parcelaRepository, StavebniObjektRepository stavebniObjektRepository,
                     AdresniMistoRepository adresniMistoRepository) {
        this.esClient = esClient;
        this.statRepository = statRepository;
        this.regionSoudrznostiRepository = regionSoudrznostiRepository;
        this.vuscRepository = vuscRepository;
        this.okresRepository = okresRepository;
        this.orpRepository = orpRepository;
        this.pouRepository = pouRepository;
        this.obecRepository = obecRepository;
        this.spravniObvodRepository = spravniObvodRepository;
        this.mopRepository = mopRepository;
        this.momcRepository = momcRepository;
        this.castObceRepository = castObceRepository;
        this.katastralniUzemiRepository = katastralniUzemiRepository;
        this.zsjRepository = zsjRepository;
        this.uliceRepository = uliceRepository;
        this.parcelaRepository = parcelaRepository;
        this.stavebniObjektRepository = stavebniObjektRepository;
        this.adresniMistoRepository = adresniMistoRepository;
    }

    public List<Object> findById(Long id) {
        List<Object> objects = new ArrayList<>();

        Optional<Stat> stat = statRepository.findById(id);

        if (stat.isPresent()) objects.add(stat);

        Optional<RegionSoudrznosti> regionSoudrznosti = regionSoudrznostiRepository.findById(id);

        if (regionSoudrznosti.isPresent()) objects.add(regionSoudrznosti);

        Optional<Vusc> vusc = vuscRepository.findById(id);

        if (vusc.isPresent()) objects.add(vusc);

        Optional<Okres> okres = okresRepository.findById(id);

        if (okres.isPresent()) objects.add(okres);

        Optional<Orp> orp = orpRepository.findById(id);

        if (orp.isPresent()) objects.add(orp);

        Optional<Pou> pou = pouRepository.findById(id);

        if (pou.isPresent()) objects.add(pou);

        Optional<Obec> obec = obecRepository.findById(id);

        if (obec.isPresent()) objects.add(obec);

        Optional<SpravniObvod> spravniObvod = spravniObvodRepository.findById(id);

        if (spravniObvod.isPresent()) objects.add(spravniObvod);

        Optional<Mop> mop = mopRepository.findById(id);

        if (mop.isPresent()) objects.add(mop);

        Optional<Momc> momc = momcRepository.findById(id);

        if (momc.isPresent()) objects.add(momc);

        Optional<CastObce> castObce = castObceRepository.findById(id);

        if (castObce.isPresent()) objects.add(castObce);

        Optional<KatastralniUzemi> katastralniUzemi = katastralniUzemiRepository.findById(id);

        if (katastralniUzemi.isPresent()) objects.add(katastralniUzemi);

        Optional<Zsj> zsj = zsjRepository.findById(id);

        if (zsj.isPresent()) objects.add(zsj);

        Optional<Ulice> ulice = uliceRepository.findById(id);

        if (ulice.isPresent()) objects.add(ulice);

        Optional<Parcela> parcela = parcelaRepository.findById(id);

        if (parcela.isPresent()) objects.add(parcela);

        Optional<StavebniObjekt> stavebniObjekt = stavebniObjektRepository.findById(id);

        if (stavebniObjekt.isPresent()) objects.add(stavebniObjekt);

        Optional<AdresniMisto> adresniMisto = adresniMistoRepository.findById(id);

        if (adresniMisto.isPresent()) objects.add(adresniMisto);

        return objects;
    }

    public void saveStat(Stat stat) {
        statRepository.save(stat);
    }

    public void saveRs(RegionSoudrznosti regionSoudrznosti) {
        regionSoudrznostiRepository.save(regionSoudrznosti);
    }

    public void saveVusc(Vusc vusc) {
        vuscRepository.save(vusc);
    }

    public void saveOkres(Okres okres) {
        okresRepository.save(okres);
    }

    public void saveOrp(Orp orp) {
        orpRepository.save(orp);
    }

    public void savePou(Pou pou) {
        pouRepository.save(pou);
    }

    public void saveObec(Obec obec) {
        obecRepository.save(obec);
    }

    public void saveSO(SpravniObvod spravniObvod) {
        spravniObvodRepository.save(spravniObvod);
    }

    public void saveMop(Mop mop) {
        mopRepository.save(mop);
    }

    public void saveMomc(Momc momc) {
        momcRepository.save(momc);
    }

    public void saveCastObce(CastObce castObce) {
        castObceRepository.save(castObce);
    }

    public void saveKatastralniUzemi(KatastralniUzemi katastralniUzemi) {
        katastralniUzemiRepository.save(katastralniUzemi);
    }

    public void saveZsj(Zsj zsj) {
        zsjRepository.save(zsj);
    }

    public void saveUlice(Ulice ulice) {
        uliceRepository.save(ulice);
    }

    public void saveParcela(Parcela parcela) {
        parcelaRepository.save(parcela);
    }

    public void saveStavebniObjekt(StavebniObjekt stavebniObjekt) {
        stavebniObjektRepository.save(stavebniObjekt);
    }

    public void saveAdresniMisto(AdresniMisto adresniMisto) {
        adresniMistoRepository.save(adresniMisto);
    }

    public List<Object> search(String searchString) throws IOException {
        SearchRequest searchRequest = new SearchRequest("okres", "obec", "castobce",
                "ulice", "adresnimisto");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(searchString,
                "nazev", "cisloDomovni", "cisloOrientacni", "cisloOrientacniPismeno",
                "psc", "nutsLau").fuzziness("AUTO"));

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Object> founded = new ArrayList<>();

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String hitIndexName = hit.getIndex();
            Long id = Long.parseLong(hit.getId());

            switch(hitIndexName) {
                case "okres" -> {
                    Optional<Okres> okres = okresRepository.findById(id);
                    founded.add(okres.orElse(null));
                }
                case "obec" -> {
                    Optional<Obec> obec = obecRepository.findById(id);
                    founded.add(obec.orElse(null));
                }
                case "castobce" -> {
                    Optional<CastObce> castObce = castObceRepository.findById(id);
                    founded.add(castObce.orElse(null));
                }
                case "ulice" -> {
                    Optional<Ulice> ulice = uliceRepository.findById(id);
                    founded.add(ulice.orElse(null));
                }
                case "adresnimisto" -> {
                    Optional<AdresniMisto> adresniMisto = adresniMistoRepository.findById(id);
                    founded.add(adresniMisto.orElse(null));
                }
            }
        }

        return founded;
    }
}
