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

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddressSearch {
    private final RestHighLevelClient esClient;

    public AddressSearch(RestHighLevelClient esClient) {
        this.esClient = esClient;
    }

    public List<String> search(final String searchString) throws IOException {
        Map<String, Float> fields = new HashMap<>();
        fields.put("municipalityName", 45F);
        fields.put("municipalityPartName", 50F);
        fields.put("streetName", 40F);
        fields.put("houseIdentificationNumber", 50F);
        fields.put("houseNumber", 50F);
        fields.put("houseReferenceNumber", 35F);
        fields.put("houseReferenceSign", 35F);
        fields.put("zipCode", 25F);

        SearchRequest searchRequest = new SearchRequest("address");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.multiMatchQuery(searchString)
                        .fields(fields)
                        .prefixLength(3)
                        .type(MultiMatchQueryBuilder.Type.MOST_FIELDS)
                        .fuzziness(Fuzziness.TWO))
                .size(5);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        return Arrays.stream(searchHits).map(this::getHitsMainInfo).toList();
    }

    private String getHitsMainInfo(SearchHit searchHit) {
        String address;
        Map<String, Object> map = searchHit.getSourceAsMap();
        String municipalityName = map.get("municipalityName").toString();
        String municipalityPartName = map.get("municipalityPartName").toString();
        String zipCode = map.get("zipCode").toString();
        String houseIdentificationNumber = map.get("houseIdentificationNumber").toString();

        if (map.get("streetName") != null) {
            String streetName = map.get("streetName").toString();
            address = String.format("%s %s, %s %s, %s",
                    streetName, houseIdentificationNumber, zipCode, municipalityPartName, municipalityName);
        } else {
            String typeSO = map.get("typeSO").toString();
            address = String.format("%s %s, %s %s, %s",
                    typeSO, houseIdentificationNumber, zipCode, municipalityPartName, municipalityName);
        }

        return address;
    }
}

