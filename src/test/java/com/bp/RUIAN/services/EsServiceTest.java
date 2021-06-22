package com.bp.RUIAN.services;

import com.bp.RUIAN.FilesParser;
import com.bp.RUIAN.entities.AdresniMisto;
import com.bp.RUIAN.entities.Obec;
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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

    @Test
    @DisplayName("Should find the address, its street and the municipality")
    public void search() throws IOException {
        String searchString = "668 55";
        SearchRequest searchRequest = new SearchRequest("adresnimisto");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(searchString,
                "cisloDomovni", "cisloOrientacni", "cisloOrientacniPismeno"));

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

        String resultActual = null;

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String hitIndexName = hit.getIndex();
            Long id = Long.parseLong(hit.getId());

            if ("adresnimisto".equals(hitIndexName)) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                String cisloD = (String) sourceAsMap.get("cisloDomovni");
                String cisloO = (String) sourceAsMap.get("cisloOrientacni");
                String cisloOP = (String) sourceAsMap.get("cisloOrientacniPismeno");

                Optional<AdresniMisto> adresniMisto = adresniMistoRepository.findById(id);

                if (adresniMisto.isPresent()) {
                    if (adresniMisto.get().uliceKod() != null) {
                        Long kodUlice = Long.parseLong(adresniMisto.get().uliceKod().toString());
                        Optional<Ulice> ulice = uliceRepository.findById(kodUlice);

                        if (ulice.isPresent()) {
                            String nazevUlice = ulice.get().nazev();
                            Long kodObce = Long.parseLong(ulice.get().kodObce().toString());
                            Optional<Obec> obec = obecRepository.findById(kodObce);

                            if (obec.isPresent()) {
                                String nazevObce = obec.get().nazev();

                                resultActual = String.format("<b>%s %s/%s%s</b><br>Adresa, %s", nazevUlice, cisloO,
                                        cisloD, cisloOP, nazevObce);
                            } else {
                                resultActual = String.format("<b>%s %s/%s%s</b><br>Adresa", nazevUlice, cisloO,
                                        cisloD, cisloOP);
                            }
                        }
                    } else {
                        resultActual = String.format("<b>%s/%s%s</b><br>Adresa", cisloO,
                                cisloD, cisloOP);
                    }
                }
            }
        }

        String resultExpected = "<b>4. května 55/668A</b><br>Adresa, Maleč";
        assertEquals(resultExpected, resultActual);
    }
}
