package com.jonathanluco.dawanmap.ui;

import java.util.Locale;

public final class CoordinateFormatter {

    private CoordinateFormatter() {
    }

    public static boolean hasValidCoordinates(double latitude, double longitude) {
        return !Double.isNaN(latitude)
                && !Double.isNaN(longitude)
                && !Double.isInfinite(latitude)
                && !Double.isInfinite(longitude);
    }

    public static String formatCoordinate(double value, Locale locale) {
        return String.format(locale, "%.6f", value);
    }
}
