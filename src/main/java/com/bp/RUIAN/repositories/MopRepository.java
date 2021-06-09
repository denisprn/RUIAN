package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Mop;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MopRepository extends ElasticsearchRepository<Mop, Long> {
}
