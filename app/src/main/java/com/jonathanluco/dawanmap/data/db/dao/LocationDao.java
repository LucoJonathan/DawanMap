package com.jonathanluco.dawanmap.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jonathanluco.dawanmap.data.db.entity.LocationEntity;

import java.util.List;

/**
 * Accès aux opérations SQL de la table des centres.
 */
@Dao
public interface LocationDao {

    /**
     * Lit toutes les localisations persistées en base locale.
     *
     * @return liste complète des centres.
     */
    @Query("SELECT * FROM locations")
    List<LocationEntity> getAllLocations();

    /**
     * Insère ou met à jour une liste de centres.
     *
     * @param locations centres à persister.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<LocationEntity> locations);
}