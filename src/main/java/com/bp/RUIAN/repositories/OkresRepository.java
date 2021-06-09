package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Okres;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface OkresRepository extends ElasticsearchRepository<Okres, Long> {
}
