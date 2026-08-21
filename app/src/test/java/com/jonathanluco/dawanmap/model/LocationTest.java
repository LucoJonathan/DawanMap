package com.jonathanluco.dawanmap.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LocationTest {

    @Test
    public void settersAndGetters_shouldKeepValues() {
        Location location = new Location();

        location.setId(7);
        location.setName("Dawan Nantes");
        location.setAddress("12 rue Test");
        location.setLatitude(47.218371);
        location.setLongitude(-1.553621);

        assertEquals(7, location.getId());
        assertEquals("Dawan Nantes", location.getName());
        assertEquals("12 rue Test", location.getAddress());
        assertEquals(47.218371, location.getLatitude(), 0.0);
        assertEquals(-1.553621, location.getLongitude(), 0.0);
    }
}
