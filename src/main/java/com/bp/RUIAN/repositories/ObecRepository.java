package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Obec;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ObecRepository extends ElasticsearchRepository<Obec, Long> {
}
