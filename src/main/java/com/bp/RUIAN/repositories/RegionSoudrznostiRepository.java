package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.RegionSoudrznosti;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RegionSoudrznostiRepository extends ElasticsearchRepository<RegionSoudrznosti, Long> {
}
