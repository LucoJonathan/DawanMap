package com.jonathanluco.dawanmap.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jonathanluco.dawanmap.data.db.dao.LocationDao;
import com.jonathanluco.dawanmap.data.db.entity.LocationEntity;

/**
 * Base Room principale de l'application.
 */
@Database(entities = {LocationEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    /**
     * @return DAO d'accès aux localisations.
     */
    public abstract LocationDao locationDao();

    /**
     * Retourne l'instance singleton de base locale.
     *
     * @param context contexte Android.
     * @return instance unique de {@link AppDatabase}.
     */
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "dawan_database"
            ).allowMainThreadQueries().build(); // Permet les requêtes directes pour gagner du temps
        }
        return instance;
    }
}
