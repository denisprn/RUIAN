package com.bp.RUIAN.services;

import com.bp.RUIAN.entities.*;
import com.bp.RUIAN.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EsService {
    private final static String INDEX_NAME = "ruian";

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

    private final ObjectMapper mapper = new ObjectMapper();

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

    public Iterable<Stat> findStaty() {
        return statRepository.findAll();
    }

    public Optional<Stat> findStatById(Long id) {
        return statRepository.findById(id);
    }

    public void saveStat(Stat stat) {
        statRepository.save(stat);
    }

    public Iterable<RegionSoudrznosti> findRs() {
        return regionSoudrznostiRepository.findAll();
    }

    public Optional<RegionSoudrznosti> findRsById(Long id) {
        return regionSoudrznostiRepository.findById(id);
    }

    public void saveRs(RegionSoudrznosti regionSoudrznosti) {
        regionSoudrznostiRepository.save(regionSoudrznosti);
    }

    public Iterable<Vusc> findVusc() {
        return vuscRepository.findAll();
    }

    public Optional<Vusc> findVuscById(Long id) {
        return vuscRepository.findById(id);
    }

    public void saveVusc(Vusc vusc) {
        vuscRepository.save(vusc);
    }

    public Iterable<Okres> findOkresy() {
        return okresRepository.findAll();
    }

    public Optional<Okres> findOkresById(Long id) {
        return okresRepository.findById(id);
    }

    public void saveOkres(Okres okres) {
        okresRepository.save(okres);
    }

    public Iterable<Orp> findOrp() {
        return orpRepository.findAll();
    }

    public Optional<Orp> findOrpById(Long id) {
        return orpRepository.findById(id);
    }

    public void saveOrp(Orp orp) {
        orpRepository.save(orp);
    }

    public Iterable<Pou> findPou() {
        return pouRepository.findAll();
    }

    public Optional<Pou> findPouById(Long id) {
        return pouRepository.findById(id);
    }

    public void savePou(Pou pou) {
        pouRepository.save(pou);
    }

    public Iterable<Obec> findObce() {
        return obecRepository.findAll();
    }

    public Optional<Obec> findObecById(Long id) {
        return obecRepository.findById(id);
    }

    public void saveObec(Obec obec) {
        obecRepository.save(obec);
    }

    public Iterable<SpravniObvod> findSO() {
        return spravniObvodRepository.findAll();
    }

    public Optional<SpravniObvod> findSOById(Long id) {
        return spravniObvodRepository.findById(id);
    }

    public void saveSO(SpravniObvod spravniObvod) {
        spravniObvodRepository.save(spravniObvod);
    }

    public Iterable<Mop> findMop() {
        return mopRepository.findAll();
    }

    public Optional<Mop> findMopById(Long id) {
        return mopRepository.findById(id);
    }

    public void saveMop(Mop mop) {
        mopRepository.save(mop);
    }

    public Iterable<Momc> findMomc() {
        return momcRepository.findAll();
    }

    public Optional<Momc> findMomcById(Long id) {
        return momcRepository.findById(id);
    }

    public void saveMomc(Momc momc) {
        momcRepository.save(momc);
    }

    public Iterable<CastObce> findCastiObce() {
        return castObceRepository.findAll();
    }

    public Optional<CastObce> findCastObceById(Long id) {
        return castObceRepository.findById(id);
    }

    public void saveCastObce(CastObce castObce) {
        castObceRepository.save(castObce);
    }

    public Iterable<KatastralniUzemi> findKatastralniUzemi() {
        return katastralniUzemiRepository.findAll();
    }

    public Optional<KatastralniUzemi> findKatastralniUzemiById(Long id) {
        return katastralniUzemiRepository.findById(id);
    }

    public void saveKatastralniUzemi(KatastralniUzemi katastralniUzemi) {
        katastralniUzemiRepository.save(katastralniUzemi);
    }

    public Iterable<Zsj> findZsj() {
        return zsjRepository.findAll();
    }

    public Optional<Zsj> findZsjById(Long id) {
        return zsjRepository.findById(id);
    }

    public void saveZsj(Zsj zsj) {
        zsjRepository.save(zsj);
    }

    public Iterable<Ulice> findUlice() {
        return uliceRepository.findAll();
    }

    public Optional<Ulice> findUliceById(Long id) {
        return uliceRepository.findById(id);
    }

    public void saveUlice(Ulice ulice) {
        uliceRepository.save(ulice);
    }

    public Iterable<Parcela> findParcely() {
        return parcelaRepository.findAll();
    }

    public Optional<Parcela> findParcelaById(Long id) {
        return parcelaRepository.findById(id);
    }

    public void saveParcela(Parcela parcela) {
        parcelaRepository.save(parcela);
    }

    public Iterable<StavebniObjekt> findStavebniObjekty() {
        return stavebniObjektRepository.findAll();
    }

    public Optional<StavebniObjekt> findStavebniObjektById(Long id) {
        return stavebniObjektRepository.findById(id);
    }

    public void saveStavebniObjekt(StavebniObjekt stavebniObjekt) {
        stavebniObjektRepository.save(stavebniObjekt);
    }

    public Iterable<AdresniMisto> findAdresniMista() {
        return adresniMistoRepository.findAll();
    }

    public Optional<AdresniMisto> findAdresniMistoById(Long id) {
        return adresniMistoRepository.findById(id);
    }

    public void saveAdresniMisto(AdresniMisto adresniMisto) {
        adresniMistoRepository.save(adresniMisto);
    }

    //Searches
    public List<Okres> searchOkresy(String searchString) throws Exception {
        SearchRequest searchRequest = new SearchRequest("okres");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(searchString,
                "nazev", "nutsLau").fuzziness("AUTO"));

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Okres> okresy = new ArrayList<>();

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();

            Integer kod = (Integer) sourceAsMap.get("kod");

            Long kodtoLong = Long.parseLong(kod.toString());

            Optional<Okres> okres = findOkresById(kodtoLong);

            okresy.add(okres.orElse(null));
        }

        return okresy;
    }

    public List<Obec> searchObce(String searchString) throws Exception {
        SearchRequest searchRequest = new SearchRequest("obec");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(searchString,
                "nazev", "nutsLau").fuzziness("AUTO"));

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Obec> obce = new ArrayList<>();

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();

            Integer kod = (Integer) sourceAsMap.get("kod");

            Long kodtoLong = Long.parseLong(kod.toString());

            Optional<Obec> obec = findObecById(kodtoLong);

            obce.add(obec.orElse(null));
        }

        return obce;
    }

    public List<CastObce> searchCastiObce(String searchString) throws Exception {
        SearchRequest searchRequest = new SearchRequest("castobce");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("nazev", searchString)
                .fuzziness("AUTO"));

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

        List<CastObce> castiObce = new ArrayList<>();

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();

            Integer kod = (Integer) sourceAsMap.get("kod");

            Long kodtoLong = Long.parseLong(kod.toString());

            Optional<CastObce> castObce = findCastObceById(kodtoLong);

            castiObce.add(castObce.orElse(null));
        }

        return castiObce;
    }

    public List<Ulice> searchUlice(String searchString) throws Exception {
        SearchRequest searchRequest = new SearchRequest("ulice");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("nazev", searchString)
                .fuzziness("AUTO"));

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Ulice> uliceList = new ArrayList<>();

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();

            Integer kod = (Integer) sourceAsMap.get("kod");

            Long kodtoLong = Long.parseLong(kod.toString());

            Optional<Ulice> ulice = findUliceById(kodtoLong);

            uliceList.add(ulice.orElse(null));
        }

        return uliceList;
    }

    public List<AdresniMisto> searchAdresniMista(String searchString) throws Exception {
        SearchRequest searchRequest = new SearchRequest("adresnimisto");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(searchString,
                "cisloDomovni", "cisloOrientacni", "cisloOrientacniPismeno", "psc")
                .fuzziness("AUTO"));

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

        List<AdresniMisto> adresniMista = new ArrayList<>();

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            
            Integer kod = (Integer) sourceAsMap.get("kod");

            Long kodtoLong = Long.parseLong(kod.toString());

            Optional<AdresniMisto> adresniMisto = findAdresniMistoById(kodtoLong);

            adresniMista.add(adresniMisto.orElse(null));
        }

        return adresniMista;
    }
}
