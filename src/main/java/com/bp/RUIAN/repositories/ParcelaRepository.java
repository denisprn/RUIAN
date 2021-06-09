package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Parcela;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Repository for Parcela records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface ParcelaRepository extends ElasticsearchRepository<Parcela, Long> {
}
