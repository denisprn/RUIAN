package com.bp.ruian.utils;

/**
 * Utility class
 * <p>Stores {@link org.slf4j.Logger} messages</p>
 * @author denisprn
 */
public final class LoggerMessages {

    public static final String CSV_PARSE_ERROR = "Failed to parse CSV data";
    public static final String SEARCH_ERROR = "Failed to search data";
    public static final String ADDRESS_FROM_ARRAY_VALUES_CONVERTED_INFO =
            "Address from array values has been converted successfully";
    public static final String ADDRESS_FROM_SEARCH_HIT_CONVERTED_INFO =
            "Address from search hit has been converted successfully";
    public static final String LAST_MONTHS_LAST_DAY_INFO =
            "Last month's last day is:";
    public static final String ARCHIVE_DOWNLOADING_STARTED =
            "Archive downloading started";
    public static final String ARCHIVE_DOWNLOADED_INFO =
            "Archive has been downloaded successfully";
    public static final String ARCHIVE_DOWNLOADING_ERROR =
            "Failed to download archive";

    private LoggerMessages() {
        throw new IllegalStateException("Utility class");
    }
}
