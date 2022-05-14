package com.example.project531;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project531.Adapter.FoodListAdapter;
import com.example.project531.Adapter.FoodListAdapterOrdered;
import com.example.project531.Domain.FoodItem;

import java.util.ArrayList;

public class DetailOrderDialog extends DialogFragment {

    private RecyclerView rcv_detail_order;
    private RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_order, container,false);

        rcv_detail_order = view.findViewById(R.id.rcv_detail_order);
        recyclerViewListFood();

        return view;
    }


    private void recyclerViewListFood() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        rcv_detail_order.setLayoutManager(linearLayoutManager);

        ArrayList<FoodItem> foodlist = new ArrayList<>();
        foodlist.add(new FoodItem("Pepperoni pizza", "pizza1", "slices pepperoni ,mozzarella cheese, fresh oregano,  ground black pepper, pizza sauce", 13.0, 5, 0));
        foodlist.add(new FoodItem("Chesse Burger", "burger", "beef, Gouda Cheese, Special sauce, Lettuce, tomato ", 15.20, 4, 2));
        foodlist.add(new FoodItem("Vagetable pizza", "pizza3", " olive oil, Vegetable oil, pitted Kalamata, cherry tomatoes, fresh oregano, basil", 11.0, 3, 3));

        adapter = new FoodListAdapterOrdered(foodlist);
        rcv_detail_order.setAdapter(adapter);
    }
}
