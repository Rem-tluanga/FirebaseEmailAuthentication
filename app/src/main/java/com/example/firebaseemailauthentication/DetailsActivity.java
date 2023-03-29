package com.example.firebaseemailauthentication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class DetailsActivity extends AppCompatActivity {

    private TextView name, blood_group, locality, district, state, age;
    String phone_no, txt_name, txt_blood_group, txt_locality, txt_district, txt_state, txt_age;
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

        setTitle("Details");

        phone_no = getIntent().getStringExtra("phone_no");
        txt_name = getIntent().getStringExtra("name");
        txt_blood_group = getIntent().getStringExtra("blood_group");
        txt_locality = getIntent().getStringExtra("locality");
        txt_district = getIntent().getStringExtra("district");
        txt_state = getIntent().getStringExtra("state");
        txt_age = getIntent().getStringExtra("age");

        name.setText("Name : " + txt_name);
        blood_group.setText("Blood Group : " + txt_blood_group);
        locality.setText("Locality : " + txt_locality);
        district.setText("District : " + txt_district);
        state.setText("State : " + txt_state);
        age.setText("Age : " + txt_age);


        call_btn = findViewById(R.id.call_btn);

        call_btn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
            builder.setMessage("Do you want to call?");
            builder.setTitle(""+phone_no);
            builder.setCancelable(true);

            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                Uri u = Uri.parse("tel:" + phone_no);
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                try {
                    startActivity(i);
                } catch (SecurityException s) {
                    Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG)
                            .show();
                }
            });

            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();


        });
    }
}