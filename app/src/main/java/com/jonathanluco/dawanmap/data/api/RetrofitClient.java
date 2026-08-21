package com.jonathanluco.dawanmap.data.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fournit une instance singleton de {@link ApiService}.
 */
public class RetrofitClient {

    private static final String BASE_URL = "https://dawan.org/";
    private static ApiService instance;

    /**
     * Retourne le client API unique initialisé avec Retrofit.
     *
     * @return instance singleton de service API.
     */
    public static ApiService getInstance() {
        if (instance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            instance = retrofit.create(ApiService.class);
        }
        return instance;
    }
}