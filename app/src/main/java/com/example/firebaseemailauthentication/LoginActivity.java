package com.example.firebaseemailauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity{

    private TextInputEditText userEmail, userPassword;
    private MaterialButton login_btn;
    private TextView reset_tv;
    private FirebaseAuth mAuth;
    private ProgressBar login_progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = findViewById(R.id.email_edit_text);
        userPassword = findViewById(R.id.password_edit_text);
        login_btn = findViewById(R.id.login_btn);
        login_progress = findViewById(R.id.login_progress);
        reset_tv = findViewById(R.id.reset_tv);


        mAuth = FirebaseAuth.getInstance();




        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_btn.setVisibility(View.GONE);
                login_progress.setVisibility(View.VISIBLE);

                final String email = userEmail.getText().toString();
                final String password = userPassword.getText().toString();

                //let's check if user has filled the text fields

                if (email.isEmpty()||password.isEmpty()){

                    Toast.makeText(LoginActivity.this,"Please check all the fields",Toast.LENGTH_SHORT).show();
                    login_btn.setVisibility(View.VISIBLE);
                    login_progress.setVisibility(View.GONE);

                }else {
                    signIn(email,password);
                }


            }
        });

        reset_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetPassword();
            }
        });


    }

    private void signIn(String email,String password){

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this,"Login Failed" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    login_btn.setVisibility(View.VISIBLE);
                    login_progress.setVisibility(View.GONE);
                }

            }
        });
    }

    private void ResetPassword(){

        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(LoginActivity.this);

        View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog_edit_text,null);
        TextInputEditText reg_email = view.findViewById(R.id.reset_email_edit_text);

        materialAlertDialogBuilder.setTitle("Reset Password")
                .setMessage("Reset Link will be sent to you Registered Email Address")
                .setView(view)
                .setPositiveButton("reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        String regemail = reg_email.getText().toString();

                        mAuth.sendPasswordResetEmail(regemail);
                        Toast.makeText(LoginActivity.this,"Kindly Please Check your email",Toast.LENGTH_SHORT).show();
                    }
                }).show();


    }

}
