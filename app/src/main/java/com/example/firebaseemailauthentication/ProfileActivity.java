package com.example.firebaseemailauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private TextView user_email, name, phno, blood_group, locality, district, state, age;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private MaterialButton logout_btn;
    private Spinner spinnerBloodGroup;
    LinearLayout linearView;
    AlertDialog dialog;
    String type, blood_groupp, namee, local, phoneno, statee, districtt, agee, userId;
    FirebaseFirestore db;


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
        currentUser = mAuth.getCurrentUser();
        userId = currentUser.getUid();


        user_email.setText("Email: " + mAuth.getCurrentUser().getEmail());

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
            }
        });

        db = FirebaseFirestore.getInstance();


        getUserData();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        EditText eText;

        eText = view.findViewById(R.id.ename);
        spinnerBloodGroup = view.findViewById(R.id.spinner_blood_group);
        linearView = view.findViewById(R.id.bloodView);
        Button submit = view.findViewById(R.id.sub_btn);

        submit.setOnClickListener(v -> {
            switch (type) {
                case "name":
//                    name.setText("Name : " + eText.getText().toString());
                    db.collection("Users").document(userId).update("Name",eText.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            getUserData();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "Update Error "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    break;

                case "age":
//                    age.setText("Age : " + eText.getText().toString());
                    db.collection("Users").document(userId).update("Age",eText.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            getUserData();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "Update Error "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    break;

                case "phoneno":
//                    phno.setText("Phone No : " + eText.getText().toString());
                    db.collection("Users").document(userId).update("Phone_No",eText.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            getUserData();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "Update Error "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    break;

                case "bloodgroup":
//                    blood_group.setText("Blood Group : " + blood_groupp);
                    db.collection("Users").document(userId).update("Blood_Group",blood_groupp).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            getUserData();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "Update Error "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    break;

                case "locality":
//                    locality.setText("Locality : " + eText.getText().toString());
                    db.collection("Users").document(userId).update("Locality",eText.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            getUserData();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "Update Error "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    break;

                case "district":
//                    district.setText("District : " + eText.getText().toString());
                    db.collection("Users").document(userId).update("District",eText.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            getUserData();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "Update Error "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                    break;

                default:
//                    state.setText("State : " + eText.getText().toString());
                    db.collection("Users").document(userId).update("State",eText.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            getUserData();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "Update Error "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    break;

            }
            dialog.dismiss();
        });

        builder.setView(view);
        dialog = builder.create();

        name.setOnClickListener(v -> {
            type = "name";
            dialog.setTitle("Name");
            eText.setText(namee);
            eText.setVisibility(View.VISIBLE);
            linearView.setVisibility(View.GONE);
            dialog.show();
        });

        age.setOnClickListener(v -> {
            type = "age";
            dialog.setTitle("Age");
            eText.setVisibility(View.VISIBLE);
            linearView.setVisibility(View.GONE);
            eText.setText(agee);
            dialog.show();
        });

        phno.setOnClickListener(v -> {
            type = "phoneno";
            dialog.setTitle("Phone No");
            eText.setVisibility(View.VISIBLE);
            linearView.setVisibility(View.GONE);
            eText.setText(phoneno);
            dialog.show();
        });

        blood_group.setOnClickListener(v -> {
            type = "bloodgroup";
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Blood_Group, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerBloodGroup.setAdapter(adapter);
            dialog.setTitle("Blood Group");
            linearView.setVisibility(View.VISIBLE);
            eText.setVisibility(View.GONE);
            dialog.show();
        });

        locality.setOnClickListener(v -> {
            type = "locality";
            dialog.setTitle("Locality");
            eText.setVisibility(View.VISIBLE);
            linearView.setVisibility(View.GONE);
            eText.setText(local);
            dialog.show();
        });

        district.setOnClickListener(v -> {
            type = "district";
            dialog.setTitle("District");
            eText.setVisibility(View.VISIBLE);
            linearView.setVisibility(View.GONE);
            eText.setText(districtt);
            dialog.show();
        });

        state.setOnClickListener(v -> {
            type = "state";
            dialog.setTitle("State");
            eText.setVisibility(View.VISIBLE);
            linearView.setVisibility(View.GONE);
            eText.setText(statee);
            dialog.show();
        });


        spinnerBloodGroup.setOnItemSelectedListener(this);

    }

    private void getUserData() {
        db.collection("Users").document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.e("TAG", document.getId() + " => " + document.getData());
                                Log.e("TAG", document.getId() + " => " + document.get("Name").toString());


                                blood_groupp = document.get("Blood_Group").toString();
                                namee = document.get("Name").toString();
                                local = document.get("Locality").toString();
                                phoneno = document.get("Phone_No").toString();
                                statee = document.get("State").toString();
                                districtt = document.get("District").toString();
                                agee = document.get("Age").toString();

                                name.setText("Name : " + namee);
                                phno.setText("Phone No : " + phoneno);
                                blood_group.setText("Blood Group : " + blood_groupp );
                                locality.setText("Locality : " + local);
                                district.setText("District : " + districtt);
                                state.setText("State : " + statee);
                                age.setText("Age : " + agee);

                            } else {
                                Log.d("TAG", "No such document");
                                if (mAuth.getCurrentUser() != null) {
                                    mAuth.getCurrentUser().delete();
                                }
                                startActivity(new Intent(ProfileActivity.this, SignupActivity.class));
                                finish();
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                            if (mAuth.getCurrentUser() != null) {
                                mAuth.getCurrentUser().delete();
                            }
                            startActivity(new Intent(ProfileActivity.this, SignupActivity.class));
                            finish();
                        }
                    }
                });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        blood_groupp = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        finish();
    }
}