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

import com.google.android.gms.tasks.OnCompleteListener;
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


    private TextView user_email,name,phno,blood_group,locality,district,state,age;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private MaterialButton logout_btn;
    private Spinner spinnerBloodGroup;
    LinearLayout linearView;
    AlertDialog dialog;
    String type,blood_groupp,namee,local,phoneno,statee,districtt,agee,userId;



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


        user_email.setText("Email: "+mAuth.getCurrentUser().getEmail());

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
                finish();
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();


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
                                startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));
                            } else {
                                Log.d("TAG", "No such document");

                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.custom_dialog,null);
        EditText eText;

        eText = view.findViewById(R.id.ename);
        spinnerBloodGroup = view.findViewById(R.id.spinner_blood_group);
        linearView = view.findViewById(R.id.bloodView);
        Button submit = view.findViewById(R.id.sub_btn);

        submit.setOnClickListener(v->{
            switch (type) {
                case "name":
                    name.setText("Name : " + eText.getText().toString());
                    break;
                case "age":
                    age.setText("Age : " + eText.getText().toString());
                    break;
                case "phoneno":
                    phno.setText("Phone No : " + eText.getText().toString());
                    break;
                case "bloodgroup":
                    blood_group.setText("Blood Group : " + blood_groupp);
                    break;
                case  "locality":
                    locality.setText("Locality : " + eText.getText().toString());
                    break;
                case "district":
                    district.setText("District : " + eText.getText().toString());
                    break;
                default:
                    state.setText("State : " + eText.getText().toString());
                    break;

            }

            dialog.dismiss();
        });

        builder.setView(view);
        dialog = builder.create();

        name.setOnClickListener(v ->{
            type = "name";
            dialog.setTitle("Name");
            eText.setText("Remtluanga");
            eText.setVisibility(View.VISIBLE);
            linearView.setVisibility(View.GONE);
            dialog.show();
        });

        age.setOnClickListener(v ->{
            type = "age";
            dialog.setTitle("Age");
            eText.setVisibility(View.VISIBLE);
            linearView.setVisibility(View.GONE);
            eText.setText("33");
            dialog.show();
        });

        phno.setOnClickListener(v ->{
            type = "phoneno";
            dialog.setTitle("Phone No");
            eText.setVisibility(View.VISIBLE);
            linearView.setVisibility(View.GONE);
            eText.setText("31234567899");
            dialog.show();
        });

        blood_group.setOnClickListener(v ->{
            type = "bloodgroup";
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Blood_Group, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerBloodGroup.setAdapter(adapter);
            dialog.setTitle("Blood Group");
            linearView.setVisibility(View.VISIBLE);
            eText.setVisibility(View.GONE);
            dialog.show();
        });

        locality.setOnClickListener(v ->{
            type = "locality";
            dialog.setTitle("Locality");
            eText.setVisibility(View.VISIBLE);
            linearView.setVisibility(View.GONE);
            eText.setText("Durtlang");
            dialog.show();
        });

        district.setOnClickListener(v ->{
            type = "district";
            dialog.setTitle("District");
            eText.setVisibility(View.VISIBLE);
            linearView.setVisibility(View.GONE);
            eText.setText("Aizawl");
            dialog.show();
        });

        state.setOnClickListener(v ->{
            type = "state";
            dialog.setTitle("State");
            eText.setVisibility(View.VISIBLE);
            linearView.setVisibility(View.GONE);
            eText.setText("Mizoram");
            dialog.show();
        });



        spinnerBloodGroup.setOnItemSelectedListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null){
            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
        }
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
        startActivity(new Intent(ProfileActivity.this,MainActivity.class));
        finish();
    }
}