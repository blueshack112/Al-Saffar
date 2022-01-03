package com.fyp.al_saffar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import static com.fyp.al_saffar.Values.TAG;


public class FRGHomepageDrive extends Fragment {
    public FloatingActionButton createDriveFab;
    public Button registerCarButton;
    public boolean carFound;


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
        registerCarButton = view.findViewById(R.id.register_car_button);
        createDriveFab.setOnClickListener(this::goToCreateRide);
        carFound = false;

        // Get car data
        getCarData();


    }

    public void goToCreateRide(View v) {
        Intent intent = new Intent(getActivity(), SelectStopsForRide.class);
        startActivity(intent);
    }

    public void goToRegisterCar(View v) {
        Intent intent = new Intent(getActivity(), RegisterCar.class);
        startActivity(intent);
    }

    public void goToCarDetails(View v) {
        Intent intent = new Intent(getActivity(), YourCarDetails.class);
        startActivity(intent);

    }

    public void getCarData() {
        String userId = Utils.getUserId(getActivity());

        // Make the GET request on cars
        // Send api call to car registration
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .encodedAuthority(Values.API_ORIGIN_WITHOUT_HTTP + Values.API_CARS)
                .appendQueryParameter("user_id", userId);
        String url = builder.build().toString();

        // Request a string response from the provided URL.
        StringRequest getCarCall = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonResponse = null;
                        try {
                            jsonResponse = new JSONObject(response.toString());
                            boolean success = jsonResponse.getBoolean("success");
                            if (!success) {
                                registerCarButton.setText(R.string.register_a_car);
                                registerCarButton
                                        .setOnClickListener(FRGHomepageDrive.this::goToRegisterCar);
                                return;
                            } else {
                                JSONObject jsonCarData = jsonResponse.getJSONObject("car_data");
                                SharedPreferences sp = getActivity()
                                        .getSharedPreferences(Values.SP_FILE_KEY,
                                                Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString(Values.SPF_CAR_ID_KEY,
                                        jsonResponse.getString("car_id"));
                                editor.putString(Values.SPF_CAR_BRAND_KEY,
                                        jsonCarData.getString("brand"));
                                editor.putString(Values.SPF_CAR_MAKE_KEY,
                                        jsonCarData.getString("make"));
                                editor.putString(Values.SPF_CAR_MODEL_KEY,
                                        jsonCarData.getString("model"));
                                editor.putString(Values.SPF_CAR_YEAR_KEY,
                                        jsonCarData.getString("year"));
                                editor.putString(Values.SPF_CAR_CAPACITY_KEY,
                                        jsonCarData.getString("capacity"));
                                editor.putString(Values.SPF_CAR_CONDITION_KEY,
                                        jsonCarData.getString("condition"));
                                editor.putBoolean(Values.SPF_CAR_APPROVED_KEY,
                                        jsonCarData.getBoolean("approved"));
                                editor.apply();
                                registerCarButton.setText(R.string.your_car_details);
                                registerCarButton
                                        .setOnClickListener(FRGHomepageDrive.this::goToCarDetails);
                                carFound = true;
                            }
                        } catch (JSONException e) {
                            Log.d(TAG, "onResponse: " + e.toString());
                            return;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                    }
                }
        );
        queue.add(getCarCall);
    }
}