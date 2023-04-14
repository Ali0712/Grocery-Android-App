package com.example.yourmart.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yourmart.R;
import com.example.yourmart.ViewAllActivity;
import com.example.yourmart.model.PopularModule;

import java.util.List;

public class PopularAdapters extends RecyclerView.Adapter<PopularAdapters.ViewHolder>{
    private Context context;
    private List<PopularModule> popularModuleList;

    public PopularAdapters(Context context, List<PopularModule> popularModuleList) {
        this.context = context;
        this.popularModuleList = popularModuleList;
    }

    @NonNull
    @Override
    public PopularAdapters.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapters.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(popularModuleList.get(position).getImg_url()).into(holder.popImg);
        holder.name.setText(popularModuleList.get(position).getName());
        holder.description.setText(popularModuleList.get(position).getDescription());
        holder.rating.setText(popularModuleList.get(position).getRating());
        holder.discount.setText(popularModuleList.get(position).getDiscount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllActivity.class);
//                intent.putExtra("type", popularModuleList.get(position).getType());
                intent.putExtra("type", popularModuleList.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularModuleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popImg;
        TextView name, description,rating,discount,type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        popImg = itemView.findViewById(R.id.pop_image);
        name = itemView.findViewById(R.id.pop_name);
        description = itemView.findViewById(R.id.pop_del);
        discount = itemView.findViewById(R.id.pop_discount);
        rating = itemView.findViewById(R.id.pop_5_0);

        }
    }
}
