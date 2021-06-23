package com.bp.RUIAN.services;

import com.bp.RUIAN.FilesParser;
import com.bp.RUIAN.entities.Obec;
import com.bp.RUIAN.repositories.AdresniMistoRepository;
import com.bp.RUIAN.repositories.ObecRepository;
import com.bp.RUIAN.repositories.UliceRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EsServiceTest {
    @Autowired
    RestHighLevelClient esClient;
    @Autowired
    EsService esService;
    @Autowired
    UliceRepository uliceRepository;
    @Autowired
    AdresniMistoRepository adresniMistoRepository;
    @Autowired
    ObecRepository obecRepository;

    @BeforeAll
    public void parse() throws IOException {
        File xmlDirectory = new ClassPathResource("/xml/").getFile();
        String xmlDirectoryPath = xmlDirectory.getAbsolutePath();

        FilesParser filesParser = new FilesParser(esService);
        filesParser.walk(xmlDirectoryPath);
    }

    public String searchAdresy(SearchHit hit, String nazevUlice, String nazevObce) {
        String cisloO = "", cisloOP = "";
        Map<String, Object> sourceAsMap = hit.getSourceAsMap();
        String cisloD = sourceAsMap.get("cisloDomovni").toString();

        if (sourceAsMap.get("cisloOrientacni") != null) {
            cisloO = sourceAsMap.get("cisloOrientacni").toString();
            cisloO += "/";
        }

        if (sourceAsMap.get("cisloOrientacniPismeno") != null) {
            cisloOP = sourceAsMap.get("cisloOrientacniPismeno").toString();
        }

        return String.format("<b>%s %s%s%s</b><br>Adresa, %s", nazevUlice, cisloO,
                cisloD, cisloOP, nazevObce);
    }

    @Test
    @DisplayName("Should find the address, its street and the municipality")
    public void search() throws IOException {
        String searchString = "4 kvetna 5";
        List<String> found = new ArrayList<>();
        String[] keys;

        // napr. '4. kvetna 5'
        if (searchString.matches("\\d*\\.*\\s*([a-žA-Ž]\\s*)+\\d+")) {
            Long idUlice;
            keys = searchString.split("\\s+");
            String result, nazevUlice, kodObce, nazevObce = "", cisloO = keys[keys.length - 1];
            StringBuilder nazevUliceFromSearch = new StringBuilder();

            for (int i = 0; i < keys.length - 1; i++) {
                if (i != keys.length - 2) {
                    nazevUliceFromSearch.append(keys[i]).append(" ");
                } else {
                    nazevUliceFromSearch.append(keys[i]);
                }
            }

            SearchRequest searchRequest = new SearchRequest("ulice");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery("nazev", nazevUliceFromSearch.toString())
                    .fuzziness(2));

            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

            if (searchResponse.getHits().getHits().length > 0) {
                SearchHit uliceHit = searchResponse.getHits().getHits()[0];
                Map<String, Object> sourceAsMap = uliceHit.getSourceAsMap();
                idUlice = Long.parseLong(sourceAsMap.get("kod").toString());
                nazevUlice = sourceAsMap.get("nazev").toString();

                if (sourceAsMap.get("kodObce") != null) {
                    kodObce = sourceAsMap.get("kodObce").toString();

                    Optional<Obec> obec = obecRepository.findById(Long.parseLong(kodObce));

                    if (obec.isPresent()) {
                        nazevObce = obec.get().nazev();
                    }
                }

                searchRequest = new SearchRequest("adresnimisto");
                searchSourceBuilder = new SearchSourceBuilder();
                BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
                boolQueryBuilder
                        .must(QueryBuilders.matchQuery("uliceKod", Integer.parseInt(idUlice.toString())))
                        .should(QueryBuilders.matchQuery("cisloOrientacni", cisloO));

                searchSourceBuilder.query(boolQueryBuilder);
                searchRequest.source(searchSourceBuilder);
                searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

                for (SearchHit hit : searchResponse.getHits().getHits()) {
                    result = searchAdresy(hit, nazevUlice, nazevObce);

                    found.add(result);
                }
            }
        }

        List<String> addresses = new ArrayList<>();
        String firstAddress = "<b>4. května 55/668A</b><br>Adresa, Maleč";
        String secondAddress = "<b>4. května 56/667B</b><br>Adresa, Maleč";
        String thirdAddress = "<b>4. května 1/669A</b><br>Adresa, Maleč";
        addresses.add(firstAddress);
        addresses.add(secondAddress);
        addresses.add(thirdAddress);

        assertEquals(addresses, found);
    }
}
