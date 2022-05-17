package com.example.project531.Home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.project531.API.ParseURL;
import com.example.project531.Activity.ListFoodActivity;
import com.example.project531.Activity.MainActivity;
import com.example.project531.Adapter.CategoryAdapter;
import com.example.project531.Adapter.RecommendedAdapter;
import com.example.project531.Domain.CategoryDomain;
import com.example.project531.Domain.FoodDomain;
import com.example.project531.Interface.ImplementJson;
import com.example.project531.ProfileActivity;
import com.example.project531.R;
import com.example.project531.SanPham.ShowDetailActivity;
import com.example.project531.SearchActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ImageView avatar_btn;
    TextView see_more_btn, textView5, textView6;
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategotyList, recyclerViewPopularList;


    private RequestQueue mQueue;
    ParseURL parseURL;

    EditText search_input;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);
        Intent i1 = new Intent(getActivity(), ProfileActivity.class);

        recyclerViewCategotyList = view.findViewById(R.id.view1);
        recyclerViewPopularList = view.findViewById(R.id.view2);


        avatar_btn = view.findViewById(R.id.avatar_btn);
        textView5 = view.findViewById(R.id.textView5);
        textView6 = view.findViewById(R.id.textView6);

        textView5.setText("Xin chào " + MainActivity.TEN);

        try {
            Picasso.get()
                    .load(MainActivity.ANH)
                    .into(avatar_btn);
        }catch (Exception e){
        }



        avatar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i1);
            }
        });

        mQueue = Volley.newRequestQueue(getContext());
        parseURL = new ParseURL(mQueue);

        recyclerViewCategoty();
        recyclerViewPopular();

        see_more_btn = view.findViewById(R.id.see_more_btn);
        Intent i2 = new Intent(getActivity(), ListFoodActivity.class);
        see_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i2);
            }
        });


        search_input = view.findViewById(R.id.editTextTextPersonName);
        search_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    //Perform your Actions here.
                    Intent intent = new Intent(getContext(), SearchActivity.class);
                    intent.putExtra("stringsearch", search_input.getText().toString());
                    getContext().startActivity(intent);
                }
                return handled;
            }
        });


        return view;
    }





    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> foodlist = new ArrayList<>();

        parseURL.ParseData(MainActivity.connectURL+"/cuahang/dexuatlist", new ImplementJson() {
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

                    adapter2 = new RecommendedAdapter(foodlist,1);
                    recyclerViewPopularList.setAdapter(adapter2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void recyclerViewCategoty() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategotyList.setLayoutManager(linearLayoutManager);


        ArrayList<CategoryDomain> categoryList = new ArrayList<>();

        parseURL.ParseData(MainActivity.connectURL+"/loaisanpham/list", new ImplementJson() {
            @Override
            public void Done(JSONArray jsonArray) {
                try{
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = null;
                        data = jsonArray.getJSONObject(i);

                        int ID = data.getInt("ID");
                        String TEN = data.getString("TEN");
                        String ANH = data.getString("ANH");

                        categoryList.add(new CategoryDomain( ID, TEN, ANH));

                        adapter = new CategoryAdapter(categoryList);
                        recyclerViewCategotyList.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }


}
