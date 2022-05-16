package com.example.project531.Home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.project531.API.ParseURL;
import com.example.project531.Activity.MainActivity;
import com.example.project531.Adapter.RecommendedAdapter;
import com.example.project531.Domain.FoodDomain;
import com.example.project531.Interface.ImplementJson;
import com.example.project531.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrdersFragment extends Fragment {

    private RecyclerView rcv_book_mark;
    private RecyclerView.Adapter adapter;

    private RequestQueue mQueue;
    ParseURL parseURL;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orders_fragment, container, false);

        mQueue = Volley.newRequestQueue(getContext());
        parseURL = new ParseURL(mQueue);

        rcv_book_mark = view.findViewById(R.id.rcv_book_mark);
        recyclerViewListFood();

        return  view;
    }


    private void recyclerViewListFood() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);

        rcv_book_mark.setLayoutManager(mLayoutManager);

        ArrayList<FoodDomain> foodlist = new ArrayList<>();
//        foodlist.add(new FoodDomain("Pepperoni pizza", "pizza1", "slices pepperoni ,mozzarella cheese, fresh oregano,  ground black pepper, pizza sauce", 13.0, 5, 20, 1000));
//        foodlist.add(new FoodDomain("Chesse Burger", "burger", "beef, Gouda Cheese, Special sauce, Lettuce, tomato ", 15.20, 4, 18, 1500));
//        foodlist.add(new FoodDomain("Vagetable pizza", "pizza3", " olive oil, Vegetable oil, pitted Kalamata, cherry tomatoes, fresh oregano, basil", 11.0, 3, 16, 800));
//

        parseURL.ParseData(MainActivity.connectURL+"/getsave?iduser="+MainActivity.ID_USER, new ImplementJson() {
            @Override
            public void Done(JSONArray jsonArray) {

                Log.e("",MainActivity.connectURL+"/getsave?iduser="+MainActivity.ID_USER);
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

                    adapter = new RecommendedAdapter(foodlist,2);
                    rcv_book_mark.setAdapter(adapter);
                } catch (JSONException e) {
                    Log.e("", String.valueOf(e));
                    e.printStackTrace();
                }
            }
        });


    }
}
