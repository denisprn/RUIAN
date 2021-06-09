package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Orp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface OrpRepository extends ElasticsearchRepository<Orp, Long> {
}
