package com.bp.ruian.utils;

/**
 * Utility class
 * <p>Stores positions of address values after parsing CSV file in array</p>
 * @author denisprn
 */
public final class AddressValuePositions {
    public static final int ID = 0;
    public static final int MUNICIPALITY_ID = 1;
    public static final int MUNICIPALITY_NAME = 2;
    public static final int MOMC_ID = 3;
    public static final int MOMC_NAME = 4;
    public static final int MOP_ID = 5;
    public static final int MOP_NAME = 6;
    public static final int MUNICIPALITY_PART_ID = 7;
    public static final int MUNICIPALITY_PART_NAME = 8;
    public static final int STREET_ID = 9;
    public static final int STREET_NAME = 10;
    public static final int TYPE_CO = 11;
    public static final int HOUSE_NUMBER = 12;
    public static final int HOUSE_REFERENCE_NUMBER = 13;
    public static final int HOUSE_REFERENCE_SIGN = 14;
    public static final int ZIP_CODE = 15;
    public static final int Y_COORDINATE = 16;
    public static final int X_COORDINATE = 17;
    public static final int VALID_SINCE = 18;

    private AddressValuePositions() {
        throw new IllegalStateException("Utility class");
    }
}
