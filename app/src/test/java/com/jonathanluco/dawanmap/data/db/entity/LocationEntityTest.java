package com.jonathanluco.dawanmap.data.db.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests unitaires de l'entité {@link LocationEntity}.
 */
public class LocationEntityTest {

    /**
     * Vérifie que le constructeur renseigne toutes les propriétés.
     */
    @Test
    public void constructor_shouldExposeAllValues() {
        LocationEntity entity = new LocationEntity(
                3,
                "Dawan Lyon",
                "8 avenue Exemple",
                45.764043,
                4.835659
        );

        assertEquals(3, entity.getId());
        assertEquals("Dawan Lyon", entity.getName());
        assertEquals("8 avenue Exemple", entity.getAddress());
        assertEquals(45.764043, entity.getLatitude(), 0.0);
        assertEquals(4.835659, entity.getLongitude(), 0.0);
    }
}
