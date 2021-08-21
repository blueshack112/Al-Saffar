package com.fyp.al_saffar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText email;
    EditText password;
    Button loginButton;
    Button createNewAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.input_email_address);
        password = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);
        createNewAccount = findViewById(R.id.btn_create_new_account);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignUp();

            }
        });

    }

    public void goToSignUp() {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }
}