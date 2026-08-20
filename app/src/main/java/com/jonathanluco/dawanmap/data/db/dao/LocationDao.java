package com.jonathanluco.dawanmap.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jonathanluco.dawanmap.data.db.entity.LocationEntity;

import java.util.List;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM locations")
    List<LocationEntity> getAllLocations();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<LocationEntity> locations);
}