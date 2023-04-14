package com.example.yourmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yourmart.model.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class DetailActivity extends AppCompatActivity {

    ImageView detailImg, addItem, removeItem;
    TextView price, description, quantity, name;
    Button addToCart;
    Toolbar toolbar;
    ViewAllModel viewAllModel = null;
    int totalQuantity = 1;
    int totalPrice = 0;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof ViewAllModel) {
            viewAllModel = (ViewAllModel) object;
        }

        detailImg = findViewById(R.id.detail_img);
        addItem = findViewById(R.id.add_items);
        removeItem = findViewById(R.id.remove_items);
        price = findViewById(R.id.detail_price);
        name = findViewById(R.id.title_detail);
        description = findViewById(R.id.detail_desc);
        quantity = findViewById(R.id.quantity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (viewAllModel != null) {
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailImg);
            description.setText(viewAllModel.getDescription());
            name.setText(viewAllModel.getName());
            price.setText(viewAllModel.getPrice()+ "$ /Kg");

            totalPrice = viewAllModel.getPrice() * totalQuantity;

            if (viewAllModel.getType().equals("egg")) {
                price.setText(viewAllModel.getPrice()+ "$ /dozen");
                totalPrice = viewAllModel.getPrice() * totalQuantity;

            }if (viewAllModel.getType().equals("milk")) {
                price.setText(viewAllModel.getPrice()+ "$ /litre");
                totalPrice = viewAllModel.getPrice() * totalQuantity;
            }
        }

        addToCart= findViewById(R.id.addtoCart);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (totalQuantity < 10) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = viewAllModel.getPrice() * totalQuantity;
                }
                else{
                    Toast.makeText(DetailActivity.this, "Can't add more than 10", Toast.LENGTH_SHORT).show();
                }

            }
        });
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (totalQuantity > 0) {
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = viewAllModel.getPrice() * totalQuantity;
                }

            }
        });


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedToCart();
            }

            private void addedToCart() {
                String saveCurrentdate, saveCurrnetTime;
                Calendar calforDate = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                saveCurrentdate = currentDate.format(calforDate.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
                saveCurrnetTime = currentTime.format(calforDate.getTime());

                final HashMap<String, Object> cartMap = new HashMap<>();

                cartMap.put("productName", viewAllModel.getName());
                cartMap.put("productPrice", price.getText().toString());
                cartMap.put("currentDate", saveCurrentdate);
                cartMap.put("currentTime", saveCurrnetTime);
                cartMap.put("totalQuantity", quantity.getText().toString());
                cartMap.put("totalPrice", totalPrice);


                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(DetailActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        });
            }
        });

    }
}