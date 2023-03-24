package com.indigi.brickmate.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.indigi.brickmate.R;
import com.indigi.brickmate.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding loginBinding;

    FirebaseAuth mAuth;

    public  static final String fileName = "Login";
    public  static final String UserName = "Username";
    public  static final String Password = "password";


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() !=null){
            Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mAuth =  FirebaseAuth.getInstance();
//        if(mAuth != null){
//            Intent i = new Intent(LoginActivity.this,DashboardActivity.class);
//            startActivity(i);
//        }


        loginBinding.btnLogin.setOnClickListener(view -> {
            loginUser();
        });

        SharedPreferences sharedPreferences= getSharedPreferences(fileName, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(UserName))
        {
            Intent i = new Intent(LoginActivity.this,DashboardActivity.class);
            startActivity(i);
            finish();
        }

        loginBinding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        loginBinding.cbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    loginBinding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    loginBinding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        loginBinding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
                builder1.setMessage("Please Contact your Developer")
                        .setCancelable(false)
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }

    private void loginUser() {

        String email = loginBinding.etEmail.getText().toString();
        String password = loginBinding.etPassword.getText().toString();
        loginBinding.invalidateAll();

        if (TextUtils.isEmpty(email)){
            loginBinding.etEmail.setError("Email cannot be empty");
            loginBinding.etEmail.requestFocus();
        }if (TextUtils.isEmpty(password)){
            loginBinding.etPassword.setError("Password cannot be empty");
            loginBinding.etPassword.requestFocus();
        } else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        SharedPreferences pref = getSharedPreferences("fileName", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(UserName,email);
                        editor.putString(Password,password);
                        editor.apply();
                        Toast.makeText(LoginActivity.this, "User Login Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}