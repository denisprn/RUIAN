package com.bp.ruian.uploader;

import com.bp.ruian.record.Address;
import com.bp.ruian.repository.AddressRepository;
import org.jetbrains.annotations.Unmodifiable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests bulk {@link Address} uploading
 * @author denisprn
 */
@SpringBootTest
class BulkAddressDataUploaderTest {
    @Autowired
    private BulkAddressDataUploader bulkAddressDataUploader;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Test bulk data uploading")
    void bulkUpload() {
        List<Address> addresses = prepareAddresses();
        bulkAddressDataUploader.bulkUpload(addresses);

        assertThat(addressRepository.existsById(1)).isTrue();
        assertThat(addressRepository.existsById(2)).isTrue();
        assertThat(addressRepository.existsById(3)).isTrue();
    }

    @NonNull
    private @Unmodifiable List<Address> prepareAddresses() {
        Address firstAddress = prepareAddressWithId(1);
        Address secondAddress = prepareAddressWithId(2);
        Address thirdAddress = prepareAddressWithId(3);

        return List.of(firstAddress, secondAddress, thirdAddress);
    }

    @NonNull
    private Address prepareAddressWithId(Integer id) {
        Integer municipalityId = 554782;
        String municipalityName = "Praha";
        Integer momcId = 500143;
        String momcName = "Praha 5";
        Integer mopId = 51;
        String mopName = "Praha 5";
        Integer municipalityPartId = 400289;
        String municipalityPartName = "Hlubočepy";
        Integer streetId = 443204;
        String streetName = "Barrandovská";
        String typeCO = "č.p.";
        String houseNumber = "1236";
        String houseReferenceNumber = "56";
        String houseReferenceSign = "b";
        String zipCode = "15200";
        String yCoordinate = "745365.84";
        String xCoordinate = "1048811.12";
        String validSince = "2016-05-09T00:00:00";

        return new Address(id,
                municipalityId, municipalityName,
                momcId, momcName,
                mopId, mopName,
                municipalityPartId, municipalityPartName,
                streetId, streetName,
                typeCO,
                houseNumber, houseReferenceNumber, houseReferenceSign,
                zipCode,
                yCoordinate, xCoordinate,
                validSince);
    }
}
