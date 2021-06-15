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

    public Optional<Stat> findStatById(Long id) {
        return statRepository.findById(id);
    }

    public void saveStat(Stat stat) {
        statRepository.save(stat);
    }

    public Optional<RegionSoudrznosti> findRsById(Long id) {
        return regionSoudrznostiRepository.findById(id);
    }

    public void saveRs(RegionSoudrznosti regionSoudrznosti) {
        regionSoudrznostiRepository.save(regionSoudrznosti);
    }

    public Optional<Vusc> findVuscById(Long id) {
        return vuscRepository.findById(id);
    }

    public void saveVusc(Vusc vusc) {
        vuscRepository.save(vusc);
    }

    public Optional<Okres> findOkresById(Long id) {
        return okresRepository.findById(id);
    }

    public void saveOkres(Okres okres) {
        okresRepository.save(okres);
    }

    public Optional<Orp> findOrpById(Long id) {
        return orpRepository.findById(id);
    }

    public void saveOrp(Orp orp) {
        orpRepository.save(orp);
    }

    public Optional<Pou> findPouById(Long id) {
        return pouRepository.findById(id);
    }

    public void savePou(Pou pou) {
        pouRepository.save(pou);
    }

    public Optional<Obec> findObecById(Long id) {
        return obecRepository.findById(id);
    }

    public void saveObec(Obec obec) {
        obecRepository.save(obec);
    }

    public Optional<SpravniObvod> findSOById(Long id) {
        return spravniObvodRepository.findById(id);
    }

    public void saveSO(SpravniObvod spravniObvod) {
        spravniObvodRepository.save(spravniObvod);
    }

    public Optional<Mop> findMopById(Long id) {
        return mopRepository.findById(id);
    }

    public void saveMop(Mop mop) {
        mopRepository.save(mop);
    }

    public Optional<Momc> findMomcById(Long id) {
        return momcRepository.findById(id);
    }

    public void saveMomc(Momc momc) {
        momcRepository.save(momc);
    }

    public Optional<CastObce> findCastObceById(Long id) {
        return castObceRepository.findById(id);
    }

    public void saveCastObce(CastObce castObce) {
        castObceRepository.save(castObce);
    }

    public Optional<KatastralniUzemi> findKatastralniUzemiById(Long id) {
        return katastralniUzemiRepository.findById(id);
    }

    public void saveKatastralniUzemi(KatastralniUzemi katastralniUzemi) {
        katastralniUzemiRepository.save(katastralniUzemi);
    }

    public Optional<Zsj> findZsjById(Long id) {
        return zsjRepository.findById(id);
    }

    public void saveZsj(Zsj zsj) {
        zsjRepository.save(zsj);
    }

    public Optional<Ulice> findUliceById(Long id) {
        return uliceRepository.findById(id);
    }

    public void saveUlice(Ulice ulice) {
        uliceRepository.save(ulice);
    }

    public Optional<Parcela> findParcelaById(Long id) {
        return parcelaRepository.findById(id);
    }

    public void saveParcela(Parcela parcela) {
        parcelaRepository.save(parcela);
    }

    public Optional<StavebniObjekt> findStavebniObjektById(Long id) {
        return stavebniObjektRepository.findById(id);
    }

    public void saveStavebniObjekt(StavebniObjekt stavebniObjekt) {
        stavebniObjektRepository.save(stavebniObjekt);
    }

    public Optional<AdresniMisto> findAdresniMistoById(Long id) {
        return adresniMistoRepository.findById(id);
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
                    Optional<Okres> okres = findOkresById(id);
                    founded.add(okres.orElse(null));
                }
                case "obec" -> {
                    Optional<Obec> obec = findObecById(id);
                    founded.add(obec.orElse(null));
                }
                case "castobce" -> {
                    Optional<CastObce> castObce = findCastObceById(id);
                    founded.add(castObce.orElse(null));
                }
                case "ulice" -> {
                    Optional<Ulice> ulice = findUliceById(id);
                    founded.add(ulice.orElse(null));
                }
                case "adresnimisto" -> {
                    Optional<AdresniMisto> adresniMisto = findAdresniMistoById(id);
                    founded.add(adresniMisto.orElse(null));
                }
            }
        }

        return founded;
    }
}
