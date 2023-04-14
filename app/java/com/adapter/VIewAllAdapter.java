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
import com.example.yourmart.DetailActivity;
import com.example.yourmart.R;
import com.example.yourmart.model.ViewAllModel;

import java.util.List;

public class VIewAllAdapter extends RecyclerView.Adapter<VIewAllAdapter.ViewHolder> {

    Context context;
    List<ViewAllModel> list;

    public VIewAllAdapter(Context context, List<ViewAllModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VIewAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VIewAllAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.description.setText(list.get(position).getDescription());
//        holder.type.setText(list.get(position).getType());
        holder.price.setText(list.get(position).getPrice()+"$ /Kg");


        if (list.get(position).getType().equals("egg")) {
            holder.price.setText(list.get(position).getPrice()+"$ /dozen");
        }if (list.get(position).getType().equals("milk")) {
            holder.price.setText(list.get(position).getPrice()+"$ /litre");
        }
        int p = holder.getAdapterPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("detail", list.get(p));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, description, type, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.view_img);
            name = itemView.findViewById(R.id.view_name);
            description = itemView.findViewById(R.id.view_desc);
            price = itemView.findViewById(R.id.view_price);

        }
    }
}
