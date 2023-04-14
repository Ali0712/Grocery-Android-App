package com.example.yourmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.yourmart.R;
import com.example.yourmart.adapter.HomeAdapter;
import com.example.yourmart.adapter.NavCategoryDetailedAdapter;
import com.example.yourmart.model.HomeCategory;
import com.example.yourmart.model.NavCatModel;
import com.example.yourmart.model.NavCategoryDetailedModel;
import com.example.yourmart.model.PopularModule;
import com.example.yourmart.model.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NavCategoryActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    List<NavCategoryDetailedModel> list;
    NavCategoryDetailedAdapter adapter;
    FirebaseFirestore db;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_category);

        String type = getIntent().getStringExtra("type");


        db = FirebaseFirestore.getInstance();
        toolbar = findViewById(R.id.dec_cat_toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.nav_cat_det_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new NavCategoryDetailedAdapter(this, list);
        recyclerView.setAdapter(adapter);
//
//        if (type != null && type.equalsIgnoreCase("fruits")) {
//            db.collection("NavCategoryDetailed").whereEqualTo("type", "fruits").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
//                        NavCategoryDetailedModel navCategoryDetailedModel = documentSnapshot.toObject(NavCategoryDetailedModel.class);
//                        list.add(navCategoryDetailedModel);
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//            });
//        }

    }
}