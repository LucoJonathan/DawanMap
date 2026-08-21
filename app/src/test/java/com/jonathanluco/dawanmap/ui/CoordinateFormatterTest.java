package com.jonathanluco.dawanmap.ui;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests unitaires des utilitaires de coordonnées.
 */
public class CoordinateFormatterTest {

    /**
     * Vérifie le rejet des coordonnées invalides.
     */
    @Test
    public void hasValidCoordinates_shouldRejectNaNAndInfinity() {
        assertFalse(CoordinateFormatter.hasValidCoordinates(Double.NaN, 2.0));
        assertFalse(CoordinateFormatter.hasValidCoordinates(2.0, Double.NaN));
        assertFalse(CoordinateFormatter.hasValidCoordinates(Double.POSITIVE_INFINITY, 2.0));
        assertFalse(CoordinateFormatter.hasValidCoordinates(2.0, Double.NEGATIVE_INFINITY));
        assertTrue(CoordinateFormatter.hasValidCoordinates(47.218371, -1.553621));
    }

    /**
     * Vérifie le format à 6 décimales.
     */
    @Test
    public void formatCoordinate_shouldKeepSixDecimals() {
        String lat = CoordinateFormatter.formatCoordinate(47.2183712, Locale.US);
        String lon = CoordinateFormatter.formatCoordinate(-1.5536214, Locale.US);

        assertEquals("47.218371", lat);
        assertEquals("-1.553621", lon);
    }
}
