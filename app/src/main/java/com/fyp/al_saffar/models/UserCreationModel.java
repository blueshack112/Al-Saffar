package com.fyp.al_saffar.models;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.FactoryConfigurationError;

public class UserCreationModel {
    public String username;
    public String firstName;
    public String lastName;
    public String emailAddress;
    public String password;
    public String confirmPassword;
    public boolean emailValid;
    public boolean passwordsMatch;
    public boolean validated;
    public int errorStatus;
    public final String[] errorMessages = {
            "Username is empty",
            "First name is empty",
            "Last name is empty",
            "Invalid email.",
            "Passwords don't match.",
            "Passwords don't match and email is invalid.",
            "First name is non alphabetic",
            "Last name is non alphabetic"
    };
    /**
     * 0 -> Username
     * 1 -> First name
     * 2 -> Last name
     * 3 -> Email
     * 4 -> Password
     * 5 -> Confirm Password
     */
    public boolean basicInfoValidated;
    public ArrayList<Integer> errors;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    public UserCreationModel(String username, String firstName, String lastName,
                             String emailAddress, String password,
                             String confirmPassword) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.emailValid = false;
        this.passwordsMatch = false;
        this.validated = false;
        this.errors = new ArrayList<>();
        this.basicInfoValidated = false;
        this.validateInformation();
    }

    public void validateInformation() {
        if (this.username.isEmpty()) {
            this.errors.add(0);
        }
        if (this.firstName.isEmpty()) {
            this.errors.add(1);
        }
        if (this.lastName.isEmpty()) {
            this.errors.add(2);
        }
        if (this.emailAddress.isEmpty()) {
            this.errors.add(3);
        }
        if (this.password.isEmpty()) {
            this.errors.add(4);
        }
        if (this.confirmPassword.isEmpty()) {
            this.errors.add(5);
        }
        // Only alphabets in first and last name
        if (!this.firstName.matches("[a-zA-Z]+")) {
            this.errors.add(1);
        }
        if (!this.lastName.matches("[a-zA-Z]+")) {
            this.errors.add(2);
        }
        this.basicInfoValidated = true;

        if (this.errors.size() > 0) {
            return;
        }


        // Email validate
        this.emailValid = validateEmail(this.emailAddress);

        // Passwords validate
        this.passwordsMatch = password.equals(confirmPassword);

        if (this.passwordsMatch && this.emailValid)
            this.errorStatus = 0;
        else if (this.passwordsMatch && !this.emailValid)
            this.errorStatus = 3;
        else if (!this.passwordsMatch && this.emailValid)
            this.errorStatus = 4;
        else if (!this.passwordsMatch && !this.emailValid)
            this.errorStatus = 5;


        this.validated = true;
    }

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }


    @Override
    public String toString() {
        return "Username: " + username + " Email: " + emailAddress + " Password: " + password;
    }
}