package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Parcela;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ParcelaRepository extends ElasticsearchRepository<Parcela, Long> {
}
