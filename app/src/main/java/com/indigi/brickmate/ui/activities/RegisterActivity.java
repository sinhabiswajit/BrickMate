package com.indigi.brickmate.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
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
import com.indigi.brickmate.databinding.ActivityRegisterBinding;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding registerBinding;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

//        if(mAuth != null){
//            Intent i = new Intent(RegisterActivity.this,DashboardActivity.class);
//            startActivity(i);
//        }


        registerBinding.btnRegister.setOnClickListener(view -> {
            createUser();

        });

        registerBinding.cbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    registerBinding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    registerBinding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        registerBinding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
//                startActivity(i);
//                finish();
                onBackPressed();
            }
        });



    }

    private void createUser() {

        String email = registerBinding.etEmail.getText().toString();
        String password = registerBinding.etPassword.getText().toString();
        registerBinding.invalidateAll();

        if (TextUtils.isEmpty(email)){
            registerBinding.etEmail.setError("Email cannot be empty");
            registerBinding.etEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            registerBinding.etPassword.setError("Password cannot be empty");
            registerBinding.etPassword.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                        startActivity(intent);

                    }else{
                        Toast.makeText(RegisterActivity.this, "Registration Error: " + Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}