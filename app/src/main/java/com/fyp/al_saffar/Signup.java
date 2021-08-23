package com.fyp.al_saffar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fyp.al_saffar.models.UserCreationModel;

public class Signup extends AppCompatActivity {

    EditText usernameET;
    EditText emailAddressET;
    EditText passwordET;
    EditText confirmPasswordET;
    Button signUpButton;
    final String TAG = "XXXXXXXXXXXXXXXX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usernameET = findViewById(R.id.input_username_signup);
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
                        emailAddressET
                                .setBackgroundColor(getResources().getColor(R.color.subtitle));
                        break;
                    case 2:
                        passwordET.setBackgroundColor(getResources().getColor(R.color.subtitle));
                        break;

                    case 3:
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
    }

    public UserCreationModel extractDataFromForm() {
        return new
                UserCreationModel(usernameET.getText().toString(),
                emailAddressET.getText().toString(), passwordET.getText().toString(),
                confirmPasswordET.getText().toString());
    }
}