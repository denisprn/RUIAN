package com.bp.RUIAN.services;

import com.bp.RUIAN.entities.*;
import com.bp.RUIAN.repositories.*;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class EsService {
    private final RestHighLevelClient esClient;
    private final AddressRepository addressRepository;

    public EsService(RestHighLevelClient esClient, AddressRepository addressRepository) {
        this.esClient = esClient;
        this.addressRepository = addressRepository;
    }

    public List<String> search(String searchString) throws IOException {
        return new AddressSearch(esClient).search(searchString);
    }

    public void saveAddress(Address address) {
        addressRepository.save(address);
    }

    public Optional<Address> findAddressById(Integer id) {
        return addressRepository.findById(id);
    }
}
