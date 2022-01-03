package com.fyp.al_saffar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fyp.al_saffar.models.CarRegistrationModel;
import com.fyp.al_saffar.models.UserCreationModel;
import com.fyp.al_saffar.models.UserLoginModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.fyp.al_saffar.Values.TAG;

public class RegisterCar extends AppCompatActivity {

    EditText brandET;
    EditText makeET;
    EditText modelET;
    EditText yearET;
    EditText capacityET;
    Button registerCarButton;
    String mUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_car);
        brandET = findViewById(R.id.input_brand_car_register);
        makeET = findViewById(R.id.input_make_car_register);
        modelET = findViewById(R.id.input_model_car_register);
        yearET = findViewById(R.id.input_year_car_register);
        capacityET = findViewById(R.id.input_capacity_car_register);
        registerCarButton = findViewById(R.id.btn_car_register);
        registerCarButton.setOnClickListener(this::registerCarClicked);

        // Get user id
        SharedPreferences sharedPreferences =
                getSharedPreferences(Values.SP_FILE_KEY, Context.MODE_PRIVATE);
        mUserID = sharedPreferences.getString(Values.SPF_USER_ID_KEY, "-1");
    }

    public void registerCarClicked(View v) {
        CarRegistrationModel carData = extractDataFromCarRegistrationForm();

        // Set everything back to what it was
        brandET.setBackgroundColor(getResources().getColor(R.color.subtitle_background));
        makeET.setBackgroundColor(getResources().getColor(R.color.subtitle_background));
        modelET.setBackgroundColor(getResources().getColor(R.color.subtitle_background));
        yearET.setBackgroundColor(getResources().getColor(R.color.subtitle_background));
        capacityET.setBackgroundColor(getResources().getColor(R.color.subtitle_background));
        // Check for information not provided
        if (carData.errors.size() != 0) {
            for (int i = 0; i < carData.errors.size(); i++) {
                int errorCode = carData.errors.get(i);
                switch (errorCode) {
                    case 0:
                        brandET.setBackgroundColor(getResources().getColor(R.color.subtitle));
                        break;
                    case 1:
                        makeET.setBackgroundColor(getResources().getColor(R.color.subtitle));
                        break;
                    case 2:
                        modelET.setBackgroundColor(getResources().getColor(R.color.subtitle));
                        break;
                    case 3:
                        yearET
                                .setBackgroundColor(getResources().getColor(R.color.subtitle));
                        break;
                    case 4:
                        Toast.makeText(this, "An Error Occurred, Try Again Later",
                                Toast.LENGTH_LONG).show();
                        return;
                    case 5:
                        capacityET.setBackgroundColor(getResources().getColor(R.color.subtitle));
                        break;
                    default:
                        break;
                }
            }
            return;
        }
        // Send api call to car registration
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Values.API_ORIGIN + Values.API_CARS;

        // Request a string response from the provided URL.
        StringRequest registerCarCall = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonResponse = null;
                        try {
                            jsonResponse = new JSONObject(response.toString());
                            boolean success = jsonResponse.getBoolean("success");
                            if (!success) {
                                String errorMessage = jsonResponse.getString("error_message");
                                Toast.makeText(RegisterCar.this, errorMessage, Toast.LENGTH_LONG)
                                        .show();
                            } else {
                                Toast.makeText(RegisterCar.this, "Your Car is registered!",
                                        Toast.LENGTH_LONG)
                                        .show();
                                goToHomepage();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(RegisterCar.this, "Error Occured: Try again later",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.getLocalizedMessage());
                    }
                }
        ) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                //Put Data
                param.put("brand", carData.brand);
                param.put("make", carData.make);
                param.put("model", carData.model);
                param.put("year", carData.year);
                param.put("user_id", carData.user_id);
                param.put("capacity", carData.capacity);
                return param;
            }
        };
        queue.add(registerCarCall);

    }

    public CarRegistrationModel extractDataFromCarRegistrationForm() {
        return new
                CarRegistrationModel(brandET.getText().toString(), makeET.getText().toString(),
                modelET.getText().toString(), yearET.getText().toString(), mUserID,
                capacityET.getText().toString());
    }

    public void goToHomepage() {
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
    }

}