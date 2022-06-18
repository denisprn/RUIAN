package com.bp.ruian.repository;

import com.bp.ruian.record.Address;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Repository for {@link Address} records
 * @author denisprn
 */
public interface AddressRepository extends ElasticsearchRepository<Address, Integer> {}
