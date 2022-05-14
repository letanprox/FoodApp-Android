package com.example.project531;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {


    Button button_forgot_password, button_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button_forgot_password = findViewById(R.id.button_forgot_password);
        button_signup = findViewById(R.id.button_signup);

        Intent i1 = new Intent(this, ForgotPasswordActivity.class);
        button_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i1);
            }
        });


        Intent i2 = new Intent(this, SignupActivity.class);
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i2);
            }
        });



    }
}