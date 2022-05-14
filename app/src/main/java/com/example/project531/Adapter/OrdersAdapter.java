package com.example.project531.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project531.Domain.FoodItem;
import com.example.project531.Domain.OrderItem;
import com.example.project531.IClickOrder;
import com.example.project531.R;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder>{

    ArrayList<OrderItem> itemArrayList;
    IClickOrder iClickOrder;

    public OrdersAdapter(ArrayList<OrderItem> itemArrayList, IClickOrder iClickOrder) {
        this.itemArrayList = itemArrayList;
        this.iClickOrder = iClickOrder;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrdersAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.order_num.setText("Đơn hàng: #" + String.valueOf(itemArrayList.get(position).getOrderNum()));
        holder.order_date.setText("Ngày: " + String.valueOf(itemArrayList.get(position).getDate()));
        holder.order_price.setText(String.valueOf(itemArrayList.get(position).getPrice()) + "đ");
        holder.order_status.setText(String.valueOf(itemArrayList.get(position).getStatus()));

        holder.item_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickOrder.clickItem(itemArrayList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView order_num, order_date, order_price,order_status;
        RelativeLayout item_order;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            order_num = itemView.findViewById(R.id.order_num);
            order_date = itemView.findViewById(R.id.order_date);
            order_price = itemView.findViewById(R.id.order_price);
            order_status = itemView.findViewById(R.id.order_status);
            item_order = itemView.findViewById(R.id.item_order);
        }
    }


}
