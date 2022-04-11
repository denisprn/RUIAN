package com.bp.ruian.utils;

import org.jetbrains.annotations.Nullable;
import org.springframework.lang.NonNull;

import java.util.Map;

/**
 * Utility class
 * <p>Check/convert map values</p>
 * @author denisprn
 */
public final class MapUtils {

    @Nullable
    public static String checkIfStringValueIsNotNull(@NonNull Map<String, Object> map, @NonNull String value) {
        return map.get(value) != null ? map.get(value).toString() : null;
    }

    @Nullable
    public static Integer checkIfIntegerValueIsNotNull(@NonNull Map<String, Object> map, @NonNull String value) {
        return map.get(value) != null ? Integer.parseInt(map.get(value).toString()) : null;
    }

    @NonNull
    public static String convertValueToString(@NonNull Map<String, Object> map, @NonNull String value) {
        return map.get(value).toString();
    }

    @NonNull
    public static Integer convertValueToInteger(@NonNull Map<String, Object> map, @NonNull String value) {
        return Integer.parseInt(map.get(value).toString());
    }

    private MapUtils() {
        throw new IllegalStateException("Utility class");
    }
}
