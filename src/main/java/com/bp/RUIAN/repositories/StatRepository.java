package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Stat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

public interface StatRepository extends ElasticsearchRepository<Stat, Long> {

}
