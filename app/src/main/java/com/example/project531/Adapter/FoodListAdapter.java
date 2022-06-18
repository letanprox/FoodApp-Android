package com.example.project531.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project531.Domain.FoodItem;
import com.example.project531.Interface.IEventAddCart;
import com.example.project531.Interface.IEventMinusCart;
import com.example.project531.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder>{

    ArrayList<FoodItem> itemArrayList;
    IEventAddCart iEventAddCart;
    IEventMinusCart iEventMinusCart;

    public FoodListAdapter(ArrayList<FoodItem> itemArrayList, IEventAddCart iEventAddCart,IEventMinusCart iEventMinusCart) {
        this.itemArrayList = itemArrayList;
        this.iEventAddCart = iEventAddCart;
        this.iEventMinusCart = iEventMinusCart;
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

        holder.plusCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iEventAddCart.Done(itemArrayList.get(position));
                itemArrayList.get(position).setNumberInCart(itemArrayList.get(position).getNumberInCart() + 1);
                holder.minusCardBtn.setVisibility(View.VISIBLE);
                holder.numberItemTxt.setVisibility(View.VISIBLE);
                holder.numberItemTxt.setText(String.valueOf(itemArrayList.get(position).getNumberInCart()));
            }
        });

        holder.minusCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iEventMinusCart.Done(itemArrayList.get(position));
                itemArrayList.get(position).setNumberInCart(itemArrayList.get(position).getNumberInCart() - 1);
                holder.numberItemTxt.setText(String.valueOf(itemArrayList.get(position).getNumberInCart()));
                if(itemArrayList.get(position).getNumberInCart() < 1){
                    holder.minusCardBtn.setVisibility(View.GONE);
                    holder.numberItemTxt.setVisibility(View.GONE);
                }

            }
        });

        Picasso.get()
                .load(itemArrayList.get(position).getPic())
                .fit()
                .centerCrop()
                .into(holder.image_food);




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
