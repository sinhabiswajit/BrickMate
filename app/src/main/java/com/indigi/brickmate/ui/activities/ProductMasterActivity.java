package com.indigi.brickmate.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.indigi.brickmate.ui.adapters.ProductAdapter;
import com.indigi.brickmate.R;
import com.indigi.brickmate.model.Product;

import java.util.ArrayList;

public class ProductMasterActivity extends AppCompatActivity {

    RecyclerView rvProductList;
    ProductAdapter adapter;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference().child("Product");
    ArrayList<Product> productList;
    private TextView tvAddNewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_master);

        setUpActionBar();

        rvProductList = findViewById(R.id.rv_product_list);
        rvProductList.setLayoutManager(new LinearLayoutManager(this));
        rvProductList.setHasFixedSize(true);
        productList = new ArrayList<>();
        adapter = new ProductAdapter(this, productList);
        rvProductList.setAdapter(adapter);
        final ProgressDialog pd = ProgressDialog.show(ProductMasterActivity.this, "", "Please wait ..", true);
        pd.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String id = (String) dataSnapshot.child("id").getValue();
                    String description = (String) dataSnapshot.child("description").getValue();
                    String gST = dataSnapshot.child("gST").getValue().toString();
                    String particular = (String)dataSnapshot.child("particular").getValue();
                    String price = dataSnapshot.child("price").getValue().toString();
                    String unit = (String) dataSnapshot.child("unit").getValue();
                    Product product = new Product(id,particular,description,unit,price,gST);
                    productList.add(product);

                }
                pd.dismiss();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        tvAddNewProduct = findViewById(R.id.tv_add_new_product);
        tvAddNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductMasterActivity.this, NewProductActivity.class);
                startActivity(i);
            }

        });
    }

    private void setUpActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_product_master_activity);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_vector_back_arrow_white);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}