package com.example.project531.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project531.Activity.ShowDetailActivity;
import com.example.project531.Domain.FoodDomain;
import com.example.project531.Domain.FoodItem;
import com.example.project531.R;

import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder>{

    ArrayList<FoodItem> itemArrayList;

    public FoodListAdapter(ArrayList<FoodItem> itemArrayList) {
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodListAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title_food.setText(itemArrayList.get(position).getTitle());
        holder.price_food.setText(String.valueOf(itemArrayList.get(position).getFee()) + "đ");
        holder.number_sold.setText( "Đã bán: " +String.valueOf(itemArrayList.get(position).getSold()) +"+");

        if(itemArrayList.get(position).getNumberInCart() > 0){
            holder.minusCardBtn.setVisibility(View.VISIBLE);
            holder.numberItemTxt.setVisibility(View.VISIBLE);
            holder.numberItemTxt.setText(String.valueOf(itemArrayList.get(position).getNumberInCart()));
        }else {
            holder.minusCardBtn.setVisibility(View.GONE);
            holder.numberItemTxt.setVisibility(View.GONE);
        }

        int drawableReourceId = holder.itemView.getContext().getResources()
                .getIdentifier(itemArrayList.get(position).getPic(), "drawable",
                        holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableReourceId)
                .into(holder.image_food);


        holder.plusCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemArrayList.get(position).setNumberInCart(itemArrayList.get(position).getNumberInCart() + 1);
                holder.minusCardBtn.setVisibility(View.VISIBLE);
                holder.numberItemTxt.setVisibility(View.VISIBLE);
                holder.numberItemTxt.setText(String.valueOf(itemArrayList.get(position).getNumberInCart()));
            }
        });

        holder.minusCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemArrayList.get(position).setNumberInCart(itemArrayList.get(position).getNumberInCart() - 1);
                holder.numberItemTxt.setText(String.valueOf(itemArrayList.get(position).getNumberInCart()));
                if(itemArrayList.get(position).getNumberInCart() < 1){
                    holder.minusCardBtn.setVisibility(View.GONE);
                    holder.numberItemTxt.setVisibility(View.GONE);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image_food, minusCardBtn, plusCardBtn;
        TextView title_food, number_sold, price_food,numberItemTxt;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_food = itemView.findViewById(R.id.image_food);
            minusCardBtn = itemView.findViewById(R.id.minusCardBtn);
            plusCardBtn = itemView.findViewById(R.id.plusCardBtn);
            title_food = itemView.findViewById(R.id.title_food);
            number_sold = itemView.findViewById(R.id.number_sold);
            price_food = itemView.findViewById(R.id.price_food);
            numberItemTxt = itemView.findViewById(R.id.numberItemTxt);
        }
    }
}
