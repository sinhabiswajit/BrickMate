package com.indigi.brickmate.ui.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.indigi.brickmate.model.Customer;
import com.indigi.brickmate.R;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    ArrayList<Customer> customerList;

    public CustomerAdapter(Context context, ArrayList<Customer> customerList) {
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        Customer customer = customerList.get(position);
        holder.name.setText(customer.getCustomer_name());
        holder.phone.setText(customer.getPhone_number());
        holder.email.setText(customer.getEmail());
        holder.address.setText(customer.getAddress());
        holder.gstNumber.setText(customer.getGst());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be Undo?");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference("Customer").child(customer.getPhone_number())
                                .removeValue();

                        Toast.makeText(holder.name.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    }
                });


                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.itemView.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1500).create();

//                dialogPlus.show();

                View view1 = dialogPlus.getHolderView();

                EditText CUSTOMER_NAME = view1.findViewById(R.id.etCustomerName);
                EditText CUSTOMER_PHONE = view1.findViewById(R.id.etCustomerNumber);
                EditText CUSTOMER_MAIL = view1.findViewById(R.id.etCustomerEmail);
                EditText CUSTOMER_ADD = view1.findViewById(R.id.etCustomerAddress);
                EditText CUSTOMER_GST = view1.findViewById(R.id.etCustomerGst);
                TextView Update = view1.findViewById(R.id.updatee);


                CUSTOMER_NAME.setText(customer.getCustomer_name());
                CUSTOMER_PHONE.setText(customer.getPhone_number());
                CUSTOMER_MAIL.setText(customer.getEmail());
                CUSTOMER_ADD.setText(customer.getAddress());
                CUSTOMER_GST.setText(customer.getGst());

                dialogPlus.show();

                Update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



//                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Customer").child(customerModel.getPhone_number());
                        Map<String,Object> map = new HashMap<>();
                        map.put("customer_name",CUSTOMER_NAME.getText().toString());
                        map.put("phone_number",CUSTOMER_PHONE.getText().toString());
                        map.put("email",CUSTOMER_MAIL.getText().toString());
                        map.put("address",CUSTOMER_ADD.getText().toString());
                        map.put("gst",CUSTOMER_GST.getText().toString());

                        FirebaseDatabase.getInstance().getReference("Customer").child(customer.getPhone_number())
                                .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){

                                            Toast.makeText(holder.name.getContext(), "Data updated successfully", Toast.LENGTH_SHORT).show();

                                        }
                                        notifyDataSetChanged();
                                    }
                                });

//                        databaseReference.updateChildren(map);
//                        Toast.makeText(holder.Customer_name.getContext(), "Data updated successfully", Toast.LENGTH_SHORT).show();


                        dialogPlus.dismiss();
                    }


                });



            }
        });
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, phone, email, address, gstNumber,btnUpdate,btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_item_customer_name);
            phone = itemView.findViewById(R.id.tv_item_customer_mobile_number);
            email = itemView.findViewById(R.id.tv_item_customer_email);
            address = itemView.findViewById(R.id.tv_item_customer_address);
            gstNumber = itemView.findViewById(R.id.tv_item_customer_gst_number);
            btnUpdate = itemView.findViewById(R.id.tv_item_customer_update);
            btnDelete = itemView.findViewById(R.id.tv_item_customer_delete);


        }

    }

}