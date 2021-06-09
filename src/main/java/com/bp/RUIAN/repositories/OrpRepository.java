package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Orp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Repository for Orp records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface OrpRepository extends ElasticsearchRepository<Orp, Long> {
}
