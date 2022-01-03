package com.fyp.al_saffar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.fyp.al_saffar.Values.TAG;

public class YourCarDetails extends AppCompatActivity {
    private TextView ownedByET;
    private TextView brandET;
    private TextView makeET;
    private TextView modelET;
    private TextView yearET;
    private TextView capacityET;
    private TextView approvedET;
    private Button deleteMyCarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_car_details);

        ownedByET = findViewById(R.id.out_car_owned_by);
        brandET = findViewById(R.id.out_car_brand);
        makeET = findViewById(R.id.out_car_make);
        modelET = findViewById(R.id.out_car_model);
        yearET = findViewById(R.id.out_car_year);
        capacityET = findViewById(R.id.out_car_capacity);
        approvedET = findViewById(R.id.out_car_approved);
        deleteMyCarButton = findViewById(R.id.btn_delete_my_car);
        deleteMyCarButton.setOnClickListener(this::deleteCar);
        assignData();
    }

    private void assignData() {
        // Read shared preferences
        SharedPreferences sp = getSharedPreferences(Values.SP_FILE_KEY, MODE_PRIVATE);

        // Extract data
        String brand = sp.getString(Values.SPF_CAR_BRAND_KEY, "Not Assigned");
        String make = sp.getString(Values.SPF_CAR_MAKE_KEY, "Not Assigned");
        String model = sp.getString(Values.SPF_CAR_MODEL_KEY, "Not Assigned");
        String year = sp.getString(Values.SPF_CAR_YEAR_KEY, "Not Assigned");
        String capacity = sp.getString(Values.SPF_CAR_CAPACITY_KEY, "Not Assigned");
        boolean approved = sp.getBoolean(Values.SPF_CAR_APPROVED_KEY, true);

        String approvedStr = null;
        if (approved) {
            approvedStr = "Yes";
        } else {
            approvedStr = "No";
        }

        // Get User full name
        SharedPreferences ps = getSharedPreferences(Values.SP_FILE_KEY, MODE_PRIVATE);
        String ownedBy = sp.getString(Values.SPF_USER_FULL_NAME_KEY, "");

        ownedByET.setText(ownedBy);
        brandET.setText(brand);
        makeET.setText(make);
        modelET.setText(model);
        yearET.setText(year);
        capacityET.setText(capacity);
        approvedET.setText(approvedStr);
    }

    private void deleteCar(View v) {
        String userID = Utils.getUserId(this);

        // Send api call to car deletion
        RequestQueue queue = Volley.newRequestQueue(this);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .encodedAuthority(
                        Values.API_ORIGIN_WITHOUT_HTTP + Values.API_CARS + Values.API_CARS_DELETE)
                .appendQueryParameter("user_id", userID);
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
                                Toast.makeText(YourCarDetails.this,
                                        "Could not delete. Try again later", Toast.LENGTH_SHORT)
                                        .show();
                                return;
                            } else {
                                Toast.makeText(YourCarDetails.this,
                                        "You car has been removed.", Toast.LENGTH_SHORT)
                                        .show();
                                goToHomePage();
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

    public void goToHomePage() {
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
    }
}