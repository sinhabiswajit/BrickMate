package com.indigi.brickmate.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.indigi.brickmate.R;
import com.indigi.brickmate.addmoreModel;

import java.util.ArrayList;
import java.util.List;

public class TempActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    TextView buttonAdd, buttonRemove;
    EditText P_Name, P_Uom, P_Price, GST_Rate, P_Quantity, P_TotalPrice, etFirstName;
    EditText productName, productUom, productPrice, productGST, productQuantity, productTotal;
    EditText searchEditText, Customer_name, Customer_number, Customer_GST, Customer_Address, Delivery_Address;
    Button btnNext;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, myRef, productRef;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerDataList;
    ArrayAdapter<String> adapter1;
    List<EditText> dynamiclist = new ArrayList<>();
    //    ArrayList<String> dynamiclist = new ArrayList<String>();
    ArrayList<String> spinnerDataList1;

    TextView Search;
    TextView SearchBtn;
    ProgressDialog Dialog;
    Spinner spinner, spinner1;
    String[] info = new String[10];
    List views = new ArrayList();
    EditText p_number;
    EditText GST;
    EditText add;


    //    private static final String productname = "product_name";
//    final ArrayList<String> addmoreModelArrayList = new ArrayList<String>();
    ArrayList<addmoreModel> addmoreModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        linearLayout = findViewById(R.id.layout_list);
        buttonAdd = findViewById(R.id.add_btn);
        Delivery_Address = findViewById(R.id.d_address);
        Customer_name = findViewById(R.id.et_customer_name);
        Customer_number = findViewById(R.id.p_number);
        Customer_GST = findViewById(R.id.GST);
        Customer_Address = findViewById(R.id.add);
        searchEditText = findViewById(R.id.searchEditText);
        etFirstName = findViewById(R.id.et_customer_name);


//        View childView = getLayoutInflater().inflate(R.layout.addproduct, null, false);
//        linearLayout.addView(childView);


        productName = findViewById(R.id.productName);
        productUom = findViewById(R.id.uom);
        productGST = findViewById(R.id.gst_rate);
        productQuantity = findViewById(R.id.qnt);
        productPrice = findViewById(R.id.price);
        productTotal = findViewById(R.id.total);
        SearchBtn = findViewById(R.id.search);
        btnNext = findViewById(R.id.btnNext);
        spinner = findViewById(R.id.spinner);

        p_number = findViewById(R.id.p_number);
        GST = findViewById(R.id.GST);
        add = findViewById(R.id.add);

        myRef = FirebaseDatabase.getInstance().getReference("Customer");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        spinnerDataList = new ArrayList<>();
//        spinner.setAdapter(adapter);
        spinnerData();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                View childView2 = getLayoutInflater().inflate(R.layout.addproduct, null, false);
                // Assume you want to add your view in linear layout
                linearLayout.addView(childView2);


            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchEditText.getText().toString().trim().length() < 10) {
                    Toast.makeText(TempActivity.this, "Please Provide a 10 digit number", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(TempActivity.this, AddProductActivity.class);
                intent.putExtra("mobNumber", p_number.getText().toString().trim());
                startActivity(intent);
            }
        });


        final ProgressDialog progressDialog = new ProgressDialog(TempActivity.this);
        progressDialog.setMessage("Loading...");
        DatabaseReference customerRef = FirebaseDatabase.getInstance().getReference().child("Customer");
        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (searchEditText.getText().toString().trim().length() < 10) {
                    Toast.makeText(TempActivity.this, "Please Provide a 10 digit number", Toast.LENGTH_LONG).show();
                    return;
                }

                progressDialog.show();
                String mobileNumber = searchEditText.getText().toString().trim();
                Log.e("Number", "" + mobileNumber);
                customerRef.child(mobileNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String address = dataSnapshot.child("address").getValue().toString();
                            String customerName = dataSnapshot.child("customer_name").getValue().toString();
                            String deliveryAddress = dataSnapshot.child("delivery_address").getValue().toString();
                            String email = dataSnapshot.child("email").getValue().toString();
                            String gst = dataSnapshot.child("gst").getValue().toString();
                            String id = dataSnapshot.child("id").getValue().toString();
                            String phoneNumber = dataSnapshot.child("phone_number").getValue().toString();

                            etFirstName.setText(customerName);
                            p_number.setText(phoneNumber);
                            GST.setText(gst);
                            add.setText(address);

                            Log.e("Data", "Address: " + address + " Customer Name: " + customerName +
                                    " Delivery Address: " + deliveryAddress + " Email: " + email +
                                    " GST: " + gst + " ID: " + id + " Phone Number: " + phoneNumber);

                            progressDialog.dismiss();
                        } else {
                            progressDialog.dismiss();
//                            Log.e("Data", "No data found for this mobile number");
                            Toast.makeText(TempActivity.this, "No data found for this mobile number", Toast.LENGTH_SHORT).show();
                            etFirstName.setText("");
                            p_number.setText("");
                            GST.setText("");
                            add.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("Data", "Error: " + databaseError.getMessage());
                        Toast.makeText(TempActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });


    }


    private void spinnerData() {
        databaseReference.child("Product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                spinnerDataList.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    spinnerDataList.add(item.child("particular").getValue(String.class));
//                    spinnerDataList1.add(item.child("particular").getValue(String.class));

                }

                adapter = new ArrayAdapter<String>(TempActivity.this, android.R.layout.simple_dropdown_item_1line, spinnerDataList);
//                spinner1.setAdapter(adapter1);
//                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
