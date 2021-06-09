package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Vusc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface VuscRepository extends ElasticsearchRepository<Vusc, Long> {
}
