package com.bp.RUIAN.services;

import com.bp.RUIAN.entities.AdresniMisto;
import com.bp.RUIAN.entities.Obec;
import com.bp.RUIAN.entities.Ulice;
import com.bp.RUIAN.entities.Unit;
import com.bp.RUIAN.repositories.ObecRepository;
import com.bp.RUIAN.repositories.UliceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddressSearch {
    private final RestHighLevelClient esClient;
    private final UliceRepository uliceRepository;
    private final ObecRepository obecRepository;
    private final List<Unit> found;

    public AddressSearch(RestHighLevelClient esClient, UliceRepository uliceRepository, ObecRepository obecRepository) {
        this.esClient = esClient;
        this.uliceRepository = uliceRepository;
        this.obecRepository = obecRepository;
        this.found = new ArrayList<>();
    }

    public List<Unit> search(final String searchString) throws IOException {
        SearchRequest searchRequest = new SearchRequest("obec", "ulice", "adresnimisto");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(
                QueryBuilders.multiMatchQuery(searchString,"nazev","cisloDomovni",
                        "cisloOrientacni", "cisloOrientacniPismeno").fuzziness("AUTO"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        return findAddresses(searchHits);
    }

    private List<Unit> findAddresses(SearchHit @NotNull [] searchHits) {
        int limit = 0;
        List<SearchHit> obecHits = new ArrayList<>(), uliceHits = new ArrayList<>(),
                adresnimHits = new ArrayList<>();
        String kodObce, kodObceUlice, kodUlice, kodUliceAM;
        ObjectMapper mapper = new ObjectMapper();
        boolean obecExist, uliceExist, amExist;

        for (SearchHit searchHit : searchHits) {
            switch (searchHit.getIndex()) {
                case "obec" -> obecHits.add(searchHit);

                case "ulice" -> uliceHits.add(searchHit);

                case "adresnimisto" -> adresnimHits.add(searchHit);
            }
        }

        obecExist = !obecHits.isEmpty();
        uliceExist = !uliceHits.isEmpty();
        amExist = !adresnimHits.isEmpty();

        if (obecExist) {
            if (uliceExist && amExist) {
                for (SearchHit obecHit : obecHits) {
                    Map<String, Object> obecMap = obecHit.getSourceAsMap();
                    Obec obec = mapper.convertValue(obecMap, Obec.class);
                    kodObce = obecHit.getId();

                    for (SearchHit uliceHit : uliceHits) {
                        kodObceUlice = uliceHit.getSourceAsMap().get("kodObce").toString();

                        if (kodObce.equals(kodObceUlice)) {
                            Map<String, Object> uliceMap = uliceHit.getSourceAsMap();
                            Ulice ulice = mapper.convertValue(uliceMap, Ulice.class);
                            kodUlice = ulice.kod().toString();

                            for (SearchHit adresniMistoHit : adresnimHits) {
                                kodUliceAM = adresniMistoHit.getSourceAsMap().get("uliceKod").toString();

                                if (kodUliceAM.equals(kodUlice)) {
                                    Map<String, Object> amMap = adresniMistoHit.getSourceAsMap();
                                    AdresniMisto adresniMisto = mapper.convertValue(amMap, AdresniMisto.class);
                                    found.add(new Unit(adresniMisto, ulice, obec));

                                    if (limit++ == 5) {
                                        return found;
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (uliceExist) {
                for (SearchHit obecHit : obecHits) {
                    Map<String, Object> obecMap = obecHit.getSourceAsMap();
                    Obec obec = mapper.convertValue(obecMap, Obec.class);
                    kodObce = obecHit.getId();

                    for (SearchHit uliceHit : uliceHits) {
                        kodObceUlice = uliceHit.getSourceAsMap().get("kodObce").toString();

                        if (kodObce.equals(kodObceUlice)) {
                            Map<String, Object> uliceMap = uliceHit.getSourceAsMap();
                            Ulice ulice = mapper.convertValue(uliceMap, Ulice.class);
                            found.add(new Unit(null, ulice, obec));
                        }
                    }
                }
            } else if (amExist) {
                for (SearchHit obecHit : obecHits) {
                    Map<String, Object> obecMap = obecHit.getSourceAsMap();
                    Obec obec = mapper.convertValue(obecMap, Obec.class);
                    kodObce = obecHit.getId();
                    List<Ulice> uliceList = uliceRepository.findUlicesByKodObce(Integer.parseInt(kodObce));

                    for (Ulice ulice : uliceList) {
                        kodObceUlice = ulice.kodObce().toString();

                        if (kodObce.equals(kodObceUlice)) {
                            kodUlice = ulice.kod().toString();

                            for (SearchHit adresniMistoHit : adresnimHits) {
                                kodUliceAM = adresniMistoHit.getSourceAsMap().get("uliceKod").toString();

                                if (kodUliceAM.equals(kodUlice)) {
                                    Map<String, Object> amMap = adresniMistoHit.getSourceAsMap();
                                    AdresniMisto adresniMisto = mapper.convertValue(amMap, AdresniMisto.class);
                                    found.add(new Unit(adresniMisto, ulice, obec));

                                    if (limit++ == 5) {
                                        return found;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                for (SearchHit obecHit : obecHits) {
                    Map<String, Object> obecMap = obecHit.getSourceAsMap();
                    Obec obec = mapper.convertValue(obecMap, Obec.class);
                    found.add(new Unit(null, null, obec));
                }
            }
        } else if (uliceExist) {
            if (amExist) {
                for (SearchHit uliceHit : uliceHits) {
                    Map<String, Object> uliceMap = uliceHit.getSourceAsMap();
                    Ulice ulice = mapper.convertValue(uliceMap, Ulice.class);
                    kodUlice = ulice.kod().toString();
                    kodObceUlice = ulice.kodObce().toString();
                    Obec obec = obecRepository.findObecByKod(Integer.parseInt(kodObceUlice));

                    for (SearchHit adresniMistoHit : adresnimHits) {
                        kodUliceAM = adresniMistoHit.getSourceAsMap().get("uliceKod").toString();

                        if (kodUliceAM.equals(kodUlice)) {
                            Map<String, Object> amMap = adresniMistoHit.getSourceAsMap();
                            AdresniMisto adresniMisto = mapper.convertValue(amMap, AdresniMisto.class);
                            found.add(new Unit(adresniMisto, ulice, obec));

                            if (limit++ == 5) {
                                return found;
                            }
                        }
                    }
                }
            } else {
                for (SearchHit uliceHit : uliceHits) {
                    Map<String, Object> uliceMap = uliceHit.getSourceAsMap();
                    Ulice ulice = mapper.convertValue(uliceMap, Ulice.class);
                    kodObceUlice = ulice.kodObce().toString();
                    Obec obec = obecRepository.findObecByKod(Integer.parseInt(kodObceUlice));
                    found.add(new Unit(null, ulice, obec));

                    if (limit++ == 5) {
                        return found;
                    }
                }
            }
        } else if (amExist) {
            for (SearchHit adresniMistoHit : adresnimHits) {
                kodUliceAM = adresniMistoHit.getSourceAsMap().get("uliceKod").toString();
                Ulice ulice = uliceRepository.findUliceByKod(Integer.parseInt(kodUliceAM));
                kodObceUlice = ulice.kodObce().toString();
                Obec obec = obecRepository.findObecByKod(Integer.parseInt(kodObceUlice));
                Map<String, Object> amMap = adresniMistoHit.getSourceAsMap();
                AdresniMisto adresniMisto = mapper.convertValue(amMap, AdresniMisto.class);
                found.add(new Unit(adresniMisto, ulice, obec));

                if (limit++ == 5) {
                    return found;
                }
            }
        }

        return found;
    }
}
