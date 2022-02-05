package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.Address;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class AddressParser implements RecordParser<Address> {
    private final String[] lineValues;

    public AddressParser(String[] lineValues) {
        this.lineValues = lineValues;
    }

    @Contract(pure = true)
    private @Nullable String checkIfEmptyString(@NotNull String value) {
        return value.isEmpty() ? null : value;
    }

    private @Nullable Integer checkIfEmptyInteger(@NotNull String value) {
        return value.isEmpty() ? null : Integer.parseInt(value);
    }

    @Override
    public Address parse() throws ParseException {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String houseReferenceNumber, houseReferenceSign, houseIdentificationNumber;

        Integer id = Integer.parseInt(lineValues[0]);
        Integer municipalityId = Integer.parseInt(lineValues[1]);
        String municipalityName = lineValues[2];
        Integer momcId = checkIfEmptyInteger(lineValues[3]);
        String momcName = checkIfEmptyString(lineValues[4]);
        Integer mopId = checkIfEmptyInteger(lineValues[5]);
        String mopName = checkIfEmptyString(lineValues[6]);
        Integer municipalityPartId = checkIfEmptyInteger(lineValues[7]);
        String municipalityPartName = checkIfEmptyString(lineValues[8]);
        Integer streetId = checkIfEmptyInteger(lineValues[9]);
        String streetName = checkIfEmptyString(lineValues[10]);
        String typeSO = lineValues[11];
        String houseNumber = lineValues[12];

        if (!lineValues[13].isEmpty()) {
            houseReferenceNumber = lineValues[13];

            if (!lineValues[14].isEmpty()) {
                houseReferenceSign = lineValues[14];
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

        String zipCode = lineValues[15];
        String yCoordinate = lineValues[16];
        String xCoordinate = lineValues[17];
        Date validSince = sdf.parse(lineValues[18]);

        return new Address(id, municipalityId, municipalityName, momcId, momcName, mopId, mopName,
                municipalityPartId, municipalityPartName, streetId, streetName, typeSO, houseIdentificationNumber,
                houseNumber, houseReferenceNumber, houseReferenceSign, zipCode, yCoordinate, xCoordinate, validSince);
    }
}
