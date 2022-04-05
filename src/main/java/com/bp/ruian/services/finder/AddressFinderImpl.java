package com.bp.ruian.services.finder;

import com.bp.ruian.services.converter.AddressConverterImpl;
import com.bp.ruian.record.Address;
import com.bp.ruian.utils.EsFieldNames;
import com.bp.ruian.utils.LoggerMessages;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * Fuzzy {@link Address} search for a given string
 * @author denisprn
 */
@Component
public class AddressFinderImpl implements AddressFinder {
    @Value("${elastic.index}")
    private String indexName;
    private final RestHighLevelClient esClient;
    private final AddressConverterImpl addressConverter;
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressFinderImpl.class);

    @Autowired
    public AddressFinderImpl(RestHighLevelClient esClient, AddressConverterImpl addressConverter) {
        this.esClient = esClient;
        this.addressConverter = addressConverter;
    }

    @Override
    public List<Address> find(final String searchString) {
        Map<String, Float> addressFields = getBoostedSearchFields();

        SearchRequest searchRequest = new SearchRequest(indexName);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.multiMatchQuery(searchString)
                        .fields(addressFields)
                        .type(MultiMatchQueryBuilder.Type.MOST_FIELDS)
                        .operator(Operator.OR)
                        .fuzziness(Fuzziness.AUTO)
                        .fuzzyRewrite("top_terms_boost_1")
                ).size(5);

        searchRequest.source(searchSourceBuilder);

        try {
            final SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
            final SearchHit[] searchHits = searchResponse.getHits().getHits();

            return Arrays.stream(searchHits).map(addressConverter::convertFromSearchHit).toList();
        } catch (IOException exception) {
            final String formattedMessage =
                    String.format("%s. %s", LoggerMessages.SEARCH_ERROR, exception.getMessage());

            LOGGER.error(formattedMessage);
        }

        return Collections.emptyList();
    }

    @NonNull
    private Map<String, Float> getBoostedSearchFields() {
        Map<String, Float> addressFields = new HashMap<>();
        addressFields.put(EsFieldNames.MUNICIPALITY_PART_NAME, 5F);
        addressFields.put(EsFieldNames.MUNICIPALITY_NAME, 5F);
        addressFields.put(EsFieldNames.STREET_NAME, 5F);
        addressFields.put(EsFieldNames.HOUSE_NUMBER, 10F);
        addressFields.put(EsFieldNames.HOUSE_REFERENCE_NUMBER, 5F);
        addressFields.put(EsFieldNames.HOUSE_REFERENCE_SIGN, 5F);
        addressFields.put(EsFieldNames.TYPE_CO, 3F);
        addressFields.put(EsFieldNames.ZIP_CODE, 8F);

        return addressFields;
    }
}

