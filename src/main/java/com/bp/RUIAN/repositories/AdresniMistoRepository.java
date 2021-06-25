package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.AdresniMisto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Repository for AdresniMisto records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface AdresniMistoRepository extends ElasticsearchRepository<AdresniMisto, Long> {
    Page<AdresniMisto>
            findAdresniMistosByUliceKodAndCisloDomovniLikeAndCisloOrientacniLikeAndCisloOrientacniPismenoLike(
            Integer uliceKod, String cisloDomovni, String cisloOrientacni,
            String cisloOrientacniPismeno, Pageable pageable);

    @Query("{ \"bool\" : { \"must\" : [ " +
            "{ \"query_string\" : { \"query\" : \"?0\", \"fields\" : [ \"uliceKod\" ] } } ], " +
            "\"should\" : [ { \"query_string\" : { \"query\" : \"?1*\", \"fields\" : [ \"cisloDomovni\" ] , " +
            "\"analyze_wildcard\": true } }, { \"query_string\" : { \"query\" : \"?1*\", " +
            "\"fields\" : [ \"cisloOrientacni\" ], \"analyze_wildcard\": true } } ] } }")
    Page<AdresniMisto> findByUliceKodWithCisloDomAndOrient(Integer uliceKod, String cislo, Pageable pageable);
}
