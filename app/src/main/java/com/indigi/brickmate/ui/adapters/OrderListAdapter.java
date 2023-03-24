package com.indigi.brickmate.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.indigi.brickmate.R;
import com.indigi.brickmate.model.OrderList;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {

    Context context;

    ArrayList<OrderList> list;

    public OrderListAdapter(Context context, ArrayList<OrderList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.orderlist_item,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrderList orderListModelNew = list.get(position);
        holder.Customer_name.setText(orderListModelNew.getCustomer_name());
        holder.Customer_Number.setText(orderListModelNew.getPhone_number());
        holder.Product_name.setText(orderListModelNew.getProduct_name());
        holder.Product_quantity.setText(orderListModelNew.getQuantity());
        holder.Product_price.setText(orderListModelNew.getTotal_price());
        holder.Delivery_Address.setText(orderListModelNew.getDelivery_address());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{


        TextView Customer_name, Customer_Number, Product_name, Product_quantity, Product_price, Delivery_Address;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Customer_name = itemView.findViewById(R.id.c_name);
            Customer_Number = itemView.findViewById(R.id.c_number);
            Product_name = itemView.findViewById(R.id.p_name);
            Product_quantity = itemView.findViewById(R.id.quantity);
            Product_price = itemView.findViewById(R.id.p_price);
            Delivery_Address = itemView.findViewById(R.id.d_add);


        }
    }

}
