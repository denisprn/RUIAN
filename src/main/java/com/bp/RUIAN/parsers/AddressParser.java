package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.Address;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class AddressParser implements RecordParser<Address> {
    private final String[] line;
    private final SimpleDateFormat sdf;

    public AddressParser(String[] line) {
        this.line = line;
        this.sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        this.sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    private String checkIfEmptyString(String value) {
        return value.isEmpty() ? null : value;
    }

    private Integer checkIfEmptyInteger(String value) {
        return value.isEmpty() ? null : Integer.parseInt(value);
    }

    @Override
    public Address parse() throws ParseException {
        String houseReferenceNumber, houseReferenceSign, houseIdentificationNumber;
        Integer id = Integer.parseInt(line[0]);
        Integer municipalityId = Integer.parseInt(line[1]);
        String municipalityName = line[2];
        Integer momcId = checkIfEmptyInteger(line[3]);
        String momcName = checkIfEmptyString(line[4]);
        Integer mopId = checkIfEmptyInteger(line[5]);
        String mopName = checkIfEmptyString(line[6]);
        Integer municipalityPartId = checkIfEmptyInteger(line[7]);
        String municipalityPartName = checkIfEmptyString(line[8]);
        Integer streetId = checkIfEmptyInteger(line[9]);
        String streetName = checkIfEmptyString(line[10]);
        String typeSO = line[11];
        String houseNumber = line[12];

        if (!line[13].isEmpty()) {
            houseReferenceNumber = line[13];

            if (!line[14].isEmpty()) {
                houseReferenceSign = line[14];
                houseIdentificationNumber = String.format("%s/%s%s", houseReferenceNumber, houseNumber, houseReferenceSign);
            } else {
                houseReferenceSign = null;
                houseIdentificationNumber = String.format("%s/%s", houseReferenceNumber, houseNumber);
            }
        } else {
            houseReferenceNumber = null;
            houseReferenceSign = null;
            houseIdentificationNumber = houseNumber;
        }

        String zipCode = line[15];
        String yCoordinate = line[16];
        String xCoordinate = line[17];
        Date validSince = sdf.parse(line[18]);

        return new Address(id, municipalityId, municipalityName, momcId, momcName, mopId, mopName,
                municipalityPartId, municipalityPartName, streetId, streetName, typeSO, houseIdentificationNumber,
                houseNumber, houseReferenceNumber, houseReferenceSign, zipCode, yCoordinate, xCoordinate, validSince);
    }
}
