package com.indigi.brickmate.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.indigi.brickmate.R;


public class EnquiryActivity extends AppCompatActivity {


    public void onBackPressed() {

        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);
    }


}