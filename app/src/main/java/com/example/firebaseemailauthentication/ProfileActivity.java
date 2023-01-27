package com.example.firebaseemailauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private TextInputEditText user_email;
    private TextInputLayout email_outlinedTextField;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private MaterialButton logout_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user_email = findViewById(R.id.user_email);
        email_outlinedTextField = findViewById(R.id.email_outlinedTextField);
        logout_btn = findViewById(R.id.logout_btn);

        email_outlinedTextField.setEnabled(false);
        user_email.setEnabled(false);

        mAuth = FirebaseAuth.getInstance();
       // currentUser = mAuth.getCurrentUser();

        user_email.setText(mAuth.getCurrentUser().getEmail());



        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
                finish();
            }
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