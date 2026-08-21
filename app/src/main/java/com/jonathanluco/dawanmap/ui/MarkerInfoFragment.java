package com.jonathanluco.dawanmap.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jonathanluco.dawanmap.R;

import java.util.Locale;

/**
 * Fragment d'affichage des détails d'un marker sélectionné.
 */
public class MarkerInfoFragment extends Fragment {

    private static final String ARG_TITLE = "arg_title";
    private static final String ARG_ADDRESS = "arg_address";
    private static final String ARG_LATITUDE = "arg_latitude";
    private static final String ARG_LONGITUDE = "arg_longitude";

    /**
     * Crée une instance du fragment avec ses données d'affichage.
     *
     * @param title nom du centre.
     * @param address adresse du centre.
     * @param latitude latitude du centre.
     * @param longitude longitude du centre.
     * @return fragment initialisé.
     */
    public static MarkerInfoFragment newInstance(String title, String address, double latitude, double longitude) {
        MarkerInfoFragment fragment = new MarkerInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_ADDRESS, address);
        args.putDouble(ARG_LATITUDE, latitude);
        args.putDouble(ARG_LONGITUDE, longitude);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Inflate la vue du fragment.
     *
     * @param inflater inflater Android.
     * @param container conteneur parent.
     * @param savedInstanceState état sauvegardé.
     * @return vue racine du fragment.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_marker_info, container, false);
    }

    /**
     * Lie les données reçues aux vues du panneau d'information.
     *
     * @param view vue racine du fragment.
     * @param savedInstanceState état sauvegardé.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView titleView = view.findViewById(R.id.tv_title);
        TextView addressView = view.findViewById(R.id.tv_address);
        TextView coordinatesView = view.findViewById(R.id.tv_coordinates);

        Bundle fragmentArgs = getArguments();
        if (fragmentArgs != null) {
            String title = fragmentArgs.getString(ARG_TITLE, "");
            String address = fragmentArgs.getString(ARG_ADDRESS, "");
            double latitude = fragmentArgs.getDouble(ARG_LATITUDE, Double.NaN);
            double longitude = fragmentArgs.getDouble(ARG_LONGITUDE, Double.NaN);

            titleView.setText(title);
            addressView.setText(address);

            if (!CoordinateFormatter.hasValidCoordinates(latitude, longitude)) {
                coordinatesView.setText(getString(R.string.coordonnees_indisponibles));
            } else {
                coordinatesView.setText(
                        getString(
                                R.string.coordonnees_format,
                                CoordinateFormatter.formatCoordinate(latitude, Locale.getDefault()),
                                CoordinateFormatter.formatCoordinate(longitude, Locale.getDefault())
                        )
                );
            }
        }
    }
}
