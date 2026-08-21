package com.jonathanluco.dawanmap.data.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entité Room persistée en base locale pour un centre.
 */
@Entity(tableName = "locations")
public class LocationEntity {

    @PrimaryKey
    private int id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;

    /**
     * Construit une entité de centre.
     *
     * @param id identifiant du centre.
     * @param name nom du centre.
     * @param address adresse du centre.
     * @param latitude latitude du centre.
     * @param longitude longitude du centre.
     */
    public LocationEntity(int id, String name, String address, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @return identifiant du centre.
     */
    public int getId() { return id; }

    /**
     * @return nom du centre.
     */
    public String getName() { return name; }

    /**
     * @return adresse du centre.
     */
    public String getAddress() { return address; }

    /**
     * @return latitude du centre.
     */
    public double getLatitude() { return latitude; }

    /**
     * @return longitude du centre.
     */
    public double getLongitude() { return longitude; }
}
