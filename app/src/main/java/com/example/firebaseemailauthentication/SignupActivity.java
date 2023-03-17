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
import com.google.firebase.firestore.DocumentSnapshot;
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
    String userID;
    String email,password,blood_group,name,local,phoneno,statee,districtt,agee;


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

        if (mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
            Log.e("ID",userID);
        }




        register_btn.setOnClickListener(view -> {


            register_btn.setVisibility(View.GONE);
            register_progress.setVisibility(View.VISIBLE);

            email = userEmail.getText().toString();
            password = userPassword.getText().toString();
            name = userName.getText().toString();
            local = locality.getText().toString();
            districtt = district.getText().toString();
            phoneno = phno.getText().toString();
            statee = state.getText().toString();
            agee = age.getText().toString();


            if (email.isEmpty() || password.isEmpty() || name.isEmpty() || phoneno.isEmpty() || local.isEmpty() || districtt.isEmpty() || statee.isEmpty() || agee.isEmpty() || blood_group.equals("Select")){

                Toast.makeText(SignupActivity.this, "These field shouldn't be Empty",Toast.LENGTH_SHORT).show();
                register_btn.setVisibility(View.VISIBLE);
                register_progress.setVisibility(View.GONE);
            }else {
                SignUp(email,password,name,local,districtt,phoneno,statee,agee);
            }
            ;


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

    private void RegisterUserAccount(String email, String password,String name,String local, String districtt,String phone,String state, String age) {
        //firestore declaration
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("Email", email);
        user.put("Name", name);
        user.put("Phone_No", phone);
        user.put("Age", age);
        user.put("Blood_Group", blood_group);
        user.put("Locality", local);
        user.put("District", districtt);
        user.put("State", state);


// Add a new document with a generated ID
        db.collection("Users/"+userID)
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());

                        //Create user and signup
                        startActivity(new Intent(SignupActivity.this, ProfileActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




    }

    private void SignUp(String email, String password,String name,String local, String districtt,String phone,String state, String age) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            userID = mAuth.getCurrentUser().getUid();
                            Log.e("ID",userID);
                            Toast.makeText(SignupActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                            RegisterUserAccount(email,password,name,local,districtt,phone,state,age);
                        } else {
//                            Toast.makeText(SignupActivity.this, "Error " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            register_btn.setVisibility(View.VISIBLE);
                            register_progress.setVisibility(View.GONE);
                            Log.e("ID","Error "+task.getException().getMessage());
                            if (task.getException().getMessage().equals("The email address is already in use by another account.")){
                                Toast.makeText(SignupActivity.this, "Account already exist!! Go to the login below.", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(SignupActivity.this, "Error "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
//                            if(signUpError is PlatformException) {
//                                if(signUpError.code == 'ERROR_EMAIL_ALREADY_IN_USE') {
//                                    /// ab@gmail.com has alread been registered.
//                                }
//                            }
                        }
                    }
                });
    }



    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
            Log.e("ID",userID);

            startActivity(new Intent(SignupActivity.this, ProfileActivity.class));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        blood_group = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(),blood_group,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}