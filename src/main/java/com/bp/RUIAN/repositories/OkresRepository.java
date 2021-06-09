package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Okres;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Repository for Okres records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface OkresRepository extends ElasticsearchRepository<Okres, Long> {
}
