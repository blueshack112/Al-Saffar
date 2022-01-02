package com.fyp.al_saffar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fyp.al_saffar.models.UserCreationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.fyp.al_saffar.Values.API_ACCOUNTS;
import static com.fyp.al_saffar.Values.TAG;

public class Signup extends AppCompatActivity {

    EditText usernameET;
    EditText firstNameET;
    EditText lastNameET;
    EditText emailAddressET;
    EditText passwordET;
    EditText confirmPasswordET;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usernameET = findViewById(R.id.input_username_signup);
        firstNameET = findViewById(R.id.input_first_name_signup);
        lastNameET = findViewById(R.id.input_last_name_signup);
        emailAddressET = findViewById(R.id.input_email_address_signup);
        passwordET = findViewById(R.id.input_password_signup);
        confirmPasswordET = findViewById(R.id.input_confirm_password_signup);
        signUpButton = findViewById(R.id.btn_signup);
        signUpButton.setOnClickListener(this::signUpButtonClicked);

    }

    public void signUpButtonClicked(View v) {
        UserCreationModel userData = extractDataFromForm();

        // Set everything back to what it was
        usernameET.setBackgroundColor(getResources().getColor(R.color.subtitle_background));
        firstNameET.setBackgroundColor(getResources().getColor(R.color.subtitle_background));
        lastNameET.setBackgroundColor(getResources().getColor(R.color.subtitle_background));
        emailAddressET.setBackgroundColor(getResources().getColor(R.color.subtitle_background));
        passwordET.setBackgroundColor(getResources().getColor(R.color.subtitle_background));
        confirmPasswordET.setBackgroundColor(getResources().getColor(R.color.subtitle_background));

        // Check for information not provided
        if (userData.basicInfoValidated && userData.errors.size() != 0) {
            for (int i = 0; i < userData.errors.size(); i++) {
                int errorCode = userData.errors.get(i);
                switch (errorCode) {
                    case 0:
                        usernameET.setBackgroundColor(getResources().getColor(R.color.subtitle));
                        break;
                    case 1:
                        firstNameET.setBackgroundColor(getResources().getColor(R.color.subtitle));
                        break;
                    case 2:
                        lastNameET.setBackgroundColor(getResources().getColor(R.color.subtitle));
                        break;
                    case 3:
                        emailAddressET
                                .setBackgroundColor(getResources().getColor(R.color.subtitle));
                        break;
                    case 4:
                        passwordET.setBackgroundColor(getResources().getColor(R.color.subtitle));
                        break;

                    case 5:
                        confirmPasswordET
                                .setBackgroundColor(getResources().getColor(R.color.subtitle));
                        break;
                    default:
                        break;
                }
            }
            return;

        }

        // Check for other errors
        if (userData.errorStatus != 0) {
            Toast.makeText(this, userData.errorMessages[userData.errorStatus], Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        // Send api call to signup
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Values.API_ORIGIN + Values.API_ACCOUNTS;

        // Request a string response from the provided URL.
        StringRequest createAccountCall = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonResponse = null;
                        try {
                            jsonResponse = new JSONObject(response.toString());
                            boolean success = jsonResponse.getBoolean("success");
                            if (!success) {
                                JSONObject jsonErrors = jsonResponse.getJSONObject("errors");
                                String errorKey = jsonErrors.keys().next();
                                String errorMessage =
                                        jsonErrors.getJSONArray(errorKey).get(0).toString();
                                Toast.makeText(Signup.this, errorMessage, Toast.LENGTH_LONG)
                                        .show();
                            } else {
                                Toast.makeText(Signup.this, "Account created!", Toast.LENGTH_LONG)
                                        .show();
                                goToLogin();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(Signup.this, "Error Occured: Try again later",
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
                param.put("username", userData.username);
                param.put("email", userData.emailAddress);
                param.put("password1", userData.password);
                param.put("password2", userData.confirmPassword);
                param.put("fname", userData.firstName);
                param.put("lname", userData.lastName);
                return param;
            }
        };


        queue.add(createAccountCall);
    }

    public UserCreationModel extractDataFromForm() {
        return new
                UserCreationModel(usernameET.getText().toString(), firstNameET.getText().toString(),
                lastNameET.getText().toString(), emailAddressET.getText().toString(),
                passwordET.getText().toString(), confirmPasswordET.getText().toString());
    }

    public void goToLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}