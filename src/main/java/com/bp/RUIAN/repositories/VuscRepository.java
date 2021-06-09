package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Vusc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Repository for Vusc records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface VuscRepository extends ElasticsearchRepository<Vusc, Long> {
}
