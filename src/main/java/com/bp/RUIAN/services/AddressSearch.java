package com.bp.RUIAN.services;

import com.bp.RUIAN.entities.Unit;
import com.bp.RUIAN.repositories.ObecRepository;
import com.bp.RUIAN.repositories.UliceRepository;
import com.bp.RUIAN.services.finders.UnitsFinder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class AddressSearch {
    private final RestHighLevelClient esClient;
    private final UliceRepository uliceRepository;
    private final ObecRepository obecRepository;

    public AddressSearch(RestHighLevelClient esClient,
                         UliceRepository uliceRepository, ObecRepository obecRepository) {
        this.esClient = esClient;
        this.uliceRepository = uliceRepository;
        this.obecRepository = obecRepository;
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
        List<SearchHit> obecHits = Arrays.stream(searchHits)
                .filter(o -> o.getIndex().equals("obec"))
                .toList();

        List<SearchHit> uliceHits = Arrays.stream(searchHits)
                .filter(u -> u.getIndex().equals("ulice"))
                .toList();

        List<SearchHit> adresnimHits = Arrays.stream(searchHits)
                .filter(a -> a.getIndex().equals("adresnimisto"))
                .toList();

        UnitsFinder unitsFinder = new UnitsFinder(obecHits, uliceHits, adresnimHits,
                uliceRepository, obecRepository);

        return unitsFinder.find();
    }
}

