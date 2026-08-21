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

public class MarkerInfoFragment extends Fragment {

    private static final String ARG_TITLE = "arg_title";
    private static final String ARG_ADDRESS = "arg_address";
    private static final String ARG_LATITUDE = "arg_latitude";
    private static final String ARG_LONGITUDE = "arg_longitude";

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_marker_info, container, false);
    }

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

            if (Double.isNaN(latitude) || Double.isNaN(longitude)) {
                coordinatesView.setText(getString(R.string.coordonnees_indisponibles));
            } else {
                coordinatesView.setText(
                        getString(
                                R.string.coordonnees_format,
                                String.format(Locale.getDefault(), "%.6f", latitude),
                                String.format(Locale.getDefault(), "%.6f", longitude)
                        )
                );
            }
        }
    }
}
