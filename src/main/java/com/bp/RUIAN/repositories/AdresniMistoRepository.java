package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.AdresniMisto;
import org.elasticsearch.common.unit.Fuzziness;
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
    /*Page<AdresniMisto> findAdresniMistosByUliceKodAndAndCisloDomovniLikeAndCisloOrientacniLikeAndCisloOrientacniPismenoLike(
            Integer uliceKod, String cisloDomovni, String cisloOrientacni,
            String cisloOrientacniPismeno, Pageable pageable);

    Page<AdresniMisto> findAdresniMistosByUliceKodAndCisloDomovniLikeAndCisloOrientacniPismenoLike(
            Integer uliceKod, String cisloDomovni, String cisloOrPismeno, Pageable pageable);

    Page<AdresniMisto> findAdresniMistosByUliceKodAndCisloDomovniLike(
            Integer uliceKod, String cisloDomovni, Pageable pageable);

    Page<AdresniMisto> findAdresniMistosByCisloOrientacni(String cisloOrientacni, Pageable pageable);

    Page<AdresniMisto> findAdresniMistosByUliceKodAndCisloOrientacniLikeAndCisloDomovniLike(
            Integer uliceKod, String cisloOrientacni, String cisloDomovni, Pageable pageable);

    Page<AdresniMisto> findAdresniMistosByUliceKodAndCisloOrientacniStartingWith(
            Integer uliceKod, String cisloOrientacni, Pageable pageable);

    Page<AdresniMisto>
            findAdresniMistosByUliceKodAndCisloOrientacniLikeAndCisloDomovniLikeAndCisloOrientacniPismenoLike(
            Integer uliceKod, String cisloOrientacni, String cisloDomovni,
            String cisloOrientacniPismeno, Pageable pageable);

    @Query("{ \"bool\" : { \"must\" : [ " +
            "{ \"query_string\" : { \"query\" : \"?0\", \"fields\" : [ \"uliceKod\" ] } } ], " +
            "\"should\" : [ { \"query_string\" : { \"query\" : \"?1*\", \"fields\" : [ \"cisloDomovni\" ] , " +
            "\"analyze_wildcard\": true } }, { \"query_string\" : { \"query\" : \"?1*\", " +
            "\"fields\" : [ \"cisloOrientacni\" ], \"analyze_wildcard\": true } } ] } }")
    Page<AdresniMisto> findByUliceKodWithCisloDomOrOrient(Integer uliceKod, String cislo, Pageable pageable);*/
}
