package com.indigi.brickmate.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.indigi.brickmate.R;
import com.indigi.brickmate.model.Payment;
import com.indigi.brickmate.ui.activities.UpdatePaymentActivity;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {


    Context context;

    ArrayList<Payment> list;

    public PaymentAdapter(Context context, ArrayList<Payment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.payment_item,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Payment payment = list.get(position);
        holder.Customer_name.setText(payment.getCustomer_name());
        holder.Customer_Number.setText(payment.getPhone_number());
        holder.Total_Bill.setText(payment.getTotal_Payment());
        holder.Receive_Payment.setText(payment.getReceive_Payment());
        holder.Outstanding_payment.setText(payment.getOutstanding_payment());

        holder.Update_Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdatePaymentActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Customer_name, Customer_Number, Total_Bill, Receive_Payment, Outstanding_payment;

        RelativeLayout Update_Payment;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Customer_name = itemView.findViewById(R.id.c_name);
            Customer_Number = itemView.findViewById(R.id.p_number);
            Total_Bill = itemView.findViewById(R.id.bill_payment);
            Receive_Payment = itemView.findViewById(R.id.total_paid);
            Outstanding_payment = itemView.findViewById(R.id.out_payment);
            Update_Payment = itemView.findViewById(R.id.update_btn);
        }
    }
}
