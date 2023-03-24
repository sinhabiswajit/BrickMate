package com.indigi.brickmate.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.indigi.brickmate.model.Payment;
import com.indigi.brickmate.R;
import com.indigi.brickmate.ui.adapters.PaymentAdapter;

import java.util.ArrayList;

public class PaymentDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    PaymentAdapter adapter;
    ArrayList<Payment> list;
    ProgressDialog pd;

    Button New_Payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        recyclerView = findViewById(R.id.recyclerView);
        database = FirebaseDatabase.getInstance().getReference("Payment");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new PaymentAdapter(this,list);
        recyclerView.setAdapter(adapter);

        pd = ProgressDialog.show(PaymentDetailsActivity.this, "", "Please wait ..", true);
        pd.show();

        New_Payment = findViewById(R.id.new_payment);
        New_Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PaymentDetailsActivity.this, PaymentActivity.class);
                startActivity(i);
            }

        });
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Payment payment = dataSnapshot.getValue(Payment.class);
                    list.add(payment);

                }
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }
}