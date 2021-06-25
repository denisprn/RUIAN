package com.bp.RUIAN.services;

import com.bp.RUIAN.entities.AdresniMisto;
import com.bp.RUIAN.entities.Obec;
import com.bp.RUIAN.repositories.AdresniMistoRepository;
import com.bp.RUIAN.repositories.ObecRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Search {
    private final RestHighLevelClient esClient;
    private final AdresniMistoRepository adresniMistoRepository;
    private final ObecRepository obecRepository;

    public Search(RestHighLevelClient esClient, AdresniMistoRepository adresniMistoRepository,
                  ObecRepository obecRepository) {
        this.esClient = esClient;
        this.adresniMistoRepository = adresniMistoRepository;
        this.obecRepository = obecRepository;
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

    public List<String> searchAddressesByStreet(
            Integer kodUlice, String adresniBod, String nazevUlice, String nazevObce) {
        List<String> adresy = new ArrayList<>();
        String[] bod;
        String adresa, cisloDomovni, cisloOrientacni = "", cisloOrientacniPismeno = "";
        Page<AdresniMisto> adresniMista = null;

        if (adresniBod.matches("\\d+")) {
            adresniMista = adresniMistoRepository.
                    findByUliceKodWithCisloDomAndOrient(kodUlice, adresniBod, Pageable.ofSize(3));
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
            } else if (cisloDomovni.matches("\\d+")){
                adresniMista = adresniMistoRepository.
                        findAdresniMistosByUliceKodAndCisloDomovniStartingWithAndCisloOrientacniStartingWith(
                                kodUlice, cisloDomovni, cisloOrientacni, Pageable.ofSize(3));
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
}
