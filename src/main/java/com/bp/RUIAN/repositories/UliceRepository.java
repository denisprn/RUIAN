package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Ulice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UliceRepository extends ElasticsearchRepository<Ulice, Long> {
}
