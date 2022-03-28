package com.bp.ruian.uploader;


import com.bp.ruian.record.Address;

import java.util.List;

/**
 * Interfacec for bulk {@link Address} uploader
 * @author denisprn
 */
public interface BulkAddressDataUploader {
    void bulkUpload(List<Address> addresses);
}
