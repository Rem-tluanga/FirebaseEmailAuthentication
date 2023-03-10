package com.example.firebaseemailauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class DetailsActivity extends AppCompatActivity {

    private TextView name,blood_group,locality,district,state,age;
    private MaterialButton call_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        name = findViewById(R.id.myName);
        blood_group = findViewById(R.id.myBloodGroup);
        locality = findViewById(R.id.myLocality);
        district = findViewById(R.id.myDistrict);
        state = findViewById(R.id.myState);
        age = findViewById(R.id.myAge);

        call_btn = findViewById(R.id.call_btn);
    }
}