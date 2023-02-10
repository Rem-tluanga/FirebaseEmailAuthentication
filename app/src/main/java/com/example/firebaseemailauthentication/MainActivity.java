package com.example.firebaseemailauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_find,btn_register,btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_find = findViewById(R.id.btn_find);
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);



        btn_login.setOnClickListener(view -> {
            startActivity(new Intent(this,LoginActivity.class));
        });

        btn_find.setOnClickListener(view -> {
            startActivity(new Intent(this, FindActivity.class));

        });

        btn_register.setOnClickListener(view -> {
            startActivity(new Intent(this,SignupActivity.class));

        });

    }
}
