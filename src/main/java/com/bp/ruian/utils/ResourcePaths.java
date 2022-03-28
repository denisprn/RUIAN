package com.bp.ruian.utils;

/**
 * Utility class
 * <p>Stores file paths</p>
 * @author denisprn
 */
public final class ResourcePaths {
    public static final String ADDRESSES_DIRECTORY = "./src/main/java/resources/addresses/";
    public static final String ARCHIVE_FILE_LOCATION = "./src/main/java/resources/addresses/Addresses.zip";

    private ResourcePaths() {
        throw new IllegalStateException("Utility class");
    }
}
