package com.indigi.brickmate.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.indigi.brickmate.R;
import com.indigi.brickmate.addmoreModel;

import java.util.ArrayList;
import java.util.UUID;

public class AddMoreProductActivity extends AppCompatActivity {

    TextView C_Name;
    EditText Product_name,Product_gst,Product_uom,Product_price;
    EditText Quantity, Total_Price, Delivery_Address;
    RelativeLayout SaveBtn,AddBtn;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerDataList;
    ArrayList<addmoreModel> list;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,myRef,productRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addproduct);
        Product_name = findViewById(R.id.productName);
        Product_gst = findViewById(R.id.gst_rate);
        Product_uom = findViewById(R.id.uom);
        Product_price = findViewById(R.id.price);
        Quantity = findViewById(R.id.qnt);
        Total_Price = findViewById(R.id.total);
        Delivery_Address = findViewById(R.id.d_address);
        SaveBtn = findViewById(R.id.save_btn);
        AddBtn = findViewById(R.id.addmore);
        C_Name = findViewById(R.id.etCustomerName);
        spinner = findViewById(R.id.productspinner);
        firebaseDatabase = FirebaseDatabase.getInstance();
//        productRef = firebaseDatabase.getReference("OrderProduct");

        myRef = FirebaseDatabase.getInstance().getReference("Customer");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        spinnerDataList = new ArrayList<>();
        spinner.setAdapter(adapter);
        spinnerData();
        Quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    if (!Product_price.getText().toString().equalsIgnoreCase("")) {
                        float a = Float.parseFloat(Product_price.getText().toString());
                        float b = Float.parseFloat(Quantity.getText().toString());
                        float c = a * b;
                        Total_Price.setText(String.valueOf(c));
                    }
                }
                if (charSequence.length() == 0) {
                    Total_Price.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String Customer_name = C_Name.getText().toString();
                String P_name = Product_name.getText().toString();
                String P_uom = Product_uom.getText().toString();
                String P_price = Product_price.getText().toString();
                String Gst_rate = Product_gst.getText().toString();
                String P_quantity = Quantity.getText().toString();
                String P_total = Total_Price.getText().toString();
                String Delivery_address = Delivery_Address.getText().toString();
                if (TextUtils.isEmpty(P_name) && TextUtils.isEmpty(P_uom) && TextUtils.isEmpty(P_price) && TextUtils.isEmpty(Gst_rate)
                        && TextUtils.isEmpty(P_quantity) && TextUtils.isEmpty(P_total) && TextUtils.isEmpty(Delivery_address)) {
                    Toast.makeText(AddMoreProductActivity.this, "Please fill up the mandatory fields", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    String id = String.valueOf(UUID.randomUUID());

                    addmoreModel addmoreModel = new addmoreModel(P_name,P_uom,P_price,
                            Gst_rate,P_quantity,P_total);

                    productRef = firebaseDatabase.getReference("OrderProduct");

                    productRef.child(id).setValue(addmoreModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(AddMoreProductActivity.this, AddMoreProductActivity.class));
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String state = spinner.getSelectedItem().toString();
                DatabaseReference sref = databaseReference.child("Product");
                Query query1 = sref.orderByChild("particular").equalTo(state);

                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot ds : snapshot.getChildren()){
                                Product_name.setText((ds.child("particular").getValue().toString()));
                                Product_gst.setText((ds.child("gST").getValue().toString()));
                                Product_uom.setText((ds.child("unit").getValue().toString()));
                                Product_price.setText((ds.child("price").getValue().toString()));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        String data = getIntent().getExtras().getString("name");
//        C_Name.setText(data);

        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String P_name = Product_name.getText().toString();
                String P_uom = Product_uom.getText().toString();
                String P_price = Product_price.getText().toString();
                String Gst_rate = Product_gst.getText().toString();
                String P_quantity = Quantity.getText().toString();
                String P_total = Total_Price.getText().toString();
                String Delivery_address = Delivery_Address.getText().toString();
                if (TextUtils.isEmpty(P_name) && TextUtils.isEmpty(P_uom) && TextUtils.isEmpty(P_price) && TextUtils.isEmpty(Gst_rate)
                        && TextUtils.isEmpty(P_quantity) && TextUtils.isEmpty(P_total) && TextUtils.isEmpty(Delivery_address)) {
                    Toast.makeText(AddMoreProductActivity.this, "Please fill up the mandatory fields", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    String id = String.valueOf(UUID.randomUUID());

                    addmoreModel addmoreModel = new addmoreModel(P_name,P_uom,P_price,
                            Gst_rate,P_quantity,P_total);

                    productRef = firebaseDatabase.getReference("OrderProduct");

                    productRef.child(id).setValue(addmoreModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(AddMoreProductActivity.this, DashboardActivity.class));

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }


    public void spinnerData(){
        databaseReference.child("Product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                spinnerDataList.clear();
                for (DataSnapshot item:snapshot.getChildren()){
                    spinnerDataList.add(item.child("particular").getValue(String.class));

                }
                adapter = new ArrayAdapter<String>(AddMoreProductActivity.this,
                        android.R.layout.simple_dropdown_item_1line,spinnerDataList);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    }


