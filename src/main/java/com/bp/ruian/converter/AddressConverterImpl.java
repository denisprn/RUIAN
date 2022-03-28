package com.bp.ruian.converter;

import com.bp.ruian.record.Address;
import com.bp.ruian.utils.*;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Converts array of address values from CSV files to {@link Address}.
 * Converts from {@link SearchHit} to {@link Address}.
 * @author denisprn
 */
@Component
public class AddressConverterImpl implements AddressConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressConverterImpl.class);

    @NonNull
    @Override
    public Address convertFromArrayValues(@NonNull final String[] lineValues) {
        final Integer id = Integer.parseInt(lineValues[AddressValuePositions.ID]);

        final Integer municipalityid = Integer.parseInt(lineValues[AddressValuePositions.MUNICIPALITY_ID]);
        final String municipalityName = lineValues[AddressValuePositions.MUNICIPALITY_NAME];

        final Integer momcId = ValuesInspector.checkIfNotEmptyThenParseInt(lineValues[AddressValuePositions.MOMC_ID]);
        final String momcName = ValuesInspector.checkIfNotEmpty(lineValues[AddressValuePositions.MOMC_NAME]);

        final Integer mopId = ValuesInspector.checkIfNotEmptyThenParseInt(lineValues[AddressValuePositions.MOP_ID]);
        final String mopName = ValuesInspector.checkIfNotEmpty(lineValues[AddressValuePositions.MOP_NAME]);

        final Integer municipalityPartId = Integer.parseInt(lineValues[AddressValuePositions.MUNICIPALITY_PART_ID]);
        final String municipalityPartName = ValuesInspector.checkIfNotEmpty(lineValues[AddressValuePositions.MUNICIPALITY_PART_NAME]);

        final Integer streetId = ValuesInspector.checkIfNotEmptyThenParseInt(lineValues[AddressValuePositions.STREET_ID]);
        final String streetName = ValuesInspector.checkIfNotEmpty(lineValues[AddressValuePositions.STREET_NAME]);

        final String typeCO = lineValues[AddressValuePositions.TYPE_CO];

        final String houseNumber = lineValues[AddressValuePositions.HOUSE_NUMBER];
        final String houseReferenceNumber = ValuesInspector.checkIfNotEmpty(lineValues[AddressValuePositions.HOUSE_REFERENCE_NUMBER]);
        final String houseReferenceSign = ValuesInspector.checkIfNotEmpty(lineValues[AddressValuePositions.HOUSE_REFERENCE_SIGN]);

        final String zipCode = lineValues[AddressValuePositions.ZIP_CODE];

        final String yCoordinate = lineValues[AddressValuePositions.Y_COORDINATE];
        final String xCoordinate = lineValues[AddressValuePositions.X_COORDINATE];

        final String validSince = ValuesInspector.checkIfNotEmpty(lineValues[AddressValuePositions.VALID_SINCE]);

        LOGGER.info(LoggerMessages.ADDRESS_FROM_ARRAY_VALUES_CONVERTED_INFO);

        return new Address(id,
                municipalityid, municipalityName,
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
    @Override
    public Address convertFromSearchHit(@NonNull SearchHit searchHit) {
        Map<String, Object> searchHitSourceMap = searchHit.getSourceAsMap();

        final Integer id = MapUtils.convertValueToInteger(searchHitSourceMap, EsFieldNames.ID);

        final Integer municipalityId = MapUtils.convertValueToInteger(searchHitSourceMap, EsFieldNames.MUNICIPALITY_ID);
        final String municipalityName = MapUtils.convertValueToString(searchHitSourceMap, EsFieldNames.MUNICIPALITY_NAME);

        final Integer momcId = MapUtils.checkIfIntegerValueIsNotNull(searchHitSourceMap, EsFieldNames.MOMC_ID);
        final String momcName = MapUtils.checkIfStringValueIsNotNull(searchHitSourceMap, EsFieldNames.MOMC_NAME);

        final Integer mopId = MapUtils.checkIfIntegerValueIsNotNull(searchHitSourceMap, EsFieldNames.MOP_ID);
        final String mopName = MapUtils.checkIfStringValueIsNotNull(searchHitSourceMap, EsFieldNames.MOP_NAME);

        final Integer municipalityPartId = MapUtils.convertValueToInteger(searchHitSourceMap, EsFieldNames.MUNICIPALITY_PART_ID);
        final String municipalityPartName = MapUtils.convertValueToString(searchHitSourceMap, EsFieldNames.MUNICIPALITY_PART_NAME);

        final Integer streetId = MapUtils.checkIfIntegerValueIsNotNull(searchHitSourceMap, EsFieldNames.STREET_ID);
        final String streetName = MapUtils.checkIfStringValueIsNotNull(searchHitSourceMap, EsFieldNames.STREET_NAME);

        final String typeCO = MapUtils.convertValueToString(searchHitSourceMap, EsFieldNames.TYPE_CO);

        final String houseNumber = MapUtils.convertValueToString(searchHitSourceMap, EsFieldNames.HOUSE_NUMBER);
        final String houseReferenceNumber = MapUtils.checkIfStringValueIsNotNull(searchHitSourceMap, EsFieldNames.HOUSE_REFERENCE_NUMBER);
        final String houseReferenceSign = MapUtils.checkIfStringValueIsNotNull(searchHitSourceMap, EsFieldNames.HOUSE_REFERENCE_SIGN);

        final String zipCode = MapUtils.convertValueToString(searchHitSourceMap, EsFieldNames.ZIP_CODE);

        final String yCoordinate = MapUtils.convertValueToString(searchHitSourceMap, EsFieldNames.Y_COORDINATE);
        final String xCoordinate = MapUtils.convertValueToString(searchHitSourceMap, EsFieldNames.X_COORDINATE);

        final String validSince = MapUtils.convertValueToString(searchHitSourceMap, EsFieldNames.VALID_SINCE);

        LOGGER.info(LoggerMessages.ADDRESS_FROM_SEARCH_HIT_CONVERTED_INFO);

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
