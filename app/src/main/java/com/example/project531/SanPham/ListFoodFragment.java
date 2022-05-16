package com.example.project531.SanPham;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project531.Adapter.FoodListAdapter;
import com.example.project531.Adapter.RecommendedAdapter;
import com.example.project531.Domain.FoodDomain;
import com.example.project531.Domain.FoodItem;
import com.example.project531.R;

import java.util.ArrayList;


public class ListFoodFragment extends Fragment {

    private RecyclerView rcv_food_list;
    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter adapterZero;

    ArrayList<FoodItem> foodlist;

    public ListFoodFragment(ArrayList<FoodItem> foodlist, RecyclerView.Adapter adapterZero) {
        this.foodlist = foodlist;
        this.adapterZero = adapterZero;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_food_fragment, container, false);
        rcv_food_list = view.findViewById(R.id.rcv_list_food);
        recyclerViewListFood();
        return  view;
    }


    private void recyclerViewListFood() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcv_food_list.setLayoutManager(linearLayoutManager);
        rcv_food_list.setAdapter(adapterZero);
    }
}
