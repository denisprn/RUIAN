package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.VolebniOkrsek;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Repository for VolebniOkrsek records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface VolebniOkrsekRepository extends ElasticsearchRepository<VolebniOkrsek, Long> {
}
