package com.jonathanluco.dawanmap.ui;

import java.util.Locale;

/**
 * Utilitaires de validation et formatage de coordonnées.
 */
public final class CoordinateFormatter {

    /**
     * Classe utilitaire non instanciable.
     */
    private CoordinateFormatter() {
    }

    /**
     * Vérifie si une latitude/longitude est exploitable.
     *
     * @param latitude latitude à valider.
     * @param longitude longitude à valider.
     * @return true si les deux valeurs sont finies.
     */
    public static boolean hasValidCoordinates(double latitude, double longitude) {
        return !Double.isNaN(latitude)
                && !Double.isNaN(longitude)
                && !Double.isInfinite(latitude)
                && !Double.isInfinite(longitude);
    }

    /**
     * Formate une coordonnée sur 6 décimales.
     *
     * @param value coordonnée à formater.
     * @param locale locale de formatage numérique.
     * @return coordonnée formatée.
     */
    public static String formatCoordinate(double value, Locale locale) {
        return String.format(locale, "%.6f", value);
    }
}
