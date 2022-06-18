package com.example.project531;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.project531.API.ParseURL;
import com.example.project531.Activity.MainActivity;
import com.example.project531.Interface.ImplementJson;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;

public class LoginActivity extends AppCompatActivity {

    Button button_forgot_password, button_signup, button_signin;
    EditText et_password, et_username;
    ImageView google_btn;


    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;

    private RequestQueue mQueue;
    ParseURL parseURL;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mQueue = Volley.newRequestQueue(this);
        parseURL = new ParseURL(mQueue);

        button_forgot_password = findViewById(R.id.button_forgot_password);
        button_signup = findViewById(R.id.button_signup);
        google_btn = findViewById(R.id.google_btn);

        /////////////////////////////////////////////////////////////////
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
        ).requestIdToken("901183913660-4gcebqh0im7ehkmu4pmf752cs5lvosh7.apps.googleusercontent.com")
                .requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, googleSignInOptions);

        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //GOOGLE AUTH:
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, 100);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
//            startActivity(new Intent(LoginActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

        //////////////////////////////////////////////

        Intent i1 = new Intent(this, OTPSampleActivity.class);
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

        button_signin = findViewById(R.id.button_signin);
        et_password = findViewById(R.id.et_password);
        et_username = findViewById(R.id.et_username);

        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                parseURL.ParseData(MainActivity.connectURL + "/api/users/get?ten="+et_username.getText().toString()+"&matkhau="+et_password.getText().toString(), new ImplementJson() {
                    @Override
                    public void Done(JSONArray jsonArray) {
                        try{
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = null;
                                data = jsonArray.getJSONObject(i);
                                int ID = data.getInt("ID");
                                Log.e("ID", String.valueOf(ID));
                                MainActivity.database.INSERT_USER(
                                        data.getInt("ID"),
                                        data.getString("TEN"),
                                        data.getString("ANH"),
                                        data.getString("SDT"),
                                        data.getString("MATKHAU"),
                                        data.getString("EMAIL")
                                );
                            }
                            progressDialog.dismiss();
                            if(jsonArray.length() == 0){
                                Toast.makeText(getApplicationContext(), "Fail to Create Acount!!", Toast.LENGTH_LONG).show();
                            }else{
                                Intent ok = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(ok);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            displayToast(String.valueOf(signInAccountTask.isSuccessful()));
            Log.e("xxx",data.toString());
            if(signInAccountTask.isSuccessful()){
                String s = "Google sign in successful";
                displayToast(s);
                try {
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);

                    if(googleSignInAccount != null){

                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

                        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    //REDIRECT
                                    firebaseAuth = FirebaseAuth.getInstance();
                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                    if(firebaseUser != null){
                                        Log.e("anh dai dien:" , String.valueOf(firebaseUser.getPhotoUrl()));

                                        parseURL.ParseData(MainActivity.connectURL + "/api/user/googleauth?ten="+String.valueOf(firebaseUser.getDisplayName())+"&email="+String.valueOf(firebaseUser.getEmail())+"&anh="+String.valueOf(firebaseUser.getPhotoUrl()), new ImplementJson() {
                                            @Override
                                            public void Done(JSONArray jsonArray) {
                                                try{
                                                    for (int i = 0; i < jsonArray.length(); i++) {
                                                        JSONObject data = null;
                                                        data = jsonArray.getJSONObject(i);
                                                        int ID = data.getInt("ID");
                                                        Log.e("ID", String.valueOf(ID));
                                                        MainActivity.database.INSERT_USER(
                                                                data.getInt("ID"),
                                                                data.getString("TEN"),
                                                                data.getString("ANH"),
                                                                data.getString("SDT"),
                                                                data.getString("MATKHAU"),
                                                                data.getString("EMAIL")
                                                        );
                                                    }
                                                    try {
                                                        progressDialog.dismiss();
                                                    }catch (Exception e){

                                                    }

                                                    if(jsonArray.length() == 0){
                                                        Toast.makeText(getApplicationContext(), "Fail to Login Acount!!", Toast.LENGTH_LONG).show();
                                                    }else{
                                                        Intent ok = new Intent(LoginActivity.this, MainActivity.class);
                                                        startActivity(ok);
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                    //startActivity(new Intent(LoginActivity.this, SampleGoogleAuthActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                }else {
                                    Log.e("ooo", task.getException().getMessage());
                                }
                            }
                        });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    public  void displayToast(String s){
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}


















