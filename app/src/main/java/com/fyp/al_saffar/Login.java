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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fyp.al_saffar.models.UserCreationModel;
import com.fyp.al_saffar.models.UserLoginModel;

import org.json.JSONException;
import org.json.JSONObject;

import static com.fyp.al_saffar.Values.TAG;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText usernameET;
    EditText passwordET;
    Button loginButton;
    Button createNewAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameET = findViewById(R.id.input_email_address);
        passwordET = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);
        createNewAccountButton = findViewById(R.id.btn_create_new_account);

        loginButton.setOnClickListener(this::loginButtonClicked);
        createNewAccountButton.setOnClickListener(this::goToSignUp);

    }

    public void loginButtonClicked(View v) {
        UserLoginModel userData = extractDataFromLoginForm();

        // Set everything back to what it was
        usernameET.setBackgroundColor(getResources().getColor(R.color.subtitle_background));
        passwordET.setBackgroundColor(getResources().getColor(R.color.subtitle_background));

        // Check for information not provided
        if (userData.errors.size() != 0) {
            for (int i = 0; i < userData.errors.size(); i++) {
                int errorCode = userData.errors.get(i);
                switch (errorCode) {
                    case 0:
                        usernameET.setBackgroundColor(getResources().getColor(R.color.subtitle));
                        break;
                    case 1:
                        passwordET.setBackgroundColor(getResources().getColor(R.color.subtitle));
                        break;
                    default:
                        break;
                }
            }
            return;
        }

        // Send api call to signup
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Values.API_ORIGIN + Values.API_ACCOUNTS + Values.API_ACCOUNTS_LOGIN;

        // Request a string response from the provided URL.
        StringRequest loginCall = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonResponse = null;
                        try {
                            jsonResponse = new JSONObject(response.toString());
                            boolean authenticated = jsonResponse.getBoolean("authenticated");
                            if (!authenticated) {
                                Toast.makeText(Login.this, "Email or password incorrect",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Login.this, "Logged in!",
                                        Toast.LENGTH_LONG).show();
                                // TODO: Go to home page

                            }
                        } catch (JSONException e) {
                            Toast.makeText(Login.this, "Error Occured: Try again later",
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
                param.put("password", userData.password);
                return param;
            }
        };
        queue.add(loginCall);


    }

    public UserLoginModel extractDataFromLoginForm() {
        return new
                UserLoginModel(usernameET.getText().toString(), passwordET.getText().toString());
    }

    public void goToSignUp(View v) {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }
}