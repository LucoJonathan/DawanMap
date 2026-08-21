package com.jonathanluco.dawanmap.model;

/**
 * Représente un centre avec ses informations de localisation.
 */
public class Location {

    private int id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;

    /**
     * @return identifiant unique du centre.
     */
    public int getId() { return id; }

    /**
     * @param id identifiant unique du centre.
     */
    public void setId(int id) { this.id = id; }

    /**
     * @return nom du centre.
     */
    public String getName() { return name; }

    /**
     * @param name nom du centre.
     */
    public void setName(String name) { this.name = name; }

    /**
     * @return adresse du centre.
     */
    public String getAddress() { return address; }

    /**
     * @param address adresse du centre.
     */
    public void setAddress(String address) { this.address = address; }

    /**
     * @return latitude du centre.
     */
    public double getLatitude() { return latitude; }

    /**
     * @param latitude latitude du centre.
     */
    public void setLatitude(double latitude) { this.latitude = latitude; }

    /**
     * @return longitude du centre.
     */
    public double getLongitude() { return longitude; }

    /**
     * @param longitude longitude du centre.
     */
    public void setLongitude(double longitude) { this.longitude = longitude; }
}