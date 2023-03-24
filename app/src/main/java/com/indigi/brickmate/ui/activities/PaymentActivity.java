package com.indigi.brickmate.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.indigi.brickmate.model.Payment;
import com.indigi.brickmate.R;

import java.util.UUID;

public class PaymentActivity extends AppCompatActivity {

    EditText Search_Customer ,Customer_name, Customer_number,Total_Payment,Receive_Payment,Outstanding_Payment;
    TextView SearchBtn;
    RelativeLayout SaveBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,myRef,productRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Search_Customer = findViewById(R.id.searchEditText);
        Customer_name = findViewById(R.id.et_customer_name);
        Customer_number = findViewById(R.id.p_number);
        Total_Payment = findViewById(R.id.total_payment);
        Outstanding_Payment = findViewById(R.id.outstanding_payment);
        Receive_Payment = findViewById(R.id.rcv_payment);


        SearchBtn = findViewById(R.id.search);
        SaveBtn = findViewById(R.id.save_btn);


        firebaseDatabase = FirebaseDatabase.getInstance();
        productRef = firebaseDatabase.getReference("Payment");



        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = Search_Customer.getText().toString();
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference dref = readRef.child("OrderProduct");
                Query query = dref.orderByChild("phone_number").equalTo(search);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                Customer_name.setText((ds.child("customer_name").getValue().toString()));
                                Customer_number.setText((ds.child("phone_number").getValue().toString()));
                                Total_Payment.setText((ds.child("total_price").getValue().toString()));
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


        Receive_Payment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    if (!Total_Payment.getText().toString().equalsIgnoreCase("")) {
                        float a = Float.parseFloat(Total_Payment.getText().toString());
                        float b = Float.parseFloat(Receive_Payment.getText().toString());
                        float c = a - b;
                        Outstanding_Payment.setText(String.valueOf(c));
                    }
                }
                if (charSequence.length() == 0) {
                    Outstanding_Payment.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String C_name = Customer_name.getText().toString();
                String C_number = Customer_number.getText().toString();
                String T_payment = Total_Payment.getText().toString();
                String R_Payment = Receive_Payment.getText().toString();
                String O_Payment = Outstanding_Payment.getText().toString();


                if (TextUtils.isEmpty(C_name) && TextUtils.isEmpty(C_number)
                        && TextUtils.isEmpty(T_payment) && TextUtils.isEmpty(R_Payment) &&
                        TextUtils.isEmpty(O_Payment)) {
                    Toast.makeText(PaymentActivity.this, "Please fill up the mandatory fields", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    String id = String.valueOf(UUID.randomUUID());

                    Payment payment = new Payment(id,C_name,C_number,T_payment,R_Payment,O_Payment);

                    productRef = firebaseDatabase.getReference("Payment");

                    productRef.child(id).setValue(payment).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(PaymentActivity.this, DashboardActivity.class));
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
}