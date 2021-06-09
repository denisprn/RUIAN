package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Stat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Stat records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface StatRepository extends ElasticsearchRepository<Stat, Long> {
}
