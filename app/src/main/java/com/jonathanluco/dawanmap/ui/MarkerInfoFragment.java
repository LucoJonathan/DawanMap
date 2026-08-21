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

public class MarkerInfoFragment extends Fragment {

    private static final String ARG_TITLE = "arg_title";
    private static final String ARG_ADDRESS = "arg_address";

    public static MarkerInfoFragment newInstance(String title, String address) {
        MarkerInfoFragment fragment = new MarkerInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_ADDRESS, address);
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

        Bundle args = getArguments();
        if (args != null) {
            titleView.setText(args.getString(ARG_TITLE, ""));
            addressView.setText(args.getString(ARG_ADDRESS, ""));
        }
    }
}
