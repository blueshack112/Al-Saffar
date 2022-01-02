package com.fyp.al_saffar.models;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserLoginModel {
    public String username;
    public String password;
    public boolean validated;
    public int errorStatus;
    public final String[] errorMessages = {
            "Username is empty.",
            "Password is empty.",
    };
    public ArrayList<Integer> errors;


    public UserLoginModel(String username, String password) {
        this.username = username;
        this.password = password;
        this.validated = false;
        this.errors = new ArrayList<>();
        this.validateInformation();
    }

    public void validateInformation() {
        if (this.username.isEmpty()) {
            this.errors.add(0);
        }
        if (this.password.isEmpty()) {
            this.errors.add(1);
        }
        if (this.errors.size() > 0) {
            return;
        }

        this.validated = true;
    }

    @Override
    public String toString() {
        return "Username: " + username + " Password: " + password;
    }
}