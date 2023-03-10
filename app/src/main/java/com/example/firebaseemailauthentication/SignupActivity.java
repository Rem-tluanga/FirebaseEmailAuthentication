package com.example.firebaseemailauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    private TextInputEditText userEmail, userPassword, userName, locality, district, phno, state,age;
    private MaterialButton register_btn;
    private TextView login_tv;
    private FirebaseAuth mAuth;
    private ProgressBar register_progress;
    private Spinner spinnerBloodGroup;
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
        spinnerBloodGroup = findViewById(R.id.spinner_blood_group);
        userName = findViewById(R.id.Name);
        locality = findViewById(R.id.Locality);
        phno = findViewById(R.id.Phno);
        state = findViewById(R.id.State);
        district = findViewById(R.id.District);
        age = findViewById(R.id.Age);


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


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Blood_Group, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodGroup.setAdapter(adapter);

        spinnerBloodGroup.setOnItemSelectedListener(this);




    }

    private void RegisterUserAccount(String email, String password) {
        //firestore declaration
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("Email", email);
        user.put("Name", "Remtluanga");
        user.put("Phone_No", "7627911435");

// Add a new document with a generated ID
        db.collection("Users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());

                        //Create user and signup
                        SignUp(email,password);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });




    }

    private void SignUp(String email, String password) {
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(),choice,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}