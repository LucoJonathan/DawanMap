package com.jonathanluco.dawanmap.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jonathanluco.dawanmap.R;
import com.jonathanluco.dawanmap.data.db.AppDatabase;
import com.jonathanluco.dawanmap.data.db.dao.LocationDao;
import com.jonathanluco.dawanmap.data.db.entity.LocationEntity;
import com.jonathanluco.dawanmap.data.api.RetrofitClient;
import com.jonathanluco.dawanmap.model.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationDao locationDao;
    private List<LocationEntity> currentLocations = new ArrayList<>();
    private final Map<Integer, Marker> markerMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        locationDao = AppDatabase.getInstance(this).locationDao();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Configuration du bouton de la liste
        FloatingActionButton btnList = findViewById(R.id.btn_list);
        btnList.setOnClickListener(v -> showLocationsListDialog());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(marker -> {
            showMarkerInfoFragment(marker);
            return true;
        });

        mMap.setOnMapClickListener(latLng -> hideMarkerInfoFragment());

        loadLocations();
    }

    private void loadLocations() {
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
        markerMap.clear();
        this.currentLocations = locations;

        if (locations.isEmpty()) {
            Toast.makeText(this, "Aucun centre disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        LatLng lastPos = null;
        for (LocationEntity loc : locations) {
            LatLng pos = new LatLng(loc.getLatitude(), loc.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(pos)
                    .title(loc.getName())
                    .snippet(loc.getAddress()));

            if (marker != null) {
                markerMap.put(loc.getId(), marker);
            }
            lastPos = pos;
        }

        if (lastPos != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastPos, 6f));
        }
    }

    private void showLocationsListDialog() {
        if (currentLocations.isEmpty()) {
            Toast.makeText(this, "Aucune localisation disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        // Noms des centres à afficher dans la liste
        String[] names = new String[currentLocations.size()];
        for (int i = 0; i < currentLocations.size(); i++) {
            names[i] = currentLocations.get(i).getName();
        }

        new AlertDialog.Builder(this)
                .setTitle("Sélectionner un centre Dawan")
                .setItems(names, (dialog, which) -> {
                    LocationEntity selected = currentLocations.get(which);
                    LatLng targetPos = new LatLng(selected.getLatitude(), selected.getLongitude());

                    // Zoom et centrage sur la position
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(targetPos, 10f));

                    // Affiche le panneau d'info du centre
                    Marker marker = markerMap.get(selected.getId());
                    if (marker != null) {
                        showMarkerInfoFragment(marker);
                    }
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void showMarkerInfoFragment(Marker marker) {
        View container = findViewById(R.id.marker_info_container);
        container.setVisibility(View.VISIBLE);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.marker_info_container,
                        MarkerInfoFragment.newInstance(
                                marker.getTitle(),
                                marker.getSnippet()
                        )
                )
                .commit();
    }

    private void hideMarkerInfoFragment() {
        View container = findViewById(R.id.marker_info_container);
        container.setVisibility(View.GONE);

        Fragment markerInfoFragment = getSupportFragmentManager().findFragmentById(R.id.marker_info_container);
        if (markerInfoFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(markerInfoFragment)
                    .commit();
        }
    }
}