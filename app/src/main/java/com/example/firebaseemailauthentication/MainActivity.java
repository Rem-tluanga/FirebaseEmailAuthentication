package com.example.firebaseemailauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    private Button btn_find,btn_register,btn_login;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    String userId;
    FirebaseFirestore db;
    DocumentReference docDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_find = findViewById(R.id.btn_find);
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        db = FirebaseFirestore.getInstance();

        if (currentUser != null){
            userId = currentUser.getUid();
        }



        btn_login.setOnClickListener(view -> {
            if(currentUser == null){
                startActivity(new Intent(this,LoginActivity.class));
            }else {

                db.collection("Users").document(userId)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                                        Log.e("TAG", document.getId() + " => " + document.getData());
                                        Log.e("TAG", document.getId() + " => " + document.get("Name").toString());
                                        startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                                    } else {
                                        mAuth.getCurrentUser().delete();
                                        Log.d("TAG", "No such document");
                                        startActivity(new Intent(MainActivity.this,SignupActivity.class));

                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });

                Log.e("Test","not empty");
                Log.e("Test",currentUser.getEmail());
            }
        });

        btn_find.setOnClickListener(view -> {
            startActivity(new Intent(this, FindActivity.class));
        });

        btn_register.setOnClickListener(view -> {
            if(currentUser == null){
                startActivity(new Intent(this,SignupActivity.class));
            }else{
                db.collection("Users").document(userId)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                                        Log.e("TAG", document.getId() + " => " + document.getData());
                                        Log.e("TAG", document.getId() + " => " + document.get("Name").toString());
                                        startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                                    } else {
                                        mAuth.getCurrentUser().delete();
                                        Log.d("TAG", "No such document");
                                        startActivity(new Intent(MainActivity.this,SignupActivity.class));

                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
            }
        });

    }
}
