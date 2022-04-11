package com.bp.ruian.services;

import com.bp.ruian.services.finder.AddressFinderImpl;
import com.bp.ruian.record.Address;
import com.bp.ruian.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Elasticsearch service
 * @author denisprn
 */
@Service
public class EsServiceImpl implements EsService {

    private final AddressFinderImpl addressFinderImpl;

    private final AddressRepository addressRepository;

    private final AddressesImportServiceImpl addressesImportServiceImpl;

    @Autowired
    public EsServiceImpl(AddressFinderImpl addressFinderImpl,
                         AddressRepository addressRepository,
                         AddressesImportServiceImpl addressesImportServiceImpl) {
        this.addressFinderImpl = addressFinderImpl;
        this.addressRepository = addressRepository;
        this.addressesImportServiceImpl = addressesImportServiceImpl;
    }

    @Override
    public List<Address> findAddressesBySearchString(String searchString) {
        return addressFinderImpl.find(searchString);
    }

    @Override
    public Optional<Address> findAddressById(Integer id) {
        return addressRepository.findById(id);
    }

    @Override
    public void importAddresses() {
        addressesImportServiceImpl.importAddressesFromRuianToEs();
    }
}
