package com.example.firebaseemailauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private TextInputEditText userEmail, userPassword;
    private MaterialButton register_btn;
    private TextView login_tv;
    private FirebaseAuth mAuth;
    private ProgressBar register_progress;
    String UserID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userEmail = findViewById(R.id.email_edit_text);
        userPassword = findViewById(R.id.password_edit_text);
        login_tv = findViewById(R.id.login_tv);
        register_btn = findViewById(R.id.register_btn);
        register_progress = findViewById(R.id.register_progress);


        mAuth = FirebaseAuth.getInstance();


        register_btn.setOnClickListener(view -> {

            register_btn.setVisibility(View.GONE);
            register_progress.setVisibility(View.VISIBLE);

            final String email = userEmail.getText().toString();
            final String password = userPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty()){

                Toast.makeText(SignupActivity.this, "Email and Password shouldn't be Empty",Toast.LENGTH_SHORT).show();
                register_btn.setVisibility(View.VISIBLE);
                register_progress.setVisibility(View.GONE);
            }else {
                RegisterUserAccount(email,password);
            }

        });

        login_tv.setOnClickListener(V->{
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        });



    }

    private void RegisterUserAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(SignupActivity.this, "Failed to Create an Account" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            register_btn.setVisibility(View.VISIBLE);
                            register_progress.setVisibility(View.GONE);
                        }
                    }
                });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(SignupActivity.this, ProfileActivity.class));
        }
    }
}