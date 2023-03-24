package com.indigi.brickmate.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.indigi.brickmate.R;

public class DashboardActivity extends BaseActivity implements View.OnClickListener {
    CardView customer;
    CardView product;
    CardView order;
    CardView order_list;
    CardView payment;
    CardView enquiry;

    ImageView Logout;

    AlertDialog.Builder builder;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar dashboardToolbar = findViewById(R.id.toolbar_dashboard_activity);
        setSupportActionBar(dashboardToolbar);

        customer = findViewById(R.id.card_customer);
        product = findViewById(R.id.card_products);
        order = findViewById(R.id.card_orders);
        order_list = findViewById(R.id.card_order_list);
        payment = findViewById(R.id.card_payment);
        enquiry = findViewById(R.id.card_enquiry);
        TextView footerLink = findViewById(R.id.tv_footer);
        builder = new AlertDialog.Builder(this);

        mAuth = FirebaseAuth.getInstance();

        customer.setOnClickListener(this);
        product.setOnClickListener(this);
        order.setOnClickListener(this);
        order_list.setOnClickListener(this);
        payment.setOnClickListener(this);
        enquiry.setOnClickListener(this);
        footerLink.setOnClickListener(this);

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {

            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        builder.setTitle("Alert!")
                .setMessage("Do you want to Logout")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                        Intent intent = new Intent(DashboardActivity.this,
                                LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_customer:
                Intent customerIntent = new Intent(DashboardActivity.this, CustomerActivity.class);
                startActivity(customerIntent);
                break;
            case R.id.card_products:
                Intent productIntent = new Intent(DashboardActivity.this, ProductMasterActivity.class);
                startActivity(productIntent);
                break;
            case R.id.card_orders:
                Intent orderIntent = new Intent(DashboardActivity.this, TempActivity.class);
                startActivity(orderIntent);
                break;
            case R.id.card_order_list:
                Intent orderListIntent = new Intent(DashboardActivity.this, OrderListActivity.class);
                startActivity(orderListIntent);
                break;
            case R.id.card_payment:
                Intent paymentIntent = new Intent(DashboardActivity.this, PaymentDetailsActivity.class);
                startActivity(paymentIntent);
                break;
            case R.id.card_enquiry:
                Intent enquiryIntent = new Intent(DashboardActivity.this, EnquiryActivity.class);
                startActivity(enquiryIntent);
                break;
            case R.id.tv_footer:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("http://indigiconsulting.com/"));
                startActivity(browserIntent);
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        doubleBackToExit();
    }
}