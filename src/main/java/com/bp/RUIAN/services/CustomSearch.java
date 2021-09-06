package com.bp.RUIAN.services;

import com.bp.RUIAN.entities.AdresniMisto;
import com.bp.RUIAN.entities.Ulice;
import com.bp.RUIAN.repositories.AdresniMistoRepository;
import com.bp.RUIAN.repositories.ObecRepository;
import com.bp.RUIAN.repositories.UliceRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.*;

public class CustomSearch {
    private final RestHighLevelClient esClient;
    private final AdresniMistoRepository adresniMistoRepository;
    private final UliceRepository uliceRepository;
    private final ObecRepository obecRepository;
    private final List<String> found = new ArrayList<>();
    private final int DEFAULT_OBEC_KOD = 586846, DEFAULT_STREET_KOD = 171441;


    public CustomSearch(RestHighLevelClient esClient, AdresniMistoRepository adresniMistoRepository,
                        UliceRepository uliceRepository, ObecRepository obecRepository) {
        this.esClient = esClient;
        this.adresniMistoRepository = adresniMistoRepository;
        this.uliceRepository = uliceRepository;
        this.obecRepository = obecRepository;
    }

    public List<String> search(String searchString) throws IOException {
        boolean cisloDomExist = false, cisloOrExist = false, cisloOPExist = false,
                nameExist = false, obecExist = false, uliceExist = false,
                uliceInObecExist = false, addressExist = false;
        String cisloOr = "", cisloDom = "", cisloOrP = "";
        int kodUlice = 0;
        String nazevUlice = "", adresniBod = "", nazevObce = "";
        StringBuilder nameStrBuilder = new StringBuilder();
        List<String> addressTokens = new ArrayList<>();

        if (!found.isEmpty()) {
            found.clear();
        }

        String[] tokens = searchString.split("[,\\s/]+");

        // raspredelenie tokenov name or cisla
        for (String token : tokens) {
            if (token.matches("[a-žA-Ž]+") || token.matches("\\d+\\.")) {
                nameStrBuilder.append(token).append(" ");
            } else if (token.matches("\\d+")) {
                if (!cisloDomExist) {
                    cisloDomExist = true;
                    cisloDom = token;
                    addressTokens.add(cisloDom);
                } else {
                    cisloDom = token;
                    addressTokens.add(cisloDom);
                }
            } else if (token.matches("\\d+[a-zA-Z]*")) {
                cisloDom = token.replaceAll("[a-zA-Z]", "");
                addressTokens.add(cisloDom);
                cisloOrP = token.replaceAll("\\d+", "");
                addressTokens.add(cisloOrP);
            }
        }

        if (!nameStrBuilder.isEmpty()) {
            int kodObce = 0, kodObceUlice = 0;
            SearchRequest searchRequest = new SearchRequest("ulice", "obec");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(
                    QueryBuilders.matchQuery("nazev", nameStrBuilder.toString())
                            .fuzziness(2));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

            for (SearchHit hit : searchResponse.getHits().getHits()) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();

                if (hit.getIndex().equals("obec")) {
                    obecExist = true;
                    nazevObce = sourceAsMap.get("nazev").toString();
                    kodObce = Integer.parseInt(sourceAsMap.get("kod").toString());
                } else if (hit.getIndex().equals("ulice")) {
                    uliceExist = true;
                    nazevUlice = sourceAsMap.get("nazev").toString();
                    kodUlice = Integer.parseInt(sourceAsMap.get("kod").toString());
                    kodObceUlice = Integer.parseInt(sourceAsMap.get("kodObce").toString());

                    if (obecExist) {
                        if (kodObce == kodObceUlice) {
                            uliceInObecExist = true;
                        }
                    }
                }
            }

            if (uliceInObecExist || uliceExist) {
                if (!obecExist) {
                    nazevObce = obecRepository.findObecByKod(kodObceUlice).nazev();
                }

                if (addressTokens.isEmpty()) {
                    String address = String.format("<b>%s</b><br>Ulice, %s",
                            nazevUlice, nazevObce);
                    found.add(address);
                } else {
                    Page<AdresniMisto> adresniMista = findAddresses(addressTokens, kodUlice);

                    if (adresniMista != null) {
                        for (AdresniMisto adresniMisto : adresniMista) {
                            addAddressToFoundList(adresniMisto, nazevUlice, nazevObce);
                        }
                    }
                }
            } else if (obecExist) {
                if (addressTokens.isEmpty()) {
                    String address = String.format("<b>%s</b><br>Obec, Česká republika", nazevObce);
                    found.add(address);
                } else {
                    Page<Ulice> streetsByObec = uliceRepository.findUlicesByKodObce(kodObce, Pageable.ofSize(5));

                    for (Ulice ulice : streetsByObec) {
                        Page<AdresniMisto> adresniMista = findAddresses(addressTokens, ulice.kod());

                        if (adresniMista != null) {
                            nazevUlice = ulice.nazev();

                            for (AdresniMisto adresniMisto : adresniMista) {
                                addAddressToFoundList(adresniMisto, nazevUlice, nazevObce);
                            }
                        }
                    }
                }
            }
        } else if (!addressTokens.isEmpty()) {
            Page<AdresniMisto> adresniMista = findAddresses(addressTokens, DEFAULT_STREET_KOD);

            if (adresniMista != null) {
                int kodObce;

                for (AdresniMisto adresniMisto : adresniMista) {
                    kodUlice = adresniMisto.uliceKod();
                    nazevUlice = uliceRepository.findUliceByKod(kodUlice).nazev();
                    kodObce = uliceRepository.findUliceByKod(kodUlice).kodObce();
                    nazevObce = obecRepository.findObecByKod(kodObce).nazev();
                    addAddressToFoundList(adresniMisto, nazevUlice, nazevObce);
                }
            }
        }

        return found;
    }

    private Page<AdresniMisto> findAddresses(List<String> addressTokens, int kodUlice) {
        String cisloDom = "", cisloOr = "", cisloOP = "";
        boolean cisloDomExist = false, cisloOrExist = false, cisloOPExist = false;

        for (String s : addressTokens) {
            if (s.matches("\\d+")) {
                if (!cisloDomExist) {
                    cisloDom = s;
                    cisloDomExist = true;
                } else {
                    cisloOr = cisloDom;
                    cisloOrExist = true;
                    cisloDom = s;
                }
            }

            if (s.matches("[a-zA-Z]+")) {
                cisloOP = s;
                cisloOPExist = true;
            }
        }

        switch (addressTokens.size()) {
            case 1 -> {
                return adresniMistoRepository.findAdresniMistosByUliceKodAndCisloDomovniLike(
                        kodUlice, cisloDom, Pageable.ofSize(5));
            }

            case 2 -> {
                if (cisloDomExist && cisloOrExist) {
                    return adresniMistoRepository.
                            findAdresniMistosByUliceKodAndCisloOrientacniLikeAndCisloDomovniLike(
                            kodUlice, cisloOr, cisloDom, Pageable.ofSize(5));
                } else if (cisloDomExist && cisloOPExist) {
                    return adresniMistoRepository.
                            findAdresniMistosByUliceKodAndCisloDomovniLikeAndCisloOrientacniPismenoLike(
                                    kodUlice, cisloOr, cisloDom, Pageable.ofSize(5));
                }
            }

            default -> {
                if (cisloOPExist) {
                    return adresniMistoRepository.
                            findAdresniMistosByUliceKodAndAndCisloDomovniLikeAndCisloOrientacniLikeAndCisloOrientacniPismenoLike(
                                    kodUlice, cisloDom, cisloOr, cisloOP, Pageable.ofSize(5));
                } else {
                    return adresniMistoRepository.
                            findAdresniMistosByUliceKodAndCisloDomovniLikeAndCisloOrientacniPismenoLike(
                                    kodUlice, cisloOr, cisloDom, Pageable.ofSize(5));
                }
            }
        }

        return null;
    }

    public void addAddressToFoundList(AdresniMisto adresniMisto,
                                      String nazevUlice, String nazevObce) {
        String cisloDomovni = "", cisloOrientacni = "", cisloOrientacniPismeno = "";

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

        String adresa = String.format("<b>%s %s%s%s</b><br>Adresa, %s", nazevUlice, cisloOrientacni,
                cisloDomovni, cisloOrientacniPismeno, nazevObce);

        found.add(adresa);
    }

    /*public void addAddressesToFoundList(@NotNull Page<AdresniMisto> adresniMista,
                                        String nazevUlice, String nazevObce) {
        String cisloDomovni = "", cisloOrientacni = "", cisloOrientacniPismeno = "";

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

            String adresa = String.format("<b>%s %s%s%s</b><br>Adresa, %s", nazevUlice, cisloOrientacni,
                    cisloDomovni, cisloOrientacniPismeno, nazevObce);

            found.add(adresa);
        }
    }*/
}
