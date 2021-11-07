package com.bp.RUIAN.repositories;

import com.bp.RUIAN.entities.Address;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories
public interface AddressRepository extends ElasticsearchRepository<Address, Integer> {

}
