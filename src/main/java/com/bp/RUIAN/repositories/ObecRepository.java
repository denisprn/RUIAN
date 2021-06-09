package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Obec;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Repository for Obec records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface ObecRepository extends ElasticsearchRepository<Obec, Long> {
}
