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

    /**
     * Find all Stat records in StatRepository
     * @return Iterable of the Stat record
     */
    public Iterable<Stat> findStaty() {
        return statRepository.findAll();
    }

    /**
     * Find Stat record by id in StatRepository
     * @param id id of the Stat record
     * @return Optional of the Stat record
     */
    public Optional<Stat> findStatById(Long id) {
        return statRepository.findById(id);
    }

    /**
     * Save Stat record in StatRepository
     * @param stat Stat record
     */
    public void saveStat(Stat stat) {
        statRepository.save(stat);
    }

    /**
     * Find all RegionSoudrznosti records in RegionSoudrznostiRepository
     * @return Iterable of the RegionSoudrznosti record
     */
    public Iterable<RegionSoudrznosti> findRs() {
        return regionSoudrznostiRepository.findAll();
    }

    /**
     * Find RegionSoudrznosti record by id in RegionSoudrznostiRepository
     * @param id id of the RegionSoudrznosti record
     * @return RegionSoudrznosti record
     */
    public Optional<RegionSoudrznosti> findRsById(Long id) {
        return regionSoudrznostiRepository.findById(id);
    }

    /**
     * Save RegionSoudrznosti record in RegionSoudrznostiRepository
     * @param regionSoudrznosti RegionSoudrznosti record
     */
    public void saveRs(RegionSoudrznosti regionSoudrznosti) {
        regionSoudrznostiRepository.save(regionSoudrznosti);
    }

    /**
     * Find all Vusc records in VuscRepository
     * @return Iterable of the Vusc record
     */
    public Iterable<Vusc> findVusc() {
        return vuscRepository.findAll();
    }

    /**
     * Find Vusc record by id in VuscRepository
     * @param id id of the Vusc record
     * @return Vusc record
     */
    public Optional<Vusc> findVuscById(Long id) {
        return vuscRepository.findById(id);
    }

    /**
     * Save Vusc record in VuscRepository
     * @param vusc Vusc record
     */
    public void saveVusc(Vusc vusc) {
        vuscRepository.save(vusc);
    }

    /**
     * Find all Okres records in OkresRepository
     * @return Iterable of the Okres record
     */
    public Iterable<Okres> findOkresy() {
        return okresRepository.findAll();
    }

    /**
     * Find Okres record by id in OkresRepository
     * @param id id of the Okres record
     * @return Okres record
     */
    public Optional<Okres> findOkresById(Long id) {
        return okresRepository.findById(id);
    }

    /**
     * Save Okres record in OkresRepository
     * @param okres Okres record
     */
    public void saveOkres(Okres okres) {
        okresRepository.save(okres);
    }

    /**
     * Find all Orp records in OrpRepository
     * @return Iterable of the Orp record
     */
    public Iterable<Orp> findOrp() {
        return orpRepository.findAll();
    }

    /**
     * Find Orp record by id in OrpRepository
     * @param id id of the Orp record
     * @return Orp record
     */
    public Optional<Orp> findOrpById(Long id) {
        return orpRepository.findById(id);
    }

    /**
     * Save Orp record in OrpRepository
     * @param orp Orp record
     */
    public void saveOrp(Orp orp) {
        orpRepository.save(orp);
    }

    /**
     * Find all Pou records in PouRepository
     * @return Iterable of the Pou record
     */
    public Iterable<Pou> findPou() {
        return pouRepository.findAll();
    }

    /**
     * Find Pou record by id in PouRepository
     * @param id id of the Pou record
     * @return Pou record
     */
    public Optional<Pou> findPouById(Long id) {
        return pouRepository.findById(id);
    }

    /**
     * Save Pou record in PouRepository
     * @param pou Pou record
     */
    public void savePou(Pou pou) {
        pouRepository.save(pou);
    }

    /**
     * Find all Obec records in ObecRepository
     * @return Iterable of the Obec record
     */
    public Iterable<Obec> findObce() {
        return obecRepository.findAll();
    }

    /**
     * Find Obec record by id in ObecRepository
     * @param id id of the Obec record
     * @return Obec record
     */
    public Optional<Obec> findObecById(Long id) {
        return obecRepository.findById(id);
    }

    /**
     * Save Obec record in ObecRepository
     * @param obec Obec record
     */
    public void saveObec(Obec obec) {
        obecRepository.save(obec);
    }

    /**
     * Find all SpravniObvod records in SpravniObvodRepository
     * @return Iterable of the SpravniObvod record
     */
    public Iterable<SpravniObvod> findSO() {
        return spravniObvodRepository.findAll();
    }

    /**
     * Find SpravniObvod record by id in SpravniObvodRepository
     * @param id id of the SpravniObvod record
     * @return SpravniObvod record
     */
    public Optional<SpravniObvod> findSOById(Long id) {
        return spravniObvodRepository.findById(id);
    }

    /**
     * Save SpravniObvod record in SpravniObvodRepository
     * @param spravniObvod SpravniObvod record
     */
    public void saveSO(SpravniObvod spravniObvod) {
        spravniObvodRepository.save(spravniObvod);
    }

    /**
     * Find all Mop records in MopRepository
     * @return Iterable of the Mop record
     */
    public Iterable<Mop> findMop() {
        return mopRepository.findAll();
    }

    /**
     * Find Mop record by id in MopRepository
     * @param id id of the Mop record
     * @return Mop record
     */
    public Optional<Mop> findMopById(Long id) {
        return mopRepository.findById(id);
    }

    /**
     * Save Mop record in MopRepository
     * @param mop Mop record
     */
    public void saveMop(Mop mop) {
        mopRepository.save(mop);
    }

    /**
     * Find all Momc records in MomcRepository
     * @return Iterable of the Momc record
     */
    public Iterable<Momc> findMomc() {
        return momcRepository.findAll();
    }

    /**
     * Find Momc record by id in MomcRepository
     * @param id id of the Momc record
     * @return Optional of the Momc record
     */
    public Optional<Momc> findMomcById(Long id) {
        return momcRepository.findById(id);
    }

    /**
     * Save Momc record in MomcRepository
     * @param momc Momc record
     */
    public void saveMomc(Momc momc) {
        momcRepository.save(momc);
    }

    /**
     * Find all CastObce records in CastObceRepository
     * @return Iterable of the CastObce record
     */
    public Iterable<CastObce> findCastiObce() {
        return castObceRepository.findAll();
    }

    /**
     * Find CastObce record by id in CastObceRepository
     * @param id id of the CastObce record
     * @return Optional of the CastObce record
     */
    public Optional<CastObce> findCastObceById(Long id) {
        return castObceRepository.findById(id);
    }

    /**
     * Save CastObce record in CastObceRepository
     * @param castObce CastObce record
     */
    public void saveCastObce(CastObce castObce) {
        castObceRepository.save(castObce);
    }

    /**
     * Find all KatastralniUzemi records in KatastralniUzemiRepository
     * @return Iterable of the KatastralniUzemi record
     */
    public Iterable<KatastralniUzemi> findKatastralniUzemi() {
        return katastralniUzemiRepository.findAll();
    }

    /**
     * Find KatastralniUzemi record by id in KatastralniUzemiRepository
     * @param id id of the KatastralniUzemi record
     * @return Optional of the KatastralniUzemi record
     */
    public Optional<KatastralniUzemi> findKatastralniUzemiById(Long id) {
        return katastralniUzemiRepository.findById(id);
    }

    /**
     * Save KatastralniUzemi record in KatastralniUzemiRepository
     * @param katastralniUzemi KatastralniUzemi record
     */
    public void saveKatastralniUzemi(KatastralniUzemi katastralniUzemi) {
        katastralniUzemiRepository.save(katastralniUzemi);
    }

    /**
     * Find all Zsj records in ZsjRepository
     * @return Iterable of the Zsj record
     */
    public Iterable<Zsj> findZsj() {
        return zsjRepository.findAll();
    }

    /**
     * Find Zsj record by id in ZsjRepository
     * @param id id of the Zsj record
     * @return Optional of the Zsj record
     */
    public Optional<Zsj> findZsjById(Long id) {
        return zsjRepository.findById(id);
    }

    /**
     * Save Zsj record in ZsjRepository
     * @param zsj Zsj record
     */
    public void saveZsj(Zsj zsj) {
        zsjRepository.save(zsj);
    }

    /**
     * Find all Ulice records in UliceRepository
     * @return Iterable of the Ulice record
     */
    public Iterable<Ulice> findUlice() {
        return uliceRepository.findAll();
    }

    /**
     * Find Ulice record by id in UliceRepository
     * @param id id of the Ulice record
     * @return Optional of the Ulice record
     */
    public Optional<Ulice> findUliceById(Long id) {
        return uliceRepository.findById(id);
    }

    /**
     * Save Ulice record in UliceRepository
     * @param ulice Ulice record
     */
    public void saveUlice(Ulice ulice) {
        uliceRepository.save(ulice);
    }

    /**
     * Find all Parcela records in ParcelaRepository
     * @return Iterable of the Parcela record
     */
    public Iterable<Parcela> findParcely() {
        return parcelaRepository.findAll();
    }

    /**
     * Find Parcela record by id in ParcelaRepository
     * @param id id of the Parcela record
     * @return Optional of the Parcela record
     */
    public Optional<Parcela> findParcelaById(Long id) {
        return parcelaRepository.findById(id);
    }

    /**
     * Save Parcela record in ParcelaRepository
     * @param parcela Parcela record
     */
    public void saveParcela(Parcela parcela) {
        parcelaRepository.save(parcela);
    }

    /**
     * Find all StavebniObjekt records in StavebniObjektRepository
     * @return Iterable of the StavebniObjekt record
     */
    public Iterable<StavebniObjekt> findStavebniObjekty() {
        return stavebniObjektRepository.findAll();
    }

    /**
     * Find StavebniObjekt record by id in StavebniObjektRepository
     * @param id id of the StavebniObjekt record
     * @return Optional of the StavebniObjekt record
     */
    public Optional<StavebniObjekt> findStavebniObjektById(Long id) {
        return stavebniObjektRepository.findById(id);
    }

    /**
     * Save StavebniObjekt record in StavebniObjektRepository
     * @param stavebniObjekt StavebniObjekt record
     */
    public void saveStavebniObjekt(StavebniObjekt stavebniObjekt) {
        stavebniObjektRepository.save(stavebniObjekt);
    }

    /**
     * Find all AdresniMisto records in AdresniMistoRepository
     * @return Iterable of the AdresniMisto record
     */
    public Iterable<AdresniMisto> findAdresniMista() {
        return adresniMistoRepository.findAll();
    }

    /**
     * Find AdresniMisto record by id in AdresniMistoRepository
     * @param id id of the AdresniMisto record
     * @return Optional of the AdresniMisto record
     */
    public Optional<AdresniMisto> findAdresniMistoById(Long id) {
        return adresniMistoRepository.findById(id);
    }

    /**
     * Save AdresniMisto record in AdresniMistoRepository
     * @param adresniMisto AdresniMisto record
     */
    public void saveAdresniMisto(AdresniMisto adresniMisto) {
        adresniMistoRepository.save(adresniMisto);
    }

    /**
     * Fuzzy search by nazev, cisloDomovni, cisloOrientacni,cisloOrientacniPismeno, psc, nutsLau
     * in okres, obec, castobce, ulice and adresnimisto indexes
     * @param searchString query string
     */
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

    /**
     * Fuzzy search of Okres records
     * @param searchString query String
     * @return List of Okres records
     * @throws IOException if input/output error occurs
     */
    public List<Okres> searchOkresy(String searchString) throws IOException {
        SearchRequest searchRequest = new SearchRequest("okres");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(searchString,
                "nazev", "nutsLau").fuzziness("AUTO"));

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Okres> okresy = new ArrayList<>();

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Long id = Long.parseLong(hit.getId());

            Optional<Okres> okres = findOkresById(id);

            okresy.add(okres.orElse(null));
        }

        return okresy;
    }

    /**
     * Fuzzy search of Obec records
     * @param searchString query String
     * @return List of Obec records
     * @throws IOException if input/output error occurs
     */
    public List<Obec> searchObce(String searchString) throws IOException {
        SearchRequest searchRequest = new SearchRequest("obec");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(searchString,
                "nazev", "nutsLau").fuzziness("AUTO"));

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Obec> obce = new ArrayList<>();

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Long id = Long.parseLong(hit.getId());

            Optional<Obec> obec = findObecById(id);

            obce.add(obec.orElse(null));
        }

        return obce;
    }

    /**
     * Fuzzy search of CastObce records
     * @param searchString query String
     * @return List of CastObce records
     * @throws IOException if input/output error occurs
     */
    public List<CastObce> searchCastiObce(String searchString) throws IOException {
        SearchRequest searchRequest = new SearchRequest("castobce");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("nazev", searchString)
                .fuzziness("AUTO"));

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

        List<CastObce> castiObce = new ArrayList<>();

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Long id = Long.parseLong(hit.getId());

            Optional<CastObce> castObce = findCastObceById(id);

            castiObce.add(castObce.orElse(null));
        }

        return castiObce;
    }

    /**
     * Fuzzy search of Ulice records
     * @param searchString query String
     * @return List of Ulice records
     * @throws IOException if input/output error occurs
     */
    public List<Ulice> searchUlice(String searchString) throws IOException {
        SearchRequest searchRequest = new SearchRequest("ulice");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("nazev", searchString)
                .fuzziness("AUTO"));

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Ulice> uliceList = new ArrayList<>();

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Long id = Long.parseLong(hit.getId());

            Optional<Ulice> ulice = findUliceById(id);

            uliceList.add(ulice.orElse(null));
        }

        return uliceList;
    }

    /**
     * Fuzzy search of AdresniMisto records
     * @param searchString query String
     * @return List of AdresniMisto records
     * @throws IOException if input/output error occurs
     */
    public List<AdresniMisto> searchAdresniMista(String searchString) throws IOException {
        SearchRequest searchRequest = new SearchRequest("adresnimisto");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(searchString,
                "cisloDomovni", "cisloOrientacni", "cisloOrientacniPismeno", "psc")
                .fuzziness("AUTO"));

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

        List<AdresniMisto> adresniMista = new ArrayList<>();

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Long id = Long.parseLong(hit.getId());

            Optional<AdresniMisto> adresniMisto = findAdresniMistoById(id);

            adresniMista.add(adresniMisto.orElse(null));
        }

        return adresniMista;
    }
}
