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
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.indigi.brickmate.model.Customer;
import com.indigi.brickmate.R;
import com.indigi.brickmate.ui.adapters.CustomerAdapter;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {

    RecyclerView rvCustomerList;
    CustomerAdapter adapter;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference().child("Customer");
    ArrayList<Customer> customerList;
    TextView tvAddNewCustomer;
    ProgressDialog progressDialog;

//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        setUpActionBar();

        rvCustomerList = findViewById(R.id.rv_customer_list);
        rvCustomerList.setLayoutManager(new LinearLayoutManager(this));
        rvCustomerList.setHasFixedSize(true);
        customerList = new ArrayList<>();
        adapter = new CustomerAdapter(this, customerList);
        rvCustomerList.setAdapter(adapter);


        progressDialog = ProgressDialog.show(CustomerActivity.this, "", "Please wait ..", true);
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                customerList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Customer customerModel = dataSnapshot.getValue(Customer.class);
                    customerList.add(customerModel);
                }
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        tvAddNewCustomer = findViewById(R.id.tv_add_new_customer);
        tvAddNewCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CustomerActivity.this, NewCustomerActivity.class);
                startActivity(i);
            }
        });
    }

    private void setUpActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_customer_activity);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }

    }
}