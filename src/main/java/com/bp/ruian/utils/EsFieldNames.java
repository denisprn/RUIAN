package com.bp.ruian.utils;

/**
 * Utility class
 * <p>Stores names of Elasticsearch {@link com.bp.ruian.record.Address} fields</p>
 * @author denisprn
 */
public final class EsFieldNames {

    public static final String ID = "id";
    public static final String MUNICIPALITY_ID = "municipalityId";
    public static final String MUNICIPALITY_NAME = "municipalityName";
    public static final String MOMC_ID = "momcId";
    public static final String MOMC_NAME = "momcName";
    public static final String MOP_ID = "mopId";
    public static final String MOP_NAME = "mopName";
    public static final String MUNICIPALITY_PART_ID = "municipalityPartId";
    public static final String MUNICIPALITY_PART_NAME = "municipalityPartName";
    public static final String STREET_ID = "streetId";
    public static final String STREET_NAME = "streetName";
    public static final String TYPE_CO = "typeCO";
    public static final String HOUSE_NUMBER = "houseNumber";
    public static final String HOUSE_REFERENCE_NUMBER = "houseReferenceNumber";
    public static final String HOUSE_REFERENCE_SIGN = "houseReferenceSign";
    public static final String ZIP_CODE = "zipCode";
    public static final String Y_COORDINATE = "yCoordinate";
    public static final String X_COORDINATE = "xCoordinate";
    public static final String VALID_SINCE = "validSince";

    private EsFieldNames() {
        throw new IllegalStateException("Utility class");
    }
}
