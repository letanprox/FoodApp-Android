package com.example.project531;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.project531.API.ParseURL;
import com.example.project531.Activity.MainActivity;
import com.example.project531.Adapter.RecommendedAdapter;
import com.example.project531.Domain.FoodDomain;
import com.example.project531.Interface.ImplementJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {


    private RecyclerView  recyclerViewPopularList;
    private RecyclerView.Adapter  adapter2;

    private RequestQueue mQueue;
    ParseURL parseURL;

    String setxt;

    TextView textView13;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        mQueue = Volley.newRequestQueue(this);
        parseURL = new ParseURL(mQueue);

        recyclerViewPopularList = findViewById(R.id.recommended_list);

        Intent i = new Intent(this, MainActivity.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });


        setxt = (String) getIntent().getSerializableExtra("stringsearch");

        textView13 = findViewById(R.id.textView13);
        textView13.setText("Tìm kiếm: "+ setxt);
        //Toast.makeText(this, (String) getIntent().getSerializableExtra("stringsearch"), Toast.LENGTH_LONG).show();

        recyclerViewPopular();


    }











    private void recyclerViewPopular() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewPopularList.setLayoutManager(mLayoutManager);

        ArrayList<FoodDomain> foodlist = new ArrayList<>();

        parseURL.ParseData(MainActivity.connectURL+"/cuahang/timkiemlist?setxt="+setxt, new ImplementJson() {
            @Override
            public void Done(JSONArray jsonArray) {
                try{
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = null;
                        data = jsonArray.getJSONObject(i);

                        String TEN = data.getString("TEN");
                        String ANH = data.getString("ANH");
                        String MOTA = data.getString("MOTA");
                        Double DANHGIA = data.getDouble("DANHGIA");
                        String VITRI = data.getString("VITRI");
                        Double GIATB = data.getDouble("GIATB");
                        String THOIGIANMO = data.getString("THOIGIANMO");
                        int ID = data.getInt("ID");

                        foodlist.add(new FoodDomain(ID,TEN, ANH, MOTA, GIATB, DANHGIA, 0, 0,0,VITRI,THOIGIANMO));

                    }

                    adapter2 = new RecommendedAdapter(foodlist,2);
                    recyclerViewPopularList.setAdapter(adapter2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}