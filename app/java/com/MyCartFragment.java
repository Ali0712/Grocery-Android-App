package com.example.yourmart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yourmart.adapter.MyCartAdapter;
import com.example.yourmart.model.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth auth;

    TextView overTotalAmount;
    RecyclerView recyclerView;
    MyCartAdapter CartAdapter;
    List<MyCartModel> cartModelList;

    Button buyNow;
    int totalBill;



    public MyCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_cart, container, false);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        overTotalAmount = root.findViewById(R.id.textView4);
        buyNow = root.findViewById(R.id.buy_now);

        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mMessageReciever,new IntentFilter("MyTotalAmount"));
        cartModelList = new ArrayList<>();
        CartAdapter = new MyCartAdapter(getActivity(), cartModelList);
        recyclerView.setAdapter(CartAdapter);
        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                                String documentId = documentSnapshot.getId();

                                MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
                                cartModel.setDocumentId(documentId);


                                cartModelList.add(cartModel);
                                CartAdapter.notifyDataSetChanged();

                            }

                            calculateTotalAmount(cartModelList);
                        }
                    }

                    private void calculateTotalAmount(List<MyCartModel> cartModelList) {
                            double total = 0.0;
                            for (MyCartModel model: cartModelList){
                                total += model.getTotalPrice();
                            }

                            overTotalAmount.setText("Total Amount: "+ total);

                     }
                });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PlaceOrderActivity.class);
                intent.putExtra("itemList", (Serializable) cartModelList);
                startActivity(intent);
            }
        });


        return root;
    }
    public BroadcastReceiver mMessageReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalBill = intent.getIntExtra("totalAmount", 0);
            overTotalAmount.setText("Total Bill : "+ totalBill+ "/-");
        }
    };

}