package com.example.project531.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.project531.Adapter.RecommendedAdapter;
import com.example.project531.Domain.CategoryDomain;
import com.example.project531.Domain.FoodDomain;
import com.example.project531.Interface.ImplementJson;
import com.example.project531.API.ParseURL;
import com.example.project531.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CateListActivity extends AppCompatActivity {

    private RecyclerView  recyclerViewPopularList;
    private RecyclerView.Adapter  adapter2;
    TextView label_cate;
    private RequestQueue mQueue;
    ParseURL parseURL;
    CategoryDomain object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cate_list);

        Intent i = new Intent(this, MainActivity.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

        object = (CategoryDomain) getIntent().getSerializableExtra("object");

        mQueue = Volley.newRequestQueue(this);
        parseURL = new ParseURL(mQueue);

        recyclerViewPopularList = findViewById(R.id.cate_list);
        label_cate = findViewById(R.id.label_cate);

        label_cate.setText(String.valueOf(object.getTitle()).toString());
        recyclerViewPopular();
    }


    private void recyclerViewPopular() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewPopularList.setLayoutManager(mLayoutManager);
        ArrayList<FoodDomain> foodlist = new ArrayList<>();

        parseURL.ParseData(MainActivity.connectURL+"/cuahang/cate?idsp="+object.getId(), new ImplementJson() {
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