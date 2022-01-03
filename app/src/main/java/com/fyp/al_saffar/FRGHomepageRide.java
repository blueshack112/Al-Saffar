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

public class FRGHomepageRide extends Fragment {
    private FloatingActionButton profileFab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage_ride, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable
            Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileFab = view.findViewById(R.id.profile_fab);
        profileFab.setOnClickListener(this::goToProfile);
    }

    private void goToProfile(View v) {
        Intent intent = new Intent(getActivity(), UserProfile.class);
        startActivity(intent);
    }
}