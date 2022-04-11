package com.bp.ruian.utils;

/**
 * Utility class
 * <p>Stores names of elasticsearch configuration file's analyzers</p>
 * @author denisprn
 */
public final class EsAnalyzerNames {

    public static final String AUTOCOMPLETE_INDEX = "autocompleteIndexAnalyzer";
    public static final String AUTOCOMPLETE_SEARCH = "autocompleteSearchAnalyzer";
    public static final String HOUSE_NUMBERS = "houseNumbersAnalyzer";
    public static final String HOUSE_REFERENCE_SIGN = "houseReferenceSignAnalyzer";
    public static final String ZIP_CODE = "zipCodeAnalyzer";
    public static final String STANDARD = "standard";

    private EsAnalyzerNames() {
        throw new IllegalStateException("Utility class");
    }
}
