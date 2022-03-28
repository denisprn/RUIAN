package com.bp.ruian.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.lang.NonNull;

/**
 * Utility class
 * <p>Check values for null</p>
 * @author denisprn
 */
public final class ValuesInspector {
    @Nullable
    @Contract(pure = true)
    public static String checkIfNotEmpty(@NonNull String value) {
        return value.isEmpty() ? null : value;
    }

    @Nullable
    @Contract(pure = true)
    public static Integer checkIfNotEmptyThenParseInt(@NonNull String value) {
        return value.isEmpty() ? null : Integer.parseInt(value);
    }

    private ValuesInspector() {
        throw new IllegalStateException("Utility class");
    }
}
