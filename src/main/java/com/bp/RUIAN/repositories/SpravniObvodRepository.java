package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.SpravniObvod;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Repository for SpravniObvod records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface SpravniObvodRepository extends ElasticsearchRepository<SpravniObvod, Long> {
}
