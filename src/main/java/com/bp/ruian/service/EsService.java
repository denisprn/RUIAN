package com.bp.ruian.service;

import com.bp.ruian.record.Address;

import java.util.List;
import java.util.Optional;

/**
 * Interface for Elasticsearch service
 * @author denisprn
 */
public interface EsService {
    List<Address> findAddressesBySearchString(String searchString);

    Optional<Address> findAddressById(Integer id);

    void importAddresses();
}
