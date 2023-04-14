package com.example.yourmart.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourmart.R;
import com.example.yourmart.model.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    Context context;
    List<MyCartModel> cartModelList;
    int totalPrice = 0;
    FirebaseFirestore firestore;
    FirebaseAuth auth;


    public MyCartAdapter(Context context, List<MyCartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.name.setText(cartModelList.get(position).getProductName());
        holder.price.setText(cartModelList.get(position).getProductPrice());
        holder.date.setText(cartModelList.get(position).getCurrentDate());
        holder.time.setText(cartModelList.get(position).getCurrentTime());
        holder.quantity.setText(cartModelList.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(cartModelList.get(position).getTotalPrice()));


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("AddToCart").document(cartModelList.get(position).getDocumentId())
                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    cartModelList.remove(cartModelList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Error: "+ task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        //pass total amount to my cart fragment
        totalPrice = totalPrice + cartModelList.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {return cartModelList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    TextView name, price, date, time, quantity, totalPrice;
    ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.productPrice);
            date = itemView.findViewById(R.id.currentDate);
            time = itemView.findViewById(R.id.currentTime);
            quantity = itemView.findViewById(R.id.TotalQuantity);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            delete = itemView.findViewById(R.id.delete);

        }
    }
}
