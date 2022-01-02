package com.fyp.al_saffar;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class FRGHomepageDrive extends Fragment {
    public FloatingActionButton createDriveFab;

    /**
     * // TODO: Rename parameter arguments, choose names that match
     * // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
     * private static final String ARG_PARAM1 = "param1";
     * private static final String ARG_PARAM2 = "param2";
     * <p>
     * // TODO: Rename and change types of parameters
     * private String mParam1;
     * private String mParam2;
     * <p>
     * public FRGHomepageDrive() {
     * // Required empty public constructor
     * }
     * <p>
     * <p>
     * // TODO: Rename and change types and number of parameters
     * public static FRGHomepageDrive newInstance(String param1, String param2) {
     * FRGHomepageDrive fragment = new FRGHomepageDrive();
     * Bundle args = new Bundle();
     * args.putString(ARG_PARAM1, param1);
     * args.putString(ARG_PARAM2, param2);
     * fragment.setArguments(args);
     * return fragment;
     * }
     * <p>
     * //    @Override
     * public void onCreate(Bundle savedInstanceState) {
     * super.onCreate(savedInstanceState);
     * if (getArguments() != null) {
     * mParam1 = getArguments().getString(ARG_PARAM1);
     * mParam2 = getArguments().getString(ARG_PARAM2);
     * }
     * }
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage_drive, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable
            Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createDriveFab = view.findViewById(R.id.create_ride_fab);
        createDriveFab.setOnClickListener(this::goToCreateRide);
    }

    public void goToCreateRide(View v) {
        Intent intent = new Intent(getActivity(), CreateRide.class);
        startActivity(intent);
    }
}