package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.SpravniObvod;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SpravniObvodRepository extends ElasticsearchRepository<SpravniObvod, Long> {
}
