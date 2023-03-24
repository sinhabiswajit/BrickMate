package com.indigi.brickmate.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.indigi.brickmate.R;

import kotlin.Suppress;

public class BaseActivity extends AppCompatActivity {

    private Boolean doubleBackToExitPressedOnce = false;



    public void doubleBackToExit(){
        if (doubleBackToExitPressedOnce){
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.click_back_again_to_exit), Toast.LENGTH_LONG).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}