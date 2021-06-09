package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.StavebniObjekt;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Repository for StavebniObjekt records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface StavebniObjektRepository extends ElasticsearchRepository<StavebniObjekt, Long> {
}
