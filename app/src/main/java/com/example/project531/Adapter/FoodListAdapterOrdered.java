package com.example.project531.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project531.Domain.FoodItem;
import com.example.project531.R;

import java.util.ArrayList;

public class FoodListAdapterOrdered extends RecyclerView.Adapter<FoodListAdapterOrdered.ViewHolder>{

    ArrayList<FoodItem> itemArrayList;

    public FoodListAdapterOrdered(ArrayList<FoodItem> itemArrayList) {
        this.itemArrayList = itemArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_ordered, parent, false);
        return new FoodListAdapterOrdered.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title_food.setText(itemArrayList.get(position).getTitle());
        holder.price_food.setText(String.valueOf(itemArrayList.get(position).getFee()) + "đ");

        int drawableReourceId = holder.itemView.getContext().getResources()
                .getIdentifier(itemArrayList.get(position).getPic(), "drawable",
                        holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableReourceId)
                .into(holder.image_food);
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image_food;
        TextView title_food, price_food,numberItemTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_food = itemView.findViewById(R.id.image_food);
            title_food = itemView.findViewById(R.id.title_food);
            price_food = itemView.findViewById(R.id.price_food);
            numberItemTxt = itemView.findViewById(R.id.numberItemTxt);
        }
    }

}
