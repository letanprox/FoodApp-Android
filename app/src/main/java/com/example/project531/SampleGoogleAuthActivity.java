package com.example.project531;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SampleGoogleAuthActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    Button button;

    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_google_auth);


        textView = findViewById(R.id.text_view);
        imageView = findViewById(R.id.image_view);
        button = findViewById(R.id.button_log);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            Glide.with(SampleGoogleAuthActivity.this).load(firebaseUser.getPhotoUrl()).into(imageView);
             textView.setText(firebaseUser.getEmail());
        }


        googleSignInClient = GoogleSignIn.getClient(SampleGoogleAuthActivity.this, GoogleSignInOptions.DEFAULT_SIGN_IN);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            firebaseAuth.signOut();
                            finish();
                        }
                    }
                });
            }
        });

    }
}