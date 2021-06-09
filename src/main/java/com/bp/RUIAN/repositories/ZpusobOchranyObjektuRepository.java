package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.ZpusobOchranyObjektu;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Repository for ZpusobOchranyObjektu records
 * @author Denys Peresychanskyi
 */
@EnableElasticsearchRepositories
public interface ZpusobOchranyObjektuRepository extends ElasticsearchRepository<ZpusobOchranyObjektu, Long> {
}
