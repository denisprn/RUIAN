package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.AdresniMisto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Repository for AdresniMisto records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface AdresniMistoRepository extends ElasticsearchRepository<AdresniMisto, Long> {
}
