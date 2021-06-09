package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Mop;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Repository for Mop records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface MopRepository extends ElasticsearchRepository<Mop, Long> {
}
