package com.example.project531;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.project531.API.ParseURL;
import com.example.project531.Activity.MainActivity;
import com.example.project531.Adapter.FoodListAdapterOrdered;
import com.example.project531.Domain.FoodItem;
import com.example.project531.Interface.ImplementJson;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupActivity extends AppCompatActivity {

    CircleImageView circleImageView;
    Button button_signin;
    TextInputEditText et_email,et_username,et_phone;
    EditText et_password,et_confirm_password;

    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    private RequestQueue mQueue;
    ParseURL parseURL;

    ProgressDialog progressDialog;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Intent i = new Intent(this, LoginActivity.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mQueue = Volley.newRequestQueue(this);
        parseURL = new ParseURL(mQueue);

        circleImageView = findViewById(R.id.image);
        button_signin = findViewById(R.id.button_signin);

        et_email = findViewById(R.id.et_email);
        et_username = findViewById(R.id.et_username);
        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile(new ISignup() {
                    @Override
                    public void Done(String url) {
                        Log.e("anh: ", url );
                        String Txturl =  URLEncoder.encode(url);
//                            Log.e("ID", MainActivity.connectURL + "/users/insert?ten="+et_username.getText().toString()+"&anh="+url+"&email="+et_email.getText().toString()+"&matkhau="+et_password.getText().toString()+"&sdt="+et_phone.getText().toString());
                            parseURL.ParseData(MainActivity.connectURL + "/api/user/insert?ten="+et_username.getText().toString()+"&anh="+Txturl+"&email="+et_email.getText().toString()+"&matkhau="+et_password.getText().toString()+"&sdt="+et_phone.getText().toString(), new ImplementJson() {
                                @Override
                                public void Done(JSONArray jsonArray) {
                                    try{
                                        progressDialog.dismiss();
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject data = null;
                                            data = jsonArray.getJSONObject(i);

                                            int ID = data.getInt("ID");
                                            Log.e("ID", String.valueOf(ID));

                                            MainActivity.database.INSERT_USER(
                                                    ID,
                                                    et_username.getText().toString(),
                                                    Txturl,
                                                    et_phone.getText().toString(),
                                                    et_password.getText().toString(),
                                                    et_email.getText().toString()
                                            );
                                            Intent ok = new Intent(SignupActivity.this, MainActivity.class);
                                            startActivity(ok);
                                        }
                                        if(jsonArray.length() == 0){
                                            Toast.makeText(getApplicationContext(), "Fail to Create Acount!!", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    }
                });
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
                mImageUri = data.getData();
                Picasso.get().load(mImageUri).into(circleImageView);
        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadFile(ISignup iSignup) {
        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    iSignup.Done(String.valueOf( uri.toString()));
                                }
                            });

                            Toast.makeText(SignupActivity.this, "Upload successful", Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

}