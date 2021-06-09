package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Momc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MomcRepository extends ElasticsearchRepository<Momc, Long> {
}
