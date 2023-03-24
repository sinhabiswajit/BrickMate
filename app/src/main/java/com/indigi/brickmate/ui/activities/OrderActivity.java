package com.indigi.brickmate.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.indigi.brickmate.model.Order;
import com.indigi.brickmate.R;

import java.util.ArrayList;
import java.util.UUID;

public class OrderActivity extends AppCompatActivity {

    private ImageView back;
    private EditText Search_Customer ,Customer_name, Customer_number,Customer_GST,Customer_Address;

    private EditText Product_name,Product_gst,Product_uom,Product_price;
    private EditText Quantity, Total_Price, Delivery_Address;

    private RelativeLayout ViewOrder,AddBtn;



    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,myRef,productRef;

    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerDataList;
    private String ValueDatabase;
    private String refinedData;
    TextView Search;
    TextView SearchBtn;
    ProgressDialog Dialog;
    Spinner spinner;




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        back = findViewById(R.id.back);
        Customer_name = findViewById(R.id.et_customer_name);
        Customer_number = findViewById(R.id.p_number);
        Customer_GST = findViewById(R.id.GST);
        Customer_Address = findViewById(R.id.add);
        Search_Customer = findViewById(R.id.searchEditText);
        SearchBtn = findViewById(R.id.search);
        spinner = findViewById(R.id.productspinner);

        Product_name = findViewById(R.id.productName);
        Product_gst = findViewById(R.id.gst_rate);
        Product_uom = findViewById(R.id.uom);
        Product_price = findViewById(R.id.price);

        Quantity = findViewById(R.id.qnt);
        Total_Price = findViewById(R.id.total);
        Delivery_Address = findViewById(R.id.d_address);

        ViewOrder = findViewById(R.id.save_btn);
        AddBtn = findViewById(R.id.addbtn);

        firebaseDatabase = FirebaseDatabase.getInstance();
        productRef = firebaseDatabase.getReference("OrderProduct");


        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String C_name = Customer_name.getText().toString();
                String C_number = Customer_number.getText().toString();
                String gst_number = Customer_GST.getText().toString();
                String C_address = Customer_Address.getText().toString();
                String P_name = Product_name.getText().toString();
                String P_uom = Product_uom.getText().toString();
                String P_price = Product_price.getText().toString();
                String Gst_rate = Product_gst.getText().toString();
                String P_quantity = Quantity.getText().toString();
                String P_total = Total_Price.getText().toString();
                String Delivery_address = Delivery_Address.getText().toString();


                if (TextUtils.isEmpty(C_name) && TextUtils.isEmpty(C_number) && TextUtils.isEmpty(gst_number)
                        && TextUtils.isEmpty(C_address) && TextUtils.isEmpty(P_name) &&
                        TextUtils.isEmpty(P_uom) && TextUtils.isEmpty(P_price) && TextUtils.isEmpty(Gst_rate)
                        && TextUtils.isEmpty(P_quantity) && TextUtils.isEmpty(P_total) && TextUtils.isEmpty(Delivery_address)) {
                    Toast.makeText(OrderActivity.this, "Please fill up the mandatory fields", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    String id = String.valueOf(UUID.randomUUID());

                    Order orderModel = new Order(id,C_name,C_number,gst_number,C_address,P_name,P_uom,P_price,
                            Gst_rate,P_quantity,P_total,Delivery_address);

                    productRef = firebaseDatabase.getReference("OrderProduct");

                    productRef.child(id).setValue(orderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(OrderActivity.this, AddMoreProductActivity.class);
//                                intent.putExtra("name", C_name);
                                startActivity(intent);
                                finish();
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
        ViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//        SaveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String C_name = Customer_name.getText().toString();
//                String C_number = Customer_number.getText().toString();
//                String gst_number = Customer_GST.getText().toString();
//                String C_address = Customer_Address.getText().toString();
//                String P_name = Product_name.getText().toString();
//                String P_uom = Product_uom.getText().toString();
//                String P_price = Product_price.getText().toString();
//                String Gst_rate = Product_gst.getText().toString();
//                String P_quantity = Quantity.getText().toString();
//                String P_total = Total_Price.getText().toString();
//                String Delivery_address = Delivery_Address.getText().toString();
//
//
//                if (TextUtils.isEmpty(C_name) && TextUtils.isEmpty(C_number) && TextUtils.isEmpty(gst_number)
//                        && TextUtils.isEmpty(C_address) && TextUtils.isEmpty(P_name) &&
//                        TextUtils.isEmpty(P_uom) && TextUtils.isEmpty(P_price) && TextUtils.isEmpty(Gst_rate)
//                        && TextUtils.isEmpty(P_quantity) && TextUtils.isEmpty(P_total) && TextUtils.isEmpty(Delivery_address)) {
//                    Toast.makeText(OrderActivity.this, "Please fill up the mandatory fields", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                else {
//                    String id = String.valueOf(UUID.randomUUID());
//
//                    OrderModel orderModel = new OrderModel(id,C_name,C_number,gst_number,C_address,P_name,P_uom,P_price,
//                            Gst_rate,P_quantity,P_total,Delivery_address);
//
//                    productRef = firebaseDatabase.getReference("OrderProduct");
////
//                    productRef.child(id).setValue(orderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()){
//                                startActivity(new Intent(OrderActivity.this, DashboardActivity.class));
//                            }
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            e.printStackTrace();
//                        }
//                    });
//
//                }
//            }
//        });

        myRef = FirebaseDatabase.getInstance().getReference("Customer");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        spinnerDataList = new ArrayList<>();
        spinner.setAdapter(adapter);
        spinnerData();

        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = Search_Customer.getText().toString();
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference dref = readRef.child("Customer");
                Query query = dref.orderByChild("phone_number").equalTo(search);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                Customer_name.setText((ds.child("customer_name").getValue().toString()));
                                Customer_number.setText((ds.child("phone_number").getValue().toString()));
                                Customer_Address.setText((ds.child("address").getValue().toString()));
                                Customer_GST.setText((ds.child("gst").getValue().toString()));
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No Source to Display",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//                startActivity(new Intent(OrderActivity.this, DashBoardActivity.class));
//            }
//        });

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
    }

    public void spinnerData(){
        databaseReference.child("Product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                spinnerDataList.clear();
                for (DataSnapshot item:snapshot.getChildren()){
                    spinnerDataList.add(item.child("particular").getValue(String.class));

                }
                adapter = new ArrayAdapter<String>(OrderActivity.this,
                        android.R.layout.simple_dropdown_item_1line,spinnerDataList);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }






}
