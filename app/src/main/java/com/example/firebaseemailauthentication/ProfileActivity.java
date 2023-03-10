package com.example.firebaseemailauthentication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private TextView user_email,name,phno,blood_group,locality,district,state,age;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private MaterialButton logout_btn;
    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.myName);
        phno = findViewById(R.id.myPhNo);
        blood_group = findViewById(R.id.myBloodGroup);
        locality = findViewById(R.id.myLocality);
        district = findViewById(R.id.myDistrict);
        state = findViewById(R.id.myState);
        user_email = findViewById(R.id.myEmail);
        logout_btn = findViewById(R.id.logout_btn);
        age = findViewById(R.id.myAge);


        user_email.setEnabled(false);

        mAuth = FirebaseAuth.getInstance();
       // currentUser = mAuth.getCurrentUser();

        user_email.setText("Email: "+mAuth.getCurrentUser().getEmail());

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
                finish();
            }
        });




        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.custom_dialog,null);
        EditText eName, eAge;
        eName = view.findViewById(R.id.name);
        eAge = view.findViewById(R.id.age);
        Button submit = view.findViewById(R.id.sub_btn);

        submit.setOnClickListener(v->{
            name.setText("Name : "+eName.getText().toString());
//            age.setText("Age : "+eAge.getText().toString());
            dialog.dismiss();
        });

        builder.setView(view);
        dialog = builder.create();

        name.setOnClickListener(v ->{
            builder.setTitle("Enter Name :");
            eName.setText("Name");
            dialog.show();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null){
            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
        }
    }
}