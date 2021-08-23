package com.fyp.al_saffar.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.FactoryConfigurationError;

public class UserCreationModel {
    public String username;
    public String emailAddress;
    public String password;
    public String confirmPassword;
    public boolean emailValid;
    public boolean passwordsMatch;
    public boolean validated;
    public int errorStatus;
    public final String[] errorMessages = {
            "",
            "Invalid email.",
            "Passwords don't match.",
            "Passwords don't match and email is invalid.",
    };
    public boolean basicInfoValidated;
    /**
     * 0 -> Username
     * 1 -> Email
     * 2 -> Password
     * 3 -> Confirm Password
     */
    public ArrayList<Integer> errors;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    public UserCreationModel(String username, String emailAddress, String password,
                             String confirmPassword) {
        this.username = username;
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
        if (this.emailAddress.isEmpty()) {
            this.errors.add(1);
        }
        if (this.password.isEmpty()) {
            this.errors.add(2);
        }
        if (this.confirmPassword.isEmpty()) {
            this.errors.add(3);
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
            errorStatus = 0;
        else if (this.passwordsMatch && !this.emailValid)
            errorStatus = 1;
        else if (!this.passwordsMatch && this.emailValid)
            errorStatus = 2;
        else if (!this.passwordsMatch && !this.emailValid)
            errorStatus = 3;


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