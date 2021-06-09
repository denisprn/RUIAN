package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Pou;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Repository for Pou records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface PouRepository extends ElasticsearchRepository<Pou, Long> {
}
