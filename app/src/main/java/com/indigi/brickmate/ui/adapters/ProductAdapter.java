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
import com.indigi.brickmate.R;
import com.indigi.brickmate.model.Product;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    Context context;


    ArrayList<Product> list;

    public ProductAdapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Product productModelclass = list.get(position);
        holder.Particular.setText(productModelclass.getParticular());
        holder.Description.setText(productModelclass.getDescription());
        holder.Unit.setText(productModelclass.getUnit());
        holder.Price.setText(productModelclass.getPrice());
        holder.Gst_number.setText(productModelclass.getgST());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.Particular.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be Undo?");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference("Product").child(productModelclass.getId())
                                .removeValue();

                        Toast.makeText(holder.Particular.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.Particular.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.itemView.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.update_product))
                        .setExpanded(true,1500).create();

                View view1 = dialogPlus.getHolderView();

                EditText PARTICULAR = view1.findViewById(R.id.etParticular);
                EditText DESCRIPTION = view1.findViewById(R.id.etDescription);
                EditText UNIT = view1.findViewById(R.id.etUnit);
                EditText SELL_PRICE = view1.findViewById(R.id.etSellprice);
                EditText GST_RATE = view1.findViewById(R.id.etGst);
                TextView Update = view1.findViewById(R.id.updatee);

                PARTICULAR.setText(productModelclass.getParticular());
                DESCRIPTION.setText(productModelclass.getDescription());
                UNIT.setText(productModelclass.getUnit());
                SELL_PRICE.setText(productModelclass.getPrice());
                GST_RATE.setText(productModelclass.getgST());
                dialogPlus.show();


                Update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



//                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Customer").child(customerModel.getPhone_number());
                        Map<String,Object> map = new HashMap<>();
                        map.put("particular",PARTICULAR.getText().toString());
                        map.put("description",DESCRIPTION.getText().toString());
                        map.put("unit",UNIT.getText().toString());
                        map.put("price",SELL_PRICE.getText().toString());
                        map.put("gST",GST_RATE.getText().toString());

                        FirebaseDatabase.getInstance().getReference("Product").child(productModelclass.getId())
                                .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){

                                            Toast.makeText(holder.Particular.getContext(), "Data updated successfully", Toast.LENGTH_SHORT).show();

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
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Sl_number,Particular,Description,Unit,Price,Gst_number,btnUpdate,btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);



            Particular = itemView.findViewById(R.id.tv_item_product_particular);
            Description = itemView.findViewById(R.id.tv_item_product_description);
            Unit = itemView.findViewById(R.id.tv_item_product_unit);
            Price = itemView.findViewById(R.id.tv_item_product_sell_price);
            Gst_number = itemView.findViewById(R.id.tv_item_product_gst_rate);
            btnUpdate = itemView.findViewById(R.id.tv_item_product_update);
            btnDelete = itemView.findViewById(R.id.tv_item_product_delete);


        }


    }
}
