package com.indigi.brickmate.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.indigi.brickmate.ui.adapters.OrderListAdapter;
import com.indigi.brickmate.R;
import com.indigi.brickmate.model.OrderList;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    OrderListAdapter adapter;
    ArrayList<OrderList> list;
    ProgressDialog pd;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        recyclerView = findViewById(R.id.recyclerView);
        database = FirebaseDatabase.getInstance().getReference("OrderProduct");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new OrderListAdapter(this,list);
        recyclerView.setAdapter(adapter);



        pd = ProgressDialog.show(OrderListActivity.this, "", "Please wait ..", true);
        pd.show();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    OrderList orderListModelNew = dataSnapshot.getValue(OrderList.class);
                    list.add(orderListModelNew);

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