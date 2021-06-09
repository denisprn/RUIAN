package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.KatastralniUzemi;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface KatastralniUzemiRepository extends ElasticsearchRepository<KatastralniUzemi, Long> {
}
