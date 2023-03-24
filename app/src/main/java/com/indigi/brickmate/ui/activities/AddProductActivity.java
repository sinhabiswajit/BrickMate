package com.indigi.brickmate.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddProductActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    TextView add_btn;
    private LinearLayout container;
    private Button addMoreButton;
    private Button submitButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference testRef;
    private List<String> value1List;
    private List<String> value2List;
    //    private List<Integer> selectedSpinnerList;
    ProgressDialog progressDialog;
    Intent intent;
    String getMobNumber;
    DatabaseReference databaseReference, myRef, productRef;
    ArrayList<String> spinnerDataList;
    ArrayAdapter<String> adapter;
    Spinner spinner;
    EditText productName,
            price,
            gst_rate,
            qnt,
            total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);
        container = findViewById(R.id.container);
        intent = getIntent();
        getMobNumber = intent.getStringExtra("mobNumber");
        addMoreButton = findViewById(R.id.add_more_button);
        submitButton = findViewById(R.id.submit_button);
        spinner = findViewById(R.id.spinner);
        value1List = new ArrayList<>();
        value2List = new ArrayList<>();
        spinnerDataList = new ArrayList<>();
        productName = findViewById(R.id.productName);
        price = findViewById(R.id.price);
        gst_rate = findViewById(R.id.gst_rate);
        qnt = findViewById(R.id.qnt);
        total = findViewById(R.id.total);
        price.setEnabled(false);
        gst_rate.setEnabled(false);
        total.setEnabled(false);
//        selectedSpinnerList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        testRef = firebaseDatabase.getReference("Customer").child(getMobNumber).child("Orders");

        progressDialog = new ProgressDialog(AddProductActivity.this);
        progressDialog.setTitle("Saving Data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        getProductDetailsFromFirebase(spinner, productName, price, gst_rate, qnt, total);

        addMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View newRow = inflater.inflate(R.layout.new_row, null);
                container.addView(newRow);

                final Spinner spinner = newRow.findViewById(R.id.spinner);
                final EditText price = newRow.findViewById(R.id.price);
                final EditText productName = newRow.findViewById(R.id.productName);
                final EditText gstRate = newRow.findViewById(R.id.gst_rate);
                final EditText qnt = newRow.findViewById(R.id.qnt);
                final EditText total = newRow.findViewById(R.id.total);

                productName.setEnabled(false);
                price.setEnabled(false);
                gstRate.setEnabled(false);
                total.setEnabled(false);


                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                rootRef.child("Product").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final List<String> products = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String particular = snapshot.child("particular").getValue(String.class);
                            products.add(particular);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddProductActivity.this, android.R.layout.simple_spinner_item, products);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedProduct = parent.getItemAtPosition(position).toString();
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        rootRef.child("Product").orderByChild("particular").equalTo(selectedProduct).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String productNameText = snapshot.child("particular").getValue(String.class);
                                    int productPrice = snapshot.child("price").getValue(Integer.class);
                                    int gstPercentage = snapshot.child("gST").getValue(Integer.class);

                                    productName.setText(productNameText);
                                    price.setText(String.valueOf(productPrice));
                                    gstRate.setText(String.valueOf(gstPercentage));
                                    qnt.setText("");
                                    total.setText("");

                                    qnt.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            if (!TextUtils.isEmpty(s)) {
                                                int q = Integer.parseInt(s.toString());
                                                double g = Double.parseDouble(gstRate.getText().toString());
                                                double pr = Double.parseDouble(price.getText().toString());
                                                double t = (q * pr) + ((q * pr) * (g / 100));
                                                total.setText(String.valueOf(t));
                                            }
                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle error
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Handle nothing selected
                    }
                });


                Button removeButton = newRow.findViewById(R.id.remove_button);
                removeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        container.removeView(newRow);
                    }
                });
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEmpty = false;
                for (int i = 0; i < container.getChildCount(); i++) {
                    View inflatedLayout = container.getChildAt(i);
                    EditText productName = inflatedLayout.findViewById(R.id.productName);
                    EditText price = inflatedLayout.findViewById(R.id.price);
                    EditText gst_rate = inflatedLayout.findViewById(R.id.gst_rate);
                    EditText qnt = inflatedLayout.findViewById(R.id.qnt);
                    EditText total = inflatedLayout.findViewById(R.id.total);
                    if (productName.getText().toString().isEmpty() ||
                            price.getText().toString().isEmpty() ||
                            gst_rate.getText().toString().isEmpty() ||
                            qnt.getText().toString().isEmpty() ||
                            total.getText().toString().isEmpty()) {
                        isEmpty = true;
                        break;
                    }
                }
                if (isEmpty) {
                    Toast.makeText(AddProductActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else {

                    progressDialog.show();

                    for (int i = 0; i < container.getChildCount(); i++) {
                        View inflatedLayout = container.getChildAt(i);
                        EditText productName = inflatedLayout.findViewById(R.id.productName);

                        EditText price = inflatedLayout.findViewById(R.id.price);
                        EditText gst_rate = inflatedLayout.findViewById(R.id.gst_rate);
                        EditText qnt = inflatedLayout.findViewById(R.id.qnt);
                        EditText total = inflatedLayout.findViewById(R.id.total);
                        String productNameValue = productName.getText().toString();
                        String priceValue = price.getText().toString();
                        String gst_rateValue = gst_rate.getText().toString();
                        String qntValue = qnt.getText().toString();
                        String totalValue = total.getText().toString();
                        //save values to firebase with unique key
                        String key = testRef.push().getKey();
//                        testRef.child(key).child("spinner").setValue(spinnerValue);
                        testRef.child(key).child("productName").setValue(productNameValue);
                        testRef.child(key).child("price").setValue(priceValue);
                        testRef.child(key).child("gst_rate").setValue(gst_rateValue);
                        testRef.child(key).child("qnt").setValue(qntValue);
                        testRef.child(key).child("total").setValue(totalValue);
                    }
                    progressDialog.dismiss();
                    new SweetAlertDialog(AddProductActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Success")
                            .setContentText("Data saved successfully!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .show();

                }
            }
        });
//        spinnerData();
    }

    private void getProductDetailsFromFirebase(final Spinner spinner, final EditText productName, final EditText price,
                                               final EditText gstRate, final EditText qnt, final EditText total) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("Product");
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> productList = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    productList.add(ds.child("particular").getValue(String.class));
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, productList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedProduct = parent.getItemAtPosition(position).toString();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (selectedProduct.equals(ds.child("particular").getValue(String.class))) {
                                int p = ds.child("price").getValue(Integer.class);
                                int gst = ds.child("gST").getValue(Integer.class);

                                productName.setText(selectedProduct);
                                price.setText(String.valueOf(p));
                                gstRate.setText(String.valueOf(gst));
                                qnt.setText("");
                                total.setText("");

                                qnt.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        if (!TextUtils.isEmpty(s)) {
                                            int q = Integer.parseInt(s.toString());
                                            double g = Double.parseDouble(gstRate.getText().toString());
                                            double pr = Double.parseDouble(price.getText().toString());
                                            double t = (q * pr) + ((q * pr) * (g / 100));
                                            total.setText(String.valueOf(t));
                                        }
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}