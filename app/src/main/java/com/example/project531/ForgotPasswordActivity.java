package com.example.project531;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.project531.API.ParseURL;
import com.example.project531.Activity.MainActivity;
import com.example.project531.Interface.ImplementJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button button_signin;

    private RequestQueue mQueue;
    ParseURL parseURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        Intent i = new Intent(this, LoginActivity.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });


        mQueue = Volley.newRequestQueue(this);
        parseURL = new ParseURL(mQueue);

        Intent ixxx = new Intent(this, MainActivity.class);
        button_signin = findViewById(R.id.button_signin);
        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String sdt = (String) getIntent().getSerializableExtra("stringsearch");

                parseURL.ParseData(MainActivity.connectURL + "/users/getphone?sdt="+sdt, new ImplementJson() {
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

                            if(jsonArray.length() == 0){
                                Toast.makeText(getApplicationContext(), "Fail to Connect Acount!!", Toast.LENGTH_LONG).show();
                            }else{
                                Intent ok = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                                startActivity(ok);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });







                startActivity(ixxx);
            }
        });


    }
}