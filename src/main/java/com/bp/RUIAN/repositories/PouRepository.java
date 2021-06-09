package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Pou;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PouRepository extends ElasticsearchRepository<Pou, Long> {
}
