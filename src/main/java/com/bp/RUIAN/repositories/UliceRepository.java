package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Ulice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Repository for Ulice records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface UliceRepository extends ElasticsearchRepository<Ulice, Long> {
}
