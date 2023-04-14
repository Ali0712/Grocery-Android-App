package com.example.yourmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.yourmart.adapter.VIewAllAdapter;
import com.example.yourmart.model.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewAllActivity extends AppCompatActivity {

    FirebaseFirestore firestore;

    List<ViewAllModel> viewAllModelList;
    VIewAllAdapter vIewAllAdapter;
    RecyclerView recyclerView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        toolbar = findViewById(R.id.toolbar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        setSupportActionBar(toolbar);

//        getSupportActionBar().hide();

        firestore = FirebaseFirestore.getInstance();

        String type = getIntent().getStringExtra("type");
        recyclerView = findViewById(R.id.view_all_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewAllModelList = new ArrayList<>();
        vIewAllAdapter = new VIewAllAdapter(this, viewAllModelList);

        recyclerView.setAdapter(vIewAllAdapter);


        // getting fruits
        if (type != null && type.equalsIgnoreCase("fruits")) {
            firestore.collection("AllProducts").whereEqualTo("type", "fruits").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        vIewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        // getting vegetables
        if (type != null && type.equalsIgnoreCase("vegetable")) {
            firestore.collection("AllProducts").whereEqualTo("type", "vegetable").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        vIewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        // getting milk
        if (type != null && type.equalsIgnoreCase("milk")) {
            firestore.collection("AllProducts").whereEqualTo("type", "milk").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        vIewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        // getting eggs
        if (type != null && type.equalsIgnoreCase("egg")) {
            firestore.collection("AllProducts").whereEqualTo("type", "egg").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        vIewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        // getting fish
        if (type != null && type.equalsIgnoreCase("fish")) {
            firestore.collection("AllProducts").whereEqualTo("type", "fish").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        vIewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }


    }
}