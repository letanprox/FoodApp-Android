package com.example.project531.DonHang;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.project531.Activity.MainActivity;
import com.example.project531.Adapter.FoodListAdapterOrdered;
import com.example.project531.Domain.FoodItem;
import com.example.project531.Domain.OrderItem;
import com.example.project531.Interface.ImplementJson;
import com.example.project531.API.ParseURL;
import com.example.project531.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailOrderDialog extends DialogFragment {

    private RecyclerView rcv_detail_order;
    private RecyclerView.Adapter adapter;

    private RequestQueue mQueue;
    ParseURL parseURL;

    OrderItem orderItem;

    public DetailOrderDialog(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_order, container,false);

        rcv_detail_order = view.findViewById(R.id.rcv_detail_order);



        mQueue = Volley.newRequestQueue(getContext());
        parseURL = new ParseURL(mQueue);

        recyclerViewListFood();

        return view;
    }


    private void recyclerViewListFood() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        rcv_detail_order.setLayoutManager(linearLayoutManager);

        ArrayList<FoodItem> foodlist = new ArrayList<>();




        parseURL.ParseData(MainActivity.connectURL + "/donhang_sanpham/list?id="+orderItem.getOrderNum(), new ImplementJson() {
            @Override
            public void Done(JSONArray jsonArray) {

                try{
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = null;
                        data = jsonArray.getJSONObject(i);


                        String TEN = data.getString("TEN");
                        String ANH = data.getString("ANH");
                        int SOLUONGBAN = data.getInt("SOLUONG");
                        double GIA = data.getDouble("GIA");


                        foodlist.add(new FoodItem(1,TEN, ANH, "", GIA, SOLUONGBAN, SOLUONGBAN));
                    }

                    Log.e("xxx", MainActivity.connectURL + "/donhang_sanpham/list?id="+orderItem.getOrderNum());


                    adapter = new FoodListAdapterOrdered(foodlist);
                    rcv_detail_order.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }
}
