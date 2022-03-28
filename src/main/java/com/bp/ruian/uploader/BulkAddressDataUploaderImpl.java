package com.bp.ruian.uploader;

import com.bp.ruian.record.Address;
import com.bp.ruian.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Bulk {@link Address} uploader
 * @author denisprn
 */
@Component
public class BulkAddressDataUploaderImpl implements BulkAddressDataUploader {
    private final AddressRepository addressRepository;

    @Autowired
    public BulkAddressDataUploaderImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void bulkUpload(List<Address> addresses) {
        addressRepository.saveAll(addresses);
    }
}
