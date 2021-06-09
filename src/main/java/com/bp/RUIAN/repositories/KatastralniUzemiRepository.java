package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.KatastralniUzemi;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Repository for KatastralniUzemi records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface KatastralniUzemiRepository extends ElasticsearchRepository<KatastralniUzemi, Long> {
}
