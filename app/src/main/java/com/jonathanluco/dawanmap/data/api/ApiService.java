package com.jonathanluco.dawanmap.data.api;

import com.jonathanluco.dawanmap.model.Location;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("public/location/")
    Call<List<Location>> getLocations();
}
