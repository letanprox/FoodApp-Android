package com.example.project531;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ChangePasswordDialog;
import com.example.ChangePositionDialog;
import com.example.project531.Activity.MainActivity;

public class ProfileActivity extends AppCompatActivity {

    ChangePasswordDialog changePasswordDialog;
    ChangePositionDialog changePositionDialog;
    TextView changepass_btn, changeposition_btn;
    LinearLayout setting_btn;


    AppCompatImageView logout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        changepass_btn = findViewById(R.id.changepass_btn);
        changepass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePasswordDialog = new ChangePasswordDialog();
                changePasswordDialog.show(getSupportFragmentManager(),"DIALOGDETAIL");
            }
        });


        changeposition_btn = findViewById(R.id.changeposition_btn);
//        changeposition_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                changePositionDialog = new ChangePositionDialog();
//                changePositionDialog.show(getSupportFragmentManager(),"DIALOGDETAIL");
//            }
//        });


        Intent A = new Intent(this, EditAccountActivity.class);
        setting_btn = findViewById(R.id.setting_btn);
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(A);
            }
        });



        Intent i = new Intent(this, MainActivity.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });




        Intent iA = new Intent(this, LoginActivity.class);

        logout_btn = findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"logout", Toast.LENGTH_SHORT).show();
                startActivity(iA);
            }
        });

    }
}