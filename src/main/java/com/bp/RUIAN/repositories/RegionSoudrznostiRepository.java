package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.RegionSoudrznosti;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Repository for RegionSoudrznosti records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface RegionSoudrznostiRepository extends ElasticsearchRepository<RegionSoudrznosti, Long> {
}
