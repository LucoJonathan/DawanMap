package com.jonathanluco.dawanmap.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jonathanluco.dawanmap.R;
import com.jonathanluco.dawanmap.data.AppDatabase;
import com.jonathanluco.dawanmap.data.LocationDao;
import com.jonathanluco.dawanmap.data.LocationEntity;
import com.jonathanluco.dawanmap.data.RetrofitClient;
import com.jonathanluco.dawanmap.model.Location;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationDao locationDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Initialisation du DAO SQLite
        locationDao = AppDatabase.getInstance(this).locationDao();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Gestion du clic sur une épingle pour afficher le détail de l'adresse
        mMap.setOnMarkerClickListener(marker -> {
            new AlertDialog.Builder(MapsActivity.this)
                    .setTitle(marker.getTitle())
                    .setMessage("Adresse :\n" + marker.getSnippet())
                    .setPositiveButton("Fermer", null)
                    .show();
            return true;
        });

        // Chargement des données
        loadLocations();
    }

    private void loadLocations() {
        // Tentative d'appel réseau Retrofit
        RetrofitClient.getInstance().getLocations().enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<LocationEntity> entities = new ArrayList<>();
                    for (Location loc : response.body()) {
                        entities.add(new LocationEntity(
                                loc.getId(), loc.getName(), loc.getAddress(),
                                loc.getLatitude(), loc.getLongitude()
                        ));
                    }

                    // Sauvegarde dans la base SQLite locale
                    new Thread(() -> {
                        locationDao.insertAll(entities);
                        runOnUiThread(() -> displayMarkers(entities));
                    }).start();
                } else {
                    loadFromSQLite();
                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                // Échec réseau : bascule sur la base SQLite
                Toast.makeText(MapsActivity.this, "Mode hors-ligne", Toast.LENGTH_SHORT).show();
                loadFromSQLite();
            }
        });
    }

    private void loadFromSQLite() {
        new Thread(() -> {
            List<LocationEntity> entities = locationDao.getAllLocations();
            runOnUiThread(() -> displayMarkers(entities));
        }).start();
    }

    private void displayMarkers(List<LocationEntity> locations) {
        mMap.clear();
        if (locations.isEmpty()) {
            Toast.makeText(this, "Aucun centre disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        LatLng lastPos = null;
        for (LocationEntity loc : locations) {
            LatLng pos = new LatLng(loc.getLatitude(), loc.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(pos)
                    .title(loc.getName())
                    .snippet(loc.getAddress()));
            lastPos = pos;
        }

        // Centre la caméra sur le dernier élément ajouté (ex: France)
        if (lastPos != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastPos, 6f));
        }
    }
}