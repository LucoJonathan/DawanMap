package com.jonathanluco.dawanmap.data.api;

import com.jonathanluco.dawanmap.model.Location;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Définit les endpoints HTTP utilisés par l'application.
 */
public interface ApiService {

    /**
     * Récupère la liste des centres depuis l'API distante.
     *
     * @return appel Retrofit retournant la liste des centres.
     */
    @GET("public/location/")
    Call<List<Location>> getLocations();
}
