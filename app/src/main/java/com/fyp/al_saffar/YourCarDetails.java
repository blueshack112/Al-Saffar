package com.fyp.al_saffar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class YourCarDetails extends AppCompatActivity {
    private TextView ownedByET;
    private TextView brandET;
    private TextView makeET;
    private TextView modelET;
    private TextView yearET;
    private TextView capacityET;
    private TextView approvedET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_car_details);

        ownedByET = findViewById(R.id.out_car_owned_by);
        brandET = findViewById(R.id.out_car_brand);
        makeET = findViewById(R.id.out_car_make);
        modelET = findViewById(R.id.out_car_model);
        yearET = findViewById(R.id.out_car_year);
        capacityET = findViewById(R.id.out_car_capacity);
        approvedET = findViewById(R.id.out_car_approved);

        assignData();
    }

    public void assignData() {
        // Read shared preferences
        SharedPreferences sp = getSharedPreferences(Values.SP_FILE_KEY, MODE_PRIVATE);

        // Extract data
        String brand = sp.getString(Values.SPF_CAR_BRAND_KEY, "Not Assigned");
        String make = sp.getString(Values.SPF_CAR_MAKE_KEY, "Not Assigned");
        String model = sp.getString(Values.SPF_CAR_MODEL_KEY, "Not Assigned");
        String year = sp.getString(Values.SPF_CAR_YEAR_KEY, "Not Assigned");
        String capacity = sp.getString(Values.SPF_CAR_CAPACITY_KEY, "Not Assigned");
        boolean approved = sp.getBoolean(Values.SPF_CAR_APPROVED_KEY, true);

        String approvedStr = null;
        if (approved) {
            approvedStr = "Yes";
        } else {
            approvedStr = "No";
        }

        // Get User full name
        SharedPreferences ps = getSharedPreferences(Values.SP_FILE_KEY, MODE_PRIVATE);
        String ownedBy = sp.getString(Values.SPF_USER_FULL_NAME_KEY, "");

        ownedByET.setText(ownedBy);
        brandET.setText(brand);
        makeET.setText(make);
        modelET.setText(model);
        yearET.setText(year);
        capacityET.setText(capacity);
        approvedET.setText(approvedStr);
    }
}