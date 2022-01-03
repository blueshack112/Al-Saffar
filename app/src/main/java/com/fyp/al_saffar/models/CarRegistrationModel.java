package com.fyp.al_saffar.models;

import java.util.ArrayList;

public class CarRegistrationModel {
    public String brand;
    public String make;
    public String model;
    public String year;
    public String user_id;
    public String capacity;
    public boolean validated;
    public int errorStatus;

    public final String[] errorMessages = {
            "Brand is empty.",
            "Model is empty.",
    };
    public ArrayList<Integer> errors;


    public CarRegistrationModel(String brand, String make, String model, String year,
                                String user_id,
                                String capacity) {
        this.brand = brand;
        this.make = make;
        this.model = model;
        this.year = year;
        this.user_id = user_id;
        this.capacity = capacity;

        this.validated = false;
        this.errors = new ArrayList<>();
        this.validateInformation();
    }

    public void validateInformation() {
        if (this.brand.isEmpty()) {
            this.errors.add(0);
        }
        if (this.make.isEmpty()) {
            this.errors.add(1);
        }
        if (this.model.isEmpty()) {
            this.errors.add(2);
        }
        if (this.year.isEmpty()) {
            this.errors.add(3);
        }
        if (this.user_id.isEmpty()) {
            this.errors.add(4);
        }
        if (this.capacity.isEmpty()) {
            this.errors.add(5);
        }


        if (this.errors.size() > 0) {
            return;
        }

        this.validated = true;
    }

    @Override
    public String toString() {
        return this.brand + " " + this.model + " " + this.make + " " + this.year;
    }
}