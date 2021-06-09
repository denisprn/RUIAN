package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Momc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Repository for Momc records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface MomcRepository extends ElasticsearchRepository<Momc, Long> {
}
