package com.indigi.brickmate.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.indigi.brickmate.model.Customer;
import com.indigi.brickmate.R;

import java.util.UUID;

public class NewCustomerActivity extends AppCompatActivity {
    private EditText etCustomerName, etCustomerEmailID, etCustomerPhone, etCustomerAddress, etCustomerGSTNo;
    private Button btnAddNewCustomer;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog Dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);

        setUpActionBar();

        etCustomerName = findViewById(R.id.et_customer_name);
        etCustomerPhone = findViewById(R.id.et_customer_phone);
        etCustomerEmailID = findViewById(R.id.et_customer_email);
        etCustomerAddress = findViewById(R.id.et_customer_address);
        etCustomerGSTNo = findViewById(R.id.et_customer_gst_number);
        btnAddNewCustomer = findViewById(R.id.btn_add_new_customer);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer");

        btnAddNewCustomer.setOnClickListener(view ->
                SaveData()
        );

    }
    private void SaveData() {
        String name = etCustomerName.getText().toString().trim();
        String phoneNumber = etCustomerPhone.getText().toString().trim();
        String email = etCustomerEmailID.getText().toString().trim();
        String address = etCustomerAddress.getText().toString().trim();
        String gstNumber = etCustomerGSTNo.getText().toString().trim();

        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(phoneNumber) && TextUtils.isEmpty(email)
                && TextUtils.isEmpty(address) && TextUtils.isEmpty(gstNumber)) {

            Toast.makeText(NewCustomerActivity.this, "Please add some data.", Toast.LENGTH_SHORT).show();
        } else {


            String id = String.valueOf(UUID.randomUUID());


            Customer newCustomer = new Customer(id, name, phoneNumber, email, address, gstNumber);

            databaseReference.child(phoneNumber).setValue(newCustomer).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(NewCustomerActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
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
    private void setUpActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_add_new_customer_activity);
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