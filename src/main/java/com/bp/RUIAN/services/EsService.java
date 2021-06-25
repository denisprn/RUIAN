package com.bp.RUIAN.services;

import com.bp.RUIAN.entities.*;
import com.bp.RUIAN.repositories.*;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<String> searchAddressesByStreet(
            Integer kodUlice, String adresniBod, String nazevUlice, String nazevObce) {
        List<String> adresy = new ArrayList<>();
        String[] bod;
        String adresa, cisloDomovni, cisloOrientacni = "", cisloOrientacniPismeno = "";
        Page<AdresniMisto> adresniMista = null;

        if (adresniBod.matches("\\d+")) {
            adresniMista = adresniMistoRepository.
                    findByUliceKodWithCisloDomAndOrient(kodUlice, adresniBod, Pageable.ofSize(5));
        } else if (adresniBod.matches("\\d+/\\d+[a-zA-Z]?")) {
            bod = adresniBod.split("/");
            cisloOrientacni = bod[0];
            cisloDomovni = bod[1];

            if (cisloDomovni.matches("\\d+[a-zA-Z]")) {
                StringBuilder cisloDomovniBuilder = new StringBuilder();

                for (int i = 0; i < cisloDomovni.length(); i++) {
                    char c = cisloDomovni.charAt(i);
                    Boolean flag = Character.isDigit(c);

                    if(flag) {
                        cisloDomovniBuilder.append(c);
                    }
                    else {
                        cisloOrientacniPismeno = Character.toString(c);
                    }
                }

                cisloDomovni = cisloDomovniBuilder.toString();

                adresniMista = adresniMistoRepository.
                        findAdresniMistosByUliceKodAndCisloDomovniLikeAndCisloOrientacniLikeAndCisloOrientacniPismenoLike(
                                kodUlice, cisloDomovni, cisloOrientacni, cisloOrientacniPismeno,
                                Pageable.ofSize(3));
            }
        }

        for (AdresniMisto adresniMisto : adresniMista) {
            cisloDomovni = adresniMisto.cisloDomovni();

            if (adresniMisto.cisloOrientacni() != null) {
                cisloOrientacni = adresniMisto.cisloOrientacni();
                cisloOrientacni += "/";
            } else {
                cisloOrientacni += "";
            }

            if (adresniMisto.cisloOrientacniPismeno() != null) {
                cisloOrientacniPismeno = adresniMisto.cisloOrientacniPismeno();
            } else {
                cisloOrientacniPismeno = "";
            }

            adresa = String.format("<b>%s %s%s%s</b><br>Adresa, %s", nazevUlice, cisloOrientacni,
                    cisloDomovni, cisloOrientacniPismeno, nazevObce);

            adresy.add(adresa);
        }

        return adresy;
    }

    private String recognizeAdresniBod(String searchString) {
        String[] tokens = searchString.split(",*\\s+");
        String adresniBod = "";

        for (int i = 0; i < tokens.length; i++) {
            if (i == tokens.length - 1) {
                adresniBod = tokens[i];
            }
        }

        return adresniBod;
    }

    public List<String> search(String searchString) throws IOException {
        Long kodObce;
        Integer kodUlice;
        String nazevUlice, adresniBod, nazevObce = "";

        SearchRequest searchRequest = new SearchRequest( "ulice");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder queryNazevUlice =
                QueryBuilders.matchQuery("nazev", searchString).fuzziness(2);
        searchSourceBuilder.query(queryNazevUlice);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

        Map<String, Object> sourceAsMap = searchResponse.getHits().getHits()[0].getSourceAsMap();
        kodUlice = Integer.parseInt(sourceAsMap.get("kod").toString());
        nazevUlice = sourceAsMap.get("nazev").toString();
        adresniBod = recognizeAdresniBod(searchString);
        kodObce = Long.parseLong(sourceAsMap.get("kodObce").toString());

        Optional<Obec> obec = obecRepository.findById(kodObce);

        if (obec.isPresent()) {
            nazevObce = obec.get().nazev();
        }

        return searchAddressesByStreet(kodUlice, adresniBod, nazevUlice, nazevObce);
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
