package com.bp.ruian.converter;

import com.bp.ruian.record.Address;
import com.bp.ruian.services.converter.AddressConverter;
import com.bp.ruian.services.converter.AddressConverterImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link AddressConverterImpl} functionality
 * @author denisprn
 */
@ContextConfiguration(classes = {
        AddressConverterImpl.class
})
@SpringBootTest
class AddressConverterImplTest {

    @Autowired
    private AddressConverter addressConverter;

    @Test
    @DisplayName("Test address converting")
    void convert() {
        String[] completeAddressValues = prepareCompleteAddressValues();
        String[] incompleteAddressValues = prepareIncompleteAddressValues();
        Address completeAddressExpected = prepareCompleteAddress();
        Address incompleteAddressExpected = prepareIncompleteAddress();
        Address completeAddressActual = addressConverter.convertFromArrayValues(completeAddressValues);
        Address incompleteAddressActual = addressConverter.convertFromArrayValues(incompleteAddressValues);

        assertThat(completeAddressActual).isNotNull().isEqualTo(completeAddressExpected);
        assertThat(incompleteAddressExpected).isNotNull().isEqualTo(incompleteAddressActual);
    }

    @NonNull
    private String[] prepareCompleteAddressValues() {
        return new String[] {"74979388", "554782", "Praha", "500143", "Praha 5", "51", "Praha 5",
                "400289", "Hlubočepy", "443204", "Barrandovská", "č.p.", "1236", "56", "b",
                "15200", "745365.84", "1048811.12", "2016-05-09T00:00:00"};
    }

    @NonNull
    private String[] prepareIncompleteAddressValues() {
        return new String[] {"74979388", "554782", "Praha", "", "", "", "",
                "400289", "Hlubočepy", "", "", "č.p.", "1236", "", "",
                "15200", "745365.84", "1048811.12", "2016-05-09T00:00:00"};
    }

    @NonNull
    private Address prepareCompleteAddress() {
        Integer id = 74979388;
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

    @NonNull
    private Address prepareIncompleteAddress() {
        Integer id = 74979388;
        Integer municipalityId = 554782;
        String municipalityName = "Praha";
        Integer municipalityPartId = 400289;
        String municipalityPartName = "Hlubočepy";
        String typeCO = "č.p.";
        String houseNumber = "1236";
        String zipCode = "15200";
        String yCoordinate = "745365.84";
        String xCoordinate = "1048811.12";
        String validSince = "2016-05-09T00:00:00";

        return new Address(id,
                municipalityId, municipalityName,
                null, null,
                null, null,
                municipalityPartId, municipalityPartName,
                null, null,
                typeCO,
                houseNumber, null, null,
                zipCode,
                yCoordinate, xCoordinate,
                validSince);
    }
}
