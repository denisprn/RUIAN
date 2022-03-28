package com.bp.ruian.utils;

/**
 * Utility class
 * <p>Stores exceptions' names</p>
 * @author denisprn
 */
public final class ExceptionMessages {
    public static final String DIRECTORY_CREATION_ERROR = "Failed to create directory ";
    public static final String ENTRY_OUTSIDE_DIRECTORY_ERROR = "Entry is outside of the target directory: ";

    private ExceptionMessages() {
        throw new IllegalStateException("Utility class");
    }
}
