package com.indigi.brickmate.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.indigi.brickmate.R;
import com.indigi.brickmate.model.Product;

import java.util.UUID;

public class NewProductActivity extends AppCompatActivity {


    ImageView back;
    RelativeLayout Save;
    EditText etProductName, etProductDescription, etProductUOM, etSellPrice, etGstRate;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        back = findViewById(R.id.back);
        Save = findViewById(R.id.product_add);
        etProductName = findViewById(R.id.p_name);
        etProductDescription = findViewById(R.id.p_description);
        etProductUOM = findViewById(R.id.p_uom);
        etSellPrice = findViewById(R.id.s_price);
        etGstRate = findViewById(R.id.g_rate);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Product");
        Save.setOnClickListener(view ->
                SaveData()

        );


    }

    private void SaveData() {

        String Product_name = etProductName.getText().toString();
        String Product_Description = etProductDescription.getText().toString();
        String Product_UOM = etProductUOM.getText().toString();
        String Product_SellPrice = etSellPrice.getText().toString();
        String Product_GSTRate = etGstRate.getText().toString();

        if (TextUtils.isEmpty(Product_name) && TextUtils.isEmpty(Product_Description) && TextUtils.isEmpty(Product_UOM)
                && TextUtils.isEmpty(Product_SellPrice) && TextUtils.isEmpty(Product_GSTRate)  ) {

            Toast.makeText(NewProductActivity.this, "Please add some data.", Toast.LENGTH_SHORT).show();
        } else {

            String id = String.valueOf(UUID.randomUUID());
            Product productModelNew = new Product(id,Product_name,  Product_Description, Product_UOM,
                    Product_SellPrice, Product_GSTRate) ;

            databaseReference = firebaseDatabase.getReference("Product");
            databaseReference.child(id).setValue(productModelNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        onBackPressed();
                        //startActivity(new Intent(NewProductActivity.this, ProductMasterActivity.class));
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });



//            addDatatoFirebase(name, Email, phone_number,Delivery_add,Customer_add,GST);
        }

    }
}