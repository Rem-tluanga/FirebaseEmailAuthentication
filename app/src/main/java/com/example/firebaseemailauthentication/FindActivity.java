package com.example.firebaseemailauthentication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class FindActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView recyclerView;
    ArrayList<User> userArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    private Spinner spinnerBloodGroup;
    String bloodGroup;
    Button searchBtn;

    TextInputEditText searchTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        getSupportActionBar().hide();

        searchTxt = findViewById(R.id.searchTxt);
        spinnerBloodGroup = findViewById(R.id.profile_spinner_blood_group);
        searchBtn = findViewById(R.id.profileSearch);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        //13:05 ah
        recyclerView = findViewById(R.id.myRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<>();
        EventChangeListener();
        myAdapter = new MyAdapter(FindActivity.this, userArrayList);
        recyclerView.setAdapter(myAdapter);

        searchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (bloodGroup.equals("Select") && Objects.requireNonNull(searchTxt.getText()).toString().equals("")) {
                    EventChangeListener();
                } else if(!Objects.requireNonNull(searchTxt.getText()).toString().equals("")&&!bloodGroup.equals("Select")){
                    FilterEventChangeListener(bloodGroup);
                }
//                Toast.makeText(FindActivity.this, "Cange", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        searchBtn.setOnClickListener(v -> {
            if (bloodGroup.equals("Select")) {
                Toast.makeText(FindActivity.this, "Select the BloodGroup!!!", Toast.LENGTH_SHORT).show();
            } else if (Objects.requireNonNull(searchTxt.getText()).toString().equals("")) {
                Toast.makeText(FindActivity.this, "Enter District!!", Toast.LENGTH_SHORT).show();
            } else {
                myAdapter.getFilter().filter(searchTxt.getText().toString());

//                FilterEventChangeListener(searchTxt.getText().toString(), bloodGroup);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Blood_Group, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodGroup.setAdapter(adapter);

        spinnerBloodGroup.setOnItemSelectedListener(this);


    }

    private void EventChangeListener() {

        db.collection("Users")
                .orderBy("District", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException error) {

                        if (error != null) {

                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        if (value != null && !value.isEmpty()) {
                            Log.e("hpa", "Not Empty Value..");

                            userArrayList.clear();

                            for (QueryDocumentSnapshot document : value) {
                                userArrayList.add(document.toObject(User.class));
                            }
                        } else {
                            userArrayList.clear();
                            Log.e("hpa", "Empty!");
                        }

                        myAdapter.notifyDataSetChanged();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });

    }

    private void FilterEventChangeListener(String bloodGroup) {
        db.collection("Users")
                .orderBy("District", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {

                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        if (value != null && !value.isEmpty()) {
                            Log.e("hpa", "Not Empty Value..");

                            userArrayList.clear();

                            for (QueryDocumentSnapshot document : value) {
                                if (Objects.equals(document.getString("Blood_Group"), bloodGroup)) {
                                    Log.e("hpa", "Added");
//                                    arrayList.add(document.toObject(Model.class));
                                    userArrayList.add(new User(document.getString("Name"), document.getString("Email"), document.getString("Phone_No"), document.getString("Age"), document.getString("Blood_Group"), document.getString("Locality"), document.getString("District"), document.getString("State")));
                                }
                                Log.d("hpa", document.getId() + " => " + document.getData());
                            }
                        } else {
                            userArrayList.clear();
                            Log.e("hpa", "Empty!");
                        }

                        myAdapter.notifyDataSetChanged();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

//                userArrayList.clear();
//                for (DocumentChange dc : value.getDocumentChanges()) {
//
//                    if (dc.getType() == DocumentChange.Type.ADDED) {
//
//                        userArrayList.add(dc.getDocument().toObject(User.class));
//                    }
//
//                    myAdapter.notifyDataSetChanged();
//                    if (progressDialog.isShowing())
//                        progressDialog.dismiss();
//                }

                    }
                });

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        bloodGroup = adapterView.getItemAtPosition(i).toString();
        if (!bloodGroup.equals("Select")) {
            FilterEventChangeListener(bloodGroup);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}