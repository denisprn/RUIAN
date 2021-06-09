package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Zsj;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


/**
 * Repository for Zsj records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface ZsjRepository extends ElasticsearchRepository<Zsj, Long> {
}
