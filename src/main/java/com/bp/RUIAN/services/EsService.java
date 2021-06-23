package com.bp.RUIAN.services;

import com.bp.RUIAN.entities.*;
import com.bp.RUIAN.repositories.*;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
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

        Optional<Okres> okres = okresRepository.findById(id);

        if (okres.isPresent()) objects.add(okres);

        Optional<Obec> obec = obecRepository.findById(id);

        if (obec.isPresent()) objects.add(obec);

        Optional<CastObce> castObce = castObceRepository.findById(id);

        if (castObce.isPresent()) objects.add(castObce);

        Optional<Ulice> ulice = uliceRepository.findById(id);

        if (ulice.isPresent()) objects.add(ulice);

        Optional<AdresniMisto> adresniMisto = adresniMistoRepository.findById(id);

        if (adresniMisto.isPresent()) objects.add(adresniMisto);

        return objects;
    }

    public String searchUlice(SearchHit hit, Long id) {
        Map<String, Object> sourceAsMap = hit.getSourceAsMap();
        String nazevUlice = sourceAsMap.get("nazev").toString();

        Optional<Ulice> ulice = uliceRepository.findById(id);

        if (ulice.isPresent()) {
            Long kodObce = Long.parseLong(ulice.get().kodObce().toString());

            Optional<Obec> obec = obecRepository.findById(kodObce);

            if (obec.isPresent()) {
                String nazevObce = obec.get().nazev();

                return String.format("<b>%s</b><br>Ulice, %s", nazevUlice, nazevObce);
            } else {
                return String.format("<b>%s</b><br>Ulice", nazevUlice);
            }
        }

        return null;
    }

    public String searchAdresy(SearchHit hit, String nazevUlice, String nazevObce) {
        String cisloO = "", cisloOP = "";
        Map<String, Object> sourceAsMap = hit.getSourceAsMap();
        String cisloD = sourceAsMap.get("cisloDomovni").toString();

        if (sourceAsMap.get("cisloOrientacni") != null) {
            cisloO = sourceAsMap.get("cisloOrientacni").toString();
            cisloO += "/";
        }

        if (sourceAsMap.get("cisloOrientacniPismeno") != null) {
            cisloOP = sourceAsMap.get("cisloOrientacniPismeno").toString();
        }

        return String.format("<b>%s %s%s%s</b><br>Adresa, %s", nazevUlice, cisloO,
                cisloD, cisloOP, nazevObce);
    }

    public List<String> search(String searchString) throws IOException {
        List<String> found = new ArrayList<>();
        String[] keys;

        // napr. '4. kvetna 5'
        if (searchString.matches("\\d*\\.*\\s*([a-žA-Ž]\\s*)+\\d+")) {
            Long idUlice;
            keys = searchString.split("\\s+");
            String result, nazevUlice, kodObce, nazevObce = "", cisloO = keys[keys.length - 1];
            StringBuilder nazevUliceFromSearch = new StringBuilder();

            for (int i = 0; i < keys.length - 1; i++) {
                if (i != keys.length - 2) {
                    nazevUliceFromSearch.append(keys[i]).append(" ");
                } else {
                    nazevUliceFromSearch.append(keys[i]);
                }
            }

            SearchRequest searchRequest = new SearchRequest("ulice");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery("nazev", nazevUliceFromSearch.toString())
                    .fuzziness(2));

            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

            if (searchResponse.getHits().getHits().length > 0) {
                SearchHit uliceHit = searchResponse.getHits().getHits()[0];
                Map<String, Object> sourceAsMap = uliceHit.getSourceAsMap();
                idUlice = Long.parseLong(sourceAsMap.get("kod").toString());
                nazevUlice = sourceAsMap.get("nazev").toString();

                if (sourceAsMap.get("kodObce") != null) {
                    kodObce = sourceAsMap.get("kodObce").toString();

                    Optional<Obec> obec = obecRepository.findById(Long.parseLong(kodObce));

                    if (obec.isPresent()) {
                        nazevObce = obec.get().nazev();
                    }
                }

                searchRequest = new SearchRequest("adresnimisto");
                searchSourceBuilder = new SearchSourceBuilder();
                BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
                boolQueryBuilder
                        .must(QueryBuilders.matchQuery("uliceKod", Integer.parseInt(idUlice.toString())))
                        .should(QueryBuilders.matchQuery("cisloOrientacni", cisloO));

                searchSourceBuilder.query(boolQueryBuilder);
                searchRequest.source(searchSourceBuilder);
                searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

                for (SearchHit hit : searchResponse.getHits().getHits()) {
                    result = searchAdresy(hit, nazevUlice, nazevObce);

                    found.add(result);
                }
            }
        }

        return found;
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
}
