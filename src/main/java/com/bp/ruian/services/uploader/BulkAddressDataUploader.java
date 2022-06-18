package com.bp.ruian.services.uploader;

import com.bp.ruian.record.Address;

import java.util.List;

/**
 * Interface for bulk {@link Address} uploader
 * @author denisprn
 */
public interface BulkAddressDataUploader {

    void bulkUpload(List<Address> addresses);
}
