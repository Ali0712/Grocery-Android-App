package com.example.yourmart.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourmart.R;
import com.example.yourmart.adapter.NavCategoryAdapter;
import com.example.yourmart.databinding.FragmentCategoryBinding;
import com.example.yourmart.model.NavCatModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class categoryFragment extends Fragment {

    FirebaseFirestore db;

    List<NavCatModel> catModelList;
    NavCategoryAdapter navCategoryAdapter;
    RecyclerView recyclerView;

    private FragmentCategoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = FirebaseFirestore.getInstance();

        recyclerView = root.findViewById(R.id.cat_rec);
        // HOme category
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        catModelList = new ArrayList<>();
        navCategoryAdapter = new NavCategoryAdapter(getActivity(), catModelList);
        recyclerView.setAdapter(navCategoryAdapter);

        db.collection("NavCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NavCatModel navCatModel = document.toObject(NavCatModel.class);
                                catModelList.add(navCatModel);
                                navCategoryAdapter.notifyDataSetChanged();

                            }
                        } else {
                            Toast.makeText(getActivity(), "Error "+ task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}