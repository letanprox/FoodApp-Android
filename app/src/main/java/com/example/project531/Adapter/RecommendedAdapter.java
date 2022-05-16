package com.example.project531.Adapter;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project531.SanPham.ShowDetailActivity;
import com.example.project531.Domain.FoodDomain;
import com.example.project531.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {
    ArrayList<FoodDomain> RecommendedDomains;
    int check;

    public RecommendedAdapter(ArrayList<FoodDomain> RecommendedDomains, int check) {
        this.RecommendedDomains = RecommendedDomains;
        this.check = check;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate;
        if(check == 1)
            inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_recommended, parent, false);
        else
            inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_recommended1, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(RecommendedDomains.get(position).getTitle());
        holder.fee.setText(String.valueOf(RecommendedDomains.get(position).getStar()));


        int drawableReourceId = holder.itemView.getContext().getResources()
                .getIdentifier(RecommendedDomains.get(position).getPic(), "drawable",
                        holder.itemView.getContext().getPackageName());

            Picasso.get()
                    .load(RecommendedDomains.get(position).getPic())
                    .fit()
                    .centerCrop()
                    .into(holder.pic);

            holder.item.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object", RecommendedDomains.get(position));
                holder.itemView.getContext().startActivity(intent);
            });

    }


    @Override
    public int getItemCount() {
        return RecommendedDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, fee;
        ImageView pic;
        ImageView addBtn;
        LinearLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            pic = itemView.findViewById(R.id.pic);
            fee = itemView.findViewById(R.id.fee);
            addBtn = itemView.findViewById(R.id.addBtn);
            item = itemView.findViewById(R.id.item_food_recommended);
        }
    }
}
