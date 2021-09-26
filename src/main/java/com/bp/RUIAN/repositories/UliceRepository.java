package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Ulice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;

/**
 * Repository for Ulice records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface UliceRepository extends ElasticsearchRepository<Ulice, Long> {
    /*@Query("{\"match\": {\"query\": \"?0\", \"fields\": [\"nazev\"], \"fuzziness\": \"AUTO\"}}")
    Page<Ulice> findFuzzyByNazev(String nazev, Pageable pageable);*/
    Ulice findUliceByKod(Integer kod);
    List<Ulice> findUlicesByKodObce(Integer kodObce);
}
