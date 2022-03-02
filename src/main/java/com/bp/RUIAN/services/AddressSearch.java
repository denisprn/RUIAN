package com.bp.RUIAN.services;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class AddressSearch {
    private final RestHighLevelClient esClient;

    @Autowired
    public AddressSearch(RestHighLevelClient esClient) {
        this.esClient = esClient;
    }

    public List<String> search(final String searchString) throws IOException {
        Map<String, Float> addressFields = new HashMap<>();
        addressFields.put("houseNumber", 7F);
        addressFields.put("zipCode", 6F);
        addressFields.put("municipalityPartName", 5F);
        addressFields.put("streetName", 5F);
        addressFields.put("municipalityName", 4F);
        addressFields.put("houseReferenceNumber", 2F);
        addressFields.put("houseReferenceSign", 2F);
        addressFields.put("typSO", 3F);

        SearchRequest searchRequest = new SearchRequest("address");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.multiMatchQuery(searchString)
                        .fields(addressFields)
                        .type(MultiMatchQueryBuilder.Type.MOST_FIELDS)
                        .prefixLength(3)
                        .fuzziness(Fuzziness.AUTO)
                ).size(5);

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        return Arrays.stream(searchHits).map(this::getHitsMainInfo).toList();
    }

    private String getHitsMainInfo(@NotNull SearchHit searchHit) {
        String address;
        Map<String, Object> map = searchHit.getSourceAsMap();
        String score = String.valueOf(searchHit.getScore());
        final String municipalityName = map.get("municipalityName").toString();
        final String municipalityPartName = map.get("municipalityPartName").toString();
        final String zipCode = map.get("zipCode").toString();
        final String houseIdentificationNumber = map.get("houseIdentificationNumber").toString();

        if (map.get("streetName") != null) {
            final String streetName = map.get("streetName").toString();
            address = String.format("%s %s, %s %s, %s (%s)",
                    streetName, houseIdentificationNumber, zipCode, municipalityPartName, municipalityName, score);
        } else {
            final String typeSO = map.get("typeSO").toString();
            address = String.format("%s %s, %s %s, %s (%s)",
                    typeSO, houseIdentificationNumber, zipCode, municipalityPartName, municipalityName, score);
        }

        return address;
    }
}

