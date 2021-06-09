package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.CastObce;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Repository for CastObce records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface CastObceRepository extends ElasticsearchRepository<CastObce, Long> {
}
