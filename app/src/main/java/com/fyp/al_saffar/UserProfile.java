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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.fyp.al_saffar.Values.TAG;

public class UserProfile extends AppCompatActivity {
    private Button changePasswordButton;
    private EditText currentPasswordET;
    private EditText newPasswordET;
    private EditText confirmNewPasswordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        changePasswordButton = findViewById(R.id.btn_change_pass);
        currentPasswordET = findViewById(R.id.input_current_password_change_pass);
        newPasswordET = findViewById(R.id.input_new_password_change_pass);
        confirmNewPasswordET = findViewById(R.id.input_confirm_password_change_pass);
        changePasswordButton.setOnClickListener(this::changePass);

    }

    public void changePass(View v) {
        String currentPass = currentPasswordET.getText().toString();
        String newPass = newPasswordET.getText().toString();
        String confirmPass = confirmNewPasswordET.getText().toString();
        String userID = Utils.getUserId(this);

        // Send api call to signup
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Values.API_ORIGIN + Values.API_ACCOUNTS + Values.API_ACCOUNTS_CHANGE_PASS;

        // Request a string response from the provided URL.
        StringRequest changePassCall = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonResponse = null;
                        try {
                            jsonResponse = new JSONObject(response.toString());
                            boolean success = jsonResponse.getBoolean("success");
                            if (!success) {
                                String errorMessage = jsonResponse.getString("error_message");
                                Toast.makeText(UserProfile.this, errorMessage, Toast.LENGTH_LONG)
                                        .show();
                            } else {
                                Toast.makeText(UserProfile.this, "Password Changed!",
                                        Toast.LENGTH_LONG).show();
                                // Assign user data to app data
                                goToHomepage();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(UserProfile.this, "Error Occured: Try again later",
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
                param.put("user_id", userID);
                param.put("current_password", currentPass);
                param.put("new_password", newPass);
                param.put("confirm_password", confirmPass);
                return param;
            }
        };
        queue.add(changePassCall);
    }

    public void goToHomepage() {
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
    }
}